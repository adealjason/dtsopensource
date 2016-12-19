package org.github.dtsopensource.schedule.taskTracker;

import org.github.dtsopensource.schedule.protocol.HttpRemoteScheduleProtocol;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

/**
 * @author ligaofeng 2016年12月14日 下午1:27:52
 */
public class RemoteTaskTrackerDelegate implements IDTSTaskTracker {

    private IDTSTaskTracker httpRemoteScheduleProtocol;

    /**
     * @param requestScheduleURL
     * @param timeOut
     */
    public RemoteTaskTrackerDelegate(String requestScheduleURL, int timeOut) {
        httpRemoteScheduleProtocol = new HttpRemoteScheduleProtocol(requestScheduleURL, timeOut);
    }

    @Override
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
        httpRemoteScheduleProtocol.executeTask(taskTrackerContext);
    }

}
