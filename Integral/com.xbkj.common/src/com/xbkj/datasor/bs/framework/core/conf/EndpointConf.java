package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-2
 * Time: ����04:51:02
 *
 */
public class EndpointConf implements Serializable {

    private static final long serialVersionUID = -4035646568411618648L;

    private String address;

    private String port;

    public String getAddress() {
        return address;
    }

    public void setAddress(String host) {
        this.address = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
