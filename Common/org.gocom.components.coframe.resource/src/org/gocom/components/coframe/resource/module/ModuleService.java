package org.gocom.components.coframe.resource.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.management.resource.IManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

public class ModuleService implements IModuleService {

	/**
	 * 获取所有模块列表
	 */
	public List<IManagedResource> getModuleList() {
		List<IManagedResource> resources = ResourceRuntimeManager
				.getInstance()
				.getRootManagedResourceListByType(IConstants.MODULE_RES_TYPE_ID);
		return resources;
	}

	/**
	 * 获取角色下授权的模块列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 模块列表
	 */
	public List<AuthResource> getAuthModuleListByRoleId(String roleId) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		return AuthRuntimeManager.getInstance().getAuthResListByRole(roleParty);
	}

	/**
	 * 根据模块ID获得角色的授权情况
	 * 
	 * @param roleId
	 *            角色ID
	 * @param moduleId
	 *            模块ID
	 * @return 授权情况
	 */
	public String getModuleAuthByRoleId(String roleId, String moduleId) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		return AuthRuntimeManager.getInstance().getAuthResourceState(roleParty,
				moduleId, IConstants.MODULE_RES_TYPE_ID);
	}

	/**
	 * 根据角色为模块列表匹配授权状态
	 * 
	 * @param roleId
	 *            角色ID
	 * @param moduleList
	 *            模块列表
	 * @return 带有授权信息的模块列表
	 */
	public List<DirResource> mathAuthForModuleList(String roleId,
			List<IManagedResource> moduleResourceList) {
		Iterator<IManagedResource> moduleListItr = moduleResourceList
				.iterator();
		List<DirResource> moduleList = new ArrayList<DirResource>();
		while (moduleListItr.hasNext()) {
			IManagedResource moduleResource = moduleListItr.next();
			DirResource module = new DirResource();
			module.setChild(null);
			module.setModuleId(moduleResource.getResourceID());
			module.setId(moduleResource.getResourceID());
			module.setName(moduleResource.getResourceName());
			module.setStatus(getModuleAuthByRoleId(roleId, moduleResource
					.getResourceID()));
			module.setType(IConstants.MODULE_RES_TYPE_ID);
			moduleList.add(module);
		}
		return moduleList;
	}

	/**
	 * 保存模块授权信息
	 * 
	 * @param roleId
	 *            角色ID
	 * @param authRes
	 *            授权模块列表
	 * @return 授权是否成功：true 授权成功; false 授权失败
	 */
	public boolean saveModuleAuth(String roleId, AuthResource[] authRes) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		return AuthRuntimeManager.getInstance().addOrUpdateAuthResBatch(
				roleParty, Arrays.asList(authRes));
	}

}
