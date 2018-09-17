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
package org.gocom.components.coframe.org;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.org.dataset.OrgPartyDataObject;
import org.gocom.components.coframe.rights.gradeauth.GradeAuthService;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * 分级授权机构服务类
 * 
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class GradeAuthOrgService {

	/**
	 * 获取可管理机构列表
	 * @return
	 */
	public List<Party> getManagedOrgList() {
		return getGradeAuthService().getManagedOrgList();
	}
	
	public List<Party> getAuthorizedRoles(String partyId, String partyType) {
		List<Party> empManagedRoleList = getEmpManagedRoleList();
		if(empManagedRoleList == null) return new ArrayList<Party>();
		
		// 可管理角色列表
		List<Party> managedRolePartyList = getManagedRoleList(partyId, partyType);
		for(Party roleParty : empManagedRoleList) {
			roleParty.putExtAttribute(IConstants.IS_MANAGED,
					managedRolePartyList.contains(roleParty) ? "true" : "false");
		}
		
		return empManagedRoleList;
	}
	
	/**
	 * 获取当前用户对应员工的可管理角色列表
	 * @return
	 */
	public List<Party> getEmpManagedRoleList() {
		return getGradeAuthService().getManagedRoleList();
	}
	
	/**
	 * 获取当前用户对应员工的可管理角色列表
	 * @param empid
	 * @return
	 */
	public List<Party> getEmpManagedRoleList(String empid) {
		return getManagedRoleList(empid, IConstants.EMP_PARTY_TYPE_ID);
	}
	
	/**
	 * 根据partyId和partyTypeId获取可管理角色列表
	 * @param partyId
	 * @param partyTypeId
	 * @return
	 */
	public List<Party> getManagedRoleList(String partyId, String partyTypeId) {
		List<Party> results = new ArrayList<Party>();
		
		Party empParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				partyId, partyTypeId);
		if(empParty != null) {
			String roleIdAndNames = empParty.getExtAttribute(IConstants.MANAGED_ROLES);
			if (roleIdAndNames != null) {
				String[] roleIdAndNameArray = StringUtils.split(roleIdAndNames, ",");
				if (roleIdAndNameArray != null) {
					for (String roleIdAndName : roleIdAndNameArray) {
						if (roleIdAndName != null) {
							Party roleParty = PartyRuntimeManager.getInstance()
							.getPartyByPartyID(StringUtils.split(roleIdAndName, ":")[0],
									IConstants.ROLE_PARTY_TYPE_ID);
							if(roleParty != null) {
								results.add(roleParty);
							}
						}
					}
				}
			}
		}
		
		return results;
	}
	
	public List<OrgPartyDataObject> convertToDataObjectList(List<Party> partyList) {
		if(partyList == null || partyList.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<OrgPartyDataObject> partyDataObjectList = new ArrayList<OrgPartyDataObject>(partyList.size());
		for(Party party : partyList) {
			partyDataObjectList.add(convertToDataObject(party));
		}
		return partyDataObjectList;
	}
	
	public OrgPartyDataObject convertToDataObject(Party party) {
		OrgPartyDataObject partyDataObject = OrgPartyDataObject.FACTORY.create();
		
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
