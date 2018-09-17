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

import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.helper.ViewResourceHelper;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
/**
 * 
 * 视图授权帮助类
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ViewAuthService {
	/**
	 * 获取模块列表
	 * 
	 * @return
	 */
	public static List<DirModule> getModuleList() {
		return DirResourceHelper.getModuleList();
	}

	/**
	 * 获取结果字段权限
	 * 
	 * @param authRes
	 * @return
	 */
	public static List<DirResource> getResultAuthRes(Map<String, LinkedHashMap<String, DirResource>> authRes) {
		LinkedHashMap<String, DirResource> resourceMap = authRes.get(ViewResourceHelper.CAP_VIEW_RESULT);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取过滤器权限
	 * 
	 * @param authRes
	 * @return
	 */
	public static List<DirResource> getFilterAuthRes(Map<String, LinkedHashMap<String, DirResource>> authRes) {
		LinkedHashMap<String, DirResource> resourceMap = authRes.get(ViewResourceHelper.CAP_VIEW_FILTER);
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
		LinkedHashMap<String, DirResource> resourceMap = authRes.get(ViewResourceHelper.CAP_VIEW_ACTION);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取视图权限
	 * 
	 * @param view
	 * @param party
	 * @return
	 */
	
	public static DirResource getViewAuthRes(DirResource view, Party party) {

		view.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(party, view.getId(), view.getType()));
		return view;
	}

	/**
	 * 获取所有子元素的权限
	 * 
	 * @param view
	 * @param party
	 * @return
	 */
	
	public static Map<String, LinkedHashMap<String, DirResource>> getChildrenAuth(DirResource view, Party party) {
		Map<String, LinkedHashMap<String, DirResource>> result = new HashMap<String, LinkedHashMap<String, DirResource>>();

		for (DirResource r : view.fetchChildList()) {
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
	 * 获取视图资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	
	public static DirResource getView(String moduleId, String viewId) {
		return DirResourceHelper.getView(moduleId, viewId);
	}

	/**
	 * 获取视图列表
	 * 
	 * @param module
	 * @return
	 */
	
	public static List<DirResource> getViews(DirModule module) {
		return DirResourceHelper.getViews(module.getId());
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

	
	public static boolean saveViewAuth(String roleId, AuthResource[] authRes) {

		Party roleParty = getParty(roleId);
		return AuthRuntimeManager.getInstance().addOrUpdateAuthResBatch(roleParty, Arrays.asList(authRes));

	}

}