package com.xbkj.datasor.bs.framework.core;

public enum Scope {

    SINGLETON("singleton"), PROTOTYPE("prototype");

    private String value;

    private Scope(String str) {
        value = str;
    }

    public String toString() {
        return value;
    }
}

