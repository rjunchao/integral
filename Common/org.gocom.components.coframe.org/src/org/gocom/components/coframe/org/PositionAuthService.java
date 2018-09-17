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
package org.gocom.components.coframe.org;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * 岗位授权服务接口的实现类
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class PositionAuthService implements IPositionAuthService {

	public List<OrgPosition> arrayToList(OrgPosition[] positions) {
		return Arrays.asList(positions);
	}

	public List<OrgPosition> getPositionAuth(List<OrgPosition> positionList,
			String roleId) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		String[] partyTypes = { IConstants.POSITION_PARTY_TYPE_ID };
		Map<String, List<Party>> partyMap = PartyRuntimeManager.getInstance()
				.getDirectAssociateChildPartyList(roleId,
						IConstants.ROLE_PARTY_TYPE_ID, partyTypes);
		List<Party> partAuthList = partyMap.get(IConstants.POSITION_PARTY_TYPE_ID);
		matchPartyAuth(partAuthList, positionList);
		return positionList;
	}

	/**
	 * 匹配授权列表和岗位列表，向岗位列表中增加授权信息
	 * 
	 * @param partAuthList
	 *            有该角色的授权列表
	 * @param orgEmployeeList
	 *            岗位列表
	 */
	private void matchPartyAuth(List<Party> partAuthList,
			List<OrgPosition> positionList) {
		Iterator<Party> partyItr = partAuthList.iterator();
		while (partyItr.hasNext()) {
			Party authPart = partyItr.next();
			Iterator<OrgPosition> positionItr = positionList.iterator();
			while (positionItr.hasNext()) {
				OrgPosition position = positionItr.next();
				if (authPart.getId().equals(position.getPositionid().toString())) {
					position.set("auth", "1");
				}
			}
		}
	}

}
