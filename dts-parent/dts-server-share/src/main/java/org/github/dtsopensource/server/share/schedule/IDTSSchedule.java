package org.github.dtsopensource.server.share.schedule;

import java.util.List;

import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.IDTS;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;
import org.github.dtsopensource.server.share.store.Status;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

/**
 * @author ligaofeng 2016年12月16日 上午11:04:53
 */
public interface IDTSSchedule extends IDTS {

    /**
     * 获取hang住的二阶事务<br>
     * 默认返回5分钟之前的数据
     * 
     * @param app 系统名称
     * @return
     */
    public ResultBase<List<ActivityEntity>> getHangTransaction(String app);

    /**
     * 关闭业务活动，并改变状态
     * 
     * @param activityId
     * @param orignStatus
     * @param targetStatus
     */
    public void closeActivity(String activityId, Status orignStatus, Status targetStatus);

    /**
     * commit
     * 
     * @param context
     * @param bizActionRule
     */
    public void commit(DTSContext context, ActivityActionRuleEntity bizActionRule) throws DTSBizException;

    /**
     * rollback
     * 
     * @param context
     * @param bizActionRule
     */
    public void rollback(DTSContext context, ActivityActionRuleEntity bizActionRule) throws DTSBizException;

}
