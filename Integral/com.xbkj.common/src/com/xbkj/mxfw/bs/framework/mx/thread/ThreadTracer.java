package com.xbkj.mxfw.bs.framework.mx.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.xbkj.log.bs.logging.Log;

public class ThreadTracer {
	private ConcurrentHashMap<Thread, ThreadEntry> hThreadEntry = new ConcurrentHashMap<Thread, ThreadEntry>();

	private static ThreadTracer m_inst = null;

	// debug ȫ��ģʽ,���Ϊfalse����Ϊ�û�ģʽ��
	private boolean globalDebug = true;

	// debug ���Ϊ�û�ģʽ���û�ID�б?
	private ArrayList<String> traceUsers = new ArrayList<String>();

	// �Ƿ���ٷ���.
	private boolean isTraceService = false;

	private String[] excludeLogKeyWord = new String[] { "monitor"};

	private Log tlogger = Log.getInstance(ThreadTracer.class);

	private Log sqllogger = Log.getInstance("sql");

	public static synchronized ThreadTracer getInstance() {
		if (m_inst == null) {
			m_inst = new ThreadTracer();
		}
		return m_inst;
	}

	/*
	 * ��ʼһ���Ự
	 */
	public void startThreadMonitor(String remoteCallMethod, String remoteAddr, String remoteUser) {
		Thread t = Thread.currentThread();
		ThreadEntry entry = hThreadEntry.get(t);
		if (entry == null) {
			entry = new ThreadEntry();
			entry.setBegintime(System.currentTimeMillis());
			entry.setService(remoteAddr == null);
			entry.setThreadId(t.getId());
			String tName = t.getName();
			entry.setThreadName(tName == null ? "service" : tName);
			hThreadEntry.put(t, entry);
		}
		entry.setDebug(isUserDebug(t));
		entry.setRemoteCallMethod(remoteCallMethod);
		entry.setRemoteAddr(remoteAddr == null ? "N/A" : remoteAddr);
		entry.setRemoteUser(remoteUser == null ? "N/A" : remoteUser);
		entry.setThreadEvent("exec : " + remoteCallMethod);
	}

	/*
	 * ����һ���Ự
	 */
	public void endThreadMonitor() {
		Thread t = Thread.currentThread();
		ThreadEntry entry = hThreadEntry.get(t);
		hThreadEntry.remove(t);
		if (entry == null)
			return;
		if (!isLog(entry))
			return;
		entry.initCostTime();
		// ��ժҪ��Ϣд����־��
		tlogger.debug(entry.getSummary());
	}

	private boolean isLog(ThreadEntry entry) {
		if (entry == null)
			return false;
		if (entry.isService()) {
			return false;
		}
		String remoteMehtod = entry.getRemoteCallMethod();
		if (remoteMehtod == null) {
			return false;
		}
		for (int i = 0; i < excludeLogKeyWord.length; i++) {
			if (remoteMehtod.indexOf(excludeLogKeyWord[i]) > -1) {
				return false;
			}
		}
		return true;
	}

	/*
	 * ��һ����ݿ�����.
	 */
	public void openConnection(String dsName, int connid) {
		if (dsName == null || dsName.length() == 0 || connid < 0)
			return;
		ThreadEntry threadentry = hThreadEntry.get(Thread.currentThread());
		if (threadentry != null) {
			threadentry.openConnection(String.valueOf(connid));
		}
	}

	/*
	 * �ر�һ����ݿ�����
	 */
	public void closeConnection(String dsName, int connid) {
		if (dsName == null || dsName.length() == 0 || connid < 0)
			return;
		ThreadEntry threadentry = hThreadEntry.get(Thread.currentThread());
		if (threadentry != null) {
			threadentry.closeConnection(String.valueOf(connid));
		}
	}

	/*
	 * ����״̬
	 */
	public void updateEvent(String event) {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.setThreadEvent(event);
	}

	/*
	 * ����һ����ѯ
	 */
	public void addNewSql(String sqlText, int connectionId, String datasource) {
		updateEvent("exec sql");
		// ����߳���service,�򲻿��ǡ�
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.getSqlHistory().addNewSql(sqlText, connectionId, datasource);
	}

	/*
	 * ����һ����ѯ
	 */
	public void endSql() {
		// ����߳���service,�򲻿��ǡ�
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		updateEvent("end exec sql");
		entry.getSqlHistory().endSql();
	}

	/*
	 * �������е�Thread��Ϣ��
	 */
	public ThreadEntry[] getAllThreadInfo() {
		hThreadEntry.remove(Thread.currentThread());
		preWorkWhenRemoteCall();
		Collection<ThreadEntry> collection = hThreadEntry.values();
		if (collection.size() == 0) {
			return null;
		}
		return (ThreadEntry[]) collection.toArray(new ThreadEntry[collection.size()]);
	}

	/*
	 * ����߳�״̬��Ϣ���̣߳���ǰ��ѯ��ִ��ʱ�䡣
	 */
	private void preWorkWhenRemoteCall() {
		Enumeration<Thread> threads = hThreadEntry.keys();
		Thread t = null;
		while (threads.hasMoreElements()) {
			t = threads.nextElement();
			ThreadEntry threadEntry = hThreadEntry.get(t);
			if (threadEntry == null)
				continue;
			threadEntry.initCostTime();
			threadEntry.setStatus(t.getState().name());
			threadEntry.setDebug(isUserDebug(t));
			threadEntry.setStackTrace0(t.getStackTrace());
		}
	}

	public void setThrowable(Throwable throwable) {
		// ����߳���service,�򲻿��ǡ�
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.setThrowable(throwable);
	}

	/*
	 * ��ʼ�����
	 */
	public void beginReadRs() {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.beginReadRs();
	}

	/*
	 * ��������
	 */
	public void endReadRs(int rowcount) {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.endReadRs(rowcount);
	}

	/*
	 * ��ʼ�����д��ͻ���
	 */
	public void beginWriteToClient(int bytelength) {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.beginWriteToClient(bytelength);
	}

	/*
	 * �������д��ͻ���
	 */
	public void endWriteToClient(int bytelength) {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.endWriteToClient(bytelength);
	}

	/*
	 * ����Ϊȫ��ģʽ
	 */
	public void setGlobalDebug(boolean global) {
		globalDebug = global;
		if (global) {
			traceUsers.clear();
		}
	}

	/*
	 * ���õ���ģʽ�µ��û�
	 */
	public void addDebugUsr(String userid) {
		if (userid == null)
			return;
		globalDebug = false;
		traceUsers.add(userid);
	}

	/*
	 * ������ģʽ�µ��û�
	 */
	public void removeDebugUsr() {
		if (traceUsers == null || traceUsers.size() == 0)
			return;
		traceUsers.clear();
	}

	/*
	 * �Ƿ�ΪDebugģʽ
	 */
	public boolean isUserDebug(Thread t) {
		ThreadEntry entry = hThreadEntry.get(t);
		if (entry == null)
			return false;
		if (entry.isService() && !isTraceService)
			return false;
		if (globalDebug)
			return true;
		String userid = entry.getRemoteUser();
		if (userid == null)
			return false;
		return traceUsers.contains(userid);
	}

	public boolean isUserDebug() {
		return isUserDebug(Thread.currentThread());
	}

	public void setTraceService(boolean b) {
		isTraceService = b;
	}

	public String getThreadPoolUsed() {
		Collection<ThreadEntry> collection = hThreadEntry.values();
		if (collection.size() == 0) {
			return "0\t\t0";
		}
		Iterator<ThreadEntry> itr = collection.iterator();
		int webcontainerThreadNum = 0;
		int backgroundServiceNum = 0;
		while (itr.hasNext()) {
			if (itr.next().isService()) {
				backgroundServiceNum++;
			} else {
				webcontainerThreadNum++;
			}
		}
		return webcontainerThreadNum + "\t\t" + backgroundServiceNum;
	}

	public boolean isExistConn(int connid) {
		if (connid < 0)
			return false;
		ThreadEntry threadentry = hThreadEntry.get(Thread.currentThread());
		if (threadentry == null)
			return false;
		return threadentry.isExistConn(connid);
	}

	/*
	 * �̼߳��
	 */
	public String check(long maxCosttime, long maxCountSql, long maxCostSql) {
		Collection<ThreadEntry> collection = hThreadEntry.values();
		if (collection.size() == 0) {
			return null;
		}
		ThreadEntry[] entrys = (ThreadEntry[]) collection.toArray(new ThreadEntry[collection.size()]);
		if (entrys == null || entrys.length == 0)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < entrys.length; i++) {
			String checkMsg = entrys[i].getCheckMsg(maxCosttime, maxCountSql, maxCostSql);
			if (checkMsg != null && checkMsg.length() > 0) {
				sb.append(checkMsg).append("\n");
			}
		}
		return sb.toString();
	}

	public void logSql(String msg) {
		sqllogger.debug(msg);
	}

	public void beginReadFromClient() {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.beginReadFromclient();
	}

	public void endReadFromClient(int bytes) {
		ThreadEntry entry = hThreadEntry.get(Thread.currentThread());
		if (entry == null)
			return;
		entry.endReadFromClient(bytes);
	}
}
