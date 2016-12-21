package org.github.dtsopensource.remote.test.bizService.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.github.dtsopensource.remote.test.FileCreator;
import org.github.dtsopensource.remote.test.bizService.ITradeLog;
import org.github.dtsopensource.server.share.DTSContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月9日 下午3:12:00
 */
@Slf4j
@Service("tradeLog")
public class TradeLog implements ITradeLog {

    private static final String dtslogRootPath = "/alidata1/admin/dtslog";

    @Override
    public void saveTradeLog(String operation, String tradeLog) {
        DTSContext dtsContext = DTSContext.getInstance();
        String activityId = dtsContext.getActivityId();
        this.saveTradeLog(activityId, operation, tradeLog);
    }

    @Override
    public void saveTradeLog(String activityId, String operation, String tradeLog) {
        String acitivitylogPath = dtslogRootPath + File.separator + activityId;
        log.info("--->新增一笔:{} 日志:{}", operation, tradeLog);
        FileCreator.createFile(tradeLog, acitivitylogPath);
    }

    @Override
    public List<String> getNewLog(String activityId) {
        String acitivitylogPath = dtslogRootPath + File.separator + activityId;
        return FileCreator.parseTxtFile(acitivitylogPath);
    }

    @Override
    public void saveTradeLogIfPossible(String activityId, String operation, String tradeLog) {
        List<String> logs = this.getNewLog(activityId);
        if (CollectionUtils.isNotEmpty(logs) && logs.contains(tradeLog)) {
            return;
        }
        this.saveTradeLog(activityId, operation, tradeLog);

    }

}
