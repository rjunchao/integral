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
package org.gocom.components.coframe.entityauth.helper;


import java.util.UUID;

import org.gocom.components.coframe.entityauth.handler.EntityAuthContributionHandler;

/**
 * 规则工具类
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */

public class RuleHelper {
	
	// 常量and
	public static final String RULE_OPERATOR_AND = "and";

	// 常量or
	public static final String RULE_OPERATOR_OR = "or";

	// 常量"并且"
	public static final String RULE_NAME_AND = "并且";

	// 常量"或者"
	public static final String RULE_NAME_OR = "或者";

	/**
	 * 获取随机的roleId
	 * 
	 * @return 随机的roleId
	 */
	public static String getRandomId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 实体是否过滤
	 * @return
	 */
	public static boolean isEntityFilter(String entityName) {
		if(EntityAuthContributionHandler.needFilterRuleList != null) {
			for(String needFilterRule : EntityAuthContributionHandler.needFilterRuleList) {
				if (entityName.equals(needFilterRule)) {
					return true;
				}
			}
		} 
		
		if(EntityAuthContributionHandler.skipFilterRuleList != null) {
			for(String skipFilterRule : EntityAuthContributionHandler.skipFilterRuleList) {
				if (entityName.startsWith(skipFilterRule)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
}
