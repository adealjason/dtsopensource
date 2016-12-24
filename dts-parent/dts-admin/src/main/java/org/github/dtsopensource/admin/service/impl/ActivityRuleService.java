package org.github.dtsopensource.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDO;
import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDOExample;
import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDOExample.Criteria;
import org.github.dtsopensource.admin.dao.mapper.ActivityRuleDOMapper;
import org.github.dtsopensource.admin.exception.DTSAdminException;
import org.github.dtsopensource.admin.service.IActivityRuleService;
import org.github.dtsopensource.admin.service.helper.ActivityRuleHelper;
import org.github.dtsopensource.server.share.rule.entity.ActivityRuleEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月23日 下午1:35:49
 */
@Slf4j
@Service
public class ActivityRuleService implements IActivityRuleService {

    @Resource
    private ActivityRuleDOMapper activityRuleDOMapper;

    @Override
    public void saveActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException {
        try {
            ActivityRuleDO activityRuleDO = ActivityRuleHelper.toActivityRuleDO(activityRuleEntity);
            activityRuleDOMapper.insert(activityRuleDO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DTSAdminException("保存业务活动失败");
        }

    }

    @Override
    public List<ActivityRuleEntity> getActivityRule(ActivityRuleEntity activityRuleEntity) throws DTSAdminException {
        ActivityRuleDO activityRuleDO = ActivityRuleHelper.toActivityRuleDO(activityRuleEntity);
        ActivityRuleDOExample example = new ActivityRuleDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeletedIsNotNull();
        if (StringUtils.isNotEmpty(activityRuleDO.getBizType())) {
            criteria.andBizTypeLike(activityRuleDO.getBizType());
        }
        if (StringUtils.isNotEmpty(activityRuleDO.getApp())) {
            criteria.andAppLike(activityRuleDO.getApp());
        }
        if (StringUtils.isNotEmpty(activityRuleDO.getAppCname())) {
            criteria.andAppCnameLike(activityRuleDO.getAppCname());
        }
        example.setOrderByClause("app,biz_type");
        List<ActivityRuleDO> lists = activityRuleDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lists)) {
            return Lists.newArrayList();
        }
        List<ActivityRuleEntity> entities = Lists.newArrayList();
        for (ActivityRuleDO an : lists) {
            entities.add(ActivityRuleHelper.toActivityRuleEntity(an));
        }
        return entities;
    }

}
