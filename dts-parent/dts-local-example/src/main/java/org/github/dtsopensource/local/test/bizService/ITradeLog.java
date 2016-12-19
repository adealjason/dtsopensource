package org.github.dtsopensource.local.test.bizService;

import java.util.List;

/**
 * @author ligaofeng 2016年12月9日 下午3:09:59
 */
public interface ITradeLog {

    /**
     * @param operation
     * @param tradeLog
     */
    public void saveTradeLog(String operation, String tradeLog);

    /**
     * @param activityId
     * @param operation
     * @param tradeLog
     */
    public void saveTradeLog(String activityId, String operation, String tradeLog);

    /**
     * 不重复记录
     * 
     * @param activityId
     * @param operation
     * @param tradeLog
     */
    public void saveTradeLogIfPossible(String activityId, String operation, String tradeLog);

    /**
     * 获取最新日志
     * 
     * @param activityId
     * @return
     */
    public List<String> getNewLog(String activityId);
}
