package org.github.dtsopensource.server.share.protocol;

import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

/**
 * @author ligaofeng 2016年12月4日 上午11:44:22
 */
public enum Protocol {

    DUBBO("dubbo"),
    HSF("hsf"),
    HTTP("http");

    private String name;

    Protocol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * get protocol
     * 
     * @param name
     * @return
     */
    public static Protocol getProtocol(String name) {
        for (Protocol s : Protocol.values()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        throw new DTSRuntimeException("No support protocol !" + " action=" + name);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
