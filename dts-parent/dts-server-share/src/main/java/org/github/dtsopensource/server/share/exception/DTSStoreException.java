package org.github.dtsopensource.server.share.exception;

/**
 * Created by Eason on 2016/10/20.
 */
public class DTSStoreException extends Exception {

    private static final long serialVersionUID = 3473398255173602638L;

    /**
     * init BizException
     */
    public DTSStoreException() {
        super();
    }

    /**
     * init BizException
     * 
     * @param message
     */
    public DTSStoreException(String message) {
        super(message);
    }

    /**
     * init BizException
     * 
     * @param message
     * @param cause
     */
    public DTSStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * init BizException
     * 
     * @param cause
     */
    public DTSStoreException(Throwable cause) {
        super(cause);
    }

    /**
     * init BizException
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DTSStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
