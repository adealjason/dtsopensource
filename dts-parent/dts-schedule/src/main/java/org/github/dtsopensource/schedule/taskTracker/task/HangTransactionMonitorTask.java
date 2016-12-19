package org.github.dtsopensource.schedule.taskTracker.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.schedule.IDTSSchedule;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;

import lombok.extern.slf4j.Slf4j;

/**
 * dts二阶事务恢复监控<br>
 * 执行超过一定次数自动将该业务活动置为异常(status:E)业务活动<br>
 * taskTrackerType:hangTransactionMonitor
 * 
 * @author ligaofeng 2016年12月18日 下午1:47:55
 */
@Slf4j
public class HangTransactionMonitorTask implements IDTSTaskTracker {

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void executeTask(TaskTrackerContext taskTrackerContext) throws DTSBizException {
        log.info("--->start to execute hang transaction monitor task:{}", taskTrackerContext);
        IDTSSchedule dtsSchedule = taskTrackerContext.getDtsSchedule();
        DTSConfig dtsConfig = taskTrackerContext.getDtsConfig();
    }

}
