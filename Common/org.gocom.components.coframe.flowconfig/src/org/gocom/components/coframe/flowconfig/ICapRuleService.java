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
package org.gocom.components.coframe.flowconfig;

import org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;

/**
 * 流程规则操作接口
 *
 * @author wanghl (mailto:wanghl@primeton.com)
 */
public interface ICapRuleService{

	/**
	 *
	 * @param capRule CapRule
	 */
	void addCapRule(CapRule capRule);

	/**
	 *
	 * @param capRules CapRule[]
	 */
	void deleteCapRule(CapRule[] capRules);

	/**
	 *
	 * @param capRule CapRule[]
	 */
	void getCapRule(CapRule capRule);

	/**
	 * 查询规则
	 * @param criteria
	 * @param begin
	 * @param length
	 * @param sortField
	 * @param sortOrder
	 * @return
	 */
	CapRule[]  queryCapRules(CriteriaType criteria,PageCond pageCond, String sortField, String sortOrder);

	/**
	 *
	 * @param capRule CapRule[]
	 */
	void updateCapRule(CapRule capRule);
	
	/**
	 * 计算规则数
	 * @param criteria
	 * @return
	 */
	int countCapRules(CriteriaType criteria);

}

