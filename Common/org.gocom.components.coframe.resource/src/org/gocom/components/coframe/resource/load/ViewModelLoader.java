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
package org.gocom.components.coframe.resource.load;

import java.io.File;

import org.gocom.components.coframe.resource.load.register.ViewRegister;

import com.primeton.ext.runtime.resource.loader.IModelLoader;
import com.primeton.ext.runtime.resource.model.IModelResource;
import com.primeton.ext.runtime.resource.model.IResourceCollection;
/**
 * 
 * 视图模型加载类，加载视图模型，并注册到可授权资源中
 *
 * @author caozw (mailto:caozw@primeton.com)
 */
public class ViewModelLoader implements IModelLoader {

	public boolean isSupportLazyLoading() {
		return false;
	}
	/**
	 * 载入视图资源时调用的方法，载入视图资源时向资源库注册可授权管控资源
	 */
	public void load(IResourceCollection modelResource) {

		IModelResource[] addedResources = modelResource.addedModel();
		IModelResource[] updatedResources = modelResource.modifiedModel();
		IModelResource[] deleteResources = modelResource.removedModel();

		for (IModelResource resource : addedResources) {
			ViewRegister.register(new File(resource.getPath()));
		}

		for (IModelResource resource : updatedResources) {
			ViewRegister.register(new File(resource.getPath()));
		}

		for (IModelResource resource : deleteResources) {
			ViewRegister.unRegister(new File(resource.getPath()));
		}
	}

}