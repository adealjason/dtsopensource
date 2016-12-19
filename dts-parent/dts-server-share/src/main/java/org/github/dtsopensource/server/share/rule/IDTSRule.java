package org.github.dtsopensource.server.share.rule;

import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

/**
 * 业务规则类<br>
 * 处理业务bizType的配置管理<br>
 * 辅助IDTS完成交易及事务恢复的接口
 * 
 * @author ligaofeng 2016年12月16日 下午2:55:44
 */
public interface IDTSRule {

    /**
     * 校验业务规则
     * 
     * @param bizType
     * @return
     */
    public ResultBase<ActivityRuleEntity> checkBizType(String bizType);

    /**
     * 获取该业务规则的配置信息<br>
     * 返回业务action的配置
     * 
     * @param bizType
     * @return
     */
    public ResultBase<ActivityRuleEntity> getBizTypeRule(String bizType);

}
