package org.github.dtsopensource.schedule.protocol;

import org.github.dtsopensource.schedule.protocol.http.HttpScheduleProtocol;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.protocol.IDTSProtocol;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

import lombok.Getter;
import lombok.Setter;

/**
 * http 协议下的任务交互
 * 
 * @author ligaofeng 2016年12月14日 下午1:21:50
 */
@Getter
@Setter
public class HttpRemoteScheduleProtocol implements IDTSProtocol, IDTSTaskTracker {

    //dts-server服务端url
    private String               serverURL;
    //超时时间 default 10s
    private int                  timeOut     = 10 * 1000;
    //整个池子的大小 default 80
    private int                  maxTotal    = 80;
    //连接到每个地址上的大小 default 20
    private int                  maxPerRoute = 20;
    //对指定端口的socket连接上限 default 20
    private int                  maxRoute    = 20;

    private HttpScheduleProtocol storeProtocol;

    /**
     * @param serverURL
     * @param timeOut
     */
    public HttpRemoteScheduleProtocol(String serverURL, int timeOut) {
        this.serverURL = serverURL;
        this.timeOut = timeOut;
        storeProtocol = new HttpScheduleProtocol(serverURL, timeOut, maxTotal, maxPerRoute, maxRoute);
    }

    @Override
    public void getConnection() throws DTSBizException {
        storeProtocol.getConnection();
    }

    @Override
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
        this.getConnection();
        storeProtocol.executeTask(taskTrackerContext);
    }

}
