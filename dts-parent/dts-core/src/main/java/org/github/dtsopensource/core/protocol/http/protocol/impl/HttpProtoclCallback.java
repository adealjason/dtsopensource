package org.github.dtsopensource.core.protocol.http.protocol.impl;

import java.util.Map;

/**
 * @author ligaofeng 2016年12月12日 下午7:07:00
 */
public abstract class HttpProtoclCallback {

    /**
     * 设置请求参数
     * 
     * @return
     */
    public abstract Map<String, Object> buildParams();

}
