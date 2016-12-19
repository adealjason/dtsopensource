package org.github.dtsopensource.core.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.core.dao.dataobject.DtsActionDO;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author ligaofeng 2016年12月4日 上午11:16:41
 */
public class DtsActionRowMapper implements RowMapper<DtsActionDO> {

    @Override
    public DtsActionDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DtsActionDO actionDO = new DtsActionDO();
        actionDO.setAction(rs.getString("action"));
        actionDO.setActionId(rs.getString("action_id"));
        actionDO.setActivityId(rs.getString("activity_id"));
        String context = rs.getString("context");
        actionDO.setContext(StringUtils.isEmpty(context) ? "" : context.replaceAll("\\\\", ""));
        actionDO.setProtocol(rs.getString("protocol"));
        actionDO.setStatus(rs.getString("status"));
        actionDO.setService(rs.getString("service"));
        actionDO.setClazzName(rs.getString("clazz_name"));
        actionDO.setVersion(rs.getString("version"));
        actionDO.setIsDeleted(rs.getString("is_deleted"));
        actionDO.setGmtCreated(rs.getTimestamp("gmt_created"));
        actionDO.setGmtModified(rs.getTimestamp("gmt_modified"));
        actionDO.setCreator(rs.getString("creator"));
        actionDO.setModifier(rs.getString("modifier"));
        return actionDO;
    }

}
