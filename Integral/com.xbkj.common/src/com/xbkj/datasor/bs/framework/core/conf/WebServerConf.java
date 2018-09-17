package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-20
 * Time: ����10:37:23
 *
 */
public class WebServerConf implements Serializable {

    private static final long serialVersionUID = -2723185428044868861L;

    private String name;

    private String address;

    private String port;

    private String protocol;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getProtocol()).append("://");
        sb.append(getAddress());
        if (getPort() != null) {
            sb.append(":").append(getPort());
        }

        return sb.toString();
    }

}
