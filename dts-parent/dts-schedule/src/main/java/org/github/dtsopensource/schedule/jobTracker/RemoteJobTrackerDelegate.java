package org.github.dtsopensource.schedule.jobTracker;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.schedule.taskTracker.RemoteTaskTrackerDelegate;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月14日 下午1:05:34
 */
public class RemoteJobTrackerDelegate extends DTSJobTrackerDelegate {

    @Getter
    @Setter
    private String taskTrackerType;
    @Getter
    @Setter
    private String requestScheduleURL;
    @Getter
    @Setter
    private int    timeOut = 10 * 1000;

    @Override
    protected void check() {
        if (jobRegister == null) {
            throw new DTSRuntimeException("尚未设置任务注册器jobRegister");
        }
        if (timerParse == null) {
            throw new DTSRuntimeException("尚未设置计时器timerParse");
        }
        if (dtsConfig == null) {
            throw new DTSRuntimeException("尚未设置系统配置dtsConfig");
        }
        if (taskTrackerType == null) {
            throw new DTSRuntimeException("尚未设置要执行的任务类型taskTrackerType");
        }
        if (StringUtils.isEmpty(requestScheduleURL)) {
            throw new DTSRuntimeException("REMOTE模式下请设置要请求的dts-server地址requestScheduleURL");
        }
    }

    @Override
    protected IDTSTaskTracker initTaskTrackerDelegate() {
        return new RemoteTaskTrackerDelegate(requestScheduleURL, timeOut);
    }

    @Override
    protected TaskTrackerContext initTaskTrackerContext() {
        TaskTrackerContext taskTrackerContext = new TaskTrackerContext();
        taskTrackerContext.setDtsConfig(dtsConfig);
        taskTrackerContext.setTaskTrackerType(taskTrackerType);
        return taskTrackerContext;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
