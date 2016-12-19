package org.github.dtsopensource.schedule;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.github.dtsopensource.core.DTS;
import org.github.dtsopensource.core.DTSCoreSpringHolder;
import org.github.dtsopensource.core.dao.ActivitySqlConstance;
import org.github.dtsopensource.core.dao.dataobject.DtsActivityDO;
import org.github.dtsopensource.core.dao.rowMapper.DtsActivityRowMapper;
import org.github.dtsopensource.core.store.help.ActivityHelper;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;
import org.github.dtsopensource.server.share.schedule.IDTSSchedule;
import org.github.dtsopensource.server.share.store.Status;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月16日 上午11:12:08
 */
@Slf4j
public class LocalDTSSchedule extends DTS implements IDTSSchedule {

    /**
     * @param jdbcTemplate
     * @param transactionTemplate
     */
    public LocalDTSSchedule(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        super(jdbcTemplate, transactionTemplate);
    }

    @Override
    public ResultBase<List<ActivityEntity>> getHangTransaction(String app) {
        ResultBase<List<ActivityEntity>> resultBase = new ResultBase<List<ActivityEntity>>();
        List<ActivityEntity> activityEntityList = Lists.newArrayList();
        resultBase.setValue(activityEntityList);
        try {
            Object[] params = new Object[] { app, Status.S.name(), DateTime.now().minusMinutes(5).toDate() };
            List<DtsActivityDO> activityList = jdbcTemplate.query(ActivitySqlConstance.select_hang_avtivity, params,
                    new DtsActivityRowMapper());
            resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
            for (DtsActivityDO activityDO : activityList) {
                activityEntityList.add(ActivityHelper.toActivityEntity(activityDO));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage("数据库查询出错");
        }
        return resultBase;
    }

    @Override
    public void closeActivity(final String activityId, final Status orignStatus, final Status targetStatus) {
        log.info("--->start to closeActivity:{},turn status from:{} to:{}", activityId, orignStatus, targetStatus);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update(ActivitySqlConstance.commit_rollback_activity_by_activity_id,
                        new PreparedStatementSetter() {

                            @Override
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, targetStatus.name());
                                ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                                ps.setString(3, activityId);
                                ps.setString(4, orignStatus.name());
                            }
                        });
            }
        });
    }

    @Override
    public void commit(DTSContext context, ActivityActionRuleEntity bizActionRule) throws DTSBizException {
        log.info("--->start to commit action:{},context:{}", bizActionRule.getClazzName(), context);
        //事务恢复受理beanId
        String transRecoveryId = bizActionRule.getTransRecoveryId();
        try {
            this.invokMethod(transRecoveryId, "commit", context);
        } catch (Exception e) {
            throw new DTSBizException(e);
        }
    }

    @Override
    public void rollback(DTSContext context, ActivityActionRuleEntity bizActionRule) throws DTSBizException {
        log.info("--->start to rollback action:{},context:{}", bizActionRule.getClazzName(), context);
        //事务恢复受理beanId
        String transRecoveryId = bizActionRule.getTransRecoveryId();
        try {
            this.invokMethod(transRecoveryId, "rollback", context);
        } catch (Exception e) {
            throw new DTSBizException(e);
        }

    }

    private void invokMethod(String service, String methodName, DTSContext dtsContext) throws Exception {
        Object serviceInstance = DTSCoreSpringHolder.getBean(service);
        Method method = serviceInstance.getClass().getMethod(methodName, DTSContext.class);
        method.invoke(serviceInstance, dtsContext);
    }

}
