package org.github.dtsopensource.server.share.store.entity;

import java.io.Serializable;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.protocol.Protocol;
import org.github.dtsopensource.server.share.store.Status;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月10日 下午1:32:15
 */
@Getter
@Setter
public class ActionEntity implements Serializable {

    private static final long serialVersionUID = -3909155342715189478L;

    private String            activityId;

    private String            actionId;

    private DTSContext        context;

    private String            service;

    private String            clazzName;

    private String            action;

    private String            version;

    private Protocol          protocol;

    private Status            status;

    /**
     * @param activityId
     * @param actionId
     * @param context
     * @param service
     * @param clazzName
     * @param action
     * @param version
     * @param protocol
     * @param status
     */
    private ActionEntity(String activityId, String actionId, DTSContext context, String service, String clazzName,
                         String action, String version, Protocol protocol, Status status) {
        this.activityId = activityId;
        this.actionId = actionId;
        this.context = context;
        this.service = service;
        this.clazzName = clazzName;
        this.action = action;
        this.version = version;
        this.protocol = protocol;
        this.status = status;
    }

    /**
     * @param activityId
     * @param actionId
     * @param context
     * @param service
     * @param clazzName
     * @param action
     * @param version
     */
    public ActionEntity(String activityId, String actionId, DTSContext context, String service, String clazzName,
                        String action, String version) {
        this(activityId, actionId, context, service, clazzName, action, version, Protocol.HSF, Status.S);
    }

    /**
     * @param actionId
     * @param context
     * @param service
     * @param clazzName
     * @param action
     * @param version
     */
    public ActionEntity(String actionId, DTSContext context, String service, String clazzName, String action,
                        String version) {
        this(context.getActivityId(), actionId, context, service, clazzName, action, version);
    }

    /**
     * @param actionId
     * @param context
     * @param service
     * @param clazzName
     * @param action
     */
    public ActionEntity(String actionId, DTSContext context, String service, String clazzName, String action) {
        this(actionId, context, service, clazzName, action, "1.0.0");
    }

    /**
     * 根据builder构建
     * 
     * @param builder
     */
    public ActionEntity(Builder builder) {
        this.action = builder.action;
        this.actionId = builder.actionId;
        this.activityId = builder.activityId;
        this.context = builder.context;
        this.protocol = builder.protocol;
        this.service = builder.service;
        this.clazzName = builder.clazzName;
        this.status = builder.status;
        this.version = builder.version;
    }

    /**
     * @return
     */
    public String protocol() {
        return protocol.name();
    }

    /**
     * @return
     */
    public String status() {
        return status.name();
    }

    /**
     * ActionEntity.build
     * 
     * @author ligaofeng 2016年12月10日 下午1:23:12
     */
    @Getter
    public static class Builder {
        private String     activityId;

        private String     actionId;

        private DTSContext context;

        private String     service;

        private String     clazzName;

        private String     action;

        private String     version;

        private Protocol   protocol;

        private Status     status;

        /**
         * 初始构造
         * 
         * @param actionId
         */
        public Builder(String actionId) {
            this.actionId = actionId;
        }

        public Builder setActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder setContext(String context) {
            this.context = JSON.parseObject(context, DTSContext.class);
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

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setProtocol(String protocol) {
            this.protocol = Protocol.valueOf(protocol);
            return this;
        }

        public Builder setStatus(String status) {
            this.status = Status.valueOf(status);
            return this;
        }

        /**
         * @return
         */
        public ActionEntity build() {
            return new ActionEntity(this);
        }
    }

    /**
     * 默认构造函数，不推荐使用 用于json转换
     */
    public ActionEntity() {
        //do nothing
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
