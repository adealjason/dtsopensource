package org.github.dtsopensource.core.dao.dataobject;

import java.util.Date;

import lombok.Data;

/**
 * @author ligaofeng 2016年12月17日 下午1:18:14
 */
@Data
public class DtsActivityActionRuleDO {

    private String bizAction;

    private String bizActionName;

    private String bizType;

    private String service;

    private String clazzName;

    private String transRecoveryId;

    private String isDeleted;

    private Date   gmtCreated;

    private Date   gmtModified;

    private String creator;

    private String modifier;

}
