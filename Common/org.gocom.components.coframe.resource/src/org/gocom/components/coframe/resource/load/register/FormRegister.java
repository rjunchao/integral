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
package org.gocom.components.coframe.resource.load.register;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.helper.FormResourceHelper;

import com.eos.runtime.core.ApplicationContext;
import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.primeton.cap.form.model.FormModel;
import com.primeton.cap.form.model.basemodel.FormStatus;
import com.primeton.cap.form.model.basemodel.Property;
import com.primeton.cap.form.model.basemodel.Widget;
import com.primeton.cap.form.model.manager.FormModelLoader;
import com.primeton.cap.form.model.widget.FormWidget;
import com.primeton.cap.form.model.widget.FormWidgetManager;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import com.primeton.cap.resource.helper.ApplicationResourceHelper;

/**
 * 
 * 表单资源注册类，注册表单、控件、状态等表单资源
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class FormRegister {

	private static Logger log = LogFactory.getLogger(FormRegister.class);

	private static final String SUBFORM_FIELD = "com.primeton.cap.subformfield";

	/**
	 * 注册表单
	 * 
	 * @param viewModel
	 */
	public static void register(File formModel) {

		String appName = ApplicationContext.getInstance().getAppName();

		String moduleId = null;
		try {
			moduleId = ApplicationResourceHelper
					.getModuleFromRunningFile(formModel);
		} catch (Exception e) {
			log.error("Error to get module from form model ["
					+ formModel.getName() + "]", e);
		}

		FormModel model = FormModelLoader.loadFormModel(appName, moduleId,
				formModel);
		String formId = model.getForm().getId();
		// 授权资源树---表单文件
		DirResource dirForm = new DirResource(moduleId, formId, null, model
				.getForm().getName());

		List<FormStatus.Status> status = model.getForm().getFormStatus()
				.getStatus();
		for (FormStatus.Status state : status) {

			DefaultManagedResource stateResource = FormResourceHelper
					.createFormStateResource(moduleId, formId, state.getId(),
							state.getName());
			// 授权资源树---表单状态
			DirResource dirState = new DirResource(moduleId, stateResource
					.getResourceID(), FormResourceHelper.CAP_FORM_STATE, state
					.getName());

			regFormField(dirState, stateResource, formId, moduleId, state
					.getId(), model);

			regFormAction(dirState, stateResource, formId, moduleId, state
					.getId(), state.getActionWidgets().getWidget());

			dirForm.addChild(dirState);

			ResourceRuntimeManager.getInstance().registerManagedResource(
					stateResource);
		}
		DirResourceHelper.addForm(dirForm);

	}

	/**
	 * 删除注册资源
	 * 
	 * @param formModel
	 */
	public static void unRegister(File formModel) {

		String appName = ApplicationContext.getInstance().getAppName();

		String moduleId = null;
		try {
			moduleId = ApplicationResourceHelper
					.getModuleFromRunningFile(formModel);
		} catch (Exception e) {
			log.error("Error to get module from form model ["
					+ formModel.getName() + "]", e);
		}

		FormModel model = FormModelLoader.loadFormModel(appName, moduleId,
				formModel);
		String formId = model.getForm().getId();
		// 授权资源树---表单文件
		DirResource dirForm = new DirResource(moduleId, formId, null, model
				.getForm().getName());

		List<FormStatus.Status> status = model.getForm().getFormStatus()
				.getStatus();
		for (FormStatus.Status state : status) {

			DefaultManagedResource stateResource = FormResourceHelper
					.createFormStateResource(moduleId, formId, state.getId(),
							state.getName());
			// 授权资源树---表单状态
			// DirResource dirState = new DirResource(moduleId, stateResource
			// .getResourceID(), FormResourceHelper.CAP_FORM_STATE, state
			// .getName());

			// regFormField(dirState, stateResource, formId, moduleId, state
			// .getId(), model);
			//
			// regFormAction(dirState, stateResource, formId, moduleId, state
			// .getId(), state.getActionWidgets().getWidget());
			//
			// dirForm.addChild(dirState);

			ResourceRuntimeManager.getInstance().unRegisterManagedResource(
					stateResource.getResourceID(),
					stateResource.getResourceType());
		}
		DirResourceHelper.deleteForm(dirForm);

	}

	/**
	 * 注册表单字段资源
	 * 
	 * @param parent
	 * @param formId
	 * @param moduleId
	 * @param state
	 * @param model
	 */
	private static void regFormField(DirResource dirParent,
			DefaultManagedResource parent, String formId, String moduleId,
			String state, FormModel model) {

		regFormFieldByWidgets(dirParent, parent, formId, moduleId, state, model
				.getForm().getWidget());

	}

	private static void regFormFieldByWidgets(DirResource dirParent,
			DefaultManagedResource parent, String formId, String moduleId,
			String state, Widget widget) {
		// TODO Auto-generated method stub
		if (widget.getChildren() != null) {

			for (Widget w : widget.getChildren().getWidget()) {
				try {

					if (StringUtils.isEmpty(w.getId())
							|| (SUBFORM_FIELD.equals(w.getType()))) {
						regFormFieldByWidgets(dirParent, parent, formId,
								moduleId, state, w);
						// dirParent.addChild(dirField);
					} else {
						FormWidget formWidget = FormWidgetManager.getInstance()
								.getWidget(w.getType());
						if (formWidget != null && formWidget.isAuthorization()) {
							DefaultManagedResource fieldResource = FormResourceHelper
									.createFormFieldResource(parent, moduleId,
											formId, state, w.getId(), w
													.getLabel());
							// 授权资源树---表单字段
							DirResource dirField = new DirResource(moduleId,
									fieldResource.getResourceID(),
									FormResourceHelper.CAP_FORM_FIELD, w
											.getLabel());
							regFormFieldByWidgets(dirField, parent, formId,
									moduleId, state, w);
							dirParent.addChild(dirField);
						} else {
							regFormFieldByWidgets(dirParent, parent, formId,
									moduleId, state, w);
						}

					}

				} catch (Exception e) {
					log.error("Register form field: module[" + moduleId
							+ "],form[" + formId + "],state[" + state
							+ "],field[" + w.getId() + "] occur error.", e);
				}
			}
		}
	}

	/**
	 * 注册表单action
	 * 
	 * @param parent
	 * @param formId
	 * @param moduleId
	 * @param state
	 * @param action
	 */
	private static void regFormAction(DirResource dirParent,
			DefaultManagedResource parent, String formId, String moduleId,
			String state, List<Widget> actions) {
		for (Widget action : actions) {
			boolean isaction = false;
			String type = action.getType();
			FormWidget formWidget = FormWidgetManager.getInstance().getWidget(type);
			if(formWidget!=null){
				isaction = formWidget.isAuthorization();
			}
//			for (Property p : action.getProperty()) {
//				if (p.getName().equals("isaction")) {
//					if (p.getValue().equals("true")) {
//						isaction = true;
//						break;
//					}
//
//				}
//			}
			if (!isaction)
				continue;
			try {
				DefaultManagedResource actionResource = FormResourceHelper
						.createFormActionResource(parent, moduleId, formId,
								state, action.getId(), action.getLabel());
				// 授权资源树---表单操作
				DirResource dirAction = new DirResource(moduleId,
						actionResource.getResourceID(),
						FormResourceHelper.CAP_FORM_ACTION, action.getLabel());
				dirParent.addChild(dirAction);
			} catch (Exception e) {
				log.error("Register form action: module[" + moduleId
						+ "],form[" + formId + "],state[" + state + "],action["
						+ action.getId() + "] occur error.", e);
			}
		}
	}

}