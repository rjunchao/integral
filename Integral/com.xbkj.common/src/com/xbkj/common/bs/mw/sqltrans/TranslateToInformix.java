package com.xbkj.common.bs.mw.sqltrans;

/**
 * �˴���������˵����
 * �������ڣ�(2004-10-9 14:38:56)
 * @author����־ǿ
 */
import java.util.Vector;

import com.xbkj.log.bs.logging.Logger;

/**
 *ģ��:	TranslateToInformix.java
 *����:	��SqlServer��䷭�뵽Informix���

 */

public class TranslateToInformix extends TranslatorObject {

    public String getSql() throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.getSql");
        //����SQL���
        translateSql();
        //��������Ϊ�գ��򷵻ؿ�
        if (m_sbDestinationSql == null)
            return null;
        String sResult = m_sbDestinationSql.toString();
        //���������ԷֺŽ�β����ȥ���ֺ�
        if (sResult.endsWith(";"))
            sResult = sResult.substring(0, sResult.length() - 1);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.getSql Over");
        return sResult;
    }

    /**
     *ת��Create���
     */

    private void translateCreate() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateCreate");
        m_sbDestinationSql = new StringBuffer(m_sResorceSQL);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateCreate Over");
    }

    /**
     *ת��Delete���
     */

    private StringBuffer translateDelete(String[] sqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateDelete");

        int iOffSet = 0;
        String sSql = new String();
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBrack;

        while (iOffSet < sqlWords.length) {
            sPreWord = sWord;
            sWord = sqlWords[iOffSet];

            //���?��
            if ((sWord.equalsIgnoreCase("square") || sWord.equalsIgnoreCase("patindex")) && sqlWords[iOffSet + 1].equals("(")) {
                iLBracket = 0;
                iRBrack = 0;
                sSql = "";
                sSql = sWord + sqlWords[iOffSet + 1];
                iLBracket++;
                iOffSet++;
                while (iOffSet < sqlWords.length) {
                    iOffSet++;
                    sSql += " " + sqlWords[iOffSet];
                    if (sqlWords[iOffSet].equals("("))
                        iLBracket++;
                    if (sqlWords[iOffSet].equals(")"))
                        iRBrack++;
                    if (iLBracket == iRBrack) {
                        iOffSet++;
                        break;
                    }
                }
                if (sWord.equalsIgnoreCase("square")) {
                    translateFunSquare(parseSql(sSql));
                }
                if (sWord.equalsIgnoreCase("patindex")) {
                    //					translateFunPatindex(parseSql(sSql));
                }

            }
            //����PI()
            if (sWord.equalsIgnoreCase("PI") && sqlWords[iOffSet + 1].equals("(") && sqlWords[iOffSet + 2].equals(")")) {
                m_sbDestinationSql.append(" 3.1415926535897931");
                iOffSet += 3;
            }

            //����ģʽƥ���
            if (sWord.equalsIgnoreCase("like")) {
                String s = new String();
                s = sqlWords[iOffSet + 1];
                if (s.indexOf("[^") > 0 && s.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + s.substring(0, s.indexOf("["));
                    sSql = sSql + s.substring(s.indexOf("^") + 1, s.indexOf("]"));
                    sSql = sSql + s.substring(s.indexOf("]") + 1);
                    m_sbDestinationSql.append(" not like " + sSql);
                    iOffSet += 2;
                }
            }
            if (sPreWord.equalsIgnoreCase("like")) {
                if (sWord.indexOf("[") > 0 && sWord.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + sWord.substring(0, sWord.indexOf("["));
                    sSql = sSql + sWord.substring(sWord.indexOf("[") + 1, sWord.indexOf("]"));
                    sSql = sSql + sWord.substring(sWord.indexOf("]") + 1);
                    m_sbDestinationSql.append(" " + sSql);
                    iOffSet++;
                }
            }

            //�����Ӳ�ѯ
            if (sWord.equalsIgnoreCase("select")) {
                int i0 = 0;
                if (sPreWord.equals("("))
                    i0 = sqlWords.length - 1;
                else
                    i0 = sqlWords.length;
                sSql = "";
                while (iOffSet < i0) {
                    sSql += " " + sqlWords[iOffSet];
                    iOffSet++;
                }
                translateSelect(parseSql(sSql));
            } else {
                if (iOffSet < sqlWords.length) {
                    if (!sWord.equals(",") && !sWord.equals(")") && !sWord.equals("]") && !sPreWord.equals("(") && !sPreWord.equals("["))
                        m_sbDestinationSql.append(" ");
                    m_sbDestinationSql.append(sqlWords[iOffSet]);
                    iOffSet++;
                } else
                    break;
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateDelete Over");
        return m_sbDestinationSql;

    }

    /**
     * ת��Drop���
     */
    private void translateDrop() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateDrop");
        m_sbDestinationSql = new StringBuffer(m_sResorceSQL);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateDrop Over");
    }

    /**
     *��ݺ�����ձ���к���ת��
     */
    private void translateFunction() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunction");

        String sWord = null;
        int iOffSet = -1;

        while (iOffSet < m_asSqlWords.length) {
            iOffSet++;
            sWord = m_asSqlWords[iOffSet];
            if ((iOffSet + 1) >= m_asSqlWords.length)
                break;
            //�ҵ�����
            if (iOffSet > 1 && m_asSqlWords[iOffSet - 2].equalsIgnoreCase("convert") && m_asSqlWords[iOffSet - 1].equals("(")
                    && m_asSqlWords[iOffSet].equalsIgnoreCase("datetime")) {
                m_asSqlWords[iOffSet] = "datetime";
                iOffSet++;
            }
            if (m_asSqlWords[iOffSet + 1].equals("(")) {
                m_asSqlWords[iOffSet] = getFunction(sWord);
                iOffSet++;
            }
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunction Over");
    }

    /**
     * ת��Insert���
     */
    private StringBuffer translateInsert(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateInsert");

        int iOffSet = 0;
        String sSql = new String();
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBracket;

        while (iOffSet < asSqlWords.length) {
            sPreWord = sWord;
            sWord = asSqlWords[iOffSet];

            //���?��
            if ((sWord.equalsIgnoreCase("square") || sWord.equalsIgnoreCase("patindex")) && asSqlWords[iOffSet + 1].equals("(")) {
                iLBracket = 0;
                iRBracket = 0;
                sSql = "";
                sSql = sWord + asSqlWords[iOffSet + 1];
                iLBracket++;
                iOffSet++;
                while (iOffSet < asSqlWords.length) {
                    iOffSet++;
                    sSql += " " + asSqlWords[iOffSet];
                    if (asSqlWords[iOffSet].equals("("))
                        iLBracket++;
                    if (asSqlWords[iOffSet].equals(")"))
                        iRBracket++;
                    if (iLBracket == iRBracket) {
                        iOffSet++;
                        break;
                    }
                }
                if (sWord.equalsIgnoreCase("square")) {
                    translateFunSquare(parseSql(sSql));
                }
                if (sWord.equalsIgnoreCase("patindex")) {
                    //					translateFunPatindex(parseSql(sSql));
                }

            }
            //����PI()
            if (sWord.equalsIgnoreCase("PI") && asSqlWords[iOffSet + 1].equals("(") && asSqlWords[iOffSet + 2].equals(")")) {
                m_sbDestinationSql.append(" 3.1415926535897931");
                iOffSet += 3;
            }

            //����ģʽƥ���
            if (sWord.equalsIgnoreCase("like")) {
                String s = new String();
                s = asSqlWords[iOffSet + 1];
                if (s.indexOf("[^") > 0 && s.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + s.substring(0, s.indexOf("["));
                    sSql = sSql + s.substring(s.indexOf("^") + 1, s.indexOf("]"));
                    sSql = sSql + s.substring(s.indexOf("]") + 1);
                    m_sbDestinationSql.append(" not like " + sSql);
                    iOffSet += 2;
                }
            }
            if (sPreWord.equalsIgnoreCase("like")) {
                if (sWord.indexOf("[") > 0 && sWord.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + sWord.substring(0, sWord.indexOf("["));
                    sSql = sSql + sWord.substring(sWord.indexOf("[") + 1, sWord.indexOf("]"));
                    sSql = sSql + sWord.substring(sWord.indexOf("]") + 1);
                    m_sbDestinationSql.append(" " + sSql);
                    iOffSet++;
                }
            }

            //�����Ӳ�ѯ
            if (sWord.equalsIgnoreCase("select")) {
                int l = 0;
                if (sPreWord.equals("("))
                    l = asSqlWords.length - 1;
                else
                    l = asSqlWords.length;
                sSql = "";
                while (iOffSet < l) {
                    sSql += " " + asSqlWords[iOffSet];
                    iOffSet++;
                }
                translateSelect(parseSql(sSql));
            } else {
                if (iOffSet < asSqlWords.length) {
                    if (!sWord.equals(",") && !sWord.equals(")") && !sWord.equals("]") && !sPreWord.equals("(") && !sPreWord.equals("["))
                        m_sbDestinationSql.append(" ");
                    m_sbDestinationSql.append(asSqlWords[iOffSet]);
                    iOffSet++;
                } else
                    break;
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateInsert Over");
        return m_sbDestinationSql;

    }

    /**
     * �������Ӹ���
     * �������ڣ�(2004-11-05 8:38:35)
     * @return java.lang.String[]
     * @param asSqlWords java.lang.String[]
     * 
     * ��ʽ��
     * ��һ�������
     * update table1 set col1=b.col2 from table1 a, table2 b where a.col3=b.col3
     * ->
     * update table1 a set col1=(select b2.col2 from table2 b where a.col3=b.col3)
     *
     * �ڶ������
     * update table1 set col1=col1+����a, a.col2=b.col2+a.col2 from table1 a,table2 b 
     *
     * where a.col3=b.col3 and a.col2=���� 
     * ->
     * update table1 a set col1=col1+����a,col2=(select b2.col2+a.col2 from table2 b 
     *
     * where a.col3=b.col3 and a.col2=����b)
     *
     * where a.col2=����b
     */
    private String[] translateJoinUpdate(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateJoinUpdate");

        int iOffSet = 0; //ƫ����
        int iOffSet1 = 1; //ȡ�����ƫ����
        String[] asWords = asSqlWords;
        //String sSql = ""; //����ֵ
        boolean bFind = false;
        String sTableName = ""; //����
        String sTableAlias = ""; //��ı���
        //�Ƿ�������Ӹ��£���ȡ�ø��±�ı���ͱ���
        if (iOffSet1 < asSqlWords.length && iOffSet1 + 5 < asSqlWords.length && asSqlWords[iOffSet1].equals("/") && asSqlWords[iOffSet1 + 1].equals("*")
                && asSqlWords[iOffSet1 + 2].equals("+")) {
            iOffSet1 += 3;
            while (!asSqlWords[iOffSet1].equals("*") && !asSqlWords[iOffSet1 + 1].equals("/")) {
                iOffSet1 += 1;
            }
            iOffSet1 += 2;
        }
        sTableName = asWords[iOffSet1];

        int iSelectNum = 0;
        int iFromNum = 0;

        //����С�������ַ��ĳ���
        while (iOffSet < asWords.length) {
            //����ǰ�ַ��ǡ�from��
            if (asWords[iOffSet].equalsIgnoreCase("from")) {
                iFromNum++;

                if (iFromNum > iSelectNum) {
                    //��¼��ǰλ��

                    iOffSet++;
                    //����С�������ַ��ĳ���
                    while (iOffSet < asWords.length) {
                        //����ǰ�ַ���ڱ���
                        if (asWords[iOffSet].equalsIgnoreCase(sTableName)) {
                            //��ǰλ�ü�1
                            //iOffSet++;
                            //����ǰ�ַ�Ϊ���ţ������ѭ��
                            //if (iOffSet < asWords.length
                            //&& (asWords[iOffSet].equalsIgnoreCase(",")
                            //|| asWords[iOffSet].equalsIgnoreCase("where")))
                            {
                                if (iOffSet >= 1 && (asWords[iOffSet - 1].equalsIgnoreCase(",") || asWords[iOffSet - 1].equalsIgnoreCase("from"))) {
                                    //sTableAlias = asWords[iOffSet - 1];

                                    if (iOffSet + 1 < asWords.length) {
                                        if (asWords[iOffSet + 1].equalsIgnoreCase("as")) {
                                            if (iOffSet + 2 < asWords.length) {
                                                sTableAlias = asWords[iOffSet + 2];
                                            }
                                        } else {
                                            if (!asWords[iOffSet + 1].equals(",") && !asWords[iOffSet + 1].equalsIgnoreCase("where")
                                                    && !asWords[iOffSet + 1].equals("(") && !asWords[iOffSet + 1].equals(")")) {
                                                sTableAlias = asWords[iOffSet + 1];
                                            }
                                        }
                                    }
                                } else if (iOffSet >= 2 && (asWords[iOffSet - 2].equalsIgnoreCase(",") || asWords[iOffSet - 2].equalsIgnoreCase("from"))) {
                                    sTableName = asWords[iOffSet - 1];
                                    sTableAlias = asWords[iOffSet];
                                } else if (iOffSet >= 3 && (asWords[iOffSet - 3].equalsIgnoreCase(",") || asWords[iOffSet - 3].equalsIgnoreCase("from"))) {
                                    sTableName = asWords[iOffSet - 2];
                                    sTableAlias = asWords[iOffSet];
                                } /*
                                 else
                                 {
                                 //sTableName=asWords[iOffSet-2];
                                 sTableAlias = sTableName;
                                 }*/
                                break;
                            }

                        } else {
                            iOffSet++;
                        }
                    }
                    break;
                }
            }

            if (asWords[iOffSet].equalsIgnoreCase("select")) {
                iSelectNum++;
            }

            iOffSet++;

        } //while����

        if (iFromNum > iSelectNum) {
            bFind = true;
        }
        if (!bFind) //û�з������Ӹ��»��Ӳ�ѯ
        {
            return asWords;
        } else {
            Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateJoinUpdate Over");
            return translateJoinUpdate(asSqlWords, sTableName, sTableAlias);
        }
    }

    /**
     * ����Sql���,����:
     * ����ת��
     */
    private StringBuffer translateSelect(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateSelect");

        int iOffSet = 0;
        String sSql = new String();
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBracket;
        String topNum = "";
        String replaceTop = "";
        boolean hasTop = false;
        boolean hasWhere = false;
        boolean isUpdate = false;
        TransUnit aTransUnit = null;

        while (iOffSet < asSqlWords.length) {
            sPreWord = sWord;
            sWord = asSqlWords[iOffSet];

            if (sWord.equalsIgnoreCase("update")) {
                isUpdate = true;
            }
            //�ڴ˶Ժ�����д���
            //���ǰ����Ϊ�������
            if ((iOffSet + 1 < asSqlWords.length) && isFunctionName(sWord, asSqlWords[iOffSet + 1])) {
                aTransUnit = dealFunction(asSqlWords, sWord, iOffSet);

                iOffSet = aTransUnit.getIOffSet();

                if (iOffSet > asSqlWords.length - 1) {
                    //����Ƕ��
                    return null;
                }
            }
            //����oracle�Ż��ؼ���
            if (iOffSet < asSqlWords.length && iOffSet + 5 < asSqlWords.length && asSqlWords[iOffSet].equals("/") && asSqlWords[iOffSet + 1].equals("*")
                    && asSqlWords[iOffSet + 2].equals("+")) {
                iOffSet += 3;

                while (!asSqlWords[iOffSet].equals("*") && !asSqlWords[iOffSet + 1].equals("/")) {
                    iOffSet += 1;
                }
                iOffSet += 2;

            }

            //����PI()
            if (sWord.equalsIgnoreCase("PI") && asSqlWords[iOffSet + 1].equals("(") && asSqlWords[iOffSet + 2].equals(")")) {
                m_sbDestinationSql.append(" 3.1415926535897931");
                iOffSet += 3;
            }

            //����count(1)->count(*)
            if (iOffSet + 3 < asSqlWords.length && asSqlWords[iOffSet].equalsIgnoreCase("count") && asSqlWords[iOffSet + 1].equals("(")
                    && (asSqlWords[iOffSet + 2].equals("1") || asSqlWords[iOffSet + 2].equals("0")) && asSqlWords[iOffSet + 3].equals(")")) {
                m_sbDestinationSql.append(" count(*) ");
                iOffSet += 4;
            }

            //����ȡģ%
            if (iOffSet + 2 < asSqlWords.length && asSqlWords[iOffSet + 1].equals("%")) {
                m_sbDestinationSql.append(" mod(" + sWord + "," + asSqlWords[iOffSet + 2] + ")");
                iOffSet += 3;
            }

            //����ģʽƥ���
            if (sWord.equalsIgnoreCase("like")) {
                String s = new String();
                s = asSqlWords[iOffSet + 1];
                if (s.indexOf("[^") > 0 && s.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + s.substring(0, s.indexOf("["));
                    sSql = sSql + s.substring(s.indexOf("^") + 1, s.indexOf("]"));
                    sSql = sSql + s.substring(s.indexOf("]") + 1);
                    m_sbDestinationSql.append(" not like " + sSql);
                    iOffSet += 2;
                } else if (s.indexOf("[") > 0 && s.indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + s.substring(0, s.indexOf("["));
                    sSql = sSql + s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                    sSql = sSql + s.substring(s.indexOf("]") + 1);
                    m_sbDestinationSql.append(" " + sSql);
                    iOffSet++;
                }
            }
            //�����Ӳ�ѯ
            if (iOffSet < asSqlWords.length && iOffSet > 0 && asSqlWords[iOffSet].equalsIgnoreCase("select") && asSqlWords[iOffSet - 1].equals("(")) {
                aTransUnit = dealSelect(asSqlWords, sWord, iOffSet);
                iOffSet = aTransUnit.getIOffSet();

            }
            //����top�ؼ���
            if (asSqlWords[iOffSet].equalsIgnoreCase("top")) {
                m_sbDestinationSql.append(" first");
                iOffSet++;
            }
            //����columnname+columnname
            if (iOffSet < asSqlWords.length - 1 && asSqlWords[iOffSet].equalsIgnoreCase("+")
                    && (asSqlWords[iOffSet + 1].indexOf("'") > -1 || asSqlWords[iOffSet - 1].indexOf("'") > -1)) {
                m_sbDestinationSql.append("||");
                iOffSet++;

            }
            if (asSqlWords[iOffSet].equalsIgnoreCase("where")) {
                hasWhere = true;
            }
            //����null
            if (asSqlWords[iOffSet].equalsIgnoreCase("null") && iOffSet > 0 && iOffSet < 2 && !asSqlWords[iOffSet - 1].equals("=")
                    && !asSqlWords[iOffSet - 1].equals("(")) {
                m_sbDestinationSql.append(" null::char");
                iOffSet++;
            } else if (asSqlWords[iOffSet].equalsIgnoreCase("null") && iOffSet < asSqlWords.length && iOffSet > 1 && !asSqlWords[iOffSet - 1].equals("=")
                    && !asSqlWords[iOffSet - 1].equalsIgnoreCase("is")
                    && (!asSqlWords[iOffSet - 2].equalsIgnoreCase("is") && !asSqlWords[iOffSet - 1].equalsIgnoreCase("not"))
                    && !((asSqlWords[iOffSet - 2].equalsIgnoreCase("cast")) && (asSqlWords[iOffSet - 1].equalsIgnoreCase("(")))) {
                m_sbDestinationSql.append(" null::char");
                iOffSet++;
            } else if (asSqlWords[iOffSet].equalsIgnoreCase("=") && iOffSet < asSqlWords.length && iOffSet > 0 && hasWhere
                    && asSqlWords[iOffSet + 1].equalsIgnoreCase("null")) {
                m_sbDestinationSql.append(" is null");
                iOffSet += 2;
            } else {
                if (iOffSet < asSqlWords.length) {
                    if (!asSqlWords[iOffSet].equals(",") && !asSqlWords[iOffSet].equals(")") && !asSqlWords[iOffSet].equals("]") && !sPreWord.equals("(")
                            && !sPreWord.equals("["))
                        m_sbDestinationSql.append(" ");
                }
                m_sbDestinationSql.append(asSqlWords[iOffSet]);
                //m_sbDestinationSql.append(" ");
                iOffSet++;
            }
        } //while loop end

        if (m_sbDestinationSql != null) {
            m_sbDestinationSql.replace(0, m_sbDestinationSql.toString().length(), m_sbDestinationSql.toString().trim());
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateSelect Over");
        return m_sbDestinationSql;

    }

    /**
     *���������ͽ���ת��
     */

    private void translateSql() throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateSql");

        //����sql���,�õ�sql��������
        m_sbDestinationSql = new StringBuffer();
        if (m_asSqlWords == null) {
            m_sbDestinationSql = null;
            Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateSql Over");
            return;
        }

        //�Ƚ��к���ת��
        translateFunction();

        //���sql������ͽ���ת��
        switch (getStatementType()) {
        case SQL_SELECT:
            translateSelect(m_asSqlWords);
            break;
        case SQL_INSERT:
            translateInsert(m_asSqlWords);
            break;
        case SQL_CREATE:
            translateCreate();
            break;
        case SQL_DROP:
            translateDrop();
            break;
        case SQL_DELETE:
            translateSelect(m_asSqlWords);
            break;
        case SQL_UPDATE:
            //translateUpdate(m_asSqlWords);
            translateUpdate(m_asSqlWords);
            break;
        case 8: //if exists
            translateIFExists(m_asSqlWords);
            break;
        }
    }

    boolean m_bSubSelect = false;

    boolean m_bUpdateFrom = false;

    /**
     * �����ַ�����asSqlWords�д�start��end�����ַ�
     * �������ڣ�(2004-11-03 19:50:50)
     */
    private String appendString(String[] asSqlWords, int start, int end) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.appendString");
        if (asSqlWords == null || asSqlWords.length < 1)
            return null;
        if (start < 0 || end < 0 || start > end) {
            System.out.println("In appendString method error");
            return null;
        }
        String result = "";
        for (int i = start; i <= end; i++) {
            result += asSqlWords[i] + " ";
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.appendString Over");
        return result;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2004-11-03 19:50:50)
     */
    public TransUnit dealFunction(String[] asSqlWords, String sWord, int iOffSet) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dealFunction");

        Vector vec = new Vector();
        vec.addElement(asSqlWords[iOffSet]);
        iOffSet += 1;
        TransUnit aTransUnit = getSubSql(asSqlWords, "(", ")", iOffSet);
        String[] newFuncSql = aTransUnit.getSqlArray();
        iOffSet = aTransUnit.getIOffSet() + 1;

        for (int i = 0; i < newFuncSql.length; i++) {
            vec.addElement(newFuncSql[i]);
        }
        newFuncSql = new String[vec.size()];
        vec.copyInto(newFuncSql);

        if (sWord.equalsIgnoreCase("left")) {
            //translateFunLeft(newFuncSql);
        } else if (sWord.equalsIgnoreCase("right")) {
            //translateFunRight(newFuncSql);
        } else if (sWord.equalsIgnoreCase("square")) {
            translateFunSquare(newFuncSql);
        } else if (sWord.equalsIgnoreCase("cast")) {
            //translateFunCast(newFuncSql);
        } else if (sWord.equalsIgnoreCase("coalesce")) {
            translateFunCoalesce(newFuncSql);
        } else if (sWord.equalsIgnoreCase("ltrim")) {
            //translateFunLtrim(newFuncSql);
        } else if (sWord.equalsIgnoreCase("rtrim")) {
            //translateFunRtrim(newFuncSql);
        } else if (sWord.equalsIgnoreCase("patindex")) {
            //translateFunPatindex(newFuncSql);
        } else if (sWord.equalsIgnoreCase("len")) {
            //translateFunLen(newFuncSql);
        } else if (sWord.equalsIgnoreCase("round")) {
            translateFunRound(newFuncSql);
        } else if (sWord.equalsIgnoreCase("convert")) {
            translateFunConvert(newFuncSql);
        } else if (sWord.equalsIgnoreCase("dateadd")) {
            translateFunDateAdd(newFuncSql);
        } else if (sWord.equalsIgnoreCase("datediff")) {
            translateFunDateDiff(newFuncSql);

        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dealFunction Over");
        return new TransUnit(null, null, iOffSet);
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2004-11-03 19:50:50)
     */
    public TransUnit dealSelect(String[] asSqlWords, String sWord, int iOffSet) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dealSelect");

        if (iOffSet < asSqlWords.length) {

            TransUnit aTransUnit = getSubSql(asSqlWords, "(", ")", iOffSet);
            String[] newCaseSql = aTransUnit.getSqlArray();
            iOffSet = aTransUnit.getIOffSet();

            String newSql[] = new String[newCaseSql.length - 1];

            for (int i = 0; i < newSql.length; i++) {
                newSql[i] = newCaseSql[i];
            }

            //�����Ӳ�ѯ
            TranslateToInformix newTranslateToInformix = new TranslateToInformix();

            newTranslateToInformix.setSqlArray(newSql);

            m_sbDestinationSql.append(newTranslateToInformix.getSql());
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dealSelect Over");
        return new TransUnit(null, null, iOffSet);
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2004-11-03 19:50:50)
     */
    private String dropTable(String[] asSqlWords, int index) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dropTable");
        String s = "";
        if (asSqlWords[asSqlWords.length - 1].equalsIgnoreCase("go")) {
            asSqlWords[asSqlWords.length - 1] = ";";
        }
        s = appendString(asSqlWords, index, asSqlWords.length - 1);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.dropTable Over");
        return s;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2004-11-03 19:50:50)
     */
    public boolean isFunctionName(String sWord, String nextWord) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.isFunctionName");
        boolean isFunc = false;

        if ((sWord.equalsIgnoreCase("square") || sWord.equalsIgnoreCase("coalesce") || sWord.equalsIgnoreCase("convert") || sWord.equalsIgnoreCase("dateadd")
                || sWord.equalsIgnoreCase("round") || sWord.equalsIgnoreCase("datediff"))
                && nextWord.equals("(")) {
            isFunc = true;
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.isFunctionName Over");
        return isFunc;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2004-11-03 19:50:50)
     */
    private int isHasWord(String asSqlWords[], String s) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.isHasWord");
        if (asSqlWords == null || asSqlWords.length < 1)
            return -1;
        int pos = -1;
        int i = 0;
        while (i < asSqlWords.length && (!asSqlWords[i].equalsIgnoreCase(s))) {
            i++;
        }
        if (i < asSqlWords.length)
            pos = i;
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.isHasWord Over");
        return pos;
    }

    /**
     *ת��Convert����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		Convert(datatype,col) -> cast(col as datatype)
     */

    private void translateFunConvert(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunConvert");
        String sRes = "";
        int iOff = 2;
        String dataType = "", theData = "", theLen = "";
        String params[] = getFunParam(asWords, iOff, asWords.length - 1);

        dataType = params[0].trim();

        theData = params[1].trim();
        if (params.length > 2) {
            theLen = "," + params[2].trim();
        }
        TranslateToInformix newTranslateToInformix = new TranslateToInformix();
        try {
            newTranslateToInformix.setSql(theData);

            theData = newTranslateToInformix.getSql();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sRes = "cast(" + theData + " as " + dataType + ")";
        m_sbDestinationSql.append(" " + sRes);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunConvert Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunDateAdd(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunDateAdd");

        int iOff = 2;

        String params[] = getFunParam(asWords, iOff, asWords.length - 1);

        String dateType = params[0].trim();

        String theNumber = params[1].trim();

        String theDate = params[2].trim();

        TranslateToInformix newTranslateToInformix = new TranslateToInformix();
        try {
            newTranslateToInformix.setSql(theDate);

            theDate = newTranslateToInformix.getSql();

            if (theDate.toLowerCase().startsWith("getdate(") || theDate.toLowerCase().startsWith("getdate (") || theDate.toLowerCase().startsWith("getdate  (")
                    || theDate.toLowerCase().startsWith("getdate   (")) {
                theDate = "date(current date)";
            } else {
                theDate = "date(" + theDate + ")";
            }

            if (dateType.equalsIgnoreCase("yy") || dateType.equalsIgnoreCase("yyyy") || dateType.equalsIgnoreCase("year")) {
                theNumber = "decimal(" + theNumber + "0000,8,0)";
            } else if (dateType.equalsIgnoreCase("mm") || dateType.equalsIgnoreCase("m") || dateType.equalsIgnoreCase("month")) {
                theNumber = "decimal(" + theNumber + "00,8,0)";
            } else {
                theNumber = "decimal(" + theNumber + ",8,0)";
            }
            m_sbDestinationSql.append(" " + "date(" + theDate + "-" + theNumber + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //m_sbDestinationSql.append("");
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunDateAdd Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunDateDiff(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunDateDiff");

        int iOff = 2;

        String params[] = getFunParam(asWords, iOff, asWords.length - 1);

        String dateType = params[0].trim();

        String theStart = params[1].trim();

        String theEnd = params[2].trim();

        TranslateToInformix newTranslateToInformix = new TranslateToInformix();
        try {
            newTranslateToInformix.setSql(theStart);
            theStart = newTranslateToInformix.getSql();

            newTranslateToInformix.setSql(theEnd);
            theEnd = newTranslateToInformix.getSql();

            if (dateType != null
                    && (dateType.trim().equalsIgnoreCase("day") || dateType.trim().equalsIgnoreCase("dd") || dateType.trim().equalsIgnoreCase("d"))) {

                if (theStart.toLowerCase().startsWith("getdate(") || theStart.toLowerCase().startsWith("getdate (")
                        || theStart.toLowerCase().startsWith("getdate  (") || theStart.toLowerCase().startsWith("getdate   (")) {

                    theStart = "days(current date)";
                } else {
                    theStart = "days(" + theStart + ")";
                }
                if (theEnd.toLowerCase().startsWith("getdate(") || theEnd.toLowerCase().startsWith("getdate (")
                        || theEnd.toLowerCase().startsWith("getdate  (") || theEnd.toLowerCase().startsWith("getdate   (")) {

                    theEnd = "days(current date)";
                } else {
                    theEnd = "days(" + theEnd + ")";
                }
            } else if (dateType != null
                    && (dateType.trim().equalsIgnoreCase("month") || dateType.trim().equalsIgnoreCase("mm") || dateType.trim().equalsIgnoreCase("m"))) {

                if (theStart.toLowerCase().startsWith("getdate(") || theStart.toLowerCase().startsWith("getdate (")
                        || theStart.toLowerCase().startsWith("getdate  (") || theStart.toLowerCase().startsWith("getdate   (")) {

                    theStart = "month(current date)";
                } else {
                    theStart = "month(" + theStart + ")";
                }
                if (theEnd.toLowerCase().startsWith("getdate(") || theEnd.toLowerCase().startsWith("getdate (")
                        || theEnd.toLowerCase().startsWith("getdate  (") || theEnd.toLowerCase().startsWith("getdate   (")) {

                    theEnd = "month(current date)";
                } else {
                    theEnd = "month(" + theEnd + ")";
                }
            } else if (dateType != null
                    && (dateType.trim().equalsIgnoreCase("year") || dateType.trim().equalsIgnoreCase("yyyy") || dateType.trim().equalsIgnoreCase("yy"))) {

                if (theStart.toLowerCase().startsWith("getdate(") || theStart.toLowerCase().startsWith("getdate (")
                        || theStart.toLowerCase().startsWith("getdate  (") || theStart.toLowerCase().startsWith("getdate   (")) {

                    theStart = "year(current date)";
                } else {
                    theStart = "year(" + theStart + ")";
                }
                if (theEnd.toLowerCase().startsWith("getdate(") || theEnd.toLowerCase().startsWith("getdate (")
                        || theEnd.toLowerCase().startsWith("getdate  (") || theEnd.toLowerCase().startsWith("getdate   (")) {

                    theEnd = "year(current date)";
                } else {
                    theEnd = "year(" + theEnd + ")";
                }
            }
            m_sbDestinationSql.append(" " + theEnd + "-" + theStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //m_sbDestinationSql.append("");
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunDateDiff Over");
    }

    /*
     * round(����1,����2,����3)
     * ����:
     * round(����1,����2,����3)->trunc(����1,����2)
     * �������ڣ�(2004-11-30 15:06:37)
     */
    private void translateFunRound(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunRound");
        int iOff = 2;
        int iLBracket = 0;
        int iRBracket = 0;
        int commaCount = 0;
        int doubleQuotationCount = 0;
        int singleQuotationCount = 0;

        int firstCommaIndex = 0;
        int secondCommaIndex = 0;

        String theNumber = "";
        String theLength = "";
        String theTyle = "";

        TranslateToInformix newTranslateToInformix = null;

        while (iOff < asSqlWords.length - 1) {
            if (asSqlWords[iOff].equals("(")) {
                iLBracket++;
            }
            if (asSqlWords[iOff].equals(")")) {
                iRBracket++;
            }
            if (asSqlWords[iOff].equals("\'")) {
                singleQuotationCount++;
            }
            if (asSqlWords[iOff].equals("\"")) {
                doubleQuotationCount++;
            }
            if (asSqlWords[iOff].equals(",") && iRBracket == iLBracket && doubleQuotationCount % 2 == 0 && singleQuotationCount % 2 == 0) {
                commaCount++;
                if (commaCount == 1) {
                    firstCommaIndex = iOff;

                    for (int i = 2; i < iOff; i++) {
                        theNumber += " " + asSqlWords[i];
                    }

                    if (iOff - 2 > 1) {
                        if (newTranslateToInformix == null) {
                            newTranslateToInformix = new TranslateToInformix();
                        }

                        newTranslateToInformix.setSql(theNumber);

                        theNumber = newTranslateToInformix.getSql();
                    }
                } else {
                    secondCommaIndex = iOff;

                    for (int i = firstCommaIndex + 1; i < iOff; i++) {
                        theLength += " " + asSqlWords[i];
                    }

                    if (iOff - (firstCommaIndex + 1) > 1) {
                        if (newTranslateToInformix == null) {
                            newTranslateToInformix = new TranslateToInformix();
                        }

                        newTranslateToInformix.setSql(theLength);

                        theLength = newTranslateToInformix.getSql();
                    }
                }
            }
            iOff++;
        }

        int fromIndex = 0;
        String s = "";

        if (commaCount == 1) {
            for (int i = firstCommaIndex + 1; i < asSqlWords.length - 1; i++) {
                theLength += " " + asSqlWords[i];
            }

            if ((asSqlWords.length - 1) - (firstCommaIndex + 1) > 1) {
                if (newTranslateToInformix == null) {
                    newTranslateToInformix = new TranslateToInformix();
                }

                newTranslateToInformix.setSql(theLength);

                theLength = newTranslateToInformix.getSql();
            }

            s = " round(" + theNumber + ", " + theLength + ") ";
        } else {
            for (int i = secondCommaIndex + 1; i < asSqlWords.length - 1; i++) {
                theTyle += " " + asSqlWords[i];
            }

            if ((asSqlWords.length - 1) - (secondCommaIndex + 1) > 1) {
                if (newTranslateToInformix == null) {
                    newTranslateToInformix = new TranslateToInformix();
                }

                newTranslateToInformix.setSql(theTyle);

                theTyle = newTranslateToInformix.getSql();
            }

            int tyle = Integer.valueOf(theTyle.trim()).intValue();

            if (tyle == 0) {
                s = " round(" + theNumber + ", " + theLength + ") ";
            } else {
                s = " trunc(" + theNumber + ", " + theLength + ") ";
            }
        }
        m_sbDestinationSql.append(s);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.TranslateToInformix Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->f*f
     */

    private void translateFunSquare(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunSquare");
        String s = new String();
        int iOff = 2;
        int iLBracket = 1;
        int iRBracket = 0;

        while (iOff < asWords.length) {
            if (asWords[iOff].equals("("))
                iLBracket++;
            if (asWords[iOff].equals(")"))
                iRBracket++;
            if (iLBracket != iRBracket)
                s += asWords[iOff];
            iOff++;
        }
        try {
            translateSelect(parseSql(s + "*" + s));
        } catch (Exception e) {
            System.out.println(e);
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunSquare Over");
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private void translateIFExists(String[] asSqlWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateIFExists");
        int index = -1;
        String result = "";
        if ((index = isHasWord(asSqlWords, "drop")) > -1 && asSqlWords[index + 1].equalsIgnoreCase("table")) {
            result = dropTable(asSqlWords, index);
        }
        m_sbDestinationSql.append(result);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateIFExists Over");
    }

    /**
     * �������Ӹ���
     * �������ڣ�(2004-11-05 8:38:35)
     * @return java.lang.String[]
     * @param asSqlWords java.lang.String[]
     * 
     * ��ʽ��
     * ��һ�������
     * update table1 set col1=b.col2 from table1 a, table2 b where a.col3=b.col3
     * ->
     * update table1 a set col1=(select b2.col2 from table2 b where a.col3=b.col3)
     *
     * �ڶ������
     * update table1 set col1=col1+����a, a.col2=b.col2+a.col2 from table1 a,table2 b 
     *
     * where a.col3=b.col3 and a.col2=���� 
     * ->
     * update table1 a set col1=col1+����a,col2=(select b2.col2+a.col2 from table2 b 
     *
     * where a.col3=b.col3 and a.col2=����b)
     *
     * where a.col2=����b
     */
    private String[] translateJoinUpdate(String[] asSqlWords, String sTableName, String sTableAlias) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateJoinUpdate");

        int iOffSet = 0; //ƫ����
        String[] asWords = asSqlWords;
        String sSql = ""; //����ֵ
        String m_Sql = ""; //��ʱsql
        String sLeftField = "";
        String sRightField = "";
        Vector vSetList = new Vector(); //���set���
        String s = ""; //�м����
        Vector vecTable = new Vector();

        String whereSql = "";
        String fromSt = "";
        //Vector vSql = new Vector(); //���sql���

        iOffSet = -1; //ƫ�����˻�
        String[] asTables = null;

        int iJoinCount = 0; //join�ĸ���

        int iSingleCount = 0; //���������
        String subfromSt = "";
        Vector vWhList = new Vector(); //���where���
        String inSql1 = "";
        String inSql2 = "";
        String andSql = "";
        boolean bExist = false;
        String m_whereSql = "";

        asTables = parseTable(asSqlWords, sTableName, sTableAlias);
        //����С�������ַ��ĳ��ȼ�1
        while (iOffSet < asWords.length - 1) {
            //�����1
            iOffSet++;

            //����ǰ�ַ��ǡ�set��
            if (asWords[iOffSet].equalsIgnoreCase("set")) {
                String str = "";
                String setsql = "";
                int setcount = 0;

                sSql += " set";
                //vSql.addElement("set");
                iOffSet++;

                int leftCount = 0; //��������
                int rightCount = 0; //��������

                //����ǰ�ַ��ǡ�from������ѭ��
                while (iOffSet < asWords.length && !asWords[iOffSet].equalsIgnoreCase("from")) {

                    //�ѱ�ı����滻Ϊ�������
                    if (sTableAlias != null && sTableAlias.trim().length() > 0 && asWords[iOffSet].startsWith(sTableAlias + ".")) {
                        int i = asWords[iOffSet].indexOf(".");
                        asWords[iOffSet] = sTableName + asWords[iOffSet].substring(i);
                    }

                    if (asWords[iOffSet].equalsIgnoreCase("(")) //��������
                    {
                        leftCount++;
                    } else if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase(")")) {
                        rightCount++;
                        if (leftCount == rightCount) {
                            leftCount = 0;
                            rightCount = 0;
                        }
                    }

                    //����ǰ�ַ��Ƕ���
                    if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase(",") && leftCount == rightCount) {
                        //��¼�ۼ���ݣ����ۼ������
                        vSetList.addElement(str);
                        str = "";
                        iOffSet++;
                    } else {
                        //�ۼƵ�ǰ�ַ�
                        str += " " + asWords[iOffSet];
                        iOffSet++;
                    }
                }
                //��¼�ۼ����
                vSetList.addElement(str);

                //ֻ��һ�н��и���
                //��ֻ��һ�����
                { //���и���
                    int i0 = 0;
                    //����С�ڼ�¼�������ȣ���ѭ��
                    while (i0 < vSetList.size()) {
                        //ȡ�õ�ǰ�ַ�
                        s = (String) vSetList.elementAt(i0);
                        //ȡ�õȺŵ�λ��
                        int start = s.indexOf("=");
                        if (haveTab(s.substring(start + 1), asTables)) { //�����ֱ���ͱ���
                            iJoinCount++;
                            if (iJoinCount > 1) {
                                sLeftField += "," + s.substring(0, start); //��ʱ�������������ƴ�ӵ�sql���
                                sRightField += "," + s.substring(start + 1);
                            } else {
                                sLeftField += " " + s.substring(0, start);
                                sRightField += " " + s.substring(start + 1);
                            }
                        } else {
                            setcount++;
                            if (setcount > 1) {
                                setsql += "," + s;
                            } else {
                                setsql += " " + s;
                            }
                        }
                        i0++;
                    }
                } ////���и��� else ����

                //������������ϼ�������0
                if (iSingleCount > 0 && iJoinCount > 0) {
                    sSql += ",";
                    //vSql.addElement(",");
                }

                //�����������0�����ϼ������0
                //if (iSingleCount > 0 || iJoinCount > 0)
                if (setcount == 0) {
                    if (iJoinCount > 1)
                        sSql += "(" + sLeftField + ")=((select " + sRightField; //����ʱ������������䣬ƴ�ӵ�sql���
                    else if (iJoinCount == 1)
                        sSql += "" + sLeftField + "=((select " + sRightField;
                } else {
                    if (iJoinCount > 1)
                        sSql += setsql + ",(" + sLeftField + ")=((select " + sRightField;
                    else if (iJoinCount == 1)
                        sSql += setsql + "," + sLeftField + "=((select " + sRightField;
                    else
                        sSql += setsql;
                }
            } //if (asWords[iOffSet].equalsIgnoreCase("set"))����

            //�����±�����from�Ӿ����޳�
            //����ǰ�ַ��ǡ�from��
            if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase("from")) {
                iOffSet++;
                int f_leftCount = 1;
                int f_rightCount = 0;
                Vector aNewVec = new Vector();
                while (iOffSet < asWords.length && !asWords[iOffSet].equalsIgnoreCase("where")) {

                    ///////////////
                    if (asWords[iOffSet].equalsIgnoreCase("(")) {
                        subfromSt = "(";
                        while ((f_leftCount != f_rightCount) && (iOffSet < asWords.length)) {
                            iOffSet++;

                            if (asWords[iOffSet].equalsIgnoreCase("(")) {
                                f_leftCount++;
                            } else if (asWords[iOffSet].equalsIgnoreCase(")")) {
                                f_rightCount++;
                            }

                            subfromSt += " " + asWords[iOffSet];

                        }

                        aNewVec.addElement(subfromSt);
                        iOffSet++;
                    }
                    //////////////
                    aNewVec.addElement(asWords[iOffSet]);
                    iOffSet++;
                }

                for (int newIndex = 0; newIndex < aNewVec.size(); newIndex++) {
                    String othtable = aNewVec.elementAt(newIndex).toString();
                    String trueName = othtable;
                    boolean isOth = false;

                    if (!othtable.equalsIgnoreCase(sTableName)) {
                        isOth = true;
                        if (vecTable.size() > 0) {
                            fromSt += ",";
                        }
                        fromSt += " " + othtable;
                        vecTable.addElement(othtable);
                    }
                    newIndex++;
                    if (newIndex < aNewVec.size()) {
                        othtable = aNewVec.elementAt(newIndex).toString();
                        if (othtable.equalsIgnoreCase("as")) {
                            newIndex++;
                            othtable = aNewVec.elementAt(newIndex).toString();
                        }

                        if (!othtable.equalsIgnoreCase(",")) {
                            if (isOth) {
                                fromSt += " " + othtable;
                                vecTable.addElement(othtable);
                            } else {
                                if (sTableAlias != null && sTableAlias.trim().length() > 0) {
                                    if (!othtable.equalsIgnoreCase(sTableAlias)) {
                                        if (vecTable.size() > 0) {
                                            fromSt += ",";
                                        }
                                        fromSt += trueName + " " + othtable;

                                        vecTable.addElement(trueName);
                                        vecTable.addElement(othtable);
                                    }
                                }
                            }
                            newIndex++;
                        }
                    }
                }

                if (fromSt.trim().length() > 0) {
                    fromSt = " from " + fromSt;
                }

                if (fromSt.endsWith(",")) {
                    fromSt = fromSt.substring(0, fromSt.length() - 1);
                }
                //�������ӱ�
                if (iJoinCount > 0) {
                    sSql += fromSt;
                }

            }

            //��ǰ�ַ��ǡ�where��
            if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase("where")) {
                int w_leftCount = 0;
                int w_rightCount = 0;
                int inCount = 0;
                int andCount = 0;
                int whereCount = 0;
                int i1 = 0;
                boolean isExist = false;
                String sw = "";
                String s1 = "";
                String w_leftField = "";
                String w_rightField = "";
                iOffSet++;
                m_Sql = sSql;
                m_Sql += " where";
                m_whereSql += " where";
                while (iOffSet < asWords.length) {
                    //�ѱ�ı����滻Ϊ�������
                    if (sTableAlias != null && sTableAlias.trim().length() > 0 && asWords[iOffSet].startsWith(sTableAlias + ".")) {
                        int i = asWords[iOffSet].indexOf(".");
                        asWords[iOffSet] = sTableName + asWords[iOffSet].substring(i);
                    }

                    m_Sql += " " + asWords[iOffSet];
                    m_whereSql += " " + asWords[iOffSet];
                    if (asWords[iOffSet].equalsIgnoreCase("(")) {
                        w_leftCount++;
                    } else if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase(")")) {
                        w_rightCount++;
                        if (w_leftCount == w_rightCount) {
                            w_leftCount = 0;
                            w_rightCount = 0;
                        }
                    }

                    //�ֲ�where�������
                    if (iOffSet < asWords.length && (asWords[iOffSet].equalsIgnoreCase("and") || asWords[iOffSet].equalsIgnoreCase("or"))
                            && w_leftCount == w_rightCount) {
                        //��¼�ۼ���ݣ����ۼ������
                        vWhList.addElement(sw);
                        vWhList.addElement(asWords[iOffSet]);
                        sw = "";
                        iOffSet++;
                    } else {
                        //�ۼƵ�ǰ�ַ�
                        sw += " " + asWords[iOffSet];
                        iOffSet++;
                    }
                }
                //��¼�ۼ����
                vWhList.addElement(sw);

                //����С�ڼ�¼�������ȣ���ѭ��
                while (i1 < vWhList.size()) {
                    //ȡ�õ�ǰ�ַ�
                    s1 = (String) vWhList.elementAt(i1);

                    //�ж��������������Ĵ���
                    if (s1.indexOf("!=") > 0 || s1.indexOf("! =") > 0 || s1.indexOf("<") > 0 || s1.indexOf(">") > 0) {
                        isExist = true;
                    }

                    if (!s1.trim().startsWith("(") && !isExist) {
                        //ȡ�õȺŵ�λ��
                        int start = s1.indexOf("=");

                        if (start > 0) {
                            //�����ֱ���ͱ���
                            w_leftField = s1.substring(0, start);
                            w_rightField = s1.substring(start + 1);
                            if ((isMasterTab(w_leftField, sTableName) || isMasterTab(w_leftField, sTableAlias)) && isMasterTab(asTables, w_rightField)) {
                                inCount++;
                                if (inCount > 1) {
                                    inSql1 += "," + w_leftField; //��ʱ�������������ƴ�ӵ�sql���
                                    inSql2 += "," + w_rightField;
                                } else {
                                    inSql1 += " " + w_leftField;
                                    inSql2 += " " + w_rightField;
                                }
                            } else if ((isMasterTab(w_rightField, sTableName) || isMasterTab(w_rightField, sTableAlias)) && isMasterTab(asTables, w_leftField)) {
                                inCount++;
                                if (inCount > 1) {
                                    inSql1 += "," + w_rightField; //��ʱ�������������ƴ�ӵ�sql���
                                    inSql2 += "," + w_leftField;
                                } else {
                                    inSql1 += " " + w_rightField;
                                    inSql2 += " " + w_leftField;
                                }
                            } else if (isMasterTab(asTables, w_leftField)) {
                                whereCount++;
                                if (whereCount > 1) {
                                    whereSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                                } else {
                                    whereSql += " " + s1;
                                }
                            } else if (isMasterTab(w_leftField, sTableName) || isMasterTab(w_leftField, sTableAlias)) {
                                andCount++;
                                if (andCount > 1) {
                                    andSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                                } else {
                                    andSql += " " + s1;
                                }
                            } else {
                                bExist = false;
                                break;
                            }
                        } else { //����"="��������"is ( not ) null"��
                            String firstWord = parseWord(s1);
                            if (haveTab(firstWord, asTables)) {
                                whereCount++;
                                if (whereCount > 1) {
                                    whereSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                                } else {
                                    whereSql += " " + s1;
                                }
                            } else if (haveTab(s1, sTableName) || haveTab(s1, sTableAlias)) {
                                andCount++;
                                if (andCount > 1) {
                                    andSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                                } else {
                                    andSql += " " + s1;
                                }
                            } else {
                                bExist = false;
                                break;
                            }
                        }
                    } else {//��"()"�����������������������������ڵ�����
                        if ((haveTab(s1, sTableName) || haveTab(s1, sTableAlias)) && haveTab(s1, asTables)) {
                            bExist = false;
                            break;
                        } else if (haveTab(s1, asTables)) {
                            whereCount++;
                            if (whereCount > 1) {
                                whereSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                            } else {
                                whereSql += " " + s1;
                            }

                        } else if (haveTab(s1, sTableName) || haveTab(s1, sTableAlias)) {
                            andCount++;
                            if (andCount > 1) {
                                andSql += " " + (String) vWhList.elementAt(i1 - 1) + " " + s1;
                            } else {
                                andSql += " " + s1;
                            }
                        } else {
                            bExist = false;
                            break;
                        }
                    }
                    i1 += 2;
                }
            } else if (iOffSet < asWords.length) {
                if (asWords[iOffSet].equalsIgnoreCase(sTableName) || asWords[iOffSet].equalsIgnoreCase(sTableAlias)) {
                    sSql += " " + sTableName;
                } else {
                    sSql += " " + asWords[iOffSet];
                }

                //sSql += " " + asWords[iOffSet];
            }
        } //while (iOffSet < asWords.length - 1)����

        m_Sql += "))";
        if (iJoinCount > 0) {
            sSql = m_Sql;
        }
        if (bExist) {
            if (andSql.trim() != null && andSql.trim().length() > 0 && inSql1.trim() != null && inSql1.trim().length() > 0 && whereSql.trim() != null
                    && whereSql.trim().length() > 0) {
                sSql += " where " + andSql + " and (" + inSql1 + ") in ( select " + inSql2 + " " + fromSt + " where " + whereSql + " )";
            } else if (andSql.trim() != null && andSql.trim().length() > 0 && inSql1.trim() != null && inSql1.trim().length() > 0) {
                sSql += " where " + andSql + " and (" + inSql1 + ") in ( select " + inSql2 + " " + fromSt + " )";
            } else if (inSql1.trim() != null && inSql1.trim().length() > 0 && whereSql.trim() != null && whereSql.trim().length() > 0) {
                sSql += " where  (" + inSql1 + ") in ( select " + inSql2 + " " + fromSt + " where " + whereSql + " )";
            } else if (inSql1.trim() != null && inSql1.trim().length() > 0) {
                sSql += " where  (" + inSql1 + ") in ( select " + inSql2 + " " + fromSt + " )";
            } else if (andSql.trim() != null && andSql.trim().length() > 0) {
                sSql += " where " + andSql;
            }
        } else {
            if (m_whereSql != null && m_whereSql.trim().length() > 0) {
                sSql += " where exists( select 1 " + fromSt + " " + m_whereSql + " )";
            }
        }
        asWords = parseSql(sSql);

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateJoinUpdate Over");
        return asWords;
    }

    private int[][] err_inf = { { -204, 208 }, //�����ͼ������
            { -104, 2715 }, //�������
            { -206, 207 }, //��Ч������
            { -421, 205 }, //ʹ��union������Ŀ���б�Ӧ������ͬ��Ŀ�ı��ʽ
            { -408, 213 }, //������ݺͱ�������Ͳ�һ��
            { -803, 2627 }, //���ܲ�����ͬ����ļ�¼
            { -407, 515 }, //��ֵ����Ϊ��
            { -433, 8152 } //�����ֵ�����й��
    };

    //������ձ�,�г�sqlServer������Informix����Ķ�Ӧ��ϵ,
    private String[][] fun_inf = { { "len", "length" }, { "substring", "substr" }, { "lower", "lower" }, { "upper", "upper" }, { "isnull", "nvl" },
            { "rtrim", "trim" }, { "ltrim", "trim" }

    };

    /**
     * TransDB2 ������ע�͡�
     */
    public TranslateToInformix() {
        super(INFORMIX);
        m_apsFunList = fun_inf;
        m_apiErrorList = err_inf;
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2 Over");
    }

    /**
     * ת�� coalesce ����
     * ����:
     * asSqlWords   coalesce�����Ӿ�
     * ����:
     * coalesce(exp1,exp2,exp3,exp4)->nvl(nvl(nvl(exp1,exp2),exp3),exp4) 
     */

    private void translateFunCoalesce(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunCoalesce");

        int iOffSet = 0;
        int iLBracket = 0;
        int iRBracket = 0;
        //��ʶÿ���������ʼλ��
        int iStart = 0;
        java.util.Vector v = new java.util.Vector();
        String sWord = asSqlWords[iOffSet];

        TranslateToInformix newTranslateToInformix = new TranslateToInformix();

        //����ǰ�ַ�Ϊ��coalesce��������һ���ַ�Ϊ������
        if (sWord.equalsIgnoreCase("coalesce") && asSqlWords[iOffSet + 1].equals("(")) {
            m_bFinded = true;
            m_sbDestinationSql.append(" ");
            //��ʶ��1
            iOffSet++;
            //��ʼλ�ü�2
            iStart += 2;
            //����־С���ַ����ȣ���ѭ��
            while (iOffSet < asSqlWords.length) {
                iOffSet++;
                //����ǰ�ַ�Ϊ�����ţ������־��1
                if (asSqlWords[iOffSet].equals("("))
                    iLBracket++;
                //����ǰ�ַ�Ϊ�����ţ����ұ�־��1
                if (asSqlWords[iOffSet].equals(")"))
                    iRBracket++;
                //�����ұ�ʶ��ȣ��ҵ�ǰ�ַ�Ϊ����
                if (iLBracket == iRBracket && asSqlWords[iOffSet].equals(",")) {
                    String str = new String();
                    //����ӿ�ʼλ�õ���ʶλ�õ��ַ�
                    for (int i = iStart; i < iOffSet; i++) {
                        str += " " + asSqlWords[i];
                    }

                    if (str.indexOf("(") >= 0) {
                        newTranslateToInformix.setSql(str);
                        str = newTranslateToInformix.getSql();
                    }
                    v.addElement(str);
                    //��ʼλ��Ϊ��ʶλ�ü�1
                    iStart = iOffSet + 1;
                }
                //�����ұ�ʶ��ȣ�����һ���ַ�Ϊ������
                if ((iLBracket == iRBracket) && asSqlWords[iOffSet + 1].equals(")")) {
                    String str = new String();
                    //����ӿ�ʼλ�õ���ʶλ�ü�1���ַ�
                    for (int i = iStart; i < iOffSet + 1; i++) {
                        str += " " + asSqlWords[i];
                    }

                    if (str.indexOf("(") >= 0) {
                        newTranslateToInformix.setSql(str);
                        str = newTranslateToInformix.getSql();
                    }

                    v.addElement(str);
                    String s = new String();

                    //����nvl
                    for (int i = 1; i < v.size(); i++)
                        m_sbDestinationSql.append("nvl(");
                    m_sbDestinationSql.append(v.elementAt(0));

                    //��ʽ����
                    for (int j = 1; j < v.size(); j++) {
                        s += " " + ",";
                        s += " " + v.elementAt(j);
                        s += " " + ")";
                    }
                    m_sbDestinationSql.append(s);
                    //translateSelect(parseSql(s));
                    break;
                }
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateFunCoalesce Over");
    }

    /**
     * ת��Update���
     */
    private StringBuffer translateUpdate(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateUpdateII");

        int iOffSet = 0; //ƫ����
        String[] asWords = null;
        String sSql = "";
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBracket;
        String s = new String();

        //�������Ӹ���
        asWords = translateJoinUpdate(asSqlWords);

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToInformix.translateUpdateII Over");
        return translateSelect(asWords);

    }
}