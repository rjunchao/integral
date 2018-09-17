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
package org.gocom.components.coframe.rights.party;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.AppUserManager;
import com.primeton.cap.TenantManager;

/**
 * 用户授权管理
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */

public class DefaultPartyManager {
    public final static DefaultPartyManager INSTANCE = new DefaultPartyManager();
	
	/**
	 * 创建一个CapPartyauth对象数组
	 * @param capRoles CapRole对象数组
	 * @param partyId 用户ID
	 * @return CapPartyauth对象数组
	 */
	public CapPartyauth[] bulidCapPartyauth(CapRole[] capRoles,
			String partyId) {
		List<CapPartyauth> partyList = new ArrayList<CapPartyauth>();
		for (CapRole capRole : capRoles) {
			CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
			capPartyauth.setCapRole(capRole);
			capPartyauth.setPartyId(partyId);
			capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
			capPartyauth.setPartyType(IConstants.USER_PARTY_TYPE_ID);
			capPartyauth.setTenantId(TenantManager.getCurrentTenantID());
			capPartyauth.setCreatetime(new Date());
			capPartyauth.setCreateuser(AppUserManager.getCurrentUserId());
			partyList.add(capPartyauth);
		}
		return partyList.toArray(new CapPartyauth[] {});
	}

	/**
	 * 从CapPartyauth对象中提取出对应的CapRole对象
	 * @param capPartyauths CapPartyauth对象数组
	 * @return CapRole对象数组
	 */
	public CapRole[] getRoles(CapPartyauth[] capPartyauths) {
		List<CapRole> roleList = new ArrayList<CapRole>();
		for (CapPartyauth capPartyauth : capPartyauths) {
			roleList.add(capPartyauth.getCapRole());
		}
		if (roleList.size() != 0) {
			return roleList.toArray(new CapRole[] {});
		}
		return null;
	}
}
