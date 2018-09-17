package org.gocom.components.coframe.flowconfig.user;

import java.util.HashMap;
import java.util.Map;

import com.eos.data.datacontext.IMUODataContext;
import com.primeton.cap.spi.auth.rule.IRuleContext;

/**
 * 工作流规则权限上下文.<br>
 * 
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class WorkflowRuleContext implements IRuleContext {
	private String appName;

	private String moduleId;

	private String formId;

	private String viewId;

	private long wfProcDefId;

	private String wfActDefId;

	private long wfProcInstId;

	private long wfWorkItemId;

	private IMUODataContext muoContext;

	private Map<String, Object> attachment = new HashMap<String, Object>();

	/**
	 * 获取应用名称.<br>
	 * 
	 * @return 应用名称
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * 设置应用名称.<br>
	 * 
	 * @param appName 应用名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 获取流程活动定义标识.<br>
	 * 
	 * @return 流程活动定义标识
	 */
	public String getWfActDefId() {
		return wfActDefId;
	}

	/**
	 * 设置流程活动定义标识.<br>
	 * 
	 * @param wfActDefId 流程活动定义标识
	 */
	public void setWfActDefId(String wfActDefId) {
		this.wfActDefId = wfActDefId;
	}

	/**
	 * 获取流程定义标识.<br>
	 * 
	 * @return 流程定义标识
	 */
	public long getWfProcDefId() {
		return wfProcDefId;
	}

	/**
	 * 设置流程定义标识.<br>
	 * 
	 * @param wfProcDefId 流程定义标识
	 */
	public void setWfProcDefId(long wfProcDefId) {
		this.wfProcDefId = wfProcDefId;
	}

	/**
	 * 获取表单标识.<br>
	 * 
	 * @return 表单标识
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * 设置表单标识.<br>
	 * 
	 * @param formId 表单标识
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * 获取模块标识.<br>
	 * 
	 * @return 模块标识
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * 设置模块标识.<br>
	 * 
	 * @param moduleId 模块标识
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * 获取视图标识.<br>
	 * 
	 * @return 视图标识
	 */
	public String getViewId() {
		return viewId;
	}

	/**
	 * 设置视图标识.<br>
	 * 
	 * @param viewId 视图标识
	 */
	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	/**
	 * 
	 * @return Returns the wfProcInstId.
	 */
	public long getWfProcInstId() {
		return wfProcInstId;
	}

	/**
	 * @param wfProcInstId The wfProcInstId to set.
	 */
	public void setWfProcInstId(long wfProcInstId) {
		this.wfProcInstId = wfProcInstId;
	}

	/**
	 * @return Returns the wfWorkItemId.
	 */
	public long getWfWorkItemId() {
		return wfWorkItemId;
	}

	/**
	 * @param wfWorkItemId The wfWorkItemId to set.
	 */
	public void setWfWorkItemId(long wfWorkItemId) {
		this.wfWorkItemId = wfWorkItemId;
	}

	/**
	 * @return Returns the muoContext.
	 */
	public IMUODataContext getMuoContext() {
		return muoContext;
	}

	/**
	 * @param muoContext The muoContext to set.
	 */
	public void setMuoContext(IMUODataContext muoContext) {
		this.muoContext = muoContext;
	}

	/**
	 * 获取附件.<br>
	 * 
	 * @return 附件
	 */
	public Map<String, Object> getAttachment() {
		return attachment;
	}

	/**
	 * 设置附件.<br>
	 * 
	 * @param attachment 附件
	 */
	public void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
}