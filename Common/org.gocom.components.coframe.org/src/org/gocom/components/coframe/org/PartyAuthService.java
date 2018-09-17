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
import java.util.List;

import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;

/**
 * Party授权服务实现类
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 */
public class PartyAuthService implements IPartyAuthService {

	public boolean savePartyAuthBatch(String roleId, Party[] partyList) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		return AuthRuntimeManager.getInstance().addOrUpdatePartyAuthBatch(
				roleParty, Arrays.asList(partyList));
	}

	public boolean deletePartyAuthBatch(String roleId, Party[] partyList) {
		Party roleParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				roleId, IConstants.ROLE_PARTY_TYPE_ID);
		return AuthRuntimeManager.getInstance().delPartyAuthBatch(roleParty,
				Arrays.asList(partyList), IAuthManagerService.DEL_MODE_SINGLE);
	}
}
