package com.xbkj.log.bs.logging;

import java.io.Serializable;

/**
 * @author �ι���
 *
 * Date: 2006-8-30
 * Time: ����07:15:01
 */
public class LogMessage implements Serializable {

    private String method; 

    private String level;
    
    private String className;
    
    private String lineNumber;
    
    private String fileName;

    private String threadName;

    private Object message;
    
    private String module;
    
    private String loggerName;
   
    private String remoteAddr;
    
    private String remotePort;
    
    private String ndc;
    
    private Throwable throwable;
    
    private String user;
    
    private String dataSource;
    
    private String logType;
    
    private long logTime;
    
    //TODO:NEED HGY TO AUDDIT
    private String serial=null;
    
    
    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getClassName() {
        return className;
    }

    public Object getMessage() {
        return message;
    }

    public String getNDC() {
        return ndc;
    }
    
    public void setNDC(String ndc) {
        this.ndc = ndc;
    }
    
    public String getThreadName() {
       return threadName;
    }
    
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fullLocation) {
        this.fileName = fullLocation;
    }

    public void setMessage(Object object) {
        this.message = object;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }
    //TODO:NEED HGY TO AUDIT.
    public String getSerial() {
      return serial;
  }

  public void setSerial(String serial) {
      this.serial = serial;
  }
}
