package org.github.dtsopensource.server.share.schedule;

import java.io.Serializable;

import org.github.dtsopensource.server.share.DTSConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月14日 上午11:10:45
 */
@Getter
@Setter
public class TaskTrackerContext implements Serializable {

    private static final long serialVersionUID = 1612166307850489500L;

    private DTSConfig         dtsConfig;

    @JSONField(serialize = false)
    private IDTSTaskTracker   taskTracker;

    @JSONField(serialize = false)
    private IDTSSchedule      dtsSchedule;

    private String            taskTrackerType;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
