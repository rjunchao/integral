package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-20
 * Time: 18:48:25
 */
public class InvalidException extends ComponentException {
  
    private static final long serialVersionUID = -3861733779608994645L;

    public InvalidException() {
        super();
    }

    public InvalidException(String msg) {
        super(msg);
    }

    public InvalidException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public InvalidException(String name, String msg) {
        super(name, msg);

    }

    public InvalidException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public InvalidException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public InvalidException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);
    }

}