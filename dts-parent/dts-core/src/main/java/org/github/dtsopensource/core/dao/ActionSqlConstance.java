package org.github.dtsopensource.core.dao;

/**
 * @author ligaofeng 2016年12月17日 上午11:21:08
 */
public class ActionSqlConstance {

    private ActionSqlConstance() {
    }

    public static final String insert_dts_action                     = "INSERT INTO dts_action(activity_id, action_id, service,clazz_name, action, version, protocol, status, context,is_deleted,gmt_created,gmt_modified,creator,modifier)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String select_dts_action_by_activity_id      = "select * from dts_action where activity_id=? and is_deleted='N'";

    public static final String count_dts_action_by_actionid          = "select count(*) from dts_action where action_id=? and is_deleted='N'";

    public static final String commit_rollback_action_by_activity_id = "update dts_action set status=?,context=?,gmt_modified=? where action_id=? and activity_id=?";
}
