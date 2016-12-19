package org.github.dtsopensource.server.share;

import java.io.Serializable;

import lombok.Data;

/**
 * @author ligaofeng 2016年12月2日 下午1:37:08
 */
@Data
public class ResultBase<T> implements Serializable {

    private static final long serialVersionUID = 2297155012372998859L;

    private DTSResultCode     dtsResultCode;

    private String            message;

    private T                 value;

    /**
     * @return
     */
    public boolean isSucess() {
        return DTSResultCode.SUCCESS == this.getDtsResultCode();
    }

    /**
     * @param errorMessage
     */
    public void addMessage(String errorMessage) {
        if (message != null && message.trim().length() > 0) {
            message = message.concat("|").concat(errorMessage);
        } else {
            message = errorMessage;
        }
    }
}
