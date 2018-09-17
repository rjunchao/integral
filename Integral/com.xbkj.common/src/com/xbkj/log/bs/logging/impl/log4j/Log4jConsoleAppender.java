package com.xbkj.log.bs.logging.impl.log4j;

import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.apache.log4j.Layout;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

import com.xbkj.log.bs.logging.NCSysOutWrapper;

/**
 * @author �ι���
 *
 * Date: 2006-9-10
 * Time: ����01:47:45
 */
public class Log4jConsoleAppender extends WriterAppender implements Log4jLayoutAppender {

    public static final String SYSTEM_OUT = "System.out";

    public static final String SYSTEM_ERR = "System.err";

    protected String target = SYSTEM_OUT;
 
    /**
     The default constructor does nothing.
     */
    public Log4jConsoleAppender() {
    }

    public Log4jConsoleAppender(Layout layout) {
        this(layout, SYSTEM_OUT);
    }

    public Log4jConsoleAppender(Layout layout, String target) {
        this.layout = layout;
        if (SYSTEM_OUT.equals(target)) {
        	setNCWriter(System.out);
        } else if (SYSTEM_ERR.equalsIgnoreCase(target)) {
        	setNCWriter(System.err);
        } else {
            targetWarn(target);
        }
    }
    private void setNCWriter(PrintStream ps){
    	if(ps instanceof NCSysOutWrapper){
    		NCSysOutWrapper ncStream = (NCSysOutWrapper)ps;
    		setWriter(new OutputStreamWriter(ncStream.getSysStream()));   
    	}
    	else{
    		setWriter(new OutputStreamWriter(ps));
    	}
    }
    /**
     *  Sets the value of the <b>Target</b> option. Recognized values
     *  are "System.out" and "System.err". Any other value will be
     *  ignored.  
     * */
    public void setTarget(String value) {
        String v = value.trim();

        if (SYSTEM_OUT.equalsIgnoreCase(v)) {
            target = SYSTEM_OUT;
        } else if (SYSTEM_ERR.equalsIgnoreCase(v)) {
            target = SYSTEM_ERR;
        } else {
            targetWarn(value);
        }
    }

    /**
     * Returns the current value of the <b>Target</b> property. The
     * default value of the option is "System.out".
     *
     * See also {@link #setTarget}.
     * */
    public String getTarget() {
        return target;
    }

    void targetWarn(String val) {
        LogLog.warn("[" + val + "] should be System.out or System.err.");
        LogLog.warn("Using previously set target, System.out by default.");
    }

    public void activateOptions() {
        if (target.equals(SYSTEM_OUT)) {
            setWriter(new OutputStreamWriter(System.out));
        } else {
            setWriter(new OutputStreamWriter(System.err));
        }
    }

    /**
     *  This method overrides the parent {@link
     *  WriterAppender#closeWriter} implementation to do nothing because
     *  the console stream is not ours to close.
     * */
    protected final void closeWriter() {
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

    public void append(LoggingEvent event, Layout layout) {
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
    }

    public synchronized void doAppend(String formatedMessage) {
        if (!checkEntryConditions()) {
            return;
        }
        this.qw.write(formatedMessage);
        
        if (this.immediateFlush) {
            this.qw.flush();
        }     
    }
    
   
}
