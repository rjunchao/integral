package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-14
 *@version 1.0.0
 *@desc
 */
public class ProductVO extends GdSuperVO{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pk_product;
	
	private String product_code;//编号
	private String product_name;//名称
	private String start_time;//生效时间
	private String product_integral;//积分单位
	private int product_num;//数量
	private String product_status;//状态：1：未入库：2:入库
	
	

	public String getPk_product() {
		return pk_product;
	}

	public void setPk_product(String pk_product) {
		this.pk_product = pk_product;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_num() {
		return product_num;
	}

	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_product";
	}

	@Override
	public String getTableName() {
		return "gd_product";
	}
	
	public String getProduct_status() {
		return product_status;
	}
	
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getProduct_integral() {
		return product_integral;
	}
	
	public void setProduct_integral(String product_integral) {
		this.product_integral = product_integral;
	}
	

}
