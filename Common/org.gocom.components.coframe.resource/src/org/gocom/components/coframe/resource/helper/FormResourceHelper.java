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

import java.util.Arrays;

import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;

/**
 * 
 * 表单资源帮助类，定义ID规则及表单授权规则
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class FormResourceHelper {

	final static public String CAP_FORM_STATE = "CAPFORMSTATE";

	final static public String CAP_FORM_FIELD = "CAPFORMFIELD";

	final static public String CAP_FORM_ACTION = "CAPFORMACTION";

	// final static private String CAP_FORM_NONE = "0";

	final static public String CAP_FORM_RESTRICTED = "1";

	final static public String CAP_FORM_ACCESSIBLE = "2";

	public static final String CAP_FORM_FIELD_HIDDEN = "2";

	public static final String CAP_FORM_FIELD_READONLY = "1";

	public static final String CAP_FORM_FIELD_EDIT = "0";

	public static final String CAP_FORM_ACTION_RESTRICTED = CAP_FORM_RESTRICTED;

	public static final String CAP_FORM_ACTION_ACCESSIBLE = CAP_FORM_ACCESSIBLE;

	final static private String[] formStatus = {
			CAP_FORM_RESTRICTED, CAP_FORM_ACCESSIBLE
	};

	final static private String[] formFiledStatus = {
			CAP_FORM_FIELD_EDIT, CAP_FORM_FIELD_READONLY, CAP_FORM_FIELD_HIDDEN
	};

	final static private String[] formActionStatus = {
			CAP_FORM_ACTION_RESTRICTED, CAP_FORM_ACTION_ACCESSIBLE
	};

	/**
	 * 创建表单状态资源
	 * 
	 * @param moduleId
	 * @param formId
	 * @param formName
	 * @return
	 */
	public static DefaultManagedResource createFormStateResource(String moduleId, String formId, String state, String formName) {
		DefaultManagedResource srouce = new DefaultManagedResource(null, generateFormStateResourceId(moduleId, formId, state), formName, formStatus,
				CAP_FORM_STATE, null, null);
		return srouce;
	}

	/**
	 * 创建表单字段资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param filterId
	 * @param filterName
	 * @return
	 */
	public static DefaultManagedResource createFormFieldResource(DefaultManagedResource parent, String moduleId, String formId, String state, String field,
			String fieldName) {
		DefaultManagedResource srouce = new DefaultManagedResource(parent, generateFormFieldResourceId(moduleId, formId, state, field), fieldName == "" ? field
				: fieldName, formFiledStatus, CAP_FORM_FIELD, null, null);
		return srouce;
	}

	/**
	 * 创建表单action资源
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param resultField
	 * @param displayName
	 * @return
	 */
	public static DefaultManagedResource createFormActionResource(DefaultManagedResource parent, String moduleId, String formId, String state, String action,
			String displayName) {
		DefaultManagedResource srouce = new DefaultManagedResource(parent, generateFormActionResourceId(moduleId, formId, state, action), displayName,
				formActionStatus, CAP_FORM_ACTION, null, null);
		return srouce;
	}

	/**
	 * 生成表单状态的资源id，生成规则：CAP_FORM_STATE前缀+模块id+表单id+state
	 * 
	 * @param moduleId
	 * @param formId
	 * @param state
	 * @return
	 */
	public static String generateFormStateResourceId(String moduleId, String formId, String state) {
		return CAP_FORM_STATE + "." + moduleId + "." + formId + "." + state;
	}

	/**
	 * 生成FormField资源id,生成规则CAP_FORM_FIELD前缀+模块id+表单id+state+field
	 * 
	 * @param moduleId
	 * @param formId
	 * @param state
	 * @param field
	 * @return
	 */
	public static String generateFormFieldResourceId(String moduleId, String formId, String state, String field) {
		return CAP_FORM_FIELD + "." + moduleId + "." + formId + "." + state + "." + field;
	}

	/**
	 * 生成FormAction资源id,生成规则CAP_FORM_ACTION前缀+模块id+表单id+state+action
	 * 
	 * @param moduleId
	 * @param formId
	 * @param state
	 * @param action
	 * @return
	 */
	public static String generateFormActionResourceId(String moduleId, String formId, String state, String action) {
		return CAP_FORM_ACTION + "." + moduleId + "." + formId + "." + state + "." + action;
	}

	/**
	 * 获取表单状态
	 * 
	 * @param moduleId
	 * @param formId
	 * @param state
	 * @return
	 */
	public static String getFormState(String moduleId, String formId, String state) {
		String resId = generateFormStateResourceId(moduleId, formId, state);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_FORM_STATE);
		return getFormState(status);

	}

	/**
	 * CAP_FORM_RESTRICTED > CAP_FORM_ACCESSIBLE
	 * 
	 * @param status
	 * @return
	 */
	public static String getFormState(String[] status) {
		if (status == null || status.length == 0)
			return null;
		String ret = CAP_FORM_ACCESSIBLE;
		for (String s : status) {
			if (CAP_FORM_RESTRICTED.equals(s)) {
				ret = CAP_FORM_RESTRICTED;
				break;
			}
		}
		return ret;
	}

	/**
	 * 获取表单action状态
	 * 
	 * @param moduleId
	 * @param formId
	 * @param state
	 * @param action
	 * @return
	 */
	public static String getFormActionState(String moduleId, String formId, String state, String action) {

		String resId = generateFormActionResourceId(moduleId, formId, state, action);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_FORM_ACTION);
		return getFormActionState(status);
	}

	public static String getFormActionState(String[] status) {
		String result = CAP_FORM_ACTION_ACCESSIBLE;
		if (status == null || status.length == 0)
			return null;
		for (String s : status) {
			if (s.equals(CAP_FORM_ACCESSIBLE))
				return CAP_FORM_ACTION_ACCESSIBLE;
			else
				result = CAP_FORM_ACTION_RESTRICTED;

		}
		return result;
	}

	/**
	 * 获取表单结果字段状态
	 * 优先级：CAP_FORM_NONE>CAP_FORM_EDIT>CAP_FORM_VIEW>CAP_FORM_RESTRICTED
	 * 
	 * @param moduleId
	 * @param viewId
	 * @param state
	 * @param field
	 * @return
	 */
	public static String getFormFieldState(String moduleId, String formId, String state, String field) {
		String resId = generateFormFieldResourceId(moduleId, formId, state, field);
		String[] status = AuthRuntimeManager.getInstance().getCurrentPartyResAuthState(resId, CAP_FORM_FIELD);
		return getFormFieldState(status);
	}

	public static String getFormFieldState(String[] status) {
		if (status == null || status.length == 0) {
			return null;
		}
		int[] intStatus = new int[status.length];
		for (int i = 0; i < status.length; i++) {
			intStatus[i] = Integer.parseInt(status[i]);
		}
		Arrays.sort(intStatus);
		return String.valueOf(intStatus[intStatus.length - 1]);
	}
}