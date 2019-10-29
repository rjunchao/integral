package com.xbkj.gd.integral.vos;
/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-1-10
 *@version 1.0.0
 *@desc
 */
public class LeadDrawIntegralVO extends IntegralDetailVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2644044178220955616L;

	
	/*
	 	pk_lead_integral_detail  ;// '主键',
	  	customer_idcard  ;// '客户身份证号',
	  	customer_integral    '提前支取积分',
	  	conversion_detail    '兑换明细',
	  	input_date    '录入时间',
	  	option_org    '操作机构',
	  	dr CHAR(1)   '删除标志',
	  	def3    '年份',
	  	ts ,
	  	def1    '提前支取',
	  	create_user ,
	  	create_user_org ,
	  	createtime ,
	  	modifiedtime ,
	  	modifier ,
	  	def8    '积分兑换批次号',
	  	def9 ,
	  	def10 
	 */
	
	@Override
	public String getTableName() {
		return "gd_lead_integral_detail";
	}
}
