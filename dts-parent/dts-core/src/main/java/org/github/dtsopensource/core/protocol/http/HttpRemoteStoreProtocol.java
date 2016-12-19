package org.github.dtsopensource.core.protocol.http;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.core.protocol.StoreProtocolManager;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月4日 下午10:57:38
 */
@Slf4j
@Getter
@Setter
public class HttpRemoteStoreProtocol extends StoreProtocolManager {

    //dts-server服务端url
    private String       serverURL;
    //超时时间 default 10s
    private int          timeOut     = 10 * 1000;
    //整个池子的大小 default 80
    private int          maxTotal    = 80;
    //连接到每个地址上的大小 default 20
    private int          maxPerRoute = 20;
    //对指定端口的socket连接上限 default 20
    private int          maxRoute    = 20;

    private HttpProtocol storeProtocol;

    @Override
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity) {
        log.info("--->dts-core start request http remote store openTransaction,activityEntity:{}", activityEntity);
        ResultBase<DTSContext> resultBase = new ResultBase<DTSContext>();
        try {
            resultBase = storeProtocol.openTransaction(activityEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }

        log.info("--->dts-core receive response from http remote store openTransaction,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        log.info("--->dts-core start request http remote store getAndCreateAction,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = storeProtocol.getAndCreateAction(actionEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from hsf remote store getAndCreateAction,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> commitActivity(String activityId) {
        log.info("--->dts-core start request http remote store commitActivity,activityId:{}", activityId);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = storeProtocol.commitActivity(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from http remote store commitActivity,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        log.info("--->dts-core start request http remote store rollbackActivity,activityId:{}", activityId);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = storeProtocol.rollbackActivity(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from http remote store rollbackActivity,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        log.info("--->dts-core start request http remote store updateAction,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = storeProtocol.updateAction(actionEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core execute updateAction sucess,actionEntity:{}", actionEntity);
        return resultBase;
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId) {
        log.info("--->dts-core start request http remote store getActionEntities,activityId:{}", activityId);
        ResultBase<List<ActionEntity>> resultBase = new ResultBase<List<ActionEntity>>();
        try {
            resultBase = storeProtocol.getActionEntities(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from http remote store getActionEntities,activityId:{},resultBase:{}",
                activityId, resultBase);
        return resultBase;
    }

    @Override
    public void getConnection() throws DTSBizException {
        storeProtocol.getConnection();
    }

    @Override
    protected void check() throws DTSBizException {
        if (StringUtils.isEmpty(serverURL)) {
            throw new DTSBizException("http协议下尚未配置dts-server url");
        }
    }

    @Override
    protected void initDTSProtocol() {
        HttpProtocol httpProtocol = new HttpProtocol(serverURL, timeOut, maxTotal, maxPerRoute, maxRoute);
        this.storeProtocol = httpProtocol;
    }

}
