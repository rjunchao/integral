package com.xbkj.datasor.bs.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.xbkj.datasor.bs.framework.util.StackTraceUtil;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-17
 * Time: 10:35:45
 *
 * The base runtime exception of the framework
 */
public class FrameworkRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2373921480522454843L;

    private boolean notAdjusted = true;

    public FrameworkRuntimeException() {
        super();
    }

    public FrameworkRuntimeException(String msg) {
        super(msg);
    }

    public FrameworkRuntimeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public StackTraceElement[] getStackTrace() {
        StackTraceElement stes[] = super.getStackTrace();

        if (notAdjusted) {
            synchronized (this) {
                if (notAdjusted) {
                    stes = StackTraceUtil.translateStackTrace(stes);
                    super.setStackTrace(stes);
                    notAdjusted = false;
                }
            }
        }
        return stes;
    }

    public void printStackTrace(PrintWriter s) {
        StackTraceUtil.printStackTrace(s, this);

    }

    public void printStackTrace(PrintStream s) {
        StackTraceUtil.printStackTrace(s, this);
    }
}
