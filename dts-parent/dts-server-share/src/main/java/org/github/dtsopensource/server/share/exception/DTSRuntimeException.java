package org.github.dtsopensource.server.share.exception;

/**
 * @author ligaofeng 2016年11月3日 下午2:46:37
 */
public class DTSRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4328158740883926572L;

    /**
     * init DTSRuntimeException
     */
    public DTSRuntimeException() {
        super();
    }

    /**
     * init DTSRuntimeException
     * 
     * @param message
     */
    public DTSRuntimeException(String message) {
        super(message);
    }

    /**
     * init DTSRuntimeException
     * 
     * @param message
     * @param cause
     */
    public DTSRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * init DTSRuntimeException
     * 
     * @param cause
     */
    public DTSRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * init DTSRuntimeException
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DTSRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
