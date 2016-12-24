package org.github.dtsopensource.admin.dao.dataobject;

import java.util.Date;

/**
 * @author ligaofeng 2016年12月23日 下午1:32:52
 */
public class ActivityRuleDO {

    private String bizType;

    private String app;

    private String appCname;

    private String bizTypeName;

    private String isDeleted;

    private Date   gmtCreated;

    private Date   gmtModified;

    private String creator;

    private String modifier;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app == null ? null : app.trim();
    }

    public String getAppCname() {
        return appCname;
    }

    public void setAppCname(String appCname) {
        this.appCname = appCname == null ? null : appCname.trim();
    }

    public String getBizTypeName() {
        return bizTypeName;
    }

    public void setBizTypeName(String bizTypeName) {
        this.bizTypeName = bizTypeName == null ? null : bizTypeName.trim();
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
