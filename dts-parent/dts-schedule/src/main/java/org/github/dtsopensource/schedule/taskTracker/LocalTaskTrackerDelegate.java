package org.github.dtsopensource.schedule.taskTracker;

import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

/**
 * @author ligaofeng 2016年12月14日 上午11:28:57
 */
public class LocalTaskTrackerDelegate implements IDTSTaskTracker {

    private final IDTSTaskTracker taskTracker;

    /**
     * @param taskTracker
     * @param taskTrackerContext
     */
    public LocalTaskTrackerDelegate(IDTSTaskTracker taskTracker) {
        this.taskTracker = taskTracker;
    }

    @Override
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
        taskTracker.executeTask(taskTrackerContext);
    }

}
