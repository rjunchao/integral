package com.xbkj.log.vo.logging;

import java.io.Serializable;

import com.xbkj.log.bs.logging.LoggerGeneralConfig;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * ģ����־��������Ϣ
 */
public class ModuleLoggerConfiguration implements Serializable, Cloneable {
    
    private static final long serialVersionUID = -1926709615450213244L;

    /**
     * ģ�����
     */
    private String moduleName;

    /**
     * ģ���Ӧ�İ�ǰ׺
     */
    private String packagePrefix;

    /**
     * ��־�ĸ�ʽ
     */
    private String logPattern;

    /**
     * ��־������
     */
    private String logType;

    /**
     * ��־����ļ�
     */
    private String logFile;

    /**
     * ��־����
     */
    private String logLevel;

    private String inherited;

    private String socketConfig;
    
    private transient boolean needSave = true;

    public String getOutModule() {
        return outModule;
    }

    public void setOutModule(String outModule) {
        this.outModule = outModule;
    }

    private String outModule;

    /**
     * ������������Ϣ����Ӧ���ӵ���������
     */
    private String additionConfigInfo;

    /**
     * �ļ������Ĳ���
     */
    private LoggerStrategy strategy;

    public ModuleLoggerConfiguration(String moduleName) {
        this.moduleName = moduleName;

    }

    /**
     * ���캯�������еĹ�����Ϣ
     *
     * @param moduleName
     * @param packagePrefix
     * @param logPattern
     * @param logFile
     * @param logLevel
     * @param stategy
     */
    public ModuleLoggerConfiguration(String moduleName, String packagePrefix, String logPattern, String logType,
            String logFile, String logLevel, LoggerStrategy stategy) {
        setModuleName(moduleName);
        setPackagePrefix(packagePrefix);
        setLogType(logType);
        setLogFile(logFile);
        setLogLevel(logLevel);
        setLogPattern(logPattern);

        setStrategy(stategy);
        this.inherited = "true";
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }

    public String getLogPattern() {
        return logPattern;
        //return logPatternToString(logPattern);
    }

    public void setLogPattern(String logPattern) {
        this.logPattern = logPattern;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getAdditionConfigInfo() {
        return additionConfigInfo;
    }

    public void setAdditionConfigInfo(String additionConfigInfo) {
        this.additionConfigInfo = additionConfigInfo;
    }

    public LoggerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(LoggerStrategy strategy) {
        this.strategy = strategy;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (packagePrefix != null)
            sb.append(moduleName).append(".").append("prefix").append("=").append(packagePrefix).append("\n");
        if (logLevel != null)
            sb.append(moduleName).append(".").append("level").append("=").append(logLevel).append("\n");
        if (logPattern != null)
            sb.append(moduleName).append(".").append("pattern").append("=").append(logPattern).append("\n");
        if (logType != null)
            sb.append(moduleName).append(".").append("type").append("=").append(logType).append("\n");
        if (logFile != null) {
            sb.append(moduleName).append(".").append("file").append("=").append(logFile).append("\n");
            if (strategy != null) {
                if (strategy.getType() == LoggerStrategy.TIME_TYPE) {

                    sb.append(moduleName).append(".").append("time").append("=").append(
                            strategy.getAttribute("datePattern")).append("\n");
                } else if (strategy.getType() == LoggerStrategy.SIZE_TYPE) {

                    sb.append(moduleName).append(".").append("size").append(".").append("maxSize").append("=")
                            .append(strategy.getAttribute("maxSize")).append("\n");
                    sb.append(moduleName).append(".").append("size").append(".").append("maxIndex").append("=")
                            .append(strategy.getAttribute("maxIndex")).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        boolean equals = false;
        if (o == this)
            equals = true;
        else if (o instanceof ModuleLoggerConfiguration) {
            ModuleLoggerConfiguration other = (ModuleLoggerConfiguration) o;
            equals = getModuleName() == null ? other.getModuleName() == null : getModuleName().equals(
                    other.getModuleName());
            equals = equals
                    && (getLogFile() == null ? other.getLogFile() == null : getLogFile().equals(other.getLogFile()));
            equals = equals
                    && (getLogLevel() == null ? other.getLogLevel() == null : getLogLevel().equals(
                            other.getLogLevel()));
            equals = equals
                    && (getLogPattern() == null ? other.getLogPattern() == null : getLogPattern().equals(
                            other.getLogPattern()));
            equals = equals
                    && (getLogType() == null ? other.getLogType() == null : getLogType().equals(other.getLogType()));
            equals = equals
                    && (getPackagePrefix() == null ? other.getPackagePrefix() == null : getPackagePrefix().equals(
                            other.getPackagePrefix()));
            equals = equals
                    && (getAdditionConfigInfo() == null ? other.getAdditionConfigInfo() == null
                            : getAdditionConfigInfo().equals(other.getAdditionConfigInfo()));
            equals = equals
                    && (getStrategy() == null ? ((other.getStrategy() == null || LoggerStrategy.NORMAL_TYPE == other
                            .getStrategy().getType()))
                            : getStrategy().equals(other.getStrategy()));
        }
        return equals;
    }

    public String getLogType() {
        if (logType == null)
            return LoggerGeneralConfig.LOG_TYPE_PUB;
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getInherited() {
        return inherited;
    }

    public void setInherited(String inherited) {
        this.inherited = inherited;
    }

    public void inheriteDefBehavior() {
        if (isEmpty(logLevel))
            logLevel = LoggerGeneralConfig.DEFAULT_LEVEL;
        if (isEmpty(logPattern))
            logPattern = LoggerGeneralConfig.DEFAULT_PATTERN;

        if (isEmpty(logType))
            logType = LoggerGeneralConfig.LOG_TYPE_PUB;

        if (isEmpty(inherited))
            inherited = "true";

    }

    public void ineritedBehavior(ModuleLoggerConfiguration config) {
        if (isEmpty(logLevel))
            logLevel = config.logLevel;
        if (isEmpty(logPattern))
            logPattern = config.logPattern;
        if (isEmpty(logType))
            logType = config.logType;

        if (isEmpty(logFile))
            logFile = config.logFile;

        if (isEmpty(strategy))
            strategy = config.strategy;

    }

    private boolean isEmpty(Object obj) {
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
    
    public boolean isNeedSave() {
        return this.needSave;
    }
    
    public void setNeedSave(boolean needSave) {
        this.needSave = needSave;
    }

    public String getSocketConfig() {
        return socketConfig;
    }

    public void setSocketConfig(String sc) {
        socketConfig = sc;
    }
}