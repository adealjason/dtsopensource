package org.github.dtsopensource.schedule.protocol.http;

import java.util.Map;

import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtoclCallback;
import org.github.dtsopensource.core.protocol.http.protocol.impl.HttpProtocolParent;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月14日 下午1:46:27
 */
@Slf4j
public class HttpScheduleProtocol extends HttpProtocolParent implements IDTSTaskTracker {

    /**
     * @param serverURL
     * @param timeOut
     * @param maxTotal
     * @param maxPerRoute
     * @param maxRoute
     */
    public HttpScheduleProtocol(String serverURL, int timeOut, int maxTotal, int maxPerRoute, int maxRoute) {
        super(serverURL, timeOut, maxTotal, maxPerRoute, maxRoute);
    }

    @Override
    public void executeTask(final TaskTrackerContext taskTrackerContext) throws DTSBizException {
        try {
            String response = httpProtocolTemplate.execute(new HttpProtoclCallback() {

                @Override
                public Map<String, Object> buildParams() {
                    Map<String, Object> params = Maps.newHashMap();
                    params.put(ProtocolConstance.paramObject, taskTrackerContext);
                    params.put(ProtocolConstance.requestStoreOperation, ProtocolMethod.requestSchedule);
                    return params;
                }
            });
            log.info("--->response:{}", response);
        } catch (Exception e) {
            throw new DTSBizException(e);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
