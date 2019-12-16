package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-11-2
 *@version 1.0.0
 *@desc
 */
public class AuditDetailVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494276884349963943L;
	
	
	private String pk_audit_detail;
	private String audit_date;
	private String audit_user;
	private String pk_org_apply_product;
	private String audit_type;

	public String getPk_audit_detail() {
		return pk_audit_detail;
	}

	public void setPk_audit_detail(String pk_audit_detail) {
		this.pk_audit_detail = pk_audit_detail;
	}

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public String getPk_org_apply_product() {
		return pk_org_apply_product;
	}

	public void setPk_org_apply_product(String pk_org_apply_product) {
		this.pk_org_apply_product = pk_org_apply_product;
	}

	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}
	
	public String getAudit_type() {
		return audit_type;
	}
	
	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		
		return "pk_audit_detail";
	}

	@Override
	public String getTableName() {
		
		return "gd_audit_detail";
	}

}
