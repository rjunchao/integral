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
package org.gocom.components.coframe.entityauth.helper;

import org.gocom.components.coframe.entityauth.pojo.Condition;

import com.primeton.cap.impl.auth.rule.ExceptionMessage;
import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.OrRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition.AndRuleConditionDefinition;
import com.primeton.cap.impl.auth.rule.condition.OrRuleCondition.OrRuleConditionDefinition;
import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition;

/**
 * 实体规则表达式
 * @author lijt (mailto:lijt@primeton.com)
 */
public class DefaultRuleCondition extends AbstractRuleCondition {

	private static final long serialVersionUID = 990099991L;
	
	public static final DefaultRuleCondition INSTANCE = new DefaultRuleCondition();

	private DefaultRuleCondition() {
	}

	public boolean isMatch(IRuleContext arg0) {
		return true;
	}
	
	/**
	 * 创建规则条件.<br>
	 * 
	 * @param definition
	 *            规则条件定义
	 * @return 规则条件
	 */
	public IRuleCondition newCondition(RuleConditionDefinition definition) {
		try {
			if (definition == null) {
				throw new RuntimeException(ExceptionMessage.NULL_ARG.fmt("definition"));
			}
			IRuleCondition condition = null;
			if (definition instanceof OrRuleConditionDefinition) {
				condition = newOrCondition();
				condition.setDefinition(definition);
			}
			if (condition == null) {
				if (definition instanceof AndRuleConditionDefinition) {
					condition = newAndCondition();
					condition.setDefinition(definition);
				}
			}
			if (condition == null) {
				condition = new Condition();
				condition.setDefinition(definition);
			}
			return condition;
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	/**
	 * 创建or条件.<br>
	 * 
	 * @return or条件
	 */
	public IRuleCondition newOrCondition() {
		return new OrRuleCondition();
	}

	/**
	 * 创建and条件.<br>
	 * 
	 * @return and条件
	 */
	public IRuleCondition newAndCondition() {
		return new AndRuleCondition();
	}

}
