package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author �ι���
 *
 * Date: 2006-9-22
 * Time: ����10:06:50
 */
public class Log4jLoggingEvent extends LoggingEvent {

    private static final long serialVersionUID = 977728011028797652L;

    public Log4jLoggingEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Priority level,
            Object message, Throwable throwable, String moduleName, String logType) {
        this(fqnOfCategoryClass, logger, timeStamp, level, message, throwable, moduleName, logType, null, null);
    }

    public Log4jLoggingEvent(String fqnOfCategoryClass, Category logger, long timeStamp, Priority level,
            Object message, Throwable throwable, String moduleName, String logType, String className,
            String methodName) {
        super(fqnOfCategoryClass, logger, timeStamp, level, message, throwable);
        this.moduleName = moduleName;
        this.logType = logType;
        this.className = className;
        this.methodName = methodName;
    }

    private String moduleName;

    private String logType;

    private String methodName;

    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}
