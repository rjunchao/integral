package com.xbkj.log.bs.logging.impl.log4j;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-18
 * Time: 17:12:25
 *
 * ��ͨ���ļ���־���
 */
public class NormalFileAppender extends org.apache.log4j.FileAppender implements QuietWriterAccess, Log4jLayoutAppender {
    public NormalFileAppender(Layout layout, String logFile, boolean appended) throws IOException {
        super(layout, logFile, appended);

    }

    public QuietWriter getQuietWriter() {
        return qw;
    }

    public void rolling() {
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

    protected void append(LoggingEvent event, Layout layout) {
        if (!checkEntryConditions()) {
            return;
        }
        subAppend(event, layout);
    }

    @Override
    public synchronized void doAppend(LoggingEvent event) {
        doAppend(event, layout);
    }

    protected void subAppend(LoggingEvent event, Layout layout) {

        if (layout != null)
            this.qw.write(layout.format(event));
        else if (this.layout != null) {
            this.qw.write(this.layout.format(event));
        }

        if (this.immediateFlush) {
            this.qw.flush();
        }

    }

    public synchronized void doAppend(String formatedMessage) {

        if (closed) {
            LogLog.error("Attempted to append to closed appender named [" + name + "].");
            return;
        }
        
        this.qw.write(formatedMessage);

        if (this.immediateFlush) {
            this.qw.flush();
        }

    }

}
