package org.github.dtsopensource.core.rule.help;

import org.github.dtsopensource.core.dao.dataobject.DtsActivityRuleDO;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

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
    public static ActivityRuleEntity toActivityRuleEntity(DtsActivityRuleDO activityRuleDO) {
        return new ActivityRuleEntity.Builder(activityRuleDO.getBizType()).setApp(activityRuleDO.getApp())
                .setAppCname(activityRuleDO.getAppCname()).setBizTypeName(activityRuleDO.getBizTypeName()).build();
    }

}
