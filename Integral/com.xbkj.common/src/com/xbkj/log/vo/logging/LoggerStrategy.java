package com.xbkj.log.vo.logging;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-9
 * Time: 12:48:04
 * <p/>
 * ��־�����Ĳ�������
 */
public class LoggerStrategy implements Serializable {
    public static final int NORMAL_TYPE = 0;
    public static final int SIZE_TYPE = 1;
    public static final int TIME_TYPE = 2;

    private int type;
    private HashMap hm = new HashMap();

    public LoggerStrategy(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

     /**
      * �������ԣ���ͬ���͵��в�ͬ������
      * @param name  time: data_pattern
      * @param o
      */
    public void setAttribute(String name, Object o) {
        hm.put(name, o);
    }

    public Object getAttribute(String name) {
        return hm.get(name);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o instanceof LoggerStrategy) {
            LoggerStrategy other = (LoggerStrategy) o;
            return other.type == type && other.hm.equals(hm);
        }
        return false;
    }
}
