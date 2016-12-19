package org.github.dtsopensource.core;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.core.manager.DTSManager;
import org.github.dtsopensource.core.manager.transaction.DTSTransactionSynchronizationAdapter;
import org.github.dtsopensource.core.rule.ActivityRuleMananger;
import org.github.dtsopensource.core.util.ActivityIdGenerator;
import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.rule.IDTSRule;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年11月29日 上午11:09:45
 */
@Slf4j
@Setter
public class BusinessActivityControlService {

    private DTSConfig  dtsConfig;

    private DTSManager dtsManager;

    private IDTSRule   activityRuleMananger;

    /**
     * 初始化httpRemoteRuleProtocol
     */
    @PostConstruct
    public void setDTSRule() {
        activityRuleMananger = new ActivityRuleMananger(dtsConfig.getRequestActivityRuleURL());
    }

    /**
     * 开启分布式事务
     * 
     * @param bizType 业务活动，需到dts-server去配置
     * @return
     * @throws DTSBizException
     */
    public DTSContext start(String bizType) throws DTSBizException {
        ActivityRuleEntity activityRuleEntity = this.getBizTypeRule(bizType);
        try {
            ActivityEntity activityEntity = new ActivityEntity(ActivityIdGenerator.generateActivityId(), dtsConfig,
                    activityRuleEntity);

            //注册进当前线程管理一个Synchronization
            TransactionSynchronization transactionSynchronization = new DTSTransactionSynchronizationAdapter(
                    activityEntity.getActivityId(), dtsManager);

            if (!TransactionSynchronizationManager.isSynchronizationActive()) {
                TransactionSynchronizationManager.initSynchronization();
            }

            TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
            //绑定新开启的一个DTSContext进当前线程事务管理器
            TransactionSynchronizationManager.bindResource(activityEntity.getActivityId(), activityEntity);

            ResultBase<DTSContext> resultBase = dtsManager.openTransaction(activityEntity);
            if (resultBase.isSucess()) {
                //要返回当前业务活动中的context，而非请求返回值中的，
                //因为走remote模式该数据是json反解析的，与业务活动的的context非同一个对象
                return activityEntity.getContext();
            }
            throw new DTSBizException(resultBase.getMessage());
        } catch (DTSBizException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DTSBizException(e);
        }
    }

    private ActivityRuleEntity getBizTypeRule(String bizType) throws DTSBizException {
        if (StringUtils.isEmpty(bizType)) {
            throw new DTSBizException("业务活动不能为空");
        }
        ResultBase<ActivityRuleEntity> getBizTypeRuleResult = activityRuleMananger.getBizTypeRule(bizType);
        if (!getBizTypeRuleResult.isSucess()) {
            throw new DTSBizException(getBizTypeRuleResult.getMessage());
        }
        return getBizTypeRuleResult.getValue();
    }

}
