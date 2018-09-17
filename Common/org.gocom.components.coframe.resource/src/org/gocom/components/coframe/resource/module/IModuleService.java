package org.gocom.components.coframe.resource.module;

import java.util.List;

import org.gocom.components.coframe.resource.dir.DirResource;

import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.management.resource.IManagedResource;

public interface IModuleService {

	/**
	 * 获取模块列表
	 * 
	 * @param module
	 * @return
	 */
	List<IManagedResource> getModuleList();

	/**
	 * 获取角色下授权的模块列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 模块列表
	 */
	List<AuthResource> getAuthModuleListByRoleId(String roleId);

	/**
	 * 根据模块ID获得角色的授权情况
	 * 
	 * @param roleId
	 *            角色ID
	 * @param moduleId
	 *            模块ID
	 * @return 授权情况
	 */
	String getModuleAuthByRoleId(String roleId, String moduleId);

	/**
	 * 根据角色为模块列表匹配授权状态
	 * 
	 * @param roleId
	 *            角色ID
	 * @param moduleList
	 *            模块列表
	 * @return 带有授权信息的模块列表
	 */
	List<DirResource> mathAuthForModuleList(String roleId,
			List<IManagedResource> moduleResourceList);

	/**
	 * 保存模块授权信息
	 * 
	 * @param roleId
	 *            角色ID
	 * @param authRes
	 *            授权模块列表
	 * @return 授权是否成功：true 授权成功; false 授权失败
	 */
	boolean saveModuleAuth(String roleId, AuthResource[] authRes);

}
