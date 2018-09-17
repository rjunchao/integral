package com.xbkj.log.vo.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-12-13
 * Time: 16:49:09
 */
public class CallerInfo {

    public final static String LINE_SEP = System.getProperty("line.separator");
    public final static int LINE_SEP_LEN = LINE_SEP.length();

    transient String lineNumber;

    transient String fileName;

    transient String className;

    transient String methodName;

    public String fullInfo;

    private static StringWriter sw = new StringWriter();
    private static PrintWriter pw = new PrintWriter(sw);

    public final static String NA = "?";


    // Check if we are running in IBM's visual age.
    static boolean inVisualAge = false;

    static {
        try {
            Class dummy = Class.forName("com.ibm.uvm.tools.DebugSupport");
            inVisualAge = true;

        } catch (Throwable e) {
            // nothing to do
        }
    }

    public CallerInfo(Throwable t, String fqn) {
        if (t == null)
            return;

        String s;
        // Protect against multiple access to sw.
        synchronized (sw) {
            t.printStackTrace(pw);
            s = sw.toString();
            sw.getBuffer().setLength(0);
        }
        //System.out.println("s is ["+s+"].");
        int ibegin, iend;


        ibegin = s.lastIndexOf(fqn + ".");
        if (ibegin == -1)
            return;


        ibegin = s.indexOf(LINE_SEP, ibegin);
        if (ibegin == -1)
            return;
        ibegin += LINE_SEP_LEN;

        // determine end of line
        iend = s.indexOf(LINE_SEP, ibegin);
        if (iend == -1)
            return;


        if (!inVisualAge) {
            ibegin = s.lastIndexOf("at ", iend);
            if (ibegin == -1)
                return;
            ibegin += 3;
        }
        this.fullInfo = s.substring(ibegin, iend);
    }


    public String getClassName() {
        if (fullInfo == null) return NA;
        if (className == null) {

            int iend = fullInfo.lastIndexOf('(');
            if (iend == -1)
                className = NA;
            else {
                iend = fullInfo.lastIndexOf('.', iend);
                int ibegin = 0;
                if (inVisualAge) {
                    ibegin = fullInfo.lastIndexOf(' ', iend) + 1;
                }

                if (iend == -1)
                    className = NA;
                else
                    className = this.fullInfo.substring(ibegin, iend);
            }
        }
        return className;
    }


    public String getFileName() {
        if (fullInfo == null) return NA;

        if (fileName == null) {
            int iend = fullInfo.lastIndexOf(':');
            if (iend == -1)
                fileName = NA;
            else {
                int ibegin = fullInfo.lastIndexOf('(', iend - 1);
                fileName = this.fullInfo.substring(ibegin + 1, iend);
            }
        }
        return fileName;
    }


    public String getLineNumber() {
        if (fullInfo == null) return NA;

        if (lineNumber == null) {
            int iend = fullInfo.lastIndexOf(')');
            int ibegin = fullInfo.lastIndexOf(':', iend - 1);
            if (ibegin == -1)
                lineNumber = NA;
            else
                lineNumber = this.fullInfo.substring(ibegin + 1, iend);
        }
        return lineNumber;
    }


    public String getMethodName() {
        if (fullInfo == null) return NA;
        if (methodName == null) {
            int iend = fullInfo.lastIndexOf('(');
            int ibegin = fullInfo.lastIndexOf('.', iend);
            if (ibegin == -1)
                methodName = NA;
            else
                methodName = this.fullInfo.substring(ibegin + 1, iend);
        }
        return methodName;
    }
}
