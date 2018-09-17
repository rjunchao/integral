package com.xbkj.log.bs.logging;

import com.xbkj.log.vo.logging.ModuleLoggerConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 *
 * <p/>
 *
 * ��������������Ϣ
 */
public interface LoggerConfigManager {

    /**
     * ����������Ϣ
     */
    public void loadConfig();

    
    public ModuleLoggerConfiguration[] getModuleLoggerConfigurations();

    /**
     * @param module
     * @return ModuleLoggerConfiguration
     */
    public ModuleLoggerConfiguration getModuleLoggerConfiguration(String module);

    /**
     * Ӧ��������Ϣ
     *
     * @param config
     */
    public void addConfig(ModuleLoggerConfiguration config);

    /**
     * ȡ��������Ϣ
     *
     * @param module
     */
    public void removeConfig(String module);

    /**
     * �������
     */
    public void clearConfigs();

    /**
     * ����������Ϣ
     */
    public void saveConfig();
    
    

    /**
     * ���ø���������
     *
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value);

    /**
     * ����������
     * @param key
     * @return
     */
    public Object getAttribute(String key);

}
