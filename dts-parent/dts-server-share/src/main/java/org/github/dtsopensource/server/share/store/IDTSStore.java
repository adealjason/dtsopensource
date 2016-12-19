package org.github.dtsopensource.server.share.store;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.IDTS;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

/**
 * @author ligaofeng 2016年12月7日 下午9:39:51
 */
public interface IDTSStore extends IDTS {

    /**
     * 开启事务
     * 
     * @param activityEntity
     * @return
     */
    public ResultBase<DTSContext> openTransaction(ActivityEntity activityEntity);

    /**
     * @param actionEntity
     * @return
     */
    public ResultBase<String> getAndCreateAction(ActionEntity actionEntity);

    /**
     * 一阶commit时调用
     * 
     * @param activityId
     * @return
     */
    public ResultBase<String> commitActivity(String activityId);

    /**
     * 一阶rollback时调用
     * 
     * @param activityId
     * @return
     */
    public ResultBase<String> rollbackActivity(String activityId);

    /**
     * 更新action
     * 
     * @param actionEntity
     * @return
     */
    public ResultBase<String> updateAction(ActionEntity actionEntity);

}
