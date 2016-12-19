package org.github.dtsopensource.server.share.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.github.dtsopensource.server.share.store.Status;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月10日 下午1:32:33
 */
@Getter
@Setter
public class ActivityEntity implements Serializable {

    private static final long  serialVersionUID = 5820980091911999482L;

    private String             activityId;

    private String             bizType;

    private DTSContext         context;

    private DTSConfig          dtsConfig;

    private List<ActionEntity> actionEntities;

    private Status             status;

    /**
     * @param activityId
     * @param context
     * @param dtsConfig
     * @param bizType
     * @param actionEntities
     * @param status
     */
    public ActivityEntity(String activityId, DTSContext context, DTSConfig dtsConfig,
                          ActivityRuleEntity activityRuleEntity, List<ActionEntity> actionEntities, Status status) {

        this.activityId = activityId;
        this.context = context == null ? DTSContext.getInstance() : context;
        this.dtsConfig = dtsConfig;
        this.bizType = activityRuleEntity.getBizType();
        this.actionEntities = actionEntities;
        this.status = status;
        this.context.setActivityId(this.activityId);
        this.context.setActivityRuleEntity(activityRuleEntity);
    }

    /**
     * @param activityId
     * @param context
     * @param dtsConfig
     * @param activityRuleEntity
     */
    public ActivityEntity(String activityId, DTSContext context, DTSConfig dtsConfig,
                          ActivityRuleEntity activityRuleEntity) {
        this(activityId, context, dtsConfig, activityRuleEntity, new ArrayList<ActionEntity>(), Status.S);
    }

    /**
     * @param activityId
     * @param dtsConfig
     * @param activityRuleEntity
     */
    public ActivityEntity(String activityId, DTSConfig dtsConfig, ActivityRuleEntity activityRuleEntity) {
        this(activityId, null, dtsConfig, activityRuleEntity);
    }

    /**
     * 默认构造函数，不推荐使用，用于json转换
     */
    public ActivityEntity() {
        //do nothing
    }

    /**
     * @return
     */
    public String status() {
        return status.name();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 根据builder构建
     * 
     * @param builder
     */
    public ActivityEntity(Builder builder) {
        this.activityId = builder.activityId;
        this.bizType = builder.bizType;
        this.context = builder.context;
        this.dtsConfig = builder.dtsConfig;
        this.actionEntities = builder.actionEntities;
        this.status = builder.status;
    }

    /**
     * ActivityEntity.build
     * 
     * @author ligaofeng 2016年12月16日 上午11:42:10
     */
    @Getter
    public static class Builder {

        private String             activityId;

        private String             bizType;

        private DTSContext         context;

        private DTSConfig          dtsConfig;

        private List<ActionEntity> actionEntities;

        private Status             status;

        /**
         * 初始构造函数
         * 
         * @param activityId
         */
        public Builder(String activityId) {
            this.activityId = activityId;
        }

        public Builder setContext(String context) {
            this.context = JSON.parseObject(context, DTSContext.class);
            return this;
        }

        public Builder setDtsConfig(String dtsConfig) {
            this.dtsConfig = JSON.parseObject(dtsConfig, DTSConfig.class);
            return this;
        }

        public Builder setActionEntities(List<ActionEntity> actionEntities) {
            this.actionEntities = actionEntities;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = Status.valueOf(status);
            return this;
        }

        public Builder setBizType(String bizType) {
            this.bizType = bizType;
            return this;
        }

        /**
         * @return
         */
        public ActivityEntity build() {
            return new ActivityEntity(this);
        }

    }

}
