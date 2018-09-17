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



import java.util.List;

import org.gocom.components.coframe.resource.FormAuthService;
import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.FormResourceHelper;

import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;
import com.eos.system.exception.EOSRuntimeException;
import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.eos.system.utility.StringUtil;
import com.eos.workflow.api.BPSServiceClientFactory;
import com.eos.workflow.api.IBPSServiceClient;
import com.eos.workflow.api.IWFDefinitionQueryManager;
import com.eos.workflow.api.IWFQueryManager;
import com.eos.workflow.data.WFActivityDefine;
import com.eos.workflow.data.WFProcessDefine;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFServiceException;
import commonj.sdo.DataObject;

/**
 * 流程配置的统一对外接口
 * 
 * @author liulei
 * 
 */
public class ProcessConfigHelper extends DASDaoSupport{
	// 创建ProcessConfigHelper实例
	private static ProcessConfigHelper INSTANCE = new ProcessConfigHelper();

	// 日志处理对象
	private static Logger logger = LogFactory.getLogger(ProcessConfigHelper.class);

	// 业务查询
	private static IWFDefinitionQueryManager proDefManager = null;

	// BPS 服务客户端
	private static IBPSServiceClient client = null;

	// 初始化IBPSServiceClient和IWFDefinitionQueryManager
	static {
		try {
			client = BPSServiceClientFactory.getDefaultClient();
			proDefManager = client.getDefinitionQueryManager();
		} catch (WFServiceException e) {
			new EOSRuntimeException("初始化IBPSServiceClient或IWFDefinitionQueryManager 出错", e);
		}
	}

	private ProcessConfigHelper() {

	}

	/**
	 * 获取ProcessConfigHelper实例
	 * 
	 * @return
	 */
	public static ProcessConfigHelper getInstance() {
		return INSTANCE;
	}

	/**
	 * 查询流程信息列表
	 * 
	 * @param proc 流程实体
	 * @param pageCond 翻页实体
	 * @return 流程信息列表
	 *//*
	public Wfprocessdefine[] getProDefs(DataObject proc, DataObject pageCond) {

		String procId = proc.getString("procId");
		String procName = proc.getString("procName");
		List<ExprType> exList = new ArrayList<ExprType>();
		if (StringUtil.isNotEmpty(procId)) {
			ExprType ex = new ExprTypeImpl();
			ex.set_op("like");
			ex.set_property(ENTITY_PROCESSDEFINE_NAME);
			ex.set_value(procId);
			exList.add(ex);
		}
		if (StringUtil.isNotEmpty(procName)) {
			ExprType ex = new ExprTypeImpl();
			ex.set_op("like");
			ex.set_property(ENTITY_PROCESSDEFINE_CHNAME);
			ex.set_value(procName);
			exList.add(ex);
		}
		CriteriaType criteriaType = CriteriaType.FACTORY.create();
		criteriaType.set_entity("org.gocom.cap.auth.flow.entity.Wfprocessdefine");
		if (exList.size() > 0) {
			criteriaType.set_expr(exList);
		}
		criteriaType.set("_order[1]/_asc", ENTITY_PROCESSDEFINE_NAME);
		DataObject[] objs = DatabaseExt.queryEntitiesByCriteriaEntityWithPage(DATA_SOURCE, criteriaType, pageCond);
		List<Wfprocessdefine> defs = new ArrayList<Wfprocessdefine>();
		for (DataObject obj : objs) {
			if (obj instanceof Wfprocessdefine) {
				defs.add((Wfprocessdefine) obj);
			}
		}
		return defs.toArray(new Wfprocessdefine[defs.size()]);
	}*/
	
	public List getProDefs(IDASCriteria criteria, PageCond pageCond){
		IWFQueryManager wfQueryManager = client.getCommonQueryManage(); 
		try {
			List<WFProcessDefine> processList = wfQueryManager.queryProcessesCriteria(criteria,pageCond);
			return processList;
		} catch (WFServiceException e) {
			e.printStackTrace();
		} 
		return null;
		
		
		
		
	}
	
	

	/**
	 * 解析环节
	 * 
	 * @param proc 流程实体信息
	 */
	public void parseAct(DataObject proc) {
		String procId = proc.getString("procId");
		String actId = proc.getString("actId");
		try {
			long procDefId = Long.parseLong(procId);
			String extendInfo = proDefManager.getExtendAttribute(procDefId, actId);
			if (StringUtil.isEmpty(extendInfo)) {
				return;
			}
			String isSpecifyFormTag = "<isSpecifyForm>";
			String formIdTag = "<formId>";
			String formStatusTag = "<formStatus>";
			String moduleIdTag = "moduleId=";
			int begin = extendInfo.indexOf(isSpecifyFormTag) + isSpecifyFormTag.length();
			int end = extendInfo.indexOf("</isSpecifyForm>", begin);
			String fromFlag = extendInfo.substring(begin, end).trim();
			if (!"true".equals(fromFlag)) {
				return;
			}
			WFActivityDefine activity = proDefManager.getActivity(procDefId, actId);

			String urlInfo = activity.getUrlID();
			begin = extendInfo.indexOf(formIdTag) + formIdTag.length();
			end = extendInfo.indexOf("</formId>", begin);
			String formId = extendInfo.substring(begin, end).trim();
			begin = urlInfo.indexOf(moduleIdTag) + moduleIdTag.length();
			end = urlInfo.indexOf("&", begin);
			String moduleId = urlInfo.substring(begin, end).trim();
			begin = extendInfo.indexOf(formStatusTag) + formStatusTag.length();
			end = extendInfo.indexOf("</formStatus>", begin);
			String state = extendInfo.substring(begin, end).trim();
			String stateId = FormResourceHelper.generateFormStateResourceId(moduleId, formId, state);
			String formName = this.getFormName(moduleId, formId);
			proc.setString("moduleId", moduleId);
			proc.setString("formId", formId);
			proc.setString("stateId", stateId);
			proc.setString("formName", formName);
			String nameSpace = AuthFlowRuleHelper.getNameSpace(moduleId, procId, actId, formId, state);
			proc.setString("nameSpace", nameSpace);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 获取表单名字
	 * 
	 * @param moduleId 模块ID
	 * @param formId 表单ID
	 * @return 表单名字
	 */
	public String getFormName(String moduleId, String formId) {
		DirResource dr = FormAuthService.getForm(moduleId, formId);
		if (dr != null) {
			return dr.getName();
		}
		return "";
	}

	/**
	 * 查询是否是子流程
	 * 
	 * @param proc 流程实体信息
	 * @return 1：是 0：否
	 */
	public DataObject isSubForm(DataObject proc) {
		String procId = proc.getString("procId");
		String actId = proc.getString("actId");
		try {
			long procDefId = Long.parseLong(procId);
			WFActivityDefine activity = proDefManager.getActivity(procDefId, actId);
			boolean isSubFlow = activity.isSubProcess();
			// 该环节关联子流程
			if (isSubFlow) {
				String subFlowName = activity.getSubProcessDefName();
				List<WFProcessDefine> defs = proDefManager.queryProcessesByName(subFlowName, WFConstants.PROC_PUBLISH_QUERY_PUBLISHED, new PageCond(
						Integer.MAX_VALUE));
				if (defs.size() < 1) {
					proc.setString("ret", "0");
				} else {
					WFProcessDefine def = defs.get(0);
					proc.setString("procId", String.valueOf(def.getProcessDefID()));
					proc.setString("procName", def.getProcessDefName());
					proc.setString("pchName", def.getProcessChName());
					proc.setString("version", def.getVersionSign());
					proc.setString("ret", "1");
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return proc;
	}

	/**
	 * 获取环节名称
	 * 
	 * @param procId 流程ID
	 * @param actId 活动ID
	 * @return 环节名称
	 */
	public static String getActName(String procId, String actId) {
		try {
			long procDefId = Long.parseLong(procId);
			WFActivityDefine activity = proDefManager.getActivity(procDefId, actId);
			return activity.getName();
		} catch (Exception e) {
			logger.error(e);
		}
		return "";
	}
}

