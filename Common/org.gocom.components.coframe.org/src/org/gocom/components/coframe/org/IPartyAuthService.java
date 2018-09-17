/*
 * Copyright 2013 Primeton.
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

import java.util.List;

import com.primeton.cap.party.Party;

/**
 * Party授权服务
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public interface IPartyAuthService {

	/**
	 * 批量赋予party一个角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @param partyList
	 *            被授权的party列表
	 * @return 是否成功: true 授权成功; false 授权失败
	 */
	boolean savePartyAuthBatch(String roleId, Party[] partyList);

	/**
	 * 批量删除party的角色授权
	 * 
	 * @param roleId
	 *            角色ID
	 * @param partyList
	 *            删除授权的party列表
	 * @return 是否成功: true 授权成功; false: 授权失败
	 */
	boolean deletePartyAuthBatch(String roleId, Party[] partyList);
}
