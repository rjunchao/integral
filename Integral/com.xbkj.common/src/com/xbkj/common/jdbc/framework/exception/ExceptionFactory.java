package com.xbkj.common.jdbc.framework.exception;

import java.sql.SQLException;

import com.xbkj.common.jdbc.framework.util.DBConsts;

/**
 * @nopublish Created by IntelliJ IDEA. User: ���� Date: 2005-1-14 Time: 16:32:08
 */
public class ExceptionFactory {

	public static DbException getException(int databaseType, String msg,
			SQLException e) {
		switch (databaseType) {
		case DBConsts.UNKOWNDATABASE:
			return new UnKnownException(msg, e);
		case DBConsts.ORACLE:
			return new OracleException(msg, e);
		case DBConsts.DB2:
			return new DB2Exception(msg, e);
		case DBConsts.SQLSERVER:
			return new MSSQLException(msg, e);
		case DBConsts.HSQL:
			return new HSQLException(msg, e);
		}
		return null;
	}

	public static DbException getException(int databaseType, String msg) {
		switch (databaseType) {
		case DBConsts.UNKOWNDATABASE:
			return new UnKnownException(msg);
		case DBConsts.ORACLE:
			return new OracleException(msg);
		case DBConsts.DB2:
			return new DB2Exception(msg);
		case DBConsts.SQLSERVER:
			return new MSSQLException(msg);
		case DBConsts.HSQL:
			return new HSQLException(msg);
		}
		return null;
	}
}
