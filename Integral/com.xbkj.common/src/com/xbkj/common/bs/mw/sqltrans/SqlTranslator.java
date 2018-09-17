package com.xbkj.common.bs.mw.sqltrans;

/**
 *ģ��:	SqlTranslator.java
 *����:	Sql�������Ķ���ӿ�
 *����:	cf
 */

import com.xbkj.common.jdbc.framework.DataSourceCenter;
import com.xbkj.common.jdbc.framework.util.DBConsts;
import com.xbkj.log.bs.logging.Logger;

public class SqlTranslator implements DBConsts {

	/**
	 * SqlTranslator ������
	 */
	public SqlTranslator() {

		super();

		initTranslator(SQLSERVER);
		// ȱʡ����Ŀ����ݿ�ΪSQLSERVER
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator Over");
	}

	/**
	 * SqlTranslator ������
	 */

	public SqlTranslator(int dbType) {
		super();
		m_dbType = dbType;
		initTranslator(dbType);
		// ����Ŀ����ݿ�����
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator Over");
	}

	/**
	 *���Ŀ����ݿ�����ת��SQL���
	 */

	public String getSql(String srcSql) throws java.sql.SQLException {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSql");

		int destDbType = getDestDbType(); // �б���ݿ�����
		// ����
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSql Over");
		return getSql(srcSql, destDbType);
	}

	/**
	 *���Ŀ����ݿ�����ת��SQL���
	 */

	public String getSql(String srcSql, int destDbType)
			throws java.sql.SQLException {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSql");

		// ��Ŀ����ݿ����ͱ������������
		if (m_trans == null || m_trans.getDestDbType() != destDbType) {
			initTranslator(destDbType);
		}
		// ������ݿ�İ汾
		((TranslatorObject) m_trans).setDbVersion(m_DbVersion);

		// ����
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSql Over");
		return getResultSql(srcSql);
	}

	/**
	 *���Ŀ����ݿ�����ת��SQL�쳣
	 */

	synchronized public java.sql.SQLException getSqlException(
			java.sql.SQLException srcExp) {
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSqlException");
		// ����ת������ԭ���
		if (!m_bTranslate)
			return srcExp;
		// ���ñ�ת���쳣
		m_trans.setSqlException(srcExp);
		// ȡ�ò������쳣ת�����
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSqlException Over");
		return m_trans.getSqlException();
	}

	/**
	 *���Ŀ����ݿ�����ת��SQL�쳣
	 */

	public java.sql.SQLException getSqlException(java.sql.SQLException srcExp,
			int destDbType) {
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSqlException");
		// ��Ŀ����ݿ����ͱ任�������ñ任
		if (m_trans.getDestDbType() != destDbType) {
			initTranslator(destDbType);
		}
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getSqlException Over");
		return getSqlException(srcExp);
	}

	/**
	 * ���ر任ʱ�䡣 �������ڣ�(00-6-22 14:16:58)
	 * 
	 * @return long
	 */
	public long getTransTime() {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getTransTime");
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getTransTime Over");
		return m_lTime;
	}

	/**
	 * ���Ŀ����ݿ�����,������ͬ�ķ����� ȱʡΪsql server
	 **/
	private void initTranslator(int dbType) {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.initTranslator");

		switch (dbType) {
		case POSTGRESQL:
			m_trans = new TranslateToPostgreSQL();
			break;
		case DB2:
			m_trans = new TranslateToDB2();
			break;
		case ORACLE:
			m_trans = new TranslateToOracle();
			break;
		case SYBASE:
			m_trans = new TranslateToSybase();
			break;
		case SQLSERVER:
			m_trans = new TranslateToSqlServer();
			break;
		case INFORMIX:
			m_trans = new TranslateToInformix();
			break;
		default: // ȱʡΪSQLSERVER;
			m_trans = new TranslateToSqlServer();
		}
		// System.out.println("hey:----the translator--"+m_trans.getClass().getName());
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.initTranslator Over");
	}

	/**
	 * �����Ƿ����־�� �������ڣ�(00-9-17 18:30:18)
	 * 
	 * @param b
	 *            boolean
	 */
	public void setTransFlag(boolean b) {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.setTransFlag");
		m_bTranslate = b;
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.setTransFlag Over");
	}

	private boolean m_bTranslate = true;

	private int m_dbType = -1;

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-3-16 11:04:26) ���ܣ�ͨ������ģ��ӿڷ�����ݿ����ͣ�ȱʡΪSQLSERVER;
	 * 
	 * @return int ��ݿ�����
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	public int getDestDbType() {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getDestDbType");

		if (m_dbType != -1)
			return m_dbType;
		else
			Logger
					.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getDestDbType Over");
		return SQLSERVER;
	}

	/**
	 *�õ����Ŀ����ݿ�����ת�����SQL���
	 */

	synchronized public String getResultSql(String srcSql)
			throws java.sql.SQLException {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getResultSql");
		// ����ת��������ԭSQL���
		if (!m_bTranslate)
			return srcSql;
		// ��¼��ǰʱ��
		m_lTime = System.currentTimeMillis();
		// ȡ��ת�����
		String sResult;
		try {

			if (srcSql == null)
				return "";

			srcSql = srcSql.trim();
			srcSql = trimPreChars(srcSql);

			m_trans.setSql(srcSql);
			sResult = m_trans.getSql();

		} catch (Exception e) {
			throw new java.sql.SQLException(e.getMessage());
		}

		// ��������ʱ��
		m_lTime = System.currentTimeMillis() - m_lTime;
		// ����ת�����
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.getResultSql Over");
		return sResult;
	}

	/**
	 *���Ŀ����ݿ�����ת��SQL���
	 */

	public String trimPreChars(String srcSql) throws java.sql.SQLException {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.trimPreChars");
		if (srcSql == null || srcSql.length() < 1)
			return "";
		int pos = 0;
		String lineSep = System.getProperty("line.separator");
		while (pos < srcSql.length()
				&& (srcSql.charAt(pos) == ' ' || srcSql.charAt(pos) == '\t' || lineSep
						.indexOf(srcSql.charAt(pos)) >= 0)) {
			pos++;
		}
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.trimPreChars Over");
		return srcSql.substring(pos);
	}

	// ��ݿ�汾
	private int m_DbVersion = 0;

	// �Ƿ���Ҫ�����־
	private long m_lTime = 0;// ��������ʱ��

	private ITranslator m_trans = null;// ������

	/**
	 * �˴����뷽��˵���� ���ܣ�����Ŀ����ݿ������ �������ڣ�(2005-01-25 09:44:00)
	 */
	public void setConnection(java.sql.Connection con) {
		Logger.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.setConnection");
		// setDbVersion(m_con);
		m_DbVersion = DataSourceCenter.getInstance().getDatabaseVersion();
		Logger
				.setThreadState("nc.bs.mw.sqltrans.SqlTranslator.setConnection Over");
	}

}