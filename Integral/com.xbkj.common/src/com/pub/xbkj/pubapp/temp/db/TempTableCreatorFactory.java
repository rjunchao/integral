package com.pub.xbkj.pubapp.temp.db;

/**
 * @nopublish
 * @author hey
 * 
 */
public class TempTableCreatorFactory {

	static public TempTableCreator getDBTemptable(String dbname) {
		
//		if (dbname.equalsIgnoreCase("POSTGRESQL")) {
//			return new PostgreSQLTempTableCreator();
//		}
//		if (dbname.equalsIgnoreCase("SQL")) {
//			return new SQLServerTempTableCreator();
//		}
		if (dbname.equalsIgnoreCase("ORACLE")) {
			return new OracleTempTableCreator();
		}
//		if (dbname.equalsIgnoreCase("GBASE")) {
//			return new GbaseTempTableCreator();
//		}
//		if (dbname.equalsIgnoreCase("DB2")) {
//			return new DB2TempTableCreator();
//		}
		else {
			throw new IllegalArgumentException("can't support the" + dbname
					+ " database ");
		}
	}

}
