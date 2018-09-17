package com.xbkj.log.bs.logging;

import com.xbkj.log.vo.logging.Util;


public class LoggerGeneralConfig {
    public final static String ANONY_MODULE = "anonymous";
    public final static String NCLOG_MODULE = "nclog";
    public final static String LINE_SEP = System.getProperty("line.separator");
    public final static String DEFAULT_LEVEL_PROPERTY = "nc.bs.logging.default.level";
    public final static String DEFAULT_PATTERN_PROPERTY = "nc.bs.logging.default.pattern";
    public static final String LOG_FORMAT_PROPERTY = "nc.bs.logging.format";

    public final static String TEXT_FORMAT = "text";
    public final static String XML_FORMAT = "xml";
    
    public static String DEFAULT_LEVEL = Util.getSystemProperty(DEFAULT_LEVEL_PROPERTY, "DEBUG");
    public static String DEFAULT_PATTERN = Util.getSystemProperty(DEFAULT_PATTERN_PROPERTY, "[%t] %d{yyyy/MM/dd HH:mm:ss} [%A] %p  - %m %n");
    public static String LOG_FORMAT = Util.getSystemProperty(LOG_FORMAT_PROPERTY, XML_FORMAT);
    
    public static final Object flagObject = new Object();
    public static final String LOG_TYPE_PUB = "Pub";
    public static final String LOG_TYPE_MW = "Mw";
    public static final String LOG_TYPE_SQL = "SQL";
    public static final String MODULE_CONFIG_PROPERTY = "nc.bs.logging.loggerConfig";
    
    public static final String MODULE_CONFIG_DEFAULT = Util.getSystemProperty(MODULE_CONFIG_PROPERTY, "./ierp/bin/logger-config.properties");
    
    private static Boolean runningServer;
    
    public static boolean isFormatAsXml() {
        return XML_FORMAT.equals(TEXT_FORMAT);
    }
    
    public static boolean isRunningInServer() {
        if (runningServer == null) {
            String runSide = System.getProperty("nc.run.side");
            if (runSide == null) {
                String classNames[] = { "nc.bs.mw.start.NCStarter",
                        "com.ibm.websphere.naming.WsnInitialContextFactory",
                        "weblogic.jndi.WLInitialContextFactory" };
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                for (int i = 0; i < classNames.length; i++) {
                    try {
                        loader.loadClass(classNames[i]);
                        runningServer = new Boolean(true);
                        System.setProperty("nc.run.side", "server");
                        break;
                    } catch (Exception exp) {
                    }
                }
                if (runningServer == null) {
                    runningServer = new Boolean(false);
                    System.setProperty("nc.run.side", "client");
                }
            } else {
                runningServer = new Boolean("server".equals(runSide));
                System.setProperty("nc.run.side", runningServer.booleanValue() ? "server" : "client");
            }
        }
        return runningServer.booleanValue();
    }

    public static final String BEGIN_REMOVE_EXCEPTION_TRACE = "at javax.servlet.GenericServlet";
    
    public static final String BEGIN_REMOVE_EXCEPTION_TRACE1 = "at javax.servlet.http.HttpServlet";
    public static final String END_REMOVE_EXCEPTION_TRACE = "Caused by:";
    public static final boolean IGNORE_MW_TRACE = "true".equals(System.getProperty("nc.ignore_mw_trace", "true"));
}
