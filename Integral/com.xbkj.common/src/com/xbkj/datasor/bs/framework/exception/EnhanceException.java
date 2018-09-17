package com.xbkj.datasor.bs.framework.exception;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-12
 * Time: ����03:51:04
 *
 */
public class EnhanceException extends ComponentException {

    private static final long serialVersionUID = 2486084550997232367L;

    public EnhanceException() {
        super();

    }

    public EnhanceException(String containerName, String name, String msg, Throwable throwable) {
        super(containerName, name, msg, throwable);

    }

    public EnhanceException(String containerName, String name, String msg) {
        super(containerName, name, msg);

    }

    public EnhanceException(String name, String msg, Throwable throwable) {
        super(name, msg, throwable);

    }

    public EnhanceException(String name, String msg) {
        super(name, msg);

    }

    public EnhanceException(String msg, Throwable throwable) {
        super(msg, throwable);

    }

    public EnhanceException(String msg) {
        super(msg);

    }

}
