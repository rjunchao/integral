package com.xbkj.common.jdbc.framework.generator;

import com.xbkj.common.bp.impl.uap.oid.OidBaseAlgorithm;
import com.xbkj.common.bs.uap.oid.OidGenerator;
import com.xbkj.common.jdbc.framework.MockDataSource;

/**
 * ID生成器
 */
public class SequenceGenerator implements IdGenerator {

	public static long OID_BASE_INITIAL_VAL = 19000000000000l;

	String dataSource;

	public SequenceGenerator() {

	}

	public SequenceGenerator(String dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * ��ݵ�ǰ��˾�������
	 */
	public String generate() {
		if (MockDataSource.isMockDataBase()) {
			OID_BASE_INITIAL_VAL++;
			return OidBaseAlgorithm.getInstance(
					String.valueOf(OID_BASE_INITIAL_VAL)).nextOidBase();

		}
		if (dataSource == null)
			return OidGenerator.getInstance().nextOid();
		else
			return OidGenerator.getInstance().nextOid(dataSource);

	}

	/**
	 * ��ݴ���Ĺ�˾�������
	 */
	public String generate(String pk_corp) {
		if (pk_corp == null)
			throw new IllegalArgumentException(
					"Can't generate primary key with null pk_corp");
		if (MockDataSource.isMockDataBase()) {
			OID_BASE_INITIAL_VAL++;
			return OidBaseAlgorithm.getInstance(
					String.valueOf(OID_BASE_INITIAL_VAL)).nextOidBase();
		}
		if (dataSource == null)
			return OidGenerator.getInstance().nextOid(pk_corp);
		else
			return OidGenerator.getInstance().nextOid(dataSource, pk_corp);

	}

	/**
	 * ��ݴ��빫˾�����������������
	 */
	public String[] generate(String pk_corp, int amount) {
		if (pk_corp == null)
			throw new IllegalArgumentException(
					"Can't generate primary key with null pk_corp");
		String[] newOids = new String[amount];
		
		for (int i = 0; i < amount; i++) {
			newOids[i] = "11111111111111111111";
		}
		
		
		if (MockDataSource.isMockDataBase()) {
			for (int i = 0; i < amount; i++) {
				OID_BASE_INITIAL_VAL++;
				newOids[i] = OidBaseAlgorithm.getInstance(
						String.valueOf(OID_BASE_INITIAL_VAL)).nextOidBase();
			}
			return newOids;
		}
		OidGenerator oidGen = OidGenerator.getInstance();
		if (dataSource == null) {
			for (int i = 0; i < amount; i++) {
				newOids[i] = oidGen.nextOid(pk_corp);
			}
		} else {
			for (int i = 0; i < amount; i++) {
				newOids[i] = oidGen.nextOid(dataSource, pk_corp);
			}
		}
		return newOids;

	}

	/**
	 * ��ݵ�ǰ��˾�����������������
	 */
	public String[] generate(int amount) {
		String[] newOids = new String[amount];
		if (MockDataSource.isMockDataBase()) {
			for (int i = 0; i < amount; i++) {
				OID_BASE_INITIAL_VAL++;
				newOids[i] = OidBaseAlgorithm.getInstance(
						String.valueOf(OID_BASE_INITIAL_VAL)).nextOidBase();
			}
			return newOids;
		}
		if (dataSource == null) {
			for (int i = 0; i < amount; i++) {
				newOids[i] = OidGenerator.getInstance().nextOid();

			}
		} else {
			for (int i = 0; i < amount; i++) {
				newOids[i] = OidGenerator.getInstance().nextOid(dataSource);

			}
		}

		return newOids;
	}

	public static void main(String[] args) {
		SequenceGenerator gen = new SequenceGenerator();
		gen.generate();
	}
}
