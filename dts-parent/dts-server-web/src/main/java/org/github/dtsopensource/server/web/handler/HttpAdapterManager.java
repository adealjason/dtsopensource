package org.github.dtsopensource.server.web.handler;

import java.util.Map;

import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;

/**
 * @author ligaofeng 2016年12月18日 上午1:53:38
 */
public abstract class HttpAdapterManager {

    /**
     * 路由处理
     * 
     * @param params
     * @throws DTSBizException
     */
    public void adapter(Map<String, Object> params) throws DTSBizException {
        String operation = (String) params.get(ProtocolConstance.requestStoreOperation);
        ProtocolMethod protocolmethod = ProtocolMethod.valueOf(operation);
        this.handle(protocolmethod, params);
    }

    /**
     * @param protocolmethod
     * @param params
     */
    protected abstract void handle(ProtocolMethod protocolmethod, Map<String, Object> params);

    protected void unsupprotMethod(Map<String, Object> params) {
        ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setDtsResultCode(DTSResultCode.FAIL);
        resultBase.setMessage("dts-server尚不支持该方法:method:"
                + ((ProtocolMethod) params.get(ProtocolConstance.requestStoreOperation)).name());
        this.buildSuccessResponse(params, resultBase);
    }

    protected void buildSuccessResponse(Map<String, Object> params, Object resultBase) {
        params.put(ProtocolConstance.resultObject, resultBase);
    }
}
