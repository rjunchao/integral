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
package org.gocom.components.coframe.auth.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.auth.service.IAuthPartyService;

import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class DefaultAuthPartyService implements IAuthPartyService {

	private String partyType;

	public DefaultAuthPartyService(String partyType) {
		this.partyType = partyType;
	}

	/**
	 * 查询直接关联的类型
	 */
	public List<Party> getAssociateAuthPartyList(String partyId) {
		String[] parentPartyTypes = this.getParentPartyTypes();
		if (parentPartyTypes == null || parentPartyTypes.length == 0) {
			return new ArrayList<Party>();

		}
		List<Party> parties = new ArrayList<Party>();
		Map<String, List<Party>> map = PartyRuntimeManager.getInstance()
				.getDirectAssociateParentPartyList(partyId, this.partyType,
						parentPartyTypes);
		if (map != null) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String partyTypeID = it.next();
				List<Party> partyList = map.get(partyTypeID);
				parties.addAll(partyList);
			}

		}
		return parties;
	}
	/**
	 * 子类一般重载该方法，指定查询哪些直接关联的父类型
	 * @return
	 */
	public String[] getParentPartyTypes() {
		return new String[0];
	}

	public List<Party> getAssociateAuthRoles(String partyId) {
		Party party = new Party(this.partyType, partyId, partyId, partyId);
		List<Party> partyList = AuthRuntimeManager.getInstance()
				.getDirectRolePartyListByParty(party);
		if (partyList == null) {
			partyList = new ArrayList<Party>();
		}
		return partyList;
	}

}
