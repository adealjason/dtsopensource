package org.github.dtsopensource.admin.service.helper;

import java.util.Date;

import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDO;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.joda.time.DateTime;

/**
 * @author ligaofeng 2016年12月17日 下午2:20:36
 */
public class ActivityRuleHelper {

    private ActivityRuleHelper() {
    }

    /**
     * @param activityRuleDO
     * @return
     */
    public static ActivityRuleEntity toActivityRuleEntity(ActivityRuleDO activityRuleDO) {
        return new ActivityRuleEntity.Builder(activityRuleDO.getBizType()).setApp(activityRuleDO.getApp())
                .setAppCname(activityRuleDO.getAppCname()).setBizTypeName(activityRuleDO.getBizTypeName()).build();
    }

    /**
     * @param activityRuleEntity
     * @return
     */
    public static ActivityRuleDO toActivityRuleDO(ActivityRuleEntity activityRuleEntity) {
        ActivityRuleDO activityRuleDO = new ActivityRuleDO();
        activityRuleDO.setApp(activityRuleEntity.getApp());
        activityRuleDO.setAppCname(activityRuleEntity.getAppCname());
        activityRuleDO.setBizType(activityRuleEntity.getBizType());
        activityRuleDO.setBizTypeName(activityRuleEntity.getBizTypeName());
        activityRuleDO.setIsDeleted("N");
        Date now = DateTime.now().toDate();
        activityRuleDO.setGmtCreated(now);
        activityRuleDO.setGmtModified(now);
        activityRuleDO.setCreator("system");
        activityRuleDO.setModifier("system");
        return activityRuleDO;
    }

}
