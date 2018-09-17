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

import org.gocom.components.coframe.resource.dir.DirResource;
import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.helper.ViewResourceHelper;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.logging.Logger;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;
import com.primeton.cap.resource.helper.ApplicationResourceHelper;
import com.primeton.cap.view.model.Action;
import com.primeton.cap.view.model.Filter;
import com.primeton.cap.view.model.ResultItem;
import com.primeton.cap.view.model.ViewModel;
import com.primeton.cap.view.model.helper.ViewModelHelper;
/**
 * 
 * 视图资源注册类，注册视图、查询结果、过滤器、操作等资源
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ViewRegister {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewRegister.class);

	/**
	 * 注册视图资源
	 * 
	 * @param viewModel
	 */
	public static void register(File viewModel) {
		ViewModel vm = ViewModelHelper.createViewModel(viewModel);
		String moduleId = null;
		try {
			moduleId = ApplicationResourceHelper.getModuleFromRunningFile(viewModel);
		} catch (Exception e) {
			logger.error("Error to get module from view model [" + viewModel.getName() + "]", e);
		}

		String viewId = vm.getId();
		String viewName = vm.getName();

		DefaultManagedResource viewResource = ViewResourceHelper.createViewResource(moduleId, viewId, viewName);
		DirResource dirView = new DirResource(moduleId, viewResource.getResourceID(), ViewResourceHelper.CAP_VIEW, viewName);
		//注册过滤器
		regFilter(dirView, viewResource, moduleId, viewId, vm.getFilters());
		//注册查询结果
		regResult(dirView, viewResource, moduleId, viewId, vm.getResult().getResults());
		//注册视图操作按钮
		regAction(dirView, viewResource, moduleId, viewId, vm.getActions());
		//注册视图资源
		ResourceRuntimeManager.getInstance().registerManagedResource(viewResource);

		DirResourceHelper.addView(dirView);
	}

	/**
	 * 删除注册的资源
	 * 
	 * @param viewModel
	 */
	public static void unRegister(File viewModel) {
		ViewModel vm = ViewModelHelper.createViewModel(viewModel);
		String moduleId = null;
		try {
			moduleId = ApplicationResourceHelper.getModuleFromRunningFile(viewModel);
		} catch (Exception e) {
			logger.error("Error to get module from view model [" + viewModel.getName() + "]", e);
		}

		String viewId = vm.getId();
		String viewName = vm.getName();

		DefaultManagedResource viewResource = ViewResourceHelper.createViewResource(moduleId, viewId, viewName);
		DirResource dirView = new DirResource(moduleId, viewResource.getResourceID(), ViewResourceHelper.CAP_VIEW, viewName);

		// regFilter(dirView, viewResource, moduleId, viewId, vm.getFilters());
		//		
		// regResult(dirView, viewResource, moduleId, viewId, vm.getResult()
		// .getResults());
		//
		// regAction(dirView, viewResource, moduleId, viewId, vm.getActions());

		ResourceRuntimeManager.getInstance().unRegisterManagedResource(viewResource.getResourceID(), viewResource.getResourceType());

		DirResourceHelper.deleteView(dirView);
	}

	/**
	 * 注册视图中的filter资源
	 * 
	 * @param parent
	 * @param moduleId
	 * @param viewId
	 * @param filters
	 */
	private static void regFilter(DirResource dirParent, DefaultManagedResource parent, String moduleId, String viewId, List<Filter> filters) {
		for (Filter filter : filters) {

			DefaultManagedResource filterResource = ViewResourceHelper.createViewFilterResource(parent, moduleId, viewId, filter.getId(), filter.getName());
			DirResource dirFilter = new DirResource(moduleId, filterResource.getResourceID(), ViewResourceHelper.CAP_VIEW_FILTER, filter.getName());
			dirParent.addChild(dirFilter);
			parent.addChildManagedResource(filterResource);
		}

	}

	/**
	 * 注册视图中的resultField资源
	 * 
	 * @param parent
	 * @param moduleId
	 * @param viewId
	 * @param results
	 */
	private static void regResult(DirResource dirParent, DefaultManagedResource parent, String moduleId, String viewId, List<ResultItem> results) {
		for (ResultItem result : results) {
			DefaultManagedResource resultResource = ViewResourceHelper.createViewResultFieldResource(parent, moduleId, viewId, result.getFieldName(), result
					.getDisplayName());
			DirResource dirResult = new DirResource(moduleId, resultResource.getResourceID(), ViewResourceHelper.CAP_VIEW_RESULT, result.getDisplayName());
			dirParent.addChild(dirResult);
			parent.addChildManagedResource(resultResource);
		}
	}

	/**
	 * 注册视图中的action资源
	 * 
	 * @param dirParent
	 * @param parent
	 * @param moduleId
	 * @param viewId
	 * @param actions
	 */
	private static void regAction(DirResource dirParent, DefaultManagedResource parent, String moduleId, String viewId, List<Action> actions) {
		for (Action action : actions) {
			DefaultManagedResource actionResource = ViewResourceHelper.createViewActionResource(parent, moduleId, viewId, action.getId(), action.getName());
			DirResource dirResult = new DirResource(moduleId, actionResource.getResourceID(), ViewResourceHelper.CAP_VIEW_ACTION, action.getName());
			dirParent.addChild(dirResult);
			parent.addChildManagedResource(actionResource);
		}
	}

}