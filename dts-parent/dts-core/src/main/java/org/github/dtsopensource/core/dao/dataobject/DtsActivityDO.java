package org.github.dtsopensource.core.dao.dataobject;

import java.util.Date;

import lombok.ToString;

/**
 * @author ligaofeng 2016年11月30日 下午4:46:29
 */
@ToString
public class DtsActivityDO {

    private Long   id;

    private String activityId;

    private String app;

    private String bizType;

    private String context;

    private String status;

    private String isDeleted;

    private Date   gmtCreated;

    private Date   gmtModified;

    private String creator;

    private String modifier;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

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
    public String getApp() {
        return app;
    }

    /**
     * @param app
     */
    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    /**
     * @return
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * @param bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
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
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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
}
