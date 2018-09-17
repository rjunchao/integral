package org.gocom.components.coframe.resource.load;

import java.io.File;

import org.gocom.components.coframe.resource.load.register.ModuleRegister;

import com.primeton.ext.runtime.resource.loader.IModelLoader;
import com.primeton.ext.runtime.resource.model.IModelResource;
import com.primeton.ext.runtime.resource.model.IResourceCollection;

/**
 * 模块资源加载类，加载模块模型，并注册到可授权管控的资源库中
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 * 
 */
public class ModuleModelLoader implements IModelLoader {

	public boolean isSupportLazyLoading() {
		return false;
	}

	/**
	 * 载入模块资源时调用的方法，载入模块时向资源库注册可授权管控的模块资源
	 */
	public void load(IResourceCollection moduleModelResource) {
		IModelResource[] addedModules = moduleModelResource.addedModel();
		IModelResource[] updatedModules = moduleModelResource.modifiedModel();
		IModelResource[] deletedModules = moduleModelResource.removedModel();

		for (IModelResource addedModule : addedModules) {
			ModuleRegister.register(new File(addedModule.getPath()));
		}

		for (IModelResource updatedModule : updatedModules) {
			ModuleRegister.register(new File(updatedModule.getPath()));
		}

		for (IModelResource deletedModule : deletedModules) {
			ModuleRegister.unRegister(new File(deletedModule.getPath()));
		}
	}

}
