
package com.xbkj.common.jdbc.framework.crossdb.adapter;

import com.xbkj.common.jdbc.framework.util.DBConsts;
/**
 * @nopublish
 * 
 * ��ݿ�����������
 * User: ���� Date: 2005-5-16 Time: 13:55:07 ${NAME}���˵��
 */

public abstract class AdapterFactory implements DBConsts {

    private AdapterFactory() {
    }

    static public Adapter getAdapter(int dbType) {
//        switch (dbType) {
//        case DB2:
//            return new DB2Adapter();
//
//        case ORACLE:
//            return new OracleAdapter();
//        case SYBASE:
//            return new DB2Adapter();
//        case SQLSERVER:
//            return new SQLServerAdapter();
//        case INFORMIX:
//            return new DB2Adapter();
//        case POSTGRESQL:
//            return new PostgresAdapter();
//        default: //ȱʡΪSQLSERVER;
//            return new SQLServerAdapter();
//        }
    	return null;
    }
}
