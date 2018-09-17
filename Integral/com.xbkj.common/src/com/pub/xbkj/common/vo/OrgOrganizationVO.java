package com.pub.xbkj.common.vo;

import java.util.Date;

import com.xbkj.basic.vo.pub.XbkjSuperVO;

/**
 *@author rjc
 *@date 2016-7-26
 *@version 1.0.0
 */
public class OrgOrganizationVO extends XbkjSuperVO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6365515557212852965L;
	private int orgid;  //机构主键
	private String orgcode;    //机构编码
	private String orgname;     //机构名
	private int orglevel;    //机构登记
	private String orgtype; //机构类型     
	private String orgaddr;      //机构类型
	private String zipcode;     //邮编
	private String linkman;    //联系人 
	private String linktel;      //联系电话
	private String email;      //邮箱
	private String weburl;       //网址
	private Date startdate;   //生效日期
	private Date enddate;     //时效日期
	//private String status;      //状态
	private String area;        //所属地域
	//private Date createtime;  //创建时间
	private Date lastupdate;   //最后更新试卷
	private int updator;     //更新人
	private int sortno;     //顺序  
	private String isleaf;  //是否叶子    
	private int subcount;     
	private String remark;    //备注  
	private int  parentorgid; //父节点
	
	
	
	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
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

	public int getOrglevel() {
		return orglevel;
	}

	public void setOrglevel(int orglevel) {
		this.orglevel = orglevel;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getOrgaddr() {
		return orgaddr;
	}

	public void setOrgaddr(String orgaddr) {
		this.orgaddr = orgaddr;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinktel() {
		return linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeburl() {
		return weburl;
	}

	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public int getUpdator() {
		return updator;
	}

	public void setUpdator(int updator) {
		this.updator = updator;
	}

	public int getSortno() {
		return sortno;
	}

	public void setSortno(int sortno) {
		this.sortno = sortno;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public int getSubcount() {
		return subcount;
	}

	public void setSubcount(int subcount) {
		this.subcount = subcount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getParentorgid() {
		return parentorgid;
	}

	public void setParentorgid(int parentorgid) {
		this.parentorgid = parentorgid;
	}

	

	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return "orgcode";
	}

	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return "org_organization";
	}

}
