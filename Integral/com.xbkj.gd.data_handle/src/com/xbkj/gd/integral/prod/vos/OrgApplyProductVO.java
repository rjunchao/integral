package com.xbkj.gd.integral.prod.vos;
/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-23
 *@version 1.0.0
 *@desc 
 */
public class OrgApplyProductVO extends ApplyProductVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String pk_org_apply_product;
	private String pk_org_audit_product;
	private String apply_user;
	private String refuse_type;//拒绝类型
	public String getApply_user() {
		return apply_user;
	}
	
	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}
	
	public String getPk_org_audit_product() {
		return pk_org_audit_product;
	}
	
	public void setPk_org_audit_product(String pk_org_audit_product) {
		this.pk_org_audit_product = pk_org_audit_product;
	}
	
	public void setPk_org_apply_product(String pk_org_apply_product) {
		this.pk_org_apply_product = pk_org_apply_product;
	}
	
	public String getPk_org_apply_product() {
		return pk_org_apply_product;
	}
	
	public String getRefuse_type() {
		return refuse_type;
	}
	
	public void setRefuse_type(String refuse_type) {
		this.refuse_type = refuse_type;
	}
	
	@Override
	public String getTableName() {
		return "gd_org_apply_product";
	}
	
	@Override
	public String getPKFieldName() {
		return "pk_org_apply_product";
	}

	@Override
	public String toString() {
		return "OrgApplyProductVO [pk_org_apply_product="
				+ pk_org_apply_product + ", pk_org_audit_product="
				+ pk_org_audit_product + ", apply_user=" + apply_user + "]";
	}
	
	

}
