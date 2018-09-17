package com.xbkj.gd.integral.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2018-9-4
 *@version 1.0.0
 *@desc 配置积分增减
 */
public class IntegralConfigVO extends GdSuperVO{

	private String pk_gd_integral_config;//主键
	private String integral_type_name;//类型的名称
	private long integral;
	private String integral_type;//1：添加积分，2：减少积分, 3:vip赠送，4：资金来源
	
	private double integral_coefficient;//积分系数，积分
	private String integral_year;//年份，送积分的
	

	public String getPk_gd_integral_config() {
		return pk_gd_integral_config;
	}

	public void setPk_gd_integral_config(String pk_gd_integral_config) {
		this.pk_gd_integral_config = pk_gd_integral_config;
	}

	public double getIntegral_coefficient() {
		return integral_coefficient;
	}

	public void setIntegral_coefficient(double integral_coefficient) {
		this.integral_coefficient = integral_coefficient;
	}

	
	public String getIntegral_year() {
		return integral_year;
	}

	public void setIntegral_year(String integral_year) {
		this.integral_year = integral_year;
	}

	public void setIntegral_type_name(String integral_type_name) {
		this.integral_type_name = integral_type_name;
	}

	public String getIntegral_type_name() {
		return integral_type_name;
	}

	public void setIntegralTypeName(String integral_type_name) {
		this.integral_type_name = integral_type_name;
	}

	public long getIntegral() {
		return integral;
	}

	public void setIntegral(long getIntegral) {
		this.integral = getIntegral;
	}
	
	public String getIntegral_type() {
		return integral_type;
	}
	
	public void setIntegral_type(String integral_type) {
		this.integral_type = integral_type;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415020810293390829L;

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_gd_integral_config";
	}

	@Override
	public String getTableName() {
		return "gd_integral_config";
	}

	

}
