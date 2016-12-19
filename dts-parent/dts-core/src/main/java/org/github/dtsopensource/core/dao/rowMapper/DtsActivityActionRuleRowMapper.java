package org.github.dtsopensource.core.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.github.dtsopensource.core.dao.dataobject.DtsActivityActionRuleDO;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author ligaofeng 2016年12月17日 下午1:28:28
 */
public class DtsActivityActionRuleRowMapper implements RowMapper<DtsActivityActionRuleDO> {

    @Override
    public DtsActivityActionRuleDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DtsActivityActionRuleDO activityActionRuleDO = new DtsActivityActionRuleDO();
        activityActionRuleDO.setBizAction(rs.getString("biz_action"));
        activityActionRuleDO.setBizActionName(rs.getString("biz_action_name"));
        activityActionRuleDO.setBizType(rs.getString("biz_type"));
        activityActionRuleDO.setService(rs.getString("service"));
        activityActionRuleDO.setClazzName(rs.getString("clazz_name"));
        activityActionRuleDO.setTransRecoveryId(rs.getString("trans_recovery_id"));
        activityActionRuleDO.setIsDeleted(rs.getString("is_deleted"));
        activityActionRuleDO.setGmtCreated(rs.getTimestamp("gmt_created"));
        activityActionRuleDO.setGmtModified(rs.getTimestamp("gmt_modified"));
        activityActionRuleDO.setCreator(rs.getString("creator"));
        activityActionRuleDO.setModifier(rs.getString("modifier"));
        return activityActionRuleDO;
    }

}
