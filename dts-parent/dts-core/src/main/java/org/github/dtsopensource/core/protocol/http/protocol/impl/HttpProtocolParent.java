package org.github.dtsopensource.core.protocol.http.protocol.impl;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.github.dtsopensource.core.protocol.http.protocol.IHttpProtocol;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.ProtocolConstance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

/**
 * 构建http连接池，初始化http相关参数，不涉及业务操作
 * 
 * @author ligaofeng 2016年12月14日 下午1:50:12
 */
public class HttpProtocolParent implements IHttpProtocol {

    //dts-server服务端url
    private String                 serverURL;
    //超时时间 
    private int                    timeOut;
    //整个池子的大小 
    private int                    maxTotal;
    //连接到每个地址上的大小 
    private int                    maxPerRoute;
    //对指定端口的socket连接上限 
    private int                    maxRoute;

    protected HttpProtocolTemplate httpProtocolTemplate;

    private final Object           lock = new Object();

    /**
     * init HttpProtocol
     * 
     * @param serverURL
     * @param timeOut
     * @param maxTotal
     * @param maxPerRoute
     * @param maxRoute
     */
    public HttpProtocolParent(String serverURL, int timeOut, int maxTotal, int maxPerRoute, int maxRoute) {
        this.serverURL = serverURL;
        this.timeOut = timeOut;
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.maxRoute = maxRoute;
    }

    @Override
    public void getConnection() throws DTSBizException {
        if (httpProtocolTemplate == null) {
            synchronized (lock) {
                if (httpProtocolTemplate == null) {
                    CloseableHttpClient httpClient = this.getHttpClient();
                    httpProtocolTemplate = new HttpProtocolTemplate(serverURL, timeOut, httpClient);
                }
            }
        }
    }

    private CloseableHttpClient getHttpClient() {
        String hostname = serverURL.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        return createHttpClient(hostname, port);
    }

    private CloseableHttpClient createHttpClient(String hostname, int port) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
        cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);
        // 请求重试处理
        return HttpClients.custom().setConnectionManager(cm).setRetryHandler(httpRequestRetryHandler).build();
    }

    //请求重试配置
    private static final HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof InterruptedIOException) {// 超时
                return false;
            }
            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                return false;
            }
            if (exception instanceof UnknownHostException) {// 目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {// SSL握手异常
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };

    protected String getReturnObject(String response) throws DTSBizException {
        Map<String, Object> paramObject = this.getMapByJson(response);
        if (MapUtils.isEmpty(paramObject)) {
            throw new DTSBizException("http请求未得到返回信息");
        }
        return String.valueOf(paramObject.get(ProtocolConstance.resultObject));
    }

    private Map<String, Object> getMapByJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return Maps.newHashMap();
        }
        Map<String, Object> paramObject = new HashMap<String, Object>();
        // 最外层解析  
        JSONObject object = JSON.parseObject(json);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            paramObject.put(k.toString(), v);
        }
        return paramObject;
    }

}
