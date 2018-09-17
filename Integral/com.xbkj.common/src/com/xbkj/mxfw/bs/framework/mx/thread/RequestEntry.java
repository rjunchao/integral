package com.xbkj.mxfw.bs.framework.mx.thread;

import java.io.Serializable;

public class RequestEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean debugOn = true; 
	private String userid = null;
	private boolean debugService = false;
	public static final String CMDTYPE_DEBUG_GLOBAL = "DEBUG_GLOBAL";
	public static final String CMDTYPE_DEBUG_USER = "DEBUG_USER";
	public static final String CMDTYPE_REMOVE_DEBUG_USER = "REMOVE_DEBUG_USER";
	public static final String CMDTYPE_QUERY = "QUERY";
	public static final String CMDTYPE_JVM_HEAP = "JVMHEAP";
	public static final String CMDTYPE_THREADPOOL_USED = "THREADPOOLUSED";
	public static final String CMDTYPE_CHECK = "CHECK";
	public static final String CMDTYPE_DEBUG_SERVICE = "DEBUG_SERVICE";
	public static final String CMDTYPE_DEBUG_PROCESSINFO = "PROCESSINFO";
	private long checkMaxCountofSql = 1000;
	private long checkCosttime = 120 * 1000l;
	private long checkMaxCostSql = 60 * 1000l;
	private String cmd = null;

	public long getCheckCosttime() {
		return checkCosttime;
	}

	public void setCheckCosttime(long checkCosttime) {
		this.checkCosttime = checkCosttime;
	}

	public long getCheckMaxCostSql() {
		return checkMaxCostSql;
	}

	public void setCheckMaxCostSql(long checkMaxCostSql) {
		this.checkMaxCostSql = checkMaxCostSql;
	}

	public long getCheckMaxCountofSql() {
		return checkMaxCountofSql;
	}

	public void setCheckMaxCountofSql(long checkMaxCountofSql) {
		this.checkMaxCountofSql = checkMaxCountofSql;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public boolean isDebugOn() {
		return debugOn;
	}

	public void setDebugOn(boolean isGlobalDebug) {
		this.debugOn = isGlobalDebug;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCmd() {
		return cmd;
	}

	public boolean isDebugService() {
		return debugService;
	}

	public void setDebugService(boolean debugService) {
		this.debugService = debugService;
	}
}
  
