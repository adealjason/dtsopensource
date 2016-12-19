package org.github.dtsopensource.server.share;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年11月29日 上午11:30:46
 */
@Getter
@Setter
public class DTSConfig implements Serializable {

    private static final long serialVersionUID = -2408123274907783162L;

    private DTSManageType     dtsManageType;

    private String            app;

    //dts业务规则请求地址
    private String            requestActivityRuleURL;

    @PostConstruct
    public void checkConfig() {
        if (dtsManageType == null) {
            throw new DTSRuntimeException("dts连接方式dtsManageType未定义");
        }
        if (app == null || app.trim().length() == 0) {
            throw new DTSRuntimeException("业务系统名称app未定义");
        }
        if (requestActivityRuleURL == null || requestActivityRuleURL.trim().length() == 0) {
            throw new DTSRuntimeException("dts业务规则请求地址requestActivityRuleURL尚未配置");
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
