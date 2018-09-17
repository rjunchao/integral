package com.xbkj.datasor.bs.framework.exception;


/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-17
 * Time: 10:54:07
 */
public class ComponentNotFoundException extends ComponentException {
    
    private static final long serialVersionUID = -2036876653664356233L;

    public ComponentNotFoundException() {
        super();
    }

    public ComponentNotFoundException(String msg) {
        super(msg);
    }

    public ComponentNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ComponentNotFoundException(String name, String msg) {
        super(name, msg);

    }

    public ComponentNotFoundException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public ComponentNotFoundException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public ComponentNotFoundException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);
    }

}
