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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gocom.components.coframe.entityauth.helper.RuleAdaptor;
import org.gocom.components.coframe.entityauth.helper.RuleHelper;
import org.gocom.components.coframe.entityauth.pojo.Condition;
import org.gocom.components.coframe.entityauth.pojo.ConstantPool;
import org.gocom.components.coframe.entityauth.pojo.RuleAndAuth;

import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.spi.auth.rule.IRuleConditionSet;
import com.primeton.cap.spi.auth.rule.Rule;
import com.primeton.cap.spi.auth.rule.RuleService;

/**
 * 实体规则操作类
 *
 * @author lijt (mailto:lijt@primeton.com)
 */
public class EntityCapRuleService implements IEntityCapRuleService{
	
	public static final String ADD_RULE = "add_rule";
	public static final String UPDATE_RULE = "update_rule";
	public static final String SUCCESS = "success";
	public static final String EXIST = "exist";
	
	/* （non-Javadoc）
	 * 
	 * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#addCapRule(java.lang.String, java.lang.String, org.gocom.components.coframe.entityauth.pojo.Condition[], com.primeton.cap.party.Party)
	 */
	public String addCapRule(String ruleName, String namespace, Condition[] ruleExpression, Party party){
		if(isUniqueRuleName(null, ruleName, ADD_RULE)) {
			Rule rule = new Rule();
			rule.setId(RuleHelper.getRandomId());
			rule.setName(ruleName);
			rule.setNamespace(namespace);
			rule.setType(ConstantPool.RULE_TYPE);
			rule.setTenantId(TenantManager.getCurrentTenantID());

			IRuleConditionSet cs = RuleAdaptor.condtion2RuleConSet(ruleExpression[0], rule.getId(), ConstantPool.RULE_TYPE);
			rule.setRootCondition(cs);
			RuleService.INSTANCE.addRule(rule);
			
			return SUCCESS;
		} else {
			return EXIST;
		}
	}

	/* （non-Javadoc）
	 * 
	 * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#deleteCapRule(com.primeton.cap.spi.auth.rule.Rule[])
	 */
	public String deleteCapRule(Rule[] rules){
		List<Party> partyList = AuthRuntimeManager.getInstance().getAllRolePartyList();		
		if(rules != null) {
			for(Rule rule : rules) {
				AuthResource authResource = new AuthResource();
				authResource.setResourceID(rule.getId());
				authResource.setResourceType(ConstantPool.RES_TYPE);
				// 删除授权
				if(partyList != null && partyList.size() !=0) {
					for(Party party : partyList) {
						AuthRuntimeManager.getInstance().delAuthRes(party, authResource, IAuthManagerService.DEL_MODE_SINGLE);
					}
				}
				
				// 删除规则
				RuleService.INSTANCE.deleteRule(rule.getId(), rule.getType());
			}
		}
		return SUCCESS;
	}
	
	/* （non-Javadoc）
	 * 
	 * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#queryCapRules(java.lang.String, java.lang.String, java.lang.String, com.primeton.cap.party.Party)
	 */
	public RuleAndAuth[]  queryCapRules(String namespace,String searchType, String ruleName, Party party){
		
		// 过滤namespace
		Rule[] namespaceRules = null;
		if(namespace == null || "".equals(namespace)) {
			namespaceRules = RuleService.INSTANCE.listRulesByType(ConstantPool.RULE_TYPE);
		} else {
			namespaceRules = RuleService.INSTANCE.listRulesByNamespace(ConstantPool.RULE_TYPE, namespace);
		}
		
		// 过滤ruleName
		List<Rule> ruleNameRules = null;
		if(ruleName != null && !"".equals(ruleName.trim())) {
			if(namespaceRules != null) {
				ruleNameRules = new ArrayList<Rule>();
				for(Rule rule : namespaceRules) {
					if(rule.getName()!=null && rule.getName().indexOf(ruleName)!=-1) {
						ruleNameRules.add(rule);
					}
				}
			}
		} else {
			if(namespaceRules != null) {
				ruleNameRules = new ArrayList<Rule>();
				for(Rule rule : namespaceRules) {
					ruleNameRules.add(rule);
				}
			}
		}
		
		// 获取当前party权限
		List<AuthResource> authResourceList = AuthRuntimeManager.getInstance().getAuthResListByRole(party, ConstantPool.RES_TYPE);
		
		// 为实体规则拼装权限
		RuleAndAuth [] allCapRuleAuths = null;
		List<RuleAndAuth> noAuthCapRuleAuthList = null;
		List<RuleAndAuth> yesAuthCapRuleAuthList = null;
		if(ruleNameRules != null && ruleNameRules.size() !=0 ) {
			allCapRuleAuths = new RuleAndAuth[ruleNameRules.size()];
			noAuthCapRuleAuthList = new ArrayList<RuleAndAuth>();
			yesAuthCapRuleAuthList = new ArrayList<RuleAndAuth>();
			for(int i=0; i < ruleNameRules.size(); i++) {
				Rule rule = ruleNameRules.get(i);
				RuleAndAuth ruleAndAuth = new RuleAndAuth();
				ruleAndAuth.setRule(rule);
				boolean flag = false;
				for(AuthResource authResource : authResourceList) {
					if(rule.getId().equals(authResource.getResourceID())) {
						flag = true;
						break;
					}
				}
				if(flag == true) {
					ruleAndAuth.setAuth("1");
					yesAuthCapRuleAuthList.add(ruleAndAuth);
				} else {
					ruleAndAuth.setAuth("0");
					noAuthCapRuleAuthList.add(ruleAndAuth);
				}
				allCapRuleAuths[i] = ruleAndAuth;
			}
		}
		
		if("2".equals(searchType)) { 
			// 已授权
			if(yesAuthCapRuleAuthList != null && yesAuthCapRuleAuthList.size()!=0) {
				return yesAuthCapRuleAuthList.toArray(new RuleAndAuth[yesAuthCapRuleAuthList.size()]);
			}
			return null;
		} else if("3".equals(searchType)) { 
			// 未授权
			if(noAuthCapRuleAuthList != null && noAuthCapRuleAuthList.size()!=0) {
				return noAuthCapRuleAuthList.toArray(new RuleAndAuth[noAuthCapRuleAuthList.size()]);
			}
			return null;
		} else { 
			// 全部
			return allCapRuleAuths;
		}
	}
	
    /* （non-Javadoc）
     * 
     * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#updateCapRule(java.lang.String, java.lang.String, org.gocom.components.coframe.entityauth.pojo.Condition[], com.primeton.cap.party.Party)
     */
    public String updateCapRule(String ruleId, String ruleName, Condition[] ruleExpression, Party party){
    	
    	if(isUniqueRuleName(ruleId, ruleName, UPDATE_RULE)) {
    		Rule[] rules = RuleService.INSTANCE.listRulesByType(ConstantPool.RULE_TYPE);
    		if(rules != null){
    			for(int i=0; i < rules.length; i++) {
    				if(ruleId.equals(rules[i].getId())) {
    					Rule rule = rules[i];
    					rule.setName(ruleName);
    					rule.setType(ConstantPool.RULE_TYPE);

    					IRuleConditionSet cs = RuleAdaptor.condtion2RuleConSet(ruleExpression[0], rule.getId(), ConstantPool.RULE_TYPE);
    					rule.setRootCondition(cs);
    					
    					RuleService.INSTANCE.updateRule(rule);
    				}
    			}
    		}
        	return SUCCESS;
    	} else {
    		return EXIST;
    	}
    }
    
	/* （non-Javadoc）
	 * 
	 * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#getRuleCondition(java.lang.String)
	 */
	public Condition[] getRuleCondition(String ruleId) {
		Rule[] rules = RuleService.INSTANCE.listRulesByType(ConstantPool.RULE_TYPE);
		if(rules != null){
			for(int i=0; i < rules.length; i++) {
				if(ruleId.equals(rules[i].getId())) {
					Condition[] conditions = new Condition[1];
					IRuleConditionSet cs = rules[i].getRootCondition();
					Condition condition = RuleAdaptor.ruleConSet2Condtion(cs);
					conditions[0] = condition;
					return conditions;
				}
			}
		}
		return null;
	}
	
	/* （non-Javadoc）
	 * 
	 * @see org.gocom.components.coframe.entityauth.IEntityCapRuleService#getCapRules(java.lang.String)
	 */
	public List<RuleAndAuth> getCapRules(String roleIds){
		// Party party = PartyRuntimeManager.getInstance().getPartyByPartyID(roleId, roleType);
		if(roleIds == null || "".equals(roleIds)) {
			return null;
		} else {
			Set<String> ruleIds = new HashSet<String>();
			List<RuleAndAuth> ruleAndAuthList = new ArrayList<RuleAndAuth>();
			String [] roleIdArr = roleIds.split(",");
			for(int i=0; i<roleIdArr.length; i++) {
				Party party = new Party();
				party.setId(roleIdArr[i]);
				party.setPartyTypeID("role");
				RuleAndAuth[] tempRuleAndAuths = queryCapRules(null, "2", null, party);
				if(tempRuleAndAuths != null) {
					for(RuleAndAuth ruleAndAuth : tempRuleAndAuths) {
						if(!ruleIds.contains(ruleAndAuth.getId())) {
							ruleIds.add(ruleAndAuth.getId());
							ruleAndAuthList.add(ruleAndAuth);
						}
					}
				}
			}
			return ruleAndAuthList;
		}
	}
	
	/**
	 * 验证规则名称是否唯一
	 * @param ruleId 规则Id
	 * @param ruleName 规则名称
	 * @param operate "add_rule","update_rule"
	 * @return flag true 唯一 false 已存在
	 */
	boolean isUniqueRuleName(String ruleId, String ruleName, String operate) {
		Rule[] rules = RuleService.INSTANCE.listRulesByType(ConstantPool.RULE_TYPE);
		if(rules != null) {
			if(ADD_RULE.equals(operate)) {
				for(int i=0; i<rules.length; i++) {
					Rule rule = rules[i];
					if(ruleName.equals(rule.getName())) {
						return false;
					}
				}
			} else if(UPDATE_RULE.equals(operate)) {
				for(int i=0; i<rules.length; i++) {
					Rule rule = rules[i];
					if(ruleName.equals(rule.getName()) && !ruleId.equals(rule.getId())) {
						return false;
					}
				}
			} 
		}
		return true;
	}
    
}


