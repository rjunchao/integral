package com.xbkj.common.bs.mw.sqltrans;

import java.util.Vector;

import com.xbkj.log.bs.logging.Logger;

/**
 * �˴���������˵���� 
 * ���ܣ����һ�����ʽ 
 * �������ڣ�(2001-3-15 19:17:17)
 * 
 * @author���� ɭ
 */
public class Expresion_str {
    Expresion_str left;//����ߵĳ�������

    Expresion_str right;//���ұߵĳ�������

    char operatie;//������

    Vector valoare;//���һ������

    //String valoarestr=null;//���һ������
    /**
     * Expresion ������ע�⡣
     */
    public Expresion_str() {
        super();
        Logger.setThreadState("nc.bs.mw.sqltrans.Expresion_str Over");
    }
}