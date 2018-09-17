package com.xbkj.mxfw.bs.framework.mx.jdbc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author hgy
 * 
 */
public class SqlHistory {

	private String dataSource;

	private int connId;  

	private String lastSql;

	private String clientAddress;

	private String clientPort;

	private Throwable throwble;

	private String stackTrace;

	private long lastSqlBeginTime;

	private List<String> historySqls = new LinkedList<String>();
	
	private String threadName;

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getConnId() {
		return connId;
	}

	public void setConnId(int conn) {
		this.connId = conn;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getLastSql() {
		return lastSql;
	}

	public void setLastSql(String lastSql) {
		if (this.lastSql != null) {
			if (historySqls.size() < 2) {
				historySqls.add(this.lastSql);
			} else {
				historySqls.remove(0);
				historySqls.add(this.lastSql);
			}
		}
		this.lastSql = lastSql;
		lastSqlBeginTime = System.currentTimeMillis();
	}

	public void setThrowable(Throwable throwable) {
		this.throwble = throwable;
		this.stackTrace = null;

	}

	public String getStackTrace() {
		if (stackTrace != null)
			return stackTrace;
		if (throwble != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwble.printStackTrace(pw);
			stackTrace = sw.toString();
			return stackTrace;
		} else {
			return null;
		}
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientIP) {
		this.clientAddress = clientIP;
	}

	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public long getLastSqlBeginTime() {
		return this.lastSqlBeginTime;
	}

	public List<String> getHistorySqls() {
		return this.historySqls;
	}
}
