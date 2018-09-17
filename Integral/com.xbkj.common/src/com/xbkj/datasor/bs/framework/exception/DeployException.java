package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-20
 * Time: 11:00:59
 */
public class DeployException extends FrameworkRuntimeException {

    private static final long serialVersionUID = -5181022979159291803L;

    String moduleName;

    public DeployException() {
        super();
    }

    public DeployException(String msg) {
        super(msg);
    }

    public DeployException(String moduleName, String msg) {
        super(msg);
        this.moduleName = moduleName;
    }

    public DeployException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public DeployException(String moduleName, String msg, Throwable throwable) {
        super(msg, throwable);
        this.moduleName = moduleName;
    }

    public String getMessage() {
        return moduleName == null ? super.getMessage() : "Module: " + moduleName + super.getMessage();
    }

    public String getModuleName() {
        return moduleName;
    }

}