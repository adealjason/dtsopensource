package org.github.dtsopensource.core.annotation;

import org.aspectj.lang.JoinPoint;

/**
 * @author ligaofeng 2016年12月2日 下午2:53:24
 */
public interface AopHandler {

    /**
     * @param joinPoint
     * @throws Throwable
     */
    public void handleAop(JoinPoint joinPoint) throws Throwable;
}
