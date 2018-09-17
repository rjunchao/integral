package com.xbkj.log.bs.logging;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 *
 * MDCΪ�̼߳���Ķ�̬ջ��Ϣ���ƣ�һ��ģ���Ӧһ��
 */
public interface NDC {

    /**
     * ��һ����Ϣѹ���߳������ĵ�һ����ջ(NDC),ʵ��Logջ��̬��Ϣ��
     *
     */
    public void push(Object msg);

    /**
     * ��һ����Ϣ�����߳������ĵ�һ����ջ(NDC),��push��Ӧ
     *
     * @return Object
     */
    public Object pop();

    /**
     * ��λ
     */
    public void reset();

    /**
     * �쿴���˵���Ϣ
     * @return
     */
    public Object get();

    /**
     * ��������NDC�����
     * @param depth
     */
    public void setDepth(int depth);
}
