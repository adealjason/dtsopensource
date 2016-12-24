package org.github.dtsopensource.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.github.dtsopensource.admin.application.IActicityRuleApplication;
import org.github.dtsopensource.admin.vo.ActivityRuleVO;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * dts-admin
 * 
 * @author ligaofeng 2016年12月22日 下午2:02:38
 */
@Slf4j
@Controller
@RequestMapping("/dtsAdmin")
public class DTSAdmin {

    private static final String      welcome = "welcome";

    @Resource
    private IActicityRuleApplication acticityRuleApplication;

    /**
     * 欢迎界面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String welcome(Map<String, Object> model) {
        return welcome;
    }

    /**
     * 新增业务活动
     * 
     * @param activityRuleVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submitActivity", method = RequestMethod.POST)
    public Map<String, Object> submitActivity(ActivityRuleVO activityRuleVO) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", DTSResultCode.SUCCESS);
        try {
            ActivityRuleEntity activityRuleEntity = new ActivityRuleEntity();
            BeanUtils.copyProperties(activityRuleEntity, activityRuleVO);
            acticityRuleApplication.addActivityRule(activityRuleEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("result", DTSResultCode.FAIL);
            result.put("message", e.getMessage());
        }
        return result;
    }

    /**
     * @param activityRuleVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryActivity", method = RequestMethod.POST)
    public Map<String, Object> queryActivity(ActivityRuleVO activityRuleVO) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("result", DTSResultCode.SUCCESS);
        try {
            ActivityRuleEntity activityRuleEntity = new ActivityRuleEntity();
            BeanUtils.copyProperties(activityRuleEntity, activityRuleVO);
            List<ActivityRuleEntity> list = acticityRuleApplication.queryActivityRule(activityRuleEntity);
            result.put("list", list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("result", DTSResultCode.FAIL);
            result.put("message", e.getMessage());
        }
        return result;
    }

}
