package com.xbkj.log.bs.logging.impl.log4j;

import com.xbkj.log.bs.logging.NDC;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: ${DATE}$
 * Time: ${TIME}$
 * <p/>
 * <p/>
 * ����log4j
 */
public class Log4jNDC implements NDC {

    public static final Log4jNDC ndc = new Log4jNDC();

    private Log4jNDC() {

    }


    /**
     * ��һ����Ϣѹ���߳������ĵ�һ����ջ(NDC),ʵ��Logջ��̬��Ϣ��
     */
    public void push(Object msg) {
        org.apache.log4j.NDC.push(msg.toString());

    }

    /**
     * ��һ����Ϣ�����߳������ĵ�һ����ջ(NDC),��push��Ӧ
     *
     * @return Object
     */
    public Object pop() {
        return org.apache.log4j.NDC.pop();
    }

    /**
     * ��λ
     */
    public void reset() {
        org.apache.log4j.NDC.clear();
    }

    /**
     * �쿴���˵���Ϣ
     *
     * @return
     */
    public Object get() {
        return org.apache.log4j.NDC.get();
    }

    /**
     * ��������NDC�����
     *
     * @param depth
     */
    public void setDepth(int depth) {
        org.apache.log4j.NDC.setMaxDepth(depth);
    }
}
