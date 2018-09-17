package com.xbkj.log.bs.logging;

import java.util.ResourceBundle;

import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * ��ʵ��
 */
public class NullLoggerPlugin implements LoggerPlugin {
	/**
	 * MDC
	 */
	private MDC mdc = new NullMDC();

	private String name;

	/**
	 * NDC
	 */
	private NDC ndc = new NullNDC();

	public NullLoggerPlugin() {

	}

	public NullLoggerPlugin(String name) {
		this.name = name;
	}

	public boolean isEnabled(Level level) {

		return false;

	}

	public void log(Level level, Object msg, Throwable throwable) {
	}

	public void log(Level level, Object msg, Throwable throwable, Class clazz, String methodName) {
	}

	public void log(Level level, Object msg) {
	}

	public void log(Level level, Object msg, Class clazz, String methodName) {

	}

	public MDC getMDC() {

		return mdc;
	}

	public NDC getNDC() {

		return ndc;
	}

	public void config(ModuleLoggerConfiguration config, LoggerPlugin plugin) {
	}

	public void configErrorLogger(ModuleLoggerConfiguration config) {

	}

	class NullMDC implements MDC {

		public void put(String key, Object msg) {
		}

		public Object get(String key) {
			return null;
		}

		public void remove(String key) {
		}

		public void reset() {

		}

		public Object getByThread(Thread thread, String key) {

			return null;
		}

	}

	class NullNDC implements NDC {

		public void push(Object msg) {

		}

		public Object pop() {
			return null;
		}

		public void reset() {

		}

		public Object get() {

			return null;
		}

		public void setDepth(int depth) {

		}
	}

	public void setLevel(Level level) {

	}

	public String getModuleName() {
		return name;
	}

	public ResourceBundle getResourceBundle() {
		return null;
	}

	public void setResourceBundle(ResourceBundle rb) {

	}

}
