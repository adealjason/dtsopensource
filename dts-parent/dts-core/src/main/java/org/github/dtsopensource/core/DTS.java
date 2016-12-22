package org.github.dtsopensource.core;

import java.util.List;

import org.github.dtsopensource.core.dao.ActionSqlConstance;
import org.github.dtsopensource.core.dao.dataobject.DtsActionDO;
import org.github.dtsopensource.core.dao.rowMapper.DtsActionRowMapper;
import org.github.dtsopensource.core.store.help.ActionHelper;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.IDTS;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月16日 上午11:07:30
 */
@Slf4j
public class DTS implements IDTS {

    protected final JdbcTemplate        jdbcTemplate;

    protected final TransactionTemplate transactionTemplate;

    public DTS(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId) {
        ResultBase<List<ActionEntity>> actionListBase = new ResultBase<List<ActionEntity>>();
        List<ActionEntity> actionEntityList = Lists.newArrayList();
        actionListBase.setValue(actionEntityList);
        try {
            Object[] params = new Object[] { activityId };
            List<DtsActionDO> actionList = jdbcTemplate.query(ActionSqlConstance.select_dts_action_by_activity_id,
                    params, new DtsActionRowMapper());
            actionListBase.setDtsResultCode(DTSResultCode.SUCCESS);
            for (DtsActionDO actionDO : actionList) {
                actionEntityList.add(ActionHelper.toActionEntity(actionDO));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            actionListBase.setDtsResultCode(DTSResultCode.FAIL);
            actionListBase.setMessage("数据库查询出错");
        }
        return actionListBase;
    }

}
