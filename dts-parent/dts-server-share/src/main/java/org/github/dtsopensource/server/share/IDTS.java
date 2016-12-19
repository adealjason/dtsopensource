package org.github.dtsopensource.server.share;

import java.util.List;

import org.github.dtsopensource.server.share.store.entity.ActionEntity;

/**
 * 所有DTS抽象的公共父接口<br>
 * 子接口有：IDTSStore(负责二阶交易)、IDTSSchedule(负责二阶事务回滚等任务)
 * 
 * @author ligaofeng 2016年12月16日 上午11:03:08
 */
public interface IDTS {

    /**
     * 获取指定业务活动的action
     * 
     * @param activityId
     * @return
     */
    public ResultBase<List<ActionEntity>> getActionEntities(String activityId);
}
