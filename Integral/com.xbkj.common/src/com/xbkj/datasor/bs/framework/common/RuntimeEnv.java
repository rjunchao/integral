package com.xbkj.datasor.bs.framework.common;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.xbkj.basic.vo.jcom.env.EnvironmentUtil;
import com.xbkj.basic.vo.jcom.util.ClassUtil;
import com.xbkj.datasor.bs.framework.exception.FrameworkRuntimeException;
import com.xbkj.datasor.bs.framework.util.PropertyUtil;
import com.xbkj.log.bs.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * 
 * Contains the runtime environment information for the
 * client or server
 */
public final class RuntimeEnv {

    /**
     * the property of VMID
     */
    public static final String VMID_PROPERTY = "VMID";

    /**
     * the NC_HOME path
     */
    public static final String SERVER_LOCATION_PROPERTY = "nc.server.location";

    public static final String MODULES_LOCATION_PROPERTY = "nc.modules.location";

    /**
     * the line separator depend on the os
     */
    public static final String LINE_SEP = PropertyUtil.getSystemProperty("line.separator");

    /**
     * the RuntimeEnv singleton implementation
     */
    private static RuntimeEnv runtimeEnv = new RuntimeEnv();

    private Boolean runingServer;

    /**
     * the properties which store all the property framework needed
     */
    private Properties abitraryProperty = new Properties();

    /**
     * the class loader to new a new object. at the client side, it is the 
     * NCClassLoader��at the server side it is the public class loader
     */
    private ClassLoader bizClassLoader;

    private Boolean developmentMode;

    private ThreadLocal<Boolean> threadRunningInServer = new ThreadLocal<Boolean>() {
        protected Boolean initialValue() {
            return new Boolean(isRunningInServer());
        }
    };

    /**
     * default construct of the RuntimeEnv
     *
     */
    private RuntimeEnv() {
        bizClassLoader = ClassUtil.getContextClassLoader();
    }

    /**
     * get the singleton RuntimeEnv instance
     * @return
     */
    public static RuntimeEnv getInstance() {
        return runtimeEnv;
    }

    /**
     * set the business class loader. normally at the client side, it is the NCClassLoader, at the
     * server side it is the public class loader
     * @param classLoader
     */
    public void setBizClassLoader(ClassLoader classLoader) {
        this.bizClassLoader = classLoader;
    }

    /**
     * get the business class loader
     * @return
     */
    public ClassLoader getBizClassLoader() {
        return bizClassLoader;
    }

    /**
     * check whether the code is running in browser.
     *  
     * @return
     */
    public boolean isRunningInBrowser() {
        return !isRunningInServer();
    }

    /**
     * set whether the code is running in server. it will inited by the client runtime environemnt
     * or the server runtime environemnt.
     * 
     * @param runningInServer
     */
    public void setRunningInServer(boolean runningInServer) {
        this.runingServer = new Boolean(runningInServer);
        System.setProperty("nc.run.side", runningInServer ? "server" : "client");
        System.setProperty("run.side", runningInServer ? "server" : "client");
    }

    public void setThreadRunningInServer(boolean runningInserver) {
        threadRunningInServer.set(new Boolean(runningInserver));
    }

    public boolean isThreadRunningInServer() {
        return ((Boolean) threadRunningInServer.get()).booleanValue();
    }

    /**
     * check wheterh the code is running in server
     * @return
     */
    public boolean isRunningInServer() {
        if (runingServer == null) {
            runingServer = new Boolean("server".equals(System.getProperty("nc.run.side")));
        }

        return runingServer.booleanValue();
    }

    /**
     * get the raw properties which store the runtime properties
     * @return
     */
    public Properties getArbitraryProperties() {
        return this.abitraryProperty;
    }

    /**
     * new a business object
     * 
     * @param bizClassName
     * @return
     */
    public Object newBizObject(String bizClassName) {

        ClassLoader loader = getBizClassLoader();
        try {
            Class clazz = loader.loadClass(bizClassName);
            return clazz.newInstance();

        } catch (ClassNotFoundException e) {
            Logger.error("Can't find the class", e);
            throw new FrameworkRuntimeException("Can't find the specified class: " + bizClassName, e);
        } catch (InstantiationException e) {
            Logger.error("Can't instantaite the object", e);
            throw new FrameworkRuntimeException("CCan't instantaite the specified object ", e);
        } catch (IllegalAccessException e) {
            Logger.error("Illegal argument excetion", e);
            throw new FrameworkRuntimeException("Illegal argument excetion ", e);

        }
    }

    /**
     * check wether the enviroment is weblogic
     *
     * @return
     */
    public static boolean isRunningInWeblogic() {
        return EnvironmentUtil.isRunningInWeblogic();

    }

    /**
     * check whether the enviroment is was
     *
     * @return
     */
    public static boolean isRunningInWebSphere() {
        return EnvironmentUtil.isRunningInWebSphere();
    }

    /**
     * store a named property in the runtime environment
     * 
     * @param name
     * @param value
     */
    public void setProperty(String name, String value) {
        abitraryProperty.put(name, value);
    }

    /**
     * get a property with name
     * 
     * @param name
     * @return
     */
    public String getProperty(String name) {
        return abitraryProperty.getProperty(name);
    }

    /**
     * get a propery with name, if not set return the defaultValue
     * @param name
     * @param defaultValue
     * @return
     */
    public String getProperty(String name, String defaultValue) {
        return abitraryProperty.getProperty(name, defaultValue);
    }

    /**
     * get the NC_HOME path
     * @return
     */
    public String getNCHome() {
        return getProperty(RuntimeEnv.SERVER_LOCATION_PROPERTY, System.getProperty("user.dir"));
    }

    public String getCanonicalNCHome() {
        String home = getNCHome();
        File f = new File(home);
        try {
            home = f.getCanonicalFile().getPath();
        } catch (IOException e) {

        }
        return home;
    }

    public String getModuleHome(String module) {
        String moduleHome = getProperty("MODULE_HOME/" + module);

        if (moduleHome == null)
            moduleHome = getNCHome() + "/modules/" + module;
        return moduleHome;
    }

    public void setModuleHome(String module, String home) {
        if (home != null) {
            File f = new File(home);
            try {
                home = f.getCanonicalFile().getPath();
            } catch (IOException e) {

            }
            setProperty("MODULE_HOME/" + module, home);
        }

    }

    public boolean isDevelopMode() {
        if (developmentMode == null) {
            String mode = System.getProperty("nc.runMode");
            if (mode == null) {
                if (!isRunningInServer()) {
                    String classNames[] = { "nc.bs.mw.fm.ServiceManager" };
                    for (int i = 0; i < classNames.length; i++) {
                        try {
                            Class.forName(classNames[i]);
                            developmentMode = new Boolean(true);
                            break;
                        } catch (Exception exp) {
                        }
                    }
                    if (developmentMode == null)
                        developmentMode = new Boolean(false);
                } else
                    developmentMode = new Boolean(false);
            } else {
                developmentMode = new Boolean("develop".equals(mode));
            }
        }
        return developmentMode.booleanValue();

    }

}