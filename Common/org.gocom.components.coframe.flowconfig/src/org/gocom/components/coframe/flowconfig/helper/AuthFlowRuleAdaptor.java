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
package org.gocom.components.coframe.flowconfig.helper;


import java.util.List;

import org.gocom.components.coframe.flowconfig.model.AuthFlowCondition;

import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.OrRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleConditionSet;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition;
import com.primeton.cap.spi.auth.rule.RuleService;

/**
 * AuthFlowConditon 和 Rule 适配器
 * 
 * @author wanghl (mailto:wanghl@primeton.com)
 */

public class AuthFlowRuleAdaptor {

	/**
	 * 将AuthFlowConditon树形数据适配为规则条件的树形数据
	 * 
	 * @param fc 规则条件实体
	 * @return
	 */
	public static IRuleConditionSet authFlowCondtion2RuleConSet(AuthFlowCondition fc, String ruleId) {
		if (fc == null) {
			return null;
		}
		IRuleConditionSet cs = null;
		if (AuthFlowRuleHelper.FLOW_RULE_OPERATOR_AND.equalsIgnoreCase(fc.getOperator())) {
			cs = new AndRuleCondition();
		} else if (AuthFlowRuleHelper.FLOW_RULE_OPERATOR_OR.equalsIgnoreCase(fc.getOperator())) {
			cs = new OrRuleCondition();
		} else {
			// 根必须为and 或者 or
			throw new RuntimeException("AuthFlowCondition 转化为 IRuleConditionSet 的根必须为and或者or运算");
		}
		List<AuthFlowCondition> fcChildren = fc.getChildren();
		if (fcChildren != null) {
			for (AuthFlowCondition fcc : fcChildren) {
				IRuleCondition rc = null;
				if (fcc.getOperator() == null || "".equals(fcc.getOperator().trim())) {
					// 子节点为简单条件
					RuleConditionDefinition rcd = authFlowCondition2Definition(fcc, ruleId);
					rc = RuleService.INSTANCE.newCondition(rcd);
				} else {
					// 递归子节点的and 或者 or
					rc = authFlowCondtion2RuleConSet(fcc, ruleId);
				}
				cs.add(rc);
			}
		}
		return cs;
	}

	/**
	 * 将authFlowConditon适配为规则条件定义
	 * 
	 * @param fc 规则条件实体
	 * @return
	 */
	public static RuleConditionDefinition authFlowCondition2Definition(AuthFlowCondition fc, String ruleId) {
		if (fc == null) {
			return null;
		}
		RuleConditionDefinition cd = new RuleConditionDefinition();
		cd.setId(fc.getName());
		cd.setLeftId(fc.getLeftId());
		cd.setRightId(fc.getRightId());
		cd.setRuleType(AuthFlowRuleHelper.FLOW_RULE_TYPE);
		cd.setRuleId(ruleId);
		cd.setOperator(fc.getCondition());
		cd.setAttachment(fc.getExtension());
		return cd;
	}

	/**
	 * IRuleConditionSet 适配为 AuthFlowCondition
	 * 
	 * @param cs 
	 * @return 
	 */
	public static AuthFlowCondition ruleConSet2AuthFlowCondtion(IRuleConditionSet cs) {
		if (cs == null) {
			return null;
		}
		AuthFlowCondition fc = new AuthFlowCondition();
		IRuleCondition[] csConditions = null;
		if (cs instanceof AndRuleCondition) {
			fc.setOperator(AuthFlowRuleHelper.FLOW_RULE_OPERATOR_AND);
			fc.setName(AuthFlowRuleHelper.FLOW_RULE_NAME_AND);
			csConditions = cs.listConditions();
		} else if (cs instanceof OrRuleCondition) {
			fc.setOperator(AuthFlowRuleHelper.FLOW_RULE_OPERATOR_OR);
			fc.setName(AuthFlowRuleHelper.FLOW_RULE_NAME_OR);
			csConditions = cs.listConditions();
		} else {
			fc = definition2AuthFlowCondition(cs.getDefinition());
		}
		if (csConditions != null) {
			for (IRuleCondition rc : csConditions) {
				// 如果子节点为and 或者or 则递归调用
				if (rc instanceof IRuleConditionSet) {
					fc.addChildren(ruleConSet2AuthFlowCondtion((IRuleConditionSet) rc));
				} else {
					fc.addChildren(definition2AuthFlowCondition(rc.getDefinition()));
				}
			}
		}
		return fc;
	}

	/**
	 * RuleConditionDefinition适配为 AuthFlowCondition
	 * 
	 * @param cd
	 * @return
	 */
	public static AuthFlowCondition definition2AuthFlowCondition(RuleConditionDefinition cd) {
		if (cd == null) {
			return null;
		}
		AuthFlowCondition fc = new AuthFlowCondition();
		fc.setName(cd.getId());
		fc.setLeftId(cd.getLeftId());
		fc.setRightId(cd.getRightId());
		fc.setCondition(cd.getOperator());
		fc.setExtension(cd.getAttachment());
		return fc;
	}
}

