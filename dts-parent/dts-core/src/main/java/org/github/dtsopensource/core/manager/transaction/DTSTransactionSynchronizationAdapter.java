package org.github.dtsopensource.core.manager.transaction;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.github.dtsopensource.core.DTSCoreSpringHolder;
import org.github.dtsopensource.core.manager.DTSManager;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * dts事务同步适配器
 * 
 * @author ligaofeng 2016年12月3日 下午4:49:54
 */
@Slf4j
public class DTSTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter {

    @Getter
    private final String     activityId;
    @Getter
    private final DTSManager dtsManager;

    /**
     * @param activityId
     * @param dtsManager
     */
    public DTSTransactionSynchronizationAdapter(String activityId, DTSManager dtsManager) {
        this.activityId = activityId;
        this.dtsManager = dtsManager;
    }

    @Override
    public void afterCompletion(int status) {
        log.info("本地事务同步状态,activityId:{},status:{}", activityId, status);
        try {

            switch (status) {
                case STATUS_COMMITTED:
                    this.transactionCommit();
                    break;
                case STATUS_ROLLED_BACK:
                    this.transactionRollback();
                    break;
                case STATUS_UNKNOWN:
                default:
                    this.transactionUnknown();
                    break;
            }
        } finally {
            TransactionSynchronizationManager.unbindResourceIfPossible(activityId);
            log.info("--->释放TransactionSynchronizationManager资源...");
            DTSContext.clear();
            log.info("--->释放DTSContext资源...");
        }
    }

    private void transactionCommit() {
        try {
            this.callStep2(activityId, "commit");
            ResultBase<String> resultBase = dtsManager.commitActivity(activityId);
            log.info("二阶事务提交结果,activityId:{},resultBase:{}", activityId, resultBase);
            if (!resultBase.isSucess()) {
                alarm();
            }
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            alarm();
        }
    }

    private void transactionRollback() {
        try {
            this.callStep2(activityId, "rollback");
            ResultBase<String> resultBase = dtsManager.rollbackActivity(activityId);
            log.info("二阶事务回滚结果,activityId:{},resultBase:{}", activityId, resultBase);
            if (!resultBase.isSucess()) {
                alarm();
            }
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            alarm();
        }
    }

    private void callStep2(String activityId, String methodName) throws DTSBizException {
        try {
            ResultBase<List<ActionEntity>> resultBase = dtsManager.getActionEntities(activityId);
            if (resultBase == null || !resultBase.isSucess() || CollectionUtils.isEmpty(resultBase.getValue())) {
                return;
            }
            for (ActionEntity actionEntity : resultBase.getValue()) {
                this.invokMethod(actionEntity.getService(), methodName, actionEntity.getContext());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DTSBizException(e);
        }
    }

    private void invokMethod(String service, String methodName, DTSContext dtsContext) throws Exception {
        Object serviceInstance = DTSCoreSpringHolder.getBean(service);
        Method method = serviceInstance.getClass().getMethod(methodName, DTSContext.class);
        method.invoke(serviceInstance, dtsContext);
    }

    private void transactionUnknown() {
        this.alarm();
    }

    private void alarm() {

    }

}
