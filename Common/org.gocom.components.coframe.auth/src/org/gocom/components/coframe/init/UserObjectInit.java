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

import javax.servlet.http.HttpServletRequest;

import com.eos.data.datacontext.UserObject;
import com.primeton.cap.ISystemConstants;
import com.primeton.cap.party.manager.PartyManagerServiceLoader;

public class UserObjectInit {
	public static final UserObjectInit INSTANCE = new UserObjectInit();

	private UserObjectInit() {
	}

	/**
	 * 初始化用户信息
	 * @param userId
	 */
	public UserObject init(String userId) {
		 return (UserObject) PartyManagerServiceLoader.getCurrentPartyUserInitService().initUserObject(userId, getTenantID(null));
	}
	
	private String getTenantID(HttpServletRequest servletRequest) {
		// 根据二级url拿到租户信息
		return ISystemConstants.DEFAULT_TENANT_ID;
	}
}
