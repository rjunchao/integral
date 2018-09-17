package com.pub.xbkj.pubapp.insert;

import com.xbkj.basic.vo.pub.SuperVO;

/**
 *@author ljb
 *@date 2016-7-31
 *@version 1.0.0
 */
public class TableColPropertyVO  extends SuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4861747462856919337L;

	/*
	 * 表名
	 */
	private String table_name;
	/*
	 * 列名
	 */
	private String column_name;
	
	/*
	 * 数据类型
	 */
	private String data_type;
	
	/*
	 * 数据长度
	 */
	private int data_length;
	
	/*
	 * 精度
	 */
	private int data_precision;
	/*
	 * 精度范围，为0时表示number是整型
	 */
	private int data_scale;
	@Override
	public String getParentPKFieldName() {
		
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getTableName() {
		
		return "user_tab_columns";
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public int getData_length() {
		return data_length;
	}

	public void setData_length(int data_length) {
		this.data_length = data_length;
	}

	public int getData_precision() {
		return data_precision;
	}

	public void setData_precision(int data_precision) {
		this.data_precision = data_precision;
	}

	public int getData_scale() {
		return data_scale;
	}

	public void setData_scale(int data_scale) {
		this.data_scale = data_scale;
	}

	
	
}
