package com.xbkj.gd.integral.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2020-2-25
 *@version 1.0.0
 *@desc
 */
public class ActivitiesPresentedVO extends GdSuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3163422629494350542L;

	private String pk_ap_integral_detail;
	private String customer_idcard;
	private String customer_name;
	private double ap_integral;
	private String remark;
	
	
	
	public String getPk_ap_integral_detail() {
		return pk_ap_integral_detail;
	}

	public void setPk_ap_integral_detail(String pk_ap_integral_detail) {
		this.pk_ap_integral_detail = pk_ap_integral_detail;
	}

	public String getCustomer_idcard() {
		return customer_idcard;
	}

	public void setCustomer_idcard(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}

	public double getAp_integral() {
		return ap_integral;
	}

	public void setAp_integral(double ap_integral) {
		this.ap_integral = ap_integral;
	}
	
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "ActivitiesPresentedVO [pk_ap_integral_detail="
				+ pk_ap_integral_detail + ", customer_idcard="
				+ customer_idcard + ", ap_integral=" + ap_integral + "]";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_ap_integral_detail";
	}

	@Override
	public String getTableName() {
		return "gd_ap_integral_detail";
	}

}
