package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-20
 * Time: 17:07:05
 */
public class DuplicateException extends ComponentException {
  
    private static final long serialVersionUID = 9179501733480372356L;

    public DuplicateException() {
        super();
    }

    public DuplicateException(String msg) {
        super(msg);
    }

    public DuplicateException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public DuplicateException(String name, String msg) {
        super(name, msg);

    }

    public DuplicateException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public DuplicateException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public DuplicateException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);
    }

}