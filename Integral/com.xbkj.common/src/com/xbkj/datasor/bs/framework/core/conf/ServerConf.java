package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;

import com.grc.basic.vo.jcom.lang.StringUtil;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-20
 * Time: ����08:52:35
 *
 */
public class ServerConf implements Serializable {

    private static final long serialVersionUID = -4434604687250963302L;

    private String name;

    private EndpointConf httpEndpointConf;

    private EndpointConf httpsEndpointConf;

    public String getName() {
        return name;
    }

    public void setName(String serverName) {
        this.name = serverName;
    }

    public EndpointConf getHttpEndpointConf() {
        return httpEndpointConf;
    }

    public void setHttpEndpointConf(EndpointConf httpEndpointConf) {
        this.httpEndpointConf = httpEndpointConf;
    }

    public EndpointConf getHttpsEndpointConf() {
        return httpsEndpointConf;
    }

    public void setHttpsEndpointConf(EndpointConf httpsEndpointConf) {
        this.httpsEndpointConf = httpsEndpointConf;
    }

    public String getHttpEndpoint() {
        return toEndpoint("http", httpEndpointConf);
    }

    public String getHttpsEndpoint() {
        return toEndpoint("https", httpsEndpointConf);
    }

    private String toEndpoint(String schema, EndpointConf conf) {
        if (conf == null)
            return null;
        StringBuffer sb = new StringBuffer();
        sb.append(schema).append("://");
        if (conf.getAddress() != null && StringUtil.hasText(conf.getAddress())) {
            sb.append(conf.getAddress());
        } else {
            sb.append("localhost");
        }

        if (conf.getPort() != null && StringUtil.hasText(conf.getPort())) {
            sb.append(":");
            sb.append(conf.getPort());
        }

        return sb.toString();
    }

}
