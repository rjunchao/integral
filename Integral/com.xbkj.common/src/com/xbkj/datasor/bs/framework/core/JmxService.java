package com.xbkj.datasor.bs.framework.core;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-15
 * Time: ����02:15:11
 *
 */
public interface JmxService {

    public ObjectInstance registerMBean(Object obj, ObjectName name);

    public void unregisterMBean(ObjectName name);

    public boolean isRegisted(ObjectName name);

    public MBeanServer getMBeanServer();
}
