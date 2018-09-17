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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
 * 视图授权管理Bean
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ViewManagerServiceBean {

	/**
	 * 构造方法（默认）
	 * 
	 */
	public ViewManagerServiceBean() {

	}

	/**
	 * 获取角色对象
	 * 
	 * @param roleId
	 *            角色Id
	 * @return
	 */
	private Party getParty(String roleId) {

		return new Party(IConstants.ROLE_PARTY_TYPE_ID, roleId, null, null,
				TenantManager.getCurrentTenantID());

	}

	/**
	 * 获取视图资源
	 * 
	 * @param moduleId
	 *            模型Id
	 * @param viewId
	 *            视图Id
	 * @return
	 */
	public DirResource getView(String moduleId, String viewId) {
		return DirResourceHelper.getView(moduleId, viewId);
	}

	/**
	 * 获取单独的视图节点，忽略子节点
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	public List<DirResource> getViewExceptChild(DirResource view) {
		DirResource dirResource = (DirResource) this.cloneObjWithFlat(view);
		List<DirResource> child = new ArrayList<DirResource>();
		dirResource.setChild(child);
		List<DirResource> dirResourceList = new ArrayList<DirResource>();
		dirResourceList.add(dirResource);
		return dirResourceList;
	}

	/**
	 * 克隆一个类新类(浅层克隆)
	 * 
	 * @param 要克隆的类
	 * @return 克隆好的新类
	 * @throws
	 */
	public Object cloneObjWithFlat(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Object newObj = null;
		try {
			newObj = obj.getClass().newInstance();
			for (int i = 0, j = fields.length; i < j; i++) {
				String propertyName = fields[i].getName();
				Object propertyValue = getProperty(obj, propertyName);
				setProperty(newObj, propertyName, propertyValue);
			}
		} catch (Exception e) {
		}
		return newObj;
	}

	/**
	 * 克隆一个类新类(深层克隆)
	 * 
	 * @param 要克隆的类
	 * @return 克隆好的新类
	 * @throws
	 */
	public Object cloneObjWithDeep(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Object newObj = null;
		try {
			newObj = obj.getClass().newInstance();
			for (int i = 0, j = fields.length; i < j; i++) {
				String propertyName = fields[i].getName();
				Object propertyValue = getProperty(obj, propertyName);
				String propertyType = fields[i].getType().getName();
				if (propertyValue != null) {
					// 如果源对象属性域是8种基本类型，或者String中的一种，则直接赋值
					if (propertyType.endsWith("String")
							|| "char".equals(propertyType)
							|| "int".equals(propertyType)
							|| "boolean".equals(propertyType)
							|| "byte".equals(propertyType)
							|| "short".equals(propertyType)
							|| "double".equals(propertyType)
							|| "long".equals(propertyType)
							|| "float".equals(propertyType)
							|| "void".equals(propertyType)) {
						setProperty(newObj, propertyName, propertyValue);
					} else {
						// 如果属性域类型是其他Object，则递归克隆一下
						Object newPropertyObj = cloneObjWithDeep(propertyValue);
						setProperty(newObj, propertyName, newPropertyObj);
					}
				}
			}
		} catch (Exception e) {
		}
		return newObj;
	}

	/**
	 * 反射调用setter，进行赋值操作
	 */
	private Object setProperty(Object bean, String propertyName, Object value) {
		Class clazz = bean.getClass();
		try {
			Field field = clazz.getDeclaredField(propertyName);
			Method method = clazz.getDeclaredMethod(getSetterName(field
					.getName()), new Class[] { field.getType() });
			return method.invoke(bean, new Object[] { value });
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 反射调用getter方法，获得属性域的值
	 */
	private Object getProperty(Object bean, String propertyName) {
		Class clazz = bean.getClass();
		try {
			Field field = clazz.getDeclaredField(propertyName);
			Method method = clazz.getDeclaredMethod(getGetterName(field
					.getName()), new Class[] {});
			return method.invoke(bean, new Object[] {});
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 根据属性域名称获得getter方法名
	 */
	private String getGetterName(String propertyName) {
		String method = "get" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
		return method;
	}

	/**
	 * 根据属性域名称获得setter方法名称
	 */
	private String getSetterName(String propertyName) {
		String method = "set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
		return method;
	}

	/**
	 * 获取所有子元素的权限
	 * 
	 * @param view
	 *            视图
	 * @param party
	 *            角色对象
	 * @return
	 */
	private Map<String, LinkedHashMap<String, DirResource>> getChildrenAuth(
			DirResource view, Party party) {

		Map<String, LinkedHashMap<String, DirResource>> result = new HashMap<String, LinkedHashMap<String, DirResource>>();
		view.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(
				party, view.getId(), view.getType()));
		for (DirResource r : view.fetchChildList()) {
			r.setStatus(AuthRuntimeManager.getInstance().getAuthResourceState(
					party, r.getId(), r.getType()));
			LinkedHashMap<String, DirResource> resourceMap = result.get(r
					.getType());

			if (resourceMap == null) {
				resourceMap = new LinkedHashMap<String, DirResource>();
				result.put(r.getType(), resourceMap);
			}
			resourceMap.put(r.getId(), r);
		}

		return result;
	}

	/**
	 * 获取过滤器权限
	 * 
	 * @param authRes
	 *            权限资源
	 * @return
	 */
	public List<DirResource> getFilterAuthRes(DirResource view, String roleId) {
		Party party = getParty(roleId);
		Map<String, LinkedHashMap<String, DirResource>> authRes = getChildrenAuth(
				view, party);
		LinkedHashMap<String, DirResource> resourceMap = authRes
				.get(ViewResourceHelper.CAP_VIEW_FILTER);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取结果字段权限
	 * 
	 * @param authRes
	 *            权限资源
	 * @return
	 */
	public List<DirResource> getResultAuthRes(DirResource view, String roleId) {
		Party party = getParty(roleId);
		Map<String, LinkedHashMap<String, DirResource>> authRes = getChildrenAuth(
				view, party);
		LinkedHashMap<String, DirResource> resourceMap = authRes
				.get(ViewResourceHelper.CAP_VIEW_RESULT);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取操作权限
	 * 
	 * @param authRes
	 *            权限资源
	 * @return
	 */
	public List<DirResource> getActionAuthRes(DirResource view, String roleId) {
		Party party = getParty(roleId);
		Map<String, LinkedHashMap<String, DirResource>> authRes = getChildrenAuth(
				view, party);
		LinkedHashMap<String, DirResource> resourceMap = authRes
				.get(ViewResourceHelper.CAP_VIEW_ACTION);
		if (resourceMap != null) {
			return new ArrayList<DirResource>(resourceMap.values());
		}

		return null;
	}

	/**
	 * 获取模块列表
	 * 
	 * @return
	 */
	public List<DirModule> getModuleList() {
		return DirResourceHelper.getModuleList();
	}

	/**
	 * 保存视图权限信息
	 * 
	 * @param roleId
	 *            角色id
	 * @param authRes
	 *            权限资源信息
	 * @return
	 */
	public boolean saveViewAuth(String roleId, AuthResource[] authRes) {
		Party roleParty = getParty(roleId);
		return AuthRuntimeManager.getInstance().addOrUpdateAuthResBatch(
				roleParty, Arrays.asList(authRes));

	}

	/**
	 * 获取视图列表
	 * 
	 * @param module
	 *            模型
	 * @return
	 */
	public List<DirResource> getViews(DirModule module) {
		return DirResourceHelper.getViews(module.getId());
	}

}