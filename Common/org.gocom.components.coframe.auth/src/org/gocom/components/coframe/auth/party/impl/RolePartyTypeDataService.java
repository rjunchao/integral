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
package org.gocom.components.coframe.auth.party.impl;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.auth.party.manager.DefaultRoleManager;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.Party;

/**
 * 角色PartyType数据加载默认实现
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class RolePartyTypeDataService implements IPartyTypeDataService {
	
	private DefaultRoleManager bean;

	public RolePartyTypeDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
	}

	/**
	 * 获取所有参与者
	 * 
	 * @param tenantID 租户ID
	 * @return 参与者列表
	 */
	public List<Party> getAllPartyList(String tenantID) {
		CapRole[] roles = bean.getAllRoleListByTenant(tenantID);
		List<Party> returnList = new ArrayList<Party>();
		if (roles != null) {
			for (CapRole role : roles) {
				Party party = new Party(IConstants.ROLE_PARTY_TYPE_ID, String.valueOf(role.getRoleId()), role.getRoleCode(), role.getRoleName(), tenantID);
				returnList.add(party);
			}
		}
		return returnList;
	}

	/**
	 * 根据参与者ID获取参与者
	 * 
	 * @param partyID 参与者ID
	 * @param tenantID 租户ID
	 * @return 参与者
	 */
	public Party getPartyByPartyID(String partyID, String tenantID) {
		CapRole role = bean.getRoleByRoleIDAndTenant(partyID, tenantID);
		if (role == null) {
			return null;
		}
		return new Party(IConstants.ROLE_PARTY_TYPE_ID, String.valueOf(role.getRoleId()), role.getRoleCode(), role.getRoleName(), tenantID);
	}

	/**
	 * 获取根参与者对象
	 * 
	 * @param tenantID 租户ID
	 * @return 参与者对象
	 */
	public List<Party> getRootPartyList(String tenantID) {
		return this.getAllPartyList(tenantID);
	}
}