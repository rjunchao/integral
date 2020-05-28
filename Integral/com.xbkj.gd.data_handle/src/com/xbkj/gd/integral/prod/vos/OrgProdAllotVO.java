package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2020-5-12
 *@version 1.0.0
 *@desc 分理处礼品提取明细表
 */
public class OrgProdAllotVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1487740890402503899L;
	private String pk_org_prod_allot;
	private String pk_org_apply_product;
	private String allot_prod;//提取礼品
	private String allot_org;//提取机构
	private String remark;//提取机构
	private int allot_num;//提取数量
	
	public OrgProdAllotVO() {
		super();
	}

	public OrgProdAllotVO(
			String pk_org_apply_product, String allot_prod, String allot_org,
			int allot_num, String remark) {
		super();
		this.pk_org_apply_product = pk_org_apply_product;
		this.allot_prod = allot_prod;
		this.allot_org = allot_org;
		this.allot_num = allot_num;
		this.remark = remark;
	}

	public String getPk_org_prod_allot() {
		return pk_org_prod_allot;
	}

	public void setPk_org_prod_allot(String pk_org_prod_allot) {
		this.pk_org_prod_allot = pk_org_prod_allot;
	}

	public String getPk_org_apply_product() {
		return pk_org_apply_product;
	}

	public void setPk_org_apply_product(String pk_org_apply_product) {
		this.pk_org_apply_product = pk_org_apply_product;
	}

	public String getAllot_prod() {
		return allot_prod;
	}

	public void setAllot_prod(String allot_prod) {
		this.allot_prod = allot_prod;
	}

	public String getAllot_org() {
		return allot_org;
	}

	public void setAllot_org(String allot_org) {
		this.allot_org = allot_org;
	}

	public int getAllot_num() {
		return allot_num;
	}

	public void setAllot_num(int allot_num) {
		this.allot_num = allot_num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "OrgProdAllotVO [pk_org_prod_allot=" + pk_org_prod_allot
				+ ", pk_org_apply_product=" + pk_org_apply_product
				+ ", allot_prod=" + allot_prod + ", allot_org=" + allot_org
				+ ", remark=" + remark + ", allot_num=" + allot_num + "]";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_org_prod_allot";
	}

	@Override
	public String getTableName() {
		return "gd_org_prod_allot";
	}

}
