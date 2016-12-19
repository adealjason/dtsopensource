package org.github.dtsopensource.local.test.bizService;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * @author ligaofeng 2016年12月8日 下午9:42:20
 */
public interface IPaymentTrade {

    /**
     * 冻结
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void freeze(DTSContext dtsContext) throws DTSBizException;

    /**
     * 解冻并减少扣除
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void deduct(DTSContext dtsContext) throws DTSBizException;

    /**
     * 解冻
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void unfreeze(DTSContext dtsContext) throws DTSBizException;
}
