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
package org.gocom.components.coframe.entityauth;

import com.primeton.cap.party.Party;

/**
 * Entity授权服务
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */
public interface IEntityAuthService {

	/**
	 * 批量赋予Entity Rule一个角色
	 * 
	 * @param party
	 *            party
	 * @param authRuleIds
	 *            被授权的ruleId列表
	 * @param delRuleIds
	 *            删除授权的ruleId列表
	 * @return 是否成功: true 授权成功; false 授权失败
	 */
	boolean authBatch(Party party, String[] authRuleIds, String[] delRuleIds);

}
