package org.github.dtsopensource.server.share.rule.entity;

import java.io.Serializable;
import java.util.List;

import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月17日 下午2:01:19
 */
@Getter
@Setter
public class ActivityRuleEntity implements Serializable {

    private static final long              serialVersionUID = 106899067427742570L;

    private String                         bizType;

    private String                         bizTypeName;

    private String                         app;

    private String                         appCname;

    private List<ActivityActionRuleEntity> activityActionRules;

    /**
     * 根据clazzName获取bizAction配置
     * 
     * @param clazzName dts-server配置的信息
     * @return
     */
    public ActivityActionRuleEntity getActivityActionRuleEntity(String clazzName) {
        if (activityActionRules == null || activityActionRules.isEmpty()) {
            throw new DTSRuntimeException("尚未配置" + clazzName + "的bizAction,请联系dts-server管理员配置");
        }
        for (ActivityActionRuleEntity an : activityActionRules) {
            if (an.getClazzName().equals(clazzName)) {
                return an;
            }
        }
        throw new DTSRuntimeException("尚未配置" + clazzName + "的bizAction,请联系dts-server管理员配置");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * ActivityRuleEntity.builder
     * 
     * @param builder
     */
    public ActivityRuleEntity(Builder builder) {
        this.app = builder.app;
        this.appCname = builder.appCname;
        this.bizType = builder.bizType;
        this.bizTypeName = builder.bizTypeName;
    }

    /**
     * used for fastjson
     */
    public ActivityRuleEntity() {
    }

    @Getter
    public static class Builder {
        private String bizType;

        private String bizTypeName;

        private String app;

        private String appCname;

        public Builder(String bizType) {
            this.bizType = bizType;
        }

        public Builder setBizTypeName(String bizTypeName) {
            this.bizTypeName = bizTypeName;
            return this;
        }

        public Builder setApp(String app) {
            this.app = app;
            return this;
        }

        public Builder setAppCname(String appCname) {
            this.appCname = appCname;
            return this;
        }

        public ActivityRuleEntity build() {
            return new ActivityRuleEntity(this);
        }
    }

}
