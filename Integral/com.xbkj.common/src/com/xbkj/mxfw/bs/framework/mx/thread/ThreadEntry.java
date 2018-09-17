package com.xbkj.mxfw.bs.framework.mx.thread;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class ThreadEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	// �߳�id.
	private long threadID = 0l;

	// �߳����
	private String threadName = null;

	// �߳��¼�������execute .
	private String threadEvent = null;

	// Զ�̵�����Ϣ��Զ�̵�ַ��
	private String remoteAddr = null;

	// Զ�̵�����Ϣ���û�
	private String remoteUser = null;

	// ���õ��࣬����
	private String remoteCallMethod = null;

	// ���ÿ�ʼʱ��
	private long begintime = 0l;

	// sqlhistory
	private SqlHistory sqlHistory = null;

	// thread stack.
	private Throwable throwable = null;

	private String stackTrace = null;

	// �߳�״̬
	private String status = null;

	private boolean isDebug = true;

	private long costTime = 0l;

	// ͳ�ƣ�read result set��ʱ��
	private long totalCostOfReadRs = 0l;

	// ͳ��-read result set number.
	private long totalRowNumOfReadRs = 0;

	private long beginReadRsTime = 0l;

	// ͳ��-����д����ʱ��
	private long totalWriteToClientCost = 0l;

	private long beginWriteClientTime = 0l;

	// ͳ�ƣ������ʱ��
	private long totalReadClientTime = 01;

	private long beginReadClientTs = 0l;

	// ͳ��-����д���ֽ�.
	private long totalbyteswriteToClient = 0l;

	private long totalbytereadfromclient = 0l;

	// ��ǰ��threadstack.
	private StackTraceElement[] stackTrace0 = null;

	// �����Ϣ.
	private String checkMsg = "";

	private boolean isService;

	private String servername = null;

	private LinkedList<String> openedConnection = new LinkedList<String>();

	public String getServername() {
		return servername;
	}

	public void openConnection(String connid) {
		if (!openedConnection.contains((connid))) {
			openedConnection.add(connid);
		}
	}

	public void closeConnection(String connid) {
		openedConnection.remove(connid);
	}

	public int getCountOpenedConnection() {
		return openedConnection.size();
	}

	public ThreadEntry() {
		servername = System.getProperty("nc.server.name");
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getThreadEvent() {
		return threadEvent;
	}

	public void setThreadEvent(String threadEvent) {
		this.threadEvent = threadEvent;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	public String getRemoteCallMethod() {
		return remoteCallMethod;
	}

	public void setRemoteCallMethod(String remoteCallMethod) {
		this.remoteCallMethod = remoteCallMethod;
	}

	public long getBegintime() {
		return begintime;
	}

	public void setBegintime(long begintime) {
		this.begintime = begintime;
	}

	public SqlHistory getSqlHistory() {
		if (sqlHistory == null)
			sqlHistory = new SqlHistory();
		return sqlHistory;
	}

	public void setSqlHistory(SqlHistory sqlHistory) {
		this.sqlHistory = sqlHistory;
	}

	public void initCostTime() {
		costTime = System.currentTimeMillis() - getBegintime();
		costTime = (costTime < 0 ? 0 : costTime);
		if (sqlHistory != null)
			sqlHistory.initCostTime();
	}

	public long getCostTime() {
		return costTime;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getStackTrace() {
		if (stackTrace0 != null && stackTrace0.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < stackTrace0.length; i++) {
				sb.append(stackTrace0[i].toString());
				sb.append("\n");
			}
			return sb.toString();
		}
		if (throwable != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			stackTrace = sw.toString();
			return stackTrace;
		}
		return null;
	}

	public void setDebug(boolean bDebug) {
		isDebug = bDebug;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStats() {
		return status;
	}

	public void beginReadFromclient() {
		beginReadClientTs = System.currentTimeMillis();
		setThreadEvent("begin read from client");
	}

	public void endReadFromClient(int bytes) {
		totalReadClientTime = System.currentTimeMillis() - beginReadClientTs;
		totalbytereadfromclient = bytes;
		setThreadEvent("end read from client");
	}

	public void beginReadRs() {
		beginReadRsTime = System.currentTimeMillis();
		setThreadEvent("read resultset");
	}

	public void endReadRs(int rowcount) {
		getSqlHistory().getSqlEntrys().getLast().setRownum(rowcount);
		totalCostOfReadRs += (System.currentTimeMillis() - beginReadRsTime);
		totalRowNumOfReadRs += rowcount;
		setThreadEvent("end read resultset,row count=" + rowcount);
	}

	public void beginWriteToClient(long bytelength) {
		beginWriteClientTime = System.currentTimeMillis();
		setThreadEvent("write to client,bytes length=" + bytelength);
	}

	public void endWriteToClient(long bytelength) {
		totalWriteToClientCost += (System.currentTimeMillis() - beginWriteClientTime);
		totalbyteswriteToClient += bytelength;
		setThreadEvent("end write to client,bytes length=" + bytelength);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("thread=").append(getThreadName());
		sb.append("\tstatus=").append(getStats());
		// initCostTime();
		sb.append("\telapsetime=").append(getCostTime());
		sb.append("\toperator=").append(getRemoteUser());
		sb.append("\rremoteAddr=").append(getRemoteAddr());
		sb.append("\rremoteCallMethod=").append(getRemoteCallMethod());
		sb.append("\n").append(getStatistics());
		sb.append("\n#threadstack\n").append(getStackTrace());
		sb.append("\n#sqlstatck:\n");
		sb.append(getSqlHistory().toString());
		return sb.toString();
	}

	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append("thread=").append(getThreadName());
		sb.append(";\tbegintime=").append(formatDate(getBegintime()));
		sb.append(";\tcosttime=").append(getCostTime());
		sb.append(";\tuserid=").append(getRemoteUser());
		sb.append(";\tremoteAddr=").append(getRemoteAddr());
		sb.append(";\tremoteCallMethod=").append(getRemoteCallMethod());
		sb.append(";\t").append(getStatistics());
		return sb.toString();
	}

	public String getStatistics() {
		StringBuffer sb = new StringBuffer();
		sb.append("sqlcosttime=").append(
				getSqlHistory().getSnapShottotalCostTime());
		sb.append(";readresulttime=").append(totalCostOfReadRs);
		sb.append(";readrownum=").append(totalRowNumOfReadRs);
		sb.append(";readfromclienttime=").append(totalReadClientTime);
		sb.append(";writetoclienttime=").append(totalWriteToClientCost);
		sb.append(";writetoclientbytes=").append(totalbyteswriteToClient);
		sb.append(";readfromclientbytes=").append(totalbytereadfromclient);
		sb.append(";notclosedconnectioncount=").append(
				getCountOpenedConnection());
		return sb.toString();
	}

	private String formatDate(long date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(date));
	}

	public void setThreadId(long id) {
		this.threadID = id;
	}

	public long getThreadID() {
		return threadID;
	}

	public StackTraceElement[] getStackTrace0() {
		return stackTrace0;
	}

	public void setStackTrace0(StackTraceElement[] stackTrace0) {
		this.stackTrace0 = stackTrace0;
	}

	public String getCheckMsg(long maxCosttime, long maxCountSql,
			long maxCostSql) {
		if (isService())
			return null;
		initCostTime();
		// �߳�ʱ����ڶ�����
		if (costTime > maxCosttime) {
			checkMsg = " costtime over " + maxCosttime + "ms!";
		}
		// SQLִ����������1000��
		if (getSqlHistory().getCountOfSql() > maxCountSql) {
			checkMsg += "total number of sql over " + maxCountSql + "!";
		}
		// ���ʱ��sql����һ����.
		if (getSqlHistory().getMaxCost() > maxCostSql) {
			checkMsg += " a sql cost time over " + maxCostSql
					+ "ms;mostcostsql=" + getSqlHistory().getMostCostSql()
					+ "!";
		}
		if (checkMsg != null && checkMsg.length() > 0)
			checkMsg = "[" + threadName + "]" + checkMsg;
		return checkMsg;
	}

	public boolean isService() {
		return isService;
	}

	public void setService(boolean b) {
		isService = b;
	}

	public boolean isExistConn(int connectionId) {
		return getSqlHistory().isExistConn(connectionId);
	}
}
