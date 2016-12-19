package org.github.dtsopensource.schedule.jobTracker;

import javax.sql.DataSource;

import org.github.dtsopensource.schedule.LocalDTSSchedule;
import org.github.dtsopensource.schedule.taskTracker.LocalTaskTrackerDelegate;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.schedule.IDTSSchedule;
import org.github.dtsopensource.server.share.schedule.IDTSTaskTracker;
import org.github.dtsopensource.server.share.schedule.TaskTrackerContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月14日 上午11:35:48
 */
public class LocalJobTrackerDelegate extends DTSJobTrackerDelegate {

    @Getter
    @Setter
    private IDTSTaskTracker taskTracker;
    @Getter
    @Setter
    private DataSource      dataSource;

    private IDTSSchedule    localDTSSchedule;

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
        if (taskTracker == null) {
            throw new DTSRuntimeException("尚未设置要执行的taskTracker任务");
        }
        if (dataSource == null) {
            throw new DTSRuntimeException("尚未设置数据源dataSource");
        }
    }

    @Override
    protected IDTSTaskTracker initTaskTrackerDelegate() {
        return new LocalTaskTrackerDelegate(taskTracker);
    }

    @Override
    protected TaskTrackerContext initTaskTrackerContext() {
        this.initLocalDTSSchedule();
        TaskTrackerContext taskTrackerContext = new TaskTrackerContext();
        taskTrackerContext.setDtsConfig(dtsConfig);
        taskTrackerContext.setTaskTracker(taskTracker);
        taskTrackerContext.setDtsSchedule(localDTSSchedule);
        return taskTrackerContext;
    }

    private void initLocalDTSSchedule() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        localDTSSchedule = new LocalDTSSchedule(jdbcTemplate, transactionTemplate);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
