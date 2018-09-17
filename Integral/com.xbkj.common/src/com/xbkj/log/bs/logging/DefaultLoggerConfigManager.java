package com.xbkj.log.bs.logging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;
import com.xbkj.log.vo.logging.Util;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2004-11-10
 * Time: 15:20:24
 * <p/>
 * �ڴ����õ�LoggerConfigManagerʵ��
 */
public class DefaultLoggerConfigManager implements LoggerConfigManager {
    
    protected Map<String, ModuleLoggerConfiguration> configurationMap = new ConcurrentHashMap<String, ModuleLoggerConfiguration>();

    protected Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    //only for performance
    protected Map<String, String> modulePackageMap = new ConcurrentHashMap<String, String>();

    public DefaultLoggerConfigManager() {

    }

    /**
     * �ٴ�load,�̰߳�ȫ
     */
    public  void loadConfig() {
        configurationMap.clear();
        modulePackageMap.clear();
    }

    public  ModuleLoggerConfiguration[] getModuleLoggerConfigurations() {
        ModuleLoggerConfiguration[] configs = new ModuleLoggerConfiguration[configurationMap.values().size()];

        configurationMap.values().toArray(configs);
        return configs;
    }

    /**
     * ��ȡ�������ԣ��̰߳�ȫ
     * @param module
     * @return ModuleLoggerConfiguration
     */
    public  ModuleLoggerConfiguration getModuleLoggerConfiguration(String module) {
        String moduleName = getModuleNameByPackage(module);
        if(moduleName == null)
            return null;
       return configurationMap.get(moduleName);
    }

    /**
     * ����������ԣ��̰߳�ȫ
     * @param config
     */
    public  void addConfig(ModuleLoggerConfiguration config) {
        configurationMap.put(config.getModuleName(), config);
        clearOldConfig(config.getModuleName());
        updatePackageModuleMap(config);

    }

    protected void clearOldConfig(String moduleName) {
        if (moduleName == null)
            return;
        ArrayList<String> keyList = new ArrayList<String>();
        Iterator itr = modulePackageMap.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            if (moduleName.equals(modulePackageMap.get(key))) {
                keyList.add(key);
            }
        }
        for (int i = 0; i < keyList.size(); i++) {
            modulePackageMap.remove(keyList.get(i));
        }

    }

    protected void updatePackageModuleMap(ModuleLoggerConfiguration config) {
        if (config.getModuleName() == null)
            return;
        String prefix = config.getPackagePrefix();
        modulePackageMap.put(config.getModuleName(), config.getModuleName());

        if (prefix != null) {
            List aList = Util.stringToList(prefix, ";");
            for (int j = 0; j < aList.size(); j++) {
                modulePackageMap.put((String)aList.get(j), config.getModuleName());
            }
        }

    }

    /**
     * ɾ���������ԣ��̰߳�ȫ
     * @param module
     */
    public  void removeConfig(String module) {
        
        ModuleLoggerConfiguration config = (ModuleLoggerConfiguration) configurationMap.remove(module);
        if(config != null)
            clearOldConfig(config.getModuleName());

    }

    public void clearConfigs() {
        configurationMap.clear();
        modulePackageMap.clear();
    }

    /**
     * ���������ļ�,�̰߳�ȫ
     */
    public synchronized void saveConfig() {

    }

    /**
     * ��ȡ�������ԣ��̰߳�ȫ
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * ���û������ԣ��̰߳�ȫ
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }


    public String getModuleNameByPackage(String packageName) {
        if (LoggerGeneralConfig.NCLOG_MODULE.equals(packageName)) {
            return LoggerGeneralConfig.NCLOG_MODULE;
        }
        String moduleName = modulePackageMap.get(packageName);
        
        return moduleName;
    }

}