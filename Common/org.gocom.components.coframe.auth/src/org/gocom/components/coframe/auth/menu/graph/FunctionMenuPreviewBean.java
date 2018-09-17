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
package org.gocom.components.coframe.auth.menu.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.gocom.components.coframe.auth.graph.GraphNode;
import org.gocom.components.coframe.auth.menu.DefaultMenuAuthService;
import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;
import org.gocom.components.coframe.auth.service.PartyAuthModel;
import org.gocom.components.coframe.tools.IAuthConstants;

import com.primeton.cap.auth.MenuTree;
import com.primeton.cap.auth.MenuTree.MenuTreeNode;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.management.resource.IManagedResource;
import com.primeton.cap.management.resource.IMenuResourceManager;
import com.primeton.cap.management.resource.manager.ResourceConfigurationManager;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import com.primeton.cap.party.Party;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class FunctionMenuPreviewBean {
	/**
	 * 获取功能菜单图形化的节点，角色连接到功能，功能连接到计算结果，计算结果连接到菜单
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public List<GraphNode> getGraphNode(String partyId, String partyType) {
		List<GraphNode> graphNodes = new ArrayList<GraphNode>();
		// 先获取party授权模型
		PartyAuthModel authModel = AuthPartyRuntimeService.getPartyModel(
				partyId, partyType);
		List<Party> roles = new ArrayList<Party>();
		if (authModel != null) {
			roles.addAll(authModel.getRoles());
		}
		HashMap<String, GraphNode> functionMap = new LinkedHashMap<String, GraphNode>();
		// 获取所有的功能
		List<IManagedResource> allFunctionResources = ResourceRuntimeManager
				.getInstance().getRootManagedResourceListByType(
						IAuthConstants.FUNCTION_TO_RESOURCE_TYPE);
		IMenuResourceManager menuResourceManager = ResourceConfigurationManager
				.getInstance().getMenuResourceManager(
						IAuthConstants.FUNCTION_TO_RESOURCE_TYPE);
		if(roles==null||roles.isEmpty()){
			//addNoRoleNode(graphNodes);
		}else{			
			// 遍历role，获取已授权的功能
			for (Party role : roles) {
				GraphNode roleNode = convertParty(role);
				roleNode.setLevel(1);
				graphNodes.add(roleNode);
				// 遍历功能，看功能是否已授权给角色
				for (IManagedResource resource : allFunctionResources) {
					String state = AuthRuntimeManager.getInstance()
					.getAuthResourceState(role, resource.getResourceID(),
							resource.getResourceType());
					String states[] = new String[] { state };
					if (menuResourceManager != null) {
						if (menuResourceManager.canAccess(states)) {
							GraphNode resourceNode = functionMap.get(resource
									.getResourceID());
							if (resourceNode == null) {
								resourceNode = convertResource(resource);
								functionMap.put(resource
									.getResourceID(), resourceNode);
								resourceNode.setLevel(2);
							}
							//graphNodes.add(resourceNode);
							resourceNode.addTargetId("compute_result");
							roleNode.addTargetId(resourceNode.getId());
						}
					}
				}
			}
		}
		if(functionMap.size()==0){
			//如果角色不为空，则设置角色连接到无功能上
			for (Party role : roles) {
				
			}
			//addNoFunctionNode(graphNodes);
			
		}else{
			graphNodes.addAll(functionMap.values());
		}
		// 添加其他功能节点
		GraphNode otherFunction = new GraphNode();
		otherFunction.setId("other_function");
		otherFunction.setName("公共功能");
		otherFunction.setLevel(3);
		otherFunction.setType(IAuthConstants.FUNCTION_TO_RESOURCE_TYPE);
		otherFunction.addTargetId("compute_result");
		graphNodes.add(otherFunction);

		// 添加汇聚节点
		GraphNode computeResult = new GraphNode();
		computeResult.setId("compute_result");
		computeResult.setName("计算菜单结果");
		computeResult.setType("result");
		computeResult.setLevel(3);
		graphNodes.add(computeResult);
		getTreeNode(graphNodes, computeResult, roles);

		return graphNodes;

	}
	/**
	 * 无授权角色时添加一个空节点
	 * @param graphNodes
	 */
	private void addNoRoleNode(List<GraphNode> graphNodes) {
		GraphNode noRoleNode = new GraphNode();
		noRoleNode.setId("no_auth_role");
		noRoleNode.setName("无授权角色");
		noRoleNode.setType("role");
		noRoleNode.setLevel(1);
		noRoleNode.addTargetId("no_auth_function");
		graphNodes.add(noRoleNode);
		
	}
	/**
	 * 无授权功能时添加一个空节点
	 * @param graphNodes
	 */
	private void addNoFunctionNode(List<GraphNode> graphNodes) {
		GraphNode noRoleNode = new GraphNode();
		noRoleNode.setId("no_auth_function");
		noRoleNode.setName("无授权功能");
		noRoleNode.setType("function");
		noRoleNode.setLevel(2);
		noRoleNode.addTargetId("compute_result");
		graphNodes.add(noRoleNode);
	}
	/**
	 * 计算菜单树的图形化展示节点
	 * @param graphNodes
	 * @param parentNode
	 * @param roles
	 */
	private void getTreeNode(List<GraphNode> graphNodes, GraphNode parentNode,
			List<Party> roles) {
		// 获取已授权的菜单
		DefaultMenuAuthService service = new DefaultMenuAuthService(roles);
		MenuTree menuTree = service.getAllPartyAuthMenuTree();
		List<MenuTreeNode> treeNodes = menuTree.getMenuTreeRootNodeList();
		if(treeNodes==null || treeNodes.isEmpty()){
			addNoMenuNode(graphNodes,parentNode);
		}
		convertTreeNodes(graphNodes, parentNode, treeNodes);

	}
	/**
	 * 无授权菜单时添加一个空节点
	 * @param graphNodes
	 * @param parentNode
	 */
	private void addNoMenuNode(List<GraphNode> graphNodes, GraphNode parentNode) {
		GraphNode noMenuNode = new GraphNode();
		noMenuNode.setId("no_menu_node");
		noMenuNode.setName("无可见菜单");
		noMenuNode.setType("menu");
		noMenuNode.setLevel(parentNode.getLevel()+1);
		parentNode.addTargetId(noMenuNode.getId());
		graphNodes.add(noMenuNode);
		
	}
	/**
	 * 计算菜单树的图形化展示节点
	 * @param graphNodes
	 * @param parentNode
	 * @param treeNodes
	 */
	private void convertTreeNodes(List<GraphNode> graphNodes,
			GraphNode parentNode, List<MenuTreeNode> treeNodes) {
		if(treeNodes!=null){			
			for (MenuTreeNode treeNode : treeNodes) {
				GraphNode menuGraphNode = convertMenuTreeNode(treeNode);
				menuGraphNode.setLevel(parentNode.getLevel() + 1);
				parentNode.addTargetId(menuGraphNode.getId());
				graphNodes.add(menuGraphNode);
				convertTreeNodes(graphNodes, menuGraphNode, treeNode
						.getChildrenMenuTreeNodeList());
			}
		}

	}
	/**
	 * 把菜单节点转为图形节点
	 * @param treeNode
	 * @return
	 */
	private GraphNode convertMenuTreeNode(MenuTreeNode treeNode) {
		GraphNode treeMenuNode = new GraphNode();
		treeMenuNode.setId(treeNode.getMenuCode() + treeNode.getMenuName() + "_menu");
		treeMenuNode.setName(treeNode.getMenuName());
		treeMenuNode.setType("menu");
		return treeMenuNode;
	}
	/**
	 * 转换功能节点为图形节点
	 * @param resource
	 * @return
	 */
	private GraphNode convertResource(IManagedResource resource) {
		GraphNode resourceNode = new GraphNode();
		resourceNode.setId(resource.getResourceID() + "_"
				+ resource.getResourceType());
		resourceNode.setName(resource.getResourceName());
		resourceNode.setType(resource.getResourceType());
		return resourceNode;
	}
	/**
	 * 转换参与者为图形节点
	 * @param party
	 * @return
	 */
	private GraphNode convertParty(Party party) {
		GraphNode partyNode = new GraphNode();
		partyNode.setId(party.getId() + "_" + party.getPartyTypeID());
		partyNode.setName(party.getName());
		partyNode.setType(party.getPartyTypeID());
		return partyNode;
	}
}
