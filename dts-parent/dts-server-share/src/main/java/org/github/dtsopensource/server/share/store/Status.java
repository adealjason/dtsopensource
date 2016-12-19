package org.github.dtsopensource.server.share.store;

import org.github.dtsopensource.server.share.exception.DTSRuntimeException;

/**
 * @author ligaofeng 2016年12月4日 上午11:43:05
 */
public enum Status {

    S("start"),
    T("success"),
    F("fail"),
    R("rollback"),
    E("exception");

    private String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * get status
     * 
     * @param name
     * @return
     */
    public static Status getStatus(String name) {
        for (Status s : Status.values()) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        throw new DTSRuntimeException("Invalid status !" + " name=" + name);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
