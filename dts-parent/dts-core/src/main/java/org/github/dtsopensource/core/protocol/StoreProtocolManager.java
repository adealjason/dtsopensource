package org.github.dtsopensource.core.protocol;

import java.util.concurrent.atomic.AtomicBoolean;

import org.github.dtsopensource.server.share.exception.DTSBizException;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.protocol.IDTSProtocol;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ligaofeng 2016年12月11日 下午12:22:47
 */
@Slf4j
public abstract class StoreProtocolManager implements InitializingBean, IDTSProtocol, IDTSStore {

    private final AtomicBoolean isInit = new AtomicBoolean(false);

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            check();
        } catch (Exception e) {
            log.error("check error in dts's dataSource, make sure the ip of web server is in the white list");
            throw new DTSRuntimeException(e);
        }
        this.init();
    }

    private void init() throws DTSBizException {
        if (isInit.compareAndSet(false, true)) {
            this.initDTSProtocol();
            this.getConnection();
        }
    }

    protected abstract void check() throws DTSBizException;

    protected abstract void initDTSProtocol();

}
