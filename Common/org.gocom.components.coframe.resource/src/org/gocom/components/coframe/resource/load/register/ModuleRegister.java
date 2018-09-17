package org.gocom.components.coframe.resource.load.register;

import java.io.File;

import org.gocom.components.coframe.resource.helper.DirResourceHelper;
import org.gocom.components.coframe.resource.util.ModuleCapReader;
import org.gocom.components.coframe.tools.IAuthConstants;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.system.log.LogFactory;
import com.eos.system.logging.Logger;
import com.primeton.cap.TenantManager;
import com.primeton.cap.management.resource.IManagedResource;
import com.primeton.cap.management.resource.impl.DefaultManagedResource;
import com.primeton.cap.management.resource.manager.ResourceRuntimeManager;

/**
 * 模块资源注册类，注册模块、表单、视图等模块资源
 * 
 * @author liuzn (mailto:liuzn@primeton.com)
 * 
 */
public class ModuleRegister {

	private static Logger log = LogFactory.getLogger(ModuleRegister.class);

	/**
	 * 注册模块
	 * 
	 * @param moduleModel
	 *            待注册的模块属性文件File
	 */
	public static void register(File moduleModel) {
		ModuleCapReader moduleLoader = new ModuleCapReader(moduleModel);
		ResourceRuntimeManager.getInstance().registerManagedResource(
				adapt(moduleLoader));
	}

	/**
	 * 注销模块
	 * 
	 * @param moduleModel
	 *            待注销的模块属性文件File
	 */
	public static void unRegister(File moduleModel) {
		removeModule(moduleModel);
		
		ResourceRuntimeManager.getInstance().unRegisterManagedResource(
				moduleModel.getParentFile().getName(), IConstants.MODULE_RES_TYPE_ID);
	}
	
	private static void removeModule(File moduleModel) {
		String moduleId = moduleModel.getParentFile().getName();
		if (moduleId != null) {
			DirResourceHelper.removeModule(moduleId);
		}
	}

	private static IManagedResource adapt(ModuleCapReader moduleLoader) {
		IManagedResource resource = new DefaultManagedResource(null,
				moduleLoader.getModuleId(), moduleLoader.getModuleName(),
				IAuthConstants.FUNCTION_TO_STATES,
				IConstants.MODULE_RES_TYPE_ID, null, "", true, TenantManager
						.getCurrentTenantID());
		return resource;
	}
}
