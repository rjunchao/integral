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
package org.gocom.components.coframe.resource.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;
/**
 * 
 * 资源树的帮助类，存放整个模块的资源树
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class DirResourceHelper {

	static LinkedHashMap<String, DirModule> modules = new LinkedHashMap<String, DirModule>();

	/**
	 * 将视图加到授权目录树
	 * 
	 * @param view
	 */
	public static void addView(DirResource view) {
		DirModule module = modules.get(view.getModuleId());
		if (module == null) {
			module = new DirModule(view.getModuleId(), view.getModuleId());
			modules.put(module.getId(), module);
		}

		module.addView(view);
	}

	/**
	 * 将表单加入的视图目录树
	 * 
	 * @param form
	 */

	public static void addForm(DirResource form) {
		DirModule module = modules.get(form.getModuleId());
		if (module == null) {
			module = new DirModule(form.getModuleId(), form.getModuleId());
			modules.put(module.getId(), module);
		}

		module.addForm(form);
	}

	/**
	 * 删除视图
	 * 
	 * @param view
	 */
	public static void deleteView(DirResource view) {
		DirModule module = modules.get(view.getModuleId());
		if (module == null) {
			return;
		}

		module.deleteView(view);
	}

	/**
	 * 删除表单资源
	 * 
	 * @param form
	 */
	public static void deleteForm(DirResource form) {
		DirModule module = modules.get(form.getModuleId());
		if (module == null) {
			return;
		}

		module.deleteForm(form);
	}

	/**
	 * 获取所有模块列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<DirModule> getModuleList() {
		return new ArrayList(modules.values());
	}
	
	public static void removeModule(String moduleId) {
		modules.remove(moduleId);
	}

	/**
	 * 获取视图资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	public static DirResource getView(String moduleId, String viewId) {
		return modules.get(moduleId).getView(viewId);
	}

	/**
	 * 获取视图资源列表
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	public static List<DirResource> getViews(String moduleId) {
		return modules.get(moduleId).fetchViewList();
	}

	/**
	 * 获取表单资源
	 * 
	 * @param moduleId
	 * @param formId
	 * @return
	 */
	public static DirResource getForm(String moduleId, String formId) {
		return modules.get(moduleId).getForm(formId);
	}

	/**
	 * 获取form资源列表
	 * 
	 * @param moduleId
	 * @return
	 */
	public static List<DirResource> getForms(String moduleId) {
		return modules.get(moduleId).fetchFormList();
	}
}