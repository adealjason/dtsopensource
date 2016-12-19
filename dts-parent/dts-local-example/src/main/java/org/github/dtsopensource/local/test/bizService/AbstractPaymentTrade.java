package org.github.dtsopensource.local.test.bizService;

import org.github.dtsopensource.core.ITwoPhaseCommit;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * 支付交易
 * 
 * @author ligaofeng 2016年12月8日 下午8:37:35
 */
public abstract class AbstractPaymentTrade implements ITwoPhaseCommit, IPaymentTrade {

    @Override
    public void pre(DTSContext dtsContext) throws DTSBizException {
        this.freeze(dtsContext);
    }

    @Override
    public void commit(DTSContext dtsContext) throws DTSBizException {
        this.deduct(dtsContext);
    }

    @Override
    public void rollback(DTSContext dtsContext) throws DTSBizException {
        this.unfreeze(dtsContext);
    }

}
