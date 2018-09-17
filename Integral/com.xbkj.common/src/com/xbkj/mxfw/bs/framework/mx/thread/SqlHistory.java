package com.xbkj.mxfw.bs.framework.mx.thread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import com.grc.mxfw.bs.framework.mx.thread.SqlEntry;

public class SqlHistory implements Serializable {

	private static final long serialVersionUID = 00002L;

	// list of SqlEntry.
	private LinkedList<SqlEntry> al = new LinkedList<SqlEntry>();

	// ��ǰ���ڹ�����sql.
	private SqlEntry workingSqlEntry = null;

	// �������ͳ�ƣ�����������Ự���˶������ӣ��ж�������
	private ArrayList<Integer> connectionUsed = new ArrayList<Integer>();

	// ִ�в�ѯ�ĸ���
	private int countOfSql = 0;

	// �ӱ��λỰ��ʼ����ѯִ�е���ʱ�䣬�����Ǳ��λỰ����ݿ��ϻ��ѵ�ʱ�䡣
	private long totalCostTimeOfSql = 0l;

	// ��ǰ����ʱ��
	private long snapshotCosttimeOfSql = 0l;

	// SqlEntry�б�Ĵ�С��
	private static int SIZEOFSQLBUFF = 50;

	// ���ʱ��SQLִ��ʱ��.
	private long mostCost = 0l;

	private String mostCostSql = null;

	/*
	 * ��ʱ�������С��sql���б������
	 */
	private void removeTheFastest() {
		al.remove(0);
	}

	/*
	 * �����µ�sql
	 */
	public void addNewSql(String sqlText, int connectionId, String datasource) {
		countOfSql++;
		if (!connectionUsed.contains(connectionId)) {
			connectionUsed.add(connectionId);
		}
		workingSqlEntry = new SqlEntry();
		workingSqlEntry.setBegintime(System.currentTimeMillis());
		workingSqlEntry.setConnectioinId(connectionId);
		workingSqlEntry.setSqltext(sqlText);
		workingSqlEntry.setDataSource(datasource == null ? "default"
				: datasource);
		if (al.size() == SIZEOFSQLBUFF) {
			removeTheFastest();
		}
		al.add(workingSqlEntry);
	}

	/*
	 * sqlִ����ϡ�
	 */
	public void endSql() {
		if (workingSqlEntry == null)
			return;
		workingSqlEntry.setEndTime(System.currentTimeMillis());
		if (workingSqlEntry.getCostTime() > mostCost) {
			mostCost = workingSqlEntry.getCostTime();
			mostCostSql = workingSqlEntry.getSqltext();
		}
		totalCostTimeOfSql += (workingSqlEntry.getCostTime());
		workingSqlEntry = null;
	}

	/*
	 */
	public void initCostTime() {
		if (workingSqlEntry != null)
			workingSqlEntry.initCosttime();
		snapshotCosttimeOfSql = totalCostTimeOfSql
				+ (workingSqlEntry == null ? 0l : workingSqlEntry.getCostTime());
	}

	public long getSnapShottotalCostTime() {
		return snapshotCosttimeOfSql;
	}

	public LinkedList<SqlEntry> getSqlEntrys() {
		return al;
	}

	public int getCountOfSql() {
		return countOfSql;
	}

	public int getCountOfConn() {
		return connectionUsed.size();
	}

	public boolean isExistConn(int connectionId) {
		return connectionUsed.contains(connectionId);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("total cost time=").append(snapshotCosttimeOfSql);
		sb.append("\ttotal number of Sql=").append(getCountOfSql());
		sb.append("\ttotal connection used=").append(getCountOfConn());
		SqlEntry entry = null;
		for (int i = 0; i < al.size(); i++) {
			entry = al.get(i);
			sb.append("\n#The Sql " + i + ":\n" + entry.getSqltext());
			sb.append("\ncosttime=").append(entry.getCostTime());
			sb.append("\nreadnum=").append(entry.getRownum());
			sb.append("\nstatus=").append(
					entry.getEndTime() == 0l ? "running" : "finished");
		}
		return sb.toString();
	}

	public long getMaxCost() {
		return mostCost;
	}

	public String getMostCostSql() {
		return mostCostSql;
	}
}
