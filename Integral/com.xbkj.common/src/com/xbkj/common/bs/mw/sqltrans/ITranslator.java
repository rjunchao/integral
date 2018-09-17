package com.xbkj.common.bs.mw.sqltrans;

/**
 * ģ��: TranslatorIF.java 
 * ����: Sql�������ӿ�,����Sql����������ʵ�ָýӿ� 
 * ����: cf
 */
public interface ITranslator {
	public int getDestDbType();

	public String getSourceSql();

	public String getSql() throws Exception;

	public java.sql.SQLException getSqlException();

	public void setSql(String sql);

	public void setSqlException(java.sql.SQLException e);
}