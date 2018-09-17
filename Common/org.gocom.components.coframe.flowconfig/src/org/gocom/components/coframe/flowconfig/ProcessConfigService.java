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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.flowconfig.helper.AuthFlowRuleHelper;
import org.gocom.components.coframe.flowconfig.helper.ProcessConfigHelper;
import org.gocom.components.coframe.flowconfig.model.AuthFlowCondition;
import org.gocom.components.coframe.flowconfig.model.FlowConditionModel;
import org.gocom.components.coframe.resource.dir.DirResource;

import com.eos.das.entity.IDASCriteria;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.extension.flow.AuthFlowTabManager;
import com.primeton.cap.auth.extension.flow.AuthTabPage;
import com.primeton.workflow.api.PageCond;
import commonj.sdo.DataObject;

/**
 * 
 * 流程配置的统一对外接口
 * 
 * @author wanghl (mailto:wanghl@primeton.com)
 */
public class ProcessConfigService {

	/**
	 * 查询流程信息列表
	 * 
	 * @param proc 流程实体
	 * @param pageCond 翻页实体
	 * @return 流程信息列表
	 */
	public static List getProcInfos(IDASCriteria criteria, PageCond pageCond) {
		return ProcessConfigHelper.getInstance().getProDefs(criteria, pageCond);
	}
	
	/**
	 * 获取授权Tab页
	 * 
	 * @return Tab页列表
	 */
	public static List<AuthTabPage> getAuthTabPages() {
		return AuthFlowTabManager.getInstance().getAuthTabPages();
	}

	/**
	 * 解析环节
	 * 
	 * @param proc 流程实体信息
	 */
	public static void parseForm(DataObject proc) {
		ProcessConfigHelper.getInstance().parseAct(proc);
	}

	/**
	 * 获取环节名称
	 * 
	 * @param procId 流程ID
	 * @param actId 活动ID
	 * @return 环节名称
	 */
	public static String getActName(String procId, String actId) {
		return ProcessConfigHelper.getActName(procId, actId);
	}

	/**
	 * 获取表单授权规则
	 * 
	 * @param nameSpace 命名空间
	 * @return 表单授权规则
	 */
	public static List<AuthTabPage> getRuleTabPages(String nameSpace) {
		return AuthFlowRuleHelper.getRuleTabPages(nameSpace);
	}

	/**
	 * 是否子流程
	 * 
	 * @param proc 流程实体信息
	 * @return 1:是 0：否
	 */
	public static DataObject isSubForm(DataObject proc) {
		return ProcessConfigHelper.getInstance().isSubForm(proc);
	}

	/**
	 * 获取流程的条件配置
	 * 
	 * @return 条件配置
	 */
	public static FlowConditionModel[] getFlowContions() {
		return FlowConditionManager.getFlowConditionLeftModel();
	}

	/**
	 * 获取随机的ruleId
	 * 
	 * @return 随机的ruleId
	 */
	public static String getRandomRuleId() {
		return AuthFlowRuleHelper.getRandomId();
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
	public static int saveRule(String ruleId, String ruleName, String nameSpace, AuthFlowCondition fc) {
		return AuthFlowRuleHelper.saveAuthFlowRule(ruleId, ruleName, nameSpace, fc);
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
	public static int updateRule(String ruleId, String ruleName, String nameSpace, AuthFlowCondition fc) {
		return AuthFlowRuleHelper.updateAuthFlowRule(ruleId, ruleName, nameSpace, fc);
	}

	/**
	 * 获取rule的条件数据
	 * 
	 * @param ruleId 规则ID
	 * @return rule的条件数据
	 */
	public static AuthFlowCondition getRuleConditions(String ruleId) {
		return AuthFlowRuleHelper.getRuleConditions(ruleId);
	}

	/**
	 * 删除rule
	 * 
	 * @param ruleId 规则ID
	 * @return
	 */
	public static int deleteRule(String ruleId) {
		return AuthFlowRuleHelper.deleteRule(ruleId);
	}

	/**
	 * 获取表单状态的授权信息
	 * 
	 * @param moduleId 模块ID
	 * @param formId 表单ID
	 * @param formStateId 表单状态ID
	 * @param ruleId 规则ID
	 * @return
	 */
	public static DirResource getFormStatusAuthRes(String moduleId, String formId, String formStateId, String ruleId) {
		return AuthFlowRuleHelper.getFormStatusAuthRes(moduleId, formId, formStateId, ruleId);
	}

	/**
	 * 获取表单资源的授权信息
	 * 
	 * @param formStatus 表单状态实体信息
	 * @param ruleId 规则ID
	 * @return
	 */
	public static Map<String, LinkedHashMap<String, DirResource>> getChildrenAuth(DirResource formStatus, String ruleId) {
		return AuthFlowRuleHelper.getChildrenAuth(formStatus, ruleId);
	}

	/**
	 * 保存表单资源的授权信息
	 * 
	 * @param ruleId 规则ID
	 * @param ars 授权资源
	 */
	public static void saveAuth(String ruleId, AuthResource[] ars) {
		AuthFlowRuleHelper.saveAuth(ruleId, ars);
	}
}

