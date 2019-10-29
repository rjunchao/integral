package com.xbkj.gd.integral.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2017-9-6
 *@version 1.0.0
 *@desc
 */
public class CustIntModifDetailVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4887779606572926286L;
	private String pk_cust_inte_detail;
	private String update_time;//修改时间',
	private String update_user;//'修改人',
	private String update_type ;// '修改类型，0:添加积分，1：减少积分',
	private String ts ;//
	private String create_user;//
	private String create_user_org ;//
	private String def1 ;//
	private String def2 ;//
	private String update_detail ;// '根据修改类型填写对应的类型'
	private String customer_idcard;//身份证
	private double conversion_integral;//兑换积分
	
	public CustIntModifDetailVO() {
		super();
	}

	public String getPk_cust_inte_detail() {
		return pk_cust_inte_detail;
	}
	public void setCustomer_idcard(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}
	
	public String getCustomer_idcard() {
		return customer_idcard;
	}

	public void setPk_cust_inte_detail(String pk_cust_inte_detail) {
		this.pk_cust_inte_detail = pk_cust_inte_detail;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_type() {
		return update_type;
	}

	public void setUpdate_type(String update_type) {
		this.update_type = update_type;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_user_org() {
		return create_user_org;
	}

	public void setCreate_user_org(String create_user_org) {
		this.create_user_org = create_user_org;
	}

	public String getDef1() {
		return def1;
	}

	public void setDef1(String def1) {
		this.def1 = def1;
	}

	public String getDef2() {
		return def2;
	}

	public void setDef2(String def2) {
		this.def2 = def2;
	}

	public String getUpdate_detail() {
		return update_detail;
	}

	public void setUpdate_detail(String update_detail) {
		this.update_detail = update_detail;
	}

	public String getCstomer_idcard() {
		return customer_idcard;
	}

	public void setPk_customer_info(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}

	public double getConversion_integral() {
		return conversion_integral;
	}
	
	public void setConversion_integral(double conversion_integral) {
		this.conversion_integral = conversion_integral;
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_cust_inte_detail";
	}

	@Override
	public String getTableName() {
		return "gd_cust_inte_detail";
	}

	@Override
	public String toString() {
		return "CustIntModifDetailVO [pk_cust_inte_detail="
				+ pk_cust_inte_detail + ", update_time=" + update_time
				+ ", update_user=" + update_user + ", update_type="
				+ update_type + ", ts=" + ts + ", create_user=" + create_user
				+ ", create_user_org=" + create_user_org + ", def1=" + def1
				+ ", def2=" + def2 + ", update_detail=" + update_detail
				+ ", customer_idcard=" + customer_idcard + "]";
	}
	
}
