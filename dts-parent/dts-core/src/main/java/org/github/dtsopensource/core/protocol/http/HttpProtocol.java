package org.github.dtsopensource.core.protocol.http;

import java.util.List;
import java.util.Map;

import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtoclCallback;
import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtocolParent;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理http协议业务需求
 * 
 * @author ligaofeng 2016年12月11日 下午12:38:25
 */
@Slf4j
@Getter
@Setter
public class HttpProtocol extends HttpProtocolParent implements IDTSStore {

    /**
     * init HttpProtocol
     * 
     * @param serverURL
     * @param timeOut
     * @param maxTotal
     * @param maxPerRoute
     * @param maxRoute
     */
    public HttpProtocol(String serverURL, int timeOut, int maxTotal, int maxPerRoute, int maxRoute) {
        super(serverURL, timeOut, maxTotal, maxPerRoute, maxRoute);
    }

    @Override
    public ResultBase<DTSContext> openTransaction(final ActivityEntity activityEntity) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, activityEntity);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.openTransaction);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<DTSContext>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<DTSContext> resultBase = new ResultBase<DTSContext>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<String> getAndCreateAction(final ActionEntity actionEntity) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, actionEntity);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.getAndCreateAction);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<String>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<String> resultBase = new ResultBase<String>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<String> commitActivity(final String activityId) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, activityId);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.commitActivity);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<String>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<String> resultBase = new ResultBase<String>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<String> rollbackActivity(final String activityId) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, activityId);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.rollbackActivity);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<String>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<String> resultBase = new ResultBase<String>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<String> updateAction(final ActionEntity actionEntity) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, actionEntity);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.updateAction);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<String>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<String> resultBase = new ResultBase<String>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(final String activityId) {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {
                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, activityId);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.getActionEntities);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<List<ActionEntity>>>() {
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ResultBase<List<ActionEntity>> resultBase = new ResultBase<List<ActionEntity>>();
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

}
