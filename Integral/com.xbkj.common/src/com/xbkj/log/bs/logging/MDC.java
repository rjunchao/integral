package com.xbkj.log.bs.logging;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * MDCΪ�̼߳���Ķ�̬��Ϣ���ƣ�һ��ģ���Ӧһ��
 */
public interface MDC {

    /**
     * ��һ����Key��־����Ϣ�����߳��������У���־ϵͳ���Զ�̬��
     * ��־�����Ϣ��ʵ����־?
     * <p/>
     * ̬��Ϣ��Ŀ��(MDC)
     *
     * @param key
     * @param msg
     */
    public void put(String key, Object msg);

    /**
     * ��ȡһ��ֵ
     *
     * @param key
     * @return Object
     */
    public Object get(String key);

    /**
     * ���߳���������ɾ��һ����keyָ���Ķ�̬��Ϣ(MDC)
     *
     * @param key
     */
    public void remove(String key);

    /**
     * ��λ
     */
    public void reset();
    
}
