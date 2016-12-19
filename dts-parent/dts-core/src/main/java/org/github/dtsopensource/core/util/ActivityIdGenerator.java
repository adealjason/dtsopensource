package org.github.dtsopensource.core.util;

import java.util.UUID;

import org.github.dtsopensource.core.DTSCoreSpringHolder;
import org.github.dtsopensource.server.share.DTSConfig;
import org.github.dtsopensource.server.share.util.DTSCoreSystem;

/**
 * @author ligaofeng 2016年11月30日 下午4:55:14
 */
public class ActivityIdGenerator {

    private ActivityIdGenerator() {
    }

    /**
     * 生成活动id
     * 
     * @param dtsConfig
     * @return
     */
    public static String generateActivityId() {
        return DTSCoreSpringHolder.getBean(DTSConfig.class).getApp().concat(DTSCoreSystem.UNDER_WRITE)
                .concat(getUUUId());
    }

    /**
     * @param action
     * @return
     */
    public static String generateActionId(String action) {
        return DTSCoreSpringHolder.getBean(DTSConfig.class).getApp().concat(DTSCoreSystem.UNDER_WRITE).concat(action)
                .concat(DTSCoreSystem.UNDER_WRITE).concat(getUUUId());
    }

    public static String getUUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
