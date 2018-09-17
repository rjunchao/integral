package com.xbkj.datasor.bs.framework.exception;

import java.io.IOException;


/**
 * User: �ι���
 * Date: 2005-12-20
 * Time: 10:02:04
 */
public class ConnectorIOException extends ConnectorException {
   
    private static final long serialVersionUID = 460583024109217841L;

    public ConnectorIOException() {
        super();
    }

    public ConnectorIOException(String msg) {
        super(msg);
    }

    public ConnectorIOException(String msg, IOException throwable) {
        super(msg, throwable);
    }

}
