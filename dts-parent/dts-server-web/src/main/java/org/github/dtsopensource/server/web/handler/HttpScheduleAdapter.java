package org.github.dtsopensource.server.web.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.github.dtsopensource.server.schedule.http.IHttpServerSchedule;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.share.protocol.ProtocolMethod;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月18日 上午1:52:14
 */
@Slf4j
@Component
public class HttpScheduleAdapter extends HttpAdapterManager {

    @Resource
    private IHttpServerSchedule httpServerScheduleProtocol;

    @Override
    protected void handle(ProtocolMethod protocolmethod, Map<String, Object> params) {
        switch (protocolmethod) {
            case requestSchedule:
                this.requestSchedule(params);
                break;
            default:
                this.unsupprotMethod(params);
                break;
        }
    }

    private void requestSchedule(Map<String, Object> params) {
        String paramObjectJson = String.valueOf(params.get(ProtocolConstance.paramObject));
        TaskTrackerContext taskTrackerContext = JSON.parseObject(paramObjectJson, TaskTrackerContext.class);
        try {
            httpServerScheduleProtocol.executeTask(taskTrackerContext);
            this.buildSuccessResponse(params, "dts-server处理成功");
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            this.buildSuccessResponse(params, "dts-server处理出现异常:" + e.getMessage());
        }
    }

}
