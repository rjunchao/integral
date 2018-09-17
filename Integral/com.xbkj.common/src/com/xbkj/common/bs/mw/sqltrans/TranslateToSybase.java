package com.xbkj.common.bs.mw.sqltrans;

import com.xbkj.log.bs.logging.Logger;

/**
 * ģ��: TranslateToSybase.java 
 * ����: ��SqlServer��䷭�뵽Sybase���
 *  
 */

public class TranslateToSybase extends TranslatorObject {
    /**
     * TranslateToSybase ������ע�͡�
     */
    public TranslateToSybase() {
        super(SYBASE);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSybase Over");
    }
}