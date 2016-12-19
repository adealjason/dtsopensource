package org.github.dtsopensource.core.manager;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月10日 下午2:17:55
 */
@Slf4j
public abstract class DTSManager implements InitializingBean, IDTSStore {

    protected IDTSStore         store;

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            check();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DTSRuntimeException(e);
        }
        init();
    }

    @Override
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity) {
        return store.openTransaction(activityEntity);
    }

    @Override
    public ResultBase<String> commitActivity(String activityId) {
        return store.commitActivity(activityId);
    }

    @Override
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId) {
        return store.getActionEntities(activityId);
    }

    @Override
    public ResultBase<String> rollbackActivity(String activityId) {
        return store.rollbackActivity(activityId);
    }

    @Override
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity) {
        return store.getAndCreateAction(actionEntity);
    }

    @Override
    public ResultBase<String> updateAction(ActionEntity actionEntity) {
        return store.updateAction(actionEntity);
    }

    private void init() {
        if (isInit.compareAndSet(false, true)) {
            store = initStore();
        }
    }

    protected abstract void check();

    protected abstract IDTSStore initStore();

}
