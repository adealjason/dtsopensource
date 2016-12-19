package org.github.dtsopensource.core.protocol.http;

import java.util.Map;

import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtoclCallback;
import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtocolParent;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.IDTSProtocol;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.rule.IDTSRule;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月16日 下午3:21:45
 */
@Slf4j
public class HttpRemoteRuleProtocol extends HttpProtocolParent implements IDTSProtocol, IDTSRule {

    /**
     * @param requestActivityRuleURL
     */
    public HttpRemoteRuleProtocol(String requestActivityRuleURL) {
        this(requestActivityRuleURL, 10 * 1000, 2, 2, 2);
    }

    /**
     * @param serverURL
     * @param timeOut
     * @param maxTotal
     * @param maxPerRoute
     * @param maxRoute
     */
    public HttpRemoteRuleProtocol(String serverURL, int timeOut, int maxTotal, int maxPerRoute, int maxRoute) {
        super(serverURL, timeOut, maxTotal, maxPerRoute, maxRoute);
    }

    @Override
    public ResultBase<ActivityRuleEntity> checkBizType(final String bizType) {
        ResultBase<ActivityRuleEntity> resultBase = new ResultBase<ActivityRuleEntity>();
        try {
            this.getConnection();
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {

                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, bizType);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.checkBizType);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<ActivityRuleEntity>>() {
            });
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

    @Override
    public ResultBase<ActivityRuleEntity> getBizTypeRule(final String bizType) {
        ResultBase<ActivityRuleEntity> resultBase = new ResultBase<ActivityRuleEntity>();
        try {
            this.getConnection();
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {

                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, bizType);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.getBizTypeRule);
                    return params;
                }
            });
            String resultObjectJson = this.getReturnObject(response);
            return JSON.parseObject(resultObjectJson, new TypeReference<ResultBase<ActivityRuleEntity>>() {
            });
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
    }

}
