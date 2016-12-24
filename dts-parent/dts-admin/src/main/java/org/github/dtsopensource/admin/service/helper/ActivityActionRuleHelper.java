package org.github.dtsopensource.admin.service.helper;

import org.github.dtsopensource.admin.dao.dataobject.ActivityActionRuleDO;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;

/**
 * @author ligaofeng 2016年12月17日 下午2:59:23
 */
public class ActivityActionRuleHelper {

    private ActivityActionRuleHelper() {
    }

    /**
     * @param actionRuleDO
     * @return
     */
    public static ActivityActionRuleEntity toActivityActionRuleEntity(ActivityActionRuleDO actionRuleDO) {
        return new ActivityActionRuleEntity.Builder(actionRuleDO.getBizAction())
                .setBizActionName(actionRuleDO.getBizActionName()).setBizType(actionRuleDO.getBizType())
                .setClazzName(actionRuleDO.getClazzName()).setService(actionRuleDO.getService())
                .setTransRecoveryId(actionRuleDO.getTransRecoveryId()).build();
    }

}
