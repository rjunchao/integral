package com.xbkj.log.bs.logging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.xbkj.log.vo.logging.Debug;
import com.xbkj.log.vo.logging.LoggerException;
import com.xbkj.log.vo.logging.LoggerStrategy;
import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
import com.xbkj.log.vo.logging.Util;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * <p/>
 * ��־����ṩ�ߣ��������εײ���־��ʵ���ز���
 */
public abstract class LoggerPluginProvider {

    /**
     * ����ָ�����ĵ������ļ�
     */
    public static final String LOGGER_CONFIG_PROPERTY = "com.grc.common.bs.logging.config";

    /**
     * ��־�ṩ�ߵ�������
     */
    public static final String PROVIDER_PROPERTY = "com.grc.common.bs.logging.LoggerPluginProvider";

    /**
     * ��־����ṩ�ߵ�Ĭ����
     */
    public static final String PROVIDER_DEFAULT = "com.grc.common.bs.logging.DefaultLoggerPluginProvider";

    /**
     * Ĭ�ϵ����ĵ������ļ�
     */
    public static final String LOGGER_CONFIG_RES = "com.grc.common/bs/logging/nc-logging.properties";;

    /**
     * ���ù�����������
     */
    public static final String CONFIG_MANAGER_PROPERTY = "com.grc.common.bs.logging.LoggerConfigManager";

    /**
     * ���ù�������Ĭ����
     */
    public static final String CONFIG_MANAGER_DEFAULT = "com.grc.common.bs.logging.FileLoggerConfigManager";

    /**
     * ����ṩ������
     */
    private static String providerClassName;

    /**
     * ���ù���������
     */
    private static String configManagerClassName;

    /**
     * ���ù���������������ʵ�����Ļ����ã�������ù�������������־ϵͳͬʱ���أ�
     * ����ͬһClassLoader
     */
    private static LoggerConfigManager configManager;

    /**
     * ����ṩ�ߣ�����ClassLoader�и��룬ʵ�ּ��ݶ���汾����־ʵ�֣�ͨ����)
     */
    protected static Map providers = new HashMap();

    /**
     * ��־ϵͳ����ʱ�Ĳ����ļ�������־ϵͳͬʱ���أ�����ͬһClassLoader
     */
    protected static Properties props = new Properties();

    /**
     * һЩ�����չ��Ҫ���Ƶ�����,�����̰߳�ȫ��Hashtable
     */
    protected Hashtable attributes = new Hashtable();

    /**
     * �쿴�Ƿ��Ѿ���ʼ��
     */
    private static boolean initialized;

    /**
     * �����������־
     */
    private static ModuleLoggerConfiguration errorConfig;

    /**
     * ��ʼ��
     */
    protected static void init() {
        String configRes = null;
        InputStream stream = null;
        if (LoggerGeneralConfig.isRunningInServer()) {
            try {
                try {
                    configRes = System.getProperty(LOGGER_CONFIG_PROPERTY);
                } catch (SecurityException exp) {
                }
                if (configRes == null)
                    configRes = LOGGER_CONFIG_RES;
                stream = getResourceAsStream(LoggerPluginProvider.class.getClassLoader(), configRes);
                //��LoggerPluginProviderͬһClassLoader
                if (stream != null) {
                    props.load(stream);
                }
            } catch (IOException e) {
                //û���κ�����ϵͳ�ܹ�����
            } catch (SecurityException e) {
                //û���κ�����ϵͳ�ܹ�����
            } finally {
                //�ر���
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException ioe) {
                    }
                }

            }
        }

        //ϵͳ�������ȣ�������Σ�Ȼ��Ĭ��
        providerClassName = getProperty(props, PROVIDER_PROPERTY, PROVIDER_DEFAULT);
        configManagerClassName = getProperty(props, CONFIG_MANAGER_PROPERTY, CONFIG_MANAGER_DEFAULT);
        LoggerGeneralConfig.LOG_FORMAT = getProperty(props, LoggerGeneralConfig.LOG_FORMAT_PROPERTY,
                LoggerGeneralConfig.LOG_FORMAT);
        LoggerGeneralConfig.DEFAULT_LEVEL = getProperty(props, LoggerGeneralConfig.DEFAULT_LEVEL_PROPERTY,
                LoggerGeneralConfig.DEFAULT_LEVEL);
        LoggerGeneralConfig.DEFAULT_PATTERN = getProperty(props, LoggerGeneralConfig.DEFAULT_PATTERN_PROPERTY,
                LoggerGeneralConfig.DEFAULT_PATTERN);
        initialized = true;
    }

    protected LoggerPluginProvider() {
    }

    /**
     * ��ȡ LoggerPluginProviderʵ������ϵͳ����
     * nc.bs.bs.logging.LoggerPluginProvider��ֵ������
     * ��־���������ļ�(nc-loggin.properties)
     * nc.bs.bs.logging.LoggerPluginProvider��ֵ���������
     * Ĭ�ϡ��̰߳�ȫ
     *
     * @return LoggerPluginProvider
     */
    public static LoggerPluginProvider getInstance() {
        if (!initialized) {
            synchronized (LoggerPluginProvider.class) {
                if (!initialized) //��ʼ��
                    init();
            }
        }

        //���ܿ���
        LoggerPluginProvider provider = getCachedProvider(getContextClassLoader());

        if (provider == null) {
            synchronized (LoggerPluginProvider.class) {
                //����ClassLoader cache LoggerPluginProvider
                provider = getCachedProvider(getContextClassLoader());
                if (provider != null)
                    return provider;
                try {

                    provider = (LoggerPluginProvider) newObject(providerClassName, getContextClassLoader());
                } catch (SecurityException e) {
                }
                if (provider != null) {
                    cacheProvider(provider);
                    if (props != null) {
                        Iterator itr = props.keySet().iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            provider.setAttribute(key, props.getProperty(key));
                        }
                    }
                } else {
                    throw new LoggerException("��־ϵͳ����");
                }
            }
        }
        return provider;
    }

    /**
     * ��ȡһ����Դ������������Դ��ƿ���Ϊclasspath���ܹ����ص���Դ����һ��URL�ַ�
     *
     * @param loader
     * @param name
     * @return
     */
    private static InputStream getResourceAsStream(final ClassLoader loader, final String name) {
        return (InputStream) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                InputStream input = null;

                if (loader != null) {
                    input = loader.getResourceAsStream(name);
                }
                if (input == null) {
                    input = ClassLoader.getSystemResourceAsStream(name);
                }
                //����Դ����һ��URL��
                if (input == null) {
                    try {
                        input = new URL(name).openStream();
                    } catch (Throwable e) {
                    }
                }
                return input;
            }
        });
    }

    /**
     * ��ȡ��ǰģ���LoggerPlugin
     *
     * @param module
     * @return LoggerPlugin
     */
    abstract public LoggerPlugin getLoggerPlugin(String module);

    /**
     * �ͷ���Դ
     */
    abstract public void release();

    /**
     * ����һ��ģ���������Ϣ
     *
     * @param config
     */
    abstract public void config(ModuleLoggerConfiguration config);

    /**
     * ��־ģ����������ģ������ù���������������ദ��ͬһClassLoader�£�
     * ��֤���õ�ͳһ
     *
     * @return
     */
    public static LoggerConfigManager getLoggerConfigManager() {
        if (!initialized) {
            synchronized (LoggerPluginProvider.class) {
                if (!initialized)
                    init();
            }
        }
        if (configManager != null)
            return configManager;
        else {
            if (configManager == null) {
                //��LoggerPluginProviderͬһClassLoader,ͳһ����
                synchronized (LoggerPluginProvider.class) {
                    if (configManager != null)
                        return configManager;
                    configManager = (LoggerConfigManager) newObject(configManagerClassName,
                            LoggerPluginProvider.class.getClassLoader());

                    if (configManager == null) {
                        Debug.debug("������ָ����LoggerConfigManager�����ҵ��������ڴ漶���DefaultLoggerConfigManager");
                        configManager = new DefaultLoggerConfigManager();
                    }
                    //��ʼ��LoggerConfigManager
                    if (props != null) {
                        Iterator itr = props.keySet().iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            configManager.setAttribute(key, props.getProperty(key));
                        }
                    }
                    configManager.loadConfig();
                }
            }
        }
        
        
       
        

        return configManager;

    }

    /**
     * ��ȡ��ǰ�̻߳�����ClassLoader
     *
     * @return
     */
    protected static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                if (LoggerGeneralConfig.isRunningInServer()) {
                    return Thread.currentThread().getContextClassLoader();
                } else
                    return getClass().getClassLoader();
            }
        });

    }

    /**
     * �½�һ��LoggerPluginProvider��
     *
     * @param objClazz
     * @param classLoader
     * @return
     * @throws LoggerException
     */
    protected static Object newObject(final String objClazz, final ClassLoader classLoader) throws LoggerException {

        //���ڿ簲ȫ��������Ȩִ��
        Object result = AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                Class clazz = null;
                try {
                    if (classLoader != null) {
                        try {
                            clazz = classLoader.loadClass(objClazz);
                        } catch (ClassNotFoundException nfe) {
                            if (classLoader == LoggerPluginProvider.class.getClassLoader()) {
                                throw nfe;
                            }
                        } catch (NoClassDefFoundError ndefe) {
                            if (classLoader == LoggerPluginProvider.class.getClassLoader()) {
                                throw ndefe;
                            }
                        }
                    }

                    //����������־ϵͳ��������ͬ��ClassLoader
                    if (clazz == null)
                        clazz = Class.forName(objClazz);
                    return clazz.newInstance();
                } catch (Throwable e) {
                    return new LoggerException(e);
                }
            }
        });
        if (result instanceof LoggerException)
            throw (LoggerException) result;
        return result;
    }

    /**
     * ��Ѱָ��ClassLoader����־���
     *
     * @param loader
     * @return
     */
    private static LoggerPluginProvider getCachedProvider(ClassLoader loader) {
        LoggerPluginProvider provider = null;
        while (loader != null && provider == null) {
            provider = (LoggerPluginProvider) LoggerPluginProvider.providers.get(loader);
            loader = loader.getParent();
        }
        return provider;
    }

    /**
     * ��LoggerPluginProvider��һ��ʵ�������ClassLoader����
     *
     * @param loader
     * @param provider
     */
    private static void cacheProvider(LoggerPluginProvider provider) {
        if (provider != null) {
            LoggerPluginProvider.providers.put(provider.getClass().getClassLoader(), provider);
        }
    }

    /**
     * �ͷ��ض�ClassLoader��LogginPlugin����
     *
     * @param loader
     */
    public static void release(ClassLoader loader) {
        synchronized (providers) {
            LoggerPluginProvider factory = (LoggerPluginProvider) providers.get(loader);
            if (factory != null) {
                factory.release();
                providers.remove(loader);
            }
        }

    }

    /**
     * �ͷ����е��ṩ�߻���,�̰߳�ȫ
     */
    public static void releaseAll() {
        synchronized (providers) {
            Iterator itrs = providers.values().iterator();
            while (itrs.hasNext()) {
                LoggerPluginProvider provider = (LoggerPluginProvider) itrs.next();
                provider.release();
            }
            providers.clear();
        }
    }

    /**
     * �������ԣ��̰߳�ȫ
     *
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        //attributesΪHashtable���̰߳�ȫ
        return (attributes.get(name));
    }

    /**
     * �������ԣ��̰߳�ȫ
     *
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        //attributesΪHashtable���̰߳�ȫ
        attributes.put(name, value);
    }

    /**
     * ������־����ϵͳ����־���ù��������̰߳�ȫ
     *
     * @param manager
     */
    public synchronized static void setLoggerConfigManager(LoggerConfigManager manager) {
        if (configManager != null) {
            configManager = manager;

            //��������������������Ҫȫ�ֵĶ����е�LoggerPlugin��������
            releaseAll();
        } else {
            throw new IllegalArgumentException("����LoggerConfigManagerΪnull");
        }
    }

    /**
     * ���ȴ�ϵͳ�л�ȡ����,Ȼ���props�л�ȡ���ԣ�props����Ϊnull
     *
     * @param props
     * @param propName
     * @param defaultValue
     * @return
     */
    private static String getProperty(Properties props, String propName, String defaultValue) {
        String retValue;
        retValue = Util.getSystemProperty(propName, null);
        if (retValue == null) {
            if (props != null)
                retValue = props.getProperty(propName, defaultValue);
            else
                retValue = defaultValue;
        }
        return retValue;
    }

    public static ModuleLoggerConfiguration getErrorLoggerConfig() {
        ModuleLoggerConfiguration config = LoggerPluginProvider.getLoggerConfigManager()
                .getModuleLoggerConfiguration(LoggerGeneralConfig.NCLOG_MODULE);
        if (errorConfig != null && errorConfig.equals(config) && config != null)
            return errorConfig;
        if (config == null) {
            errorConfig = defaultErrorLoggerConfig();
            LoggerPluginProvider.getLoggerConfigManager().addConfig(errorConfig);
        } else {
            errorConfig = config;
        }
        return errorConfig;
    }

    /**
     * ���NC��׼��Ĭ�����
     *
     * @return
     */
    public static ModuleLoggerConfiguration defaultErrorLoggerConfig() {
        //        LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.TIME_TYPE);
        //        strategy.setAttribute("datePattern", ".yyyy MMM dd");
        LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.SIZE_TYPE);
        strategy.setAttribute("maxSize", "4MB");
        strategy.setAttribute("maxIndex", "10");
        ModuleLoggerConfiguration errorConfig = new ModuleLoggerConfiguration(LoggerGeneralConfig.NCLOG_MODULE, null,
                LoggerGeneralConfig.DEFAULT_PATTERN, LoggerGeneralConfig.LOG_TYPE_PUB, "./NCLogs/nc-log.log",
                "DEBUG", strategy);
        return errorConfig;
    }

}