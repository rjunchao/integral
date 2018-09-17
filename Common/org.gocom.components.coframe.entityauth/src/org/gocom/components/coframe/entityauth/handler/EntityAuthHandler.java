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
package org.gocom.components.coframe.entityauth.handler;

import java.io.Serializable;
import java.util.List;

import org.gocom.components.coframe.entityauth.IEntityCapRuleService;
import org.gocom.components.coframe.entityauth.ValuePoolService;
import org.gocom.components.coframe.entityauth.helper.RuleHelper;
import org.gocom.components.coframe.entityauth.pojo.RuleAndAuth;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.IUserObject;
import com.eos.spring.BeanFactory;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.ISystemConstants;
import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.OrRuleCondition;
import com.primeton.cap.impl.auth.rule.condition.RuleConditionSet;
import com.primeton.cap.spi.auth.rule.IRuleCondition;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition;
import com.primeton.das.entity.impl.handler.IEntityHandler;
import com.primeton.das.entity.impl.hibernate.criterion.Criterion;
import commonj.sdo.DataObject;

/**
 * 实体授权拦截器
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */

public class EntityAuthHandler implements IEntityHandler {

	public static String DS_NAME = "default";
	
	public void beforeLoad(String entityName, IDASCriteria criteria) {		
		
		// 某些实体需要调过过滤
		if(!RuleHelper.isEntityFilter(entityName)) {
			return;
		}
		
		IMUODataContext context = DataContextManager.current().getMUODataContext();
		if(context == null) {
			return;
		}
		IUserObject userObject = context.getUserObject();
		if(userObject == null) {
			return;
		}
		if (userObject.getAttributes() == null) {
			return;
		}
		String roleIds = (String)userObject.getAttributes().get("roleList");
		IEntityCapRuleService entityCapRuleService = BeanFactory.newInstance().getBean("entityCapRuleServiceBean");
		List<RuleAndAuth> ruleAndAuthList = entityCapRuleService.getCapRules(roleIds);
		if (ruleAndAuthList == null) {
			return ;
		}
		for (RuleAndAuth ruleAndAuth : ruleAndAuthList) {
			if(entityName.equals(ruleAndAuth.getNamespace())) {
				Criterion criterion = createExpr(entityName, ruleAndAuth.getRootCondition());
				criteria.add(criterion);
			}
		}
	}
	
	/**
	 * 条件转化查询表达规则
	 * @param criteria
	 * @param condition
	 * @return Criterion
	 */
	private Criterion createExpr(String entityName, IRuleCondition condition) {
		if (condition instanceof AndRuleCondition) {
			RuleConditionSet conditionSet = (RuleConditionSet) condition;
			IRuleCondition[] condtions = conditionSet.listConditions();
			Criterion criterion = null;
			for (int i=0; i<condtions.length; i++) {
				if (i == 0) {
					criterion = createExpr(entityName, condtions[i]);
				} else {
					Criterion tmp = createExpr(entityName, condtions[i]);
					criterion = ExpressionHelper.and(criterion, tmp);
				}
			}
			return criterion;
		} else if (condition instanceof OrRuleCondition) {
			RuleConditionSet conditionSet = (RuleConditionSet) condition;
			IRuleCondition[] condtions = conditionSet.listConditions();
			Criterion criterion = null;
			for (int i=0; i<condtions.length; i++) {
				if (i == 0) {
					criterion = createExpr(entityName, condtions[i]);
				} else {
					Criterion tmp = createExpr(entityName, condtions[i]);
					criterion = ExpressionHelper.or(criterion, tmp);
				}
			}
			return criterion;
		} else {
			RuleConditionDefinition definition = condition.getDefinition();
			String leftId = definition.getLeftId();
			String operator = definition.getOperator();
			String rightId = definition.getRightId();
			
			IMUODataContext context = DataContextManager.current().getMUODataContext();
			IUserObject userObject = context.getUserObject();
			
			boolean flag = ValuePoolService.iExistRightValue(rightId);
			
			String value = "";
			if(flag) { // select
				if (rightId.equals("com.primeton.cap.defaultVaule.userName")) {
					value = userObject.getUserName();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.empId")) {
					value = userObject.getUserId();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userId")) {
					value = (String) userObject.get(ISystemConstants.USER_ID);
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userMail")) {
					value = userObject.getUserMail();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.uniqueId")) {
					value = userObject.getUniqueId();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userOrgId")) {
					value = userObject.getUserOrgId();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userOrgName")) {
					value = userObject.getUserOrgName();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userRealName")) {
					value = userObject.getUserRealName();
				} else if (rightId.equals("com.primeton.cap.defaultVaule.userRemoteIP")) {
					value = userObject.getUserRemoteIP();
				}
			} else { // input
				 value = rightId;
			}
			
			if(StringUtil.isNotEmpty(value)) {
				/*
				String namespace = entityName.substring(0, entityName.lastIndexOf("."));
				String pojoName = entityName.substring(entityName.lastIndexOf(".") + 1, entityName.length());
				IEntityService entityService = BeanFactory.newInstance().getBean("entityServiceBean");
				PropertyInfo propertyInfo = entityService.queryPropertyInfo(namespace, pojoName, leftId);
				*/
				Object objValue = null;
				Object [] objValueArr = null;
				
				if("in".equals(operator) || "notin".equals(operator)) {
					String [] valueArray = value.split(",");
					objValueArr = new Object[valueArray.length];
					for(int i=0; i<valueArray.length; i++) {
						objValueArr[i] = String.valueOf(valueArray[i]);
					}
				} else {
					objValue = String.valueOf(value);
				}
				
				if ("=".equals(operator)) {
					return ExpressionHelper.eq(leftId, objValue);
				} else if("<".equals(operator)){
					return ExpressionHelper.lt(leftId, objValue);
				} else if("<=".equals(operator)){
					return ExpressionHelper.le(leftId, objValue);
				} else if(">".equals(operator)){
					return ExpressionHelper.gt(leftId, objValue);
				} else if(">=".equals(operator)){
					return ExpressionHelper.ge(leftId, objValue);
				} else if("<>".equals(operator)){
					return ExpressionHelper.ne(leftId, objValue);
				} else if("in".equals(operator)){
					return ExpressionHelper.in(leftId, objValueArr);
				} else if("notin".equals(operator)){
					return ExpressionHelper.not(ExpressionHelper.in(leftId, objValueArr));
				} else if("null".equals(operator)){
					return ExpressionHelper.isNull(leftId);
				} else if("notnull".equals(operator)){
					return ExpressionHelper.isNotNull(leftId);
				} else if("like_end".equals(operator)){
					return ExpressionHelper.like(leftId, "%" + objValue);
				} else if("like_start".equals(operator)){
					return ExpressionHelper.like(leftId, objValue + "%");
				} else if("like_none".equals(operator)){
					return ExpressionHelper.not(ExpressionHelper.like(leftId, "%" + objValue + "%"));
				} else if("like_all".equals(operator)){
					return ExpressionHelper.like(leftId, "%" + objValue + "%");
				}
			}
			
		}
		throw new IllegalArgumentException("Param condition is exception");
	}
	
	public void afterDelete(DataObject entity, Serializable primaryKey, String[] propertyNames, Object[] values) {
	}

	public void beforeSave(DataObject entity, Serializable primaryKey, String[] propertyNames, Object[] values) {
	}

	public void beforeUpdate(DataObject entity, Serializable primaryKey, String[] propertyNames, Object[] previousValues, Object[] currentValues) {
	}
	

	public void afterLoad(DataObject entity, Serializable primaryKey, String[] propertyNames, Object[] values) {

	}

}