package com.xbkj.log.bs.logging;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xbkj.log.bs.logging.impl.log4j.Log4jLogger;
import com.xbkj.log.vo.logging.LoggerException;
import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
import com.xbkj.log.vo.logging.Util;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-8
 * Time: 9:15:01
 * <p/>
 * 
 * ��־����ṩ��ʵ���࣬�ṩ������־����ķ���ʵ��
 */
public class DefaultLoggerPluginProvider extends LoggerPluginProvider {
    /**
     * ��־�����cache
     */
    private Map<String, LoggerPlugin> pluginMap = new HashMap<String, LoggerPlugin>();

    /**
     * ��־����Ĺ��캯��
     */
    private Constructor pluginConstructor = null;

    /**
     * ��־����Ĺ��캯�����Ҫ��
     */
    protected Class logConstructorSignature[] = { java.lang.String.class };

    /**
     * ��־���
     */
    public static final String PLUGIN_PROPERTY = "com.grc.log.bs.logging.LoggerPlugin";

    /**
     * ���ģ���ȡ��־���,���ܻ�ʹ���ò������������ã����ǿ��ǵ�����
     *
     * @param module
     * @return
     */
    public LoggerPlugin getLoggerPlugin(String module) {
        //not make the NCLOG as the normal log parent, or get it directly
        if (module == null || LoggerGeneralConfig.NCLOG_MODULE.equals(module))
            module = LoggerGeneralConfig.ANONY_MODULE;

        LoggerPlugin plugin = null;

        plugin = (LoggerPlugin) pluginMap.get(module);
        if (plugin == null) {
            synchronized (pluginMap) {
                plugin = (LoggerPlugin) pluginMap.get(module);
                if (plugin != null)
                    return plugin;
                plugin = newPlugin(module);
                ModuleLoggerConfiguration config = getModuleConfiguration(module);
                LoggerPlugin parent = null;
                if (config != null && !module.equals(config.getModuleName())) {
                    //module as the prefix config
                    parent = getLoggerPlugin(config.getModuleName());
                }
                plugin.config(config, parent);
                pluginMap.put(module, plugin);
            }
        }
        return plugin;
    }

    /**
     * �ͷ�����cache�е���־���
     */
    public void release() {
        pluginMap.clear();
    }

    /**
     * ������־,һ����ϵͳ���ù�������У����������⻹��Ҫ��LoggerConfigManager��Ӹ����ã�������
     *
     * @param config
     */
    public synchronized void config(ModuleLoggerConfiguration config) {

        if (config == null) {
            throw new IllegalArgumentException("ģ����ƣ�ģ��������Ϣ����Ϊ��");
        }

        getLoggerConfigManager().addConfig(config);
        if (LoggerGeneralConfig.NCLOG_MODULE.equals(config.getModuleName())) {
            Log4jLogger.configErrorLogger(config);
        } else {
            LoggerPlugin parent = getLoggerPlugin(config.getModuleName());
            parent.config(config, null);
            List aList = Util.stringToList(config.getPackagePrefix(), ";");
            
            for (Iterator iter = aList.iterator(); iter.hasNext();) {
                LoggerPluginProvider.getInstance().getLoggerPlugin((String)iter.next()).config(config, parent);       
            }
            
            

        }

    }

    /**
     * ���ģ���ȡ��־������Ϣ
     *
     * @param module
     * @return
     */
    private ModuleLoggerConfiguration getModuleConfiguration(String module) {
        return getLoggerConfigManager().getModuleLoggerConfiguration(module);
    }

    /**
     * ����ģ���½�һ����־���
     *
     * @param module
     * @return
     */
    private LoggerPlugin newPlugin(String module) {
        LoggerPlugin plugin = null;
        Object params[] = new Object[1];
        params[0] = module;

        try {
            plugin = (LoggerPlugin) getPluginConstructor().newInstance(params);
        } catch (InstantiationException e) {
            throw new LoggerException("��־�������ʧ��", e);
        } catch (IllegalAccessException e) {
            throw new LoggerException("��־�������ʧ��,���ù��캯���Ƿ�Ϊpublic", e);
        } catch (InvocationTargetException e) {
            throw new LoggerException("��־��������쳣", e.getTargetException());
        } catch (NullPointerException e) {
            throw new LoggerException("��־��������쳣", e);
        }
        return plugin;
    }

    /**
     * ��ȡ��־����Ĺ��캯��
     *
     * @return
     */
    private Constructor getPluginConstructor() {
        if (pluginConstructor != null)
            return pluginConstructor;
        String pluginClassName = null;
        try {
            pluginClassName = System.getProperty(PLUGIN_PROPERTY);
        } catch (SecurityException e) {
        }
        if (pluginClassName == null) {
            pluginClassName = (String) getAttribute(PLUGIN_PROPERTY);
        }

        if ((pluginClassName == null) && isLog4JAvailable()) {
            pluginClassName = "com.grc.log.bs.logging.impl.log4j.Log4jLoggerPlugin";
        }

        if ((pluginClassName == null) && isJdk14Available()) {
            pluginClassName = "com.grc.log.bs.logging.impl.jdk.JDKLoggerPlugin";
        }

        if (pluginClassName == null) {
            pluginClassName = "com.grc.log.bs.logging.NullLoggerPlugin";
        }

        try {
            Class pluginClass = loadClass(pluginClassName);
            pluginConstructor = pluginClass.getConstructor(logConstructorSignature);

        } catch (ClassNotFoundException e) {
            throw new LoggerException("��־ϵͳ�Ҳ����ʺϵ���־���: " + pluginClassName);
        } catch (NoSuchMethodException e) {
            throw new LoggerException("��־ϵͳ���ȱ�����ַ�Ϊ����Ĺ��캯��: " + pluginClassName);
        } catch (Throwable t) {
            throw new LoggerException(t);
        }
        return pluginConstructor;
    }

    /**
     * ���log4j��log�Ƿ����
     *
     * @return
     */
    protected boolean isLog4JAvailable() {
        try {
            loadClass("org.apache.log4j.Logger");
            loadClass("com.grc.log.bs.logging.impl.log4j.Log4jLoggerPlugin");
            return (true);
        } catch (Throwable t) {
            t.printStackTrace();
            return (false);
        }
    }

    /**
     * ���jdk1.4��log�Ƿ����
     *
     * @return
     */
    protected boolean isJdk14Available() {
        try {
            loadClass("java.util.logging.Logger");
            loadClass("com.grc.log.bs.logging.impl.jdk.JDKLoggerPlugin ");
            return (true);
        } catch (Throwable t) {
            return (false);
        }
    }

    /**
     * ����Ƿ��� NC server��
     *
     * @return
     */
    protected boolean isOnNC31Server() {
        boolean isOnServer = System.getProperties().containsKey("WWW_HOME");
        return isOnServer;
    }

    /**
     * �����߳�ClassLoader�ļ�����
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    private static Class loadClass(final String name) throws ClassNotFoundException {
        Object result = AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                if (LoggerGeneralConfig.isRunningInServer()) {
                    ClassLoader threadCL = getContextClassLoader();
                    if (threadCL != null) {
                        try {
                            return threadCL.loadClass(name);
                        } catch (ClassNotFoundException ex) {
                        }
                    }
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        return e;
                    }
                } else {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {

                    }
                    ClassLoader threadCL = getContextClassLoader();
                    if (threadCL != null) {
                        try {
                            return threadCL.loadClass(name);
                        } catch (ClassNotFoundException ex) {
                            return ex;
                        }
                    }

                    return new ClassNotFoundException("Class not foud: " + name);

                }
            }
        });

        if (result instanceof Class)
            return (Class) result;
        throw (ClassNotFoundException) result;
    }

}