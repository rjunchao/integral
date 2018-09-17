package com.xbkj.log.bs.logging;

import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-19
 * Time: 14:20:15
 */
public class Log {
	private LoggerPlugin logPlugin;

	private static LoggerPlugin anonyLoggerProvider = LoggerPluginProvider.getInstance().getLoggerPlugin(null);

	private Log(LoggerPlugin logPlugin) {
		this.logPlugin = logPlugin;
	}

	public static Log getInstance(String name) {
		if (isRunningInServer()) {
			return new Log(LoggerPluginProvider.getInstance().getLoggerPlugin(name));
		} else {
			return new Log(anonyLoggerProvider);
		}
	}

	static boolean isRunningInServer() {
		return "server".equals(System.getProperty("run.side")) || "server".equals(System.getProperty("nc.run.side"));
	}

	public static Log getInstance(Class clazz) {
		return getInstance(clazz.getName());
	}

	/**
	 * ��鵱ǰģ����־�����Ƿ�����
	 *
	 * @return boolean
	 */
	public boolean isDebugEnabled() {
		return logPlugin.isEnabled(Level.DEBUG);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public void debug(Object msg) {
		logPlugin.log(Level.DEBUG, msg);
	}

	/**
	 * ��־������Ϣ,���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public void debug(Object msg, Class caller, String methodName) {
		logPlugin.log(Level.DEBUG, msg, caller, methodName);

	}

	/**
	 * ��鵱ǰģ����־��ʾ��Ϣ�Ƿ�����
	 *
	 * @return boolean
	 */
	public boolean isInfoEnabled() {
		return logPlugin.isEnabled(Level.INFO);
	}

	/**
	 * ��־��ͨ��Ϣ
	 *
	 * @param msg
	 */
	public void info(Object msg) {
		logPlugin.log(Level.INFO, msg);

	}

	/**
	 * ��־��ͨ��Ϣ���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public void info(Object msg, Class caller, String methodName) {
		logPlugin.log(Level.INFO, msg, caller, methodName);
	}

	/**
	 * ��鵱ǰģ����־�����Ƿ�����
	 *
	 * @return boolean
	 */
	public boolean isWarnEnabled() {
		return logPlugin.isEnabled(Level.WARN);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 * @param throwable
	 */
	public void warn(Object msg, Throwable throwable) {
		logPlugin.log(Level.WARN, msg, throwable);

	}

	public void warn(Object msg, Throwable throwable, Class caller, String methodName) {
		logPlugin.log(Level.WARN, msg, throwable, caller, methodName);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public void warn(Object msg) {
		logPlugin.log(Level.WARN, msg);

	}

	/**
	 * ��־������Ϣ,���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public void warn(Object msg, Class caller, String methodName) {
		logPlugin.log(Level.WARN, msg, caller, methodName);

	}

	/**
	 * ��鵱ǰģ����־���󱨸��Ƿ�����
	 *
	 * @return boolean
	 */
	public boolean isErrorEnabled() {
		return logPlugin.isEnabled(Level.ERROR);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 * @param throwable
	 */
	public void error(Object msg, Throwable throwable) {
		logPlugin.log(Level.ERROR, msg, throwable);
	}

	/**
	 * ��־������Ϣ�����ܿ���
	 *
	 * @param msg
	 * @param throwable
	 * @param caller
	 * @param methodName
	 */
	public void error(Object msg, Throwable throwable, Class caller, String methodName) {
		logPlugin.log(Level.ERROR, msg, throwable, caller, methodName);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public void error(Object msg) {
		logPlugin.log(Level.ERROR, msg);
	}

	/**
	 * ��־������Ϣ�����ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public void error(Object msg, Class caller, String methodName) {
		logPlugin.log(Level.ERROR, msg, caller, methodName);
	}

	/**
	 * ��һ����Key��־����Ϣ�����߳��������У���־ϵͳ���Զ�̬����־�����Ϣ��ʵ����־?
	 * <p/>
	 * ̬��Ϣ��Ŀ��(MDC)
	 *
	 * @param key
	 * @param value
	 */
	public void putMDC(String key, Object value) {
		logPlugin.getMDC().put(key, value);

	}

	/**
	 * ���߳���������ɾ��һ����keyָ���Ķ�̬��Ϣ(MDC)
	 *
	 * @param key
	 */
	public void removeMDC(String key) {
		logPlugin.getMDC().remove(key);
	}

	/**
	 * ��λ�߳��������е�����Map��Ϣ(MDC)
	 */
	public void resetMDC() {
		logPlugin.getMDC().reset();
	}

	/**
	 * ��һ����Ϣѹ���߳������ĵ�һ����ջ(NDC),ʵ��Logջ��̬��Ϣ��
	 *
	 * @param msg
	 */
	public void pushNDC(Object msg) {
		logPlugin.getNDC().push(msg);
	}

	/**
	 * ��һ����Ϣ�����߳������ĵ�һ����ջ(NDC),��pushNDC��Ӧ
	 *
	 * @return Object
	 */
	public Object popNDC() {
		return logPlugin.getNDC().pop();
	}

	/**
	 * ����ջ��̬��Ϣ����־���
	 *
	 * @param depth
	 */
	public void setNDCDepth(int depth) {
		logPlugin.getNDC().setDepth(depth);

	}

	/**
	 * ��λջ��̬��Ϣ
	 */
	public void resetNDC() {
		logPlugin.getNDC().reset();
	}

	public void setLevel(Level level) {
		logPlugin.setLevel(level);
	}

	public ResourceBundle getResourceBundle() {
		return logPlugin.getResourceBundle();
	}

	public void setResourceBundle(ResourceBundle rb) {
		logPlugin.setResourceBundle(rb);
	}

}
