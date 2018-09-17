package com.pub.xbkj.pubapp.temp.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vbm.grc.pubapp.query.DBTool;

public class GfcDB2TempTableCreator implements TempTableIndexCreator {


	public String[] createIndex(Connection con, String tableName, String[] indexes) throws SQLException {
		List<String> indexNameList = new ArrayList<String>();
		String sql = null;
		Statement stmt = null;
		String indexName = null;
		try {
			stmt = con.createStatement();
			for (String index : indexes) {
				indexName = "i_" + tableName + "_" + index;
				sql = "create index " + indexName + " on " + tableName + "(" + index + ")";
				stmt.addBatch(sql);
				indexNameList.add(indexName);
			}
			stmt.executeBatch();
		} finally {
			DBTool.closeStmt(stmt);
			DBTool.closeConn(con);
		}
		return indexNameList.toArray(new String[0]);
	}
	
	
	public String createGroupIndex(Connection con, String tableName, String colName, String indexStr)  throws SQLException {
		String sql = null;
		String indexName = null;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			indexName = "i_" + tableName + "_" + colName;
			sql = "create index " + indexName + " on " + tableName + "(" + indexStr + ")";
			stmt.executeUpdate(sql);
		} finally {
			DBTool.closeDB(con, stmt);
		}
		return indexName;
	}

}
