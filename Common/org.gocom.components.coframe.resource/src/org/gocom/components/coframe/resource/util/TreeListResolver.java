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
package org.gocom.components.coframe.resource.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.resource.dir.DirModule;
import org.gocom.components.coframe.resource.dir.DirResource;

/**
 * 树形转换器
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class TreeListResolver {

	/**
	 * 将传入的模块及资源封装成树结构的平行列表
	 * 
	 * @param dirModule
	 *            模块
	 * @param resourceList
	 *            模块下的子资源列表
	 * @return 树形的列表结构
	 */
	public List<Map<String, Object>> moduleResourceTreeList(
			DirModule dirModule, List<DirResource> resourceList, String leafType) {
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		if (null == dirModule) {
			return treeList;
		}
		// 制作模块节点
		Map<String, Object> moduleNode = this.makeModuleNode(dirModule);
		treeList.add(moduleNode);

		// 制作资源节点列表
		if (null == resourceList || resourceList.size() == 0) {
			return treeList;
		}
		List<Map<String, Object>> resourceNodeList = this.makeResourceNodeList(
				resourceList, dirModule.getId() + "_module", leafType, false);
		treeList.addAll(resourceNodeList);
		return treeList;
	}

	/**
	 * 制作模块类型节点
	 * 
	 * @param dirModule
	 *            模块
	 * @param resourceList
	 *            模块下的资源列表
	 * @return 模块类型节点
	 */
	private Map<String, Object> makeModuleNode(DirModule dirModule) {
		String nodeId = dirModule.getId(); // 节点id
		String nodeName = dirModule.getName(); // 节点名称
		String nodeType = "module"; // 节点类型
		// 制作节点
		Map<String, Object> moduleNode = new HashMap<String, Object>();
		moduleNode.put("id", nodeId);
		moduleNode.put("index", nodeId + "_module");
		moduleNode.put("name", nodeName);
		moduleNode.put("type", nodeType);
		// 返回模块类型节点
		return moduleNode;
	}

	/**
	 * 制作资源节点列表
	 * 
	 * @param resourceList
	 *            资源列表
	 * @param pid
	 *            父节点id
	 * @return 资源节点列表
	 */
	private List<Map<String, Object>> makeResourceNodeList(
			List<DirResource> resourceList, String pid, String leafType,
			boolean ifLeaf) {
		List<Map<String, Object>> resourceNodeList = new ArrayList<Map<String, Object>>();
		if (ifLeaf) {
			return resourceNodeList;
		}
		// 遍历资源列表
		Iterator<DirResource> resourceListItr = resourceList.iterator();

		while (resourceListItr.hasNext()) {
			DirResource resource = resourceListItr.next();
			if (leafType.equals("")) {
				if ("CAPFORMACTION".equals(resource.getType())
						|| "CAPFORMFIELD".equals(resource.getType())) {
					return resourceNodeList;
				}
			} else {
				if (leafType.equals(resource.getType().trim())) {
					ifLeaf = true;
				}
			}
			// 获得资源下的子资源
			List<DirResource> childList = resource.fetchChildList();
			// 制作资源节点
			Map<String, Object> resourceNode = this.makeResourceNode(resource,
					childList, pid);
			resourceNodeList.add(resourceNode);
			if (null != childList && childList.size() > 0) {
				List<Map<String, Object>> childNodeList = this
						.makeResourceNodeList(childList, resource.getId(),
								leafType, ifLeaf);
				resourceNodeList.addAll(childNodeList);
			}
			ifLeaf = false;
		}
		// 返回资源节点列表
		return resourceNodeList;
	}

	/**
	 * 制作资源类型节点
	 * 
	 * @param resource
	 *            资源
	 * @param childList
	 *            子资源列表
	 * @param pid
	 *            父节点id
	 * @return 资源类型节点
	 */
	private Map<String, Object> makeResourceNode(DirResource resource,
			List<DirResource> childList, String pid) {
		String nodeId = resource.getId(); // 节点id
		String nodeName = resource.getName(); // 节点名称
		String nodeType = resource.getType(); // 节点类型
		String nodeStatus = resource.getStatus(); // 节点状态
		// 制作节点
		Map<String, Object> resourceNode = new HashMap<String, Object>();
		resourceNode.put("id", nodeId);
		resourceNode.put("index", nodeId);
		resourceNode.put("name", nodeName);
		resourceNode.put("type", nodeType);
		resourceNode.put("pid", pid);
		resourceNode.put("status", nodeStatus);
		// 返回资源类型节点
		return resourceNode;
	}

}
