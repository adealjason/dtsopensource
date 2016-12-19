package org.github.dtsopensource.local.test.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.github.dtsopensource.local.test.PurchaseContext;
import org.github.dtsopensource.local.test.application.IPurchaseService;
import org.github.dtsopensource.local.test.bizService.ITradeLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月7日 上午12:01:29
 */
@Slf4j
@Controller
@RequestMapping("/product")
public class DTSController {

    @Resource
    private IPurchaseService purchaseService;
    @Resource
    private ITradeLog        tradeLog;

    /**
     * 申购
     * 
     * @param productName
     * @param orderAmount
     * @param currentAmount
     * @param response
     */
    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public void puchase(@RequestParam String productName, @RequestParam BigDecimal orderAmount,
                        @RequestParam BigDecimal currentAmount, HttpServletResponse response) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        try {
            this.sysout(response, "购买商品:[" + productName + "],订单金额:[" + orderAmount + "],账户余额:[" + currentAmount + "]");
            PurchaseContext context = new PurchaseContext();
            context.setCurrentAmount(currentAmount);
            context.setOrderAmount(orderAmount);
            context.setProductName(productName);
            log.info(context.toString());
            String activityId = purchaseService.puchase(context);
            this.sysout(response, "业务活动ID：" + activityId);
            List<String> list = tradeLog.getNewLog(activityId);
            for (String an : list) {
                this.sysout(response, an);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sysout(HttpServletResponse response, String dtsLog) {
        try {
            response.getOutputStream().write(dtsLog.getBytes("utf-8"));
            response.getOutputStream().write("<br/>".getBytes("utf-8"));
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
