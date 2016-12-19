package org.github.dtsopensource.server.web.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.github.dtsopensource.server.rule.HttpServerRuleProtocol;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.stereotype.Component;

/**
 * @author ligaofeng 2016年12月16日 下午4:27:58
 */
@Component
public class HttpRuleAdapter extends HttpAdapterManager {

    @Resource
    private HttpServerRuleProtocol httpServerRuleProtocol;

    @Override
    protected void handle(ProtocolMethod protocolmethod, Map<String, Object> params) {
        switch (protocolmethod) {
            case getBizTypeRule:
                this.getBizTypeRule(params);
                break;
            case checkBizType:
                this.checkBizType(params);
                break;
            default:
                this.unsupprotMethod(params);
                break;
        }
    }

    private void getBizTypeRule(Map<String, Object> params) {
        String bizType = String.valueOf(params.get(ProtocolConstance.paramObject));
        ResultBase<ActivityRuleEntity> resultBase = httpServerRuleProtocol.getBizTypeRule(bizType);
        this.buildSuccessResponse(params, resultBase);
    }

    private void checkBizType(Map<String, Object> params) {
        String bizType = String.valueOf(params.get(ProtocolConstance.paramObject));
        ResultBase<ActivityRuleEntity> resultBase = httpServerRuleProtocol.checkBizType(bizType);
        this.buildSuccessResponse(params, resultBase);
    }

}
