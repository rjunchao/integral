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

import java.util.List;

import org.gocom.components.coframe.auth.graph.GraphNode;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class PartyGraphServiceBean {
	/**
	 * 获取某个party的权限
	 * 
	 * @param partyId
	 * @param partyType
	 * @return
	 */
	public List<GraphNode> getPartyGraph(String partyId, String partyType) {
		PartyGraphService service = new PartyGraphService();
		return service.getPartyGraph(partyId, partyType);
	}

}
