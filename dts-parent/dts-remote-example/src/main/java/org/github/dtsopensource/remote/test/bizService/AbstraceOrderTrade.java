package org.github.dtsopensource.remote.test.bizService;

import org.github.dtsopensource.core.ITwoPhaseCommit;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * 订单交易
 * 
 * @author ligaofeng 2016年12月8日 下午8:35:55
 */
public abstract class AbstraceOrderTrade implements ITwoPhaseCommit, IOrderTrade {

    @Override
    public void pre(DTSContext dtsContext) throws DTSBizException {
        this.createOrder(dtsContext);
    }

    @Override
    public void commit(DTSContext dtsContext) throws DTSBizException {
        this.submitOrder(dtsContext);
    }

    @Override
    public void rollback(DTSContext dtsContext) throws DTSBizException {
        this.cancleOrder(dtsContext);
    }

}
