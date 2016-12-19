package org.github.dtsopensource.core.protocol.hsf;

import java.util.List;

import org.github.dtsopensource.core.protocol.StoreProtocolManager;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月4日 下午10:56:07
 */
@Slf4j
public class HSFRemoteStoreProtocol extends StoreProtocolManager {

    @Getter
    @Setter
    private IDTSStore hsfServerStoreProtocol;

    @Override
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity) {
        log.info("--->dts-core start request hsf remote store openTransaction,activityEntity:{}", activityEntity);
        ResultBase<DTSContext> resultBase = new ResultBase<DTSContext>();
        try {
            resultBase = hsfServerStoreProtocol.openTransaction(activityEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }

        log.info("--->dts-core receive response from hsf remote store openTransaction,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        log.info("--->dts-core start request hsf remote store getAndCreateAction,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = hsfServerStoreProtocol.getAndCreateAction(actionEntity);
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
        log.info("--->dts-core start request hsf remote store commitActivity,activityId:{}", activityId);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = hsfServerStoreProtocol.commitActivity(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from hsf remote store commitActivity,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        log.info("--->dts-core start request hsf remote store rollbackActivity,activityId:{}", activityId);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = hsfServerStoreProtocol.rollbackActivity(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from hsf remote store rollbackActivity,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        log.info("--->dts-core start request hsf remote store updateAction,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = new ResultBase<String>();
        try {
            resultBase = hsfServerStoreProtocol.updateAction(actionEntity);
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
        log.info("--->dts-core start request hsf remote store getActionEntities,activityId:{}", activityId);
        ResultBase<List<ActionEntity>> resultBase = new ResultBase<List<ActionEntity>>();
        try {
            resultBase = hsfServerStoreProtocol.getActionEntities(activityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultBase.setDtsResultCode(DTSResultCode.FAIL);
            resultBase.setMessage(e.getMessage());
            return resultBase;
        }
        log.info("--->dts-core receive response from hsf remote store getActionEntities,activityId:{},resultBase:{}",
                activityId, resultBase);
        return resultBase;
    }

    @Override
    public void getConnection() throws DTSBizException {
        //do nothing hsf协议下的连接由hsf框架自动处理
    }

    @Override
    protected void check() throws DTSBizException {
        if (hsfServerStoreProtocol == null) {
            throw new DTSBizException("hsf协议下需配置hsfServerStoreProtocol");
        }
    }

    @Override
    protected void initDTSProtocol() {
        //do nothing hsf协议下的初始化由spring配置完成
    }

}
