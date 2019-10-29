package com.xbkj.gd.integral.vos;

import java.math.BigDecimal;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2017-9-5
 *@version 1.0.0
 *@desc:客户vo
 */
public class CustomerVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8933738955550540791L;
	private String pk_customer_info;//主键
	private String customer_name;//客户名称
	private String customer_idcard;//身份证，唯一
	private String customer_phone;//手机号码
	private String recommend_phone;//推荐人手机号
	private double now_usable_integral;//当前可用积分
	private String input_org;//建档机构，登录用户机构
	private String orgname;//机构名称
	private String real_idcard;
	
	
	public CustomerVO() {
		super();
		
	}
	public CustomerVO(String customer_name, String customer_idcard,
			String customer_phone, String recommend_phone,
			double now_usable_integral, String input_org) {
		super();
		this.customer_name = customer_name;
		this.customer_idcard = customer_idcard;
		this.customer_phone = customer_phone;
		this.recommend_phone = recommend_phone;
		this.now_usable_integral = now_usable_integral;
		this.input_org = input_org;
		
	}
	
	public String getReal_idcard() {
		return real_idcard;
	}
	
	public void setReal_idcard(String real_idcard) {
		this.real_idcard = real_idcard;
	}
	
	public String getPk_customer_info() {
		return pk_customer_info;
	}
	public void setPk_customer_info(String pk_customer_info) {
		this.pk_customer_info = pk_customer_info;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_idcard() {
		return customer_idcard;
	}
	public void setCustomer_idcard(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}
	public String getCustomer_phone() {
		return customer_phone;
	}
	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}
	public String getRecommend_phone() {
		return recommend_phone;
	}
	public void setRecommend_phone(String recommend_phone) {
		this.recommend_phone = recommend_phone;
	}
	public double getNow_usable_integral() {
		return new BigDecimal(now_usable_integral).setScale(2, BigDecimal.ROUND_UP).doubleValue();
	}
	public void setNow_usable_integral(double now_usable_integral) {
		this.now_usable_integral = new BigDecimal(now_usable_integral).setScale(2, BigDecimal.ROUND_UP).doubleValue();
	}
	public String getInput_org() {
		return input_org;
	}
	public void setInput_org(String input_org) {
		this.input_org = input_org;
	}
	
	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}
	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return "pk_customer_info";
	}
	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return "gd_customer_info2";
	}
	@Override
	public String toString() {
		return "CustomerVO [pk_customer_info=" + pk_customer_info
				+ ", customer_name=" + customer_name + ", customer_idcard="
				+ customer_idcard + ", customer_phone=" + customer_phone
				+ ", recommend_phone=" + recommend_phone
				+ ", now_usable_integral=" + now_usable_integral
				+ ", input_org=" + input_org + ", orgname=" + orgname + "]";
	}
	
}
