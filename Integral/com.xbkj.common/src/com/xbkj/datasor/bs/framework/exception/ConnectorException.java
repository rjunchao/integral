package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 * 
 * The runtime exception for framework connector
 */
public class ConnectorException extends FrameworkRuntimeException {
   
    private static final long serialVersionUID = -6049910873194670737L;

    public ConnectorException() {
        super();
    }
    
    public ConnectorException(String msg) {
        super(msg);
    }
    
    public ConnectorException(String msg, Throwable exp) {
        super(msg, exp);
    }

}
