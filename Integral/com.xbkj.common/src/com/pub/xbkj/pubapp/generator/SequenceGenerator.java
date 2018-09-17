package com.pub.xbkj.pubapp.generator;




import com.xbkj.common.bp.impl.uap.oid.OidBaseAlgorithm;
import com.xbkj.common.bs.uap.oid.OidGenerator;
import com.xbkj.common.jdbc.framework.MockDataSource;
import com.xbkj.common.jdbc.framework.generator.IdGenerator;
import com.xbkj.common.util.PrimaryKeyUtil;
import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;

/**
 * 主键生成工具

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
	 * 根据当前公司生成主键
	 */
	public String generate() {
		if (MockDataSource.isMockDataBase()) {
			OID_BASE_INITIAL_VAL++;
			return OidBaseAlgorithm.getInstance(
					String.valueOf(OID_BASE_INITIAL_VAL)).nextOidBase();

		}

		String groupNumber = OidGenerator.GROUP_PK_CORP;;
		

		String ds = dataSource;
		if (ds == null) {
			ds = InvocationInfoProxy.getInstance().getUserDataSource();
		}

		return OidGenerator.getInstance().nextOid(ds, groupNumber);

	}

	/**
	 * 根据传入的公司生成主键
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
	 * 根据传入公司和数量生成主键数组
	 */
	public String[] generate(String pk_corp, int amount) {
		if (pk_corp == null)
			throw new IllegalArgumentException(
					"Can't generate primary key with null pk_corp");
		String[] newOids = new String[amount];
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
	 * 根据当前公司和数量生成主键数组
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

		String groupNumber = OidGenerator.GROUP_PK_CORP;;
		
		String ds = dataSource;
		if (ds == null) {
			ds = InvocationInfoProxy.getInstance().getUserDataSource();
		}

		for (int i = 0; i < amount; i++) {
			newOids[i] = OidGenerator.getInstance().nextOid(ds, groupNumber);
		}

		return newOids;
	}

	/**
	 * 根据当前公司和数量生成主键数组
	 */
	public String[] generate2(int amount) {
		String[] newOids = new String[amount];
		
		for (int i = 0; i < amount; i++) {
			newOids[i] = PrimaryKeyUtil.getPrimaryKey();
		}

		return newOids;
	}

	/**
	 * 根据当前公司和数量生成主键数组
	 */
	public String generate2() {
		
			return PrimaryKeyUtil.getPrimaryKey();
		
	}
}
