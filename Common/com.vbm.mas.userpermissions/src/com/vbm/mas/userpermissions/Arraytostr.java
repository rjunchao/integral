/**
 * 
 */
package com.vbm.mas.userpermissions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.eos.common.connection.ConnectionHelper;
import com.eos.foundation.database.DatabaseExt;
import com.eos.foundation.database.DatabaseUtil;
import com.eos.system.annotation.Bizlet;
import com.vbm.mas.userpermissions.orgdataset.FcUserPermissions;
import commonj.sdo.DataObject;

/**
 * @author WES
 * @date 2015-04-16 17:35:19
 *
 */
@Bizlet("")
public class Arraytostr {
	@Bizlet("")
	public String arraytostring(DataObject [] a,String orgUn){
		if(a.length==0){
			return "";
		}
		StringBuffer b=new StringBuffer();
		for(int i=0;i<a.length;i++){
			if(i==a.length-1){
				b.append(a[i].getString("porgid"));
			}else{
				b.append(a[i].getString("porgid")+",");
			}
		}
		String aa="where orgid not in ("+b.toString()+")";
		if(orgUn !=null && !orgUn.isEmpty()){
			aa += " and (orgcode like '%"+orgUn+"%'"+" or orgname like '%"+orgUn+"%')";
		}
			
		return aa.toString();
		
	}
	@Bizlet("")
	public String deleteUserOrg(String orgid,String empid,String userid,String[] left,String[] right){
		String sql="delete from MAS_USER_PERMISSIONS where empid="+empid;
		try{
		DatabaseExt.executeNamedSql("default", "com.vbm.mas.userpermissions.orgTree.deleteuserOrg", sql);
		List temp = new ArrayList();
		for(int i=0;i<left.length;i++){
			FcUserPermissions userPermissions=FcUserPermissions.FACTORY.create();
			userPermissions.setOrgid(Integer.valueOf(orgid));
			userPermissions.setEmpid(Integer.valueOf(empid));
			userPermissions.setUserid(userid);
			userPermissions.setPorgid(Integer.valueOf(left[i]));
			temp.add(userPermissions);
		}
		FcUserPermissions[] fcUserPermissions = Arrays.asList(temp.toArray()).toArray(new FcUserPermissions[0]);
		DatabaseUtil.insertEntityBatch("default",  fcUserPermissions);
		}catch(Exception e){
			e.printStackTrace();
			return "2";
		}
		return "1";
	}
	@Bizlet("树形结构授权")
	public String AuthUserOrg(String orgid,String empid,String userid,String[] orgIds){
		String sql="delete from MAS_USER_PERMISSIONS where empid="+empid;
		try{
		DatabaseExt.executeNamedSql("default", "com.vbm.mas.userpermissions.orgTree.deleteuserOrg", sql);
		List temp = new ArrayList();
		for(int i=0;i<orgIds.length;i++){
			FcUserPermissions userPermissions=FcUserPermissions.FACTORY.create();
			userPermissions.setOrgid(Integer.valueOf(orgid));
			userPermissions.setEmpid(Integer.valueOf(empid));
			userPermissions.setUserid(userid);
			userPermissions.setPorgid(Integer.valueOf(orgIds[i]));
			temp.add(userPermissions);
		}
		FcUserPermissions[] fcUserPermissions = Arrays.asList(temp.toArray()).toArray(new FcUserPermissions[0]);
		DatabaseUtil.insertEntityBatch("default",  fcUserPermissions);
		}catch(Exception e){
			e.printStackTrace();
			return "2";
		}
		return "1";
	}
	@Bizlet("设置查询权限")
	public void updateAuth(DataObject[] auths){
		Connection conn = ConnectionHelper
				.getCurrentContributionConnection("default");
		PreparedStatement pst1 = null;		
		String update = "update org_employee set auth=? where empid=? ";
		try {
			pst1 =  conn.prepareStatement(update);
			for(int i=0;i<auths.length;i++){
				pst1.setString(1, auths[i].get("auth").toString());
				pst1.setString(2, auths[i].get("empid").toString());
				pst1.addBatch();
			}
			pst1.executeBatch();
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				pst1.close();
				pst1.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
