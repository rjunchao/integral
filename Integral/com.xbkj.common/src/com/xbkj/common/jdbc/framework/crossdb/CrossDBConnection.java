package com.xbkj.common.jdbc.framework.crossdb;

import java.sql.Connection;
import java.sql.SQLException;

import com.xbkj.common.jdbc.framework.DataSourceCenter;
//import com.grc.mxfw.bs.framework.server.ServerConfiguration;
//import com.grc.basic.jdbc.framework.crossdb.adapter.Adapter;
//import com.grc.common.jdbc.framework.util.LRUCache;

/**
 * @nopublish ��ݿ����� created by hey in 2005.5
 */
public class CrossDBConnection extends CrossDBObject //implements Connection,DBConsts 
{

	//
	private String dataSource = null;
//
//	private Adapter adapter = null;
//
//	// ��װ��connection
//	private Connection realConnection = null;
//
//	// Statement����
//	private Vector<Statement> statements = new Vector<Statement>();
//
//	// ��ݿ�����
//	private int dbType = -1;
//
//	// ��ݿ����
//	private String dbCode = null;
//
//	// SQLת����
//	private SqlTranslator translator = null;
//
//	// �Ƿ�ر�
//	private boolean closed = false;
//
//	// JdbcOdbcStatement�Ƿ�֧����ݿ�����*
//	private boolean batchSupported = true;
//
//	// �ڱ��������Ƿ�����SQL������
//	private boolean enableTranslator = true;
//
//	// �Ƿ���Ҫ��sql server2000��JdbcOdbcDriver�����⴦��
//	private boolean jdbcOdbc = false;
//
//	// �Ƿ���Ҫ����JDBCOdcb�ŵ�˫�ֽڴ����Bug*
//	private boolean jdbcOdbcBug = false;
//
//	// ���ʱ���
//	private boolean addTimeStamp = true;
//
//	// databasemetadata�࣬���metadata��ݵĶ�ȡ
//	private DatabaseMetaData dbmd = null;
//
//	// Connection������
//	// private static int counter;
//
//	protected int id;
//
//	// ������仺��
//	private LRUCache cache = null;
//
//	/**
//	 * @param conn
//	 * @throws SQLException
//	 */
	public CrossDBConnection(Connection conn, String dataSource)
			throws SQLException {
		super();
		this.dataSource = dataSource;
		DataSourceCenter sourceCenter = DataSourceCenter.getInstance();
//		realConnection = conn;
//		batchSupported = sourceCenter.isSupportBatch(dataSource);
//		jdbcOdbc = sourceCenter.isODBC(dataSource);
//		dbType = sourceCenter.getDatabaseType(dataSource);
//		if (dbType == UNKOWNDATABASE)
//			dbType = sourceCenter.getDbType(conn.getMetaData());
//		// �õ�������
//		cache = SQLCache.getInstance().getCache(dataSource);
//		translator = new SqlTranslator(dbType);
//		// �õ�������
//		adapter = AdapterFactory.getAdapter(dbType);
//		adapter.setNativeConn(realConnection);
//		translator.setConnection(this);
//		NativeJdbcExtractor extractor = NativeJdbcExtractorFactory
//				.createJdbcExtractor();
//		id = System.identityHashCode(extractor.getNativeConnection(conn));
//		// TODO:NEED HGY AUDIT.
//		ThreadTracer.getInstance().openConnection(dataSource, id);
//		if (dbType == ORACLE && ThreadTracer.getInstance().isUserDebug()
//				&& !ThreadTracer.getInstance().isExistConn(id)) {
//			StringBuffer mapInfo = new StringBuffer(ServerConfiguration
//					.getServerConfiguration().getServerName());
//			mapInfo.append("#");
//			mapInfo.append(Thread.currentThread().getName());
//			// mapInfo.append("#");
//			// mapInfo.append(Logger.getMDC("user"));
//			mapSessionToDB(mapInfo.toString());
//		}
//		// ThreadTracer.getInstance().setThrowable(new Throwable());
//		initHistory(dataSource, conn);
	}
//
//	// TODO:NEED HGY AUDIT.
//	private void mapSessionToDB(String value) {
//		CallableStatement statment = null;
//		try {
//			if (dbType == ORACLE) {
//				ThreadTracer.getInstance().updateEvent("map thread to db.");
//				if (closed)
//					return;
//				statment = prepareCall("call DBMS_SESSION.SET_IDENTIFIER(?)");
//				statment.setString(1, value);
//				statment.execute();
//				statment.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (statment != null) {
//				try {
//					statment.close();
//					statment = null;
//				} catch (Exception ex) {
//					// ex.printStackTrace();
//				}
//			}
//			ThreadTracer.getInstance().updateEvent("end map thread to db");
//		}
//	}
//
//	private void initHistory(String name, Connection conn) {
//		SqlHistoryMap sqlHistoryMap = (SqlHistoryMap) SqlHistoryMap
//				.getCurrentSqlHistoryMap();
//		SqlHistory sqlHistory = sqlHistoryMap.getSqlHistory(id);
//		if (sqlHistory == null) {
//			sqlHistory = new SqlHistory();
//			sqlHistory.setDataSource(name);
//			sqlHistory.setConnId(id);
//			sqlHistory.setThreadName(Thread.currentThread().getName());
//			sqlHistory.setThrowable(new Throwable());
//			sqlHistory.setClientAddress((String) Logger.getMDC("remoteAddr"));
//			sqlHistory.setClientPort("" + Logger.getMDC("remotePort"));
//			sqlHistoryMap.addSqlHistory(sqlHistory);
//		} else {
//			sqlHistory.setThrowable(new Throwable());
//		}
//
//	}
//
//	/**
//	 * @param conn
//	 * @param moduleLang
//	 *            Ĭ��Ϊ0�������0�ͽ�������ת��
//	 * @throws SQLException
//	 */
	public CrossDBConnection(Connection conn) throws SQLException {
		this(conn, DataSourceCenter.getInstance().getSourceName());
	}
//
//	public void clearWarnings() throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.trace(getId());
//			realConnection.clearWarnings();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public void close() throws SQLException {
//		try {
//			// TODO:NEED HGY AUDIT.
//			if (!closed) {
//				ThreadTracer.getInstance().closeConnection(dataSource, id);
//			}
//			if (Logger.isInfoEnabled())
//				Trace.traceDetail("Close " + getId());
//			closed = true;
//			closeStatements();
//			// if(dbType==DBConsts.DB2)
//			// TempTabMgr.getInstance().dropTempTables(this);
//			realConnection.close();
//
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public void commit() throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.trace(getId());
//			// TODO:NEED HGY TOAUDIT.
//			ThreadTracer.getInstance().updateEvent("begin commit");
//			realConnection.commit();
//			ThreadTracer.getInstance().updateEvent("end commmit");
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public Statement createStatement() throws SQLException {
//		try {
//			CrossDBStatement st = null;
//			st = new CrossDBStatement(realConnection.createStatement(), this,
//					dbType, batchSupported, jdbcOdbcBug, cache, dataSource);
//			convertLang(st);
//			registerStatement(st);
//			if (Trace.isEnabled())
//				Trace.traceResult("Create:" + st.getId());
//			return st;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * ת����������
//	 * 
//	 * @param st
//	 */
//	private void convertLang(CrossDBStatement st) {
//		if (nModuleLang != 0) {
//			st.nModuleLang = nModuleLang;
//			st.setGU_CONVERT(true);
//			st.setUG_CONVERT(true);
//		}
//	}
//
//	public Statement createStatement(int resultSetType, int resultSetConcurrency)
//			throws SQLException {
//		try {
//			if (Trace.isEnabled())
//				Trace
//						.trace(getId(), resultSetType + ","
//								+ resultSetConcurrency);
//			CrossDBStatement stat = new CrossDBStatement(realConnection
//					.createStatement(resultSetType, resultSetConcurrency),
//					this, dbType, batchSupported, jdbcOdbcBug, cache,
//					dataSource);
//			convertLang(stat);
//			registerStatement(stat);
//			return stat;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	public boolean getAutoCommit() throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId());
//		try {
//			return realConnection.getAutoCommit();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public String getCatalog() throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId());
//		checkClosed();
//		try {
//			return realConnection.getCatalog();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * ���metadataһ��connectionֻ��ȡһ��
//	 * 
//	 * @return
//	 * @throws SQLException
//	 */
//	public java.sql.DatabaseMetaData getMetaData() throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId());
//		checkClosed();
//		try {
//			if (dbmd == null) {
//				// System.out.println("get meta con" + realConnection);
//				dbmd = realConnection.getMetaData();
//				// System.out.println("get meta " + dbmd);
//			}
//			return dbmd;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * @return
//	 * @throws SQLException
//	 */
//	public int getTransactionIsolation() throws SQLException {
//		checkClosed();
//		try {
//			return realConnection.getTransactionIsolation();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public Map getTypeMap() throws SQLException {
//		checkClosed();
//		return realConnection.getTypeMap();
//	}
//
//	public java.sql.SQLWarning getWarnings() throws SQLException {
//		checkClosed();
//		try {
//			return realConnection.getWarnings();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * @return
//	 * @throws SQLException
//	 */
//	public boolean isClosed() throws SQLException {
//		checkClosed();
//		return realConnection.isClosed();
//	}
//
//	/**
//	 * @return
//	 * @throws SQLException
//	 */
//	public boolean isReadOnly() throws SQLException {
//		checkClosed();
//		try {
//			return realConnection.isReadOnly();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	/**
//	 * @param sql
//	 * @return
//	 * @throws SQLException
//	 */
//	public String nativeSQL(String sql) throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), sql);
//		checkClosed();
//		try {
//			String sqlFixed = translate(sql);
//			String val = realConnection.nativeSQL(sqlFixed);
//			if (Trace.isEnabled())
//				Trace.traceResultQuote(sqlFixed);
//			return val;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public CallableStatement prepareCall(String sql) throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), sql);
//		checkClosed();
//		try {
//			String sqlFixed = translate(sql);
//			CallableStatement s = realConnection.prepareCall(sqlFixed);
//			registerStatement(s);
//			return s;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public CallableStatement prepareCall(String sql, int resultSetType,
//			int resultSetConcurrency) throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), sql);
//		checkClosed();
//		try {
//
//			String sqlFixed = translate(sql);
//			// if (isJdbcOdbcBug())
//			// sqlFixed = fixJdbcOdbcCharToByte(sqlFixed);
//			CallableStatement s = realConnection.prepareCall(sqlFixed);
//			registerStatement(s);
//			return s;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public PreparedStatement prepareStatement(String sql) throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), "");
//		checkClosed();
//		CrossDBPreparedStatement s = null;
//		String sqlFixed = translate(sql);
//		s = new CrossDBPreparedStatement(realConnection
//				.prepareStatement(sqlFixed), this, sqlFixed, dbType, true,
//				false, cache, dataSource);
//		convertLang(s);
//		s.setDbType(dbType);
//		registerStatement(s);
//		// if (Trace.isEnabled())
//		// Trace.traceResult("Create:" + s.getId());
//		return s;
//	}
//
//	public PreparedStatement prepareStatement(String sql, int resultSetType,
//			int resultSetConcurrency) throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), "");
//		checkClosed();
//		CrossDBPreparedStatement s = null;
//		String sqlFixed = translate(sql);
//		s = new CrossDBPreparedStatement(realConnection.prepareStatement(
//				sqlFixed, resultSetType, resultSetConcurrency), this, sqlFixed,
//				dbType, true, false, cache, dataSource);
//		// s.setSql(sqlFixed);
//		convertLang(s);
//		s.setDbType(dbType);
//		registerStatement(s);
//		if (Trace.isEnabled())
//			Trace.traceResult("Create��" + s.getId());
//		return s;
//
//	}
//
//	protected void registerStatement(Statement s) {
//		statements.addElement(s);
//		return;
//	}
//
//	public void rollback() throws SQLException {
//		checkClosed();
//		// if (Trace.isEnabled())
//		// Trace.trace(getId());
//		try {
//			realConnection.rollback();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	public void setAutoCommit(boolean autoCommit) throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.trace(getId(), "" + autoCommit);
//			realConnection.setAutoCommit(autoCommit);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public void setCatalog(String arg0) throws SQLException {
//		try {
//			realConnection.setCatalog(arg0);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public void setReadOnly(boolean arg0) throws SQLException {
//		try {
//			realConnection.setReadOnly(arg0);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * ���������ڽ�SQLת������Ϊ��Ч״̬���������Ӧ�ó��� ����Ҫת��ʱ����������������á� �������ڣ�(00-9-17 18:14:46)
//	 * 
//	 * @param b
//	 *            boolean
//	 */
//	public void setSqlTrans(boolean b) {
//		translator.setTransFlag(b);
//	}
//
//	public void setTransactionIsolation(int level) throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.trace(getId(), level);
//			realConnection.setTransactionIsolation(level);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public void setTypeMap(Map map) throws SQLException {
//		try {
//
//			realConnection.setTypeMap(map);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/**
//	 * 
//	 */
//	public String toString() {
//		return (realConnection == null) ? "connection is closed"
//				: realConnection.toString() + ":" + id;
//	}
//
	public void enableSQLTranslator(boolean newBEnableSQLTranslator) {
//		enableTranslator = newBEnableSQLTranslator;
	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2002-1-13 18:42:38)
//	 * 
//	 * @return nc.bs.mw.sqltrans.SqlTranslator
//	 */
//	public SqlTranslator getSqlTranslator() {
//		return translator;
//	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2002-6-4 10:13:46)
//	 * 
//	 * @return boolean
//	 */
//	public boolean isAddTimeStamp() {
//		return addTimeStamp;
//	}
//
//	public boolean isJdbcOdbcBug() {
//
//		return jdbcOdbcBug;
//	}
//
//	/**
//	 * ��sql server2000�¶�JdbcOdbcDriver�����⴦�? �������ڣ�(2005-4-14 9:22:20)
//	 * 
//	 * @return boolean
//	 */
//	public boolean isJdbcOdbc() {
//		return jdbcOdbc;
//	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2002-1-30 13:22:20)
//	 * 
//	 * @return boolean
//	 */
//	public boolean isSQLTranslatorEnabled() {
//		return enableTranslator;
//	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2002-6-4 10:13:46)
//	 * 
//	 * @param newAddTimeStamp
//	 *            boolean
//	 */
	public void setAddTimeStamp(boolean newAddTimeStamp) {
//		addTimeStamp = newAddTimeStamp;
	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2001-8-29 20:35:10)
//	 * 
//	 * @return boolean
//	 */
//	public boolean supportBatchOperate() {
//		return batchSupported;
//	}
//
//	/**
//	 * �˴����뷽��˵���� �������ڣ�(2003-6-26 14:22:30)
//	 * 
//	 * @param i
//	 *            int
//	 */
//	public void setDatabaseType(int i) {
//		dbType = i;
//	}
//
//	/*
//	 * ���� Javadoc��
//	 * 
//	 * @see java.sql.Connection#getHoldability()
//	 */
//	public int getHoldability() throws SQLException {
//		checkClosed();
//		try {
//			return realConnection.getHoldability();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	/*
//	 * ���� Javadoc��
//	 * 
//	 * @see java.sql.Connection#setHoldability(int)
//	 */
//	public void setHoldability(int holdability) throws SQLException {
//		try {
//			realConnection.setHoldability(holdability);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	/*
//	 * ���� Javadoc��
//	 * 
//	 * @see java.sql.Connection#setSavepoint()
//	 */
//	public Savepoint setSavepoint() throws SQLException {
//		try {
//			return realConnection.setSavepoint();
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	/*
//	 * ���� Javadoc��
//	 * 
//	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
//	 */
//	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
//
//		try {
//			realConnection.releaseSavepoint(savepoint);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//
//	}
//
//	/*
//	 * 
//	 */
//	public void rollback(Savepoint savepoint) throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.traceQuote(getId(), savepoint + "");
//			realConnection.rollback(savepoint);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	/*
//	 * 
//	 */
//	public Statement createStatement(int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//		try {
//			if (Trace.isEnabled())
//				Trace.trace(getId(), resultSetType + "," + resultSetConcurrency
//						+ "," + resultSetHoldability);
//			CrossDBStatement stat = new CrossDBStatement(realConnection
//					.createStatement(resultSetType, resultSetConcurrency,
//							resultSetHoldability), this, dbType,
//					batchSupported, jdbcOdbcBug, cache, dataSource);
//			convertLang(stat);
//			registerStatement(stat);
//			return stat;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public CallableStatement prepareCall(String sql, int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), sql);
//		checkClosed();
//		try {
//			String sqlFixed = translate(sql);
//			CallableStatement s = realConnection.prepareCall(sqlFixed);
//			registerStatement(s);
//			return s;
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
//			throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId(), sql + "," + autoGeneratedKeys);
//		throw ExceptionFactory.getUnsupportedException();
//	}
//
//	public PreparedStatement prepareStatement(String sql, int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//
//		// if (Trace.isEnabled())
//		// Trace.traceQuote(getId(), sql);
//		checkClosed();
//		CrossDBPreparedStatement s = null;
//		String sqlFixed = translate(sql);
//		s = new CrossDBPreparedStatement(realConnection.prepareStatement(
//				sqlFixed, resultSetType, resultSetConcurrency,
//				resultSetHoldability), this, sqlFixed, dbType, true, false,
//				cache, dataSource);
//		convertLang(s);
//		s.setDbType(dbType);
//		registerStatement(s);
//		if (Trace.isEnabled())
//			Trace.traceResult("create:" + s.getId());
//		return s;
//	}
//
//	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
//			throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId());
//		throw ExceptionFactory.getUnsupportedException();
//
//	}
//
//	public Savepoint setSavepoint(String name) throws SQLException {
//		try {
//			// if (Trace.isEnabled())
//			// Trace.traceQuote(getId(), name);
//			return realConnection.setSavepoint(name);
//		} catch (SQLException e) {
//			throw translator.getSqlException(e);
//		}
//	}
//
//	public PreparedStatement prepareStatement(String sql, String[] columnNames)
//			throws SQLException {
//		// if (Trace.isEnabled())
//		// Trace.trace(getId(), sql + "," + columnNames[0]);
//		throw ExceptionFactory.getUnsupportedException();
//
//	}
//
//	/**
//	 * �õ���ݿ�����
//	 * 
//	 * @return
//	 * @throws SQLException
//	 */
//	public int getDatabaseType() throws SQLException {
//		return dbType;
//	}
//
//	/**
//	 * @return
//	 */
//	public String getDbcode() {
//		return dbCode;
//	}
//
//	/**
//	 * ����SQL�����������ݿ�
//	 * 
//	 * @param sql
//	 * @return
//	 * @throws SQLException
//	 */
//	protected String translate(String sql) throws SQLException {
//		if (Trace.isEnabled()) {
//			Trace.traceSQL("Database Type 0d 1o 2s:" + dbType);
//			Trace.traceSQL("Original SQL :" + sql);
//		}
//		if (sql == null)
//			return null;
//		if (!isSQLTranslatorEnabled()) {
//			if (isAddTimeStamp())
//				return stampSQL(sql);
//			return sql;
//		}
//		if (sql.length() > 8000) {
//			String sqlFixed = translator.getSql(sql);
//			if (isAddTimeStamp())
//				sqlFixed = stampSQL(sqlFixed);
//			return sqlFixed;
//		}
//		String key = sql;
//		String result = (String) cache.getPreparedSQL(key);
//		if (result != null) {
//			if (isAddTimeStamp())
//				result = stampSQL(result);
//			if (Trace.isEnabled())
//				Trace.traceSQL("Translated Prepared SQL :" + result);
//			return result;
//		}
//
//		// if (UG_CONVERT)
//		// sql = unicodeFromNoUC(sql);
//		sql = translator.getSql(sql);
//		// if (isJdbcOdbcBug())
//		// sql = fixJdbcOdbcCharToByte(sql);
//		cache.putPreparedSQL(key, sql);
//		if (isAddTimeStamp())
//			sql = stampSQL(sql);
//		if (Trace.isEnabled())
//			Trace.traceSQL("Translated Prepared  SQL :" + sql);
//		return sql;
//	}
//
//	/**
//	 * �ر�����Statement
//	 */
//	private void closeStatements() {
//		Object[] ss;
//		synchronized (statements) {
//			ss = new Object[statements.size()];
//			statements.copyInto(ss);
//		}
//		for (int i = 0; i < ss.length; i++) {
//			if (ss[i] == null || !(ss[i] instanceof CrossDBStatement)) {
//			} else {
//				try {
//					((CrossDBStatement) ss[i]).close();
//				} catch (SQLException e) {
//				}
//			}
//		}
//		statements = new Vector<Statement>();
//	}
//
//	/**
//	 * ��������Ƿ�ر�
//	 * 
//	 * @throws SQLException
//	 *             ���ر��׳��쳣
//	 */
//	private void checkClosed() throws SQLException {
//		if (closed)
//			throw new SQLException("�����Ѿ��ر�");
//	}
//
//	/**
//	 * @param s
//	 */
//	protected void deregisterStatement(CrossDBStatement s) {
//		if (s == null) {
//			return;
//		}
//		if (!statements.removeElement(s)) {
//			return;
//		}
//	}
//
//	/**
//	 * @return
//	 */
//
	public String getId() {
//		return "Connection(" + id + ")";
		return null;
	}
//
//	protected Adapter getAdapter() {
//		return adapter;
//	}
//
//	public Connection getPConnection() {
//		return realConnection;
//	}
//
	public String getDataSource() {
		return dataSource;
	}

}