package org.github.dtsopensource.server.share.exception;

/**
 * Created by Eason on 2016/10/20.
 */
public class DTSBizException extends Exception {

    private static final long serialVersionUID = 3473398255173602638L;

    /**
     * init BizException
     */
    public DTSBizException() {
        super();
    }

    /**
     * init BizException
     * 
     * @param message
     */
    public DTSBizException(String message) {
        super(message);
    }

    /**
     * init BizException
     * 
     * @param message
     * @param cause
     */
    public DTSBizException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * init BizException
     * 
     * @param cause
     */
    public DTSBizException(Throwable cause) {
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
    public DTSBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
