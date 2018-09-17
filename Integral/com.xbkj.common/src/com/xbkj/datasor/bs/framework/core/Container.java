package com.xbkj.datasor.bs.framework.core;

import java.net.URL;

/**
 * Created by IntelliJ IDEA
 * User: �ι���
 * Date: 2005-1-21
 * Time: 19:20:50
 * 
 */
public interface Container extends GenericContainer<ComponentMeta>, Priority {
    public URL getUrl();

    public Server getServer();

    public Builder newBuilder();

}
