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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.auth.menu.DefaultMenuAuthService;
import org.gocom.components.coframe.framework.constants.IAppConstants;
import org.gocom.components.coframe.tools.IAuthConstants;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.access.http.IWebInterceptor;
import com.eos.access.http.IWebInterceptorChain;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.UserObject;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.TenantManager;
import com.primeton.cap.management.resource.IManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.ext.access.http.processor.MultipartResolver;
import com.primeton.ext.common.muo.MUODataContextHelper;

/**
 * 功能WebInterceptor
 * @author wuyuhou
 * 
 */
public class FunctionWebInterceptor implements IWebInterceptor {

	public void doIntercept(HttpServletRequest request,
			HttpServletResponse response, IWebInterceptorChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(MultipartResolver.getEncoding());
		if ("true".equals(request.getAttribute(UserLoginWebInterceptor.IS_FILTER_FUNCTION_KEY))) {
			chain.doIntercept(request, response);
			return;
		}

		IMUODataContext muo = MUODataContextHelper.create(request.getSession());
		DataContextManager.current().setMUODataContext(muo);

		String resId = request
				.getParameter(IAuthConstants.FUNCTION_PARAN_RESOURCE_ID);
		String resType = request
				.getParameter(IAuthConstants.FUNCTION_PARAM_REAOURCE_TYPE);
		if (StringUtil.isNotEmpty(resType)
				&& !IAuthConstants.FUNCTION_TO_RESOURCE_TYPE.equals(resType)) {
			chain.doIntercept(request, response);
			return;
		}

		IManagedResource managedResource = null;
		if (StringUtils.isEmpty(resId) || StringUtils.isEmpty(resType)) {
			managedResource = getManagedResource(request);
		} else {
			managedResource = ResourceRuntimeManager.getInstance()
					.getManagedResource(resId, resType);
		}

		if (hasPermission(managedResource, request)) {
			chain.doIntercept(request, response);
		} else {
			request.getRequestDispatcher("/coframe/auth/noAuth.jsp").forward(
					request, response);
		}
	}

	/**
	 * 判断是否拥有访问该角色的权限
	 * @param managedResource
	 * @param request
	 * @return
	 */
	public boolean hasPermission(IManagedResource managedResource,
			HttpServletRequest request) {
		if (managedResource == null) {
			return true;
		} else if (managedResource != null
				&& "0".equals(managedResource
						.getAttribute(IAppConstants.FUNCTION_IS_CHECK))) {
			return true;
		}
		UserObject userObject = (UserObject) request.getSession().getAttribute(
				"userObject");
		String roleIds = (String) userObject.get(IConstants.ROLE_LIST);
		List<Party> roles = new ArrayList<Party>();
		if (roleIds != null) {
			String[] roleIdArr = roleIds
					.split(com.primeton.cap.auth.IConstants.SPLIET);
			for (String roleId : roleIdArr) {
				if (!StringUtils.isEmpty(roleId)) {
					roles.add(new Party(IConstants.ROLE_PARTY_TYPE_ID, roleId,
							roleId, roleId));
				}
			}
		}
		String funcCode = managedResource.getResourceID();
		DefaultMenuAuthService menuAuthService = new DefaultMenuAuthService(roles);
		if(menuAuthService.canAccessFunction(funcCode)){
			return true;
		}
		return false;
	}

	/**
	 * 获得功能资源
	 * @param request
	 * @return
	 */
	public IManagedResource getManagedResource(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		List<IManagedResource> resources = ResourceRuntimeManager.getInstance()
				.getRootManagedResourceListByType(
						IAuthConstants.FUNCTION_TO_RESOURCE_TYPE);
		String tenantId = TenantManager.getCurrentTenantID();
		for (IManagedResource resource : resources) {
			String funcAction = resource
					.getAttribute(IAppConstants.FUNCTION_URL);
			if (StringUtil.isNotEmpty(funcAction)) {
				String funcURI = StringUtil.substringBefore(funcAction, "?");
				String funcTenant = resource.getTenantID();
				if (StringUtil.equal(tenantId, funcTenant)
						&& StringUtil.equal(servletPath, funcURI)) {
					String funcParam = resource
							.getAttribute(IAppConstants.FUNCTION_PARA_INFO);
					Map paramMap = request.getParameterMap();
					if (funcParam == null) {
						return resource;
					} else if (isContain(paramMap, funcParam)) {
						return resource;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 判断用户请求某功能的参数中是否包含该功能所必需参数
	 * @param paramMap 用户请求参数Map
	 * @param funcParam 功能必需参数
	 * @return true 包含
	 */
	public boolean isContain(Map paramMap, String funcParam) {
		String[] funcParams = funcParam.split("&");
		boolean bool = true;
		for (int i = 0; i < funcParams.length; i++) {
			if (!StringUtil.isBlank(funcParams[i])) {
				bool = false;
				String[] params = funcParams[i].split("=");
				if (!StringUtil.isBlank(params[0])) {
					String paramValue = (String) paramMap.get(params[0]);
					if (StringUtil.isBlank(paramValue)) {
						if (paramValue.equals(params[1])) {
							bool = true;
						}
					} else if (StringUtil.isBlank(params[1])) {
						bool = true;
					}
				} else {
					bool = true;
				}
			}
		}
		return bool;
	}
}