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

import org.gocom.components.coframe.auth.party.manager.DefaultUserManager;
import org.gocom.components.coframe.rights.dataset.CapUser;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.Party;

/**
 * 用户PartyType实现
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class UserPartyTypeDataService implements IPartyTypeDataService {
	// 默认用户管理Bean
	private DefaultUserManager bean = null;

	/**
	 * 构造方法
	 * 
	 */
	public UserPartyTypeDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		this.bean = beanFactory.getBean(DefaultUserManager.SPRING_BEAN_NAME);
	}

	/**
	 * 获取所有参与者列表
	 * 
	 * @param tenantID 租户ID
	 * @return 参与者列表
	 */
	public List<Party> getAllPartyList(String tenantID) {
		CapUser[] userArray = this.bean.getAllUserList(tenantID);
		List<Party> returnList = new ArrayList<Party>();
		if (userArray != null) {
			for (CapUser user : userArray) {
				returnList.add(adapt(user));
			}
		}
		return returnList;
	}

	/**
	 * 根据参与者ID获取参与者对象
	 * 
	 * @param partyID 参与者ID
	 * @param tenantID 租户ID
	 * @return 参与者对象
	 * 
	 */
	public Party getPartyByPartyID(String partyID, String tenantID) {
		CapUser user = this.bean.getCapUserByUserId(partyID, tenantID);
		if (user != null) {
			return adapt(user);
		}
		return null;
	}

	/**
	 * 获取根参与者列表
	 * 
	 * @param tenantID 租户ID
	 * @return 根参与者列表
	 */
	public List<Party> getRootPartyList(String tenantID) {
		return this.getAllPartyList(tenantID);
	}

	/**
	 * 改变用户类型,使其为参与者类型
	 * 
	 * @param capUser cap用户
	 * @return 参与者
	 */
	private Party adapt(CapUser capUser) {
		Party party = new Party(IConstants.USER_PARTY_TYPE_ID, capUser.getUserId(), null, capUser.getUserName(), capUser.getTenantId());
		party.putExtAttribute(IConstants.EMAIL, capUser.getEmail());
		return party;
	}

}