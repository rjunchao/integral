package com.xbkj.datasor.bs.framework.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;


/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 */
public class InitException extends FrameworkRuntimeException {

    private static final long serialVersionUID = 5687533809159213934L;

    private Properties serverProperties;

    public InitException() {
        super();
    }

    public InitException(Properties props, String msg) {
        super(msg);
        this.serverProperties = props;

    }

    public InitException(String msg) {
        super(msg);
    }

    public InitException(Properties props, String msg, Throwable throwable) {
        super(msg, throwable);
        this.serverProperties = props;
    }

    public InitException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public String getMessage() {

        StringWriter sw = new StringWriter();
        if (serverProperties != null) {
            serverProperties.list(new PrintWriter(sw));
        }

        return super.getMessage() + ", server environment: " + sw.toString();

    }

}
