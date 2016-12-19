package org.github.dtsopensource.server.share.rule.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月17日 下午2:01:00
 */
@Getter
@Setter
public class ActivityActionRuleEntity implements Serializable {

    private static final long serialVersionUID = -4294822530381109974L;

    private String            bizAction;

    private String            bizActionName;

    private String            bizType;

    private String            service;

    private String            clazzName;

    private String            transRecoveryId;

    /**
     * used for fastjson
     */
    public ActivityActionRuleEntity() {
    }

    /**
     * @param builder
     */
    public ActivityActionRuleEntity(Builder builder) {
        this.bizAction = builder.bizAction;
        this.bizActionName = builder.bizActionName;
        this.bizType = builder.bizType;
        this.clazzName = builder.clazzName;
        this.service = builder.service;
        this.transRecoveryId = builder.transRecoveryId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * ActivityActionRuleEntity.builder
     * 
     * @author ligaofeng 2016年12月17日 下午3:04:39
     */
    @Getter
    public static class Builder {
        private String bizAction;

        private String bizActionName;

        private String bizType;

        private String service;

        private String clazzName;

        private String transRecoveryId;

        public Builder(String bizAction) {
            this.bizAction = bizAction;
        }

        public Builder setBizActionName(String bizActionName) {
            this.bizActionName = bizActionName;
            return this;
        }

        public Builder setBizType(String bizType) {
            this.bizType = bizType;
            return this;
        }

        public Builder setService(String service) {
            this.service = service;
            return this;
        }

        public Builder setClazzName(String clazzName) {
            this.clazzName = clazzName;
            return this;
        }

        public Builder setTransRecoveryId(String transRecoveryId) {
            this.transRecoveryId = transRecoveryId;
            return this;
        }

        public ActivityActionRuleEntity build() {
            return new ActivityActionRuleEntity(this);
        }
    }

}
