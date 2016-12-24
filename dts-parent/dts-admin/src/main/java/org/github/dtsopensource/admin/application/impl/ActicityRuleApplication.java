package org.github.dtsopensource.admin.application.impl;

import java.util.List;

import javax.annotation.Resource;

import org.github.dtsopensource.admin.application.IActicityRuleApplication;
import org.github.dtsopensource.admin.exception.DTSAdminException;
import org.github.dtsopensource.admin.service.IActivityRuleService;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ligaofeng 2016年12月23日 下午2:02:39
 */
@Service
public class ActicityRuleApplication implements IActicityRuleApplication {

    @Resource
    private IActivityRuleService activityRuleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException {
        activityRuleService.saveActivityRule(activityRuleEntity);
    }

    @Override
    public List<ActivityRuleEntity> queryActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException {
        return activityRuleService.getActivityRule(activityRuleEntity);
    }

}
