package com.xbkj.datasor.bs.framework.core;

import java.net.URL;
import java.util.Properties;

import com.grc.datasor.bs.framework.core.conf.Configuration;

/**
 * @author �ι���
 *
 * Date: 2007-1-6
 * Time: ����02:37:53
 */
public interface Server extends LifeCycle {
    public String getServerName();

    public ServiceCache getServiceCache();

    public Configuration getConfiguration();

    public JndiContext getJndiContext();

    public Deployer getDeployer();

    public void init(Properties props);

    public void destroy();

    public Monitor getMonitor();

    public JmxService getJmxService();

    public Mode getMode();

    public void addContainer(Container container);

    public Container removeContainer(URL url);

    public Container removeContainer(String name);

    public Container getContainer(URL url);

    public Container getContainer(String name);

    public Container[] getContainers();

}
