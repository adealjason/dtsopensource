package org.github.dtsopensource.server.share.protocol;

import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * DTS 连接协议
 * 
 * @author ligaofeng 2016年12月11日 下午12:50:26
 */
public interface IDTSProtocol {

    /**
     * 获取连接
     * 
     * @throws DTSBizException
     */
    public void getConnection() throws DTSBizException;
}
