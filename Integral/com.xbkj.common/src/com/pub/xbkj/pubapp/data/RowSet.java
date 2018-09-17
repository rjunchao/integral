package com.pub.xbkj.pubapp.data;

import java.math.BigDecimal;

import com.vbm.grc.pubapp.AssertUtils;
import com.vbm.grc.pubapp.exception.ExceptionUtils;

/**
 * 断开数据库连接的数据集接口的具体实现类
 * 
 */
public final class RowSet implements IRowSet {

	private static final long serialVersionUID = 1183409025447339141L;

	/**
	 * 二维矩阵数据
	 */
	private Object[][] datas;

	/**
	 * 遍历的游标
	 */
	private int cursor = -1;

	/**
	 * 构造函数
	 * 
	 * @param datas
	 *            二维矩阵数组
	 */
	public RowSet(Object[][] datas) {
		this.datas = datas;
	}

	public int getInt(int index) {
		Object obj = this.getObject(index);
		return ValueUtils.getInt(obj, 0);
	}

	public Boolean getBoolean(int index) {
		Object obj = this.getObject(index);
		return ValueUtils.getBoolean(obj);
	}


	public Object getObject(int index) {
		Object object = null;
		Object[] row = this.datas[this.cursor];
		if (row.length <= index) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("out of the rowset's row size ");
			buffer.append(" the row size is:");
			buffer.append(row.length);
			buffer.append(" the index is:");
			buffer.append(index);
			ExceptionUtils.wrappBusinessException(buffer.toString());
		}
		object = row[index];
		return object;
	}

	public String getString(int index) {
		String retValue = null;
		Object obj = this.getObject(index);
		retValue = ValueUtils.getString(obj);
		return retValue;
	}

	public Double getDouble(int index) {
		Object obj = this.getObject(index);
		Double returnValue = ValueUtils.getDouble(obj);
		return returnValue;
	}

	public Integer getInteger(int index) {
		Object obj = this.getObject(index);
		Integer returnValue = ValueUtils.getInteger(obj);
		return returnValue;
	}

	public BigDecimal getBigDecimal(int index) {
		Object obj = this.getObject(index);
		BigDecimal returnValue = ValueUtils.getBigDecimal(obj);
		return returnValue;
	}

	public boolean next() {
		boolean returnValue = false;

		int size = this.datas.length;

		if ((this.cursor < (size - 1)) && (size > 0)) {
			returnValue = true;
		}
		this.cursor++;

		return returnValue;
	}

	public void reset() {
		this.cursor = -1;
	}

	public int size() {
		return this.datas.length;
	}

	public String[][] toTwoDimensionStringArray() {
		int size = this.datas.length;
		if (size == 0) {
			return new String[0][0];
		}
		Object[] row = this.datas[0];
		int length = row.length;

		String[][] ret = new String[size][length];
		for (int i = 0; i < size; i++) {
			row = this.datas[i];
			for (int j = 0; j < length; j++) {
				ret[i][j] = row[j] == null ? null : row[j].toString();
			}
		}
		return ret;
	}

	public String[] toOneDimensionStringArray() {
		int size = this.datas.length;
		if (size == 0) {
			return new String[0];
		}
		Object[] row = this.datas[0];
		int length = row.length;
		AssertUtils.assertValue(length == 1, "length == 1");

		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			row = this.datas[i];
			ret[i] = row[0] == null ? null : row[0].toString();
		}
		return ret;
	}

}
