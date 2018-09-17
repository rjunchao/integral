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

import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * 机构授权服务接口的实现类
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class OrganizationAuthService implements IOrganizationAuthService {

	public List<OrgOrganization> arrayToList(OrgOrganization[] orgs) {
		return Arrays.asList(orgs);
	}

	public List<OrgOrganization> getOrgAuth(List<OrgOrganization> orgList,
			String roleId) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		String[] partyTypes = { IConstants.ORG_PARTY_TYPE_ID };
		Map<String, List<Party>> partyMap = PartyRuntimeManager.getInstance()
				.getDirectAssociateChildPartyList(roleId,
						IConstants.ROLE_PARTY_TYPE_ID, partyTypes);
		List<Party> partAuthList = partyMap.get(IConstants.ORG_PARTY_TYPE_ID);
		matchPartyAuth(partAuthList, orgList);
		return orgList;
	}

	/**
	 * 匹配授权列表和机构列表，向机构列表中增加授权信息
	 * 
	 * @param partAuthList
	 *            有该角色的授权列表
	 * @param orgList
	 *            机构列表
	 */
	private void matchPartyAuth(List<Party> partAuthList,
			List<OrgOrganization> orgList) {
		Iterator<Party> partyItr = partAuthList.iterator();
		while (partyItr.hasNext()) {
			Party authPart = partyItr.next();
			Iterator<OrgOrganization> orgItr = orgList.iterator();
			while (orgItr.hasNext()) {
				OrgOrganization org = orgItr.next();
				if (authPart.getId().equals(org.getOrgid().toString())) {
					org.set("auth", "1");
				}
			}
		}
	}

}
