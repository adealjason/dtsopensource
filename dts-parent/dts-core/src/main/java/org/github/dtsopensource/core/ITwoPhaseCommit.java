package org.github.dtsopensource.core;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * @author ligaofeng 2016年12月16日 下午3:47:39
 */
public interface ITwoPhaseCommit {

    /**
     * 一阶准备
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void pre(DTSContext dtsContext) throws DTSBizException;

    /**
     * 二阶提交
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void commit(DTSContext dtsContext) throws DTSBizException;

    /**
     * 错误回滚
     * 
     * @param dtsContext
     * @throws DTSBizException
     */
    public void rollback(DTSContext dtsContext) throws DTSBizException;
}
