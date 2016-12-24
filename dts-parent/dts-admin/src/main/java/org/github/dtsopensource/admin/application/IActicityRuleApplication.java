package org.github.dtsopensource.admin.application;

import java.util.List;

import org.github.dtsopensource.admin.exception.DTSAdminException;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

/**
 * @author ligaofeng 2016年12月23日 下午2:01:22
 */
public interface IActicityRuleApplication {

    /**
     * 创建业务活动
     * 
     * @param activityRuleEntity
     * @throws DTSAdminException
     */
    public void addActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException;

    /**
     * 查询业务活动
     * 
     * @param activityRuleEntity
     * @return
     * @throws DTSAdminException
     */
    public List<ActivityRuleEntity> queryActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException;
}
