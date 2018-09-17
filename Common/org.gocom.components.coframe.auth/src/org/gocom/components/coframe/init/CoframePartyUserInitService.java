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
package org.gocom.components.coframe.init;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;
import org.gocom.components.coframe.auth.service.PartyAuthModel;
import org.gocom.components.coframe.org.util.IOrgConstants;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.data.datacontext.IUserObject;
import com.eos.data.datacontext.UserObject;
import com.primeton.cap.ISystemConstants;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.impl.DefaultPartyUserInitService;

/**
 * 用户初始化实现类
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class CoframePartyUserInitService extends DefaultPartyUserInitService {

	@Override
	public IUserObject initUserObject(String userId, String tenantId) {
		UserObject userObject = new UserObject();
		PartyAuthModel partyAuthModel = AuthPartyRuntimeService.getPartyModel(
				userId, IConstants.USER_PARTY_TYPE_ID);
		Collection<Party> partyList = partyAuthModel.getPartys();
		Set<String> roleSet = new HashSet<String>();
		Map<String, Party> orgPartyMap = new HashMap<String, Party>();
		String mainOrgId = null;
		for (Party party : partyList) {
			String partyTypeId = party.getPartyTypeID();
			if (IConstants.USER_PARTY_TYPE_ID.equals(partyTypeId)) {
				userObject.put(ISystemConstants.USER_ID, party.getId());
				userObject.setUserName(party.getName());
				userObject.setUserMail(party.getExtAttribute(IConstants.EMAIL));
			} else if (IConstants.EMP_PARTY_TYPE_ID.equals(partyTypeId)) {
				mainOrgId = party.getExtAttribute(IOrgConstants.ORGID_PROPERTY);
				userObject.setUserId(party.getId());
				userObject.setUserRealName(party.getName());
				userObject.setUserMail(party
						.getExtAttribute(IConstants.EMAIL));
				userObject.setUserOrgId(mainOrgId);
			} else if (IConstants.ORG_PARTY_TYPE_ID.equals(partyTypeId)) {
				orgPartyMap.put(party.getId(), party);
			}
		}
		//如果只有用户没有员工，则把该处设置为userId
		if(userObject.getUserId()==null){
			userObject.setUserId(userId);
		}
		userObject.put(ISystemConstants.TENENT, tenantId);
		Iterator<String> it = orgPartyMap.keySet().iterator();
		while (it.hasNext()) {
			String orgId = it.next();
			if (orgId.equals(mainOrgId)) {
				Party party = orgPartyMap.get(orgId);
				userObject.setUserOrgName(party.getName());
				break;
			}
		}
		Collection<Party> rolePartyList = partyAuthModel.getRoles();
		if(rolePartyList!=null){			
			for(Party roleParty:rolePartyList){
				roleSet.add(roleParty.getId());
			}
		}
		String roles = StringUtils.join(roleSet.toArray(new String[] {}), com.primeton.cap.auth.IConstants.SPLIET);
		userObject.put(IConstants.ROLE_LIST, roles);
		return userObject;
	}

}
