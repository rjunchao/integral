/**
 * 
 */
package com.xbkj.gd.data_handle.common;


import java.math.BigDecimal;

import com.xbkj.gd.base.GdSuperVO;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-27
 *@version 1.0.0
 *@desc
 */
/**
 * @author rjc
 * @date 2019-10-27 20:11:29
 *
 */
public class OrganizationVO extends GdSuperVO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgid;// decimal(10,0) NOT NULL,
	private String orgcode;// varchar(32) NOT NULL,
	private String orgname;// varchar(64) DEFAULT NULL,
	/*private BigDecimal orglevel;// decimal(2,0) DEFAULT '1',
	private String orgdegree;// varchar(255) DEFAULT NULL,
	private String orgseq;// varchar(512) DEFAULT NULL,
	private String orgtype;// varchar(12) DEFAULT NULL,
	private String orgaddr;// varchar(256) DEFAULT NULL,
	private String zipcode;// varchar(10) DEFAULT NULL,
	private BigDecimal manaposition;// decimal(10,0) DEFAULT NULL,
	private BigDecimal managerid;// decimal(10,0) DEFAULT NULL,
	private String orgmanager;// varchar(128) DEFAULT NULL,
	private String linkman;// varchar(30) DEFAULT NULL,
	private String linktel;// varchar(20) DEFAULT NULL,
	private String email;// varchar(128) DEFAULT NULL,
	private String weburl;// varchar(512) DEFAULT NULL,
	private Date startdate;// date DEFAULT NULL,
	private Date enddate;// date DEFAULT NULL,
	private String status;// varchar(255) DEFAULT NULL,
	private String area;// varchar(30) DEFAULT NULL,
	private Date createtime;// datetime NOT NULL,
	private Date lastupdate;// datetime NOT NULL,
	private String updator;// decimal(10,0) DEFAULT NULL,
	private int sortno;// int(11) DEFAULT NULL,
	private String isleaf;// varchar(1) DEFAULT NULL,
	private BigDecimal subcount;// decimal(10,0) DEFAULT NULL,
	private String remark;// varchar(512) DEFAULT NULL,
	private String tenant_id;// varchar(64) NOT NULL,
	private String app_id;// varchar(64) DEFAULT NULL,
*/	private BigDecimal parentorgid;// decimal(10,0) DEFAULT NULL,

	
	public String getOrgid() {
		return orgid;
	}
	
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	public String getOrgcode() {
		return orgcode;
	}
	
	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
	
	public String getOrgname() {
		return orgname;
	}
	
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public BigDecimal getParentorgid() {
		return parentorgid;
	}
	
	public void setParentorgid(BigDecimal parentorgid) {
		this.parentorgid = parentorgid;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getPKFieldName() {
		return "orgid";
	}

	@Override
	public String getTableName() {
		return "org_organization";
	}

}
