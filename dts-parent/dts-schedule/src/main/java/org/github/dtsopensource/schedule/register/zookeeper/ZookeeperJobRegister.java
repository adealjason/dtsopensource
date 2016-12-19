package org.github.dtsopensource.schedule.register.zookeeper;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.github.dtsopensource.schedule.IJobRegister;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ligaofeng 2016年12月14日 下午3:51:14
 */
public class ZookeeperJobRegister implements IJobRegister {

    @Getter
    @Setter
    private String        zkConnectionString;
    @Getter
    @Setter
    private int           zkSessionTimeout = 60000;
    @Getter
    @Setter
    private String        ephemeralZnodeParent;
    @Getter
    @Setter
    private String        ephemeralZnodeNamePrefix;

    private LeaderElector leaderElector;

    /**
     * 初始化
     */
    @PostConstruct
    public void initLeaderElector() {
        if (StringUtils.isEmpty(zkConnectionString)) {
            throw new DTSRuntimeException("zkConnectionString尚未配置");
        }
        if (StringUtils.isEmpty(ephemeralZnodeParent)) {
            throw new DTSRuntimeException("ephemeralZnodeParent尚未配置");
        }
        if (StringUtils.isEmpty(ephemeralZnodeNamePrefix)) {
            throw new DTSRuntimeException("ephemeralZnodeNamePrefix尚未配置");
        }
        leaderElector = new LeaderElector(zkConnectionString, zkSessionTimeout, ephemeralZnodeParent,
                ephemeralZnodeNamePrefix);
    }

    @Override
    public boolean register() {
        return leaderElector.isLeader();
    }

    @Override
    public void stop() {
        //do nothing
    }

    @Override
    public void destroy() {
        leaderElector.destroy();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
