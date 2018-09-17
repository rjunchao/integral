package com.xbkj.common.bs.mw.sqltrans;

import java.util.Vector;

import com.xbkj.log.bs.logging.Logger;

/**
 *ģ��:	TranslateToDB2.java
 *����:	��SqlServer��䷭�뵽DB2���
 *����:	cf
 */

public class TranslateToDB2 extends TranslatorObject {

    //������ձ�,�г�sqlServer������DB2����Ķ�Ӧ��ϵ,
    private String[][] fun_db2 = { { "len", "length" }, { "substring", "substr" }, { "lower", "lcase" }, { "upper", "ucase" }, { "isnull", "coalesce" }
    //{"square","power"}		//square(f)->power(f,2)
    };

    //ErrorCode���ձ�,�г�sqlServer ErrorCode��DB2 ErrorCode�Ķ�Ӧ��ϵ ��ʽ:{db2,SqlServer}
    private int[][] err_db2 = { { -204, 208 }, //�����ͼ������
            { -104, 2715 }, //�������
            { -206, 207 }, //��Ч������
            { -421, 205 }, //ʹ��union������Ŀ���б�Ӧ������ͬ��Ŀ�ı��ʽ
            { -408, 213 }, //������ݺͱ�������Ͳ�һ��
            { -803, 2627 }, //���ܲ�����ͬ����ļ�¼
            { -407, 515 }, //��ֵ����Ϊ��
            { -433, 8152 } //�����ֵ�����й��
    };

    /**
     * TransDB2 ������ע�͡�
     */
    public TranslateToDB2() {
        super(DB2);
        m_apsFunList = fun_db2;
        m_apiErrorList = err_db2;
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2 Over");
    }

    public String getSql() throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.getSql");
        //����SQL���
        translateSql();
        //��������Ϊ�գ��򷵻ؿ�
        if (m_sbDestinationSql == null)
            return null;
        String sResult = m_sbDestinationSql.toString();
        //���������ԷֺŽ�β����ȥ���ֺ�
        if (sResult.endsWith(";"))
            sResult = sResult.substring(0, sResult.length() - 1);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.getSql Over");
        return sResult;
    }

    /**
     *ת��Create���
     */

    private void translateCreate() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateCreate");
        m_sbDestinationSql = new StringBuffer(m_sResorceSQL);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateCreate Over");
    }

    /**
     *ת��Delete���
     */

    private StringBuffer translateDelete(String[] sqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateDelete");

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
                    translateFunPatindex(parseSql(sSql));
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateDelete Over");
        return m_sbDestinationSql;

    }

    /**
     * ת��Drop���
     */
    private void translateDrop() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateDrop");
        m_sbDestinationSql = new StringBuffer(m_sResorceSQL);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateDrop Over");
    }

    /**
     *��ݺ�����ձ���к���ת��
     */
    private void translateFunction() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunction");

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
                m_asSqlWords[iOffSet] = "date";
                iOffSet++;
            }
            if (m_asSqlWords[iOffSet + 1].equals("(")) {
                m_asSqlWords[iOffSet] = getFunction(sWord);
                iOffSet++;
            }
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunction Over");
    }

    /**
     * ת��"||"
     * ����:
     * off ƫ����
     * ����:
     * ƫ����
     * ����:
     * ��Oracle������ת��	
     */
    private int translateII(int ioff) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateII");
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateII Over");
        return ioff;
    }

    /**
     * ת��Insert���
     */
    private StringBuffer translateInsert(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateInsert");

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
                    translateFunPatindex(parseSql(sSql));
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
//                if (s.indexOf("[^") > 0 && s.indexOf("]") > 0) {
//                    sSql = "";
//                    sSql = sSql + s.substring(0, s.indexOf("["));
//                    sSql = sSql + s.substring(s.indexOf("^") + 1, s.indexOf("]"));
//                    sSql = sSql + s.substring(s.indexOf("]") + 1);
//                    m_sbDestinationSql.append(" not like " + sSql);
//                    iOffSet += 2;
//                }
//                else   if (sWord.indexOf("[") > 0 && sWord.indexOf("]") > 0) {
//                    sSql = "";
//                    sSql = sSql + sWord.substring(0, sWord.indexOf("["));
//                    sSql = sSql + sWord.substring(sWord.indexOf("[") + 1, sWord.indexOf("]"));
//                    sSql = sSql + sWord.substring(sWord.indexOf("]") + 1);
//                    m_sbDestinationSql.append(" " + sSql);
//                    iOffSet++;
//                }
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateInsert Over");
        return m_sbDestinationSql;

    }

    /**
     * �������Ӹ���
     * �������ڣ�(00-6-9 8:38:35)
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateJoinUpdate");

        int iOffSet = 0; //ƫ����
        int iOffSet1 = 1; //ȡ�����ƫ����
        String[] asWords = asSqlWords;
        //String sSql = ""; //����ֵ
        boolean bFind = false;
        //String sLeftField = "";
        //String sRightField = "";
        //java.util.Vector vSetList = new java.util.Vector(); //���set���
        String sTableName = ""; //����
        String sTableAlias = ""; //��ı���
        //String s = ""; //�м����
        //Vector vecTable = new Vector();

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
                                } /*
                                 else if (iOffSet >= 2 && (asWords[iOffSet - 2].equalsIgnoreCase(",")))
                                 {
                                 sTableAlias = "";
                                 }*/
                                else if (iOffSet >= 2 && (asWords[iOffSet - 2].equalsIgnoreCase(",") || asWords[iOffSet - 2].equalsIgnoreCase("from"))) {
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
            Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateJoinUpdate Over");
            return translateJoinUpdate(asSqlWords, sTableName, sTableAlias);
        }
    }

    /**
     * ����Sql���,����:
     * ����ת��
     */
    private StringBuffer translateSelect(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateSelect");

        int iOffSet = 0;
        String sSql = new String();
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBracket;
        //added by zhangsd
        String topNum = "";
        String replaceTop = "";
        boolean hasTop = false;
        boolean hasWhere = false; //zhangsd add 20020808
        boolean isUpdate = false; //zhangsd add 20020812
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
            //�����Ż��ؼ���
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
            //����ȡģ%
            if (iOffSet + 2 < asSqlWords.length && asSqlWords[iOffSet + 1].equals("%")) {
                m_sbDestinationSql.append(" mod(" + sWord + "," + asSqlWords[iOffSet + 2] + ")");
                iOffSet += 3;
            }

            //����ģʽƥ���
//            if (sWord.equalsIgnoreCase("like")) {
//                String s = new String();
//                s = asSqlWords[iOffSet + 1];
//                if (s.indexOf("^") > 0 && s.indexOf("]") > 0) {
//                    sSql = "";
//                    sSql = sSql + s.substring(0, s.indexOf("["));
//                    sSql = sSql + s.substring(s.indexOf("^") + 1, s.indexOf("]"));
//                    sSql = sSql + s.substring(s.indexOf("]") + 1);
//                    m_sbDestinationSql.append(" not like " + sSql);
//                    iOffSet += 2;
//                    
//                } 
                
//                else if (s.indexOf("[") > 0 && s.indexOf("]") > 0) {
//                    sSql = "";
//                    sSql = sSql + s.substring(0, s.indexOf("["));
//                    sSql = sSql + s.substring(s.indexOf("[") + 1, s.indexOf("]"));
//                    sSql = sSql + s.substring(s.indexOf("]") + 1);
//                    m_sbDestinationSql.append(" " + sSql);
//                    iOffSet++;
//                }
 //           }
            if (iOffSet < asSqlWords.length && iOffSet > 0 && asSqlWords[iOffSet].equalsIgnoreCase("select") && asSqlWords[iOffSet - 1].equals("(")) {
                aTransUnit = dealSelect(asSqlWords, sWord, iOffSet);
                iOffSet = aTransUnit.getIOffSet();

            }
            if (asSqlWords[iOffSet].equalsIgnoreCase("top")) {
                topNum = asSqlWords[iOffSet + 1];
                replaceTop = "fetch first " + topNum + " rows only";
                hasTop = true;
                iOffSet += 2;
            }
            //�жϴ���columnname+'zhangsd'
            if (iOffSet < asSqlWords.length - 1 && asSqlWords[iOffSet].equalsIgnoreCase("+")
                    && (asSqlWords[iOffSet + 1].indexOf("'") > -1 || asSqlWords[iOffSet - 1].indexOf("'") > -1)) {
                m_sbDestinationSql.append("||");
                iOffSet++;

            }
            if (asSqlWords[iOffSet].equalsIgnoreCase("where")) {
                hasWhere = true;
            }
            if (asSqlWords[iOffSet].equalsIgnoreCase("null") && iOffSet > 0 && iOffSet < 2 && !asSqlWords[iOffSet - 1].equals("=")
                    && !asSqlWords[iOffSet - 1].equals("(")) {
                m_sbDestinationSql.append(" nullif('1','1')");
                iOffSet++;
            } else if (asSqlWords[iOffSet].equalsIgnoreCase("null") && iOffSet < asSqlWords.length && iOffSet > 0 && !asSqlWords[iOffSet - 1].equals("=")
                    && !asSqlWords[iOffSet - 1].equalsIgnoreCase("is") && iOffSet > 1 && !asSqlWords[iOffSet - 1].equalsIgnoreCase("then")
                    && (!asSqlWords[iOffSet - 2].equalsIgnoreCase("is") && !asSqlWords[iOffSet - 1].equalsIgnoreCase("not")) && iOffSet > 1
                    && !((asSqlWords[iOffSet - 2].equalsIgnoreCase("cast"))  && (asSqlWords[iOffSet - 1].equalsIgnoreCase("(")))   ) {
                m_sbDestinationSql.append(" nullif('1','1')");
                iOffSet++;
            } 
            else if (asSqlWords[iOffSet].equalsIgnoreCase("=") && iOffSet < asSqlWords.length && iOffSet > 0 && hasWhere
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
        if (hasTop) {
            m_sbDestinationSql.append(" " + replaceTop);
        }
        if (m_sbDestinationSql != null) {
            m_sbDestinationSql.replace(0, m_sbDestinationSql.toString().length(), m_sbDestinationSql.toString().trim());
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateSelect Over");
        return m_sbDestinationSql;

    }

    /**
     *���������ͽ���ת��
     */

    private void translateSql() throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateSql");

        //����sql���,�õ�sql��������
        m_sbDestinationSql = new StringBuffer();
        //m_asSqlWords = parseSql(m_sResorceSQL);
        if (m_asSqlWords == null) {
            m_sbDestinationSql = null;
            Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateSql Over");
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
            //zhangsd modify 20020808
            //translateDelete(m_asSqlWords);
            translateSelect(m_asSqlWords);
            break;
        case SQL_UPDATE:
            //translateUpdate(m_asSqlWords);
            translateUpdateII(m_asSqlWords);
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
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private String appendString(String[] asSqlWords, int start, int end) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.appendString");
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.appendString Over");
        return result;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    public TransUnit dealFunction(String[] asSqlWords, String sWord, int iOffSet) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dealFunction");

        Vector vec = new Vector();
        vec.addElement(asSqlWords[iOffSet]);

        //vec.addElement(asSqlWords[iOffSet + 1]);
        //iOffSet += 2;
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
            translateFunCast(newFuncSql);
        } else if (sWord.equalsIgnoreCase("coalesce")) {
            //translateFunCoalesce(newFuncSql);
        } else if (sWord.equalsIgnoreCase("ltrim")) {
            //translateFunLtrim(newFuncSql);
        } else if (sWord.equalsIgnoreCase("rtrim")) {
            //translateFunRtrim(newFuncSql);
        } else if (sWord.equalsIgnoreCase("patindex")) {
            translateFunPatindex(newFuncSql);
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
            //iOffSet-=newFuncSql.length;
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dealFunction Over");
        return new TransUnit(null, null, iOffSet);
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    public TransUnit dealSelect(String[] asSqlWords, String sWord, int iOffSet) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dealSelect");

        if (iOffSet < asSqlWords.length) {

            TransUnit aTransUnit = getSubSql(asSqlWords, "(", ")", iOffSet);
            String[] newCaseSql = aTransUnit.getSqlArray();
            iOffSet = aTransUnit.getIOffSet();

            String newSql[] = new String[newCaseSql.length - 1];

            for (int i = 0; i < newSql.length; i++) {
                newSql[i] = newCaseSql[i];
            }

            //�����Ӳ�ѯ
            TranslateToDB2 newTranslateToDb2 = new TranslateToDB2();

            newTranslateToDb2.setSqlArray(newSql);

            m_sbDestinationSql.append(newTranslateToDb2.getSql());
        }

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dealSelect Over");
        return new TransUnit(null, null, iOffSet);
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private String dropTable(String[] asSqlWords, int index) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dropTable");
        String s = "";
        if (asSqlWords[asSqlWords.length - 1].equalsIgnoreCase("go")) {
            asSqlWords[asSqlWords.length - 1] = ";";
        }
        s = appendString(asSqlWords, index, asSqlWords.length - 1);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.dropTable Over");
        return s;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    public int getStatementType() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.getStatementType");
        int iType = 0;
        //��ʱ��  2002.03.07�޸�
        //����䳤��С��2������ʾ����
        if (m_asSqlWords.length < 1) {
            return iType;
        }
        //�޸Ľ���
        //���αȽ��������
        if (m_asSqlWords[0].equalsIgnoreCase("SELECT")) {
            iType = SQL_SELECT;
        } else if (m_asSqlWords[0].equalsIgnoreCase("INSERT")) {
            iType = SQL_INSERT;
        } else if (m_asSqlWords[0].equalsIgnoreCase("CREATE")) {
            if (m_asSqlWords.length > 1 && m_asSqlWords[1].equalsIgnoreCase("view")) {
                //create view
                iType = SQL_SELECT; //2001.11.06 ��ɭ�޸ģ�Ϊ��֧��cteate view
            } else {
                //create table
                iType = SQL_CREATE;
            }
        } else if (m_asSqlWords[0].equalsIgnoreCase("DROP")) {
            iType = SQL_DROP;
        } else if (m_asSqlWords[0].equalsIgnoreCase("DELETE")) {
            iType = SQL_DELETE;
        } else if (m_asSqlWords[0].equalsIgnoreCase("UPDATE")) {
            iType = SQL_UPDATE;
        } else if (m_asSqlWords[0].equalsIgnoreCase("EXPLAIN")) {
            iType = SQL_EXPLAIN;

        } else if (m_asSqlWords[0].equalsIgnoreCase("if") && m_asSqlWords[1].equalsIgnoreCase("exists")) {
            iType = 8;
        } else {
            iType = SQL_SELECT;
        }
        //�����������
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.getStatementType Over");
        return iType;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private boolean hasWhere(String[] asWords, int iOffSet) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.hasWhere");
        if (asWords == null)
            return false;
        for (int i = 0; i < asWords.length; i++) {
            if (asWords[i].equalsIgnoreCase("where"))
                return true;
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.hasWhere Over");
        return false;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    public boolean isFunctionName(String sWord, String nextWord) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.isFunctionName");
        boolean isFunc = false;

        if ((//(sWord.equalsIgnoreCase("left")&& !nextWord.equalsIgnoreCase("outer")) /** 1 **/
                //|| (sWord.equalsIgnoreCase("right")&& !nextWord.equalsIgnoreCase("outer")) /** 2 **/
                sWord.equalsIgnoreCase("square") //��ǰ����Ϊ��square��
                        || sWord.equalsIgnoreCase("cast") //��ǰ����Ϊ��cast��
                        //|| sWord.equalsIgnoreCase("coalesce")
                        //|| sWord.equalsIgnoreCase("ltrim")
                        //|| sWord.equalsIgnoreCase("rtrim")
                        || sWord.equalsIgnoreCase("patindex") //|| sWord.equalsIgnoreCase("len")
                        || sWord.equalsIgnoreCase("round") || sWord.equalsIgnoreCase("convert") || sWord.equalsIgnoreCase("dateadd") || sWord
                        .equalsIgnoreCase("datediff")) //����һ�������ǡ�(��
                && nextWord.equals("(")) {
            isFunc = true;
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.isFunctionName Over");
        return isFunc;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private int isHasWord(String asSqlWords[], String s) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.isHasWord");
        if (asSqlWords == null || asSqlWords.length < 1)
            return -1;
        int pos = -1;
        int i = 0;
        while (i < asSqlWords.length && (!asSqlWords[i].equalsIgnoreCase(s))) {
            i++;
        }
        if (i < asSqlWords.length)
            pos = i;
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.isHasWord Over");
        return pos;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private void newMethod() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.newMethod");
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.newMethod Over");
    }

    /**
     * �ڱ��ʽ�еȺź��Ѱ���Ƿ���ָ���ı�����ı�������ı�����ı���
     * ����У�����true,���򣬷���false
     * Author:ljq
     * @return boolean
     * @param s java.lang.String���ʽ
     * @param sTableName java.lang.String����
     * @param sTableAlias java.lang.String�����
     */
    public boolean searchHaveOther(String s, String sTableName, String sTableAlias) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.searchHaveOther");
        String sDotString = "";
        boolean bFlg = false;
        int iStart = 0;
        int iEnd = 0;
        String s0 = "";

        //�ڱ��ʽ�У������һ������ڵȺŵĺ��
        if (s.lastIndexOf(".") > s.indexOf("=")) //��ɭ2001.3.17   ��
        {
            //ȡ�õȺŵ�λ��
            iStart = s.indexOf("=");
            //��ȡ�Ⱥź���ַ�
            sDotString = s.substring(iStart);
            //ȡ����һ���Ⱥŵ�λ��
            iStart = sDotString.indexOf("=");
            int i0 = 0;
            //����ǰλ��С�ڽغ��ַ�ĳ��ȼ�1
            while (i0 < sDotString.length() - 1) {
                //����ǰλ�õ��ַ�Ϊ�����+������-������*����/��
                if (sDotString.substring(i0, i0 + 1).equals("+") || sDotString.substring(i0, i0 + 1).equals("-")
                        || sDotString.substring(i0, i0 + 1).equals("*") || sDotString.substring(i0, i0 + 1).equals("/")
                        || sDotString.substring(i0, i0 + 1).equals("%") //��ɭ2001.3.17 ��
                        || (sDotString.substring(i0, i0 + 1).equals("|") && sDotString.substring(i0 + 1, i0 + 2).equals("|")) //�ж��ַ�����
                        || sDotString.substring(i0, i0 + 1).equals("(") //�жϺ���
                        || sDotString.substring(i0, i0 + 1).equals(")")) {
                    if (sDotString.substring(i0, i0 + 1).equals("(")) //��ɭ2001.3.17 ��,����������ţ������
                    {
                        iStart = i0;
                    } else {
                        //��ȡ�����ǰ���ַ�
                        s0 = sDotString.substring(iStart + 1, i0);
                        //����ȡ�ַ��а���
                        if (s0.indexOf(".") > 0) {
                            //ȡ�õ�ŵ�λ��
                            iEnd = s0.indexOf(".");
                            //����ȡ�ַ�����Ŀ������Ҳ�����Ŀ���������˳�ѭ�����ұ�־Ϊ��
                            if (!s0.substring(iStart, iEnd).equalsIgnoreCase(sTableName) && !s0.substring(iStart, iEnd).equalsIgnoreCase(sTableAlias)) {
                                bFlg = true;
                                break;

                            } else {
                                //����ȡ�ַ����Ŀ������ҵ���Ŀ������//��ɭ2001.3.17 ��
                                if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||"
                                        && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                                    iStart = i0 + 1;
                                } else {
                                    iStart = i0;
                                }
                            }
                        } else { //��������
                            if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||",//��ɭ2001.3.17 ��
                                    && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                                iStart = i0 + 1;
                            } else {
                                iStart = i0;
                            }

                        }
                    }
                }

                if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||"
                        && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                    i0 += 2; //������� "||",//��ɭ2001.3.17 ��
                } else {
                    i0++;
                }
            }

            //��δ�ҵ�,������һ��"."
            if (!bFlg) {
                s0 = sDotString.substring(iStart + 1);
                if (s0.indexOf(".") > 0) {
                    iEnd = s0.indexOf(".");
                    /*
                     if (!s0.substring(iStart + 1, iEnd).equalsIgnoreCase(sTableName)//��ɭ2001.3.17  �ص������
                     && !s0.substring(iStart + 1, iEnd).equalsIgnoreCase(sTableAlias))
                     */
                    if (!s0.substring(0, iEnd).equalsIgnoreCase(sTableName) //��ɭ2001.3.17 ��
                            && !s0.substring(0, iEnd).equalsIgnoreCase(sTableAlias)) {
                        bFlg = true;
                    }
                }
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.searchHaveOther Over");
        return bFlg;
    }

    /**
     * �ڱ��ʽ�еȺź��]Ѱ��ָ���ı�����ı���
     * ����У�����true,���򣬷���false
     * Author:ljq
     * @return boolean
     * @param s java.lang.String���ʽ
     * @param sTableName java.lang.String����
     * @param sTableAlias java.lang.String�����
     */
    public boolean searchHaving(String s, String sTableName, String sTableAlias) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.searchHaving");
        String sDotString = "";
        boolean bFlg = false;
        int iStart = 0;
        int iEnd = 0;
        String s0 = "";

        //�ڱ��ʽ�У������һ������ڵȺŵĺ��
        if (s.lastIndexOf(".") > s.indexOf("=")) //��ɭ2001.3.17  ��
        {
            //ȡ�õȺŵ�λ��
            iStart = s.indexOf("=");
            //��ȡ�Ⱥź���ַ�
            sDotString = s.substring(iStart);
            //ȡ����һ���Ⱥŵ�λ��
            iStart = sDotString.indexOf("=");
            int i0 = 0;
            //����ǰλ��С�ڽغ��ַ�ĳ��ȼ�1
            while (i0 < sDotString.length() - 1) {
                //����ǰλ�õ��ַ�Ϊ�����+������-������*����/��
                if (sDotString.substring(i0, i0 + 1).equals("+") || sDotString.substring(i0, i0 + 1).equals("-")
                        || sDotString.substring(i0, i0 + 1).equals("*") || sDotString.substring(i0, i0 + 1).equals("/")
                        || sDotString.substring(i0, i0 + 1).equals("%") //��ɭ2001.3.17 ��
                        || (sDotString.substring(i0, i0 + 1).equals("|") && sDotString.substring(i0 + 1, i0 + 2).equals("|")) //�ж��ַ�����
                        || sDotString.substring(i0, i0 + 1).equals("(") //�жϺ���
                        || sDotString.substring(i0, i0 + 1).equals(")")) {
                    if (sDotString.substring(i0, i0 + 1).equals("(")) //��ɭ2001.3.17 ��,����������ţ������
                    {
                        iStart = i0;
                    } else {
                        //��ȡ�����ǰ���ַ�
                        s0 = sDotString.substring(iStart + 1, i0);
                        //����ȡ�ַ��а���
                        if (s0.indexOf(".") > 0) {
                            //ȡ�õ�ŵ�λ��
                            iEnd = s0.indexOf(".");
                            //����ȡ�ַ����Ŀ���������Ŀ���������˳�ѭ�����ұ�־Ϊ��
                            if (s0.substring(iStart, iEnd).equalsIgnoreCase(sTableName) || s0.substring(iStart, iEnd).equalsIgnoreCase(sTableAlias)) {
                                bFlg = true;
                                break;

                            } else {
                                //����ȡ�ַ����Ŀ������ҵ���Ŀ������//��ɭ2001.3.17 ��
                                if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||"
                                        && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                                    iStart = i0 + 1;
                                } else {
                                    iStart = i0;
                                }
                            }
                        } else { //��������
                            if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||",//��ɭ2001.3.17 ��
                                    && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                                iStart = i0 + 1;
                            } else {
                                iStart = i0;
                            }

                        }
                    }
                }

                if (sDotString.substring(i0, i0 + 1).equals("|") //������� "||"
                        && sDotString.substring(i0 + 1, i0 + 2).equals("|")) {
                    i0 += 2; //������� "||",//��ɭ2001.3.17 ��
                } else {
                    i0++;
                }
            }

            //��δ�ҵ�,������һ��"."
            if (!bFlg) {
                s0 = sDotString.substring(iStart + 1);
                if (s0.indexOf(".") > 0) {
                    iEnd = s0.indexOf(".");
                    /*
                     if (!s0.substring(iStart + 1, iEnd).equalsIgnoreCase(sTableName)//��ɭ2001.3.17  �ص������
                     && !s0.substring(iStart + 1, iEnd).equalsIgnoreCase(sTableAlias))
                     */
                    if (s0.substring(0, iEnd).equalsIgnoreCase(sTableName) //��ɭ2001.3.17 ��
                            || s0.substring(0, iEnd).equalsIgnoreCase(sTableAlias)) {
                        bFlg = true;
                    }
                }
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.searchHaving Over");
        return bFlg;
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunConvert(String[] asWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunConvert");

    	int iOff = 2;
    	int iLBracket = 0;
    	int iRBracket = 0;
    	boolean isDateToChar = false;
    	boolean charLenCtrl = false;
    	boolean isDate = false;
    	boolean isChar = false;
    	String charLenth = null;
    	String dataType = "";
    	String col = "";
    	//ȡ������Ĳ���
    	String params[] = getFunParam(asWords, iOff, asWords.length - 1);
    	dataType = params[0];
    	col = params[1];
    	dataType = dataType.trim();
    	String oldDataType = dataType;
    	//ȡ���������
    	if (dataType.indexOf("(") > 0) {
    		dataType = dataType.substring(0, dataType.indexOf("("));
    	}
    	if (col.equalsIgnoreCase("null"))
    		m_sbDestinationSql.append(" cast(");
    	else {
    		//�����������
    		if (isDateType(dataType)) {
    			isDate = true;
    			m_sbDestinationSql.append(" timestamp(");
    		}
    		else
    			if (isCharType(dataType)) {
    				isChar = true;
    				if (oldDataType.indexOf("(") > 0) {
    					charLenCtrl = true;
    					charLenth =
    						oldDataType.substring(oldDataType.indexOf("(") + 1, oldDataType.length() - 1);
    				}
    				if (charLenCtrl)
    					m_sbDestinationSql.append(" substr(char(");
    				else
    					m_sbDestinationSql.append(" char(");
    				if (params.length == 3 && params[2] != null) {
    					if (params[2].trim().equals("21"))
    						isDateToChar = true;
    				}
    			}
    			else
    				m_sbDestinationSql.append(" cast(");
    	}
    	try {
    		translateSelect(parseSql(col));

    		if (isChar) {
    			if (charLenCtrl)
    				m_sbDestinationSql.append(") ,1," + charLenth + " )");
    			else
    				m_sbDestinationSql.append(" )");
    		}
    		else
    			if (isDate) {
    				m_sbDestinationSql.append(")");
    			}
    			else
    				m_sbDestinationSql.append(" as " + oldDataType + ")");

    	}
    	catch (Exception e) {
    		throw e;
    	}
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunConvert Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunDateAdd(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunDateAdd");

        int iOff = 2;

        String params[] = getFunParam(asWords, iOff, asWords.length - 1);

        String dateType = params[0].trim();

        String theNumber = params[1].trim();

        String theDate = params[2].trim();

        TranslateToDB2 newTranslateToDB2 = new TranslateToDB2();
        try {
            newTranslateToDB2.setSql(theDate);

            theDate = newTranslateToDB2.getSql();

            //��ʱ���޸� 2002-05-22
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunDateAdd Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunDateDiff(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunDateDiff");

        int iOff = 2;
        //DATEDIFF(day,  getdate(),'2002-05-15')
        String params[] = getFunParam(asWords, iOff, asWords.length - 1);

        String dateType = params[0].trim();

        String theStart = params[1].trim();

        String theEnd = params[2].trim();

        TranslateToDB2 newTranslateToDB2 = new TranslateToDB2();
        try {
            newTranslateToDB2.setSql(theStart);
            theStart = newTranslateToDB2.getSql();

            newTranslateToDB2.setSql(theEnd);
            theEnd = newTranslateToDB2.getSql();
            //��ʱ���޸� 2002-06-12
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

                    //hgy
                    theStart = "(year(current date)*12 + month(current date))";
                } else {
                    //hgy
                    theStart = "(year("+ theStart +")*12 + month(" + theStart + "))";
                }
                if (theEnd.toLowerCase().startsWith("getdate(") || theEnd.toLowerCase().startsWith("getdate (")
                        || theEnd.toLowerCase().startsWith("getdate  (") || theEnd.toLowerCase().startsWith("getdate   (")) {

                    //hgy
                    theEnd = "(year(current date)*12 + month(current date))";
                } else {
                    //hgy
                    theEnd = "(year(" + theEnd + ")*12 + month(" + theEnd + "))";
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunDateDiff Over");
    }

    /**
     *ת��patindex����
     *����:
     *asWords: patindex�������
     *����:
     *patindex('%exp1%',exp2)->locate('exp1,exp2)
     */

    private void translateFunPatindex(String[] asWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunPatindex");
        int iOff = 2;
        String sSql = "locate(";

        String sWord = asWords[iOff];
        if (sWord.length() > 4 && asWords[iOff + 1].equals(",")) {
            sSql += "'" + sWord.substring(2, sWord.length() - 2) + "',";
            iOff += 2;
            while (iOff < asWords.length) {
                sSql += " " + asWords[iOff];
                iOff++;
            }
            try {
                translateSelect(parseSql(sSql));
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            for (int i = 0; i < asWords.length; i++) {
                m_sbDestinationSql.append(asWords[i]);
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunPatindex Over");
    }

    /**
     * ת��Rtrim����
     * ����:
     * asSqlWords: Ltrim�����Ӿ�
     * ����:
     * Ltrim(str)->Ltrim(str,' ')
     */
    private void translateFunRound(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunRound");
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

        TranslateToDB2 newTranslateToDB2 = null;

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
                        if (newTranslateToDB2 == null) {
                            newTranslateToDB2 = new TranslateToDB2();
                        }

                        newTranslateToDB2.setSql(theNumber);

                        theNumber = newTranslateToDB2.getSql();
                    }
                } else {
                    secondCommaIndex = iOff;

                    for (int i = firstCommaIndex + 1; i < iOff; i++) {
                        theLength += " " + asSqlWords[i];
                    }

                    if (iOff - (firstCommaIndex + 1) > 1) {
                        if (newTranslateToDB2 == null) {
                            newTranslateToDB2 = new TranslateToDB2();
                        }

                        newTranslateToDB2.setSql(theLength);

                        theLength = newTranslateToDB2.getSql();
                    }
                }
            }
            iOff++;
        }

        int fromIndex = 0;
        String s = " ";

        if (commaCount == 0) {
            for (int i = 0; i < asSqlWords.length; i++) {
                s += asSqlWords[i];
            }

        } else if (commaCount == 1) {
            for (int i = firstCommaIndex + 1; i < asSqlWords.length - 1; i++) {
                theLength += " " + asSqlWords[i];
            }

            if ((asSqlWords.length - 1) - (firstCommaIndex + 1) > 1) {
                if (newTranslateToDB2 == null) {
                    newTranslateToDB2 = new TranslateToDB2();
                }

                newTranslateToDB2.setSql(theLength);

                theLength = newTranslateToDB2.getSql();
            }

            s = " round(" + theNumber + ", " + theLength + ") ";
        } else {
            for (int i = secondCommaIndex + 1; i < asSqlWords.length - 1; i++) {
                theTyle += " " + asSqlWords[i];
            }

            if ((asSqlWords.length - 1) - (secondCommaIndex + 1) > 1) {
                if (newTranslateToDB2 == null) {
                    newTranslateToDB2 = new TranslateToDB2();
                }

                newTranslateToDB2.setSql(theTyle);

                theTyle = newTranslateToDB2.getSql();
            }

            int tyle = Integer.valueOf(theTyle.trim()).intValue();

            if (tyle == 0) {
                s = " round(" + theNumber + ", " + theLength + ") ";
            } else {
                //s =" floor(" + theNumber + "*(10**" + theLength + "))/(10**" + theLength + ") ";
                s = " floor(" + theNumber + "*(power(10.0," + theLength + ")))/(power(10.0," + theLength + ")) ";
            }
        }
        m_sbDestinationSql.append(s);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunRound Over");
    }

    /**
     *ת��Square����
     *	����:
     *		asWords �����Ӿ�
     *	����:
     *		square(f)->power(f,2)
     */

    private void translateFunSquare(String[] asWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunSquare");
        String s = new String();
        int iOff = 2;
        int iLBracket = 0;
        int iRBracket = 0;
        s += "power(";
        while (iOff < (asWords.length)) {
            if (asWords[iOff].equals("("))
                iLBracket++;
            if (asWords[iOff].equals(")"))
                iRBracket++;
            if ((iLBracket == iRBracket) && asWords[iOff + 1].equals(")")) {
                s += asWords[iOff];
                iOff++;
                s += ",2";
            }
            s += asWords[iOff];
            iOff++;
        }
        try {
            translateSelect(parseSql(s));
        } catch (Exception e) {
            System.out.println(e);
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateFunSquare Over");
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2002-4-3 19:50:50)
     */
    private void translateIFExists(String[] asSqlWords) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateIFExists");
        int index = -1;
        String result = "";
        if ((index = isHasWord(asSqlWords, "drop")) > -1 && asSqlWords[index + 1].equalsIgnoreCase("table")) {
            result = dropTable(asSqlWords, index);
        }
        m_sbDestinationSql.append(result);
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateIFExists Over");
    }

    /**
     * �������Ӹ���
     * �������ڣ�(00-6-9 8:38:35)
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
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateJoinUpdate");

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
        boolean bExist = true;
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
                //�������Ϊ�գ����¼֮

                if (!sTableAlias.equalsIgnoreCase("") && !sTableAlias.equalsIgnoreCase(sTableName)) {
                    sSql += " " + sTableAlias;
                    //vSql.addElement(sTableAlias);
                }

                sSql += " set";
                //vSql.addElement("set");
                iOffSet++;

                int leftCount = 0; //��������
                int rightCount = 0; //��������

                //����ǰ�ַ��ǡ�from������ѭ��
                while (iOffSet < asWords.length && !asWords[iOffSet].equalsIgnoreCase("from")) {
                    if (asWords[iOffSet].equalsIgnoreCase("(")) //����������ɭ��2001��3��17 ��
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
                        sSql += "(" + sLeftField + ")=(select " + sRightField; //����ʱ������������䣬ƴ�ӵ�sql���
                    else if (iJoinCount == 1)
                        sSql += "" + sLeftField + "=(select " + sRightField;
                } else {
                    if (iJoinCount > 1)
                        sSql += setsql + ",(" + sLeftField + ")=(select " + sRightField;
                    else if (iJoinCount == 1)
                        sSql += setsql + "," + sLeftField + "=(select " + sRightField;
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

            } //if (asWords[iOffSet].equalsIgnoreCase("from"))����

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
            }
            //sSql += " " + asWords[iOffSet];
            //iOffSet++;
            //���뵥һ�����������Ӹ�������
            /*				while (iOffSet < asWords.length) {
             sSql += " " + asWords[iOffSet];
             whereSql += " " + asWords[iOffSet];
             iOffSet++;
             } //while (iOffSet < asWords.length)����
             } //if (asWords[iOffSet].equalsIgnoreCase("where"))����

             *///����CASE WHEN
            if (iOffSet < asWords.length && asWords[iOffSet].equalsIgnoreCase("case")) {
                //TransUnit aTransUnit = dealCaseWhen(asWords, asWords[iOffSet], iOffSet);

                //iOffSet = aTransUnit.getIOffSet();

                //String sSql_new = aTransUnit.getSql();

                //sSql += translateJoinUpdate(parseSql(sSql_new));
            } else if (iOffSet < asWords.length) {
                if (asWords[iOffSet].equalsIgnoreCase(sTableName) || asWords[iOffSet].equalsIgnoreCase(sTableAlias)) {
                    sSql += " " + sTableName;
                } else {
                    sSql += " " + asWords[iOffSet];
                }

                //sSql += " " + asWords[iOffSet];
            }
        } //while (iOffSet < asWords.length - 1)����

        m_Sql += ")";
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

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateJoinUpdate Over");
        return asWords;
    }

    /**
     * ת��Update���
     2002-07-22 ��ʱ���øú���
     */
    private StringBuffer translateUpdate_old(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateUpdate_old");

        int iOffSet = 0;
        String sSql = new String();
        String sWord = "";
        String sPreWord = "";
        int iLBracket;
        int iRBracket;
        String[] asWords = null;
        asWords = asSqlWords;
        String sUpdateTable = "";
        String sUpdateAlias = "";
        boolean bInfrom = false;
        //asWords = translateJoinUpdate(asSqlWords);

        TransUnit aTransUnit = null;

        while (iOffSet < asWords.length) {
            sPreWord = sWord;
            sWord = asWords[iOffSet];

            //if(sPreWord.equalsIgnoreCase("update")){
            //if(asSqlWords[iOffSet+1].equalsIgnoreCase("set")){
            //sUpdateTable=asSqlWords[iOffSet];
            //}
            //else if(asSqlWords[iOffSet+2].equalsIgnoreCase("set")){
            //sUpdateTable=asSqlWords[iOffSet];
            //sUpdateAlias=asSqlWords[iOffSet+1];
            //}

            //}
            //���?��
            if ((iOffSet + 1 < asSqlWords.length) && isFunctionName(sWord, asSqlWords[iOffSet + 1])) {
                aTransUnit = dealFunction(asSqlWords, sWord, iOffSet);

                iOffSet = aTransUnit.getIOffSet();

                if (iOffSet > asSqlWords.length - 1) {
                    //����Ƕ��
                    return null;
                }
            }
            //����PI()
            if (asWords[iOffSet].equalsIgnoreCase("PI") && asWords[iOffSet + 1].equals("(") && asWords[iOffSet + 2].equals(")")) {
                m_sbDestinationSql.append(" 3.1415926535897931");
                iOffSet += 3;
            }

            //����ģʽƥ���
            if (asWords[iOffSet].equalsIgnoreCase("like")) {
                String s = new String();
                s = asWords[iOffSet + 1];
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
                if (asWords[iOffSet].indexOf("[") > 0 && asWords[iOffSet].indexOf("]") > 0) {
                    sSql = "";
                    sSql = sSql + asWords[iOffSet].substring(0, asWords[iOffSet].indexOf("["));
                    sSql = sSql + asWords[iOffSet].substring(asWords[iOffSet].indexOf("[") + 1, asWords[iOffSet].indexOf("]"));
                    sSql = sSql + asWords[iOffSet].substring(asWords[iOffSet].indexOf("]") + 1);
                    m_sbDestinationSql.append(" " + sSql);
                    iOffSet++;
                }
            }

            //�����Ӳ�ѯ
            if (asWords[iOffSet].equalsIgnoreCase("select")) {
                int l = 0;
                if (sPreWord.equals("("))
                    l = asWords.length - 1;
                else
                    l = asWords.length;
                sSql = "";
                while (iOffSet < l) {
                    sSql += " " + asWords[iOffSet];
                    iOffSet++;
                }
                m_bSubSelect = true;
                translateSelect(parseSql(sSql));
                m_bSubSelect = false;
            }
            if (asWords[iOffSet].equalsIgnoreCase("from") && !m_bSubSelect) {
                //���from �ĺ����� where
                if (hasWhere(asWords, iOffSet)) {
                    m_bUpdateFrom = true;
                    m_sbDestinationSql.append(" where exists (select 1 ");
                    m_sbDestinationSql.append(asWords[iOffSet]);
                    iOffSet++;
                }
            } else {
                if (iOffSet < asWords.length) {
                    if (!asWords[iOffSet].equals(",") && !asWords[iOffSet].equals(")") && !asWords[iOffSet].equals("]") && !sPreWord.equals("(")
                            && !sPreWord.equals("["))
                        m_sbDestinationSql.append(" ");
                    m_sbDestinationSql.append(asWords[iOffSet]);
                    iOffSet++;
                } else
                    break;
            }
        }
        if (m_bUpdateFrom) {
            if (m_sbDestinationSql.toString().endsWith(";")) {
                m_sbDestinationSql.replace(m_sbDestinationSql.toString().length() - 1, m_sbDestinationSql.toString().length() - 1, ")");
            } else {
                m_sbDestinationSql.append(")");
            }
        }
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateUpdate_old Over");
        return m_sbDestinationSql;

    }

    /**
     * ת��Update���
     */
    private StringBuffer translateUpdateII(String[] asSqlWords) throws Exception {
        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateUpdateII");

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

        Logger.setThreadState("nc.bs.mw.sqltrans.TranslateToDB2.translateUpdateII Over");
        return translateSelect(asWords);

    }
    
    private void translateFunCast(String[] asWords) throws Exception {
    	 Logger.setThreadState(
    		"nc.bs.mw.sqltrans.TranslateToDB2.translateFunCast");

    	String s = new String();
    	s = "";
    	int iOff = 2;
    	int iLBracket = 0;
    	int iRBracket = 0;
    	boolean charLenCtrl = false;
    	boolean isDate = false;
    	boolean isChar = false;
    	String charLenth = null;
    	//δ�������һ����ѭ��
    	while (iOff < (asWords.length - 1)) {
    		//����ǰ�ַ�Ϊ������
    		if (asWords[iOff].equals("("))
    			iLBracket++;
    		//����ǰ�ַ�Ϊ������
    		if (asWords[iOff].equals(")"))
    			iRBracket++;
    		//�����ǵ����ұ�ǣ�����һ���ַ�Ϊ��as��
    		if (iLBracket == iRBracket && asWords[iOff + 1].equalsIgnoreCase("as")) {
    			if (asWords[iOff - 1].equalsIgnoreCase("(")
    				&& asWords[iOff].equalsIgnoreCase("null"))
    				m_sbDestinationSql.append(" cast(");
    			else {
    				if (isDateType(asWords[iOff + 2])) {
    					isDate = true;
    					m_sbDestinationSql.append(" timestamp(");
    				}
    				else
    					if (isCharType(asWords[iOff + 2])) {
    						isChar = true;
    						if (iOff + 4 < asWords.length && asWords[iOff + 3].equals("(")) {
    							charLenCtrl = true;
    							charLenth = asWords[iOff + 4];
    							m_sbDestinationSql.append(" substr(char(");
    						}
    						else {
    							m_sbDestinationSql.append(" char(");
    						}
    					}
    					else {
    						m_sbDestinationSql.append(" cast(");
    					}
    			}
    			s += " " + asWords[iOff];
    			iOff++;
    			break;

    		}
    		s += " " + asWords[iOff];
    		iOff++;
    	}
    	try {
    		translateSelect(parseSql(s));
    		if (isChar) {
    			if (charLenCtrl)
    				m_sbDestinationSql.append(") ,1," + charLenth + " )");
    			else
    				m_sbDestinationSql.append(" )");
    		}
    		else
    			if (isDate) {
    				m_sbDestinationSql.append(")");
    			}
    			else
    				while (iOff < (asWords.length)) {
    					m_sbDestinationSql.append(" " + asWords[iOff]);
    					iOff++;
    				}
    	}
    	catch (Exception e) {
    		throw e;
    	}
    	 Logger.setThreadState(
    		"nc.bs.mw.sqltrans.TranslateToOracle.TranslateToDB2 Over");
    }
}