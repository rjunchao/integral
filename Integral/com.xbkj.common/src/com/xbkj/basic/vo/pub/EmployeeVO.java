package com.xbkj.basic.vo.pub;
/**
 *@author heFei
 *@date 2016-8-25
 *@version 1.0.0
 *员工实体类
 */
public class EmployeeVO extends XbkjSuperVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //以下是员工实体的相关字段
	private String sys_c0047095;//员工表主键
	private String empcode;//员工编码
	private String userid;//员工id
	private String empname;//员工的姓名
	
	public static final String SYS_C0047095 = "SYS_C0047095";
	public static final String EMPCODE = "empcode";
	public static final String USERID = "userid";
	public static final String EMPNAME = "empname";
	
	

	
    
	public String getSys_c0047095() {
		return sys_c0047095;
	}

	public void setSys_c0047095(String sys_c0047095) {
		this.sys_c0047095 = sys_c0047095;
	}

	public String getEmpcode() {
		return empcode;
	}

	public void setEmpcode(String empcode) {
		this.empcode = empcode;
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

	@Override
	public String getParentPKFieldName() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public String getPKFieldName() {
		// 获取员工表主键名称
		return "sys_c0047095";
	}

	@Override
	public String getTableName() {
		// 获取员工表
		return "org_employee";
	}

}
