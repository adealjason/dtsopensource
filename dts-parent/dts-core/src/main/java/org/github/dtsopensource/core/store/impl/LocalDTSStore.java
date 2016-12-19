package org.github.dtsopensource.core.store.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.github.dtsopensource.core.DTS;
import org.github.dtsopensource.core.dao.ActionSqlConstance;
import org.github.dtsopensource.core.dao.ActivitySqlConstance;
import org.github.dtsopensource.core.dao.dataobject.DtsActionDO;
import org.github.dtsopensource.core.dao.dataobject.DtsActivityDO;
import org.github.dtsopensource.core.dao.rowMapper.DtsActivityRowMapper;
import org.github.dtsopensource.core.store.help.ActionHelper;
import org.github.dtsopensource.core.store.help.ActivityHelper;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.github.dtsopensource.server.share.store.Status;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月17日 上午11:25:59
 */
@Slf4j
@Getter
public class LocalDTSStore extends DTS implements IDTSStore {

    /**
     * @param jdbcTemplate
     * @param transactionTemplate
     */
    public LocalDTSStore(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        super(jdbcTemplate, transactionTemplate);
    }

    @Override
    public ResultBase<DTSContext> openTransaction(final ActivityEntity activityEntity) {

        final ResultBase<DTSContext> resultBase = new ResultBase<DTSContext>();

        transactionTemplate.execute(new TransactionCallback<ResultBase<DTSContext>>() {

            @Override
            public ResultBase<DTSContext> doInTransaction(TransactionStatus status) {
                try {
                    final DtsActivityDO activityDO = ActivityHelper.toDtsActivityDO(activityEntity);
                    jdbcTemplate.update(ActivitySqlConstance.insert_dts_activity, new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, activityDO.getActivityId());
                            ps.setString(2, activityDO.getApp());
                            ps.setString(3, activityDO.getBizType());
                            ps.setString(4, activityDO.getContext());
                            ps.setString(5, activityDO.getStatus());
                            ps.setString(6, activityDO.getIsDeleted());
                            ps.setTimestamp(7, new Timestamp(activityDO.getGmtCreated().getTime()));
                            ps.setTimestamp(8, new Timestamp(activityDO.getGmtModified().getTime()));
                            ps.setString(9, activityDO.getCreator());
                            ps.setString(10, activityDO.getModifier());
                        }
                    });
                    resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                    resultBase.setValue(activityEntity.getContext());
                    return resultBase;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    resultBase.setDtsResultCode(DTSResultCode.FAIL);
                    resultBase.setMessage("保存业务活动失败,reason:" + e.getMessage());
                    return resultBase;
                }
            }
        });
        log.info(resultBase.toString());
        return resultBase;
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        final ResultBase<String> resultBase = new ResultBase<String>();
        try {
            final DtsActionDO actionDO = ActionHelper.toDtsActionDO(actionEntity);
            //check是否存在
            Object[] params = new Object[] { actionDO.getActionId() };
            Integer count = jdbcTemplate.queryForObject(ActionSqlConstance.count_dts_action_by_actionid, params,
                    Integer.class);
            if (count != null && count.intValue() > 0) {
                resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                resultBase.setValue(actionDO.getActionId());
                return resultBase;
            }
            //保存
            transactionTemplate.execute(new TransactionCallback<ResultBase<String>>() {
                @Override
                public ResultBase<String> doInTransaction(TransactionStatus status) {
                    try {
                        jdbcTemplate.update(ActionSqlConstance.insert_dts_action, new PreparedStatementSetter() {

                            @Override
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setString(1, actionDO.getActivityId());
                                ps.setString(2, actionDO.getActionId());
                                ps.setString(3, actionDO.getService());
                                ps.setString(4, actionDO.getClazzName());
                                ps.setString(5, actionDO.getAction());
                                ps.setString(6, actionDO.getVersion());
                                ps.setString(7, actionDO.getProtocol());
                                ps.setString(8, actionDO.getStatus());
                                ps.setString(9, actionDO.getContext());
                                ps.setString(10, actionDO.getIsDeleted());
                                ps.setTimestamp(11, new Timestamp(actionDO.getGmtCreated().getTime()));
                                ps.setTimestamp(12, new Timestamp(actionDO.getGmtModified().getTime()));
                                ps.setString(13, actionDO.getCreator());
                                ps.setString(14, actionDO.getModifier());
                            }
                        });
                        resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                        resultBase.setValue(actionDO.getActionId());
                        return resultBase;
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        status.setRollbackOnly();
                        resultBase.setDtsResultCode(DTSResultCode.FAIL);
                        resultBase.setMessage("保存业务动作失败,reason:" + e.getMessage());
                        return resultBase;
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage("保存业务动作失败,reason:" + e.getMessage());
        }
        return resultBase;
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        final ResultBase<String> resultBase = new ResultBase<String>();
        final DtsActionDO actionDO = this.castDtsActionDO(actionEntity);

        transactionTemplate.execute(new TransactionCallback<ResultBase<String>>() {

            @Override
            public ResultBase<String> doInTransaction(TransactionStatus status) {
                try {
                    jdbcTemplate.update(ActionSqlConstance.commit_rollback_action_by_activity_id,
                            new PreparedStatementSetter() {

                                @Override
                                public void setValues(PreparedStatement ps) throws SQLException {
                                    ps.setString(1, actionDO.getStatus());
                                    ps.setString(2, actionDO.getContext());
                                    ps.setTimestamp(3, new Timestamp(actionDO.getGmtModified().getTime()));
                                    ps.setString(4, actionDO.getActionId());
                                    ps.setString(5, actionDO.getActivityId());
                                }
                            });
                    resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                    resultBase.setValue(actionDO.getActionId());
                    return resultBase;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    resultBase.setDtsResultCode(DTSResultCode.FAIL);
                    resultBase.setMessage("更新action失败,reason:" + e.getMessage());
                    return resultBase;
                }
            }

        });
        return resultBase;
    }

    private DtsActionDO castDtsActionDO(ActionEntity actionEntity) {
        DtsActionDO actionDO = new DtsActionDO();
        actionDO.setGmtModified(new Date());
        actionDO.setStatus(actionEntity.getStatus().name());
        actionDO.setContext(JSON.toJSONString(actionEntity.getContext()));
        actionDO.setActivityId(actionEntity.getActivityId());
        actionDO.setActionId(actionEntity.getActionId());
        return actionDO;
    }

    @Override
    public ResultBase<String> commitActivity(String activityId) {
        final ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setValue(activityId);

        final ResultBase<DtsActivityDO> activityDOResult = this.getActivityDO(activityId);
        if (!activityDOResult.isSucess() || activityDOResult.getValue() == null) {
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage("无效业务活动Id或者数据库查询报错:" + activityId);
            return resultBase;
        }
        //提交业务活动
        DtsActivityDO activityDO = activityDOResult.getValue();
        ResultBase<String> commitActivityResult = this.commitActivity(activityDO);
        if (!commitActivityResult.isSucess()) {
            log.info("业务活动提交失败,commitActivityResult:{}", commitActivityResult);
            return commitActivityResult;
        }
        resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
        resultBase.setMessage("业务活动提交成功");
        return resultBase;
    }

    private ResultBase<String> commitActivity(final DtsActivityDO activityDO) {
        final ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setValue(activityDO.getActivityId());

        transactionTemplate.execute(new TransactionCallback<ResultBase<String>>() {

            @Override
            public ResultBase<String> doInTransaction(TransactionStatus status) {
                try {
                    jdbcTemplate.update(ActivitySqlConstance.commit_rollback_activity_by_activity_id,
                            new PreparedStatementSetter() {

                                @Override
                                public void setValues(PreparedStatement ps) throws SQLException {
                                    ps.setString(1, Status.T.name());
                                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                                    ps.setString(3, activityDO.getActivityId());
                                    ps.setString(4, Status.S.name());
                                }
                            });
                    resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                    return resultBase;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    resultBase.setDtsResultCode(DTSResultCode.FAIL);
                    resultBase.setMessage("保存业务动作失败,reason:" + e.getMessage());
                    return resultBase;
                }
            }

        });
        return resultBase;
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setValue(activityId);

        final ResultBase<DtsActivityDO> activityDOResult = this.getActivityDO(activityId);

        if (!activityDOResult.isSucess() || activityDOResult.getValue() == null) {
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage("无效业务活动Id或者数据库查询报错:" + activityId);
            return resultBase;
        }
        //回滚务活动
        DtsActivityDO activityDO = activityDOResult.getValue();
        ResultBase<String> rollbackResult = this.rollbackActivity(activityDO);
        if (!rollbackResult.isSucess()) {
            return rollbackResult;
        }
        resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
        resultBase.setMessage("业务活动回滚成功");
        return resultBase;
    }

    private ResultBase<String> rollbackActivity(final DtsActivityDO activityDO) {
        final ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setValue(activityDO.getActivityId());

        transactionTemplate.execute(new TransactionCallback<ResultBase<String>>() {

            @Override
            public ResultBase<String> doInTransaction(TransactionStatus status) {
                try {
                    jdbcTemplate.update(ActivitySqlConstance.commit_rollback_activity_by_activity_id,
                            new PreparedStatementSetter() {

                                @Override
                                public void setValues(PreparedStatement ps) throws SQLException {
                                    ps.setString(1, Status.R.name());
                                    ps.setTimestamp(2, new Timestamp(new Date().getTime()));
                                    ps.setString(3, activityDO.getActivityId());
                                    ps.setString(4, Status.S.name());
                                }
                            });
                    resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
                    return resultBase;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    resultBase.setDtsResultCode(DTSResultCode.FAIL);
                    resultBase.setMessage("回滚业务活动失败,reason:" + e.getMessage());
                    return resultBase;
                }
            }

        });
        return resultBase;
    }

    private ResultBase<DtsActivityDO> getActivityDO(String activityId) {
        ResultBase<DtsActivityDO> resultBase = new ResultBase<DtsActivityDO>();
        try {
            DtsActivityDO activityDO = jdbcTemplate.queryForObject(
                    ActivitySqlConstance.select_dts_activity_by_activity_id, new Object[] { activityId },
                    new DtsActivityRowMapper());
            resultBase.setDtsResultCode(DTSResultCode.SUCCESS);
            resultBase.setValue(activityDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage("数据库查询出错");
        }
        return resultBase;
    }

}
