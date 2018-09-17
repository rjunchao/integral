/*
 * �������� 2005-8-19
 *
 * TODO 
 */
package com.xbkj.common.jdbc.framework;
 
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import com.eos.common.connection.SimpleConnectionHelper;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.xbkj.common.jdbc.framework.util.DBConsts;
import com.xbkj.common.jdbc.framework.util.DBUtil;
import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;
import com.xbkj.mxfw.bs.framework.mx.thread.ThreadTracer;
//import com.ibm.websphere.rsadapter.JDBCConnectionSpec;
//import com.ibm.websphere.rsadapter.WSDataSource;
//import com.ibm.websphere.rsadapter.WSRRAFactory;
//import com.grc.common.jdbcframework.datasource.DataSourceProvider;

/**
 * @author hey ��ݿ���Ϣ�࣬�ṩ���Դ��Ӧ��ݿ�ľ�����Ϣ������ݿ����ͣ���ݿ�汾�� �������Ǹ�����
 */
public class DataSourceCenter implements DBConsts {

	 private static Logger logger = TraceLoggerFactory.getLogger(DataSourceCenter.class);
	  
	static boolean isRunningInPlugin;

//	static DataSourceProvider provider;

	static {
		try {
			Class.forName("nc.uap.mde.lib.Activator");
			isRunningInPlugin = true;
		} catch (ClassNotFoundException e) {
			isRunningInPlugin = false;
		}

//		if (isRunningInPlugin) {
//			try {
//				provider = (DataSourceProvider) Class.forName(
//						"nc.uap.mde.lib.PluginDataSourceProvider")
//						.newInstance();
//			} catch (Exception e) {
//			}
//		}
	}

	static private DataSourceCenter center = new DataSourceCenter();

	private Map<String, DBMetaInfo> metaCache = new ConcurrentHashMap<String, DBMetaInfo>();

	private Map<String, DataSource> dataSourceCache = new ConcurrentHashMap<String, DataSource>();

	private String defaultSource = "design";

	private DataSourceCenter() {
	}

	static public DataSourceCenter getInstance() {

		return center;
	}

	/**
	 * �õ����Դ���
	 * 
	 * @return ���Դ���
	 */
	public String getSourceName() {
		String dataSource = InvocationInfoProxy.getInstance()
				.getUserDataSource();
		if (dataSource == null)
			dataSource = InvocationInfoProxy.getInstance().getUserDataSource();
		if (dataSource == null)
			return defaultSource;
		return dataSource;
	}

	/**
	 * �õ�Ĭ����ݿ�����
	 * 
	 * @return ��ݿ�����
	 * @throws java.sql.SQLException
	 *             �������׳�SQLException
	 */
	public Connection getConnection() throws SQLException {
		String sourceName = getSourceName();
		//TODO:NEED HGY AUDIT.
		ThreadTracer.getInstance().updateEvent("get connection");
		Connection con= getConnection(sourceName);
		ThreadTracer.getInstance().updateEvent("end get connection");
		return con;
	}

	/**
	 * ������Դ��Ƶõ���ݿ�����
	 * 
	 * @return ��ݿ�����
	 * @throws SQLException
	 *             �������׳�SQLException
	 */
	public Connection getConnection(String sourceName) throws SQLException {

		String name = sourceName;
		if (name == null) {

			name = getSourceName();
		}
		DataSource ds = dataSourceCache.get(name);
	
		try {
				Connection conn = null;
				synchronized (this) {
					dataSourceCache.put(name, ds);
					conn = getDiffConnection(name);
					DatabaseMetaData DBMeta = conn.getMetaData();
					int DBType = getDbType(DBMeta);
					String DBName = getDbName(DBMeta);
					boolean isBatch = isSupportBatch(DBMeta);
					boolean isODBC = isODBC(DBMeta);
					int version = getDbVersion(conn);
					DBMetaInfo meta = new DBMetaInfo(DBType, version, DBName,
							isBatch, isODBC);
					metaCache.put(name, meta);
				}
				return conn;
			
		} catch (NullPointerException e) {
			throw new SQLException("Can't get connection from database(" + name
					+ ")");
		}
	}

	private Connection getDiffConnection(String dataSourceName) throws SQLException {

		 Connection con = SimpleConnectionHelper.getConnection();
		 return con;
	}

	/**
	 * �õ���ݿ�����
	 * 
	 * @return
	 */
	public int getDatabaseType() {
		String sourceName = getSourceName();
		return getDatabaseType(sourceName);
	}

	/**
	 * ������Դ��Ƶõ���ݿ�����
	 * 
	 * @param sourceName
	 *            ���Դ���
	 * @return ��ݿ�����
	 */
	public int getDatabaseType(String sourceName) {
		if (sourceName == null)
			sourceName = getSourceName();
		DBMetaInfo meta = metaCache.get(sourceName);
		if (meta == null) {
			Connection con = null;
			// ��û�е���getConnectionǰֱ�ӵ���getDatabaseType�������metaΪ�յ������
			try {
				con = getConnection(sourceName);
				meta = metaCache.get(sourceName);
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				DBUtil.closeConnection(con);
			}
			if (meta == null)
				return UNKOWNDATABASE;
		}
		return meta.getType();
	}

	/**
	 * �õ�Ĭ�����Դ����ݿ����
	 * 
	 * @return
	 */
	public String getDatabaseName() {
		String sourceName = getSourceName();
		return getDatabaseName(sourceName);
	}

	/**
	 * ������Դ��Ƶõ���ݿ����
	 * 
	 * @param sourceName
	 * @return
	 */
	public String getDatabaseName(String sourceName) {
		if (sourceName == null)
			sourceName = getSourceName();
		DBMetaInfo meta = metaCache.get(sourceName);
		if (meta == null)
			return JDBC_SQLSERVER;
		return meta.getName();
	}

	/**
	 * �õ���Ĭ�����Դ����ݿ�汾
	 * 
	 * @return
	 */
	public int getDatabaseVersion() {
		String sourceName = getSourceName();

		return getDatabaseVersion(sourceName);
	}

	/**
	 * �Ƿ�֧����ִ��
	 * 
	 * @param sourceName
	 *            ���Դ���
	 * @return
	 */
	public boolean isSupportBatch(String sourceName) {
		if (sourceName == null)
			sourceName = getSourceName();
		DBMetaInfo meta = metaCache.get(sourceName);
		if (meta == null)
			return true;
		return meta.isSupportBatch();

	}

	/**
	 * �Ƿ�֧����ִ��
	 * 
	 * @return
	 */
	public boolean isSupportBatch() {

		String sourceName = getSourceName();
		return isSupportBatch(sourceName);
	}

	/**
	 * �Ƿ���ODBC
	 * 
	 * @return
	 */
	public boolean isODBC() {

		String sourceName = getSourceName();
		return isODBC(sourceName);
	}

	/**
	 * @param sourceName
	 * @return
	 */
	public boolean isODBC(String sourceName) {
		if (sourceName == null)
			sourceName = getSourceName();
		DBMetaInfo meta = metaCache.get(sourceName);
		if (meta == null)
			return true;
		return meta.isODBC();
	}

	/**
	 * �õ���ݿ�汾
	 * 
	 * @param sourceName
	 * @return
	 */
	public int getDatabaseVersion(String sourceName) {
		if (sourceName == null)
			sourceName = getSourceName();
		DBMetaInfo meta = metaCache.get(sourceName);
		if (meta == null)
			return 1;
		return meta.getVersion();
	}

	/**
	 * �õ���ݿ�����
	 * 
	 * @return
	 * @throws SQLException
	 *             �������׳��쳣
	 */
	public int getDbType(DatabaseMetaData dmd) throws SQLException {
		String dpn = dmd.getDatabaseProductName();
		// System.out.println("hey--datasource type--"+dpn);
		if (dpn.toUpperCase().indexOf("POSTGRESQL") != -1)
			return POSTGRESQL;
		if (dpn.toUpperCase().indexOf("DB2") != -1)
			return DB2;
		if (dpn.toUpperCase().indexOf("ORACLE") != -1)
			return ORACLE;
		if (dpn.toUpperCase().indexOf("SQL") != -1)
			return SQLSERVER;
		if (dpn.toUpperCase().indexOf("SYBASE") != -1)
			return SYBASE;
		if (dpn.toUpperCase().indexOf("INFORMIX") != -1)
			return INFORMIX;
		return UNKOWNDATABASE;
	}

	/**
	 * @param dmd
	 * @return
	 * @throws SQLException
	 */
	private String getDbName(DatabaseMetaData dmd) throws SQLException {
		// ��ȡ�������ӵ���ݿ�����
		String m_dbname = null;
		String dpn = dmd.getDatabaseProductName();
		if (dpn.toUpperCase().indexOf("POSTGRESQL") != -1)
			m_dbname = "POSTGRESQL";
		if (dpn.toUpperCase().indexOf("DB2") != -1)
			m_dbname = "DB2";
		if (dpn.toUpperCase().indexOf("ORACLE") != -1)
			m_dbname = "ORACLE";
		if (dpn.toUpperCase().indexOf("SQL") != -1)
			m_dbname = "SQL";
		if (dpn.toUpperCase().indexOf("INFORMIX") != -1)
			m_dbname = "INFORMIX";
		// if (dpn.toUpperCase().indexOf("OSCAR") != -1)
		// m_dbname = "OSCAR";
		return m_dbname;
	}

	/**
	 * �ж��Ƿ���jdbc odbc������
	 * 
	 * @return
	 * @throws SQLException
	 */
	private boolean isODBC(DatabaseMetaData dmd) throws SQLException {
		String url = dmd.getURL();
		return url.indexOf("odbc") >= 0;
	}

	/**
	 * @param dmd
	 * @return
	 * @throws SQLException
	 */
	private boolean isSupportBatch(DatabaseMetaData dmd) throws SQLException {
		return dmd.supportsBatchUpdates();

	}

	/**
	 * @throws SQLException
	 */
	private int getDbVersion(Connection m_con) throws SQLException {
		int m_DbVersion = -1;
		java.sql.Statement stmt = null;
		try {
			DatabaseMetaData meta = m_con.getMetaData();
			if (m_con == null)
				return m_DbVersion;
			String strDbVersion;
			String strVersion;
			int dbType = getDbType(meta);
			if (dbType != ORACLE) {
				return m_DbVersion;
			} else {
				stmt = m_con.createStatement();
				java.sql.ResultSet rs = stmt
						.executeQuery("select value from V$parameter where lower(name)='compatible'");
				if (rs.next()) {
					strDbVersion = rs.getString(1);
					StringTokenizer dbVersion = new StringTokenizer(
							strDbVersion, ".");
					if (dbVersion.hasMoreTokens()) {
						strVersion = dbVersion.nextToken();
						m_DbVersion = Integer.parseInt(strVersion);
					}
				}
			}
			return m_DbVersion;
		} catch (SQLException e) {
			return m_DbVersion;
		} finally {
			if (stmt != null)
				stmt.close();

		}
	}

}
