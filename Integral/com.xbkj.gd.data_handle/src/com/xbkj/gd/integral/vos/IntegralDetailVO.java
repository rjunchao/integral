package com.xbkj.gd.integral.vos;

import java.math.BigDecimal;
import java.util.Arrays;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
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
	
	private String sign;
	
	
	private String customer_name;
	
	private String marketing_people1;//营销人1
	private String marketing_people2;//营销人2
	private String marketing_people3;//营销人3
	private double marketing_people_amt1;//营销人1营销金额
	private double marketing_people_amt2;//营销人2营销金额
	private double marketing_people_amt3;//营销人3营销金额
	

	private String capital_source1;//资金来源1
	private String capital_source2;//资金来源2
	private String capital_source3;//资金来源3
	private double capital_source_amt1;//资金来源1
	private double capital_source_amt2;//资金来源2
	private double capital_source_amt3;//资金来源3
	private String duration;//存续期
	private String remark;//备注

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
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
	
	
	
	public String getMarketing_people1() {
		return marketing_people1;
	}
	public void setMarketing_people1(String marketing_people1) {
		this.marketing_people1 = marketing_people1;
	}
	public String getMarketing_people2() {
		return marketing_people2;
	}
	public void setMarketing_people2(String marketing_people2) {
		this.marketing_people2 = marketing_people2;
	}
	public String getMarketing_people3() {
		return marketing_people3;
	}
	public void setMarketing_people3(String marketing_people3) {
		this.marketing_people3 = marketing_people3;
	}
	public double getMarketing_people_amt1() {
		return marketing_people_amt1;
	}
	public void setMarketing_people_amt1(double marketing_people_amt1) {
		this.marketing_people_amt1 = marketing_people_amt1;
	}
	public double getMarketing_people_amt2() {
		return marketing_people_amt2;
	}
	public void setMarketing_people_amt2(double marketing_people_amt2) {
		this.marketing_people_amt2 = marketing_people_amt2;
	}
	public double getMarketing_people_amt3() {
		return marketing_people_amt3;
	}
	public void setMarketing_people_amt3(double marketing_people_amt3) {
		this.marketing_people_amt3 = marketing_people_amt3;
	}
	public String getCapital_source1() {
		return capital_source1;
	}
	public void setCapital_source1(String capital_source1) {
		this.capital_source1 = capital_source1;
	}
	public String getCapital_source2() {
		return capital_source2;
	}
	public void setCapital_source2(String capital_source2) {
		this.capital_source2 = capital_source2;
	}
	public String getCapital_source3() {
		return capital_source3;
	}
	public void setCapital_source3(String capital_source3) {
		this.capital_source3 = capital_source3;
	}
	
	public double getCapital_source_amt1() {
		return capital_source_amt1;
	}
	public void setCapital_source_amt1(double capital_source_amt1) {
		this.capital_source_amt1 = capital_source_amt1;
	}
	public double getCapital_source_amt2() {
		return capital_source_amt2;
	}
	public void setCapital_source_amt2(double capital_source_amt2) {
		this.capital_source_amt2 = capital_source_amt2;
	}
	public double getCapital_source_amt3() {
		return capital_source_amt3;
	}
	public void setCapital_source_amt3(double capital_source_amt3) {
		this.capital_source_amt3 = capital_source_amt3;
	}
	public String getDuration() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
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
		return "gd_integral_detail2";
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
				+ input_date + ", option_org=" + option_org + ", real_idcard="
				+ real_idcard + ", customer_name=" + customer_name
				+ ", marketing_people1=" + marketing_people1
				+ ", Marketing_people2=" + marketing_people2
				+ ", Marketing_people3=" + marketing_people3
				+ ", Marketing_people_amt1=" + marketing_people_amt1
				+ ", Marketing_people_amt2=" + marketing_people_amt2
				+ ", Marketing_people_amt3=" + marketing_people_amt3
				+ ", capital_source1=" + capital_source1 + ", capital_source2="
				+ capital_source2 + ", capital_source3=" + capital_source3
				+ ", getReal_idcard()=" + getReal_idcard()
				+ ", getPk_integral_detail()=" + getPk_integral_detail()
				+ ", getCustomer_idcard()=" + getCustomer_idcard()
				+ ", getCustomer_integral()=" + getCustomer_integral()
				+ ", getCustomer_account()=" + getCustomer_account()
				+ ", getDeposit_receipt_num()=" + getDeposit_receipt_num()
				+ ", getDeposit_receipt_amt()=" + getDeposit_receipt_amt()
				+ ", getConversion_detail()=" + getConversion_detail()
				+ ", getInput_date()=" + getInput_date() + ", getOption_org()="
				+ getOption_org() + ", getCustomer_name()="
				+ getCustomer_name() + ", getMarketing_people1()="
				+ getMarketing_people1() + ", getMarketing_people2()="
				+ getMarketing_people2() + ", getMarketing_people3()="
				+ getMarketing_people3() + ", getMarketing_people_amt1()="
				+ getMarketing_people_amt1() + ", getMarketing_people_amt2()="
				+ getMarketing_people_amt2() + ", getMarketing_people_amt3()="
				+ getMarketing_people_amt3() + ", getCapital_source1()="
				+ getCapital_source1() + ", getCapital_source2()="
				+ getCapital_source2() + ", getCapital_source3()="
				+ getCapital_source3() + ", getParentPKFieldName()="
				+ getParentPKFieldName() + ", getPKFieldName()="
				+ getPKFieldName() + ", getTableName()=" + getTableName()
				+ ", get_id()=" + get_id() + ", get_pid()=" + get_pid()
				+ ", get_state()=" + get_state() + ", get_uid()=" + get_uid()
				+ ", get_editing()=" + get_editing() + ", getExpanded()="
				+ getExpanded() + ", getEntityName()=" + getEntityName()
				+ ", getpKFieldName()=" + getpKFieldName() + ", get_level()="
				+ get_level() + ", getChecked()=" + getChecked()
				+ ", getFlag()=" + getFlag() + ", getCreatetime()="
				+ getCreatetime() + ", getModifier()=" + getModifier()
				+ ", getModifiedtime()=" + getModifiedtime()
				+ ", getEmpname()=" + getEmpname() + ", getOrgname()="
				+ getOrgname() + ", getDef1()=" + getDef1() + ", getDef2()="
				+ getDef2() + ", getDef3()=" + getDef3() + ", getDef4()="
				+ getDef4() + ", getDef5()=" + getDef5() + ", getDef6()="
				+ getDef6() + ", getDef7()=" + getDef7() + ", getDef8()="
				+ getDef8() + ", getDef9()=" + getDef9() + ", getDef10()="
				+ getDef10() + ", getDr()=" + getDr() + ", getTs()=" + getTs()
				+ ", getCreate_user()=" + getCreate_user()
				+ ", getCreate_user_org()=" + getCreate_user_org()
				+ ", getAttributeNames()="
				+ Arrays.toString(getAttributeNames()) + ", getPrimaryKey()="
				+ getPrimaryKey() + ", getStatus()=" + getStatus()
				+ ", isDirty()=" + isDirty() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
