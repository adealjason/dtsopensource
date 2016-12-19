package org.github.dtsopensource.server.web.handler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.github.dtsopensource.server.store.http.impl.HttpServerStoreProtocol;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * @author ligaofeng 2016年12月12日 下午1:27:08
 */
@Component
public class HttpStoreAdapter extends HttpAdapterManager {

    @Resource
    private HttpServerStoreProtocol httpServerStoreProtocol;

    @Override
    protected void handle(ProtocolMethod protocolmethod, Map<String, Object> params) {
        switch (protocolmethod) {
            case openTransaction:
                this.openTransaction(params);
                break;
            case getAndCreateAction:
                this.getAndCreateAction(params);
                break;
            case commitActivity:
                this.commitActivity(params);
                break;
            case rollbackActivity:
                this.rollbackActivity(params);
                break;
            case updateAction:
                this.updateAction(params);
                break;
            case getActionEntities:
                this.getActionEntities(params);
                break;
            case unkonwn:
            default:
                this.unsupprotMethod(params);
                break;
        }
    }

    private void getActionEntities(Map<String, Object> params) {
        String activityId = String.valueOf(params.get(ProtocolConstance.paramObject));
        ResultBase<List<ActionEntity>> resultBase = httpServerStoreProtocol.getActionEntities(activityId);
        this.buildSuccessResponse(params, resultBase);
    }

    private void updateAction(Map<String, Object> params) {
        String paramObjectJson = String.valueOf(params.get(ProtocolConstance.paramObject));
        ActionEntity actionEntity = JSON.parseObject(paramObjectJson, ActionEntity.class);
        ResultBase<String> resultBase = httpServerStoreProtocol.updateAction(actionEntity);
        this.buildSuccessResponse(params, resultBase);
    }

    private void rollbackActivity(Map<String, Object> params) {
        String activityId = String.valueOf(params.get(ProtocolConstance.paramObject));
        ResultBase<String> resultBase = httpServerStoreProtocol.rollbackActivity(activityId);
        this.buildSuccessResponse(params, resultBase);
    }

    private void commitActivity(Map<String, Object> params) {
        String activityId = String.valueOf(params.get(ProtocolConstance.paramObject));
        ResultBase<String> resultBase = httpServerStoreProtocol.commitActivity(activityId);
        this.buildSuccessResponse(params, resultBase);
    }

    private void getAndCreateAction(Map<String, Object> params) {
        String paramObjectJson = String.valueOf(params.get(ProtocolConstance.paramObject));
        ActionEntity actionEntity = JSON.parseObject(paramObjectJson, ActionEntity.class);
        ResultBase<String> resultBase = httpServerStoreProtocol.getAndCreateAction(actionEntity);
        this.buildSuccessResponse(params, resultBase);
    }

    private void openTransaction(Map<String, Object> params) {
        String paramObjectJson = String.valueOf(params.get(ProtocolConstance.paramObject));
        ActivityEntity activityEntity = JSON.parseObject(paramObjectJson, ActivityEntity.class);
        ResultBase<DTSContext> resultBase = httpServerStoreProtocol.openTransaction(activityEntity);
        this.buildSuccessResponse(params, resultBase);
    }

}
