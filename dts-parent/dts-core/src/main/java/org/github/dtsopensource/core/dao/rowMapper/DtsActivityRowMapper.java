package org.github.dtsopensource.core.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.core.dao.dataobject.DtsActivityDO;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author ligaofeng 2016年12月4日 上午11:11:47
 */
public class DtsActivityRowMapper implements RowMapper<DtsActivityDO> {

    @Override
    public DtsActivityDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DtsActivityDO activityDO = new DtsActivityDO();
        activityDO.setActivityId(rs.getString("activity_id"));
        activityDO.setApp(rs.getString("app"));
        activityDO.setBizType(rs.getString("biz_type"));
        String context = rs.getString("context");
        activityDO.setContext(StringUtils.isEmpty(context) ? "" : context.replaceAll("\\\\", ""));
        activityDO.setStatus(rs.getString("status"));
        activityDO.setIsDeleted(rs.getString("is_deleted"));
        activityDO.setGmtCreated(rs.getTimestamp("gmt_created"));
        activityDO.setGmtModified(rs.getTimestamp("gmt_modified"));
        activityDO.setCreator(rs.getString("creator"));
        activityDO.setModifier(rs.getString("modifier"));
        return activityDO;
    }

}
