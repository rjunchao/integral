package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-20
 * Time: 13:56:35
 */
public class InstException extends ComponentException {

    private static final long serialVersionUID = 5232229378078218145L;

    public InstException() {
        super();
    }

    public InstException(String msg) {
        super(msg);
    }

    public InstException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public InstException(String name, String msg) {
        super(name, msg);

    }

    public InstException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public InstException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public InstException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);
    }

}
