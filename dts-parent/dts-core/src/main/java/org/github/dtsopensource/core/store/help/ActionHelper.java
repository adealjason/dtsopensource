package org.github.dtsopensource.core.store.help;

import java.util.Date;

import org.github.dtsopensource.core.dao.dataobject.DtsActionDO;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;

import com.alibaba.fastjson.JSON;

/**
 * @author ligaofeng 2016年12月2日 下午5:21:22
 */
public class ActionHelper {

    private ActionHelper() {
    }

    /**
     * @param actionDO
     * @return
     */
    public static ActionEntity toActionEntity(DtsActionDO actionDO) {
        if (actionDO == null) {
            return null;
        }
        return new ActionEntity.Builder(actionDO.getActionId()).setAction(actionDO.getAction())
                .setActivityId(actionDO.getActivityId()).setContext(actionDO.getContext())
                .setProtocol(actionDO.getProtocol()).setService(actionDO.getService())
                .setClazzName(actionDO.getClazzName()).setStatus(actionDO.getStatus()).setVersion(actionDO.getVersion())
                .build();
    }

    /**
     * @param actionEntity
     * @return
     * @throws SequenceException
     */
    public static DtsActionDO toDtsActionDO(ActionEntity actionEntity) {
        if (actionEntity == null) {
            return new DtsActionDO();
        }
        Date now = new Date();
        DtsActionDO actionDO = new DtsActionDO();
        actionDO.setAction(actionEntity.getAction());
        actionDO.setActivityId(actionEntity.getActivityId());
        actionDO.setActionId(actionEntity.getActionId());
        actionDO.setContext(JSON.toJSONString(actionEntity.getContext()));
        actionDO.setProtocol(actionEntity.protocol());
        actionDO.setService(actionEntity.getService());
        actionDO.setClazzName(actionEntity.getClazzName());
        actionDO.setStatus(actionEntity.status());
        actionDO.setVersion(actionEntity.getVersion());
        actionDO.setIsDeleted("N");
        actionDO.setGmtCreated(now);
        actionDO.setGmtModified(now);
        actionDO.setCreator("system");
        actionDO.setModifier("system");
        return actionDO;
    }

}
