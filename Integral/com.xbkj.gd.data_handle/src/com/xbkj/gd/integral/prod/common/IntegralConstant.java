package com.xbkj.gd.integral.prod.common;
/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc 常量定义
 */
public class IntegralConstant {

	//商品申请审批状态表
	public static final String PROD_APPLY_AUDIT_STATUS_INIT="1";//审批中
	public static final String PROD_APPLY_AUDIT_STATUS_SUCCESS="2";//审批通过
	public static final String PROD_APPLY_AUDIT_STATUS_ABORT="3";//审批失败
	
	//商品申请审批情况
	public static final String ORG_SUB_BRANCH_AMDIN="1";//支行管理员
	public static final String HEAD_ORG_AMDIN="2";//合行管理员
	public static final String HEAD_ORG_LEADER="3";//合行领导
	public static final String AUDIT_PASS="4";//审核通过
	public static final String ALLOT_PASS="5";//调拨
	public static final String AUDIT_ABORT="6";//拒绝
}
