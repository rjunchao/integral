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
package org.gocom.components.coframe.flowconfig.helper;


import java.util.ArrayList;
import java.util.List;

import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.spi.auth.rule.RuleResourcePrivilege;

/**
 * AuthResource 适配器
 * 
 * @author wanghl (mailto:wanghl@primeton.com)
 */

public class AuthResourceAdapor {
	/**
	 * 将授权资源 数据适配为特权资源
	 * 
	 * @param ar 授权资源
	 * @param ruleId 规则ID
	 * @return 特权资源
	 */
	public static RuleResourcePrivilege authResource2Privilege(AuthResource ar, String ruleId) {
		RuleResourcePrivilege rp = new RuleResourcePrivilege();
		rp.setId(AuthFlowRuleHelper.getRandomId());
		rp.setTenantId(TenantManager.getCurrentTenantID());
		rp.setRuleId(ruleId);
		rp.setResourceId(ar.getResourceID());
		rp.setResourceType(ar.getResourceType());
		rp.setState(ar.getState());
		return rp;
	}

	/**
	 * 将规则特权资源适配为授权资源
	 * 
	 * @param rp 规则特权资源
	 * @return 授权资源
	 */
	public static AuthResource privilege2AuthResource(RuleResourcePrivilege rp) {
		AuthResource ar = new AuthResource();
		ar.setResourceID(rp.getResourceId());
		ar.setResourceType(rp.getResourceType());
		ar.setState(rp.getState());
		return ar;
	}

	/**
	 * 批处理将规则特权资源适配为授权资源
	 * 
	 * @param rps 规则特权资源
	 * @return 授权资源
	 */
	public static AuthResource[] privilege2AuthResourceBatch(RuleResourcePrivilege[] rps) {
		List<AuthResource> ars = new ArrayList<AuthResource>();
		for (RuleResourcePrivilege rp : rps) {
			ars.add(privilege2AuthResource(rp));
		}

		return ars.toArray(new AuthResource[ars.size()]);
	}

	/**
	 * 批处理将授权资源适配为规则特权资源
	 * 
	 * @param rps 规则特权资源
	 * @return 规则特权资源
	 */
	public static List<RuleResourcePrivilege> authResource2PrivilegeBatch(AuthResource[] ars, String ruleId) {
		List<RuleResourcePrivilege> rps = new ArrayList<RuleResourcePrivilege>();
		for (AuthResource ar : ars) {
			rps.add(authResource2Privilege(ar, ruleId));
		}
		return rps;
	}
}
