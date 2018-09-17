/*
 * Copyright 2013 Primeton.
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
package org.gocom.components.coframe.resource.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.resource.FormAuthService;
import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.util.TreeListResolver;

/**
 * IFormService接口实装类
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class FormService implements IFormService {

	/**
	 * 树形转换器
	 */
	private TreeListResolver resolver;

	public List<Map<String, Object>> getFormTree() {
		// 获取所有模块列表
		List<DirModule> dirModuleList = DirResourceHelper.getModuleList();
		// 遍历模块列表，获取模块下的资源
		Iterator<DirModule> dirModuleItr = dirModuleList.iterator();
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		while (dirModuleItr.hasNext()) {
			DirModule dirModule = dirModuleItr.next();
			// 获取模块下的资源
			List<DirResource> resourceList = FormAuthService
					.getForms(dirModule);
			// 获取当前模块树形列表
			treeList.addAll(resolver.moduleResourceTreeList(dirModule, resourceList, ""));
		}
		return treeList;
	}

	public TreeListResolver getResolver() {
		return resolver;
	}

	public void setResolver(TreeListResolver resolver) {
		this.resolver = resolver;
	}

}
