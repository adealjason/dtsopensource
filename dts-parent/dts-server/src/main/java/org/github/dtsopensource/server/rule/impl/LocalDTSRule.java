package org.github.dtsopensource.server.rule.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.github.dtsopensource.core.dao.ActivityActionRuleSqlConstance;
import org.github.dtsopensource.core.dao.ActivityRuleSqlConstance;
import org.github.dtsopensource.core.dao.dataobject.DtsActivityActionRuleDO;
import org.github.dtsopensource.core.dao.dataobject.DtsActivityRuleDO;
import org.github.dtsopensource.core.dao.rowMapper.DtsActivityActionRuleRowMapper;
import org.github.dtsopensource.core.dao.rowMapper.DtsActivityRuleRowMapper;
import org.github.dtsopensource.core.rule.help.ActivityActionRuleHelper;
import org.github.dtsopensource.core.rule.help.ActivityRuleHelper;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.rule.IDTSRule;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月16日 下午3:52:36
 */
@Slf4j
public class LocalDTSRule implements IDTSRule {

    @Setter
    private DataSource          dataSource;

    private JdbcTemplate        jdbcTemplate;

    private TransactionTemplate transactionTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public ResultBase<ActivityRuleEntity> checkBizType(final String bizType) {
        ResultBase<ActivityRuleEntity> checkResultBase = new ResultBase<ActivityRuleEntity>();
        try {
            List<DtsActivityRuleDO> dtsActivityRuleList = jdbcTemplate.query(
                    ActivityRuleSqlConstance.select_dts_activity_by_biztype, new Object[] { bizType },
                    new DtsActivityRuleRowMapper());

            if (CollectionUtils.isEmpty(dtsActivityRuleList)) {
                checkResultBase.setDtsResultCode(DTSResultCode.FAIL);
                checkResultBase.setMessage("业务活动" + bizType + "尚未定义,请联系dts-server系统管理员配置");
                return checkResultBase;
            }
            DtsActivityRuleDO dtsActivityRuleDO = dtsActivityRuleList.get(0);
            ActivityRuleEntity activityRuleEntity = ActivityRuleHelper.toActivityRuleEntity(dtsActivityRuleDO);
            checkResultBase.setDtsResultCode(DTSResultCode.SUCCESS);
            checkResultBase.setValue(activityRuleEntity);
            return checkResultBase;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            checkResultBase.setDtsResultCode(DTSResultCode.FAIL);
            checkResultBase.setMessage("数据库查询出错");
            return checkResultBase;
        }
    }

    @Override
    public ResultBase<ActivityRuleEntity> getBizTypeRule(String bizType) {
        //校验bizType
        ResultBase<ActivityRuleEntity> checkResult = this.checkBizType(bizType);
        if (!checkResult.isSucess()) {
            return checkResult;
        }
        ResultBase<ActivityRuleEntity> getBizTypeResult = new ResultBase<ActivityRuleEntity>();
        ActivityRuleEntity activityRuleEntity = checkResult.getValue();
        //查询actionRules
        ResultBase<List<ActivityActionRuleEntity>> actionRulesResult = this.getActionRules(bizType);
        if (!actionRulesResult.isSucess()) {
            getBizTypeResult.setDtsResultCode(DTSResultCode.FAIL);
            getBizTypeResult.setMessage(actionRulesResult.getMessage());
            return getBizTypeResult;
        }
        //设置entity
        activityRuleEntity.setActivityActionRules(actionRulesResult.getValue());
        getBizTypeResult.setValue(activityRuleEntity);
        getBizTypeResult.setDtsResultCode(DTSResultCode.SUCCESS);
        return getBizTypeResult;
    }

    private ResultBase<List<ActivityActionRuleEntity>> getActionRules(String bizType) {
        ResultBase<List<ActivityActionRuleEntity>> actionRulesResult = new ResultBase<List<ActivityActionRuleEntity>>();
        try {
            List<DtsActivityActionRuleDO> actionRulesList = jdbcTemplate.query(
                    ActivityActionRuleSqlConstance.select_biz_action_by_biz_type, new Object[] { bizType },
                    new DtsActivityActionRuleRowMapper());

            if (CollectionUtils.isEmpty(actionRulesList)) {
                actionRulesResult.setDtsResultCode(DTSResultCode.FAIL);
                actionRulesResult.setMessage("尚未配置" + bizType + "的bizAction,请联系dts-server管理员配置");
                return actionRulesResult;
            }

            List<ActivityActionRuleEntity> actionRulesEntity = Lists.newArrayList();
            for (DtsActivityActionRuleDO actionRuleDO : actionRulesList) {
                actionRulesEntity.add(ActivityActionRuleHelper.toActivityActionRuleEntity(actionRuleDO));
            }
            actionRulesResult.setDtsResultCode(DTSResultCode.SUCCESS);
            actionRulesResult.setValue(actionRulesEntity);
            return actionRulesResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            actionRulesResult.setDtsResultCode(DTSResultCode.FAIL);
            actionRulesResult.setMessage("数据库查询出错");
            return actionRulesResult;
        }
    }

}
