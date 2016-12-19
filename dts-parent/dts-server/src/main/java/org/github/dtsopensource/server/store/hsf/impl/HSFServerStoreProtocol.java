package org.github.dtsopensource.server.store.hsf.impl;

import java.util.List;

import javax.annotation.Resource;

import org.github.dtsopensource.core.manager.DTSManager;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.github.dtsopensource.server.store.hsf.IHSFServerStore;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * dts_server端接收dts_core的hsf remote request
 * 
 * @author ligaofeng 2016年12月5日 下午1:01:52
 */
@Slf4j
@Service("hsfServerStoreProtocol")
public class HSFServerStoreProtocol implements IHSFServerStore {

    @Resource
    private DTSManager localDTSManager;

    @Override
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity) {
        log.info("--->openTransaction receive request,activityEntity:{}", activityEntity);
        ResultBase<DTSContext> resultBase = localDTSManager.openTransaction(activityEntity);
        log.info("--->openTransaction response,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        log.info("--->getAndCreateAction receive request,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = localDTSManager.getAndCreateAction(actionEntity);
        log.info("--->getAndCreateAction response,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> commitActivity(String activityId) {
        log.info("--->commitActivity receive request,activityId:{}", activityId);
        ResultBase<String> resultBase = localDTSManager.commitActivity(activityId);
        log.info("--->commitActivity response,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        log.info("--->rollbackActivity receive request,activityId:{}", activityId);
        ResultBase<String> resultBase = localDTSManager.rollbackActivity(activityId);
        log.info("--->rollbackActivity response,resultBase:{}", resultBase);
        return resultBase;
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        log.info("--->updateAction receive request,actionEntity:{}", actionEntity);
        ResultBase<String> resultBase = localDTSManager.updateAction(actionEntity);
        log.info("--->updateAction execute success,actionEntity:{}", actionEntity);
        return resultBase;
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId) {
        log.info("--->getActionEntities receive request,activityId:{}", activityId);
        ResultBase<List<ActionEntity>> resultBase = localDTSManager.getActionEntities(activityId);
        log.info("--->getActionEntities response,resultBase:{}", resultBase);
        return resultBase;
    }

}
