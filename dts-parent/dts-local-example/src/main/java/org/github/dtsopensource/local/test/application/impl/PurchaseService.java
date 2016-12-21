package org.github.dtsopensource.local.test.application.impl;

import javax.annotation.Resource;

import org.github.dtsopensource.core.BusinessActivityControlService;
import org.github.dtsopensource.core.ITwoPhaseCommit;
import org.github.dtsopensource.core.util.ActivityIdGenerator;
import org.github.dtsopensource.local.test.PurchaseContext;
import org.github.dtsopensource.local.test.application.IPurchaseService;
import org.github.dtsopensource.local.test.bizService.ITradeFlowService;
import org.github.dtsopensource.local.test.bizService.ITradeLog;
import org.github.dtsopensource.server.share.DTSContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月8日 下午9:39:11
 */
@Slf4j
@Service
public class PurchaseService implements IPurchaseService {

    @Resource
    private TransactionTemplate            transactionTemplateBiz;
    @Resource
    private BusinessActivityControlService businessActivityControlService;
    @Resource
    private ITwoPhaseCommit                orderTrade;
    @Resource
    private ITwoPhaseCommit                yuePaymentTrade;
    @Resource
    private ITradeFlowService              tradeFlowService;
    @Resource
    private ITradeLog                      tradeLog;

    @Override
    public String puchase(final PurchaseContext purchaseContext) {

        return transactionTemplateBiz.execute(new TransactionCallback<String>() {

            @Override
            public String doInTransaction(TransactionStatus status) {
                String activityId = ActivityIdGenerator.getUUUId();
                try {
                    DTSContext context = businessActivityControlService.start("dts-local-example");
                    activityId = context.getActivityId();
                    context.addArgs(PurchaseContext.class.getName(), purchaseContext);
                    orderTrade.pre(context);
                    yuePaymentTrade.pre(context);
                    String orderId = context.getArgs("orderId", String.class);
                    saveTradeFlow(orderId);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    status.setRollbackOnly();
                    tradeLog.saveTradeLogIfPossible(activityId, "系统异常", e.getMessage());
                }
                return activityId;
            }
        });
    }

    private void saveTradeFlow(String orderId) {
        tradeFlowService.saveTradeFlow("[orderId]:" + orderId);
    }

}
