package com.xbkj.log.bs.logging.impl.log4j;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-18
 * Time: 17:13:30
 *
 * ���մ�С���еĵ��ļ���־���
 */
public class SizeRollingFileAppender extends org.apache.log4j.RollingFileAppender implements QuietWriterAccess, Log4jLayoutAppender {

    public SizeRollingFileAppender(Layout layout, String logFile) throws IOException {
        super(layout, logFile);

    }

    public synchronized void setFile(String filename, boolean append, boolean bufferedIO, int bufferSize)
            throws IOException {
        super.setFile(filename, append, bufferedIO, bufferSize);
    }

    public void setFile(final String filename) {
        super.setFile(filename);
    }

    public QuietWriter getQuietWriter() {
        return qw;
    }

    public void rolling() {
        if ((fileName != null) && ((CountingQuietWriter) qw).getCount() >= maxFileSize)
            this.rollOver();
    }

    public synchronized void doAppend(LoggingEvent event, Layout layout) {
        if (closed) {
            LogLog.error("Attempted to append to closed appender named [" + name + "].");
            return;
        }

        if (!isAsSevereAsThreshold(event.getLevel())) {
            return;
        }

        Filter f = this.headFilter;

        FILTER_LOOP: while (f != null) {
            switch (f.decide(event)) {
            case Filter.DENY:
                return;
            case Filter.ACCEPT:
                break FILTER_LOOP;
            case Filter.NEUTRAL:
                f = f.next;
            }
        }

        append(event, layout);
    }

    @Override
    public synchronized void doAppend(LoggingEvent event) {
        doAppend(event, layout);
    }

    protected  void append(LoggingEvent event, Layout layout) {
        if (!checkEntryConditions()) {
            return;
        }
        subAppend(event, layout);
    }

    protected void subAppend(LoggingEvent event, Layout layout) {
        if(layout != null)
            this.qw.write(layout.format(event));
        else if(this.layout != null) {
            this.qw.write(this.layout.format(event));
        }
        
        if (this.immediateFlush) {
            this.qw.flush();
        }
        if ((fileName != null) && ((CountingQuietWriter) qw).getCount() >= maxFileSize)
            this.rollOver();
    }
    
    public synchronized void doAppend(String formatedMessage) {
        if (!checkEntryConditions()) {
            return;
        }
        this.qw.write(formatedMessage);
        
        if (this.immediateFlush) {
            this.qw.flush();
        }  
        
        if ((fileName != null) && ((CountingQuietWriter) qw).getCount() >= maxFileSize)
            this.rollOver();
    }
}