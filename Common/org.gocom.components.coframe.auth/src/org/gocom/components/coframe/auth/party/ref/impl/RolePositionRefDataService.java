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
import org.gocom.components.coframe.auth.queryentity.QueryPositionByRole;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 角色岗位关系描述(角色为父，岗位为子)
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class RolePositionRefDataService implements IPartyTypeRefDataService {

	private DefaultRoleManager roleBean = null;

	public RolePositionRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		roleBean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
	}

	/**
	 * 根据角色获取岗位列表
	 * @param parentPartyID 角色ID
	 * @param tenantID 租户ID
	 * @return 岗位列表
	 */
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		List<Party> returnList = new ArrayList<Party>();
		QueryPositionByRole[] queryPositionByRoles = roleBean.queryPositionsByRoleId(parentPartyID, tenantID);
		if (queryPositionByRoles != null) {
			for (QueryPositionByRole queryPositionByRole : queryPositionByRoles) {
				returnList.add(new Party(IConstants.POSITION_PARTY_TYPE_ID, String.valueOf(queryPositionByRole.getPositionid()), 
						queryPositionByRole.getPosicode(), queryPositionByRole.getPosiname(), queryPositionByRole.getTenantId()));
			}
		}
		return returnList;
	}

	/**
	 * 根据岗位获取角色列表
	 * @param childPartyID 岗位ID
	 * @param tenantID 租户ID
	 * @return 角色列表
	 */
	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		List<Party> returnList = new ArrayList<Party>();
		CapRole[] roleArray = roleBean.getCapRoleListByAuthParty(IConstants.POSITION_PARTY_TYPE_ID, childPartyID, tenantID);
		if (roleArray != null) {
			for (CapRole role : roleArray) {
				returnList.add(new Party(IConstants.ROLE_PARTY_TYPE_ID, String.valueOf(role.getRoleId()), role.getRoleCode(), role.getRoleName(), role
						.getTenantId()));
			}
		}
		return returnList;
	}
}
