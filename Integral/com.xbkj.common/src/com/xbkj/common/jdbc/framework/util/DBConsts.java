package com.xbkj.common.jdbc.framework.util;

/**
 * ��ݿ�����
 */
public interface DBConsts {

	// ��ݿ�����
	public final static int DB2 = 0;
	public final static int ORACLE = 1;
	public final static int SQLSERVER = 2;
	public final static int SYBASE = 3;
	public final static int INFORMIX = 4;
	public final static int HSQL = 5;
	public final static int OSCAR = 6;
	public final static int POSTGRESQL = 7;
	public final static int UNKOWNDATABASE = -1;
	public final static String DEFAULT_DATABASE_ID = "default_database";

	public final static String ORACLE_NAME = "ORACLE";
	public final static String MSSQL_NAME = "MSSQL";
	public final static String DB2_NAME = "DB2";
	public final static String HSQL_NAME = "HSQL";
	public final static String SYBASE_NAME = "SYBASE";
	public final static String INFORMIX_NAME = "INFORMIX";
	public final static String OSCAR_NAME = "OSCAR";
	public final static String POSTGRESQL_NAME = "POSTGRESQL";
	public final static String UNKOWN_NAME = "UNKOWN";

	// JDBC��
	public final static String JDBC_ODBC = "sun.jdbc.odbc.JdbcOdbcDriver";
	public final static String JDBC_DB2_NET = "COM.ibm.db2.jdbc.net.DB2Driver";
	public final static String JDBC_DB2_APP = "COM.ibm.db2.jdbc.app.DB2Driver";
	public final static String JDBC_ORACLE = "oracle.jdbc.driver.OracleDriver";
	public final static String JDBC_SYBASE = "com.sybase.jdbc.SybDriver";

	// ConnectionDriver
	public static final String URL_PREFIX = "jdbc:ufsoft:jdbcDriver";
	public static final int MAJOR_VERSION = 1;
	public static final int MINOR_VERSION = 0;

	public static final String JdbcOdbcBridgeName = "JDBC-ODBC Bridge";

	public final static String JDBC_INFORMIX = "com.informix.jdbc.IfxDriver";
	public final static String JDBC_SQLSERVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

	// ------------------------------------------------------------------------
	// Sql�������
	// ------------------------------------------------------------------------

	public final static int SQL_SELECT = 1;
	public final static int SQL_INSERT = 2;
	public final static int SQL_CREATE = 3;
	public final static int SQL_DROP = 4;
	public final static int SQL_DELETE = 5;
	public final static int SQL_UPDATE = 6;
	public final static int SQL_EXPLAIN = 7;

	// ------------------------------------------------------------------------
	// �����б�,��SQL�����ֻ������Щ����
	// ------------------------------------------------------------------------
	public static String[] functions = { "coalesce", "len", "left", "right",
			"substring", "lower", "upper", "ltrim", "rtrim", "sqrt", "abs",
			"square", "sign", "count", "max", "min", "sum", "avg", "cast" };
}