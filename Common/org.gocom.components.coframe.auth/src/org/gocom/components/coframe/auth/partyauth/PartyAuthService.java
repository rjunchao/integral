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
package org.gocom.components.coframe.auth.partyauth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gocom.components.coframe.auth.queryentity.PartyDataObject;
import org.gocom.components.coframe.rights.gradeauth.GradeAuthService;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.common.connection.ConnectionHelper;
import com.eos.spring.BeanFactory;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * Party授权服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public class PartyAuthService implements IPartyAuthService{

	public boolean addOrUpdatePartyAuthBatch(Party[] roleList, Party party) {
		
		if(roleList == null || roleList.length == 0 || party == null){
			return false;
		}
		AuthRuntimeManager.getInstance().addOrUpdatePartyAuthBatch(Arrays.asList(roleList), party);
		
		String sql = "update xbkj.cap_partyauth set role_type='role',party_id = party_type , " +
				" party_type= tenant_id,tenant_id ='default'  where role_type ='default' " ;
		
		this.updateTable(sql);
		
		return true;
		
	}

	public boolean delPartyAuthBatch(Party[] roleList, Party party) {
		if(roleList == null || roleList.length == 0 || party == null){
			return false;
		}
		String sql = "delete from cap_partyauth where party_id = '"+party.getId()+"' and party_type = '"+party.getPartyTypeID()+"' and tenant_id ='"+party.getTenantID()+"'  ";
		//return AuthRuntimeManager.getInstance().delPartyAuthBatch(Arrays.asList(roleList), party, IAuthManagerService.DEL_MODE_SINGLE);
        this.updateTable(sql);
		return true;
	}

	public List<Party> getAuthorizedRoles(String partyId, String partyType) {
		Party currentParty = PartyRuntimeManager.getInstance().getPartyByPartyID(partyId, partyType);
		List<Party> authorizedRolePartyList = AuthRuntimeManager.getInstance().getDirectRolePartyListByParty(currentParty);
		if(authorizedRolePartyList == null) return new ArrayList<Party>();
		
		// 可管理角色列表
		List<Party> managedRolePartyList = getGradeAuthService().getManagedRoleList();
		for(Party roleParty : authorizedRolePartyList) {
			roleParty.putExtAttribute(IConstants.IS_MANAGED,
					managedRolePartyList.contains(roleParty) ? "true" : "false");
		}
		
		return authorizedRolePartyList;
	}

	public List<Party> getUnauthorizedRoles(String partyId, String partyType) {
		Party currentParty = PartyRuntimeManager.getInstance().getPartyByPartyID(partyId, partyType);
		List<Party> authorizedPartyList = AuthRuntimeManager.getInstance().getDirectRolePartyListByParty(currentParty);
		// 可管理角色列表
		List<Party> managedRolePartyList = getGradeAuthService().getManagedRoleList();
		if(managedRolePartyList == null){
			return new ArrayList<Party>();
		}
		List<Party> unauthorizePartyList = new ArrayList<Party>();
		Set<String> authorizedPartyIdSet = new HashSet<String>();
		if(authorizedPartyList != null){
			for(Party authorizedParty : authorizedPartyList){
				authorizedPartyIdSet.add(authorizedParty.getId());
			}
		}
		// 排除已授权角色
		for(Party party : managedRolePartyList){
			if(!authorizedPartyIdSet.contains(party.getId())){
				unauthorizePartyList.add(party);
			}
		}
		return unauthorizePartyList;
	}
	
	public List<PartyDataObject> convertToDataObjectList(List<Party> partyList) {
		if(partyList == null || partyList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<PartyDataObject> partyDataObjectList = new ArrayList<PartyDataObject>(partyList.size());
		for(Party party : partyList) {
			partyDataObjectList.add(convertToDataObject(party));
		}
		return partyDataObjectList;
	}
	
	public PartyDataObject convertToDataObject(Party party) {
		PartyDataObject partyDataObject = PartyDataObject.FACTORY.create();
		
		if(party == null) return partyDataObject;
		
		partyDataObject.setId(party.getId());
		partyDataObject.setCode(party.getCode());
		partyDataObject.setName(party.getName());
		partyDataObject.setTenantID(party.getTenantID());
		partyDataObject.set(IConstants.IS_MANAGED, party.getExtAttribute(IConstants.IS_MANAGED));
		
		return partyDataObject;
	}
	
	// GradeAuthBean在另一个构件包，所以直接用BeanFactory获取
	private GradeAuthService getGradeAuthService() {
		return BeanFactory.newInstance().getBean(GradeAuthService.SPRING_BEAN_NAME);
	}
	
	
	/**
	 * mysql下字段会错位
	 * @param sql
	 */
	private void updateTable(String sql){
		Connection conn = null;
		
		Statement stmt =  null;
		
		ResultSet rs = null;
		try {
			conn = ConnectionHelper.getCurrentContributionConnection("default");
			stmt = conn.createStatement();
		
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			try {
			if(rs != null){	
			  rs.close();
			}
			
			if(stmt != null){
				stmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		
	}
	
}

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
 
package org.gocom.components.coframe.auth.partyauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gocom.components.coframe.auth.queryentity.PartyDataObject;
import org.gocom.components.coframe.rights.gradeauth.GradeAuthService;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

*//**
 * Party授权服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 *//*
public class PartyAuthService implements IPartyAuthService{

	public boolean addOrUpdatePartyAuthBatch(Party[] roleList, Party party) {
		if(roleList == null || roleList.length == 0 || party == null){
			return false;
		}
		return AuthRuntimeManager.getInstance().addOrUpdatePartyAuthBatch(Arrays.asList(roleList), party);
	}

	public boolean delPartyAuthBatch(Party[] roleList, Party party) {
		if(roleList == null || roleList.length == 0 || party == null){
			return false;
		}
		return AuthRuntimeManager.getInstance().delPartyAuthBatch(Arrays.asList(roleList), party, IAuthManagerService.DEL_MODE_SINGLE);
	}

	public List<Party> getAuthorizedRoles(String partyId, String partyType) {
		Party currentParty = PartyRuntimeManager.getInstance().getPartyByPartyID(partyId, partyType);
		List<Party> authorizedRolePartyList = AuthRuntimeManager.getInstance().getDirectRolePartyListByParty(currentParty);
		if(authorizedRolePartyList == null) return new ArrayList<Party>();
		
		// 可管理角色列表
		List<Party> managedRolePartyList = getGradeAuthService().getManagedRoleList();
		for(Party roleParty : authorizedRolePartyList) {
			roleParty.putExtAttribute(IConstants.IS_MANAGED,
					managedRolePartyList.contains(roleParty) ? "true" : "false");
		}
		
		return authorizedRolePartyList;
	}

	public List<Party> getUnauthorizedRoles(String partyId, String partyType) {
		Party currentParty = PartyRuntimeManager.getInstance().getPartyByPartyID(partyId, partyType);
		List<Party> authorizedPartyList = AuthRuntimeManager.getInstance().getDirectRolePartyListByParty(currentParty);
		// 可管理角色列表
		List<Party> managedRolePartyList = getGradeAuthService().getManagedRoleList();
		if(managedRolePartyList == null){
			return new ArrayList<Party>();
		}
		List<Party> unauthorizePartyList = new ArrayList<Party>();
		Set<String> authorizedPartyIdSet = new HashSet<String>();
		if(authorizedPartyList != null){
			for(Party authorizedParty : authorizedPartyList){
				authorizedPartyIdSet.add(authorizedParty.getId());
			}
		}
		// 排除已授权角色
		for(Party party : managedRolePartyList){
			if(!authorizedPartyIdSet.contains(party.getId())){
				unauthorizePartyList.add(party);
			}
		}
		return unauthorizePartyList;
	}
	
	public List<PartyDataObject> convertToDataObjectList(List<Party> partyList) {
		if(partyList == null || partyList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<PartyDataObject> partyDataObjectList = new ArrayList<PartyDataObject>(partyList.size());
		for(Party party : partyList) {
			partyDataObjectList.add(convertToDataObject(party));
		}
		return partyDataObjectList;
	}
	
	public PartyDataObject convertToDataObject(Party party) {
		PartyDataObject partyDataObject = PartyDataObject.FACTORY.create();
		
		if(party == null) return partyDataObject;
		
		partyDataObject.setId(party.getId());
		partyDataObject.setCode(party.getCode());
		partyDataObject.setName(party.getName());
		partyDataObject.setTenantID(party.getTenantID());
		partyDataObject.set(IConstants.IS_MANAGED, party.getExtAttribute(IConstants.IS_MANAGED));
		
		return partyDataObject;
	}
	
	// GradeAuthBean在另一个构件包，所以直接用BeanFactory获取
	private GradeAuthService getGradeAuthService() {
		return BeanFactory.newInstance().getBean(GradeAuthService.SPRING_BEAN_NAME);
	}
}

*/