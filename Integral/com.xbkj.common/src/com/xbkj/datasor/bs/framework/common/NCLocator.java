package com.xbkj.datasor.bs.framework.common;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.grc.datasor.bs.framework.exception.FrameworkRuntimeException;
import com.grc.datasor.bs.framework.naming.Context;
import com.grc.basic.vo.jcom.util.ClassUtil;

public abstract class NCLocator implements Context {

    private static final RuntimeEnv runtimeEnv = RuntimeEnv.getInstance();

    public static final String SERVICEDISPATCH_URL = "SERVICEDISPATCH_URL";

    public static final String CLIENT_COMMUNICATOR = "CLIENT_COMMUNICATOR";

    public static final String TARGET_MODULE = "nc.targetModule";

    private static NCLocator DEFAULT_SERVER_LOCATOR = null;

    private static NCLocator DEFAULT_CLIENT_LOCATOR = null;

    private static Map<String, NCLocator> locatorMap = new ConcurrentHashMap<String, NCLocator>();

    /**
     * the NCLocator implementation class property
     */
    public static final String LOCATOR_PROVIDER_PROPERTY = "nc.locator.provider";

    /**
     * �ͻ��˵�NCLocatorʵ�������
     */
    private static final String CLIENT_LOCATOR = "nc.bs.framework.comn.cli.ClientNCLocator";

    /**
     * ����˵�NCLocatorʵ�����
     */
    private static final String SERVER_LOCATOR = "nc.bs.framework.server.ServerNCLocator";

    /**
     * ��ݻ�����ȡNCLocator��ʵ�֣�����������ͨ��RuntimeEnv����
     * @see RuntimeEnv
     * @return
     */
    public static NCLocator getInstance() {
        return getInstance(null);
    }

    /**
     * ��ݴ��ݵĻ������Ի�ȡ�ض���NCLocatorʵ�֡���Ҫ�Ĳ���Ϊnc.locator.provider,����
     * NCLocator��ʵ��
     * 
     * @param props
     * @return
     */
    public static NCLocator getInstance(Properties props) {
        NCLocator locator = null;

        String svcDispatchURL = getProperty(props, SERVICEDISPATCH_URL);
        String locatorProvider = getProperty(props, LOCATOR_PROVIDER_PROPERTY);
        String targetModule = getProperty(props, TARGET_MODULE);

        String key = ":" + svcDispatchURL + ":" + locatorProvider + ":" + targetModule;
        locator = locatorMap.get(key);
        if (locator != null) {
            return locator;
        }
        if (!isEmpty(locatorProvider)) {

            locator = newInstance(locatorProvider);
        } else {
            if (!isEmpty(svcDispatchURL)) {
                locator = newInstance(CLIENT_LOCATOR);
            } else {
                locator = getDefaultLocator();
            }
        }

        locator.init(props);
        locatorMap.put(key, locator);
        return locator;
    }

    /**
     * ��ȡĬ�ϵ�NCLocatorʵ�֣�ͨ��RuntimeEnv������л���������
     * 
     * @return
     */
    private static NCLocator getDefaultLocator() {

        if (RuntimeEnv.getInstance().isThreadRunningInServer()) {
            if (DEFAULT_SERVER_LOCATOR == null) {
                DEFAULT_SERVER_LOCATOR = newInstance(SERVER_LOCATOR);
            }
            return DEFAULT_SERVER_LOCATOR;
        } else {
            if (DEFAULT_CLIENT_LOCATOR == null) {
                DEFAULT_CLIENT_LOCATOR = newInstance(CLIENT_LOCATOR);
            }
            return DEFAULT_CLIENT_LOCATOR;
        }

    }

    /**
     * ����һ��NCLocatorʵ��
     * 
     * @param name
     * @return
     */
    private static NCLocator newInstance(String name) {
        try {
            return (NCLocator) ClassUtil.loadClass(name).newInstance();
        } catch (Exception e) {
            throw new FrameworkRuntimeException("Can't find the class: " + name);
        }

    }

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * ��ʼ��NCLocator��ʵ�֣���ͬ��ʵ�ֵĳ�ʼ���Ļ������Բ�ͬ
     * 
     * @param env
     */
    abstract protected void init(Properties env);

    protected static String getProperty(Properties env, String name) {
        String value = env == null ? null : env.getProperty(name);
        if (value == null) {
            value = InvocationInfoProxy.getInstance().getProperty(name);
            if (value == null)
                value = runtimeEnv.getProperty(name);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public <T> T lookup(Class<T> clazz) {
        return (T) lookup(clazz.getName());
    }

}