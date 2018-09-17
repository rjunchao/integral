package com.xbkj.log.bs.logging.impl.log4j;

/**
 * @author �ι���
 *
 * Date: 2006-8-30
 * Time: ����08:24:36
 */
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import com.xbkj.log.bs.logging.LogMessage;
import com.xbkj.log.bs.logging.LogMessageBuffer;
import com.xbkj.log.bs.logging.Logger;

/**
 * @author �ι���
 *
 * Date: 2006-8-30
 * Time: ����03:01:46
 */
public class BufferAppender extends AppenderSkeleton {

    private LogMessageBuffer lb;
  
    public BufferAppender() {
        lb = new LogMessageBuffer();
    }

    public BufferAppender(LogMessageBuffer buff) {
        lb = buff;
    }

    public BufferAppender(int bufferSize) {
        lb = new LogMessageBuffer(bufferSize);
    }

    protected void append(LoggingEvent event) {
        if (lb != null && lb.isEnabled()) {

            LogMessage message = new LogMessage();

            message.setLevel(event.getLevel().toString());
            message.setMethod(getMethod(event));
            message.setClassName(getClassName(event));
            if (lb.isEnableDetailTrace()) {
                message.setLineNumber(event.getLocationInformation().getLineNumber());
                message.setFileName(event.getLocationInformation().getFileName());
            } else {
                message.setLineNumber("?");
                message.setFileName("?");
            }

            message.setThreadName(event.getThreadName());
            message.setModule(getModule(event));
            message.setLoggerName(event.getLoggerName());

            message.setRemoteAddr((String) Logger.getMDC("remoteAddr"));
            message.setRemotePort(Logger.getMDC("remotePort") + "");
            message.setMessage(getMessage(event));
            message.setNDC(event.getNDC());
            if (event.getThrowableInformation() != null)
                message.setThrowable(event.getThrowableInformation().getThrowable());
            message.setDataSource((String) event.getMDC("datasource"));
            message.setUser(event.getMDC("user") + "");

            message.setLogType(getLogType(event));
            message.setLogTime(event.timeStamp);
            //TODO:NEED HGY TO AUDIT
            message.setSerial(Logger.getMDC("serial")+"");
            lb.addMessage(message);
        }
    }

    private String getLogType(LoggingEvent event) {
        String logType = (((Log4jLoggingEvent) event)).getLogType();

        if (logType == null)
            return "PUB";
        else
            return logType;
    }

    private Object getMessage(LoggingEvent event) {
        return event.getMessage();
    }

    private String getModule(LoggingEvent event) {
        String moduleName = ((Log4jLoggingEvent) event).getModuleName();

        if (moduleName == null || "anonymous".equals(moduleName))
            moduleName = event.getLoggerName();

        return moduleName;
    }

    private String getClassName(LoggingEvent event) {
        String retValue = ((Log4jLoggingEvent) event).getClassName();

        if (retValue == null) {
            LocationInfo locationInfo = event.getLocationInformation();
            retValue = locationInfo.getClassName();
        }
        return retValue;

    }

    private String getMethod(LoggingEvent event) {
        String retValue = ((Log4jLoggingEvent) event).getMethodName();

        if (retValue == null && lb.isEnableDetailTrace()) {
            LocationInfo locationInfo = event.getLocationInformation();
            retValue = locationInfo.getMethodName();
        }

        return retValue;
    }

    public void close() {

    }

    public boolean requiresLayout() {
        return false;
    }

    public void setBufferSize(int newBufferSize) {
        lb.setBufferSize(newBufferSize);
    }

    public LogMessageBuffer getLogMessageBuffer() {
        return lb;
    }

}
