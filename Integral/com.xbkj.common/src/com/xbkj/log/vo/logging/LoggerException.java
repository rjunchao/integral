package com.xbkj.log.vo.logging;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 *
 * ��־������ʱ���쳣����־ϵͳ�е��쳣����non-catchable���쳣��ʽ
 */
public class LoggerException extends RuntimeException {
    protected Throwable cause = null;

    public LoggerException() {
        super();
    }

    public LoggerException(String message) {
        super(message);
    }

    public LoggerException(Throwable cause) {
        this((cause == null) ? null : cause.toString(), cause);
    }

    public LoggerException(String message, Throwable cause) {

        super(message + " (ԭ�� " + cause + ")");
        this.cause = cause;

    }

    public Throwable getCause() {
        return (this.cause);
    }
}
