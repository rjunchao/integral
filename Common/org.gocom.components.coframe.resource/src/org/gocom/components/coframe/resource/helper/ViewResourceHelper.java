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
package org.gocom.components.coframe.resource.helper;

import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;
/**
 * 
 * 视图资源帮助类，定义视图授权规则
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ViewResourceHelper {

	final static public String CAP_VIEW = "CAPVIEW";

	final static public String CAP_VIEW_FILTER = "CAPVIEWFILTER";

	final static public String CAP_VIEW_RESULT = "CAPVIEWRESULT";

	final static public String CAP_VIEW_ACTION = "CAPVIEWACTION";

	final static public String CAP_VIEW_NONE = "0";

	final static public String CAP_VIEW_RESTRICTED = "1";

	final static public String CAP_VIEW_ACCESSIBLE = "2";

	final static public String CAP_VIEW_FILTER_DISABLE = "3";

	final static public String CAP_VIEW_FILTER_ENABLE = "4";

	// 视图状态:0不可访问，1可访问
	final static public String[] viewStatus = {
			CAP_VIEW_RESTRICTED, CAP_VIEW_ACCESSIBLE
	};

	// 视图filter状态:0:不生效,1生效
	final static public String[] viewFilterStatus = {
			CAP_VIEW_FILTER_DISABLE, CAP_VIEW_FILTER_ENABLE
	};

	// 视图结果字段状态:0不可见，1可见
	final static public String[] viewResultStatus = {
			CAP_VIEW_NONE, CAP_VIEW_RESTRICTED, CAP_VIEW_ACCESSIBLE
	};

	// 视图操作状态:0不可见，1可见
	final static public String[] viewActionStatus = {
			CAP_VIEW_RESTRICTED, CAP_VIEW_ACCESSIBLE
	};

	/**
	 * 创建视图资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param viewName
	 * @return
	 */
	public static DefaultManagedResource createViewResource(String moduleId, String viewId, String viewName) {
		DefaultManagedResource srouce = new DefaultManagedResource(null, generateViewResourceId(moduleId, viewId), viewName, viewStatus, CAP_VIEW, null, null);
		return srouce;
	}

	/**
	 * 创建视图过滤器资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param filterId
	 * @param filterName
	 * @return
	 */
	public static DefaultManagedResource createViewFilterResource(DefaultManagedResource parent, String moduleId, String viewId, String filterId,
			String filterName) {
		DefaultManagedResource srouce = new DefaultManagedResource(parent, generateViewFilterResourceId(moduleId, viewId, filterId), filterName,
				viewFilterStatus, CAP_VIEW_FILTER, null, null);
		return srouce;
	}

	/**
	 * 创建视图结果字段资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param resultField
	 * @param displayName
	 * @return
	 */
	public static DefaultManagedResource createViewResultFieldResource(DefaultManagedResource parent, String moduleId, String viewId, String resultField,
			String displayName) {
		DefaultManagedResource srouce = new DefaultManagedResource(parent, generateViewResultFeildResourceId(moduleId, viewId, resultField), displayName,
				viewResultStatus, CAP_VIEW_RESULT, null, null);
		return srouce;
	}

	/**
	 * 生成view的资源id，生成规则：CAP_VIEW前缀+模块id+视图id
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	public static String generateViewResourceId(String moduleId, String viewId) {
		return CAP_VIEW + "." + moduleId + "." + viewId;
	}

	/**
	 * 生成view filer资源id,生成规则CAP_VIEW_FILTER前缀+模块id+视图id+filterId
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param filterId
	 * @return
	 */
	public static String generateViewFilterResourceId(String moduleId, String viewId, String filterId) {
		return CAP_VIEW_FILTER + "." + moduleId + "." + viewId + "." + filterId;
	}

	/**
	 * 生成view resultField资源id,生成规则CAP_VIEW_FILTER前缀+模块id+视图id+filterId
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param fieldName
	 * @return
	 */
	public static String generateViewResultFeildResourceId(String moduleId, String viewId, String fieldName) {
		return CAP_VIEW_RESULT + "." + moduleId + "." + viewId + "." + fieldName;
	}

	/**
	 * 获取视图状态
	 * 
	 * @param moduleId
	 * @param viewId
	 * @return
	 */
	public static String getViewState(String moduleId, String viewId) {
		String resId = generateViewResourceId(moduleId, viewId);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_VIEW);
		if (status == null || status.length == 0)
			return CAP_VIEW_ACCESSIBLE;
		for (String state : status) {
			if (state.equals(CAP_VIEW_ACCESSIBLE))
				return CAP_VIEW_ACCESSIBLE;
		}
		return CAP_VIEW_RESTRICTED;

	}

	/**
	 * 获取视图过滤器状态
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param filterId
	 * @return
	 */
	public static String getViewFilterState(String moduleId, String viewId, String filterId) {
		String resId = generateViewFilterResourceId(moduleId, viewId, filterId);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_VIEW_FILTER);
		if (status == null || status.length == 0)
			return CAP_VIEW_FILTER_DISABLE;
		for (String state : status) {
			if (state.equals(CAP_VIEW_FILTER_DISABLE))
				return CAP_VIEW_FILTER_DISABLE;
		}
		return CAP_VIEW_FILTER_ENABLE;
	}

	/**
	 * 获取视图结果字段状态 权限优先级：CAP_VIEW_NONE>CAP_VIEW_ACCESSIBLE>CAP_VIEW_RESTRICTED
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param field
	 * @return
	 */
	public static String getViewResultFieldState(String moduleId, String viewId, String field) {
		String result = null;
		String resId = generateViewResultFeildResourceId(moduleId, viewId, field);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_VIEW_RESULT);
		if (status == null || status.length == 0)
			return CAP_VIEW_NONE;
		for (String state : status) {
			if (state.equals(CAP_VIEW_NONE))
				return CAP_VIEW_NONE;
			else {
				if (state.equals(CAP_VIEW_ACCESSIBLE))
					result = CAP_VIEW_ACCESSIBLE;
				else
					result = CAP_VIEW_RESTRICTED;
			}
		}
		if (result == null)
			return CAP_VIEW_NONE;
		else
			return result;
	}

	/**
	 * 获取视图action状态
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param state
	 * @param action
	 * @return
	 */
	public static String getViewActionState(String moduleId, String viewId, String action) {

		String resId = generateViewActionResourceId(moduleId, viewId, action);
		String result = CAP_VIEW_ACCESSIBLE;
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_VIEW_ACTION);
		if (status == null || status.length == 0)
			return CAP_VIEW_ACCESSIBLE;
		for (String s : status) {
			if (s.equals(CAP_VIEW_ACCESSIBLE))
				return CAP_VIEW_ACCESSIBLE;
			else
				result = CAP_VIEW_RESTRICTED;
		}
		return result;
	}

	/**
	 * 生成ViewAction资源id,生成规则CAP_VIEW_ACTION前缀+模块id+视图id+state+action
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param state
	 * @param action
	 * @return
	 */
	public static String generateViewActionResourceId(String moduleId, String viewId, String action) {
		return CAP_VIEW_ACTION + "." + moduleId + "." + viewId + "." + action;
	}

	/**
	 * 创建视图action资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param resultField
	 * @param displayName
	 * @return
	 */
	public static DefaultManagedResource createViewActionResource(DefaultManagedResource parent, String moduleId, String viewId, String action,
			String displayName) {
		DefaultManagedResource srouce = new DefaultManagedResource(parent, generateViewActionResourceId(moduleId, viewId, action), displayName,
				viewActionStatus, CAP_VIEW_ACTION, null, null);
		return srouce;
	}
}