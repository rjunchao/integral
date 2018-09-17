package com.xbkj.log.bs.logging;

import org.apache.commons.logging.Log;

/**
 * Created by IntelliJ IDEA. User: �ι��� Date: 2005-1-19 Time: 14:20:15
 */
public class CommonsLog implements Log {

    private static final LoggerPlugin commonsLog = LoggerPluginProvider.getInstance().getLoggerPlugin("commons-log");

    public CommonsLog(String name) {

    }

    /**
     * ��鵱ǰģ����־�����Ƿ�����
     * 
     */
    public boolean isTraceEnabled() {
        return commonsLog.isEnabled(Level.TRACE);
    }

    /**
     * ��־������Ϣ
     * 
     */
    public void trace(Object msg) {
        commonsLog.log(Level.TRACE, msg);
    }

    /**
     * ��־������Ϣ�����������ջ��Ϣ
     */
    public void trace(Object msg, Throwable t) {
        commonsLog.log(Level.TRACE, msg, t);
    }

    /**
     * ��鵱ǰģ����־���ش��󼶱��Ƿ�����
     * 
     */
    public boolean isFatalEnabled() {
        return commonsLog.isEnabled(Level.FATAL);
    }

    /**
     * ��־���ش�����Ϣ
     * 
     */
    public void fatal(Object msg) {
        commonsLog.log(Level.FATAL, msg);
    }

    /**
     * ��־���ش�����Ϣ�����������ջ��Ϣ
     */
    public void fatal(Object msg, Throwable t) {
        commonsLog.log(Level.FATAL, msg, t);
    }

    /**
     * ��鵱ǰģ����־�����Ƿ�����
     * 
     * @return boolean
     */
    public boolean isDebugEnabled() {
        return commonsLog.isEnabled(Level.DEBUG);
    }

    /**
     * ��־������Ϣ
     * 
     * @param msg
     */
    public void debug(Object msg) {
        commonsLog.log(Level.DEBUG, msg);
    }

    /**
     * ��־������Ϣ�����������ջ��Ϣ
     */
    public void debug(Object msg, Throwable t) {
        commonsLog.log(Level.DEBUG, msg, t);
    }

    /**
     * ��鵱ǰģ����־��ʾ��Ϣ�Ƿ�����
     * 
     * @return boolean
     */
    public boolean isInfoEnabled() {
        return commonsLog.isEnabled(Level.INFO);
    }

    /**
     * ��־��ͨ��Ϣ
     * 
     * @param msg
     */
    public void info(Object msg) {
        commonsLog.log(Level.INFO, msg);

    }

    /**
     * ��־��ͨ��Ϣ�����������ջ��Ϣ
     */
    public void info(Object msg, Throwable t) {
        commonsLog.log(Level.INFO, msg, t);
    }

    /**
     * ��鵱ǰģ����־�����Ƿ�����
     * 
     * @return boolean
     */
    public boolean isWarnEnabled() {
        return commonsLog.isEnabled(Level.WARN);
    }

    /**
     * ��־������Ϣ
     * 
     * @param msg
     * @param throwable
     */
    public void warn(Object msg, Throwable t) {
        commonsLog.log(Level.WARN, msg, t);
    }

    /**
     * ��־������Ϣ
     * 
     * @param msg
     */
    public void warn(Object msg) {
        commonsLog.log(Level.WARN, msg);

    }

    /**
     * ��鵱ǰģ����־���󱨸��Ƿ�����
     * 
     * @return boolean
     */
    public boolean isErrorEnabled() {
        return commonsLog.isEnabled(Level.ERROR);

    }

    /**
     * ��־������Ϣ
     * 
     * @param msg
     * @param throwable
     */
    public void error(Object msg, Throwable t) {
        commonsLog.log(Level.ERROR, msg, t);
    }

    /**
     * ��־������Ϣ
     * 
     * @param msg
     */
    public void error(Object msg) {
        commonsLog.log(Level.ERROR, msg);
    }
}
