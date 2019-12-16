/**
 * 
 */
package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;



/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-30
 *@version 1.0.0
 *@desc
 */

public class SignVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6681745693702034199L;
	private String pk_sign;// VARCHAR(30) NOT NULL,
	private String sign_date;// CHAR(19) COMMENT '签名时间',
//	private String pk_integral_sign;//` VARCHAR(30) NOT NULL,
	private String sign_file_name;//` VARCHAR(30) NOT NULL COMMENT '文件名',
	private String sign_file_path;//` VARCHAR(128) NOT NULL COMMENT '路径',
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getPKFieldName() {
		return "pk_sign";
	}
	@Override
	public String getTableName() {
		return "gd_sign";
	}
	public String getPk_sign() {
		return pk_sign;
	}
	public void setPk_sign(String pk_sign) {
		this.pk_sign = pk_sign;
	}
	public String getSign_date() {
		return sign_date;
	}
	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}
	/*public String getPk_integral_sign() {
		return pk_integral_sign;
	}
	public void setPk_integral_sign(String pk_integral_sign) {
		this.pk_integral_sign = pk_integral_sign;
	}*/
	public String getSign_file_name() {
		return sign_file_name;
	}
	public void setSign_file_name(String sign_file_name) {
		this.sign_file_name = sign_file_name;
	}
	public String getSign_file_path() {
		return sign_file_path;
	}
	public void setSign_file_path(String sign_file_path) {
		this.sign_file_path = sign_file_path;
	}
	
}
