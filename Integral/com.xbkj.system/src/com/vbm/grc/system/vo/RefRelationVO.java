package com.vbm.grc.system.vo;

import com.xbkj.basic.vo.pub.XbkjSuperVO;

/**
 *@author heFei
 *@date 
 *@version 1.0.0
 */
public class RefRelationVO extends XbkjSuperVO{
    
	private static final long serialVersionUID=1l;
	
	private String pk_ref;//主键
	private String refed_table_name;//被引用表名
	private String refed_table_key;//被引用表字段
	private String refing_table_name;//引用表名
	private String refing_table_colum;//引用字段名
	private int dr;	//删除标志位
	private String ts;	//时间戳
	
	public static final String PK_REF = "pk_ref";
	public static final String REFED_TABLE_NAME = "refed_table_name";
	public static final String REFED_TABLE_KEY = "refed_table_key";
	public static final String REFING_TABLE_NAME = "refing_table_name";
	public static final String REFING_TABLE_COLUM = "refing_table_colum";
	public static final String DR = "dr";
	public static final String TS = "ts";
	
	
	public String getPk_ref() {
		return pk_ref;
	}

	public void setPk_ref(String pk_ref) {
		this.pk_ref = pk_ref;
	}

	public String getRefed_table_name() {
		return refed_table_name;
	}

	public void setRefed_table_name(String refed_table_name) {
		this.refed_table_name = refed_table_name;
	}

	public String getRefed_table_key() {
		return refed_table_key;
	}

	public void setRefed_table_key(String refed_table_key) {
		this.refed_table_key = refed_table_key;
	}

	public String getRefing_table_name() {
		return refing_table_name;
	}

	public void setRefing_table_name(String refing_table_name) {
		this.refing_table_name = refing_table_name;
	}

	public String getRefing_table_colum() {
		return refing_table_colum;
	}

	public void setRefing_table_colum(String refing_table_colum) {
		this.refing_table_colum = refing_table_colum;
	}

	public int getDr() {
		return dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	@Override
	public String getParentPKFieldName() {
		return null;
	}
    /**
     * 获取主键字段
     */
	@Override
	public String getPKFieldName() {
		
		return "pk_ref";
	}
    /**
     * 获取数据库表名
     */
	@Override
	public String getTableName() {
		
		return "grc_ref_relation";
	}

}
