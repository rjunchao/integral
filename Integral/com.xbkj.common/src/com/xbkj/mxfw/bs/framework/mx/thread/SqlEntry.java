package com.xbkj.mxfw.bs.framework.mx.thread;

import java.io.Serializable;

public class SqlEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	// connectionid
	private int connectioinId;
	// begin time.
	private long begintime = 0l;
	// end time.
	private long endTime = 0l;
	// cost time.
	private long costTime = 0l;
	// sql text.
	private String sqltext = null;
	// data source.
	private String datasource = null;
	// row number
	private int rownum = 0;


	public int getConnectioinId() {
		return connectioinId; 
	}

	public void setConnectioinId(int connectioinId) {
		this.connectioinId = connectioinId;
	}

	public long getBegintime() {
		return begintime;
	}

	public void setBegintime(long begintime) {
		this.begintime = begintime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
		costTime = endTime - begintime;
		// ��sql��Ϣд����־
		String sql = getSqltext();
		if (sql == null || sql.length() == 0)
			return;
		if (!sql.trim().toLowerCase().startsWith("select")) {
			String msg = sql+"|costtime="+costTime+"|" ;
			ThreadTracer.getInstance().logSql(msg);
		}
	}

	public String getSqltext() {
		return sqltext;
	}

	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}

	public long getCostTime() {
		return costTime;
	}

	public void initCosttime() {
		if (endTime < 1) {
			costTime = (begintime < 1 ? 0 : (System.currentTimeMillis() - begintime));
		} else {
			costTime = endTime - begintime;
		}
	
	}

	public void setDataSource(String dataSource) {
		this.datasource = dataSource;
	}

	public String getDataSource() {
		return datasource;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
		String sql = getSqltext();
		if (sql == null || sql.length() == 0)
			return;

		if (sql.trim().toLowerCase().startsWith("select")) {
			String msg = sql+"|costtime="+costTime+"||rownum=" + rownum + "|" ;
			ThreadTracer.getInstance().logSql(msg);
		}
	} 
}
