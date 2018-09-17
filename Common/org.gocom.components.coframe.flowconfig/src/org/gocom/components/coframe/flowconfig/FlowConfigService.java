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

import org.gocom.components.coframe.flowconfig.model.AuthFlowCondition;
import org.gocom.components.coframe.flowconfig.model.FlowConditionModel;
import org.gocom.components.coframe.resource.FormAuthService;
import org.gocom.components.coframe.resource.dir.DirResource;

import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.extension.flow.AuthTabPage;
import com.primeton.workflow.api.PageCond;

/**
 * TODO 流程配置服务Bean
 *
 * @author wanghl (mailto:wanghl@primeton.com)
 */
public class FlowConfigService extends DASDaoSupport {

	/**
	 * 
	 */
	public FlowConfigService() {
	}
	
	/**
	 * 删除规则
	 * 
	 * @param ruleId 规则ID return
	 */
	public int deleteRule(String ruleId) {
		return ProcessConfigService.deleteRule(ruleId);
	}

	/**
	 * 获取流程的条件配置
	 * 
	 * @return 流程条件配置信息
	 */
	public FlowConditionModel[] getFlowCondtions() {
		return (FlowConditionModel[]) ProcessConfigService.getFlowContions();
	}

	/**
	 * 获取表单授权信息，操作权限和字段权限 1.获取表单状态的授权信息 2.获取操作权限 3.获取字段权限
	 * 
	 * @param formId 表单ID
	 * @param stateId 状态ID
	 * @param moduleId 模块ID
	 * @param ruleId 规则ID
	 * @return
	 */
	public Object[] getFormStatusAuth(String formId, String stateId, String moduleId, String ruleId) {
		Object[] object = new Object[3];

		DirResource formStatus = ProcessConfigService.getFormStatusAuthRes(moduleId, formId, stateId, ruleId);

		Map<String, LinkedHashMap<String, DirResource>> authRes = ProcessConfigService.getChildrenAuth(formStatus, ruleId);
		List<DirResource> actions = FormAuthService.getActionAuthRes(authRes);
		List<DirResource> fields = FormAuthService.getFieldAuthRes(authRes);

		object[0] = formStatus;
		object[1] = actions;
		object[2] = fields;

		return object;
	}

	/**
	 * 查询流程信息
	 * 
	 * @param proc 流程对象
	 * @param pageCond 分页条件
	 * @return
	 */
	public List getProcInfos(CriteriaType criteriaType, PageCond pageCond, String sortField, String sortOrder) {
		criteriaType.set_entity("com.eos.workflow.data.WFProcessDefine");
		if(sortField != null && sortOrder != null){
			criteriaType.set("_orderby[1]/_property", sortField);
			criteriaType.set("_orderby[1]/_sort",sortOrder);
		}
		IDASCriteria criteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return ProcessConfigService.getProcInfos(criteria, pageCond);
	}
	
	public int countWfprocessdefines(CriteriaType criteria){
    	criteria.set_entity("com.eos.workflow.data.WFProcessDefine");
    	IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
    	return getDASTemplate().count(dasCriteria);
    }

	/**
	 * 获取随机的规则ID
	 * 
	 * @return
	 */
	public String getRandomRuleId() {
		return ProcessConfigService.getRandomRuleId();
	}

	/**
	 * 获取规则的条件数据
	 * 
	 * @param ruleId 规则ID
	 * @return
	 */
	public AuthFlowCondition[] getRuleCondition(String ruleId) {
		AuthFlowCondition[] conditions = new AuthFlowCondition[1];
		conditions[0] = ProcessConfigService.getRuleConditions(ruleId);
		return conditions;
	}

	/**
	 * 获取表单授权规则
	 * 
	 * @param nameSpace 命名空间
	 * @return
	 */
	public List<AuthTabPage> getRuleTab(String nameSpace) {
		return ProcessConfigService.getRuleTabPages(nameSpace);
	}

	/**
	 * 保存规则并保存表单资源的授权信息
	 * 
	 * @param ruleId 规则ID
	 * @param ruleName 规则名称
	 * @param nameSpace 命名空间
	 * @param fc 流程条件配置
	 * @param authRes 授权资源
	 * @return
	 */
	public int saveRule(String ruleId, String ruleName, String nameSpace, AuthFlowCondition[] fc, AuthResource[] authRes) {
		int result = ProcessConfigService.saveRule(ruleId, ruleName, nameSpace, fc[0]);
		ProcessConfigService.saveAuth(ruleId, authRes);
		return result;

	}

	/**
	 * 更新规则并保存表单资源的授权信息
	 * 
	 * @param ruleId 规则ID
	 * @param ruleName 规则名称
	 * @param nameSpace 命名空间
	 * @param fc 流程条件配置
	 * @param authRes 授权资源
	 * @return
	 */
	public int updateRule(String ruleId, String ruleName, String nameSpace, AuthFlowCondition[] fc, AuthResource[] authRes) {
		int result = ProcessConfigService.updateRule(ruleId, ruleName, nameSpace, fc[0]);
		ProcessConfigService.saveAuth(ruleId, authRes);
		return result;
	}

}
