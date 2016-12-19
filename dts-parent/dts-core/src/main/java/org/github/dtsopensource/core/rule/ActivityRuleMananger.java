package org.github.dtsopensource.core.rule;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.core.protocol.http.HttpRemoteRuleProtocol;
import org.github.dtsopensource.server.share.DTSResultCode;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.rule.IDTSRule;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;

/**
 * @author ligaofeng 2016年12月17日 下午1:47:11
 */
public class ActivityRuleMananger implements IDTSRule {

    private IDTSRule httpRemoteRuleProtocol;

    /**
     * @param requestActivityRuleURL
     */
    public ActivityRuleMananger(String requestActivityRuleURL) {
        httpRemoteRuleProtocol = new HttpRemoteRuleProtocol(requestActivityRuleURL);
    }

    @Override
    public ResultBase<ActivityRuleEntity> checkBizType(String bizType) {
        ResultBase<ActivityRuleEntity> checkResult = new ResultBase<ActivityRuleEntity>();
        if (StringUtils.isEmpty(bizType)) {
            checkResult.setDtsResultCode(DTSResultCode.FAIL);
            checkResult.setMessage("业务活动不能为空");
            return checkResult;
        }
        return httpRemoteRuleProtocol.checkBizType(bizType);
    }

    @Override
    public ResultBase<ActivityRuleEntity> getBizTypeRule(String bizType) {
        ResultBase<ActivityRuleEntity> checkResult = new ResultBase<ActivityRuleEntity>();
        if (StringUtils.isEmpty(bizType)) {
            checkResult.setDtsResultCode(DTSResultCode.FAIL);
            checkResult.setMessage("业务活动不能为空");
            return checkResult;
        }
        return httpRemoteRuleProtocol.getBizTypeRule(bizType);
    }

}
