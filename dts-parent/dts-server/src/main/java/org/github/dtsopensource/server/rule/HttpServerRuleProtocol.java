package org.github.dtsopensource.server.rule;

import javax.annotation.Resource;

import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.rule.IDTSRule;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.stereotype.Service;

/**
 * @author ligaofeng 2016年12月16日 下午3:49:47
 */
@Service
public class HttpServerRuleProtocol implements IDTSRule {

    @Resource
    private IDTSRule localDTSRule;

    @Override
    public ResultBase<ActivityRuleEntity> checkBizType(String bizType) {
        return localDTSRule.checkBizType(bizType);
    }

    @Override
    public ResultBase<ActivityRuleEntity> getBizTypeRule(String bizType) {
        return localDTSRule.getBizTypeRule(bizType);
    }

}
