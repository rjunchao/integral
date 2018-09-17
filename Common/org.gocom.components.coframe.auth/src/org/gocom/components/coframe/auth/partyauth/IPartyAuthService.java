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

import java.util.List;

import com.primeton.cap.party.Party;

/**
 * party授权服务
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public interface IPartyAuthService {
	
	/**
	 * 获取已授权的角色列表
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	List<Party> getAuthorizedRoles(String partyId, String partyType);
	
	/**
	 * 获取未授权的角色列表
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	List<Party> getUnauthorizedRoles(String partyId, String partyType);
	
	/**
	 * 添加或修改某个Party的角色映射
	 * @param roleList 角色列表
	 * @param party 
	 * @return
	 */
	boolean  addOrUpdatePartyAuthBatch(Party[] roleList, Party currentParty);
	
	/**
	 * 删除某个Party的角色列表
	 * @param roleList 角色列表
	 * @param party
	 * @return
	 */
	boolean delPartyAuthBatch(Party[] roleList, Party currentParty);
	
}
