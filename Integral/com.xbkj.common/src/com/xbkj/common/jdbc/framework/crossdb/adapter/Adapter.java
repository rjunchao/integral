package com.xbkj.common.jdbc.framework.crossdb.adapter;

import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.xbkj.common.jdbc.framework.util.DBConsts;
//import com.grc.common.jdbc.framework.crossdb.CrossDBPreparedStatement;

/**
 * @nopublish User: ���� Date: 2005-5-16 Time: 13:55:53 Adapter1���˵��
 */
public interface Adapter extends DBConsts {
	public void setNativeConn(Connection nativeConn) throws SQLException;

	public void init() throws SQLException;

	String getName();

	String getDriverClass();

	String getBinaryConstant(String s);

	String getNow() throws SQLException;

	String getDefaultSchema() throws SQLException;

	SQLException convertThrowable(Throwable e);

	SQLException convertSQLException(SQLException e);

	void cancel(Statement stat) throws SQLException;

	byte[] getBytes(ResultSet rs, int columnIndex) throws SQLException;

	byte[] getBytes(ResultSet rs, String columnname) throws SQLException;

	String getString(ResultSet rs, int columnIndex) throws SQLException;

	String getString(ResultSet rs, String columnName) throws SQLException;

	boolean getBoolean(ResultSet rs, String columnName) throws SQLException;

	boolean getBoolean(ResultSet rs, int columnIndex) throws SQLException;

	Object getObject(ResultSet rs, int columnIndex, int scale)
			throws SQLException;

	Object getObject(ResultSet rs, String columnName, int scale)
			throws SQLException;

	Reader getCharacterStream(ResultSet rs, int columnIndex)
			throws SQLException;

	Reader getCharacterStream(ResultSet rs, String columnName)
			throws SQLException;

	String getClobString(ResultSet rs, int columnIndex) throws SQLException;

	String getClobString(ResultSet rs, String columnName) throws SQLException;

	public byte[] getBlobByte(ResultSet rs, String columnName)
			throws SQLException, java.io.IOException;

	public byte[] getBlobByte(ResultSet rs, int index) throws SQLException,
			java.io.IOException;

//	void setString(CrossDBPreparedStatement prep, int parameterIndex, String x)
//			throws SQLException;
//
//	void setBoolean(CrossDBPreparedStatement prep, int parameterIndex, boolean v)
//			throws SQLException;
//
//	void setCharacterStream(CrossDBPreparedStatement prep, int parameterIndex,
//			Reader x, int length) throws SQLException;
//
//	void setAsciiStream(CrossDBPreparedStatement prep, int parameterIndex,
//			InputStream x, int length) throws SQLException;
//
//	void setNull(CrossDBPreparedStatement prep, int parameterIndex, int sqlType)
//			throws SQLException;
//
//	void setBytes(CrossDBPreparedStatement prep, int parameterIndex, byte[] x)
//			throws SQLException;
//
//	void setBinaryStream(CrossDBPreparedStatement prep, int parameterIndex,
//			InputStream x, int length) throws SQLException;
}
