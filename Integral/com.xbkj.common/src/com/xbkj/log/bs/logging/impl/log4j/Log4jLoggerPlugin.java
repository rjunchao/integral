package com.xbkj.log.bs.logging.impl.log4j;

import java.util.ResourceBundle;

import com.xbkj.log.bs.logging.FQCNMessage;
import com.xbkj.log.bs.logging.Level;
import com.xbkj.log.bs.logging.Logger;
import com.xbkj.log.bs.logging.LoggerGeneralConfig;
import com.xbkj.log.bs.logging.LoggerMDC;
import com.xbkj.log.bs.logging.LoggerPlugin;
import com.xbkj.log.bs.logging.MDC;
import com.xbkj.log.bs.logging.NDC;
import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * ����log4j
 */
public class Log4jLoggerPlugin implements LoggerPlugin {

	public static final String fqcnLog4j = Logger.FQCN + ".";

	transient Log4jLogger log;

	private transient String logType;

	public Log4jLoggerPlugin(String module) {
		log = Log4jLogger.getLog4jLogger(module);
	}

	public boolean isEnabled(Level level) {
		return log.isEnabledFor(XLevel.log4jLevel(level));
	}

	public void log(Level level, Object msg, Throwable throwable) {
		if (msg instanceof FQCNMessage) {
			String fqcn = ((FQCNMessage) msg).getFqcn();
			msg = ((FQCNMessage) msg).getMsg();
			log.log(fqcn, XLevel.log4jLevel(level), msg, throwable);
		} else {
			log.log(fqcnLog4j, XLevel.log4jLevel(level), msg, throwable);
		}
	}

	public void log(Level level, Object msg, Throwable throwable, Class clazz, String methodName) {
		if (msg instanceof FQCNMessage) {
			String fqcn = ((FQCNMessage) msg).getFqcn();
			msg = ((FQCNMessage) msg).getMsg();
			log.log(fqcn, XLevel.log4jLevel(level), msg, throwable, clazz.getName(), methodName);
		} else {
			log.log(fqcnLog4j, XLevel.log4jLevel(level), msg, throwable, clazz.getName(), methodName);
		}
	}

	public void log(Level level, Object msg) {
		log(level, msg, null);
	}

	public void log(Level level, Object msg, Class clazz, String methodName) {
		log(level, msg, null, clazz, methodName);
	}

	public MDC getMDC() {
		return LoggerMDC.mdc;
	}

	public NDC getNDC() {
		return Log4jNDC.ndc;
	}

	public void setLevel(Level level) {
		log.setLevel(XLevel.log4jLevel(level));
	}

	/**
	 * ����������־���,�̰߳�ȫ
	 *
	 * @param config
	 */
	public synchronized void config(ModuleLoggerConfiguration config, LoggerPlugin parent) {

		if (parent != null)
			log.config(config, ((Log4jLoggerPlugin) parent).log);
		else
			log.config(config, null);
	}

	static boolean isRunningInServer() {
		return LoggerGeneralConfig.isRunningInServer();
	}

	public synchronized String getModuleName() {
		return log.moduleName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public ResourceBundle getResourceBundle() {
		return log.getResourceBundle();
	}

	public void setResourceBundle(ResourceBundle rb) {
		log.setResourceBundle(rb);
	}

}