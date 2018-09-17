package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-3
 * Time: ����01:01:38
 *
 */
public enum Mode {
    PRODUCT("product"), DEVELOPMENT("development"), OUTJAVAEE("outjavaee");
    private final String value;

    private Mode(String str) {
        value = str;
    }

    public String toString() {
        return value;
    }

    public static Mode toMode(String s) {
        if (s == null)
            return DEVELOPMENT;
        if ("product".equals(s)) {
            return PRODUCT;
        } else if ("development".equals(s)) {
            return DEVELOPMENT;
        } else if ("outjavaee".equals(s)) {
            return OUTJAVAEE;
        } else {
            return DEVELOPMENT;
        }
    }

}
