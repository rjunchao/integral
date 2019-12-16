package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-11-9
 *@version 1.0.0
 *@desc
 */
public class ReportProductVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821876723375432823L;
	private int org_sub_num;//兑换数量和
	private int apply_product_num;//申请商品数量和
	private int allot_product_num;//调换数总和
	private int residue_class;//剩余数
	private String apply_product_name;//申请商品名称
	private String apply_user;//申请人
	
	
	public int getOrg_sub_num() {
		return org_sub_num;
	}

	public void setOrg_sub_num(int org_sub_num) {
		this.org_sub_num = org_sub_num;
	}

	public int getApply_product_num() {
		return apply_product_num;
	}

	public void setApply_product_num(int apply_product_num) {
		this.apply_product_num = apply_product_num;
	}

	public String getApply_product_name() {
		return apply_product_name;
	}

	public void setApply_product_name(String apply_product_name) {
		this.apply_product_name = apply_product_name;
	}

	public String getApply_user() {
		return apply_user;
	}

	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}

	public int getResidue_class() {
		return residue_class;
	}
	public void setResidue_class(int residue_class) {
		this.residue_class = residue_class;
	}
	
	public int getAllot_product_num() {
		return allot_product_num;
	}
	
	public void setAllot_product_num(int allot_product_num) {
		this.allot_product_num = allot_product_num;
	}
	
	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
