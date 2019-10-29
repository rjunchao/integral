package com.xbkj.gd.integral.prod.vos;
/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-23
 *@version 1.0.0
 *@desc
 */
public class OrgAuditProductVO extends AuditProductVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pk_org_audit_product;
	
	public String getPk_org_audit_product() {
		return pk_org_audit_product;
	}
	
	public void setPk_org_audit_product(String pk_org_audit_product) {
		this.pk_org_audit_product = pk_org_audit_product;
	}
	
	@Override
	public String getTableName() {
		return "gd_org_audit_product";
	}
	
	@Override
	public String getPKFieldName() {
		return "pk_org_audit_product";
	}
	
}
