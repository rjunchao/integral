package com.pub.xbkj.pubapp.temp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.query.DBTool;
import com.xbkj.basic.vo.jcom.lang.StringUtil;



public class GfcOracleTempTableCreator implements TempTableIndexCreator {

	public String[] createIndex(Connection con, String tableName, String[] indexes) throws SQLException {
		List<String> indexNameList = new ArrayList<String>();
		String sql = null;
//		Connection newcon = null;
		Statement stmt = null;
		boolean iExist = true;
		try {
			stmt = con.createStatement();
			//判断是否已经存在索引
			for (int i = 0; i < indexes.length; i++) {
				indexNameList.add("i_" + tableName + "_" + indexes[i]); 
			}
			String indexStr = StringUtil.getUnionStr(indexNameList.toArray(new String[0]), ",", "'");
			ResultSet rs = stmt
			.executeQuery("select index_name from user_indexes where table_name = '"
					+ tableName.toUpperCase()
					+ "' and index_name in (" + indexStr.toUpperCase() + ")");
			if(rs.next()){
				throw new SQLException("the index is exist");
			}else{
				iExist = false;
			}
			//如果没有索引，则插入
			if(!iExist){
				for (int i = 0; i < indexes.length; i++) {
					sql = "create index " + indexNameList.get(i) + " on " + tableName + "(" + indexes[i] + ")";
					stmt.addBatch(sql);
				}
				stmt.executeBatch();
			}
		} finally {
			DBTool.closeStmt(stmt);
			DBTool.closeConn(con);
		}
		return indexNameList.toArray(new String[0]);
	}
	
	
	public String createGroupIndex(Connection con, String tableName, String colName, String indexStr)  throws SQLException {
		String sql = null;
		String indexName = null;
//		Connection newcon = null;
		Statement stmt = null;
		boolean iExist = true;
		try {
			stmt = con.createStatement();
			indexName = "i_" + tableName + "_" + colName;
			ResultSet rs = stmt
			.executeQuery("select index_name from user_indexes where table_name = '"
					+ tableName.toUpperCase()
					+ "' and index_name = '" + indexName + "'");
			if(!rs.next()){
				iExist = false;
			}
			//如果没有索引，则插入
			if(!iExist){
				sql = "create index " + indexName + " on " + tableName + "(" + indexStr + ")";
				stmt.executeUpdate(sql);
			}
		} finally {
			DBTool.closeStmt(stmt);
			DBTool.closeConn(con);
		}
		return indexName;
	}

}
