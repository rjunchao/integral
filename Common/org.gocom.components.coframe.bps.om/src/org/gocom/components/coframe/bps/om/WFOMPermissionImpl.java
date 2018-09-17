/*******************************************************************************
 * $Header:
 * /cvsroot/PCAP700/develop/core/opensource/org.gocom.cap.auth/org.gocom.cap.auth.base/src/org/gocom/cap/auth/base/workflow/party/impl/WFOMPermissionImpl.java,v
 * 1.3 2012/11/02 09:32:28 liuxiang Exp $ $Revision: 1.5 $ $Date: 2012/11/02
 * 09:32:28 $
 * 
 * ==============================================================================
 * 
 * Copyright (c) 2001-2012 Primeton Technologies, Ltd. All rights reserved.
 * 
 * Created on 2012-6-19
 ******************************************************************************/

package org.gocom.components.coframe.bps.om;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eos.engine.component.ILogicComponent;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.exception.EOSRuntimeException;
import com.eos.system.logging.Logger;
import com.eos.workflow.omservice.IWFPermissionService;
import com.primeton.ext.engine.component.LogicComponentFactory;

/**
 * TODO 此处填写 class 信息
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class WFOMPermissionImpl implements IWFPermissionService {

	private Logger logger = TraceLoggerFactory
			.getLogger(WFOMPermissionImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFPermissionService#getPermissionList(java.lang.String)
	 */
	public List<String> getPermissionList(String userID) {
		// List<Party>rolePartyList =
		// AuthRuntimeManager.getInstance().getAllRolePartyList();
		List<String> returnList = new ArrayList<String>();
		// for(Party party : rolePartyList){
		// returnList.add(party.getCode());
		// }
		returnList.add("bpsclient");
		// returnList.add("bpsadmin");
		returnList.add("bpsmanager");
		returnList.add("bpscustom");
		returnList.add("bpsconfig");
		returnList.add("bpsbizcat");
		returnList.add("bpsstatis");
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFPermissionService#hasPermission(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean hasPermission(String userID, String permType) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFPermissionService#validate(java.lang.String,
	 *      java.lang.String, java.util.Map)
	 */
	public boolean validate(String userID, String pwd, Map attributes) {
		if ("sysadmin".equals(userID)) {
			try {
				return isIllegalAppUser(userID, pwd);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return false;
	}

	public boolean isIllegalAppUser(String userId, String password)
			throws Exception {
		Object[] result = null;
		// 逻辑构件名称
		String componentName = "org.gocom.components.coframe.auth.LoginManager";
		// 逻辑流名称
		String operationName = "authentication";
		ILogicComponent logicComponent = LogicComponentFactory
				.create(componentName);
		// 逻辑流的输入参数
		Object[] params = new Object[] { userId, password };
		try {
			result = logicComponent.invoke(operationName, params);
		} catch (Throwable e) {
			logger.error("Call biz [" + componentName + "." + operationName + ".biz] failed! ", e);
			throw new EOSRuntimeException(e);
		}
		// 逻辑流的返回值
		if (result.length == 1 && result[0] != null) {
			if (StringUtils.equals("1", result[0].toString())) {
				return true;
			}
		}
		return false;

	}
}