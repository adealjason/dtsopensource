package org.github.dtsopensource.server.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;
import org.github.dtsopensource.server.web.handler.HttpRuleAdapter;
import org.github.dtsopensource.server.web.handler.HttpScheduleAdapter;
import org.github.dtsopensource.server.web.handler.HttpStoreAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月12日 上午11:57:13
 */
@Slf4j
@Controller
@RequestMapping("/dtsServer")
public class DTSServer {

    @Resource
    private HttpStoreAdapter    httpProtocolAdapter;
    @Resource
    private HttpRuleAdapter     httpRuleAdapter;
    @Resource
    private HttpScheduleAdapter httpScheduleAdapter;

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestStore", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> requestStore(HttpServletRequest request) {
        Map<String, Object> paramObject = Maps.newHashMap();
        try {
            String json = request.getParameter(ProtocolConstance.jsonMap);
            paramObject = this.getMapByJson(json);

            log.info("--->dts-server requestStore request:{}", paramObject);
            httpProtocolAdapter.adapter(paramObject);
            log.info("--->dts-server requestStore response:{}", paramObject);
            return paramObject;
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            return this.buildFailResponse(paramObject, e.getMessage());
        }
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestRule", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> requestRule(HttpServletRequest request) {
        Map<String, Object> paramObject = Maps.newHashMap();
        try {
            String json = request.getParameter(ProtocolConstance.jsonMap);
            paramObject = this.getMapByJson(json);

            log.info("--->dts-server requestRule request:{}", paramObject);
            httpRuleAdapter.adapter(paramObject);
            log.info("--->dts-server requestRule response:{}", paramObject);
            return paramObject;
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            return this.buildFailResponse(paramObject, e.getMessage());
        }
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestSchedule", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> requestSchedule(HttpServletRequest request) {

        Map<String, Object> paramObject = Maps.newHashMap();
        try {
            String json = request.getParameter(ProtocolConstance.jsonMap);
            paramObject = this.getMapByJson(json);

            log.info("--->dts-server requestSchedule request:{}", paramObject);
            httpScheduleAdapter.adapter(paramObject);
            log.info("--->dts-server requestSchedule response:{}", paramObject);
            return paramObject;
        } catch (DTSBizException e) {
            log.error(e.getMessage(), e);
            return this.buildFailResponse(paramObject, e.getMessage());
        }

    }

    private Map<String, Object> getMapByJson(String json) {
        Map<String, Object> paramObject = new HashMap<String, Object>();
        // 最外层解析  
        JSONObject object = JSON.parseObject(json);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            paramObject.put(k.toString(), v);
        }
        return paramObject;
    }

    private Map<String, Object> buildFailResponse(Map<String, Object> paramObject, String message) {
        ResultBase<String> resultBase = new ResultBase<String>();
        resultBase.setDtsResultCode(DTSResultCode.FAIL);
        resultBase.setMessage(message);
        paramObject.put(ProtocolConstance.resultObject, resultBase);
        return paramObject;
    }

}
