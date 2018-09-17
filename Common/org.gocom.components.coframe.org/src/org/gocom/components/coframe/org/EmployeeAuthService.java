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

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * 人员授权服务接口的实现类
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class EmployeeAuthService implements IEmployeeAuthService {

	public List<OrgEmployee> arrayToList(OrgEmployee[] orgEmployees) {
		return Arrays.asList(orgEmployees);
	}

	public List<OrgEmployee> getEmployeeAuth(List<OrgEmployee> orgEmployeeList,
			String roleId) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		String[] partyTypes = { IConstants.EMP_PARTY_TYPE_ID };
		Map<String, List<Party>> partyMap = PartyRuntimeManager.getInstance()
				.getDirectAssociateChildPartyList(roleId,
						IConstants.ROLE_PARTY_TYPE_ID, partyTypes);
		List<Party> partAuthList = partyMap.get(IConstants.EMP_PARTY_TYPE_ID);
		/*
		 * List<Party> partAuthList = AuthRuntimeManager.getInstance()
		 * .getPartyListByTypeWithRole(roleParty, IConstants.EMP_PARTY_TYPE_ID);
		 */
		matchPartyAuth(partAuthList, orgEmployeeList);
		return orgEmployeeList;
	}

	/**
	 * 匹配授权列表和人员列表，向人员列表中增加授权信息
	 * 
	 * @param partAuthList
	 *            有该角色的授权列表
	 * @param orgEmployeeList
	 *            人员列表
	 */
	private void matchPartyAuth(List<Party> partAuthList,
			List<OrgEmployee> orgEmployeeList) {
		if (null == partAuthList || null == orgEmployeeList) {
			return;
		}
		Iterator<Party> partyItr = partAuthList.iterator();
		while (partyItr.hasNext()) {
			Party authPart = partyItr.next();
			Iterator<OrgEmployee> orgEmployeeItr = orgEmployeeList.iterator();
			while (orgEmployeeItr.hasNext()) {
				OrgEmployee employee = orgEmployeeItr.next();
				if (authPart.getId().equals(employee.getEmpid().toString())) {
					employee.set("auth", "1");
				}
			}
		}
	}

}
