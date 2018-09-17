package com.xbkj.gd.integral.vos;

import java.math.BigDecimal;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-5
 *@version 1.0.0
 *@desc: 积分明细表
 */
public class IntegralDetailVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893812773577983451L;

	
	private String pk_integral_detail;//主键
	private String customer_idcard;//客户身份证号，根据客户得到
	private double customer_integral;//客户积分
	private String customer_account;//客户账号
	private String deposit_receipt_num;//存单号
	private double deposit_receipt_amt;//存单金额
	private String conversion_detail;//兑换明细
	private String input_date;//录入时间
	private String option_org;//操作机构
	private String real_idcard;
	
	
	private String customer_name;
	
	
	public IntegralDetailVO() {
		super();
		
	}
	public IntegralDetailVO(String pk_integral_detail, String customer_idcard,
			double customer_integral, String customer_account,
			String deposit_receipt_num, double deposit_receipt_amt, String conversion_detail,
			String input_date, String option_org) {
		super();
		this.pk_integral_detail = pk_integral_detail;
		this.customer_idcard = customer_idcard;
		this.customer_integral = customer_integral;
		this.customer_account = customer_account;
		this.deposit_receipt_num = deposit_receipt_num;
		this.deposit_receipt_amt = deposit_receipt_amt;
		this.conversion_detail = conversion_detail;
		this.input_date = input_date;
		this.option_org = option_org;
	}
	public String getReal_idcard() {
		return real_idcard;
	}
	
	public void setReal_idcard(String real_idcard) {
		this.real_idcard = real_idcard;
	}
	public String getPk_integral_detail() {
		return pk_integral_detail;
	}

	public void setPk_integral_detail(String pk_integral_detail) {
		this.pk_integral_detail = pk_integral_detail;
	}

	public String getCustomer_idcard() {
		return customer_idcard;
	}

	public void setCustomer_idcard(String customer_idcard) {
		this.customer_idcard = customer_idcard;
	}

	/*
	 * 处理四舍五入
	 */
	public double getCustomer_integral() {
		return new BigDecimal(customer_integral).setScale(2, BigDecimal.ROUND_UP).doubleValue();
	}

	public void setCustomer_integral(double customer_integral) {
		this.customer_integral = new BigDecimal(customer_integral).setScale(2, BigDecimal.ROUND_UP).doubleValue();
	}

	public String getCustomer_account() {
		return customer_account;
	}

	public void setCustomer_account(String customer_account) {
		this.customer_account = customer_account;
	}

	public String getDeposit_receipt_num() {
		return deposit_receipt_num;
	}

	public void setDeposit_receipt_num(String deposit_receipt_num) {
		this.deposit_receipt_num = deposit_receipt_num;
	}

	public double getDeposit_receipt_amt() {
		return deposit_receipt_amt;
	}

	public void setDeposit_receipt_amt(double deposit_receipt_amt) {
		this.deposit_receipt_amt = deposit_receipt_amt;
	}

	public String getConversion_detail() {
		return conversion_detail;
	}
	
	public void setConversion_detail(String conversion_detail) {
		this.conversion_detail = conversion_detail;
	}
	public String getInput_date() {
		return input_date;
	}

	public void setInput_date(String input_date) {
		this.input_date = input_date;
	}

	public String getOption_org() {
		return option_org;
	}

	public void setOption_org(String option_org) {
		this.option_org = option_org;
	}


	public String getCustomer_name() {
		return customer_name;
	}
	
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_integral_detail";
	}

	@Override
	public String getTableName() {
		return "gd_integral_detail";
	}
	@Override
	public String toString() {
		return "IntegralDetailVO [pk_integral_detail=" + pk_integral_detail
				+ ", customer_idcard=" + customer_idcard
				+ ", customer_integral=" + customer_integral
				+ ", customer_account=" + customer_account
				+ ", deposit_receipt_num=" + deposit_receipt_num
				+ ", deposit_receipt_amt=" + deposit_receipt_amt
				+ ", conversion_detail=" + conversion_detail + ", input_date="
				+ input_date + ", option_org=" + option_org + ",  customer_name=" + customer_name + "]";
	}
	
}
