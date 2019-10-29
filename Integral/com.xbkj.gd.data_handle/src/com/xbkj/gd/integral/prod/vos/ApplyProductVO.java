package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc
 */
public class ApplyProductVO  extends GdSuperVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pk_apply_product;//
	private String pk_audit_product;//审批表主键
	private String apply_product_code;//申请商品编码
	private String apply_product_integral;//申请商品积分单位
	private String apply_product_name;//申请商品名称
	private int apply_product_num;//申请商品数量
	private int audit_product_num;//申请商品审批通过数量
	//申请人 状态，1：支行管理员，2：合行管理员，3：合行领导，4：审批通过，5：调拨通过,6:拒绝
	private String audit_status;//状态
	private String audit_user;//审批人
	private String audit_date;//审批时间
	private String apply_org;//
	
	private String data;

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_apply_product";
	}

	@Override
	public String getTableName() {
		return "gd_apply_product";
	}

	public String getPk_apply_product() {
		return pk_apply_product;
	}

	public void setPk_apply_product(String pk_apply_product) {
		this.pk_apply_product = pk_apply_product;
	}

	public String getPk_audit_product() {
		return pk_audit_product;
	}

	public void setPk_audit_product(String pk_audit_product) {
		this.pk_audit_product = pk_audit_product;
	}

	public String getApply_product_code() {
		return apply_product_code;
	}

	public void setApply_product_code(String apply_product_code) {
		this.apply_product_code = apply_product_code;
	}

	public String getApply_product_name() {
		return apply_product_name;
	}

	public void setApply_product_name(String apply_product_name) {
		this.apply_product_name = apply_product_name;
	}

	public int getApply_product_num() {
		return apply_product_num;
	}

	public void setApply_product_num(int apply_product_num) {
		this.apply_product_num = apply_product_num;
	}

	public int getAudit_product_num() {
		return audit_product_num;
	}

	public void setAudit_product_num(int audit_product_num) {
		this.audit_product_num = audit_product_num;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}

	public String getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public String getApply_product_integral() {
		return apply_product_integral;
	}
	
	public void setApply_product_integral(String apply_product_integral) {
		this.apply_product_integral = apply_product_integral;
	}

	@Override
	public String toString() {
		return "ApplyProductVO [pk_apply_product=" + pk_apply_product
				+ ", pk_audit_product=" + pk_audit_product
				+ ", apply_product_code=" + apply_product_code
				+ ", apply_product_integral=" + apply_product_integral
				+ ", apply_product_name=" + apply_product_name
				+ ", apply_product_num=" + apply_product_num
				+ ", audit_product_num=" + audit_product_num
				+ ", audit_status=" + audit_status + ", audit_user="
				+ audit_user + ", audit_date=" + audit_date + "]";
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	public String getApply_org() {
		return apply_org;
	}
	
	public void setApply_org(String apply_org) {
		this.apply_org = apply_org;
	}
}
