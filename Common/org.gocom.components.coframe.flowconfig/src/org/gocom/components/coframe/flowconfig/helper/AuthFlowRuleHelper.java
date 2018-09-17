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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.gocom.components.coframe.flowconfig.model.AuthFlowCondition;
import org.gocom.components.coframe.resource.FormAuthService;
import org.gocom.components.coframe.resource.dir.DirResource;

import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.extension.flow.AuthTabPage;
import com.primeton.cap.spi.auth.rule.IRuleConditionSet;
import com.primeton.cap.spi.auth.rule.Rule;
import com.primeton.cap.spi.auth.rule.RuleResourcePrivilege;
import com.primeton.cap.spi.auth.rule.RuleService;

/**
 * 流程表单规则授权实现类
 * 
 * @author wanghl (mailto:wanghl@primeton.com)
 */

public class AuthFlowRuleHelper {
	// 常量 cap_flow
	public static final String FLOW_RULE_TYPE = "cap_flow";

	// 常量and
	public static final String FLOW_RULE_OPERATOR_AND = "and";

	// 常量or
	public static final String FLOW_RULE_OPERATOR_OR = "or";

	// 常量"并且"
	public static final String FLOW_RULE_NAME_AND = "并且";

	// 常量"或者"
	public static final String FLOW_RULE_NAME_OR = "或者";

	// 常量"="
	public static final String FLOW_RULE_EQUAL = "=";

	// 常量"<>"
	public static final String FLOW_RULE_NOTEQUAL = "<>";

	// 常量 流程表单权限的路径
	public static final String FLOW_FORM_AUTH_PATH = "org.gocom.cap.auth.flow.procFormAuth.flow";

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
	 * 保存rule
	 * 
	 * @param ruleId 规则ID
	 * @param ruleName 规则名称
	 * @param nameSpace 命名空间
	 * @param fc 流程条件
	 * @return 0：成功 1：失败
	 */
	public static int saveAuthFlowRule(String ruleId, String ruleName, String namespace, AuthFlowCondition fc) {

		return saveAuthFlowRule(ruleId, ruleName, namespace, fc, 0);
	}

	/**
	 * 更新rule
	 * 
	 * @param ruleId 规则ID
	 * @param ruleName 规则名称
	 * @param nameSpace 命名空间
	 * @param fc 流程的条件数据
	 * @return 0：成功 1：失败
	 */
	public static int updateAuthFlowRule(String ruleId, String ruleName, String namespace, AuthFlowCondition fc) {

		return saveAuthFlowRule(ruleId, ruleName, namespace, fc, 1);
	}

	/**
	 * 保存rule
	 * 
	 * @param ruleId 规则ID
	 * @param ruleName 规则名称
	 * @param nameSpace 命名空间
	 * @param fc 流程的条件数据
	 * @param flag 标志 0：保存 1：更新
	 * @return 0：成功 1：失败
	 */
	public static int saveAuthFlowRule(String ruleId, String ruleName, String namespace, AuthFlowCondition fc, int flag) {
		Rule rule = new Rule();
		rule.setId(ruleId);
		rule.setName(ruleName);
		rule.setNamespace(namespace);
		rule.setType(FLOW_RULE_TYPE);
		rule.setTenantId(TenantManager.getCurrentTenantID());

		try {
			IRuleConditionSet cs = AuthFlowRuleAdaptor.authFlowCondtion2RuleConSet(fc, ruleId);
			rule.setRootCondition(cs);
			if (flag == 0) {
				RuleService.INSTANCE.addRule(rule);
			} else if (flag == 1) {
				RuleService.INSTANCE.updateRule(rule);
			}

		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	/**
	 * 获取rule的条件数据
	 * 
	 * @param ruleId 规则ID
	 * @return rule的条件数据
	 */
	public static AuthFlowCondition getRuleConditions(String ruleId) {
		Rule rule = RuleService.INSTANCE.getRule(ruleId, FLOW_RULE_TYPE);
		IRuleConditionSet cs = rule.getRootCondition();
		AuthFlowCondition fc = AuthFlowRuleAdaptor.ruleConSet2AuthFlowCondtion(cs);
		return fc;
	}

	/**
	 * 获取规则Tab页面
	 * 
	 * @param namespace 命名空间
	 * @return 规则Tab页面
	 */
	public static List<AuthTabPage> getRuleTabPages(String namespace) {
		List<AuthTabPage> tabs = new ArrayList<AuthTabPage>();
		Rule[] rules = RuleService.INSTANCE.listRulesByNamespace(FLOW_RULE_TYPE, namespace);
		if (rules != null) {
			for (Rule rule : rules) {
				tabs.add(new AuthTabPage(rule.getId(), rule.getName(), FLOW_FORM_AUTH_PATH, 1));
			}
		}
		return tabs;
	}

	/**
	 * 删除rule
	 * 
	 * @param ruleId 规则ID
	 * @return 0：删除成功
	 */
	public static int deleteRule(String ruleId) {
		try {
			RuleService.INSTANCE.deleteRule(ruleId, FLOW_RULE_TYPE);
			RuleService.INSTANCE.deletePrivilegesByRuleId(ruleId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0;
	}

	/**
	 * 保存表单资源的授权信息
	 * 
	 * @param ruleId 规则ID
	 * @param ars 授权资源
	 */
	public static void saveAuth(String ruleId, AuthResource[] ars) {
		if (ars == null) {
			return;
		}
		Map<String, RuleResourcePrivilege> rpMap = getResIdMapByRule(ruleId);
		// 筛选更新和添加
		List<RuleResourcePrivilege> toAdd = new ArrayList<RuleResourcePrivilege>();
		List<RuleResourcePrivilege> toUpdate = new ArrayList<RuleResourcePrivilege>();
		for (AuthResource ar : ars) {
			RuleResourcePrivilege rp = rpMap.get(ar.getResourceID());
			if (rp != null) {
				rp.setState(ar.getState());
				toUpdate.add(rp);
				rpMap.remove(rp.getResourceId());
			} else {
				// 必须关联type
				if (ar.getResourceType() == null) {
					continue;
				}
				rp = AuthResourceAdapor.authResource2Privilege(ar, ruleId);
				toAdd.add(rp);
			}
		}
		// 获取删除的元素
		List<String> toDelete = new ArrayList<String>();
		for (RuleResourcePrivilege rp : rpMap.values()) {
			toDelete.add(rp.getId());
		}

		RuleService.INSTANCE.deletePrivilege(toDelete.toArray(new String[toDelete.size()]));
		RuleService.INSTANCE.addPrivilege(toAdd);
		RuleService.INSTANCE.updatePrivilege(toUpdate.toArray(new RuleResourcePrivilege[toUpdate.size()]));
	}

	/**
	 * 根据规则ID 获取规则资源的等级集合
	 * 
	 * @param ruleId 规则ID
	 * @return 规则资源的等级
	 */
	private static Map<String, RuleResourcePrivilege> getResIdMapByRule(String ruleId) {
		RuleResourcePrivilege[] rps = RuleService.INSTANCE.listPrivilegesByRuleId(ruleId);
		Map<String, RuleResourcePrivilege> rpMap = new HashMap<String, RuleResourcePrivilege>();
		if (rps != null) {
			for (RuleResourcePrivilege rp : rps) {
				rpMap.put(rp.getResourceId(), rp);
			}
		}
		return rpMap;
	}

	/**
	 * 获取资源等级
	 * 
	 * @param resId 资源ID
	 * @param resType 资源类型
	 * @param ruleId 规则ID
	 * @return 资源等级
	 */
	public static RuleResourcePrivilege getResourcePrivilege(String resId, String resType, String ruleId) {
		String[] rk = {
				resId, resType
		};
		RuleResourcePrivilege[][] rpss = RuleService.INSTANCE.listPrivilegesByResourceKey(rk);
		RuleResourcePrivilege[] rps = rpss[0];
		if (rps == null || rps.length < 1) {
			return null;
		} else {
			for (RuleResourcePrivilege rp : rps) {
				if (ruleId.equals(rp.getRuleId())) {
					return rp;
				}
			}
		}
		return null;
	}

	/**
	 * 获取资源状态
	 * 
	 * @param resId 资源ID
	 * @param resType 资源类型
	 * @param ruleId 规则ID
	 * @return 资源状态
	 */
	public static String getResourceState(String resId, String resType, String ruleId) {
		RuleResourcePrivilege rp = getResourcePrivilege(resId, resType, ruleId);
		if (rp == null) {
			return "";
		} else {
			return rp.getState();
		}
	}

	/**
	 * 获取资源状态
	 * 
	 * @param dr 资源列表
	 * @param ruleId 规则ID
	 * @return 资源状态
	 */
	public static String getResourceState(DirResource dr, String ruleId) {
		return getResourceState(dr.getId(), dr.getType(), ruleId);
	}

	/**
	 * 获取表单状态的授权信息
	 * 
	 * @param moduleId 模块ID
	 * @param formId 表单ID
	 * @param formStateId 表单状态ID
	 * @param ruleId 规则ID
	 * @return 表单状态的授权信息
	 */
	public static DirResource getFormStatusAuthRes(String moduleId, String formId, String formStateId, String ruleId) {

		DirResource formStatus = FormAuthService.getFormState(FormAuthService.getForm(moduleId, formId), formStateId);
		formStatus.setStatus(getResourceState(formStatus, ruleId));
		return formStatus;
	}

	/**
	 * 获取子表单的授权信息
	 * 
	 * @param formStatus 表单状态
	 * @param ruleId 规则ID
	 * @return 子表单的授权信息
	 */
	public static Map<String, LinkedHashMap<String, DirResource>> getChildrenAuth(DirResource formStatus, String ruleId) {
		Map<String, LinkedHashMap<String, DirResource>> result = new HashMap<String, LinkedHashMap<String, DirResource>>();

		for (DirResource r : formStatus.fetchChildList()) {
			r.setStatus(getResourceState(r, ruleId));
			LinkedHashMap<String, DirResource> resourceMap = result.get(r.getType());

			if (resourceMap == null) {
				resourceMap = new LinkedHashMap<String, DirResource>();
				result.put(r.getType(), resourceMap);
			}
			resourceMap.put(r.getId(), r);
		}

		return result;
	}

	/**
	 * 获取命名空间
	 * 
	 * @param moduleId 模块ID
	 * @param procId 流程ID
	 * @param actId 活动ID
	 * @param formId 表单ID
	 * @param state 状态
	 * @return 命名空间
	 */
	public static String getNameSpace(String moduleId, String procId, String actId, String formId, String state) {
		return new StringBuilder(FLOW_RULE_TYPE)
					.append(".")
					.append(moduleId)
					.append(".")
					.append(procId)
					.append(".")
					.append(actId)
					.append(".")
					.append(formId)
					.append(".")
					.append(state).toString();
	}

}
