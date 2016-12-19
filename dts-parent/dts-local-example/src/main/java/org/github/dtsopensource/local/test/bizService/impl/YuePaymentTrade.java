package org.github.dtsopensource.local.test.bizService.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.github.dtsopensource.local.test.GenerateId;
import org.github.dtsopensource.local.test.PurchaseContext;
import org.github.dtsopensource.local.test.bizService.AbstractPaymentTrade;
import org.github.dtsopensource.local.test.bizService.ITradeLog;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.springframework.stereotype.Service;

/**
 * 余额支付交易
 * 
 * @author ligaofeng 2016年12月8日 下午9:19:41
 */
@Service
public class YuePaymentTrade extends AbstractPaymentTrade {
    @Resource
    private ITradeLog tradeLog;

    @Override
    public void freeze(DTSContext dtsContext) throws DTSBizException {
        PurchaseContext purchaseContext = dtsContext.getArgs(PurchaseContext.class.getName(), PurchaseContext.class);
        String orderId = dtsContext.getArgs("orderId", String.class);
        String paymentId = GenerateId.generatePaymentId();
        dtsContext.addArgs("paymentId", paymentId);

        boolean freezeResult = purchaseContext.getCurrentAmount().compareTo(purchaseContext.getOrderAmount()) >= 0;
        String freeze = "马云的订单：" + orderId;

        if (freezeResult) {
            BigDecimal yue = purchaseContext.getCurrentAmount().subtract(purchaseContext.getOrderAmount());
            freeze = freeze + " 冻结金额￥" + purchaseContext.getOrderAmount() + "成功，账户可用余额：￥" + yue;
            tradeLog.saveTradeLog("freeze_" + paymentId, freeze);
        } else {
            freeze = freeze + "由于余额不足扣款失败";
            tradeLog.saveTradeLog("freeze_" + paymentId, freeze);
            throw new DTSBizException(freeze);
        }
    }

    @Override
    public void deduct(DTSContext dtsContext) throws DTSBizException {
        PurchaseContext purchaseContext = dtsContext.getArgs(PurchaseContext.class.getName(), PurchaseContext.class);
        String orderId = dtsContext.getArgs("orderId", String.class);
        String paymentId = dtsContext.getArgs("paymentId", String.class);

        String deduct = "马云的订单：" + orderId;

        boolean deductResult = true;
        if (deductResult) {
            BigDecimal yue = purchaseContext.getCurrentAmount().subtract(purchaseContext.getOrderAmount());
            deduct = deduct + "扣款成功，账户可用余额：￥" + yue;
            tradeLog.saveTradeLog("deduct_" + paymentId, deduct);
        } else {
            deduct = deduct + "扣款失败，账户可用余额：￥" + purchaseContext.getCurrentAmount();
            tradeLog.saveTradeLog("deduct_" + paymentId, deduct);
            throw new DTSBizException(deduct);
        }

    }

    @Override
    public void unfreeze(DTSContext dtsContext) throws DTSBizException {
        PurchaseContext purchaseContext = dtsContext.getArgs(PurchaseContext.class.getName(), PurchaseContext.class);
        String orderId = dtsContext.getArgs("orderId", String.class);
        String paymentId = dtsContext.getArgs("paymentId", String.class);

        String unfreeze = "马云的订单：" + orderId;

        boolean unfreezeResult = true;
        if (unfreezeResult) {
            unfreeze = unfreeze + "解冻成功，账户可用余额：￥" + purchaseContext.getCurrentAmount();
            tradeLog.saveTradeLog("unfreeze_" + paymentId, unfreeze);
        } else {
            BigDecimal yue = purchaseContext.getCurrentAmount().subtract(purchaseContext.getOrderAmount());
            unfreeze = unfreeze + "解冻失败，账户可用余额：￥" + yue;
            tradeLog.saveTradeLog("unfreeze_" + paymentId, unfreeze);
            throw new DTSBizException(unfreeze);
        }
    }

}
