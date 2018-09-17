package com.xbkj.log.bs.logging.impl.log4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Hierarchy;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.net.SocketAppender;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggingEvent;

import com.xbkj.log.bs.logging.Level;
import com.xbkj.log.bs.logging.LogMessageBuffer;
import com.xbkj.log.bs.logging.LoggerGeneralConfig;
import com.xbkj.log.bs.logging.LoggerPluginProvider;
import com.xbkj.log.vo.logging.Debug;
import com.xbkj.log.vo.logging.LoggerStrategy;
import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
import com.xbkj.log.vo.logging.Util;

/**
 * @author �ι���
 *
 * Date: 2006-9-10
 * Time: ����10:53:43
 */
public class Log4jLogger extends Logger {

    private static final Hierarchy hierarchy;

    private static LoggerFactory loggerFactory = new Log4jLoggerFactory();

    //when the jvm exist it will close
    private static Appender[] ncLogAppender;

    public static final String fqcnLog4j = "nc.bs.logging.";

    private static Map<String, Appender> appenderMap = new ConcurrentHashMap<String, Appender>();

    static {

        Log4jLogger rootLogger = new Log4jLogger("anonymous");

        if (LoggerGeneralConfig.isRunningInServer()) {
            rootLogger.config(getAnonyLoggerConfig(), null);

        } else {

            ModuleLoggerConfiguration config = new ModuleLoggerConfiguration(LoggerGeneralConfig.ANONY_MODULE);

            if (config.getLogLevel() == null) {
                config.setLogLevel(LoggerGeneralConfig.DEFAULT_LEVEL);
            }

            if (config.getLogPattern() == null) {
                config.setLogPattern(LoggerGeneralConfig.DEFAULT_PATTERN);
            }

            if (config == null) {
                config = new ModuleLoggerConfiguration(LoggerGeneralConfig.ANONY_MODULE, null,
                        LoggerGeneralConfig.DEFAULT_PATTERN, LoggerGeneralConfig.LOG_TYPE_PUB, null, "DEBUG", null);
            }

            rootLogger.config(config, null);

        }

        if (!rootLogger.getAllAppenders().hasMoreElements()) {
            rootLogger.addAppender(new Log4jConsoleAppender(rootLogger.getEffectiveLayout(), "System.out"));
        }

        hierarchy = new Hierarchy(rootLogger);

        configErrorLogger();

        initLoggers();
    }

    private BufferAppender buffAppender;

    protected Layout layout;

    protected String moduleName;

    protected String logType;

    protected Level myLevel;

    private List<Appender> list = new ArrayList<Appender>();

    protected Log4jLogger(String name) {
        super(name);
    }

    @Override
    public void callAppenders(LoggingEvent event) {
        callAppenders(event, layout);
    }

    public synchronized void setLayout(Layout layout) {
        this.layout = layout;
    }

    public synchronized Layout getLayout() {
        return layout;
    }

    public void delegateAppenders(LoggingEvent event, Layout layout) {
        String formatedMessage = null;
        ArrayList<Appender> tempList = new ArrayList<Appender>();
        for (Category c = this; c != null; c = c.getParent()) {
            tempList.clear();
            synchronized (c) {
                Enumeration enumAppenders = c.getAllAppenders();
                while (enumAppenders.hasMoreElements()) {
                    tempList.add((Appender) enumAppenders.nextElement());
                }
            }

            int size = tempList.size();
            if (formatedMessage == null && size > 0 && ((Log4jLogger) c).layout != null) {
                formatedMessage = ((Log4jLogger) c).layout.format(event);
            }

            for (int i = 0; i < size; i++) {

                Appender appender = tempList.get(i);

                if (appender instanceof Log4jLayoutAppender && formatedMessage != null) {
                    ((Log4jLayoutAppender) appender).doAppend(formatedMessage);
                } else {
                    appender.doAppend(event);
                }
            }
            if (!c.getAdditivity()) {
                break;
            }
        }

    }

    public void callAppenders(LoggingEvent event, Layout layout) {
        if (LoggerGeneralConfig.isRunningInServer()) {
            for (Log4jLogger c = this; c != null; c = (Log4jLogger) c.parent) {
                if (c.buffAppender != null) {
                    c.buffAppender.doAppend(event);
                    break;
                }
            }
        }

        if (ncLogAppender != null) {
            for (int i = 0; i < ncLogAppender.length; i++) {
                ncLogAppender[i].doAppend(event);
            }
        }

        delegateAppenders(event, layout);

    }

    public synchronized void addAppender(Appender newAppender) {
        list.add(newAppender);
        setAdditivity(false);
    }

    public synchronized void removeAllAppenders() {
        list.clear();
        setAdditivity(true);
    }

    public synchronized Set<Appender> getCurrentAppenders() {
        HashSet<Appender> set = new HashSet<Appender>();
        set.addAll(list);
        return set;
    }

    public synchronized void shutdownAllAppenders() {
        for (int i = 0; i < list.size(); i++) {
            Appender appender = list.get(i);
            appender.close();
        }
        list.clear();
        setAdditivity(true);
    }

    public synchronized void removeAppender(Appender appender) {
        list.remove(appender);
    }

    public synchronized void removeAppender(String name) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (name.equals((list.get(i)).getName())) {
                list.remove(i);
                break;
            }
        }
    }

    synchronized public Enumeration getAllAppenders() {
        final Iterator itr = list.iterator();
        return new Enumeration() {
            public boolean hasMoreElements() {
                return itr.hasNext();
            }

            public Object nextElement() {
                return itr.next();
            }
        };
    }

    public synchronized Appender getAppender(String name) {
        int size = list.size();
        Appender appender;
        for (int i = 0; i < size; i++) {
            appender = list.get(i);
            if (name.equals(appender.getName()))
                return appender;
        }
        return null;
    }

    public synchronized void setModuleName(String module) {
        if (LoggerGeneralConfig.isRunningInServer()) {

            LogMessageBuffer buff = LogMessageBuffer.getInternalLogMessageBuffer(module);
            if (buff == null) {
                buffAppender = new BufferAppender();
                LogMessageBuffer.addLogMessageBuffer(module, buffAppender.getLogMessageBuffer());
            } else {
                buffAppender = new BufferAppender(buff);
            }
            //init errorAppender
        }

        this.moduleName = module;
    }

    public synchronized String getModuleName() {
        return moduleName;
    }

    public synchronized String getEffectiveModuleName() {
        for (Log4jLogger c = this; c != null; c = (Log4jLogger) c.parent) {
            if (c.moduleName != null)
                return c.moduleName;
        }

        return null;
    }

    protected void setParent(Log4jLogger log) {
        this.parent = log;
    }

    protected Layout getEffectiveLayout() {
        for (Log4jLogger c = this; c != null; c = (Log4jLogger) c.parent) {
            if (c.layout != null)
                return c.layout;
        }
        return null;
    }

    public void config(ModuleLoggerConfiguration config, Log4jLogger parent) {
        Log4jLogger myParent = (Log4jLogger) parent;
        //1. no any configuration
        if (config == null) {
            if (parent != null) {
                this.parent = myParent;
            } else {
                return;//use default log4j
            }
        } else {
            //has the exact configuration
            if (config.getModuleName().equals(name)) {
                if (parent != null) {
                    //has the parent(prefix) configuration
                    if (!name.startsWith(myParent.name)) {
                        this.parent = myParent;
                    } //default log4j   
                }

                //overide the inherited attributes
                String aLevel = config.getLogLevel();
                String aPattern = config.getLogPattern();

                if (!isEmpty(aLevel)) {
                    setLevel(Level.getLevel(aLevel));
                }

                if (!isEmpty(aPattern)) {
                    setLayout(new Log4jPatternFormatter(aPattern));
                }

                removeAllAppenders();

                Appender[] appenders = createAppenderByConfig(this, config, getEffectiveLayout());

                if (appenders.length > 0) {
                    for (int i = 0; i < appenders.length; i++) {
                        addAppender(appenders[i]);
                    }
                } else {
                    if (!LoggerGeneralConfig.isRunningInServer() || "anonymous".equals(config.getModuleName()))
                        this.addAppender(new Log4jConsoleAppender(this.getEffectiveLayout(), "System.out"));
                }

            } else {
                //assert parent mustn't be null
                if (!name.startsWith(myParent.name))
                    this.parent = myParent;
            }
        }

        this.logType = config.getLogType();
        setModuleName(config.getModuleName());
    }

    public void setLevel(Level level) {
        myLevel = level;
        this.setLevel(XLevel.log4jLevel(level));

    }

    private static void initLoggers() {
        if (LoggerGeneralConfig.isRunningInServer()) {
            ModuleLoggerConfiguration[] configs = LoggerPluginProvider.getLoggerConfigManager()
                    .getModuleLoggerConfigurations();
            for (int i = 0; i < configs.length; i++) {

                if (LoggerGeneralConfig.ANONY_MODULE.equals(configs[i].getModuleName())
                        || LoggerGeneralConfig.ANONY_MODULE.equals(configs[i].getModuleName())) {
                    continue;
                } else {
                    LoggerPluginProvider.getInstance().getLoggerPlugin(configs[i].getModuleName());
                    String prefix = configs[i].getPackagePrefix();
                    List aList = Util.stringToList(prefix, ";");

                    for (Iterator iter = aList.iterator(); iter.hasNext();) {
                        LoggerPluginProvider.getInstance().getLoggerPlugin((String) iter.next());
                    }
                }
            }
        }
    }

    private static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;
        else {
            if (obj instanceof String) {
                String str = ((String) obj).trim();
                return "".equals(str);
            }
            return false;
        }

    }

//    @Override
//    public org.apache.log4j.Level getEffectiveLevel() {
//        for (Category c = this; c != null; c = c.getParent()) {
//            org.apache.log4j.Level level = null;
//            synchronized (c) {
//                level = c.getLevel();
//            }
//            if (level != null)
//                return level;
//        }
//        return null; // If reached will cause an NullPointerException.
//    }

    @Override
    protected void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
        callAppenders(new Log4jLoggingEvent(fqcn, this, System.currentTimeMillis(), level, message, t, moduleName,
                logType));
    }

    protected void forcedLog(String fqcn, Priority level, Object message, Throwable t, String clazz,
            String methodName) {
        callAppenders(new Log4jLoggingEvent(fqcn, this, System.currentTimeMillis(), level, message, t, moduleName,
                logType, clazz, methodName));
    }

    public void log(String callerFQCN, Priority level, Object message, Throwable t, String clazz, String methodName) {
        if (repository.isDisabled(level.toInt())) {
            return;
        }
        if (level.isGreaterOrEqual(this.getEffectiveLevel())) {
            forcedLog(callerFQCN, level, message, t, clazz, methodName);
        }
    }

    private static Appender[] createAppenderByConfig(Log4jLogger log, ModuleLoggerConfiguration config,
            Layout layout) {
        List<Appender> appenderList = new ArrayList<Appender>(2);
        LoggerStrategy strategy = config.getStrategy();
        String logFile = config.getLogFile();
        Appender appender = null;
        if (!isEmpty(logFile)) {
            try {
                appender = appenderMap.get(logFile);
                if (appender == null) {
                    String filePath = Util.makePath(logFile);
                    if (strategy == null || strategy.getType() == LoggerStrategy.NORMAL_TYPE) {
                        appender = new NormalFileAppender(layout, filePath, true);
                    } else if (strategy.getType() == LoggerStrategy.TIME_TYPE) {
                        appender = new TimeRollingFileAppender(layout, filePath, (String) strategy
                                .getAttribute("datePattern"));
                    } else if (strategy.getType() == LoggerStrategy.SIZE_TYPE) {
                    	File file = new File(filePath);
                    	if (file.exists()) {
                    		file.delete();
                    	}
                        appender = new SizeRollingFileAppender(layout, filePath);
                        ((SizeRollingFileAppender) appender).setMaxFileSize((String) strategy
                                .getAttribute("maxSize"));
                        ((SizeRollingFileAppender) appender).setMaxBackupIndex(Integer.parseInt((String) strategy
                                .getAttribute("maxIndex")));
                    }
                }
                appenderMap.put(logFile, appender);
            } catch (Throwable e) {
                Debug.debug("Log4jLoggerPlugin,�������ô���Appender�����쳣,�������������õ�ԭ��");
            }

        } else if (log != null && config.getOutModule() != null
                && !config.getOutModule().equals(config.getModuleName())) {
            appender = new LoggerDelegate(log, config.getOutModule());

        }

        if (appender != null)
            appenderList.add(appender);

        if (config.getSocketConfig() != null) {
            String sc = config.getSocketConfig();

            int indexSep = sc.indexOf(":");
            String host = sc;
            int port = 4560;
            if (indexSep > 0) {
                host = sc.substring(0, indexSep);
                if (indexSep < (sc.length() - 1)) {
                    try {
                        port = Integer.valueOf(sc.substring(indexSep + 1)).intValue();
                    } catch (Throwable thr) {
                        System.err.println("Warning: the socket port config error, try the default socket: 4560");
                    }
                }
            }

            String entryKey = host + ":" + port;
            //System.out.println(entryKey);
            appender = (Appender) appenderMap.get(entryKey);
            if (appender == null) {
                synchronized (appenderMap) {
                    appender = (Appender) appenderMap.get(entryKey);
                    if (appender == null) {
                        appender = new SocketAppender(host, port);
                        appenderMap.put(host + ":" + port, appender);
                    }
                }
            }

            appenderList.add(appender);
        }

        return (Appender[]) appenderList.toArray(new Appender[0]);
    }

    private static void configErrorLogger() {
        if (!LoggerGeneralConfig.isRunningInServer()) {
            return;
        }

        configErrorLogger(getErrorLoggerConfig());
    }

    private static ModuleLoggerConfiguration getErrorLoggerConfig() {
        ModuleLoggerConfiguration config = LoggerPluginProvider.getLoggerConfigManager()
                .getModuleLoggerConfiguration(LoggerGeneralConfig.NCLOG_MODULE);

        if (config == null) {
            return defaultErrorLoggerConfig();
        }
        
        //nclog can't config level
        config.setLogLevel("ERROR");

        return config;
    }

    private static ModuleLoggerConfiguration getAnonyLoggerConfig() {
        ModuleLoggerConfiguration config = LoggerPluginProvider.getLoggerConfigManager()
                .getModuleLoggerConfiguration(LoggerGeneralConfig.ANONY_MODULE);

        if (config == null) {
            config = new ModuleLoggerConfiguration(LoggerGeneralConfig.ANONY_MODULE);
        }

        if (config.getLogLevel() == null) {
            config.setLogLevel(LoggerGeneralConfig.DEFAULT_LEVEL);
        }

        if (config.getLogPattern() == null) {
            config.setLogPattern(LoggerGeneralConfig.DEFAULT_PATTERN);
        }

        if (config == null) {
            config = new ModuleLoggerConfiguration(LoggerGeneralConfig.ANONY_MODULE, null,
                    LoggerGeneralConfig.DEFAULT_PATTERN, LoggerGeneralConfig.LOG_TYPE_PUB, null, "DEBUG", null);
        }

        LoggerPluginProvider.getLoggerConfigManager().addConfig(config);

        return config;
    }

    /**
     * ���NC��׼��Ĭ�����
     *
     * @return
     */
    private static ModuleLoggerConfiguration defaultErrorLoggerConfig() {
        LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.SIZE_TYPE);
        strategy.setAttribute("maxSize", "4MB");
        strategy.setAttribute("maxIndex", "10");
        ModuleLoggerConfiguration errorConfig = new ModuleLoggerConfiguration(LoggerGeneralConfig.NCLOG_MODULE,
                null, LoggerGeneralConfig.DEFAULT_PATTERN, LoggerGeneralConfig.LOG_TYPE_PUB,
                "./nclogs/${server}/nc-log.log", "DEBUG", strategy);
        return errorConfig;
    }

    public static void configErrorLogger(final ModuleLoggerConfiguration config) {
        if (!LoggerGeneralConfig.isRunningInServer()) {
            return;
        }

        Appender[] newAppenders;
        final ModuleLoggerConfiguration errorConfig = config;

        if (isEmpty(errorConfig.getLogLevel())) {
            errorConfig.setLogLevel(LoggerGeneralConfig.DEFAULT_LEVEL);
        }

        if (isEmpty(errorConfig.getLogPattern())) {
            errorConfig.setLogPattern(LoggerGeneralConfig.DEFAULT_PATTERN);
        }

        if (errorConfig.getLogFile() == null) {
            LoggerStrategy strategy = new LoggerStrategy(LoggerStrategy.SIZE_TYPE);
            strategy.setAttribute("maxSize", "4MB");
            strategy.setAttribute("maxIndex", "10");
            errorConfig.setStrategy(strategy);
            errorConfig.setLogFile("./nclogs/${server}/nc-log.log");
        }

        Layout layout = new Log4jPatternFormatter(errorConfig.getLogPattern());

        newAppenders = createAppenderByConfig(null, errorConfig, layout);

        for (int i = 0; i < newAppenders.length; i++) {
            newAppenders[i].setName(LoggerGeneralConfig.NCLOG_MODULE + i);
            newAppenders[i].clearFilters();
            newAppenders[i].addFilter(new org.apache.log4j.spi.Filter() {
                public int decide(LoggingEvent event) {
                    if (event.getLevel().toInt() < org.apache.log4j.Level.toLevel(errorConfig.getLogLevel())
                            .toInt()) {
                        return org.apache.log4j.spi.Filter.DENY;
                    }
                    return org.apache.log4j.spi.Filter.ACCEPT;
                }
            });
        }

        synchronized (hierarchy) {
            ncLogAppender = newAppenders;
        }

    }

    public static Log4jLogger getLog4jLogger(String name) {
        if ("anonymous".equals(name)) {
            return (Log4jLogger) hierarchy.getRootLogger();
        } else {
            return (Log4jLogger) hierarchy.getLogger(name, loggerFactory);
        }
    }
  	/**
  	 * TODO: NEED HGY TO AUDIT
  	 */
  	public org.apache.log4j.Level getEffectiveLevel() {
  		if (com.grc.log.bs.logging.Logger.getUserLevel() != null) {
  			return XLevel.toLevel(com.grc.log.bs.logging.Logger.getUserLevel());
  		} else {
  			return super.getEffectiveLevel();
  		} 
  	}
}
