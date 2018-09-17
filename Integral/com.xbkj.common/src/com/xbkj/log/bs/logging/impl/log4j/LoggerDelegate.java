package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

class LoggerDelegate extends AppenderSkeleton {

    private String delegateToName;

    private ThreadLocal stackPath = new ThreadLocal();

    private Log4jLogger thisLog;

    private Log4jLogger delegateToLog;

    boolean useDelegateLayout = false;

    public LoggerDelegate(Log4jLogger thisLog, String delegateToName) {
        this(thisLog, delegateToName, false);
    }

    public LoggerDelegate(Log4jLogger thisLog, String delegateToName, boolean useDelegateLayout) {
        this.thisLog = thisLog;
        this.delegateToName = delegateToName;
        delegateToLog = (Log4jLogger) Log4jLogger.getLog4jLogger(delegateToName);
        this.useDelegateLayout = useDelegateLayout;

    }

    public void close() {
    }

    protected void append(LoggingEvent event) {
        if (contains(this))
            return;
        add(this);
        if(useDelegateLayout) {
            delegateToLog.delegateAppenders(event, delegateToLog.getLayout());
        } else {
            delegateToLog.delegateAppenders(event, thisLog.getLayout());
        }
        clear();
    }

    public boolean requiresLayout() {
        return layout != null;
    }

    private boolean contains(Object obj) {
        return stackPath.get() != null;
    }

    private void add(Object obj) {
        stackPath.set(this);
    }

    private void clear() {
        stackPath.set(null);

    }

    public String getDelegateToName() {
        return delegateToName;
    }

}