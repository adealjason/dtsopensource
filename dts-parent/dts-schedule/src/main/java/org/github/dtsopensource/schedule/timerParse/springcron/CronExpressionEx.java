package org.github.dtsopensource.schedule.timerParse.springcron;

import java.text.ParseException;
import java.util.Set;
import java.util.StringTokenizer;

import org.quartz.CronExpression;

/**
 * @author ligaofeng 2016年12月14日 下午3:23:05
 */
public class CronExpressionEx extends CronExpression {

    private static final long serialVersionUID = -4457096856332091267L;

    /**
     * 秒
     */
    private String            secondsExp;

    /**
     * 分
     */
    private String            minutesExp;

    /**
     * 小时
     */
    private String            hoursExp;

    /**
     * 天
     */
    private String            daysOfMonthExp;

    /**
     * 月
     */
    private String            monthsExp;

    /**
     * 周
     */
    private String            daysOfWeekExp;

    /**
     * @param cronExpression
     * @throws ParseException
     */
    public CronExpressionEx(String cronExpression) throws ParseException {
        super(cronExpression);

        StringTokenizer exprsTok = new StringTokenizer(cronExpression, " \t", false);
        secondsExp = exprsTok.nextToken().trim();
        minutesExp = exprsTok.nextToken().trim();
        hoursExp = exprsTok.nextToken().trim();
        daysOfMonthExp = exprsTok.nextToken().trim();
        monthsExp = exprsTok.nextToken().trim();
        daysOfWeekExp = exprsTok.nextToken().trim();
    }

    /**
     * @return
     */
    public Set getSecondsSet() {
        return seconds;
    }

    /**
     * @return
     */
    public String getSecondsField() {
        return getExpressionSetSummary(seconds);
    }

    /**
     * @return
     */
    public Set getMinutesSet() {
        return minutes;
    }

    /**
     * @return
     */
    public String getMinutesField() {
        return getExpressionSetSummary(minutes);
    }

    /**
     * @return
     */
    public Set getHoursSet() {
        return hours;
    }

    /**
     * @return
     */
    public String getHoursField() {
        return getExpressionSetSummary(hours);
    }

    /**
     * @return
     */
    public Set getDaysOfMonthSet() {
        return daysOfMonth;
    }

    /**
     * @return
     */
    public String getDaysOfMonthField() {
        return getExpressionSetSummary(daysOfMonth);
    }

    /**
     * @return
     */
    public Set getMonthsSet() {
        return months;
    }

    /**
     * @return
     */
    public String getMonthsField() {
        return getExpressionSetSummary(months);
    }

    /**
     * @return
     */
    public Set getDaysOfWeekSet() {
        return daysOfWeek;
    }

    /**
     * @return
     */
    public String getDaysOfWeekField() {
        return getExpressionSetSummary(daysOfWeek);
    }

    /**
     * @return
     */
    public String getSecondsExp() {
        return secondsExp;
    }

    /**
     * @return
     */
    public String getMinutesExp() {
        return minutesExp;
    }

    /**
     * @return
     */
    public String getHoursExp() {
        return hoursExp;
    }

    /**
     * @return
     */
    public String getDaysOfMonthExp() {
        return daysOfMonthExp;
    }

    /**
     * @return
     */
    public String getMonthsExp() {
        return monthsExp;
    }

    /**
     * @return
     */
    public String getDaysOfWeekExp() {
        return daysOfWeekExp;
    }

}
