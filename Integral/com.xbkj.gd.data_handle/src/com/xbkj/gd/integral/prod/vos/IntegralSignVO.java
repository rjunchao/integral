package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-30
 *@version 1.0.0
 *@desc
 */
public class IntegralSignVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pk_integral_sign;
	private String pk_sign;
	private String pk_integral_detail;
	
	public String getPk_integral_sign() {
		return pk_integral_sign;
	}

	public void setPk_integral_sign(String pk_integral_sign) {
		this.pk_integral_sign = pk_integral_sign;
	}

	public String getPk_sign() {
		return pk_sign;
	}

	public void setPk_sign(String pk_sign) {
		this.pk_sign = pk_sign;
	}

	public String getPk_integral_detail() {
		return pk_integral_detail;
	}

	public void setPk_integral_detail(String pk_integral_detail) {
		this.pk_integral_detail = pk_integral_detail;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_integral_sign";
	}

	@Override
	public String getTableName() {
		return "gd_integral_sign";
	}

}
