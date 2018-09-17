package com.xbkj.log.bs.logging;

import java.io.Serializable;

public class FQCNMessage implements Serializable {
    
    private String fqcn;

    private Object msg;
    
    public FQCNMessage(String fqcn, Object msg) {
        super();
        this.fqcn = fqcn;
        this.msg = msg;
    }

    public String getFqcn() {
        return fqcn;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String toString() {
        return msg == null ? null : msg.toString();
    }

   
}