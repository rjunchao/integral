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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.helper.FormResourceHelper;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
/**
 * 
 * TODO 表单授权的服务类，获取和授予表单权限
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class FormAuthService {
	/**
	 * 获取模型列表
	 * 
	 * @return
	 */
	
	public static List<DirModule> getModuleList() {
		return DirResourceHelper.getModuleList();
	}

	/**
	 * 获取字段权限
	 * 
	 * @param authRes
	 * @return
	 */
	
	public static List<DirResource> getFieldAuthRes(Map<String, LinkedHashMap<String, DirResource>> authRes) {
		LinkedHashMap<String, DirResource> resourceMap = authRes.get(FormResourceHelper.CAP_FORM_FIELD);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取操作权限
	 * 
	 * @param authRes
	 * @return
	 */
	
	public static List<DirResource> getActionAuthRes(Map<String, LinkedHashMap<String, DirResource>> authRes) {
		LinkedHashMap<String, DirResource> resourceMap = authRes.get(FormResourceHelper.CAP_FORM_ACTION);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取表单状态权限
	 * 
	 * @param moduleId
	 * @param formId
	 * @param formStateId
	 * @param party
	 * @return
	 */
	
	public static DirResource getFormStatusAuthRes(String moduleId, String formId, String formStateId, Party party) {

		DirResource formStatus = getFormState(getForm(moduleId, formId), formStateId);

		formStatus.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(party, formStatus.getId(), formStatus.getType()));
		return formStatus;
	}

	/**
	 * 获取所有子元素的权限
	 * 
	 * @param formStatus
	 * @param party
	 * @return
	 */
	
	public static Map<String, LinkedHashMap<String, DirResource>> getChildrenAuth(DirResource formStatus, Party party) {
		Map<String, LinkedHashMap<String, DirResource>> result = new HashMap<String, LinkedHashMap<String, DirResource>>();

		getResourceChildAuthState(formStatus,party);
		for (DirResource r : formStatus.fetchChildList()) {
			r.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(party, r.getId(), r.getType()));
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
	 * 遍历树状结构中的子节点，获取子节点的状态
	 * @param resource
	 * @param party
	 */
	private static void getResourceChildAuthState(DirResource resource, Party party) {
		for (DirResource childResource : resource.fetchChildList()) {
			childResource.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(party, childResource.getId(), childResource.getType()));
			getResourceChildAuthState(childResource,party);
		}
	}

	/**
	 * 获取表单所有状态
	 * 
	 * @param moduleId
	 * @param formId
	 * @return
	 */
	
	public static List<DirResource> getFormStatus(String moduleId, String formId) {
		List<DirResource> results = new ArrayList<DirResource>();
		DirResource form = getForm(moduleId, formId);
		for (DirResource r : form.fetchChildList()) {
			if (r.getType().equals(FormResourceHelper.CAP_FORM_STATE)) {
				results.add(r);
			}

		}
		return results;
	}

	/**
	 * 获取表单资源对象
	 * 
	 * @param moduleId
	 * @param formId
	 * @return
	 */
	
	public static DirResource getForm(String moduleId, String formId) {
		return DirResourceHelper.getForm(moduleId, formId);
	}

	/**
	 * 获取所有表单列表
	 * 
	 * @param module
	 * @return
	 */
	
	public static List<DirResource> getForms(DirModule module) {
		return DirResourceHelper.getForms(module.getId());
	}

	/**
	 * 获取角色对象
	 * 
	 * @param roleId
	 * @return
	 */
	
	public static Party getParty(String roleId) {

		return new Party(IConstants.ROLE_PARTY_TYPE_ID, roleId, null, null, TenantManager.getCurrentTenantID());

	}

	/**
	 * 保存表单授权
	 * 
	 * @param roleId
	 * @param authRes
	 * @return
	 */
	
	public static boolean saveFormAuth(String roleId, AuthResource[] authRes) {

		Party roleParty = getParty(roleId);
		return AuthRuntimeManager.getInstance().addOrUpdateAuthResBatch(roleParty, Arrays.asList(authRes));

	}

	public static DirResource getFormState(DirResource form, String statusId) {

		for (DirResource r : form.fetchChildList()) {
			if (r.getId().equals(statusId)) {
				return r;
			}

		}
		return null;
	}

}