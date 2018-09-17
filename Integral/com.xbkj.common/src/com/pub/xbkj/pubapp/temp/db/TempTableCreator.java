package com.pub.xbkj.pubapp.temp.db;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * @nopublish
 * @author hey
 *
 */
public interface TempTableCreator {
public void setRowNum(int rowNum);
	
public	String createTempTable(Connection con, String tableName, String columns, String... indexs) throws SQLException ;

}
