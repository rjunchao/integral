package com.xbkj.log.bs.logging;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * ����log4j,����Log4j��MDC
 */
public class LoggerMDC implements MDC {

    public static LoggerMDC mdc = new LoggerMDC();

    static ThreadLocal<Map<String, Object>> mappedThreadState = new ThreadLocal<Map<String, Object>>() {
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    private LoggerMDC() {
    }

    /**
     * ��һ����Key��־����Ϣ�����߳��������У���־ϵͳ���Զ�̬��
     * ��־�����Ϣ��ʵ����־?
     * <p/>
     * ̬��Ϣ��Ŀ��(MDC)
     *
     * @param key
     * @param msg
     */
    public void put(String key, Object msg) {
        mappedThreadState.get().put(key, msg);
    }

    /**
     * ��ȡһ��ֵ
     *
     * @param key
     * @return Object
     */
    public Object get(String key) {
        return mappedThreadState.get().get(key);
    }

    /**
     * ���߳���������ɾ��һ����keyָ���Ķ�̬��Ϣ(MDC)
     *
     * @param key
     */
    public void remove(String key) {
        mappedThreadState.get().remove(key);
    }

    /**
     * ��λ
     */
    public void reset() {
        mappedThreadState.get().clear();
    }

   
}
