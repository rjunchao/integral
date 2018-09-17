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
import org.gocom.components.coframe.auth.party.manager.DefaultUserManager;
import org.gocom.components.coframe.auth.queryentity.QueryUserByRole;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 角色和用户的关系描述(角色为父，用户为子)
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class RoleUserRefDataService implements IPartyTypeRefDataService {
//	角色Bean
	private DefaultRoleManager roleBean = null;
	//用户Bean
	private DefaultUserManager userBean = null;
	/**
	 * 构造方法
	 *
	 */
	public RoleUserRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		this.roleBean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
		this.userBean = beanFactory.getBean(DefaultUserManager.SPRING_BEAN_NAME);
	}

	/**
	 * 根据角色获取用户
	 * @param parentPartyID 角色ID
	 * @param tenantID 租户ID
	 * @return 用户
	 */
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据角色获取用户
		List<Party> returnList = new ArrayList<Party>();
		QueryUserByRole[] queryUserByRoles = this.userBean.queryCapUserListByRoleId(parentPartyID, tenantID);
		if (queryUserByRoles != null) {
			for (QueryUserByRole queryUserByRole : queryUserByRoles) {
				Party party = new Party(IConstants.USER_PARTY_TYPE_ID, queryUserByRole.getUserId(), null, queryUserByRole.getUserName(), queryUserByRole
						.getTenantId());
				party.putExtAttribute(IConstants.EMAIL, queryUserByRole.getEmail());
				returnList.add(party);
			}
		}
		return returnList;
	}

	/**
	 * 根据用户获取角色
	 * @param childPartyID 用户ID
	 * @param tenantID 租户ID
	 * @return 角色
	 */
	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据用户获取角色
		List<Party> returnList = new ArrayList<Party>();
		CapRole[] roleArray = this.roleBean.getCapRoleListByAuthParty(IConstants.USER_PARTY_TYPE_ID, childPartyID, tenantID);
		if (roleArray != null) {
			for (CapRole role : roleArray) {
				returnList.add(new Party(IConstants.ROLE_PARTY_TYPE_ID, String.valueOf(role.getRoleId()), role.getRoleCode(), role.getRoleName(), role
						.getTenantId()));
			}
		}
		return returnList;
	}

}