package org.github.dtsopensource.core.dao.dataobject;

import java.util.Date;

import lombok.Data;

/**
 * @author ligaofeng 2016年12月16日 下午4:17:23
 */
@Data
public class DtsActivityRuleDO {

    private String bizType;

    private String bizTypeName;

    private String app;

    private String appCname;

    private String creator;

    private String modifier;

    private String isDeleted;

    private Date   gmtCreated;

    private Date   gmtModified;

}
