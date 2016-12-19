package org.github.dtsopensource.core.dao.dataobject;

import java.util.Date;

import lombok.ToString;

/**
 * @author ligaofeng 2016年12月2日 下午4:31:02
 */
@ToString
public class DtsActionDO {

    private String activityId;

    private String actionId;

    private String service;

    private String clazzName;

    private String action;

    private String version;

    private String protocol;

    private String status;

    private String context;

    private String isDeleted;

    private Date   gmtCreated;

    private Date   gmtModified;

    private String creator;

    private String modifier;

    /**
     * @return
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * @param activityId
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    /**
     * @return
     */
    public String getActionId() {
        return actionId;
    }

    /**
     * @param actionId
     */
    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }

    /**
     * @return
     */
    public String getService() {
        return service;
    }

    /**
     * @param service
     */
    public void setService(String service) {
        this.service = service == null ? null : service.trim();
    }

    /**
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     */
    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    /**
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * @return
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol == null ? null : protocol.trim();
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * @return
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * @param gmtCreated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * @return
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * @param gmtModified
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * @return
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param modifier
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }
}
