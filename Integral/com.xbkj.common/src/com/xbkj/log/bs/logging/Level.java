package com.xbkj.log.bs.logging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * ��־����Ŀǰֻ����DEBUG, WARN, ERROR
 * ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF��
 * ֻ�м������С�Ĳ�����־����
 */
public class Level implements Serializable {

    /**
     * �����е��Լ���
     */
    public final static Level ALL;
    /**
     * ���ټ���
     */
    public final static Level TRACE;

    /**
     * ���Լ���
     */
    public final static Level DEBUG;
    /**
     * ��ʾ����
     */
    public final static Level INFO;

    /**
     * ���漶��
     */
    public final static Level WARN;

    /**
     * ���󼶱�
     */
    public final static Level ERROR;

    /**
     * ���ش��󼶱�
     */
    public final static Level FATAL;

    /**
     * �رռ���
     */
    public final static Level OFF;
    
    private final static Map<String, Level> levelMap = new HashMap<String, Level>();
    
    static {
        ALL = new Level("ALL", 0);
        TRACE = new Level("TRACE", 1);
        DEBUG = new Level("DEBUG", 2);
        INFO = new Level("INFO", 3);
        WARN = new Level("WARN", 4);
        ERROR = new Level("ERROR", 5);
        FATAL = new Level("FATAL", 6);
        OFF = new Level("OFF", 7);
        
        levelMap.put("ALL", ALL);
        levelMap.put("TRACE", TRACE);
        levelMap.put("DEBUG", DEBUG);
        levelMap.put("INFO", INFO);
        levelMap.put("WARN", WARN);
        levelMap.put("ERROR", ERROR);
        levelMap.put("FATAL", FATAL);
        levelMap.put("OFF", OFF);
        
    }


    /**
     * ��ʾ���Լ�����ַ�
     */
    protected String strLevel;
    
    private int value;


    /**
     * @param strLevel
     */
    private Level(String strLevel, int value) {
        this.strLevel = strLevel;
        this.value = value;
    }

    /**
     * @param strLevel
     * @return Level
     */
    public static Level getLevel(String strLevel) {
        return levelMap.get(strLevel);
    }

    /**
     * toString
     *
     * @return
     */
    public String toString() {
        return strLevel == null ? "ERROR" : strLevel;
    }

    /**
     * ����Ƿ����
     *
     * @param o
     * @return
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if (o instanceof Level) {
            Level other = (Level) o;
            return other.strLevel.equals(strLevel);

        } else {
            return false;
        }

    }
    
    public int getValue() {
        return value;
    }
}
