package com.xbkj.common.bs.mw.sqltrans;

import com.xbkj.log.bs.logging.Logger;

/**
 *ģ��:	TranslateToSqlServer.java
 *����:	��������䷭�뵽SqlServer���
 *����:	cf
 */

public class TranslateToSqlServer extends TranslatorObject {
    /**
     * TranslateToSqlServer ������ע�͡�
     */
    public TranslateToSqlServer() {
        super(SQLSERVER);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer Over");
    }

    public String getSql() throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.getSql");

        translateSql();

        if (m_sbDestinationSql == null)
            return null;

        String sResult = m_sbDestinationSql.toString();

        //���������ԷֺŽ�β����ȥ���ֺ�
        if (sResult.endsWith(";")) ////��ɭ 2001.3.17 ��
            sResult = sResult.substring(0, sResult.length() - 1);

        /*//��ɭ 2001.3.17 ע�͵�
         if (!sResult.endsWith(";"))//Ϊʲô�ӷֺţ�
         {
         sResult += ";";
         }
         */
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.getSql Over");
        return sResult;
    }

    /**
     * ת��"||"
     * ����:
     * iOff ƫ����
     * ����:
     * ƫ����
     * ����:
     * || -> +
     */
    int translateII(int iOff) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.translateII");

        int iOffSet = iOff;
        String sWord = m_asSqlWords[iOffSet];

        if (sWord.equals("||")) {
            m_bFinded = true;
            m_sbDestinationSql.append(" +");
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.translateII Over");
        return iOffSet;
    }

    /**
     *
     */
    void translateSql() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.translateSql");

        //����sql���,�õ�sql��������
        //m_asSqlWords = parseSql(m_sResorceSQL);
        if (m_asSqlWords == null) {
            m_sbDestinationSql = null;
            Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToSqlServer.translateSql Over");
            return;
        }

        int iOffSet = 0;

        m_bFinded = false;
        m_sbDestinationSql = new StringBuffer();
        String sWord = "";

        while (iOffSet < m_asSqlWords.length) {

            sWord = m_asSqlWords[iOffSet];

            //����Oracle�Ż��ؼ���
            if (iOffSet < m_asSqlWords.length && iOffSet + 5 < m_asSqlWords.length && m_asSqlWords[iOffSet].equals("/")
                    && m_asSqlWords[iOffSet + 1].equals("*") && m_asSqlWords[iOffSet + 2].equals("+")) {
                iOffSet += 3;

                while (!m_asSqlWords[iOffSet].equals("*") && !m_asSqlWords[iOffSet + 1].equals("/")) {
                    iOffSet += 1;
                }
                iOffSet += 2;
                continue;
            }

            //ת��"||"
            if (!m_bFinded) {
                iOffSet = translateII(iOffSet);
            }

            if (!m_bFinded) {
                if (iOffSet > 0) {
                    m_sbDestinationSql.append(" ");
                }
                m_sbDestinationSql.append(sWord);
            }

            m_bFinded = false;
            iOffSet++;
        }
    }
}