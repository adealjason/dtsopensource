package org.github.dtsopensource.admin.exception;

/**
 * @author ligaofeng 2016年12月23日 下午1:34:26
 */
public class DTSAdminException extends Exception {

    private static final long serialVersionUID = 674322939576507543L;

    /**
     * init BizException
     */
    public DTSAdminException() {
        super();
    }

    /**
     * init BizException
     * 
     * @param message
     */
    public DTSAdminException(String message) {
        super(message);
    }

    /**
     * init BizException
     * 
     * @param message
     * @param cause
     */
    public DTSAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * init BizException
     * 
     * @param cause
     */
    public DTSAdminException(Throwable cause) {
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
    public DTSAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
