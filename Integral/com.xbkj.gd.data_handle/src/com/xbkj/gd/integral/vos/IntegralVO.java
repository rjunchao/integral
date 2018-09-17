package com.xbkj.gd.integral.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-6
 *@version 1.0.0
 *@desc: 积分表
 */
public class IntegralVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5420260934169246446L;
	private String pk_integral;//
	private String integral_count;//总积分
	private String pk_customer_info;//客户主键
	
	
	
	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return null;
	}

}
