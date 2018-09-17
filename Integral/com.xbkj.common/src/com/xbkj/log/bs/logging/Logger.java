package com.xbkj.log.bs.logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.xbkj.log.vo.logging.Util;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * ��־API�����ӿڣ���־ϵͳ����ڡ����ǵ���ͬ��lib�ڵ�����־ģ��ʱ��
 * ������������ڵĲ�ͬģ�飬�ֱ������־������ʹ��
 * ����־ϵͳ��lib����Ҫ�����������е�ģ�� ��
 * ʵ���˶�����־���𣬲��ܹ���̬�����á�������־�����۹�ʵ�����ֶ�̬���͵���Ϣ��
 * ־(MDC��NDC)��
 * ��־�ļ����Ϊ��
 * all < trace < debug < info < warn < error < fatal < off��
 * ���ǰ�������־����С�ڵ�ǰ����־����������־����ֹ��
 * ��־�ĵ��Լ���ͨ���������á�
 * Ŀǰֻ����debug, info, warn, error����
 */
public abstract class Logger {

	private static Map<Thread, ThreadState> threadStates = new HashMap<Thread, ThreadState>();

	private static Map<Thread, Map<String, Object>> threadStateMap = new HashMap<Thread, Map<String, Object>>();

	/**
	 * ģ����Ϣ��һ���̼߳����ȫ�ֱ���
	 */
	private static final ThreadLocal<String> module = new ThreadLocal<String>();

	/**
	 * �̼߳����buffer���������ǰ���ṩ��Ϣ��Ŀ��
	 */
	private static ThreadLocal threadBuffer = new ThreadLocal();

	public static boolean enableThreadTrace = false;

	/**
	 * ��־�ĸ���ƣ���ϵ����־������Ƶ��㷨
	 */
	public static final String FQCN = "nc.bs.logging";//Logger.class.getName();

	private static final LoggerPlugin anonyLoggerPlugin = LoggerPluginProvider
			.getInstance().getLoggerPlugin("anonymous");

	private static final LoggerPluginProvider provider = LoggerPluginProvider
			.getInstance();

	private static final ThreadLocal<LoggerPlugin> threadLogger = new ThreadLocal<LoggerPlugin>();
	//TODO:NEED HGY TO AUDIT.
	private static final ThreadLocal<String> userLevel = new ThreadLocal<String>();
	/**
	 * ��ʼ��Logger,ϵͳ����ݵ��õĶ�ջ�Ƶ�������ģ�飬ģ�鰴����İ���ǰ����������?
	 * ��ҪLog�������ģ�飬�벻Ҫ���á�����־�ķ����ߵ���
	 */
	public static void init() {
		String callingClassName = Util.inferCaller(Logger.FQCN)[0];
		init(callingClassName);

	}

	/**
	 * ��������࣬��ȥ�Ƶ����
	 *
	 * @param caller
	 */
	public static void init(Class caller) {
		init(caller.getName());

	}

	/**
	 * ��ʼ��Logger,����ϵͳĿǰ��ģ�飬�������Ϊ�գ�ϵͳ����ݵ��õĶ�ջ�Ƶ�������ģ?
	 * ��ģ�鰴����İ���ǰ�������������ҪLog�������ģ�飬�벻Ҫ���á�����־�ķ���?
	 *
	 * @param moduleName
	 */
	public static void init(String name) {
		module.set(name);
		threadLogger.set(provider.getLoggerPlugin(name));
	}

	/**
	 * �ӵ�ǰִ�л����а�ģ����Ϣ���и�λ��
	 */
	@SuppressWarnings("unchecked")
	public static void reset() {
		resetNDC();
		resetMDC();
		threadStateMap.remove(Thread.currentThread());
		threadStates.remove(Thread.currentThread());
		module.set(null);
	}

	/**
	 * ��ȡ������߳��е���Ϣ
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getThreadBuffer() {
		StringBuffer sb = (StringBuffer) threadBuffer.get();
		threadBuffer.set(null);
		if (sb != null)
			return sb.toString();
		else
			return "";
	}

	/**
	 * ��ȡ��ǰ������ģ����
	 *
	 * @return String
	 */
	public static String getModule() {
		String name = (String) module.get();
		return name;
	}

	/**
	 * ���߳��е�buffer�����Ϣ
	 *
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public static void addInfoToThreadBuffer(Object msg) {
		if (threadBuffer.get() == null) {
			threadBuffer.set(new StringBuffer());
		}
		StringBuffer sb = (StringBuffer) threadBuffer.get();
		sb.append(LoggerGeneralConfig.LINE_SEP).append(msg);
	}

	/**
	 * ��鵱ǰģ����־�����Ƿ�����
	 *
	 * @return boolean
	 */
	public static boolean isDebugEnabled() {
		return getLoggerPlugin().isEnabled(Level.DEBUG);

	}

	static boolean isTraceEnabled() {
		return getLoggerPlugin().isEnabled(Level.TRACE);
	}

	static void trace(Object msg, Throwable thr) {
		getLoggerPlugin().log(Level.TRACE, msg, thr);
	}

	static void trace(Object msg) {
		getLoggerPlugin().log(Level.TRACE, msg);
	}

	static boolean isFatalEnabled() {
		return getLoggerPlugin().isEnabled(Level.FATAL);
	}

	static void fatal(Object message) {
		getLoggerPlugin().log(Level.FATAL, message);
	}

	static void fatal(Object message, Throwable thr) {
		getLoggerPlugin().log(Level.FATAL, message, thr);
	}

	static void debug(Object msg, Throwable thr) {
		getLoggerPlugin().log(Level.DEBUG, msg, thr);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public static void debug(Object msg) {
		getLoggerPlugin().log(Level.DEBUG, msg);
	}

	/**
	 * ��־������Ϣ,���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public static void debug(Object msg, Class caller, String methodName) {
		getLoggerPlugin().log(Level.DEBUG, msg, caller, methodName);
	}

	/**
	 * ��鵱ǰģ����־��ʾ��Ϣ�Ƿ�����
	 *
	 * @return boolean
	 */
	public static boolean isInfoEnabled() {
		boolean retValue = getLoggerPlugin().isEnabled(Level.INFO);
		return retValue;
	}

	/**
	 * ��־��ͨ��Ϣ
	 *
	 * @param msg
	 */
	public static void info(Object msg) {
		getLoggerPlugin().log(Level.INFO, msg);
	}

	/**
	 * ��־��ͨ��Ϣ���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public static void info(Object msg, Class caller, String methodName) {
		getLoggerPlugin().log(Level.INFO, msg, caller, methodName);
	}

	/**
	 * ��鵱ǰģ����־�����Ƿ�����
	 *
	 * @return boolean
	 */
	public static boolean isWarnEnabled() {
		return getLoggerPlugin().isEnabled(Level.WARN);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 * @param throwable
	 */
	public static void warn(Object msg, Throwable throwable) {
		getLoggerPlugin().log(Level.WARN, msg, throwable);
	}

	public static void warn(Object msg, Throwable throwable, Class caller,
			String methodName) {
		getLoggerPlugin().log(Level.WARN, msg, throwable, caller, methodName);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public static void warn(Object msg) {
		getLoggerPlugin().log(Level.WARN, msg);
	}

	/**
	 * ��־������Ϣ,���ܿ���
	 *
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public static void warn(Object msg, Class caller, String methodName) {
		getLoggerPlugin().log(Level.WARN, msg, caller, methodName);
	}

	/**
	 * ��鵱ǰģ����־���󱨸��Ƿ�����
	 *
	 * @return boolean
	 */
	public static boolean isErrorEnabled() {
		return getLoggerPlugin().isEnabled(Level.ERROR);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 * @param throwable
	 */
	public static void error(Object msg, Throwable throwable) {
		getLoggerPlugin().log(Level.ERROR, msg, throwable);
	}

	/**
	 * ��־������Ϣ�����ܿ���
	 * @param msg
	 * @param throwable
	 * @param caller
	 * @param methodName
	 */
	public static void error(Object msg, Throwable throwable, Class caller,
			String methodName) {
		getLoggerPlugin().log(Level.ERROR, msg, throwable, caller, methodName);
	}

	/**
	 * ��־������Ϣ
	 *
	 * @param msg
	 */
	public static void error(Object msg) {
		getLoggerPlugin().log(Level.ERROR, msg);
	}

	/**
	 * ��־������Ϣ�����ܿ���
	 * @param msg
	 * @param caller
	 * @param methodName
	 */
	public static void error(Object msg, Class caller, String methodName) {
		getLoggerPlugin().log(Level.ERROR, msg, caller, methodName);
	}

	/**
	 * ��һ����Key��־����Ϣ�����߳��������У���־ϵͳ���Զ�̬����־�����Ϣ��ʵ����־?
	 * <p/>
	 * ̬��Ϣ��Ŀ��(MDC)
	 *
	 * @param key
	 * @param value
	 */
	public static void putMDC(String key, Object value) {
		LoggerMDC.mdc.put(key, value);
	}

	public static Object getMDC(String key) {
		return getLoggerPlugin().getMDC().get(key);
	}

	/**
	 * ���߳���������ɾ��һ����keyָ���Ķ�̬��Ϣ(MDC)
	 *
	 * @param key
	 */
	public static void removeMDC(String key) {
		getLoggerPlugin().getMDC().remove(key);
	}

	/**
	 * ��λ�߳��������е�����Map��Ϣ(MDC)
	 */
	public static void resetMDC() {
		getLoggerPlugin().getMDC().reset();
	}

	/**
	 * ��һ����Ϣѹ���߳������ĵ�һ����ջ(NDC),ʵ��Logջ��̬��Ϣ��
	 *
	 * @param msg
	 */
	public static void pushNDC(Object msg) {
		getLoggerPlugin().getNDC().push(msg);
	}

	/**
	 * ��һ����Ϣ�����߳������ĵ�һ����ջ(NDC),��pushNDC��Ӧ
	 *
	 * @return Object
	 */
	public static Object popNDC() {
		Object ret = getLoggerPlugin().getNDC().pop();
		return ret;
	}

	/**
	 * ����ջ��̬��Ϣ����־���
	 *
	 * @param depth
	 */
	public static void setNDCDepth(int depth) {
		getLoggerPlugin().getNDC().setDepth(depth);
	}

	/**
	 * ��λջ��̬��Ϣ
	 */
	public static void resetNDC() {
		getLoggerPlugin().getNDC().reset();
	}

	public static void setLevel(Level level) {
		getLoggerPlugin().setLevel(level);
	}

	/**
	 * ��ȡ��̬��LoggerPlugin
	 *
	 * @return
	 */
	private static LoggerPlugin getLoggerPlugin() {
		if (LoggerGeneralConfig.isRunningInServer()) {
			LoggerPlugin plugin = threadLogger.get();
			if (plugin == null) {
				return anonyLoggerPlugin;
			} else {

				return plugin;
			}
		} else
			return anonyLoggerPlugin;
	}

	@SuppressWarnings("unchecked")
	public static void setThreadState(String state) {
		if (enableThreadTrace) {
			threadStates.put(Thread.currentThread(), new ThreadState(state,
					System.currentTimeMillis()));
		}
	}

	public static ThreadState getThreadState(Thread thread) {
		return threadStates.get(thread);
	}

	public static Object getMappedThreadState(String key) {
		return getMappedThreadState(Thread.currentThread(), key);
	}

	public static Object getMappedThreadState(Thread thread, String key) {
		Map<String, Object> map = null;
		synchronized (threadStateMap) {
			map = threadStateMap.get(thread);
		}
		if (map != null) {
			return map.get(key);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List getThreadStates() {
		List list = new ArrayList();
		Set<Thread> threadStateKeys = threadStates.keySet();
		for (Thread t : threadStateKeys) {
			Object o = getThreadState(t);
			if (o != null) {
				list.add(o);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List getMappedThreadStates(String key) {
		List list = new ArrayList();
		Set<Thread> threadStateKeys = threadStateMap.keySet();
		for (Thread t : threadStateKeys) {
			Object o = getMappedThreadState(t, key);
			if (o != null) {
				list.add(o);
			}
		}
		return list;
	}

	public static void setMappedThreadState(String key, Object value) {
		Map<String, Object> map = threadStateMap.get(Thread.currentThread());
		if (map == null) {
			map = new HashMap<String, Object>();
			threadStateMap.put(Thread.currentThread(), map);
		}

		map.put(key, value);
	}

	public static String getThreadState() {
		return (String) getThreadState(Thread.currentThread()).getState();
	}

	public static void resetMappedThreadState() {
		LoggerMDC.mdc.reset();
	}

	public static synchronized void enableThreadTrace(boolean enable) {
		enableThreadTrace = enable;
	}

	public static ResourceBundle getResourceBundle() {
		return getLoggerPlugin().getResourceBundle();
	}

	public static void setResourceBundle(ResourceBundle rb) {
		getLoggerPlugin().setResourceBundle(rb);
	}

	static void info(Object msg, Throwable t) {
		getLoggerPlugin().log(Level.INFO, msg, t);

	}
	
	/**
	 * TODO: NEED HGY TO AUDIT.
	 * @param level
	 */
	public static void setUserLevel(String level) {
		userLevel.set(level);
	}
	
	/**
 * TODO: NEED HGY TO AUDIT.
	 * @return
	 */
	public static String getUserLevel() {
		//TODO:NEED HGY TO AUDIT.
		return userLevel.get();
	}  

}