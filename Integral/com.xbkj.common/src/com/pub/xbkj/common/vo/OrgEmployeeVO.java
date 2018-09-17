package com.pub.xbkj.common.vo;

import java.util.Date;

import com.xbkj.basic.vo.pub.XbkjSuperVO;

/**
 * @author rjc
 * @date 2016-7-27
 * @version 1.0.0
 * @desc
 */
public class OrgEmployeeVO extends XbkjSuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int empid;//id
	private String empcode;//编码
	private int operatorid;//操作id
	private String userid;//用户id
	private String empname;//用户名
	private String realname;//别名
	private String gender;//性别
	private Date birthdate;//生日
	private int position;//岗位
	private String empstatus;//状态
	private String cardtype;//类型
	private String cardno;//
	private Date indate;
	private Date outdate;
	private String otel;
	private String oaddress;
	private String ozipcode;
	private String oemail;
	private String faxno;
	private String mobileno;
	private String qq;
	private String htel;
	private String haddress;
	private String hzipcode;
	private String pemail;
	private String party;
	private String degree;
	private int major;
	private String specialty;
	private String workexp;
	private Date regdate;
	private Date lastmodytime;
	private String orgidlist;
	private int orgid;//机构id
	private String remark;//备注
	private String tenant_id;
	private String app_id;
	private String weibo;//微博

	
	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}

	public int getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getEmpstatus() {
		return empstatus;
	}

	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getOtel() {
		return otel;
	}

	public void setOtel(String otel) {
		this.otel = otel;
	}

	public String getOaddress() {
		return oaddress;
	}

	public void setOaddress(String oaddress) {
		this.oaddress = oaddress;
	}

	public String getOzipcode() {
		return ozipcode;
	}

	public void setOzipcode(String ozipcode) {
		this.ozipcode = ozipcode;
	}

	public String getOemail() {
		return oemail;
	}

	public void setOemail(String oemail) {
		this.oemail = oemail;
	}

	public String getFaxno() {
		return faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHtel() {
		return htel;
	}

	public void setHtel(String htel) {
		this.htel = htel;
	}

	public String getHaddress() {
		return haddress;
	}

	public void setHaddress(String haddress) {
		this.haddress = haddress;
	}

	public String getHzipcode() {
		return hzipcode;
	}

	public void setHzipcode(String hzipcode) {
		this.hzipcode = hzipcode;
	}

	public String getPemail() {
		return pemail;
	}

	public void setPemail(String pemail) {
		this.pemail = pemail;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getWorkexp() {
		return workexp;
	}

	public void setWorkexp(String workexp) {
		this.workexp = workexp;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	
	public Date getLastmodytime() {
		return lastmodytime;
	}

	public void setLastmodytime(Date lastmodytime) {
		this.lastmodytime = lastmodytime;
	}

	public String getOrgidlist() {
		return orgidlist;
	}

	public void setOrgidlist(String orgidlist) {
		this.orgidlist = orgidlist;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return "";
	}

	@Override
	public String getPKFieldName() {
		// TODO 自动生成的方法存根
		return "empcode";
	}

	@Override
	public String getTableName() {
		// TODO 自动生成的方法存根
		return "org_employee";
	}

}
