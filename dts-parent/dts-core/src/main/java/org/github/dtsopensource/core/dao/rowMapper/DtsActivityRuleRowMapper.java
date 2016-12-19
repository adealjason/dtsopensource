package org.github.dtsopensource.core.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dtsopensource.core.dao.dataobject.DtsActivityRuleDO;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author ligaofeng 2016年12月16日 下午4:19:23
 */
public class DtsActivityRuleRowMapper implements RowMapper<DtsActivityRuleDO> {

    @Override
    public DtsActivityRuleDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DtsActivityRuleDO activityRuleDO = new DtsActivityRuleDO();
        activityRuleDO.setApp(rs.getString("app"));
        activityRuleDO.setBizType(rs.getString("biz_type"));
        activityRuleDO.setIsDeleted(rs.getString("is_deleted"));
        activityRuleDO.setGmtCreated(rs.getTimestamp("gmt_created"));
        activityRuleDO.setGmtModified(rs.getTimestamp("gmt_modified"));
        activityRuleDO.setCreator(rs.getString("creator"));
        activityRuleDO.setModifier(rs.getString("modifier"));
        return activityRuleDO;
    }

}
