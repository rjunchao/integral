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
package org.gocom.components.coframe.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.auth.service.impl.DefaultAuthPartyService;

import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class AuthPartyRuntimeService {

	private static final Map<String, IAuthPartyService> SERVICES = new HashMap<String, IAuthPartyService>();

	/**
	 * 增加一种party类型的处理服务
	 * 
	 * @param key
	 * @param service
	 */
	public static void addAuthPartyService(String key, IAuthPartyService service) {
		SERVICES.put(key, service);
	}

	public static IAuthPartyService getAuthPartyService(String key) {
		IAuthPartyService service = SERVICES.get(key);
		if (service == null) {
			service = new DefaultAuthPartyService(key);
		}
		return service;
	}

	/**
	 * 获取可继承权限的party的列表,根据该类型展开权限计算层级，譬如用户关联操作员，岗位关联职位，机构无关联
	 * 
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public static List<Party> getAssociateAuthPartyList(String partyId,
			String partyType) {
		return getAuthPartyService(partyType)
				.getAssociateAuthPartyList(partyId);
	}

	/**
	 * 获取可继承权限的party的列表,根据该类型展开权限计算层级，譬如用户关联操作员，岗位关联职位，机构无关联
	 * 
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public static List<Party> getAssociateAuthRoles(String partyId,
			String partyType) {
		return getAuthPartyService(partyType).getAssociateAuthRoles(partyId);
	}

	/**
	 * 获取参与者的授权模型，包括参与者关联的所有party以及所有角色
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public static PartyAuthModel getPartyModel(String partyId, String partyType) {
		PartyAuthModel authModel = new PartyAuthModel();
		initAssociateAuthRoleList(partyId, partyType, authModel);
		return authModel;
	}

	private static void initAssociateAuthRoleList(String partyId,
			String partyType, PartyAuthModel authModel) {
		List<Party> directPartyList = getAssociateAuthPartyList(partyId,
				partyType);
		Party currParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				partyId, partyType);
		if (currParty != null) {
			authModel.addParty(currParty);
			for (Party party : directPartyList) {
				initAssociateAuthRoleList(party.getId(),
						party.getPartyTypeID(), authModel);
			}

			List<Party> directAuthRoleList = getAssociateAuthRoles(partyId,
					partyType);
			for (Party party : directAuthRoleList) {
				authModel.addRoleparty(party);
			}
		}

	}

}
