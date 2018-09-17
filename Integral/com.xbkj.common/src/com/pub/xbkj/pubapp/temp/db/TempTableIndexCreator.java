package com.pub.xbkj.pubapp.temp.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface TempTableIndexCreator {
	
	/**
	 * indexName = i_tableName_indexs[0],i_table_indexs[1],i_table_indexs[2]...
	 * @param con
	 * @param tableName
	 * @param colName
	 * @param indexs
	 * @return
	 * @throws SQLException
	 */
	public String[] createIndex(Connection con, String tableName, String[] indexes) throws SQLException ;
	
	/**
	 * indexName = i_tabelName_colName
	 * @param con
	 * @param tableName
	 * @param colName
	 * @param indexStr
	 * @return
	 * @throws SQLException
	 */
	public String createGroupIndex(Connection con, String tableName, String colName, String indexStr) throws SQLException ;
	
}
