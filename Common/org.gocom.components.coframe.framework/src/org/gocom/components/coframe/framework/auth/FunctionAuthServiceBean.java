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
package org.gocom.components.coframe.framework.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.framework.constants.IAppConstants;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 *
 * @author fangwl (mailto:fangwl@primeton.com)
 */
public class FunctionAuthServiceBean {
	private Logger log = TraceLoggerFactory.getLogger(FunctionAuthServiceBean.class);
	
	private Party getParty(String roleId){
		return new Party(IConstants.ROLE_PARTY_TYPE_ID, roleId, null, null, TenantManager.getCurrentTenantID());
	}
	
	public List<Map> getFunctionCheckedTree(List<Map> functions, String roleId){
		Party party = getParty(roleId);
		List<Map> ret = new ArrayList();
		for(Map map : functions){
			if ("0".equals(map.get(IAppConstants.FUNCTION_IS_CHECK)) || "false".equals(map.get(IAppConstants.FUNCTION_IS_CHECK))) {
				/*  不需要权限校验的功能就不需要展示在树上 */
				continue;
			}
			String funccode = map.get("realId").toString();
			String funcType = map.get("type").toString();
			String resState = AuthRuntimeManager.getInstance().getAuthResourceState(party, funccode, funcType);
			if("1".equals(resState)){
				map.put("checked", true);
			}
			ret.add(map);
		}
		return ret;
	}
	
	public boolean saveAuthFunctionsBatch(DataObject[] dataObjects, String roleId){
		Party party = getParty(roleId);
		AuthRuntimeManager manager = AuthRuntimeManager.getInstance();
		List<AuthResource> authList = new ArrayList<AuthResource>();
		for(int i = 0; i < dataObjects.length; i++){
			String resId = dataObjects[i].get("realId").toString();
			String resType = dataObjects[i].get("type").toString();
			AuthResource resource = new AuthResource();
			resource.setResourceID(resId);
			resource.setResourceType(resType);
			resource.setState("1");
			authList.add(resource);
		}
		try{
			manager.delAuthResBatch(party, manager.getAuthResListByRole(party, "function"), 0);
			manager.addOrUpdateAuthResBatch(party, authList);
		}catch (Throwable t) {
			log.error("Save AuthFunctions failure, please do the operation again or contact the sysadmin.", t);
			return false;
		}
		return true;
	}
}
