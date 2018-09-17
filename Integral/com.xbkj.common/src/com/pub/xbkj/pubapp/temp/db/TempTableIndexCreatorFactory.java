package com.pub.xbkj.pubapp.temp.db;


public class TempTableIndexCreatorFactory {

	static public TempTableIndexCreator getDBTemptable(String dbname) {
		
		if (dbname.equalsIgnoreCase("SQL")) {
			return new GfcSQLServerTempTableCreator();
		}
		if (dbname.equalsIgnoreCase("ORACLE")) {
			return new GfcOracleTempTableCreator();
		}
		if (dbname.equalsIgnoreCase("DB2")) {
			return new GfcDB2TempTableCreator();
		} else {
			throw new IllegalArgumentException("can't support the" + dbname
					+ " database ");
		}
	}

}