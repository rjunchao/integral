package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class LoggerCenterAppender extends AppenderSkeleton {

    protected QuietWriterAccess access;

    public LoggerCenterAppender(Layout layout, QuietWriterAccess access) {
        this.layout = layout;
        this.access = access;
    }

    protected void append(LoggingEvent event) {

        if (access != null && access.getQuietWriter() != null) {
            synchronized (access) {
                access.rolling();
                access.getQuietWriter().write(this.layout.format(event));

                if (layout.ignoresThrowable()) {
                    String[] s = event.getThrowableStrRep();
                    if (s != null) {
                        int len = s.length;
                        for (int i = 0; i < len; i++) {
                            access.getQuietWriter().write(s[i]);
                            access.getQuietWriter().write(Layout.LINE_SEP);
                        }
                    }
                }

                access.getQuietWriter().flush();
            }

        }
    }

    public void close() {
    }

    public boolean requiresLayout() {
        return true;
    }

}