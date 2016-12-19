package org.github.dtsopensource.server.share.schedule;

import org.github.dtsopensource.server.share.exception.DTSBizException;

/**
 * @author ligaofeng 2016年12月14日 上午11:14:33
 */
public interface IDTSTaskTracker {

    /**
     * 执行任务
     * 
     * @param taskTrackerContext
     * @throws DTSBizException
     */
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException;
}
