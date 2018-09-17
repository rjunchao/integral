package com.xbkj.mxfw.bs.framework.mx.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grc.log.bs.logging.Logger;

public class SqlHistoryMap {
	public static final String KEY = "sqlHistoryMap";

	private Map<Integer, SqlHistory> historyMap = new HashMap<Integer, SqlHistory>();

	public SqlHistory getSqlHistory(int conn) {
		return historyMap.get(conn);
	}

	public void addSqlHistory(SqlHistory history) {
		historyMap.put(history.getConnId(), history);
	}

	public static SqlHistoryMap getCurrentSqlHistoryMap() {
		SqlHistoryMap sqlHistoryMap = (SqlHistoryMap) Logger.getMappedThreadState(SqlHistoryMap.KEY);
		if (sqlHistoryMap == null) {
			sqlHistoryMap = new SqlHistoryMap();
			Logger.setMappedThreadState(SqlHistoryMap.KEY, sqlHistoryMap);
		}

		return sqlHistoryMap;
	}

	@SuppressWarnings("unchecked")
	public static SqlHistory[] getAllSqlHistory() {
		List historyMapList = Logger.getMappedThreadStates(KEY);
		List l = new ArrayList();
		for (Object o : historyMapList) {
			l.addAll(((SqlHistoryMap) o).historyMap.values());
		}
		return (SqlHistory[]) l.toArray(new SqlHistory[0]);

	}
}
