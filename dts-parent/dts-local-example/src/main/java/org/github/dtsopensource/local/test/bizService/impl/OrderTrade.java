package org.github.dtsopensource.local.test.bizService.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.github.dtsopensource.local.test.GenerateId;
import org.github.dtsopensource.local.test.PurchaseContext;
import org.github.dtsopensource.local.test.bizService.AbstraceOrderTrade;
import org.github.dtsopensource.local.test.bizService.ITradeLog;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.springframework.stereotype.Service;

/**
 * 订单交易
 * 
 * @author ligaofeng 2016年12月8日 下午8:46:32
 */
@Service
public class OrderTrade extends AbstraceOrderTrade {

    @Resource
    private ITradeLog tradeLog;

    @Override
    public void createOrder(DTSContext dtsContext) throws DTSBizException {

        PurchaseContext purchaseContext = dtsContext.getArgs(PurchaseContext.class.getName(), PurchaseContext.class);
        String orderId = GenerateId.generateOrderId();
        String productName = purchaseContext.getProductName();
        dtsContext.addArgs("orderId", orderId);
        String createOrder = "马云购买了一个 " + productName + "，订单号：" + orderId + "金额：￥" + purchaseContext.getOrderAmount()
                + "，当前状态：处理中";
        tradeLog.saveTradeLog("createOrder_" + orderId, createOrder);
    }

    @Override
    public void submitOrder(DTSContext dtsContext) throws DTSBizException {

        PurchaseContext purchaseContext = dtsContext.getArgs(PurchaseContext.class.getName(), PurchaseContext.class);
        String orderId = dtsContext.getArgs("orderId", String.class);

        String productName = purchaseContext.getProductName();
        BigDecimal orderAmount = purchaseContext.getOrderAmount();
        String submitOrder = "马云的订单：" + orderId + "已处理成功，马云花了 ￥" + orderAmount + " 购买了一个 " + productName;
        tradeLog.saveTradeLog("submitOrder_" + orderId, submitOrder);

    }

    @Override
    public void cancleOrder(DTSContext dtsContext) throws DTSBizException {
        String orderId = dtsContext.getArgs("orderId", String.class);
        String cancleOrder = "马云的订单：" + orderId + "已取消成功";
        tradeLog.saveTradeLog("cancleOrder_" + orderId, cancleOrder);
    }

}
