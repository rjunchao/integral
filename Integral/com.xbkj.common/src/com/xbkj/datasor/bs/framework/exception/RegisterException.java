package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * 
 * User: �ι���
 * Date: 2005-1-20
 * Time: 17:04:20
 */
public class RegisterException extends ComponentException {

    private static final long serialVersionUID = 8028480213501195495L;

    public RegisterException() {
        super();
    }

    public RegisterException(String msg) {
        super(msg);
    }

    public RegisterException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public RegisterException(String name, String msg) {
        super(name, msg);

    }

    public RegisterException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public RegisterException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public RegisterException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);
    }

}
