package com.xbkj.common.jdbc.framework.exception;

import java.sql.SQLException;

/**
 * 
 * Created by IntelliJ IDEA.
 * User: ����<br>
 * Date: 2005-1-14<br>
 * Time: 16:32:42<br>
 * DbException��һ�������쳣�࣬ ����������SQLException�����ǵ���ݷ����쳣֮���ת��������ݷ��ʿ�����ṩ�˲�ͬ��ݿ�Բ�ͬDbException��ʵ�ֲ���ÿ����ݿ��SQLErrorCode�����˴��?ͳһת���ɲ�ͬ�ķ�����ͨ��DbException�ܹ���ȷ��ָ������ݷ��ʹ��������ֵ����⡣
 */
public abstract class DbException extends Exception {

    protected int sqlErrorCode = 0;


    protected String SQLState = null;


    public abstract boolean isDataIntegrityViolation();


    public abstract boolean isBadSQLGrammar();

  protected SQLException  realException;



    public DbException(String msg, SQLException e) {
        super(msg,e);
       realException=e;
        sqlErrorCode = e.getErrorCode();
         SQLState=e.getSQLState();
    }


    public DbException(String msg) {
        super(msg);
        sqlErrorCode = -1;
        SQLState = null;
    }


    public int getSQLErrorCode() {
        return (sqlErrorCode);
    }


    public String getSQLState() {
        return (SQLState);
    }

    public SQLException getRealException()
    {
        return realException;
    }
}

