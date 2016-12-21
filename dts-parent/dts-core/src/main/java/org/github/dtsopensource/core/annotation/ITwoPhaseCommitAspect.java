package org.github.dtsopensource.core.annotation;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ligaofeng 2016年11月4日 下午2:26:56
 */
@Component
@Aspect
public class ITwoPhaseCommitAspect {

    @Resource
    private AopHandler actionAroundAophandler;

    @Pointcut(value = "execution(* org.github.dtsopensource.core.ITwoPhaseCommit.pre(..))")
    public void prePointcut() {
    }

    @Pointcut(value = "execution(* org.github.dtsopensource.core.ITwoPhaseCommit.commit(..))")
    public void commitPointcut() {
    }

    @Pointcut(value = "execution(* org.github.dtsopensource.core.ITwoPhaseCommit.rollback(..))")
    public void rollbackPointcut() {
    }

    /**
     * @param joinPoint
     * @throws Throwable
     */
    @Around("prePointcut()")
    public void aroundPre(JoinPoint joinPoint) throws Throwable {
        actionAroundAophandler.handleAop(joinPoint);
    }

    /**
     * @param joinPoint
     * @throws Throwable
     */
    @Around("commitPointcut()")
    public void aroundCommit(JoinPoint joinPoint) throws Throwable {
        actionAroundAophandler.handleAop(joinPoint);
    }

    /**
     * @param joinPoint
     * @throws Throwable
     */
    @Around("rollbackPointcut()")
    public void aroundRollBack(JoinPoint joinPoint) throws Throwable {
        actionAroundAophandler.handleAop(joinPoint);
    }

}
