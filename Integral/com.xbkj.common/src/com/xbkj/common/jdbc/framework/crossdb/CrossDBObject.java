package com.xbkj.common.jdbc.framework.crossdb;
/**
 * @nopublish
 */
import java.util.Calendar;
import java.util.StringTokenizer;

import com.xbkj.basic.vo.pub.util.ISNConvert;

public class CrossDBObject {

    private static boolean bEnableGUConvert = false;
    private static boolean bEnableUGConvert = false;
    protected boolean GU_CONVERT = false;
   
    public static boolean isDebug = false;
    /*
    * Simplelized_Chinese---->>>0---->>>��������
    * Traditional_Chinese---->>>1---->>>��������
    * English---->>>2---->>>Ӣ��(����)
    * ȱʡ����:Simplelized_Chinese---->>>0---->>>��������;
    */
    protected int nModuleLang = 0; //�Ƿ�ת���ַ�
    protected boolean UG_CONVERT = false;

    
    /**
     * SqlObject ������ע�͡�
     */
    public CrossDBObject() {
        super();
        //�Ƿ�ת���ַ�
        UG_CONVERT = bEnableUGConvert;
        GU_CONVERT = bEnableGUConvert;
    }


   

    
    /**
     * ����jdbcOdbc��������ݿ�ʱ��������Ҫͨ��sun.jdbc.odbc.JdbcOdbc��SQLNativeSql()
     * ����SQL�����б��ػ����ڱ��ػ��Ĺ���л����sun.jdbc.odbc.JdbcOdbcObject��
     * CharsToBytes("GBK", sqlChars)����char����ת��Ϊbyte���顣�ڴ˹���У�������N��
     * ˫�ֽڵ����,����ÿһ��������ת��(������ת�����ַ���Ϊ300Char),ת�����byte[]
     * ����ȷ��byte[]��������N��byte�����һ�����(ת�����ַ���<300Char)��ת�����
     * ���У�������������һ�����ַ�'\0', ��˽��ᶪʧN-1��Byte����ӵ�����'\0'��
     * �Ӷ������������������⡣
     * ���µĽ���취:
     * 1.����ÿ300��Char,����������˫�ֽ��ַ�ĸ���N��������ȥ��N��byte��ӵ������
     * Char�����У��������˹�����д��?
     * 2.����ʣ�µ�<300Char�Ĳ��֣�����"GBK"���뷽ʽ�õ���byte[]�����(N-1)��byte + '\0'
     * ���������󣬿��Եõ���ȷ�Ľ��
     * 3.����û��˫�ֽ��ַ��Case���������κδ��?
     * ��־ǿ
     * 2001-6-25 �޸�
     */
    public String fixJdbcOdbcCharToByte(String originSQL)
            throws java.sql.SQLException {
         //����ǿգ���ֱ�ӷ���null
        if (originSQL == null || originSQL.equals(""))
            return originSQL;

        //��ȥ�ո�
        //originSQL = originSQL.trim();

        //�����˫�ֽ��ַ���ֱ�ӷ���ԭ�����ַ�
        if (originSQL.getBytes().length == originSQL.length())
            return originSQL;

        //���ע�������������޲����ԣ����Ժ���˫�ֽ��ַ��SQL ����������
        String retSQL = "";
        boolean bFixError = false;
        int nFixedTimeCounter = 0;

        try {
            int nBatchSize = 300;
            char[] batchChars = new char[nBatchSize];
            String unTreatedString = originSQL;
            boolean endOfSQL = false;
            String strByteCodingFormat = "GBK";
            int nCharCount = 0;
            char[] unTreatedChars = null;
            String retSQLBackup = "";
            String retStatusString = null;

            while (!endOfSQL) {
                //�ж��Ƿ����ڽ��пո�岹��������ǣ��򽫼��������㣻
                if (retStatusString == null || !retStatusString.equals(retSQL)) {
                    nFixedTimeCounter = 0;
                    retStatusString = new String(retSQL);
                }
                //���Ƚ����ֳ�����
            //    unTreatedStringBackup = new String(unTreatedString);
                retSQLBackup = new String(retSQL);
                unTreatedChars = unTreatedString.toCharArray();
                if (unTreatedChars.length >= nBatchSize) {
                    nCharCount = nBatchSize;
                    System.arraycopy(unTreatedChars, 0, batchChars, 0, nCharCount);

                    char[] leftChars = new char[unTreatedChars.length - nCharCount];
                    System.arraycopy(unTreatedChars, nCharCount, leftChars, 0, leftChars.length);
                    unTreatedString = new String(leftChars);

                    String strBatch = new String(batchChars);

             //       System.out.println("String Batch: " + strBatch);

                    retSQL += strBatch;

                    int nCountOfDoubleByteChar = strBatch.getBytes().length - nCharCount;
                    if (nCountOfDoubleByteChar > 0) {
                        byte[] bDif = new byte[nCountOfDoubleByteChar];
                        byte[] byteBatch = strBatch.getBytes();
                        System.arraycopy(byteBatch,
                                (byteBatch.length - nCountOfDoubleByteChar),
                                bDif,
                                0,
                                nCountOfDoubleByteChar);
                        int nNegativeByte = 0;
                        int nEndCharCount = 0;
                        for (int i = 0; i < nCountOfDoubleByteChar; i++) {
                            int nByte = (int) bDif[i];
                            if (nByte < 0)
                                nNegativeByte++;
                            else
                                nEndCharCount++;
                        }
                        if ((nNegativeByte % 2) != 0) {
                            String fixedString = "";
                            nFixedTimeCounter++;
//                            if (nc.bs.mw.fm.MWRuntimeParams.bAutoInsertSpace && (nFixedTimeCounter <= nc.bs.mw.fm.MWRuntimeParams.MAX_FIX_FAIL_TIME))
//                                fixedString = addOneSpaceChar(unTreatedStringBackup, nCharCount - nEndCharCount - (nNegativeByte / 2) - 1);
                            if (fixedString != null && !fixedString.equals("")) {
                                unTreatedString = fixedString;
                                retSQL = retSQLBackup;
                                continue;
                            } else {
                                bFixError = true;
                                break;
                            }
                        }
                        unTreatedString = new String(bDif, strByteCodingFormat) + unTreatedString;
                    }
                } else {
                    nCharCount = unTreatedChars.length;
                    System.arraycopy(unTreatedChars, 0, batchChars, 0, nCharCount);
                    unTreatedString = null;

                    String strBatch = new String(batchChars, 0, nCharCount);

                    int nCountOfDoubleByteChar = strBatch.getBytes().length - nCharCount;
                    if (nCountOfDoubleByteChar > 0) {
                        int nSpaceLeft = nBatchSize - nCharCount;
                        String strNull = new String(new char[]{'\0'});
                        //û�����Ӵ�����Σ�ֱ�ӽ�'\0'��ӵ�ĩβ����
                        if (nCountOfDoubleByteChar <= nSpaceLeft) {
                            retSQL += strBatch;
                            for (int i = 0; i < nCountOfDoubleByteChar; i++) {
                                retSQL += strNull;
                            }
                        } else {
                            retSQL += strBatch;
                            //������'\0'�����ַ���䵽300Char
                            for (int i = 0; i < nSpaceLeft; i++) {
                                retSQL += strNull;
                            }
                            //��������ȥ��(nCountOfDoubleByteChar - nSpaceLeft) ��byte;
                            byte[] byteBatch = strBatch.getBytes();
                            int nFixLength = nCountOfDoubleByteChar - nSpaceLeft;
                            byte[] byteFix = new byte[nFixLength];
                            System.arraycopy(byteBatch,
                                    (byteBatch.length - nFixLength),
                                    byteFix,
                                    0,
                                    nFixLength);
                            int nNegativeByte = 0;
                            int nEndCharCount = 0;
                            for (int i = 0; i < nFixLength; i++) {
                                int nByte = (int) byteFix[i];
                                if (nByte < 0)
                                    nNegativeByte++;
                                else
                                    nEndCharCount++;
                            }
                            if ((nNegativeByte % 2) != 0) {
                                String fixedString = "";
                                nFixedTimeCounter++;
//                                if (nc.bs.mw.fm.MWRuntimeParams.bAutoInsertSpace && (nFixedTimeCounter <= nc.bs.mw.fm.MWRuntimeParams.MAX_FIX_FAIL_TIME))
//                                    fixedString = addOneSpaceChar(unTreatedStringBackup, nCharCount - nEndCharCount - (nNegativeByte / 2) - 1);
                                if (fixedString != null && !fixedString.equals("")) {
                                    unTreatedString = fixedString;
                                    retSQL = retSQLBackup;
                                    continue;
                                } else {
                                    bFixError = true;
                                    break;
                                }
                            }
                            unTreatedString = new String(byteFix, strByteCodingFormat);
                        }
                    } else {
                        retSQL += strBatch;
                        //unTreatedString = null;
                    }
                }
                if (unTreatedString == null || unTreatedString.length() <= 0)
                    endOfSQL = true;
            }

        } catch (Exception e) {
            bFixError = true;
        }
        if (bFixError)
            throw new java.sql.SQLException("������������ַ��SQL���ʱ�������");
        //���ʵ�����SQL����д��������ַ����SQL����г��ֵ�λ�ã������ƹ�ô���
 
        return retSQL;
    }



 

    public String noucFromUnicode(String src) {
 
        //��������
        if (nModuleLang == 1) {
            return ISNConvert.gbToBig5(src);
        }
        return src;
    }
    



    /**
     * �˴����뷽��˵����
     * �������ڣ�(2001-12-13 11:21:53)
     *
     * @param newGU_CONVERT boolean
     */
    public void setGU_CONVERT(boolean newGU_CONVERT) {
        GU_CONVERT = newGU_CONVERT;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2001-12-13 11:21:53)
     *
     * @param newUG_CONVERT boolean
     */
    public void setUG_CONVERT(boolean newUG_CONVERT) {
        UG_CONVERT = newUG_CONVERT;
    }

    /**
     * �˴����뷽��˵����
     * �������ڣ�(2001-11-3 14:37:48)
     *
     * @param sql java.lang.String
     * @return java.lang.String
     */
    public static String stampSQL(String sql) {
         if ( sql == null || sql.equals("") || sql.length() < 6)
            return sql;
        String strSQLCopy = sql.toLowerCase();
        String strSelectWord = "select ";
        String strInsertWord = "insert ";
        String strUpdateWord = "update ";
        if (strSQLCopy.trim().startsWith(strSelectWord)) 
            return sql;
      //  else if(strSQLCopy.toLowerCase().indexOf(",ts ")>-1)
      //  	return sql;
        else if (strSQLCopy.trim().startsWith(strInsertWord)) {
            //PART I:���Դ����SQL���:

            //	1.��ʽָ���˲�����:
            //a.�������а�ts:INSERT INTO pub_systemplate (pk_busitype,ts,dr) values(NULL,'2001-11-29 14:14:25',0)--->>>ֱ�ӷ���;

            //b.�����в���ts:INSERT INTO pub_systemplate (pk_busitype,dr) values(NULL,0)--->>>����Ϊ:
            //INSERT INTO pub_systemplate (pk_busitype,dr,ts) values(NULL,0,'2001-11-29 14:14:25')

            //PART II:������֧�ֵ�SQL���:
            //2.�ݲ�����֧�ֲ���ʽָ�������е�Insert���:
            //insert into table_a values('zhangsd','ufsoft',5844)
            //3.�ݲ�����֧��--INSERT...SELECT example
            //USE pubs
            //INSERT author_sales
            //SELECT 'SELECT', authors.au_id, authors.au_lname,
            //SUM(titles.price * sales.qty)
            //FROM authors INNER JOIN titleauthor
            //ON authors.au_id = titleauthor.au_id INNER JOIN titles
            //ON titleauthor.title_id = titles.title_id INNER JOIN sales
            //ON titles.title_id = sales.title_id
            //WHERE authors.au_id LIKE '8%'
            //GROUP BY authors.au_id, authors.au_lname

            //4.�ݲ�����֧��INSERT...EXECUTE procedure example
            //INSERT author_sales EXECUTE get_author_sales

            //5.�ݲ�����֧��INSERT...EXECUTE('string') example
            //INSERT author_sales
            //EXECUTE
            //('
            //SELECT ''EXEC STRING'', authors.au_id, authors.au_lname,
            //SUM(titles.price * sales.qty)
            //FROM authors INNER JOIN titleauthor
            //ON authors.au_id = titleauthor.au_id INNER JOIN titles
            //ON titleauthor.title_id = titles.title_id INNER JOIN sales
            //ON titles.title_id = sales.title_id
            //WHERE authors.au_id like ''8%''
            //GROUP BY authors.au_id, authors.au_lname
            //')
            String strResSql = sql;

            int nValuesPos = strSQLCopy.indexOf(" values");
            if(nValuesPos==-1)
            	nValuesPos= strSQLCopy.indexOf("values");
            if (nValuesPos > 0) {
                int nLeftBracketPos = strSQLCopy.substring(0, nValuesPos).indexOf("(");
                int nRightBracketPos = strSQLCopy.substring(0, nValuesPos).indexOf(")");
                if (nLeftBracketPos > 0 && nRightBracketPos > 0 && nLeftBracketPos < nRightBracketPos) {
                    StringTokenizer stColumns = new StringTokenizer(strSQLCopy.substring(nLeftBracketPos + 1, nRightBracketPos), ", ");
                    boolean bHasTSColumn = false;
                    while (stColumns.hasMoreTokens()) {
                        String strToken = stColumns.nextToken();
                        if (strToken.equalsIgnoreCase("ts")) {
                            bHasTSColumn = true;
                            break;
                        }
                    }
                    //String strSQLTrimed = strSQLCopy.trim();
                    if (!bHasTSColumn) {// && strSQLTrimed.charAt(strSQLTrimed.length() - 1) == ')'){
                        //Let's do it:
                        StringBuffer sbNewSql = new StringBuffer();
                        sbNewSql.append(sql.substring(0, nRightBracketPos));
                        sbNewSql.append("," +"ts");
                        int nLastBracketPos = sql.lastIndexOf(")");
                        sbNewSql.append(sql.substring(nRightBracketPos, nLastBracketPos));
                        sbNewSql.append(",'" + getCurrentTimeStampString() + "'");
                        sbNewSql.append(sql.substring(nLastBracketPos, sql.length()));
                        strResSql = sbNewSql.toString();
//                        if (nc.bs.mw.fm.MWRuntimeParams.nDebugLevel > 0) {
//                            System.out.println("After stamp:" + strResSql);
//                        }
                    }
                }
            }
     //       nc.bs.mw.itf.MwTookit.setThreadState("nc.jdbc.framework.crossdb.CrossDBObject.stampSQL Over");
            return strResSql;


        } else if (strSQLCopy.trim().startsWith(strUpdateWord)) {
            /*
            ����UPDATE ��䣨ʱ����ֶ������ͳһ�ģ���ΪTS����
               UPDATE TABLE1 SET A=B��C=D��... WHERE R=T AND U=X...
             ����ɣ�
            UPDATE TABLE1 SET TS='��ǰʱ���', A=B��C=D��... WHERE R=T AND U=X...
            ���ڽ�ӹ�ʱ������������ԣ�
            */
            if (strSQLCopy.indexOf(" set ts") >= 0||strSQLCopy.indexOf(",ts") >= 0||strSQLCopy.indexOf(", ts") >= 0)
                return sql;
            int nReplaceIndex = strSQLCopy.indexOf(" set ");
            if (nReplaceIndex > 0) {
                StringBuffer sbNewSql = new StringBuffer();
                sbNewSql.append(sql.substring(0, nReplaceIndex + 5));
                sbNewSql.append("ts" ).append("='").append(getCurrentTimeStampString()).append("',");
                sbNewSql.append(sql.substring(nReplaceIndex + 5));      
                return sbNewSql.toString();
            } else {              
                return sql;
            }
        } else {       
            return sql;
        }
    }

   

    public String unicodeFromNoUC(String src) {

        //��������
        if (nModuleLang == 1) {
            return ISNConvert.big5ToGb(src);
        }
        return src;
    }

    /**
     * �˴����뷽��������
     * �������ڣ�(2002-7-5 12:30:18)
     *
     * @return long
     */
    public static String getCurrentTimeStampString() {
         return getTimeStampString(System.currentTimeMillis());
    }

    /**
     * �˴����뷽��������
     * �������ڣ�(2002-7-5 11:08:25)
     *
     * @return java.lang.String
     */
    public static String getTimeStampString(long l) { 
        java.util.Calendar cl = java.util.Calendar.getInstance();
//        cl.setTimeInMillis(TimeService.getInstance().getTime());
        int ia[] = new int[5];
        int year = cl.get(Calendar.YEAR);
        ia[0] = cl.get(Calendar.MONTH) + 1;
        ia[1] = cl.get(Calendar.DAY_OF_MONTH);
        ia[2] = cl.get(Calendar.HOUR_OF_DAY);
        ia[3] = cl.get(Calendar.MINUTE);
        
        ia[4] = cl.get(Calendar.SECOND);
        byte ba[] = new byte[19];
        ba[4] = ba[7] = (byte) '-';
        ba[10] = (byte) ' ';
        ba[13] = ba[16] = (byte) ':';
        ba[0] = (byte) (year / 1000 + '0');
        ba[1] = (byte) ((year / 100) % 10 + '0');
        ba[2] = (byte) ((year / 10) % 10 + '0');
        ba[3] = (byte) (year % 10 + '0');
        for (int i = 0; i < 5; i++) {
            ba[i * 3 + 5] = (byte) (ia[i] / 10 + '0');
            ba[i * 3 + 6] = (byte) (ia[i] % 10 + '0');
        }
        return new String(ba);
        //return getTimeStampString;
    }

    /**
     * �˴����뷽��������
     * �������ڣ�(2002-7-5 12:20:01)
     *
     * @param args java.lang.String[]
     */
    public static void main(String[] args) {
        System.out.println("TEST GE TTIME STAMP");
    String    sql ="UPDATE pub_sysinit SET sysinit = 'GLV50AFl7064376237A1', initcode = 'GL097', initname = 'ƾ֤���Ƿ������޸�', makedate = '2006-06-23', value = 'Y', editflag = 'Y', controlflag = false, pkvalue = null, pk_org = '1001', dr = null WHERE pk_sysinit = '0001V6100000000001M1'";
          
    System.out.println(sql=CrossDBObject.stampSQL(sql) );
    }
}