package org.github.dtsopensource.server.share.protocol;

/**
 * @author ligaofeng 2016年12月12日 下午1:06:24
 */
public enum ProtocolMethod {
    //store
    openTransaction,

    getAndCreateAction,

    commitActivity,

    rollbackActivity,

    updateAction,

    getActionEntities,

    //rule
    getBizTypeRule,

    checkBizType,

    //schedule
    requestSchedule,

    unkonwn;
}
