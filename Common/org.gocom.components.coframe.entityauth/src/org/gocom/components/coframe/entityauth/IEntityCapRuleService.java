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
package org.gocom.components.coframe.entityauth;

import java.util.List;

import org.gocom.components.coframe.entityauth.pojo.Condition;
import org.gocom.components.coframe.entityauth.pojo.RuleAndAuth;

import com.primeton.cap.party.Party;
import com.primeton.cap.spi.auth.rule.Rule;

/**
 * 实体规则操作接口
 *
 * @author lijt (mailto:lijt@primeton.com)
 */
public interface IEntityCapRuleService{

	/**
	 * 添加实体规则
	 * @param ruleName String
	 * @param namespace String
	 * @param ruleExpression Condition[]
	 * @param party Party
	 */
	String addCapRule(String ruleName, String namespace, Condition[] ruleExpression, Party party);

	/**
	 * 删除实体规则
	 * @param capRules CapRule[]
	 */
	String deleteCapRule(Rule[] rules);

	/**
	 * 修改实体规则
	 * @param ruleId String
	 * @param ruleName String
	 * @param ruleExpression Condition[]
	 * @param party Party
	 */
	String updateCapRule(String ruleId, String ruleName, Condition[] ruleExpression, Party party);

	/**
	 * 查询实体规则列表
	 * @param namespace
	 * @param searchType
	 * @param ruleName
	 * @param party
	 * @return
	 */
	RuleAndAuth[] queryCapRules(String namespace,String searchType, String ruleName, Party party);

	
	/**
	 * 获取规则的条件数据
	 * 
	 * @param ruleId 规则ID
	 * @return
	 */
	Condition[] getRuleCondition(String ruleId);
	
	/**
	 * 获取多角色的实体规则
	 * 
	 * @param roleIds
	 * @return
	 */
	List<RuleAndAuth> getCapRules(String roleIds);
}

