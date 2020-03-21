package com.xbkj.gd.integral.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-3-20
 *@version 1.0.0
 *@desc
 */
public class OtherIntegralVO extends GdSuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pk_other_integral_detail;
	private String customer_idcard;
	private String customer_name;
	private double other_integral;//积分
	private String integral_type;//积分类型 1：线上转线下
	
	
	
	public String getPk_other_integral_detail() {
		return pk_other_integral_detail;
	}
	public void setPk_other_integral_detail(String pk_other_integral_detail) {
		this.pk_other_integral_detail = pk_other_integral_detail;
	}
	public String getCustomer_idcard() {
		return customer_idcard;
	}
	public void setCustomer_idcard(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public double getOther_integral() {
		return other_integral;
	}
	public void setOther_integral(double other_integral) {
		this.other_integral = other_integral;
	}
	
	public String getIntegral_type() {
		return integral_type;
	}
	public void setIntegral_type(String integral_type) {
		this.integral_type = integral_type;
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getPKFieldName() {
		return "pk_other_integral_detail";
	}
	@Override
	public String getTableName() {
		return "gd_other_integral_detail";
	}
}
