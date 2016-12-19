package org.github.dtsopensource.schedule.timerParse.springcron;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.schedule.IJobTimerParse;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月14日 下午2:59:12
 */
@Slf4j
public class SpringCronParse implements IJobTimerParse {

    private Object           lock            = new Object();

    private Date             lastExecuteDate = null;

    private CronExpressionEx exp;

    @Getter
    @Setter
    private String           cron;

    @PostConstruct
    public void initCronExpression() {
        if (StringUtils.isEmpty(cron)) {
            throw new DTSRuntimeException("spring cron表达式尚未配置");
        }
        if (exp == null) {
            try {
                exp = new CronExpressionEx(cron);
            } catch (Exception e) {
                throw new DTSRuntimeException("spring cron表达式格式不正确cron:" + cron);
            }
        }
    }

    @Override
    public boolean canRun() {

        synchronized (lock) {
            if (lastExecuteDate == null) {
                lastExecuteDate = new Date();
            }
            Date nextDate = exp.getNextValidTimeAfter(lastExecuteDate);
            boolean canRun = !nextDate.after(new Date());
            if (canRun) {
                lastExecuteDate = nextDate;
                return true;
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
