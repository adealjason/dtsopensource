package org.github.dtsopensource.core.dao;

/**
 * @author ligaofeng 2016年12月17日 下午2:53:47
 */
public class ActivityActionRuleSqlConstance {

    private ActivityActionRuleSqlConstance() {
    }

    public static final String select_biz_action_by_biz_type = "SELECT * FROM dts_activity_action_rule WHERE biz_type=? AND is_deleted='N'";
}
