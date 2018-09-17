package com.pub.xbkj.pubapp.temp.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pub.xbkj.pubapp.query.DBTool;
import com.xbkj.datasor.bs.framework.ds.DataSourceMeta;
import com.xbkj.datasor.bs.framework.ds.DataSourceMetaMgr;
import com.xbkj.log.bs.logging.Logger;

/**
 * 此处插入类型说明。 创建日期：(2004-2-23 13:49:03)
 * 
 * @author：杨志强 modified by hey
 */
public class CreateTempTable {
	private int m_rowsnum = 2000;

	private static String oracleVersion = "x.x.x.x.x";
	
	public String createTempTable(Connection con, String tableName,
			String columns, String... idx) throws SQLException {
		if (tableName == null || columns == null || con == null)
			return null;
		String dbname;
		String m_tabname = null;
		// 获得数据库类型
		dbname = getDbType(con);
		TempTableCreator tempTable = TempTableCreatorFactory
				.getDBTemptable(dbname);
		tempTable.setRowNum(m_rowsnum);
		m_tabname = tempTable.createTempTable(con, tableName, columns, idx);
		return m_tabname;
	}

	static private String getDbType(Connection con) throws SQLException {

		// 获取传入连接的数据库类型
		String m_dbname = null;
		DatabaseMetaData dmd = con.getMetaData();
		String dpn = dmd.getDatabaseProductName();
		if (dpn.toUpperCase().indexOf("POSTGRES") != -1)
			return "POSTGRESQL";
		if (dpn.toUpperCase().indexOf("DB2") != -1)
			m_dbname = "DB2";
		if (dpn.toUpperCase().indexOf("ORACLE") != -1) {
			m_dbname = "ORACLE";
			String productVer = dmd.getDatabaseProductVersion();
			int idx = productVer.indexOf("Release ") + "Release ".length();
			oracleVersion = productVer.substring(idx, idx
					+ "9.2.0.x.0".length());
		}

		if (dpn.toUpperCase().indexOf("SQL") != -1)
			m_dbname = "SQL";
		if (dpn.toUpperCase().indexOf("INFORMIX") != -1)
			m_dbname = "INFORMIX";
		if (dpn.toUpperCase().indexOf("OSCAR") != -1)
			m_dbname = "OSCAR";
		if (dpn.toUpperCase().indexOf("GBASE") != -1) {
			m_dbname = "GBASE";
		}
		return m_dbname;
	}

	public void dropTempTable(Connection con, String TabName)
			throws SQLException {
		if (con == null)
			return;
		String dbname = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 获得数据库类型
			dbname = getDbType(con);
			stmt = con.createStatement();
			if (dbname.equalsIgnoreCase("ORACLE")) {
				// 取得数据库的临时表
				if (TabName == null || TabName.length() == 0) {
					rs = stmt
							.executeQuery("select TABLE_NAME from user_tables where TEMPORARY='Y'");
					while (rs.next()) {
						String sql = "drop table " + rs.getString(1);
						stmt.execute(sql);
					}
				} else {
					String sql = "drop table " + TabName.trim();
					stmt.execute(sql);
				}
			} else if (dbname.equalsIgnoreCase("DB2")) {
				if (TabName != null) {
					String sql = "drop table " + TabName.trim();
					stmt.execute(sql);
				}
			}
		} catch (Exception e) {
			Logger.error("删除临时表异常!", e);
		} finally {
			DBTool.closeRs(rs);
			DBTool.closeStmt(stmt);
			
		}

	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-9-28 15:33:09)
	 */
	public void setRowsnum(int num) {
		m_rowsnum = num;
	}

	public static void gatherStats(Connection con, String TabName,
			int columnCounts, int rowCounts) {
		ResultSet rs = null;
		Statement stmt = null;
		CallableStatement cstmt = null;
		Connection myCon = null;
		String dbname = null;
		try {
			// 获得数据库类型
			dbname = getDbType(con);
			if (!(dbname.equalsIgnoreCase("ORACLE"))) {
				return;
			}
			/*
			 * cc+ 对于 ORACLE 9.2.0.4.x 之后的版本，通过设置 optimizer_dynamic_sampling = 4
			 * 来自动收集临时表统计信息
			 */
			if (verHigher()) {
				return;
			}
			if (-1 == rowCounts) {
				stmt = con.createStatement();
				rs = stmt
						.executeQuery("select count(*) as CNT from " + TabName);
				if (rs.next()) {
					rowCounts = rs.getInt("CNT");
				}
			}
			int blockCounts = 0;
			blockCounts = (int) (7 * (rowCounts * columnCounts) / 5000);
			if (blockCounts == 0)
				blockCounts = 2;
			myCon = getConnect();
			String username = myCon.getMetaData().getUserName();
			String sql1 = "begin dbms_stats.set_table_stats(ownname=>'"
					+ username.toUpperCase() + "',tabname=>'"
					+ TabName.toUpperCase() + "',numrows=>" + rowCounts
					+ ",numblks=>" + blockCounts + "); end ;";
			cstmt = myCon.prepareCall(sql1);
			cstmt.execute();
		} catch (Exception e) {
		} finally {
			DBTool.closeRs(rs);
			DBTool.closeStmt(stmt);
			DBTool.closeStmt(cstmt);
			DBTool.closeConn(myCon);
		}
	}

	static private Connection getConnect() throws SQLException {
		DataSourceMetaMgr manager = DataSourceMetaMgr.getInstance();
		DataSourceMeta meta = manager.getDataSourceMeta();
		String strUrl = meta.getDatabaseUrl();
		String user = meta.getUser();
		String pwd = meta.getPassword();
		Connection newCon = DriverManager.getConnection(strUrl, user, pwd);
		return newCon;
	}

	// cc+
	private static boolean verHigher() {
		if (oracleVersion.equals("x.x.x.x.x"))
			throw new IllegalArgumentException(
					"Cannot Parse Oracle Version in Temptable ...");
		if (oracleVersion.charAt(0) == '8')
			return false;
		if (oracleVersion.charAt(0) == '9') {
			if (oracleVersion.charAt(2) < '2')
				return false;
			if (oracleVersion.charAt(2) > '2')
				return true;
			if (oracleVersion.charAt(2) == '2') {
				if (oracleVersion.charAt(4) > '0')
					return true;
				if (oracleVersion.charAt(6) >= '4')
					return true;
			}
		}
		return true;
	}

}