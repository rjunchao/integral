package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-2-2
 * Time: 8:48:13
 *
 */
public class FrameworkEJBException extends FrameworkRuntimeException {

    private static final long serialVersionUID = -8366261599440766724L;

    public FrameworkEJBException() {
        super();
    }

    public FrameworkEJBException(String msg) {
        super(msg);
    }

    public FrameworkEJBException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
