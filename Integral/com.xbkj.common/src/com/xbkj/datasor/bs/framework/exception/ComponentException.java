package com.xbkj.datasor.bs.framework.exception;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-20
 * Time: 11:53:42
 * 
 * The root of the compnent exception hierarchy
 */
public class ComponentException extends FrameworkRuntimeException {

    private static final long serialVersionUID = 5844549315401699424L;

    String componentName;

    String containerName;

    public ComponentException() {
        super();
    }

    public ComponentException(String msg) {
        super(msg);
    }

    public ComponentException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ComponentException(String name, String msg) {
        super(msg);
        this.componentName = name;
    }

    public ComponentException(String name, String msg, Throwable throwable) {
        super(msg, throwable);
        this.componentName = name;
    }

    public ComponentException(String containerName, String name, String msg) {
        super(msg);
        this.componentName = name;
        this.containerName = containerName;

    }

    public ComponentException(String containerName, String name, String msg, Throwable throwable) {
        super(msg, throwable);
        this.containerName = containerName;
        this.componentName = name;
    }

    public String getMessage() {
        StringBuffer sb = new StringBuffer();

        if (getContainerName() != null)
            sb.append("Container: ").append(getContainerName()).append(',');
        if (getComponentName() != null) {
            sb.append("Component: ").append(getComponentName()).append(',');
        }
        if (super.getMessage() != null) {
            sb.append("Detail Message: ").append(super.getMessage());
        }
        return sb.toString();
    }

    public String getComponentName() {
        return componentName;
    }

    public String getContainerName() {
        return containerName;

    }
}
