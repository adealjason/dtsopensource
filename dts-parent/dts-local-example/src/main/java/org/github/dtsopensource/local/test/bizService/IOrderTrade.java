package org.github.dtsopensource.local.test.bizService;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * @author ligaofeng 2016年12月8日 下午9:40:31
 */
public interface IOrderTrade {

    /**
     * 创建一笔新订单
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void createOrder(DTSContext dtsContext) throws DTSBizException;

    /**
     * 订单处理成功
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void submitOrder(DTSContext dtsContext) throws DTSBizException;

    /**
     * 取消订单
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void cancleOrder(DTSContext dtsContext) throws DTSBizException;
}
