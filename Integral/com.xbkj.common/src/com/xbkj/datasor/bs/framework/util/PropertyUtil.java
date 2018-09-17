package com.xbkj.datasor.bs.framework.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 * 
 * The utility class help access the system property
 */
public class PropertyUtil {

    /**
     * get the system property with name, if not set return the default value
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getSystemProperty(final String name, final String defaultValue) {
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty(name, defaultValue);
            }
        });
    }

    /**
     * get the system property with name
     * 
     * @param name
     * @return
     */
    public static String getSystemProperty(final String name) {
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.getProperty(name);
            }
        });
    }

    /**
     * set the system named property with value
     * 
     * @param name
     * @param value
     */
    public static String setSystemProperty(final String name, final String value) {
        return AccessController.doPrivileged(new PrivilegedAction<String>() {
            public String run() {
                return System.setProperty(name, value);
            }
        });
    }

    /**
     * get the raw system properties
     * @return
     */
    public static Properties getSytemProperties() {
        return AccessController.doPrivileged(new PrivilegedAction<Properties>() {
            public Properties run() {
                return System.getProperties();
            }
        });
    }

}
