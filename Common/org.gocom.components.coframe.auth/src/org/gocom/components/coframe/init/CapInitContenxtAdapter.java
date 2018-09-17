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

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gocom.components.coframe.tools.IConstants;

import com.eos.access.http.OnlineUserManager;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.IUserObject;
import com.eos.data.datacontext.UserObject;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.primeton.cap.ISystemConstants;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyManagerServiceLoader;
import com.primeton.cap.party.manager.PartyRuntimeManager;
import com.primeton.ext.common.muo.MUODataContextHelper;

import edu.yale.its.tp.cas.client.IContextInit;

/**
 * sso中一般应用的初始化
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class CapInitContenxtAdapter implements IContextInit {

	private Logger logger = TraceLoggerFactory.getLogger(CapInitContenxtAdapter.class);

	public String getTranslatorUser(String userId) {
		// 从用户表中找到相关user，如果存在则继续，否则返回null；目前的映射关系为具有相同名字
		Party userParty = PartyRuntimeManager.getInstance().getPartyByPartyID(userId, IConstants.USER_PARTY_TYPE_ID);
		if (userParty == null) {
			return null;
		} else {
			return userId;
		}
	}

	public void initContext(ServletRequest request, ServletResponse response, FilterChain fc, String userId) {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		if (userId == null) {
			throw new RuntimeException("Illegal user");
		}

		UserObject userObject = (UserObject) PartyManagerServiceLoader.getCurrentPartyUserInitService().initUserObject(userId, getTenantID(servletRequest));
		
		// 当前皮肤
		/*IPortalUserService userService = PortalServiceRegistry.INSTANCE
				.getUserService();
		userService.initUserDataIfNecessary(tenantId, userId);
		PortalUserConfig userConfig = userService.getUserConfig(tenantId,
				userId);
		if (userConfig != null && userConfig.getSkin()!=null && userConfig.getSkin().trim().length()>0) {
			session.setAttribute("skin", userConfig.getSkin());
		} else {
			PortalSystemConfig systemConfig = userService
					.getSystemConfig(tenantId);
			if (systemConfig != null) {
				session.setAttribute("skin", systemConfig.getDefaultSkin());
			}

		}*/
		
		DataContextManager.current().setMapContextFactory(new com.primeton.ext.access.http.HttpMapContextFactory(request, response));
		OnlineUserManager.login(userObject);
		session.setAttribute(IUserObject.KEY_IN_CONTEXT, userObject);
		IMUODataContext muo = MUODataContextHelper.create(session);
		DataContextManager.current().setMUODataContext(muo);

	}

	private String getTenantID(HttpServletRequest servletRequest) {
		// 根据二级url拿到租户信息
		return ISystemConstants.DEFAULT_TENANT_ID;
	}

}