package com.xbkj.gd.integral.prod.vos;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-17
 *@version 1.0.0
 *@desc 审批记录
 */

public class AuditProductVO extends GdSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pk_audit_product ;// NOT NULL,
	private String apply_user;// (20) COMMENT '申请人',
	private String apply_date;// (19) COMMENT '申请时间',
	private String apply_org;// (20) COMMENT '申请人机构',
	private String audit_user;// (20) COMMENT '审批人',
	private String audit_date;// (19) COMMENT '审批时间',
	private String audit_status;// (1) COMMENT '状态，1：审批中，2：审批通过，3：审批拒绝',
	private String datas;
	

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "pk_audit_product";
	}

	@Override
	public String getTableName() {
		return "gd_audit_product";
	}

	public String getPk_audit_product() {
		return pk_audit_product;
	}

	public void setPk_audit_product(String pk_audit_product) {
		this.pk_audit_product = pk_audit_product;
	}

	public String getApply_user() {
		return apply_user;
	}

	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}

	public String getApply_org() {
		return apply_org;
	}

	public void setApply_org(String apply_org) {
		this.apply_org = apply_org;
	}

	public String getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}
	
	public String getDatas() {
		return datas;
	}
	
	public void setDatas(String datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "ApplyProductVO [pk_audit_product=" + pk_audit_product
				+ ", apply_user=" + apply_user + ", apply_date=" + apply_date
				+ ", apply_org=" + apply_org + ", audit_user=" + audit_user
				+ ", audit_date=" + audit_date + ", audit_status="
				+ audit_status + "]";
	}
}
