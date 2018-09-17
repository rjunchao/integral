package com.xbkj.common.bs.mw.sqltrans;

import com.xbkj.log.bs.logging.Logger;

/**
 * �˴���������˵���� 
 * �������ڣ�(2001-12-28 18:31:14)
 *  
 */
public class TransUnit {
    //private java.lang.String[] asSqlWords = null;
    //private java.lang.String sWords = null;
    private int iOffSet = 0;

    public java.lang.String sql = null;

    public java.lang.String[] sqlArray = null;

    private boolean dontHaveWhere = false;

    /**
     * TransUnit ������ע�⡣
     */
    public TransUnit() {
        super();
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit Over");
    }

    /**
     * TransUnit ������ע�⡣
     */
    public TransUnit(String[] newStArray, String newSql, int newOffset) {
        super();
        setSqlArray(newStArray);
        setSql(newSql);
        setIOffSet(newOffset);
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit Over");
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:33:44)
     * 
     * @return int
     */
    public int getIOffSet() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getIOffSet");
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getIOffSet Over");
        return iOffSet;
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:34:26)
     * 
     * @return java.lang.String
     */
    public java.lang.String getSql() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getSql");
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getSql Over");
        return sql;
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:47:53)
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getSqlArray() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getSqlArray");
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.getSqlArray Over");
        return sqlArray;
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 21:26:09)
     * 
     * @return boolean
     */
    public boolean isDontHaveWhere() {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.isDontHaveWhere");
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.isDontHaveWhere Over");
        return dontHaveWhere;
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 21:26:09)
     * 
     * @param newDontHaveWhere
     *            boolean
     */
    public void setDontHaveWhere(boolean newDontHaveWhere) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setDontHaveWhere");
        dontHaveWhere = newDontHaveWhere;
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setDontHaveWhere Over");
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:33:44)
     * 
     * @param newIOffSet
     *            int
     */
    public void setIOffSet(int newIOffSet) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setIOffSet");
        iOffSet = newIOffSet;
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setIOffSet Over");
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:34:26)
     * 
     * @param newSql
     *            java.lang.String
     */
    public void setSql(java.lang.String newSql) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setSql");
        sql = newSql;
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setSql Over");
    }

    /**
     * �˴����뷽��˵���� 
     * �������ڣ�(2001-12-28 18:47:53)
     * 
     * @param newSqlArray
     *            java.lang.String[]
     */
    public void setSqlArray(java.lang.String[] newSqlArray) {
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setSqlArray");
        sqlArray = newSqlArray;
        Logger.setThreadState("nc.bs.mw.sqltrans.TransUnit.setSqlArray Over");
    }
}