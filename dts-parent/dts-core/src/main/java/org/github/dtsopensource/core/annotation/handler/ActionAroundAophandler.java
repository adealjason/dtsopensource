package org.github.dtsopensource.core.annotation.handler;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.github.dtsopensource.core.annotation.AopHandler;
import org.github.dtsopensource.core.manager.DTSManager;
import org.github.dtsopensource.core.util.ActivityIdGenerator;
import org.github.dtsopensource.server.share.DTSContext;
import org.github.dtsopensource.server.share.ResultBase;
import org.github.dtsopensource.server.share.rule.entity.ActivityActionRuleEntity;
import org.github.dtsopensource.server.share.store.Status;
import org.github.dtsopensource.server.share.store.entity.ActionEntity;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月4日 下午1:36:10
 */
@Slf4j
@Component
public class ActionAroundAophandler implements AopHandler {

    @Resource
    private DTSManager dtsManager;

    @Override
    public void handleAop(JoinPoint joinPoint) throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
        DTSContext context = (DTSContext) proceedingJoinPoint.getArgs()[0];
        String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        String twoPurchaseAction = method.getName();

        ActivityActionRuleEntity bizActionRule = context.getActivityRuleEntity().getActivityActionRuleEntity(clazzName);

        String service = bizActionRule.getService();

        ActionEntity actionEntity = new ActionEntity(ActivityIdGenerator.generateActionId(twoPurchaseAction), context,
                service, clazzName, twoPurchaseAction);
        try {
            ResultBase<String> resultBase = dtsManager.getAndCreateAction(actionEntity);
            actionEntity.setActionId(resultBase.getValue());

            proceedingJoinPoint.proceed();

            this.actionSuccess(actionEntity);
        } catch (Throwable t) {
            log.error("activityId.actionId:{},errorMessage:{}",
                    actionEntity.getActivityId().concat(".").concat(actionEntity.getActionId()), t.getMessage(), t);
            this.actionFail(actionEntity, t.getMessage());
            throw t;
        }
    }

    private void actionSuccess(ActionEntity actionEntity) {
        actionEntity.setStatus(Status.T);
        actionEntity.getContext().removeArgsIfPossible("actionResult");
        dtsManager.updateAction(actionEntity);
    }

    private void actionFail(ActionEntity actionEntity, String errorMessage) {
        actionEntity.setStatus(Status.F);
        actionEntity.getContext().addArgs("actionResult", errorMessage);
        dtsManager.updateAction(actionEntity);
    }

}
