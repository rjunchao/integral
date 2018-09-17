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
package org.gocom.components.coframe.auth.party.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.auth.graph.GraphNode;
import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;

import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class PartyGraphService {

	private boolean hasDirectParty = false;

	private boolean hasRole = false;

	private Map<String, GraphNode> nodeMap = new LinkedHashMap<String, GraphNode>();

	/**
	 * 获取某个party的权限
	 * 
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public List<GraphNode> getPartyGraph(String partyId, String partyType) {
		List<GraphNode> graphNodes = new ArrayList<GraphNode>();
		Party currParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				partyId, partyType);
		GraphNode firstPartyNode = null;
		if (currParty != null) {
			GraphNode currPartyNode = convertParty(currParty);
			currPartyNode.setLevel(1);
			firstPartyNode = currPartyNode;
			initAssociateAuthPartyList(currParty, graphNodes, currPartyNode);
		} else {
			GraphNode noCurrPartyNode = new GraphNode();
			noCurrPartyNode.setId(partyId + "_" + partyType);
			noCurrPartyNode.setName(partyId);
			noCurrPartyNode.setType(partyType);
			noCurrPartyNode.setLevel(1);
			firstPartyNode = noCurrPartyNode;
			initAssociateAuthPartyList(currParty, graphNodes, noCurrPartyNode);
		}
		if (!hasDirectParty && !hasRole) {
			firstPartyNode.addTargetId("auth_result");
		} else if (hasDirectParty && !hasRole) {
			for (GraphNode partyNode : nodeMap.values()) {
				if(!StringUtils.equals(partyNode.getId(), firstPartyNode.getId())){
					partyNode.addTargetId("auth_result");
				}
			}
		}
		graphNodes.addAll(nodeMap.values());
		// 计算结果图元
		GraphNode computeResultNode = new GraphNode();
		computeResultNode.setId("auth_result");
		computeResultNode.setName("查看权限计算结果");
		computeResultNode.setType("result");
		computeResultNode.setLevel(4);
		graphNodes.add(computeResultNode);
		return graphNodes;
	}

	private void initAssociateAuthPartyList(Party currParty,
			List<GraphNode> graphNodes, GraphNode parentGraphNode) {
		List<Party> directPartyList = null;
		List<Party> directAuthRoleList = null;
		if (currParty != null) {
			directPartyList = AuthPartyRuntimeService
					.getAssociateAuthPartyList(currParty.getId(), currParty
							.getPartyTypeID());
			directAuthRoleList = AuthPartyRuntimeService.getAssociateAuthRoles(
					currParty.getId(), currParty.getPartyTypeID());
		}
		if (directPartyList != null && directPartyList.size() > 0) {
			for (Party directParty : directPartyList) {
				GraphNode directPartyNode = convertParty(directParty);
				// 其他参与者放在第二阶
				hasDirectParty = true;
				directPartyNode.setLevel(2);
				parentGraphNode.addTargetId(directPartyNode.getId());
				initAssociateAuthPartyList(directParty, graphNodes,
						directPartyNode);
			}
		}
		if (directAuthRoleList != null && directAuthRoleList.size() > 0) {
			for (Party directAuthRole : directAuthRoleList) {
				GraphNode directRoleNode = convertParty(directAuthRole);
				// 角色放在第二阶
				directRoleNode.setLevel(3);
				hasRole = true;
				directRoleNode.addTargetId("auth_result");
				parentGraphNode.addTargetId(directRoleNode.getId());
			}
		}

	}

	/**
	 * 转换参与者为图形节点
	 * 
	 * @param party
	 * @return
	 */
	private GraphNode convertParty(Party party) {
		String id = party.getId() + "_" + party.getPartyTypeID();
		if (nodeMap.containsKey(id)) {
			return nodeMap.get(id);
		}
		GraphNode partyNode = new GraphNode();
		partyNode.setId(id);
		if(party.getName()!=null){			
			partyNode.setName(party.getName());
		}else{
			partyNode.setName(party.getId());
		}
		partyNode.setType(party.getPartyTypeID());
		nodeMap.put(id, partyNode);
		return partyNode;
	}
}
