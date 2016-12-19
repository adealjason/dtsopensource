package org.github.dtsopensource.schedule.taskTracker.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.github.dtsopensource.server.share.schedule.IDTSSchedule;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;
import org.github.dtsopensource.server.share.store.Status;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * dts二阶事务恢复<br>
 * taskTrackerType:hangTransaction
 * 
 * @author ligaofeng 2016年12月14日 下午2:51:33
 */
@Slf4j
public class HangTransactionTask implements IDTSTaskTracker {

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
        log.info("--->start to execute hang transaction task:{}", taskTrackerContext);
        IDTSSchedule dtsSchedule = taskTrackerContext.getDtsSchedule();
        DTSConfig dtsConfig = taskTrackerContext.getDtsConfig();
        ResultBase<List<ActivityEntity>> hangActivityBase = dtsSchedule.getHangTransaction(dtsConfig.getApp());
        log.info("--->get :{} hang Transaction from db,hangActivityBase:{}", dtsConfig.getApp(), hangActivityBase);
        if (!hangActivityBase.isSucess() || CollectionUtils.isEmpty(hangActivityBase.getValue())) {
            return;
        }
        for (ActivityEntity activityEntity : hangActivityBase.getValue()) {
            executor.execute(new HangTransactionAction(activityEntity, dtsSchedule));
        }
    }

    class HangTransactionAction implements Runnable {

        private final ActivityEntity activityEntity;

        private final IDTSSchedule   dtsSchedule;

        public HangTransactionAction(ActivityEntity activityEntity, IDTSSchedule dtsSchedule) {
            this.activityEntity = activityEntity;
            this.dtsSchedule = dtsSchedule;
        }

        @Override
        public void run() {
            if (activityEntity == null || StringUtils.isEmpty(activityEntity.getActivityId())) {
                return;
            }
            //获取DTSContext及ActivityActionRuleEntity
            DTSContext context = activityEntity.getContext();
            ActivityRuleEntity activityRuleEntity = context.getActivityRuleEntity();
            List<ActivityActionRuleEntity> activityActionRules = activityRuleEntity.getActivityActionRules();
            //如果当前未配置bizActionRule则退出
            if (CollectionUtils.isEmpty(activityActionRules)) {
                log.error("--->当前尚未配置bizActionRule,dts事务无法恢复,请联系dts-server管理员配置后在恢复事务,bizType:{}",
                        activityEntity.getBizType());
                return;
            }
            //获取到当前所有的dts事务处理情况
            List<ActionEntity> actionEntities = this.getActionEntities(activityEntity.getActivityId());
            if (CollectionUtils.isEmpty(actionEntities)) {
                //没有产生一阶事务，直接关闭该业务活动
                dtsSchedule.closeActivity(activityEntity.getActivityId(), activityEntity.getStatus(), Status.F);
                return;
            }
            Map<String, List<ActionEntity>> dispatchActions = this.dispatchActionEntities(actionEntities);
            if (!dispatchActions.containsKey("pre")) {
                //没有产生一阶事务，直接关闭该业务活动
                dtsSchedule.closeActivity(activityEntity.getActivityId(), activityEntity.getStatus(), Status.F);
                return;
            }
            List<ActionEntity> preActions = dispatchActions.get("pre");
            List<ActionEntity> rollbackActions = dispatchActions.get("rollback");
            List<ActionEntity> commitActions = dispatchActions.get("commit");
            //确定一阶处理结果
            Status preStatus = this.checkPreActionStatus(preActions);
            if (Status.F == preStatus) {
                this.executeRollback(context.getActivityId(), activityEntity.getStatus(), preActions, rollbackActions,
                        activityActionRules);
                return;
            }
            if (Status.T == preStatus) {
                this.executeCommit(context.getActivityId(), activityEntity.getStatus(), preActions, commitActions,
                        activityActionRules);
            }
        }

        private Status checkPreActionStatus(List<ActionEntity> preActions) {
            int ssize = 0;
            int fsize = 0;
            for (ActionEntity actionEntity : preActions) {
                if (Status.F == actionEntity.getStatus()) {
                    fsize++;
                }
                if (Status.S == actionEntity.getStatus()) {
                    ssize++;
                }
            }
            if (fsize > 0) {
                return Status.F;
            }
            if (ssize > 0) {
                return Status.F;
            }
            return Status.T;
        }

        private void executeCommit(String activityId, Status orginStatus, List<ActionEntity> preActions,
                                   List<ActionEntity> commitActions,
                                   List<ActivityActionRuleEntity> activityActionRules) {
            boolean commitResult = true;
            for (ActivityActionRuleEntity bizActionRule : activityActionRules) {
                boolean handleResult = this.isHandleSuccess(commitActions, bizActionRule.getClazzName());
                //二阶提交成功跳过
                if (handleResult) {
                    continue;
                }
                try {
                    DTSContext context = this.getContextFromPreAction(preActions, bizActionRule.getClazzName());
                    if (context == null) {
                        log.error("--->无法获取DTSContext,事务无法恢复,bizActionRule:{}", bizActionRule);
                        dtsSchedule.closeActivity(activityId, orginStatus, Status.E);
                        return;
                    }
                    dtsSchedule.commit(context, bizActionRule);
                } catch (DTSBizException e) {
                    log.error(e.getMessage(), e);
                    commitResult = false;
                }
            }
            if (commitResult) {
                dtsSchedule.closeActivity(activityId, orginStatus, Status.T);
            }
        }

        private void executeRollback(String activityId, Status orginStatus, List<ActionEntity> preActions,
                                     List<ActionEntity> rollbackActions,
                                     List<ActivityActionRuleEntity> activityActionRules) {
            boolean rollbackResult = true;
            for (ActivityActionRuleEntity bizActionRule : activityActionRules) {
                boolean handleResult = this.isHandleSuccess(rollbackActions, bizActionRule.getClazzName());
                //二阶回滚成功跳过
                if (handleResult) {
                    continue;
                }
                try {
                    DTSContext context = this.getContextFromPreAction(preActions, bizActionRule.getClazzName());
                    if (context == null) {
                        log.error("--->无法获取DTSContext,事务无法恢复,bizActionRule:{}", bizActionRule);
                        dtsSchedule.closeActivity(activityId, orginStatus, Status.E);
                        return;
                    }
                    dtsSchedule.rollback(context, bizActionRule);
                } catch (DTSBizException e) {
                    log.error(e.getMessage(), e);
                    rollbackResult = false;
                }
            }
            if (rollbackResult) {
                dtsSchedule.closeActivity(activityId, orginStatus, Status.R);
            }
        }

        /**
         * 从一阶处理结果中找二阶的DTSContext<br>
         * 二阶是由系统自动触发的，所以在一阶处理的context中要预埋二阶需要的数据
         * 
         * @return
         */
        private DTSContext getContextFromPreAction(List<ActionEntity> preActions, String clazzName) {
            for (ActionEntity actionEntity : preActions) {
                if (actionEntity.getClazzName().equals(clazzName)) {
                    return actionEntity.getContext();
                }
            }
            return null;
        }

        private boolean isHandleSuccess(List<ActionEntity> actionEntities, String clazzName) {
            if (CollectionUtils.isEmpty(actionEntities)) {
                return false;
            }
            for (ActionEntity actionEntity : actionEntities) {
                if (actionEntity.getClazzName().equals(clazzName)) {
                    return Status.T == actionEntity.getStatus();
                }
            }
            return false;
        }

        private Map<String, List<ActionEntity>> dispatchActionEntities(List<ActionEntity> actionEntities) {
            Map<String, List<ActionEntity>> dispatchActions = Maps.newHashMap();
            for (ActionEntity actionEntity : actionEntities) {
                if (dispatchActions.containsKey(actionEntity.getAction())) {
                    List<ActionEntity> targetActionList = dispatchActions.get(actionEntity.getAction());
                    targetActionList.add(actionEntity);
                } else {
                    List<ActionEntity> targetActionList = Lists.newArrayList();
                    targetActionList.add(actionEntity);
                    dispatchActions.put(actionEntity.getAction(), targetActionList);
                }
            }
            return dispatchActions;
        }

        private List<ActionEntity> getActionEntities(String activityId) {
            ResultBase<List<ActionEntity>> actionEntitiesBase = dtsSchedule.getActionEntities(activityId);
            if (actionEntitiesBase.isSucess()) {
                return actionEntitiesBase.getValue();
            }
            return Lists.newArrayList();
        }

    }

}
