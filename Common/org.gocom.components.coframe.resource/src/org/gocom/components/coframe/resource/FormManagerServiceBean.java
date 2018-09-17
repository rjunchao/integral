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
package org.gocom.components.coframe.resource;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;

import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;

/**
 * Form管理服务Bean
 * 
 * @author Administrator
 * 
 */
public class FormManagerServiceBean {

	/**
	 * 构造方法（默认）
	 * 
	 */
	public FormManagerServiceBean() {

	}

	/**
	 * 获取表单资源对象
	 * 
	 * @param module
	 * @return
	 */
	public List<DirResource> getForms(DirModule module) {
		return FormAuthService.getForms(module);
	}

	/**
	 * 获取表单所有状态
	 * 
	 * @param forms 表单
	 * @return
	 */
	public List<DirResource> getFormStatus(DirResource forms) {
		return FormAuthService.getFormStatus(forms.getModuleId(), forms.getId());
	}

	/**
	 * 保存表单授权
	 * 
	 * @param roleId 角色ID
	 * @param authRes 授权资源
	 * @return result 
	 */
	public boolean saveFormAuth(String roleId, AuthResource[] authRes) {
		Party roleParty = FormAuthService.getParty(roleId);
		return AuthRuntimeManager.getInstance().addOrUpdateAuthResBatch(roleParty, Arrays.asList(authRes));
	}

	/**
	 * 获取表单状态权限
	 * 
	 * @param moduleId 模型ID
	 * @param formId 表单ID
	 * @param formStateId 表单状态ID
	 * @param roleId 角色ID
	 * @return
	 */
	public DirResource getFormStatusAuthRes(String roleId, String moduleId, String formId, String formStateId) {

		Party party = FormAuthService.getParty(roleId);
		DirResource formStatus = FormAuthService.getFormState(FormAuthService.getForm(moduleId, formId), formStateId);
		formStatus.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(party, formStatus.getId(), formStatus.getType()));

		return formStatus;
	}

	/**
	 * 获取操作权限
	 * 
	 * @param formStatus 表单状态
	 * @param roleId 角色ID
	 * @return
	 */
	public List<DirResource> getActionAuthRes(DirResource formStatus, String roleId) {

		Party party = FormAuthService.getParty(roleId);
		Map<String, LinkedHashMap<String, DirResource>> authRes = FormAuthService.getChildrenAuth(formStatus, party);
		return FormAuthService.getActionAuthRes(authRes);
	}

	/**
	 * 获取字段权限
	 * 
	 * @param formStatus 表单状态
	 * @param roleId 角色ID
	 * @return
	 */
	public List<DirResource> getFieldAuthRes(DirResource formStatus, String roleId) {

		Party party = FormAuthService.getParty(roleId);
		Map<String, LinkedHashMap<String, DirResource>> authRes = FormAuthService.getChildrenAuth(formStatus, party);
		return FormAuthService.getFieldAuthRes(authRes);
	}
}