package org.github.dtsopensource.admin.service;

import java.util.List;

import org.github.dtsopensource.admin.exception.DTSAdminException;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

/**
 * @author ligaofeng 2016年12月23日 下午1:33:27
 */
public interface IActivityRuleService {

    /**
     * 保存业务活动
     * 
     * @param activityRuleEntity
     * @throws DTSAdminException
     */
    public void saveActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException;

    /**
     * 查询业务活动
     * 
     * @param activityRuleEntity
     * @return
     * @throws DTSAdminException
     */
    public List<ActivityRuleEntity> getActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException;
}
