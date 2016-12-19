package org.github.dtsopensource.schedule.register.zookeeper;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linco lee
 */
@Getter
@Setter
@Slf4j
public class LeaderElector {

    private String    zkConnectionString;
    private String    ephemeralZnodeNamePrefix;
    private String    ephemeralZnodeParent;
    private String    path;
    private int       zkSessionTimeout;
    private String    nodeTag;
    private ZooKeeper zkClient;
    private boolean   registered = false;

    /**
     * @param zkConnectionString
     * @param zkSessionTimeout
     * @param ephemeralZnodeParent
     * @param ephemeralZnodeNamePrefix
     */
    public LeaderElector(String zkConnectionString, int zkSessionTimeout, String ephemeralZnodeParent,
                         String ephemeralZnodeNamePrefix) {
        this.zkConnectionString = zkConnectionString;
        this.zkSessionTimeout = zkSessionTimeout;
        this.ephemeralZnodeParent = ephemeralZnodeParent;
        this.ephemeralZnodeNamePrefix = ephemeralZnodeNamePrefix;
        this.nodeTag = makeNodeTag();
        this.path = ephemeralZnodeParent + "/" + ephemeralZnodeNamePrefix;
    }

    /**
     * 注册
     */
    private synchronized void register() {
        if (registered) {
            return;
        }
        try {
            this.zkClient = new ZooKeeper(zkConnectionString, zkSessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                }
            });

            if (zkClient.exists(ephemeralZnodeParent, true) == null) {
                // create persistent parent node
                zkClient.create(ephemeralZnodeParent, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //一旦创建了子节点，path的值已经改变，后面会自动加上序列号。
            path = zkClient.create(path, null,
                    Collections.singletonList(new ACL(ZooDefs.Perms.ALL, ZooDefs.Ids.ANYONE_ID_UNSAFE)),
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            registered = true;
        } catch (IOException e) {
            log.error("Exception occurred when try to connect to zookeeper " + zkConnectionString, e);
        } catch (Exception e) {
            log.error("Exception occurred when try to create EPHEMERAL_SEQUENTIAL znode.", e);
        }
    }

    /**
     * 获取当前机子的IP地址拼接成字符串
     * 
     * @return 当前机子IP地址
     */
    private String makeNodeTag() {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<?> nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        // skip ipv6
                        continue;
                    }
                    sb.append(ia.getHostAddress() + ",");
                }
            }
            String ips = sb.toString();
            return ips.substring(0, ips.length() - 1);
        } catch (SocketException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public synchronized boolean isLeader() {
        if (!registered) {
            register();
        }
        try {
            List<String> children = zkClient.getChildren(ephemeralZnodeParent, false);
            if (CollectionUtils.isEmpty(children)) {
                log.error("Get NO children of " + ephemeralZnodeParent);
                return false;
            }
            Collections.sort(children);
            String smallestChild = ephemeralZnodeParent + "/" + children.get(0);
            return path.equals(smallestChild);
        } catch (KeeperException e) {
            registered = false;
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            registered = false;
            log.error("Exception occurred when try to get children of " + ephemeralZnodeParent, e);
        }
        return false;

    }

    /**
     * 销毁
     */
    public void destroy() {
        try {
            zkClient.close();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

}
