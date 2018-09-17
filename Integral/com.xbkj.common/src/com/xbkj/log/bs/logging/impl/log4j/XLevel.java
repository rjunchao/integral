package com.xbkj.log.bs.logging.impl.log4j;

import com.xbkj.log.bs.logging.Level;

/**
 * ��ǿLog4J�ĵ��Լ���������־ϵͳ�ĵ��Լ���
 */
public class XLevel extends org.apache.log4j.Level {

    public static final int TRACE_INT = org.apache.log4j.Level.DEBUG_INT - 100;

    public static final String TRACE_STR = "TRACE";

    public static final XLevel TRACE = new XLevel(TRACE_INT, TRACE_STR, 7);
    
    private static final  org.apache.log4j.Level[] log4jLevels;

    static {

        log4jLevels = new org.apache.log4j.Level[] {
                org.apache.log4j.Level.ALL,
                XLevel.TRACE,
                org.apache.log4j.Level.DEBUG,
                org.apache.log4j.Level.INFO,
                org.apache.log4j.Level.WARN,
                org.apache.log4j.Level.ERROR,
                org.apache.log4j.Level.FATAL,
                org.apache.log4j.Level.OFF
        };
    }

    protected XLevel(final int level, final String strLevel, final int syslogEquiv) {
        super(level, strLevel, syslogEquiv);
    }

    public static org.apache.log4j.Level toLevel(final String name, final org.apache.log4j.Level defaultLevel) {
        if (name == null)
            return defaultLevel;

        String upper = name.toUpperCase();
        if (upper.equals(TRACE_STR)) {
            return TRACE;
        }

        return org.apache.log4j.Level.toLevel(name, defaultLevel);
    }

    public static org.apache.log4j.Level toLevel(final String name) {
        return toLevel(name, TRACE);
    }

    public static org.apache.log4j.Level toLevel(int i) {
        return toLevel(i, TRACE);
    }

    public static org.apache.log4j.Level toLevel(final int i, final org.apache.log4j.Level defaultLevel) {
        org.apache.log4j.Level p;
        if (i == TRACE_INT)
            p = TRACE;
        else
            p = org.apache.log4j.Level.toLevel(i, defaultLevel);
        return p;
    }

    public static org.apache.log4j.Level log4jLevel(Level level) {
        if(level.getValue() >= log4jLevels.length)
            throw new IllegalArgumentException("���Ϸ��ĵ��Լ���" + level);
        return log4jLevels[level.getValue()];
    }
}