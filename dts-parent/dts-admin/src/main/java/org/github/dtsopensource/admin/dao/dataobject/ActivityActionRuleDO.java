package org.github.dtsopensource.admin.dao.dataobject;

import java.util.Date;

/**
 * @author ligaofeng 2016年12月23日 下午1:32:44
 */
public class ActivityActionRuleDO {

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

    public String getBizAction() {
        return bizAction;
    }

    public void setBizAction(String bizAction) {
        this.bizAction = bizAction == null ? null : bizAction.trim();
    }

    public String getBizActionName() {
        return bizActionName;
    }

    public void setBizActionName(String bizActionName) {
        this.bizActionName = bizActionName == null ? null : bizActionName.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service == null ? null : service.trim();
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName == null ? null : clazzName.trim();
    }

    public String getTransRecoveryId() {
        return transRecoveryId;
    }

    public void setTransRecoveryId(String transRecoveryId) {
        this.transRecoveryId = transRecoveryId == null ? null : transRecoveryId.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }
}
