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
package org.gocom.components.coframe.framework;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.NumberUtils;
import org.gocom.components.coframe.framework.application.AppApplication;
import org.gocom.components.coframe.framework.application.AppFunction;
import org.gocom.components.coframe.framework.constants.IAppConstants;
import org.gocom.components.coframe.tools.IAuthConstants;

import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.spring.DASDaoSupport;
import com.eos.system.logging.Logger;
import com.primeton.cap.TenantManager;
import com.primeton.cap.management.resource.IManagedResource;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 *
 * @author fangwl (mailto:fangwl@primeton.com)
 */
public class AppFunctionService extends DASDaoSupport implements IAppFunctionService{
	private Logger log = TraceLoggerFactory.getLogger(AppFunctionService.class);
	
	public void addAppFunction(AppFunction appFunction){
		//getDASTemplate().getPrimaryKey(appFunction);
		appFunction.setTenant_id(TenantManager.getCurrentTenantID());
		try {
			getDASTemplate().insertEntity(appFunction);
			getDASTemplate().expandEntity(appFunction);
			ResourceRuntimeManager.getInstance().registerManagedResource(adapt(appFunction));
		} catch (Throwable t) {
			log.error("Insert function [funcode=" + appFunction.getFunccode() + "] failure, please do the operation again or contact the sysadmin.", t);
		}
	}

	public void deleteAppFunction(AppFunction[] appFunctions ){
		for(DataObject appFunction:appFunctions){
			try {
				getDASTemplate().deleteEntityCascade(appFunction);
				ResourceRuntimeManager.getInstance().unRegisterManagedResource(appFunction.getString("funccode"), IAuthConstants.FUNCTION_TO_RESOURCE_TYPE);
			} catch (Throwable t) {
				log.error("Delete function [funcode=" + appFunction.get("funccode") + "] failure, please do the operation again or contact the sysadmin.", t);
			}
		}
	}


	public void getAppFunction(AppFunction appFunction){
		getDASTemplate().expandEntity(appFunction);
	}
	public int validateAppFunction(AppFunction appFunction){
		return getDASTemplate().expandEntity(appFunction);
	}

    public void updateAppFunction(AppFunction appFunction){
    	appFunction.setTenant_id(TenantManager.getCurrentTenantID());
    	try {
		    getDASTemplate().updateEntity(appFunction);
		    getDASTemplate().expandEntity(appFunction);
		    ResourceRuntimeManager.getInstance().updateRegisteredManagedResource(adapt(appFunction));
	    } catch (Throwable t) {
			log.error("Update function [funcode=" + appFunction.get("funccode") + "] failure, please do the operation again or contact the sysadmin.", t);
		}
    }
	
	public int countAppFunction(CriteriaType criteria) {
		criteria.set_entity(AppFunction.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		return getDASTemplate().count(dasCriteria);
	}
	
	public AppFunction[]  queryAppFunctions(CriteriaType criteria, PageCond pageCond){
		criteria.set_entity(AppFunction.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		AppFunction[] results = getDASTemplate().queryEntitiesByCriteriaEntityWithPage(AppFunction.class, dasCriteria, pageCond);
		return results;
	}
	
	public AppFunction[]  queryAppFunctions(CriteriaType criteria){
		criteria.set_entity(AppFunction.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		AppFunction[] results = getDASTemplate().queryEntitiesByCriteriaEntity(AppFunction.class, dasCriteria);
		return results;
	}

	public void deleteFunctionById(String id) {
		AppFunction appFunction = AppFunction.FACTORY.create();
		appFunction.setFunccode(id);
		try{
			getDASTemplate().deleteEntityCascade(appFunction);
		} catch (Throwable t) {
			log.error("Delete function [funcode=" + appFunction.get("funccode") + "] failure, please do the operation again or contact the sysadmin.", t);
		}
	}


	public void initFunctions() {
		AppFunction[] functions = queryAllFunctions();
		for(AppFunction function : functions){
			ResourceRuntimeManager.getInstance().registerManagedResource(adapt(function));
		}
	}
	
	public void updateResoucesBatch(AppFunction[] appfunctions){
		for(AppFunction function : appfunctions){
			ResourceRuntimeManager.getInstance().updateRegisteredManagedResource(adapt(function));
		}
	}
	
	public AppFunction[] queryAllFunctions() {
		CriteriaType criteria = CriteriaType.FACTORY.create();
		criteria.set_entity(AppFunction.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		AppFunction[] results = getDASTemplate().queryEntitiesByCriteriaEntity(AppFunction.class, dasCriteria);
		return results;
	}
	

	@SuppressWarnings("deprecation")
	private IManagedResource adapt(AppFunction function){
		IManagedResource resource = new DefaultManagedResource(null, function.getFunccode(), function.getFuncname(), IAuthConstants.FUNCTION_TO_STATES, IAuthConstants.FUNCTION_TO_RESOURCE_TYPE,
				null, function.getFuncdesc(), true, function.getTenant_id());
		AppApplication application = function.getAppFuncgroup().getAppApplication();
		if(application!=null){
			String appType = application.getApptype();
			String protocolType = application.getProtocoltype();
			String ipAddr = application.getIpaddr();
			String ipPort = application.getIpport();
			if(ipPort == null || "".equals(ipPort)){
				ipPort = "80";
			}
			String contextPath = application.getUrl();
			if("1".equals(appType)){
				try {
					URL url = new URL(protocolType,ipAddr,NumberUtils.stringToInt(ipPort),contextPath);
					resource.addAttribute(IAppConstants.APP_URL, url.toString());
				} catch (MalformedURLException e) {
					log.error("Get Appurl [appid=" + application.getAppid() + "] failure, please do the operation again or contact the sysadmin.", e);
				}
			}
		}
		resource.addAttribute(IAppConstants.FUNCTION_IS_CHECK, function.getIscheck());
		resource.addAttribute(IAppConstants.FUNCTION_PARA_INFO, function.getParainfo());
		resource.addAttribute(IAppConstants.FUNCTION_URL, function.getFuncaction());
		return resource;
	}

	public AppFunction[] getFunctionsByAppId(String appid) {
		CriteriaType criteria = CriteriaType.FACTORY.create();
		criteria.set_entity(AppFunction.QNAME);
		criteria.set("_expr[1]/appFuncgroup.appApplication.appid", appid);
		criteria.set("_expr[1]/_op", "=");
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		AppFunction[] results = getDASTemplate().queryEntitiesByCriteriaEntity(AppFunction.class, dasCriteria);
		return results;
	}
	
	public AppFunction[] getFunctionsByFuncGroupIds(String[] funcGroupIds){
		CriteriaType criteria = CriteriaType.FACTORY.create();
		criteria.set_entity(AppFunction.QNAME);
		criteria.set("_expr[1]/appFuncgroup.funcgroupid", funcGroupIds);
		criteria.set("_expr[1]/_op","in");
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		AppFunction[] results = getDASTemplate().queryEntitiesByCriteriaEntity(AppFunction.class, dasCriteria);
		return results;
	}
}
