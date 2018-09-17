package com.xbkj.common.bs.uap.oid;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.xbkj.common.bp.impl.uap.oid.OidBaseAlgorithm;
import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;

/**
 * OID 生成器，用其生成OID
 *
 */

public class OidGenerator {
	public static final int OID_AMOUNT = 1000;

	public static final String OID_BASE_INITIAL_VAL = "10000000000000";

	public static final String GROUP_PK_CORP = "0001";

	/** 填充字符 预留做其他用 */
	private static String INTERN_FIXER = "AA";

	//private static DataSourceMetaMgr dsMgr = DataSourceMetaMgr.getInstance();

	private Object lock;

	private Map<String, Lock> locks = new HashMap<String, Lock>();

	
	/** 初始化 */
	private OidGenerator() {
		
		lock = new Object();
		
		
	}

	/** 单例 */
	private static OidGenerator instance = null;

	/** 哈希表，公司为键值，存放该公司当前 OidBase 和已取数量 */
	private static Map<String, OidCounter> oidMap = new ConcurrentHashMap<String, OidCounter>();

	static {
		instance = new OidGenerator();
	}

	/** 静态工厂方法 */
	public static OidGenerator getInstance() {
		return instance;
	}

	/**
	 * 取得下一个 OID，默认为集团 OID
	 * 
	 * @return 公司编码（4字节）+“AA”（2字节）+ 序列号（14字节），总长度为20字节
	 */
	public final String nextOid() {
		return nextOid(GROUP_PK_CORP);
	}

	/**
	 * 取得下一批 OID，按照参数所指定的公司和oid的数量
	 * 
	 * @return String 公司编码（4字节）+“AA”（2字节）+ 序列号（14字节），总长度为20字节
	 */
	public final String[] nextOid(String groupNumber, int count) {
		String[] oids = new String[count];
		for (int i = 0; i < count; i++) {
			String oid = nextOid(groupNumber);
			oids[i] = oid;
		}
		return oids;
	}

	public final String[] nextOid(String dataSource, String groupNumber,
			int count) {
		String[] oids = new String[count];
		for (int i = 0; i < count; i++) {
			String oid = nextOid(dataSource, groupNumber);
			oids[i] = oid;
		}
		return oids;
	}

	public String nextOid(String dataSource, String groupNumber) {
		String ds = dataSource;
		String nextOid;
		String oidMark;
		if (dataSource == null)
			ds = getDataSource();

		OidCounter oidCounter = null;
		String oidBase = null;
		// Get infomation from JVM HashMap
		// FIXED BY HEGY, NOT cached by pk_corp, but the ds

		//oidMark = dsMgr.getOIDMark(ds);
		//if (oidMark == null || oidMark.length() != 2)
		 oidMark = INTERN_FIXER;
		// FIXED BY HEGY: THREAD CONCERN AND INDEXED BY DS INSTEAD OF PK_CORP

		Lock l = getLock(ds, groupNumber);

		l.lock();
		try {
			String key = groupNumber + ds;
			oidCounter = oidMap.get(key);
			if (oidCounter == null) {
				oidCounter = new OidCounter();
				oidMap.put(key, oidCounter);
			}
			
				oidBase = oidCounter.oidBase;
			

			// calculate next oidBase
			nextOid = OidBaseAlgorithm.getInstance(oidBase).nextOidBase();
			// modify oid info in JVM
			oidCounter.oidBase = nextOid;
			++oidCounter.amount;
		} finally {
			l.unlock();
		}
		return getWholeOid(groupNumber, oidMark, nextOid);
	}

	/**
	 * 取得下一个公司 OID，参数指定公司 ID
	 * 
	 * @param groupNumber
	 *            String 公司 ID
	 * @return 公司编码（4字节）+“AA”（2字节）+ 序列号（14字节），总长度为20字节
	 */
	public String nextOid(String groupNumber) {
		String ds = getDataSource();
		return nextOid(ds, groupNumber);
	}

	private String getDataSource() {
		return InvocationInfoProxy.getInstance().getUserDataSource();
	}

	/**
	 * 取得当前的编码 FIXED BY HEGY, ADD PARAMETER oidMark binded with datasource,
	 * default is "AA" 整个编码 20 位：前 4 位为单位编码 + "AA" + 后 14 位 oidBase
	 */
	private final String getWholeOid(String groupNumber, String oidMark,
			String oidBase) {
		return (groupNumber + oidMark + oidBase);
	}

	/**
	 * 用来存放当前 OidBase 和已取数量的类
	 */
	private class OidCounter {
		public String oidBase;

		public int amount;

		public OidCounter() {
			this.amount = 0;
			this.oidBase = OID_BASE_INITIAL_VAL;
		}
	}

	private Lock getLock(String ds, String groupNumber) {
		String lockKey = "" + ds + ":" + groupNumber;
		Lock l = null;
		synchronized (lock) {
			l = locks.get(lockKey);
			if (l == null) {
				l = new ReentrantLock();
				locks.put(lockKey, l);
			}
		}

		return l;
	}
}