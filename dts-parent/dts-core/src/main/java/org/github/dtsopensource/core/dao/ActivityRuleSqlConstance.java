package org.github.dtsopensource.core.dao;

/**
 * @author ligaofeng 2016年12月17日 下午12:00:42
 */
public class ActivityRuleSqlConstance {

    private ActivityRuleSqlConstance() {
    }

    public static final String select_dts_activity_by_biztype = "select * from dts_activity_rule where biz_type=? and is_deleted='N'";

}
