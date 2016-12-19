package org.github.dtsopensource.remote.test.bizService;

/**
 * 交易流水记录
 * 
 * @author ligaofeng 2016年12月8日 下午9:48:54
 */
public interface ITradeFlowService {

    /**
     * 登记交易流水
     * 
     * @param flow
     */
    public void saveTradeFlow(String flow);
}
