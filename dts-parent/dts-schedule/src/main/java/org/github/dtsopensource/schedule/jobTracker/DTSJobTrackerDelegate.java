package org.github.dtsopensource.schedule.jobTracker;

import java.util.concurrent.atomic.AtomicBoolean;

import org.github.dtsopensource.schedule.IDTSJobTracker;
import org.github.dtsopensource.schedule.IJobNode;
import org.github.dtsopensource.schedule.IJobRegister;
import org.github.dtsopensource.schedule.IJobTimerParse;
import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月14日 上午11:19:54
 */
@Slf4j
public abstract class DTSJobTrackerDelegate implements InitializingBean, DisposableBean, IJobNode, IDTSJobTracker {

    @Getter
    @Setter
    protected IJobRegister      jobRegister;
    @Getter
    @Setter
    protected IJobTimerParse    timerParse;
    @Getter
    @Setter
    protected DTSConfig         dtsConfig;

    private IDTSTaskTracker     taskTrackerDelegate;

    private TaskTrackerContext  taskTrackerContext;

    private JobTrackerAction    jobTrackerAction;

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    @Override
    public boolean startNode() {
        return jobRegister.register();
    }

    @Override
    public void stopNode() {
        jobRegister.stop();
    }

    @Override
    public void destroyNode() {
        jobRegister.destroy();
        jobTrackerAction.destroy();
    }

    @Override
    public void submitTask() {
        try {
            taskTrackerDelegate.executeTask(taskTrackerContext);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() throws Exception {
        this.destroyNode();
    }

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

    private void init() {
        if (isInit.compareAndSet(false, true)) {
            if (jobRegister.register()) {
                taskTrackerDelegate = this.initTaskTrackerDelegate();
                taskTrackerContext = this.initTaskTrackerContext();
                jobTrackerAction = new JobTrackerAction(timerParse);
                jobTrackerAction.start();
                log.info("--->任务启动成功,jobRegister:{},timerParse:{},taskTrackerDelegate:{},taskTrackerContext:{}",
                        jobRegister, timerParse, taskTrackerDelegate, taskTrackerContext);
            }
        }
    }

    protected abstract void check();

    protected abstract IDTSTaskTracker initTaskTrackerDelegate();

    protected abstract TaskTrackerContext initTaskTrackerContext();

    class JobTrackerAction extends Thread {

        private final IJobTimerParse timerParse;

        public JobTrackerAction(IJobTimerParse timerParse) {
            this.timerParse = timerParse;
        }

        @Override
        public void run() {
            while (true) {
                if (timerParse.canRun()) {
                    submitTask();
                }
                try {
                    Thread.sleep(1 * 1000l);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

}
