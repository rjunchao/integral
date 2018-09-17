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
package org.gocom.components.coframe.auth.intercepter;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gocom.components.coframe.tools.IConstants;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.access.http.IWebInterceptor;
import com.eos.access.http.IWebInterceptorChain;
import com.eos.access.http.OnlineUserManager;
import com.eos.access.http.UserLoginCheckedFilter;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.IUserObject;
import com.eos.data.datacontext.UserObject;
import com.eos.system.exception.EOSRuntimeException;
import com.eos.system.logging.Logger;
import com.primeton.cap.ISystemConstants;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.manager.PartyRuntimeManager;
import com.primeton.ext.access.http.ExceptionConstant;
import com.primeton.ext.access.http.ExceptionObject;
import com.primeton.ext.access.http.HttpUrlHelper;
import com.primeton.ext.access.http.ThrowableProcessHelper;
import com.primeton.ext.common.muo.MUODataContextHelper;

/**
 * [登录]直接访问应用开发主页未跳转到登录页面
 * 
 * @author wuyuhou
 * 
 */
public class UserLoginWebInterceptor implements IWebInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(UserLoginWebInterceptor.class);

    //是否有过滤功能
	public static final String IS_FILTER_FUNCTION_KEY = "IS_FILTER_FUNCTION";
	
	private static String ORIGINAL_URL = "original_url";

	private boolean hasInit = false;

	public void doIntercept(HttpServletRequest request,
			HttpServletResponse response, IWebInterceptorChain chain)
			throws IOException, ServletException {
		if (UserLoginCheckedFilter.isPortal() == true) {
			initContext(request, response, true);
			chain.doIntercept(request, response);
			return;
		}

		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRep = (HttpServletResponse) response;
		boolean isLogin = isLogin(httpReq, httpRep);
		if (isLogin == true) {
			initContext(request, response, false);
			chain.doIntercept(httpReq, httpRep);
			return;
		}
		String servletPath = HttpUrlHelper.getRequestUrl(httpReq, httpRep);
		if (logger.isDebugEnabled()) {
			logger.debug("checked url [" + servletPath + "] is exclude ?");
		}

		// 记住原始的url
		Object original_url = httpReq.getAttribute(ORIGINAL_URL);
		if (original_url == null) {
			httpReq.setAttribute(ORIGINAL_URL, servletPath);
		}

		boolean isExcludeUrl = HttpUrlHelper.isIn(servletPath,
				UserLoginCheckedFilter.getExcludeUrls());
		//http://127.0.0.1:8080/default/coframe/auth/login/ssologin.jsp
		//String url = httpReq.getContextPath()+"/coframe/auth/login/ssologin.jsp";
		//url ="http://127.0.0.1:8080/default/coframe/auth/login/ssologin.jsp";
//		if(servletPath.contains("ssologin.jsp")){
//			//chain.doIntercept(httpReq, httpRep);
//			httpRep.sendRedirect(url);
//			return;
//		}
		if (isExcludeUrl == true) {
			chain.doIntercept(httpReq, httpRep);
			//httpRep.sendRedirect("/ssologin.jsp");
			return;
		}
		if (HttpUrlHelper.isIn(servletPath, UserLoginCheckedFilter
				.getIncludeUrls())) {
			if (logger.isErrorEnabled())
				logger.error("access url [" + servletPath + "] must be login.");
			// 是需要检查的url，并且不在前面的ExcludeUrl中，所以要跳转到错误页面
			ExceptionObject exObj = new ExceptionObject(httpReq);
			exObj.setInvalid(true);
			exObj.setForwardPage(UserLoginCheckedFilter.getErrorPage());
			exObj.setThrowable(new EOSRuntimeException(
					ExceptionConstant.HTTP_12101001));
			httpReq.setAttribute(IS_FILTER_FUNCTION_KEY, "true");
			ThrowableProcessHelper.execute(httpReq, httpRep, exObj, true);
		} else {
			chain.doIntercept(httpReq, httpRep);
			return;
		}

	}

	private boolean isLogin(HttpServletRequest httpReq,
			HttpServletResponse httpRep) {
		HttpSession session = httpReq.getSession(false);
//		String url = httpReq.getRequestURL().toString();
//		httpReq.getRequestURI();
//		if(url.contains("ssologin.jsp")){
//			return true;
//		}
		if (session == null)
			return false;
		boolean isLogin = false;
		if (session.getAttribute(IUserObject.KEY_IN_CONTEXT) != null
				&& (!(session.getAttribute(IUserObject.KEY_IN_CONTEXT) instanceof IUserObject))) {
			throw new EOSRuntimeException(ExceptionConstant.HTTP_12101011,
					new String[] {
							IUserObject.class.getName(),
							session.getAttribute(IUserObject.KEY_IN_CONTEXT)
									.getClass().getName() });
		}
		IUserObject userObject = (IUserObject) session
				.getAttribute(IUserObject.KEY_IN_CONTEXT);
		if (userObject != null) {
			if (OnlineUserManager.getUserObjectsByUniqueId(userObject
					.getUniqueId()) != null) {
				isLogin = true;
			}
		}
		return isLogin;
	}

	private void initContext(ServletRequest request, ServletResponse response,
			boolean isPortal) {
		if (hasInit)
			return;
		// TODO 该处需要写成通用实现
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpSession session = servletRequest.getSession();
		String userId = null;
		UserObject userObject = null;
		if (isPortal) {
			userId = "guest";
			userObject = new UserObject();
			userObject.setUserId(userId);
		} else {
			userObject = (UserObject) session
					.getAttribute(IUserObject.KEY_IN_CONTEXT);
			if (userObject == null)
				throw new RuntimeException(
						"userObject not found in session, perhaps not login");
			Object obj = userObject.get(ISystemConstants.USER_ID);
			if (obj != null) {
				userId = (String) obj;
			} else {
				userId = userObject.getUserId();
			}
		}
		if (userId == null) {
			throw new RuntimeException("Illegal user");
		}

		// 在BPS中此处的userID设置的是人员，需要做额外处理
		userObject.put(ISystemConstants.USER_ID, userId);
		// 真正的userId中存储了empId
		// userObject.setUserId(userId);
		userObject.put(ISystemConstants.TENENT, getTenantID(servletRequest));
		// Map<String, List<Party>>map =
		// PartyRuntimeManager.getInstance().getLoginPartyCache(userId, "user");

		Map<String, List<Party>> map = PartyRuntimeManager.getInstance()
				.getAllParentPartyList(userId, IConstants.USER_PARTY_TYPE_ID);
		// if (map.isEmpty()) {
		// logger.error("The user [" + userId + "] is illegal.");
		// throw new RuntimeException("Illegal user");
		// }
		if (map != null) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String partyTypeID = it.next();
				List<Party> partyList = map.get(partyTypeID);

				if (partyTypeID.equals(IConstants.USER_PARTY_TYPE_ID)) {
					// 用户自身的信息
					if (partyList != null && partyList.size() == 1) {
						// 表示用户合法
						userObject.setUserName(partyList.get(0).getName());
						continue;
					} else {
						logger.error("The user [" + userId + "] is illegal.");
						return;
					}
				}

				if (partyTypeID.equals(IConstants.EMP_PARTY_TYPE_ID)) {
					if (partyList != null && partyList.size() == 1) {
						userObject.setUserId(partyList.get(0).getId());
						continue;
					}
				}

				StringBuffer partyIDBuffer = new StringBuffer();
				for (Party party : partyList) {
					partyIDBuffer.append(party.getId()
							+ com.primeton.cap.auth.IConstants.SPLIET);
					// partyIDStr += party.getId() + IConstants.SPLIET;
				}

				String partyIDStr = partyIDBuffer.toString();
				if (partyIDStr
						.endsWith(com.primeton.cap.auth.IConstants.SPLIET)) {
					partyIDStr = partyIDStr.substring(0,
							partyIDStr.length() - 1);
				}

				if (IConstants.ROLE_PARTY_TYPE_ID.equals(partyTypeID)) {
					userObject.put(com.primeton.cap.auth.IConstants.ROLE_LIST,
							partyIDStr);
				}
				session.setAttribute(partyTypeID, partyIDStr);
			}
		}

		DataContextManager.current().setMapContextFactory(
				new com.primeton.ext.access.http.HttpMapContextFactory(request,
						response));
		IMUODataContext muo = null;
		if (isPortal) {
			session.setAttribute(IUserObject.KEY_IN_CONTEXT, userObject);
			muo = MUODataContextHelper.create(session);
		}
		DataContextManager.current().setMUODataContext(muo);
		hasInit = true;
	}

	private String getTenantID(HttpServletRequest servletRequest) {
		// 根据二级url拿到租户信息
		return ISystemConstants.DEFAULT_TENANT_ID;
	}

}