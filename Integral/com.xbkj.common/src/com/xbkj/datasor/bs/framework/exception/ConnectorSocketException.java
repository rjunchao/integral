package com.xbkj.datasor.bs.framework.exception;

import java.net.SocketException;

/**
 * @author �ι���
 *
 * Date: 2006-12-31
 * Time: ����11:51:31
 */
public class ConnectorSocketException extends ConnectorIOException {
    
    private static final long serialVersionUID = 6788237476042561231L;

    public ConnectorSocketException() {
        super();
    }

    public ConnectorSocketException(String msg) {
        super(msg);
    }

    public ConnectorSocketException(String msg, SocketException throwable) {
        super(msg, throwable);
    }
}
