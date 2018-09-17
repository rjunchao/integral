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


import java.util.List;

import org.gocom.components.coframe.entityauth.pojo.Condition;

import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.OrRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleConditionSet;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition;

/**
 * Rule 适配器
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */

public class RuleAdaptor {

	/**
	 * 将RuleConditon树形数据适配为规则条件的树形数据
	 * 
	 * @param fc 规则条件实体
	 * @return
	 */
	public static IRuleConditionSet condtion2RuleConSet(Condition fc, String ruleId, String ruleType) {
		if (fc == null) {
			return null;
		}
		IRuleConditionSet cs = null;
		if (RuleHelper.RULE_OPERATOR_AND.equalsIgnoreCase(fc.getOperator())) {
			cs = new AndRuleCondition();
		} else if (RuleHelper.RULE_OPERATOR_OR.equalsIgnoreCase(fc.getOperator())) {
			cs = new OrRuleCondition();
		} else {
			// 根必须为and 或者 or
			throw new RuntimeException("Condition 转化为 IRuleConditionSet 的根必须为and或者or运算");
		}
		List<Condition> fcChildren = fc.getChildren();
		if (fcChildren != null) {
			for (Condition fcc : fcChildren) {
				IRuleCondition rc = null;
				if (fcc.getOperator() == null || "".equals(fcc.getOperator().trim())) {
					// 子节点为简单条件
					RuleConditionDefinition rcd = condition2Definition(fcc, ruleId, ruleType);
					rc = DefaultRuleCondition.INSTANCE.newCondition(rcd);
				} else {
					// 递归子节点的and 或者 or
					rc = condtion2RuleConSet(fcc, ruleId, ruleType);
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
	public static RuleConditionDefinition condition2Definition(Condition fc, String ruleId, String ruleType) {
		if (fc == null) {
			return null;
		}
		RuleConditionDefinition cd = new RuleConditionDefinition();
		cd.setId(fc.getName());
		cd.setLeftId(fc.getLeftId());
		cd.setRightId(fc.getRightId());
		cd.setRuleType(ruleType);
		cd.setRuleId(ruleId);
		cd.setOperator(fc.getCondition());
		cd.setAttachment(fc.getExtension());
		return cd;
	}

	/**
	 * IRuleConditionSet 适配为 Condition
	 * 
	 * @param cs 
	 * @return 
	 */
	public static Condition ruleConSet2Condtion(IRuleConditionSet cs) {
		if (cs == null) {
			return null;
		}
		Condition fc = new Condition();
		IRuleCondition[] csConditions = null;
		if (cs instanceof AndRuleCondition) {
			fc.setOperator(RuleHelper.RULE_OPERATOR_AND);
			fc.setName(RuleHelper.RULE_NAME_AND);
			csConditions = cs.listConditions();
		} else if (cs instanceof OrRuleCondition) {
			fc.setOperator(RuleHelper.RULE_OPERATOR_OR);
			fc.setName(RuleHelper.RULE_NAME_OR);
			csConditions = cs.listConditions();
		} else {
			fc = definition2Condition(cs.getDefinition());
		}
		if (csConditions != null) {
			for (IRuleCondition rc : csConditions) {
				// 如果子节点为and 或者or 则递归调用
				if (rc instanceof IRuleConditionSet) {
					fc.addChildren(ruleConSet2Condtion((IRuleConditionSet) rc));
				} else {
					fc.addChildren(definition2Condition(rc.getDefinition()));
				}
			}
		}
		return fc;
	}

	/**
	 * RuleConditionDefinition适配为 Condition
	 * 
	 * @param cd
	 * @return
	 */
	public static Condition definition2Condition(RuleConditionDefinition cd) {
		if (cd == null) {
			return null;
		}
		Condition fc = new Condition();
		fc.setName(cd.getId());
		fc.setLeftId(cd.getLeftId());
		fc.setRightId(cd.getRightId());
		fc.setCondition(cd.getOperator());
		fc.setExtension(cd.getAttachment());
		return fc;
	}
}

