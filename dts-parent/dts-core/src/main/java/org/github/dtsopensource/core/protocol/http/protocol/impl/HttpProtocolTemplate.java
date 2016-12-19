package org.github.dtsopensource.core.protocol.http.protocol.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * http 请求模板
 * 
 * @author ligaofeng 2016年12月12日 下午7:08:40
 */
@Slf4j
public class HttpProtocolTemplate {

    private String                    serverURL;
    //超时时间 
    private int                       timeOut;

    private final CloseableHttpClient httpClient;

    public HttpProtocolTemplate(String serverURL, int timeOut, CloseableHttpClient httpClient) {
        this.serverURL = serverURL;
        this.timeOut = timeOut;
        this.httpClient = httpClient;
    }

    public String execute(HttpProtoclCallback httpCallback) throws DTSBizException {
        Map<String, Object> params = httpCallback.buildParams();
        return this.post(params);
    }

    private void initRequestConfig(HttpRequestBase httpRequestBase) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut).setSocketTimeout(timeOut).build();
        httpRequestBase.setConfig(requestConfig);
    }

    /**
     * http get
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private String get() throws DTSBizException {
        HttpGet httpget = new HttpGet(serverURL);
        initRequestConfig(httpget);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            throw new DTSBizException(e);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private String post(Map<String, Object> params) throws DTSBizException {
        log.info("--->http protocol template params:{}", params);
        HttpPost httppost = new HttpPost(serverURL);
        initRequestConfig(httppost);
        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httppost, HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            log.info("--->http protocol template response:{}", result);
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            throw new DTSBizException(e);
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void setPostParams(HttpPost httpost, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        String json = JSON.toJSONString(params);
        nvps.add(new BasicNameValuePair(ProtocolConstance.jsonMap, json));
        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

}
