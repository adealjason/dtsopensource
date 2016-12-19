package org.github.dtsopensource.core.dao;

/**
 * @author ligaofeng 2016年12月16日 下午8:30:11
 */
public class ActivitySqlConstance {

    private ActivitySqlConstance() {
    }

    public static final String insert_dts_activity                     = "INSERT INTO dts_activity(activity_id,app,biz_type,context,status,is_deleted,gmt_created,gmt_modified,creator,modifier) VALUES(?,?,?,?,?,?,?,?,?,?)";

    //查询二阶hang住的业务活动
    public static final String select_hang_avtivity                    = "select * from dts_activity where app=? and status=? and gmt_created<=? and is_deleted='N'";

    public static final String select_dts_activity_by_activity_id      = "select * from dts_activity where activity_id=? and is_deleted='N'";

    public static final String commit_rollback_activity_by_activity_id = "update dts_activity set status=?,gmt_modified=? where activity_id=? and status=?";

}
