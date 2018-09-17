package com.xbkj.common.jdbc.framework.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;

import com.xbkj.basic.vo.pub.lang.UFBoolean;
import com.xbkj.basic.vo.pub.lang.UFDate;
import com.xbkj.basic.vo.pub.lang.UFDateTime;
import com.xbkj.basic.vo.pub.lang.UFDouble;
import com.xbkj.basic.vo.pub.lang.UFTime;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.jdbc.framework.crossdb.CrossDBConnection;
import com.xbkj.common.jdbc.framework.type.BlobParamType;
import com.xbkj.common.jdbc.framework.type.ClobParamType;
import com.xbkj.common.jdbc.framework.type.NullParamType;
import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;
import com.xbkj.datasor.bs.framework.ds.DataSourceMeta;
import com.xbkj.datasor.bs.framework.ds.DataSourceMetaMgr;
import com.xbkj.log.bs.logging.Logger;

/**
 * @nopublish
 */
public class DBUtil {
	private static final BigDecimal d = new BigDecimal("0.00000001");

	public static boolean needToInt(BigDecimal b) {
		b = b.abs();
		BigDecimal c = b.subtract(new BigDecimal(b.intValue()));
		return c.compareTo(d) < 0;
	}

	public static void setStatementParameter(PreparedStatement statement,
			SQLParameter params) throws SQLException {
		if (statement == null || params == null)
			throw new IllegalArgumentException("SQLParameter!");
		for (int i = 0; i < params.getCountParams(); i++) {
			Object param = params.get(i);
			if (param == null)
				throw new IllegalArgumentException("SQLParameter不能为空");
			if (param instanceof NullParamType) {
				statement.setNull(i + 1, ((NullParamType) param).getType());
			} else if (param instanceof Integer) {
				statement.setInt(i + 1, ((Integer) param).intValue());
			} else if (param instanceof Short) {
				statement.setShort(i + 1, ((Short) param).shortValue());
			} else if (param instanceof Timestamp) {
				statement.setTimestamp(i + 1, (Timestamp) param);
			} else if (param instanceof Time) {
				statement.setTime(i + 1, (Time) param);
			} else if (param instanceof String) {
				String s = (String) param;
				statement.setString(i + 1, s);
			} else if (param instanceof Calendar) {
				Calendar s = (Calendar) param;
				statement.setString(i + 1, new UFDateTime(s.getTime())
						.toString());
			} else if (param instanceof UFTime) {
				statement.setString(i + 1, ((UFTime) param).toString());
			} else if (param instanceof UFBoolean) {
				statement.setString(i + 1, ((UFBoolean) param).toString());
			} else if (param instanceof UFDate) {
				statement.setString(i + 1, ((UFDate) param).toString());
			} else if (param instanceof UFDateTime) {
				statement.setString(i + 1, ((UFDateTime) param).toString());
			} else if (param instanceof Double) {
				statement.setDouble(i + 1, ((Double) param).doubleValue());
			} else if (param instanceof UFDouble) {
				statement.setBigDecimal(i + 1, ((UFDouble) param)
						.toBigDecimal());
			} else if (param instanceof Float) {
				statement.setFloat(i + 1, ((Float) param).floatValue());
			} else if (param instanceof Long) {
				statement.setFloat(i + 1, ((Long) param).longValue());
			} else if (param instanceof Boolean) {
				statement.setBoolean(i + 1, ((Boolean) param).booleanValue());
			} else if (param instanceof java.sql.Date) {
				statement.setDate(i + 1, (java.sql.Date) param);
			}
			// �����BLOB
			else if (param instanceof BlobParamType) {
				statement.setBytes(i + 1, ((BlobParamType) param).getBytes());
			}
			// �����CLOB
			else if (param instanceof ClobParamType) {
				ClobParamType clob = (ClobParamType) param;
				statement.setCharacterStream(i + 1, clob.getReader(), clob
						.getLength());
			} else {
				statement.setObject(i + 1, param);
			}
		}
	}

	public static int getDbType(Connection con) {
		try {
			return getDbType(con.getMetaData());
		} catch (SQLException e) {
			Logger.error("get database meta error", e);
			return DBConsts.UNKOWNDATABASE;
		}
	}

	public static String dbTypeToString(int dbType) {
		if (dbType == DBConsts.DB2)
			return DBConsts.DB2_NAME;
		if (dbType == DBConsts.ORACLE)
			return DBConsts.ORACLE_NAME;
		if (dbType == DBConsts.SQLSERVER)
			return DBConsts.MSSQL_NAME;
		if (dbType == DBConsts.INFORMIX)
			return DBConsts.INFORMIX_NAME;
		if (dbType == DBConsts.OSCAR)
			return DBConsts.OSCAR_NAME;
		if (dbType == DBConsts.HSQL)
			return DBConsts.HSQL_NAME;
		if (dbType == DBConsts.SYBASE)
			return DBConsts.SYBASE_NAME;
		if (dbType == DBConsts.POSTGRESQL)
			return DBConsts.POSTGRESQL_NAME;
		return DBConsts.UNKOWN_NAME;

	}

	public static int getDbType(DatabaseMetaData dmd) {
		String dpn = null;
		try {
			dpn = dmd.getDatabaseProductName();
		} catch (SQLException exp) {
			Logger.error("get database prodcut name error", exp);
			return DBConsts.UNKOWNDATABASE;
		}
		String udpn = dpn.toUpperCase();
		if (udpn.indexOf("POSTGRES") != -1)
			return DBConsts.POSTGRESQL;
		if (udpn.indexOf("DB2") != -1)
			return DBConsts.DB2;
		if (udpn.indexOf("ORACLE") != -1)
			return DBConsts.ORACLE;
		if (udpn.indexOf("SQL") != -1)
			return DBConsts.SQLSERVER;
		if (udpn.indexOf("INFORMIX") != -1)
			return DBConsts.INFORMIX;
		if (udpn.toUpperCase().indexOf("OSCAR") != -1)
			return DBConsts.OSCAR;
		if (udpn.indexOf("HSQL") != -1)
			return DBConsts.HSQL;
		if (udpn.indexOf("SYBASE") != -1)
			return DBConsts.SYBASE;
		return DBConsts.UNKOWNDATABASE;
	}

	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
		}
	}

	public static void closeStmt(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
		}
	}

	public static void closeRs(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * У�����Դ�Ƿ����
	 * 
	 * @param dsName
	 * @return
	 */
	public static boolean isDbExisted(String dsName) {
		try {

			DataSourceMeta dsm = DataSourceMetaMgr.getInstance()
					.getDataSourceMeta(dsName);

			String strSourceUrl = dsm.getDatabaseUrl();
			String strSourceDriver = dsm.getDriverClassName();
			String strSourceUser = dsm.getUser();
			String strSourcePassword = dsm.getPassword().trim();

			Class<?> driverCls = Class.forName(strSourceDriver);
			Driver driver = (Driver) driverCls.newInstance();
			Properties prop = new Properties();
			prop.put("user", strSourceUser);
			prop.put("password", strSourcePassword);
			Connection con = driver.connect(strSourceUrl, prop);

			Statement stmt = con.createStatement();
			if (stmt != null) {
				closeStmt(stmt);
				closeConnection(con);
				return true;
			} else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

	public static String getDbTypeAsString(Connection con) {
		try {
			return getDbTypeAsString(con.getMetaData());
		} catch (SQLException e) {
			Logger.error("get database metat data error", e);
			return DBConsts.UNKOWN_NAME;
		}
	}

	public static String getDbTypeAsString(DatabaseMetaData dmd) {

		String dpn = null;
		try {
			dpn = dmd.getDatabaseProductName();
		} catch (SQLException e) {
			Logger.error("get database prodcut name error", e);
			return DBConsts.UNKOWN_NAME;
		}
		String udpn = dpn.toUpperCase();
		if (udpn.indexOf("POSTGRES") != -1)
			return DBConsts.POSTGRESQL_NAME;
		if (udpn.indexOf("DB2") != -1)
			return DBConsts.DB2_NAME;
		if (udpn.indexOf("ORACLE") != -1)
			return DBConsts.ORACLE_NAME;
		if (udpn.indexOf("SQL") != -1)
			return DBConsts.MSSQL_NAME;
		if (udpn.indexOf("INFORMIX") != -1)
			return DBConsts.INFORMIX_NAME;
		if (udpn.toUpperCase().indexOf("OSCAR") != -1)
			return DBConsts.OSCAR_NAME;
		if (udpn.indexOf("HSQL") != -1)
			return DBConsts.HSQL_NAME;
		if (udpn.indexOf("SYBASE") != -1)
			return DBConsts.SYBASE_NAME;

		return dpn;
	}

	public static String getDataSource(Connection conn) {
		String dsName = null;
		if (conn instanceof CrossDBConnection) {
			dsName = ((CrossDBConnection) conn).getDataSource();
		}

		if (dsName == null) {
			dsName = InvocationInfoProxy.getInstance().getUserDataSource();
		}
		return dsName;

	}

}
