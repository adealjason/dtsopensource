package org.github.dtsopensource.core.manager;

import org.github.dtsopensource.core.protocol.StoreProtocolManager;
import org.github.dtsopensource.core.store.impl.RemoteDTSStore;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.store.IDTSStore;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月3日 下午4:28:49
 */
@Slf4j
@Setter
public class RemoteDTSManager extends DTSManager {

    private StoreProtocolManager remoteProtocol;

    @Override
    protected void check() {
        if (remoteProtocol == null) {
            throw new DTSRuntimeException("remote模式下必须指定IStoreProtocol协议");
        }
        log.info("--->RemoteDTSManager check ok");
    }

    @Override
    protected IDTSStore initStore() {
        return new RemoteDTSStore(remoteProtocol);
    }

}
