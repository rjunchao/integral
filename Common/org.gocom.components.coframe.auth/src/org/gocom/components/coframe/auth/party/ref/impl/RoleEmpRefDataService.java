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
package org.gocom.components.coframe.auth.party.ref.impl;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.auth.party.manager.DefaultRoleManager;
import org.gocom.components.coframe.auth.queryentity.QueryEmpByRole;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 角色人员关系描述(角色为父，人员为子)
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class RoleEmpRefDataService implements IPartyTypeRefDataService {

	// 角色Bean
	private DefaultRoleManager roleBean = null;

	public RoleEmpRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		roleBean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
	}

	/**
	 * 根据角色获取人员
	 * @param parentPartyID 角色ID
	 * @param tenantID 租户ID
	 * @return 人员
	 */
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据角色获取人员
		List<Party> returnList = new ArrayList<Party>();
		QueryEmpByRole[] queryEmpByRoles = roleBean.queryEmpsByRoleId(parentPartyID, tenantID);
		if (queryEmpByRoles != null) {
			for (QueryEmpByRole queryEmpByRole : queryEmpByRoles) {
				returnList.add(new Party(IConstants.EMP_PARTY_TYPE_ID, String.valueOf(queryEmpByRole.getEmpid()), queryEmpByRole.getEmpcode(), queryEmpByRole
						.getEmpname(), queryEmpByRole.getTenantId()));
			}
		}
		return returnList;
	}

	/**
	 * 根据人员获取角色
	 * @param childPartyID 人员ID
	 * @param tenantID 租户ID
	 * @return 角色
	 */
	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据人员获取角色
		List<Party> returnList = new ArrayList<Party>();
		CapRole[] roleArray = roleBean.getCapRoleListByAuthParty(IConstants.EMP_PARTY_TYPE_ID, childPartyID, tenantID);
		if (roleArray != null) {
			for (CapRole role : roleArray) {
				returnList.add(new Party(IConstants.ROLE_PARTY_TYPE_ID, String.valueOf(role.getRoleId()), role.getRoleCode(), role.getRoleName(), role
						.getTenantId()));
			}
		}
		return returnList;
	}
}
