package org.github.dtsopensource.core.store.impl;

import java.util.List;

import org.github.dtsopensource.core.protocol.StoreProtocolManager;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import lombok.Getter;

/**
 * @author ligaofeng 2016年12月3日 下午4:33:16
 */
public class RemoteDTSStore implements IDTSStore {

    @Getter
    private final StoreProtocolManager remoteProtocol;

    /**
     * @param remoteProtocol
     */
    public RemoteDTSStore(StoreProtocolManager remoteProtocol) {
        this.remoteProtocol = remoteProtocol;
    }

    @Override
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity) {
        return remoteProtocol.openTransaction(activityEntity);
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        return remoteProtocol.getAndCreateAction(actionEntity);
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        return remoteProtocol.updateAction(actionEntity);
    }

    @Override
    public ResultBase<String> commitActivity(String activityId) {
        return remoteProtocol.commitActivity(activityId);
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        return remoteProtocol.rollbackActivity(activityId);
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId) {
        return remoteProtocol.getActionEntities(activityId);
    }

}
