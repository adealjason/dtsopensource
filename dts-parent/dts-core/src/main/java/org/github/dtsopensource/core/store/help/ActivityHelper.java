package org.github.dtsopensource.core.store.help;

import java.util.Date;

import org.github.dtsopensource.core.dao.dataobject.DtsActivityDO;
import org.github.dtsopensource.server.share.store.entity.ActivityEntity;

import com.alibaba.fastjson.JSON;

/**
 * @author ligaofeng 2016年12月1日 下午9:09:08
 */
public class ActivityHelper {

    private ActivityHelper() {
    }

    /**
     * @param activityDO
     * @return
     */
    public static ActivityEntity toActivityEntity(DtsActivityDO activityDO) {
        if (activityDO == null) {
            return null;
        }
        return new ActivityEntity.Builder(activityDO.getActivityId()).setBizType(activityDO.getBizType())
                .setContext(activityDO.getContext()).setStatus(activityDO.getStatus()).build();
    }

    /**
     * @param activityEntity
     * @return
     * @throws SequenceException
     */
    public static DtsActivityDO toDtsActivityDO(ActivityEntity activityEntity) {
        if (activityEntity == null) {
            return new DtsActivityDO();
        }
        Date now = new Date();
        DtsActivityDO activityDO = new DtsActivityDO();
        activityDO.setActivityId(activityEntity.getActivityId());
        activityDO.setApp(activityEntity.getDtsConfig().getApp());
        activityDO.setBizType(activityEntity.getBizType());
        activityDO.setContext(JSON.toJSONString(activityEntity.getContext()));
        activityDO.setStatus(activityEntity.status());
        activityDO.setIsDeleted("N");
        activityDO.setGmtCreated(now);
        activityDO.setGmtModified(now);
        activityDO.setCreator("system");
        activityDO.setModifier("system");
        return activityDO;
    }
}
