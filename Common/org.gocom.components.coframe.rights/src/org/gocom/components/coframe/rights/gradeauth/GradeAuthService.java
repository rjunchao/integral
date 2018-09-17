/*
 * Copyright 2013 Primeton Technologies Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gocom.components.coframe.rights.gradeauth;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.tools.IConstants;
import org.gocom.components.coframe.tools.superadmin.SuperAdminService;
import com.eos.common.connection.ConnectionHelper;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.IUserObject;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * 分级授权的类，获取可管理的机构列表和可管理的角色列表
 * 
 * @author caozw@primeton.com
 * 
 */
public class GradeAuthService {
	
	public static final String SPRING_BEAN_NAME = "GradeAuthBean";
	
	/**
	 * 分级授权的方法，获取可管理的机构列表，从员工的参与者中获取可授权机构列表
	 * 
	 * @return
	 */
	/*public List<Party> getManagedOrgList() {
		List<Party> partyList = new ArrayList<Party>();
		
		if (SuperAdminService.currUserIsSupserAdmin()) {
			return PartyRuntimeManager.getInstance().getRootPartyList(
					IConstants.ORG_PARTY_TYPE_ID);
		}
		
		if(!judgeIsEmp()) {
			return partyList;
		}
		
		String userId = getUserId();
		t
		Party empParty = PartyRuntimeManager.getInsance().getPartyByPartyID(
				userId, IConstants.EMP_PARTY_TYPE_ID);
		String orgIdAndNames = empParty.getExtAttribute(IConstants.MANAGED_ORGS);
		if (orgIdAndNames != null) {
			String[] orgIdAndNameArray = StringUtils.split(orgIdAndNames, ",");
			if (orgIdAndNameArray != null) {
				for (String orgIdAndName : orgIdAndNameArray) {
					Party orgParty = PartyRuntimeManager.getInstance()
							.getPartyByPartyID(StringUtils.split(orgIdAndName, ":")[0],
									IConstants.ORG_PARTY_TYPE_ID);
					if(orgParty != null) {
						partyList.add(orgParty);
					}
				}
			}
		}
		return partyList;
	}*/
	
	
	/**
	 * 获取对应机构列表
	 * 修改原有获取可管理的机构列表方法
	 * @author lc
	 * @2015/4/27
	 * @return
	 */
	public List<Party> getManagedOrgList() {
		List<Party> partyList = new ArrayList<Party>();	
		
		if (SuperAdminService.currUserIsSupserAdmin()) {
			return PartyRuntimeManager.getInstance().getRootPartyList(
					IConstants.ORG_PARTY_TYPE_ID);
		}	
		
		if(!judgeIsEmp()) {
			return partyList;
		}	
		
		//普通用户情况判断
		IMUODataContext muoContext = DataContextManager.current()
				.getMUODataContext();
		IUserObject userObject = muoContext.getUserObject();
		String empOrgid = userObject.getUserOrgId();
		OrgOrganization orgIdAndNames = queryEmpIdAndName(empOrgid);
		Party orgParty = null ;
		if (orgIdAndNames.getOrgid() != null || !"".equals(orgIdAndNames.getOrgid())) {	
				 orgParty = PartyRuntimeManager.getInstance()
						.getPartyByPartyID(orgIdAndNames.getOrgid().toString(),
								IConstants.ORG_PARTY_TYPE_ID);
			if(orgParty != null) {
				partyList.add(orgParty);
			}									
		}
		return partyList;
	}
	
	/**
	 * 根据empid获的机构ID和机构名称
	 * 新增查询方法
	 * @author lc
	 * @2015/4/27
	 * @return
	 */
	public OrgOrganization queryEmpIdAndName(String empOrgid){		
		OrgOrganization orgOrganization = OrgOrganization.FACTORY.create();
		String sql = "select orgid,orgname from org_organization where orgid = "+empOrgid+" ";
		Connection conn = ConnectionHelper
				.getCurrentContributionConnection("default");		
		Statement stmt = null;
		try {			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);		
			while (rs.next()) {
				BigDecimal bd = new BigDecimal(rs.getString(1));
				orgOrganization.setOrgid(bd);
				orgOrganization.setOrgname(rs.getString(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
			close(conn);
		}
	       return orgOrganization;
	}
	
	/**
	 * @author lc
	 * @2015/4/27
	 */
	private static void close(Connection conn) {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	
	/**
	 * @author lc
	 * @2015/4/27
	 */
	private static void close(Statement stmt) {
		if (stmt == null)
			return;
		try {
			stmt.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	

	private String getUserId() {
		IMUODataContext muoContext = DataContextManager.current()
				.getMUODataContext();
		IUserObject userObject = muoContext.getUserObject();
		if (userObject != null) {
			return userObject.getUserId();
		}
		return null;
	}
	
	private boolean judgeIsEmp() {
		IMUODataContext muoContext = DataContextManager.current()
				.getMUODataContext();
		IUserObject userObject = muoContext.getUserObject();
		if (userObject != null) {
			if(userObject.getUserOrgId() == null || "".equals(userObject.getUserOrgId())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 分级授权的方法，获取可管理的角色列表，从员工的参与者中获取可授权角色列表
	 * 
	 * @return
	 */
	public List<Party> getManagedRoleList() {
		if (SuperAdminService.currUserIsSupserAdmin()) {
			return PartyRuntimeManager.getInstance().getAllPartyList(
					IConstants.ROLE_PARTY_TYPE_ID);
		}
		String userId = getUserId();
		List<Party> partyList = new ArrayList<Party>();
		Party empParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				userId, IConstants.EMP_PARTY_TYPE_ID);
		String roleIdAndNames = empParty.getExtAttribute(IConstants.MANAGED_ROLES);
		if (roleIdAndNames != null) {
			String[] roleIdAndNameArray = StringUtils.split(roleIdAndNames, ",");
			if (roleIdAndNameArray != null) {
				for (String roleIdAndName : roleIdAndNameArray) {
					Party roleParty = PartyRuntimeManager.getInstance()
							.getPartyByPartyID(StringUtils.split(roleIdAndName, ":")[0],
									IConstants.ROLE_PARTY_TYPE_ID);
					if(roleParty != null) {
						partyList.add(roleParty);
					}
				}
			}
		}
		return partyList;
	}
}
