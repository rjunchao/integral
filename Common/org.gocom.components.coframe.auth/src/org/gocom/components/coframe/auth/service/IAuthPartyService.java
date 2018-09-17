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

import java.util.List;

import com.primeton.cap.party.Party;

/**
 * TODO 定义授权和party的接口类，根据用户ID获取所有的party及party关联的角色
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public interface IAuthPartyService {

	/**
	 * 获取可继承权限的party的列表,根据该类型展开权限计算层级，譬如用户关联操作员，岗位关联职位，机构无关联
	 * 
	 * @param partyId
	 * @return
	 */
	List<Party> getAssociateAuthPartyList(String partyId);

	/**
	 * 获取直接授权的角色列表
	 * 
	 * @param partyId
	 * @return
	 */
	List<Party> getAssociateAuthRoles(String partyId);
}
