package com.xbkj.common.bp.impl.uap.oid;

/**
 * OID生成算法
 */

/**
 * OidBase 算法 本算法采取 42 进位的方法 在 48 － 90 为连续可见字符 表示的最大数为 42^14个 相当于 10^22 位
 * 
 * 修改：赵继江（2001-09-21）：在 SQL Server中，主键字段不区分 大小写，因此将MAX_CODE由126改为96.
 * 修改：赵继江（2001-11-08）：使用的字符只用数字和大写字母.
 * 
 * @author cc 2005-03-10 NC v5 移植，算法与公司编码脱离。 去掉 Tablename hashCode，直接改为
 *         AA，放到OidGenerator.java中
 * 
 */

public class OidBaseAlgorithm {
	/** 最小编码 33 */
	private final static byte MIN_CODE = 48;

	/** 最大编码 90 */
	private final static byte MAX_CODE = 90;

	/** OidBase 码长度 */
	private final int CODE_LENGTH = 14;

	/** 14 位 OidBase 码 */
	private byte[] oidBaseCodes = new byte[CODE_LENGTH];

	/**
	 * 构造函数私有化，确保算法类的不可实例化
	 */
	private OidBaseAlgorithm() {
	}

	/**
	 * 设置 oidBaseCodes
	 * 
	 * @return
	 */
	private void setOidBaseCodes(byte[] oidBaseCodes) {
		if (oidBaseCodes.length != CODE_LENGTH)
			return;
		System.arraycopy(oidBaseCodes, 0, this.oidBaseCodes, 0, CODE_LENGTH);
	}

	/**
	 * 获得算法实例的工厂方法
	 * 
	 * @return
	 */
synchronized public static OidBaseAlgorithm getInstance() {
		OidBaseAlgorithm oidBase = new OidBaseAlgorithm();
		return oidBase;
	}

	/**
	 * 获得算法实例的工厂方法
	 * 
	 * @return
	 */
	public static OidBaseAlgorithm getInstance(String strOidBase) {
		// Here, lack of safty...
		OidBaseAlgorithm oidBase = new OidBaseAlgorithm();
		oidBase.setOidBaseCodes(strOidBase.getBytes());

		return oidBase;
	}

	/**
	 * 取得下一个 oidBase 编码
	 * 
	 * @return String 下一个 oidBase 编码
	 */
	public String nextOidBase() {
		for (int i = oidBaseCodes.length - 1; i >= 0; --i) {
			byte code = (byte) (oidBaseCodes[i] + 1);
			boolean carryUp = false;
			byte newCode = code;
			if (code > MAX_CODE) {
				newCode = MIN_CODE;
				carryUp = true;
			}
			// 跳过数字与大写字母之间的其他字符：
			if (newCode == 58) {
				newCode = 65;
			}
				oidBaseCodes[i] = newCode;
			
			if (!carryUp)
				break;
		}

		return new String(oidBaseCodes);
	}

	

}