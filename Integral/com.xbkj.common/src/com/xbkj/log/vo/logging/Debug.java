package com.xbkj.log.vo.logging;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-12-2
 * Time: 10:12:19
 * <p/>
 * 
 * ������ͨ�ĳ������Ŀ�ģ�������־
 */
public class Debug {

    /**
     * �Ƿ����е���
     */
    protected static boolean debugEnabled = true;

    /**
     * �Ƿ����д���
     */
    protected static boolean errorEnabled = true;

    /**
     * ��ʾǰ׺
     */
    private static final String PREFIX = "����: ";

    /**
     * ����ǰ׺
     */
    private static final String ERR_PREFIX = "����: ";

    /**
     * �����Ƿ����
     * @param enabled
     */
    public static void setDebuggable(boolean enabled) {
        debugEnabled = enabled;
        errorEnabled = enabled;
    }

    /**
     * �����Ƿ��������
     * @param enabled
     */
    public static void setErrorable(boolean enabled) {
        debugEnabled = false;
        errorEnabled = enabled;
    }

    /**
     * �����е���ʾ��Ϣ
     * @param msg
     */
    public static void debug(Object msg) {
        if (debugEnabled) {
            System.out.println(PREFIX + msg);
        }
    }

    /**
     * �����е���ʾ��Ϣ
     * @param msg
     * @param t
     */
    public static void debug(Object msg, Throwable t) {
        if (debugEnabled) {
            System.out.println(PREFIX + msg);
            if (t != null)
                System.err.println(getRenderedMsg(msg, t));
        }
    }

    /**
     * ��������ʾ������Ϣ
     * @param msg
     */
    public static void error(Object msg) {
        if (errorEnabled) {
            System.err.println(ERR_PREFIX + msg);
        }
    }

    /**
     * ��������ʾ������Ϣ
     * @param msg
     * @param t
     */
    public static void error(Object msg, Throwable t) {
        if (errorEnabled) {
            System.err.println(ERR_PREFIX + msg);
            if (t != null) {
                System.err.println(getRenderedMsg(msg, t));
            }
        }
    }

    private static class VectorWriter extends PrintWriter {

        private Vector v;

        VectorWriter() {
            super(new NullWriter());
            v = new Vector();
        }

        public void print(Object o) {
            v.addElement(o.toString());
        }

        public void print(char[] chars) {
            v.addElement(new String(chars));
        }

        public void print(String s) {
            v.addElement(s);
        }

        public void println(Object o) {
            v.addElement(o.toString());
        }

        // JDK 1.1.x apprenly uses this form of println while in
        // printStackTrace()
        public void println(char[] chars) {
            v.addElement(new String(chars));
        }

        public void println(String s) {
            v.addElement(s);
        }

        public void write(char[] chars) {
            v.addElement(new String(chars));
        }

        public void write(char[] chars, int off, int len) {
            v.addElement(new String(chars, off, len));
        }

        public void write(String s, int off, int len) {
            v.addElement(s.substring(off, off + len));
        }

        public void write(String s) {
            v.addElement(s);
        }

        public String[] toStringArray() {
            int len = v.size();
            String[] sa = new String[len];
            for (int i = 0; i < len; i++) {
                sa[i] = (String) v.elementAt(i);
            }
            return sa;
        }

    }

    private static class NullWriter extends Writer {

        public void close() {
            // blank
        }

        public void flush() {
            // blank
        }

        public void write(char[] cbuf, int off, int len) {
            // blank
        }
    }

    private static String getRenderedMsg(Object msg, Throwable thr) {
        StringBuffer sb = new StringBuffer(msg == null ? "" : msg.toString());
        VectorWriter w = new VectorWriter();
        w.println();
        thr.printStackTrace(w);
        String[] strs = w.toStringArray();

        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                sb.append(strs[i]);
            }
        }
        return sb.toString();
    }

}
