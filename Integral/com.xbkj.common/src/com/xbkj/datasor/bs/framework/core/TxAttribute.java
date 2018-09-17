package com.xbkj.datasor.bs.framework.core;

public enum TxAttribute {

    CMT("CMT"), BMT("BMT"), NONE("NONE");

    private String repStr;

    private TxAttribute(String str) {
        repStr = str;
    }

    public String toString() {
        return repStr;
    }

    public static TxAttribute fromString(String tx) {
        if ("NONE".equalsIgnoreCase(tx)) {
            return TxAttribute.NONE;
        } else if ("CMT".equalsIgnoreCase(tx)) {
            return TxAttribute.CMT;
        } else if ("BMT".equalsIgnoreCase(tx)) {
            return TxAttribute.BMT;
        } else {
            return TxAttribute.NONE;
        }

    }
}
