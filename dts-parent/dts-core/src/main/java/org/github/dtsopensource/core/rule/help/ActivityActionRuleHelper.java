package org.github.dtsopensource.core.rule.help;

import org.github.dtsopensource.core.dao.dataobject.DtsActivityActionRuleDO;
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
    public static ActivityActionRuleEntity toActivityActionRuleEntity(DtsActivityActionRuleDO actionRuleDO) {
        return new ActivityActionRuleEntity.Builder(actionRuleDO.getBizAction())
                .setBizActionName(actionRuleDO.getBizActionName()).setBizType(actionRuleDO.getBizType())
                .setClazzName(actionRuleDO.getClazzName()).setService(actionRuleDO.getService())
                .setTransRecoveryId(actionRuleDO.getTransRecoveryId()).build();
    }

}
