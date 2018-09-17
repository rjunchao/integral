package com.xbkj.datasor.bs.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.xbkj.datasor.bs.framework.util.StackTraceUtil;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-17
 * Time: 10:34:56
 *
 * The base exception of the framework
 */
public class FrameworkException extends Exception {

    private static final long serialVersionUID = -7817444337191584676L;

    private boolean notAdjusted = true;

    public FrameworkException() {
        super();
    }

    public FrameworkException(String msg) {
        super(msg);
    }

    public FrameworkException(String msg, Throwable throwable) {
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
