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
package org.gocom.components.coframe.auth.startup;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.gocom.components.coframe.auth.party.config.PartyModel;
import org.gocom.components.coframe.auth.party.config.PartyModelConfiguration;
import org.gocom.components.coframe.auth.party.config.PartyTypeModel;
import org.gocom.components.coframe.auth.party.config.PartyTypeRefModel;
import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;
import org.gocom.components.coframe.auth.service.IAuthPartyService;
import org.gocom.components.coframe.init.CoframePartyUserInitService;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.access.http.UserLoginCheckedFilter;
import com.eos.access.http.WebInterceptorConfig;
import com.eos.access.http.WebInterceptorManager;
import com.eos.infra.config.Configuration;
import com.eos.infra.config.Configuration.Group;
import com.eos.infra.config.Configuration.Module;
import com.eos.runtime.resource.ContributionMetaData;
import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
import com.eos.spring.BeanFactory;
import com.eos.system.logging.Logger;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.manager.AuthManagerServiceLoader;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.management.resource.IMenuResourceManager;
import com.primeton.cap.management.resource.manager.ResourceConfigurationManager;
import com.primeton.cap.party.IPartyManagerService;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.PartyType;
import com.primeton.cap.party.PartyTypeRef;
import com.primeton.cap.party.manager.PartyManagerServiceLoader;
import com.primeton.cap.party.manager.PartyTypeManager;
import com.primeton.cap.party.manager.PartyTypeRefManager;

/**
 * 组织权限管理构件包启动监听器
 * 
 * @author wuyuhou
 * 
 */
public class AuthStartupContributionListener implements IContributionListener {

	private static final Logger log = LoggerFactory
			.getLogger(AuthStartupContributionListener.class);

	public void load(IContributionEvent event) {

	}

	// 加载WebInterceptor
	private void loadWebInterceptor(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		Module module = contributionConfig.getModule("WebInterceptor");
		for (Group group : module.getGroups().values()) {
			String id = group.getConfigValue("id");
			String idxStr = group.getConfigValue("idxStr");
			String pattern = group.getConfigValue("pattern");
			String className = group.getConfigValue("class");
			WebInterceptorConfig config = new WebInterceptorConfig();
			config.setFilterId(id);
			config.setClassName(className);
			if (pattern != null && !pattern.equals(""))
				config.setPattern(pattern);
			if (idxStr != null && !idxStr.equals("")) {
				int idx = new Integer(idxStr);
				if (idx < 0) {
					continue;
				}
				config.setSortIdx(idx);
			}
			WebInterceptorManager.INSTANCE.removeInterceptorConfig(id);
			if ("FunctionWebInterceptor".equals(group.getName())) {
				String isRegister = group.getConfigValue("isRegister");
				if (isRegister.equals("true")) {
					WebInterceptorManager.INSTANCE.addInterceptorConfig(config);
				}
			} else {
				WebInterceptorManager.INSTANCE.addInterceptorConfig(config);
			}
		}
	}

	/**
	 * 加载授权计算的接口
	 * 
	 * @param event
	 */
	private void loadPartyRoleAuthService(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		Module module = contributionConfig.getModule("PartyRoleAuthService");
		for (Group group : module.getGroups().values()) {
			String service = group.getConfigValue("service");
			if (StringUtil.isNotEmpty(service)) {
				try {
					Class clazz = AuthStartupContributionListener.class
							.getClassLoader().loadClass(service);
					AuthPartyRuntimeService.addAuthPartyService(
							group.getName(), (IAuthPartyService) clazz
									.newInstance());
				} catch (Throwable t) {
					log
							.error(
									"loadPartyAuthService[IAuthPartyService={0}] error.",
									new Object[] { service }, t);
				}
			}
		}
	}

	// 加载party管理服务
	private void loadPartyService(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		String service = contributionConfig.getConfigValue("Party", "Service",
				"PartyManagerService");
		if (StringUtil.isNotEmpty(service)) {
			try {
				Class clazz = AuthStartupContributionListener.class
						.getClassLoader().loadClass(service);
				PartyManagerServiceLoader
						.setCurrentPartyManagerService((IPartyManagerService) clazz
								.newInstance());
			} catch (Throwable t) {
				log.error("loadPartyService[IPartyManagerService={0}] error.",
						new Object[] { service }, t);
			}
		}
	}

	// 加载auth管理服务
	private void loadAuthService(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		String service = contributionConfig.getConfigValue("Auth", "Service",
				"AuthManagerService");
		if (StringUtil.isNotEmpty(service)) {
			try {
				Class clazz = AuthStartupContributionListener.class
						.getClassLoader().loadClass(service);
				AuthManagerServiceLoader
						.setCurrentPartyManagerService((IAuthManagerService) clazz
								.newInstance());
			} catch (Throwable t) {
				log.error("loadAuthService[IAuthManagerService={0}] error.",
						new Object[] { service }, t);
			}
		}
	}

	/**
	 * 加载Party模型配置
	 * 
	 * @param event
	 *            监听事件对象
	 */

	private void loadPartyTypeConfig(IContributionEvent event) {
		ContributionMetaData metaData = event.getContributionMetaData();
		PartyModelConfiguration partyConfiguration = new PartyModelConfiguration(
				new File(metaData.getContributionLocation(),
						"META-INF/party-config.xml"));
		PartyModel partyModel = partyConfiguration.getPartyModel();
		loadPartyModel(partyModel);
		loadPartyRefModel(partyModel);
	}

	/**
	 * 加载Party模型
	 * 
	 * @param partyModel
	 *            参与者模型
	 */
	private void loadPartyModel(PartyModel partyModel) {
		List<PartyTypeModel> partyTypeModelListList = partyModel
				.getPartyTypeModelList();
		for (PartyTypeModel partyTypeModel : partyTypeModelListList) {
			String typeID = partyTypeModel.getTypeID();

			PartyType partyType = new PartyType();
			partyType.setTypeID(typeID);
			partyType.setName(partyTypeModel.getName());
			partyType.setDescription(partyTypeModel.getDescription());
			String priority = partyTypeModel.getPriority();
			try {
				partyType.setPriority(Integer.parseInt(priority));
			} catch (Exception e) {
				partyType.setPriority(0);
			}

			if ("true".equals(partyTypeModel.getIsRole())) {
				partyType.setRole(true);
			}

			if ("true".equals(partyTypeModel.getShowInTree())) {
				partyType.setShowInTree(true);
			}

			partyType.setIcon16path(partyTypeModel.getIcon16());
			partyType.setIcon32path(partyTypeModel.getIcon32());
			if ("true".equals(partyTypeModel.getShowAtRoot())) {
				partyType.setShowAtRoot(true);
			}
			if ("true".equals(partyTypeModel.getIsLeaf())) {
				partyType.setLeaf(true);
			}
			Properties partyTypeExtProp = partyTypeModel.getExtProperties();
			Iterator it = partyTypeExtProp.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				partyType.putExtAttribute(key, partyTypeExtProp
						.getProperty(key));
			}
			PartyTypeManager.getInstance().putPartyType(typeID, partyType);

			String partyTypeDataServiceStr = partyTypeModel
					.getPartyTypeDataService();
			if (StringUtil.isNotEmpty(partyTypeDataServiceStr)) {
				try {
					Class clazz = AuthStartupContributionListener.class
							.getClassLoader()
							.loadClass(partyTypeDataServiceStr);
					PartyTypeManager
							.getInstance()
							.putPartyTypeDataService(typeID,
									(IPartyTypeDataService) clazz.newInstance());
				} catch (Throwable t) {
					log
							.error(
									"loadPartyModel[typeID={0}][IPartyTypeDataService={1}] error.",
									new Object[] { typeID,
											partyTypeDataServiceStr }, t);
				}
			}
		}
	}

	/**
	 * 加载参与者引用模型
	 * 
	 * @param partyModel
	 *            参与者模型
	 */
	private void loadPartyRefModel(PartyModel partyModel) {
		List<PartyTypeRefModel> partyTypeRefModelList = partyModel
				.getPartyTypeRefModelList();
		for (PartyTypeRefModel partyTypeRefModel : partyTypeRefModelList) {
			String refID = partyTypeRefModel.getRefID();
			PartyTypeRef partyTypeRef = new PartyTypeRef();
			partyTypeRef.setRefID(refID);
			partyTypeRef.setRefName(partyTypeRefModel.getRefName());

			String refType = partyTypeRefModel.getRefType();
			partyTypeRef.setRefType(refType);
			// 父子关系必须设置父子两个partyType属性
			String parentPartyTypeStr = partyTypeRefModel
					.getParentPartyTypeID();
			String childPartyTypeStr = partyTypeRefModel.getChildPartyTypeID();
			PartyType parentPartyType = PartyTypeManager.getInstance()
					.getPartyType(parentPartyTypeStr);
			PartyType childPartyType = PartyTypeManager.getInstance()
					.getPartyType(childPartyTypeStr);
			partyTypeRef.setParentPartyType(parentPartyType);
			partyTypeRef.setChildPartyType(childPartyType);
			Properties partyTypeRefExtProp = partyTypeRefModel
					.getExtProperties();
			Iterator it = partyTypeRefExtProp.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				partyTypeRef.putExtAttribute(key, partyTypeRefExtProp
						.getProperty(key));
			}
			PartyTypeRefManager.getInstance().putPartyTypeRef(refID,
					partyTypeRef);
			String partyTypeRefDataServiceStr = partyTypeRefModel
					.getPartyTypeRefDataService();
			if (StringUtil.isNotEmpty(partyTypeRefDataServiceStr)) {
				try {
					Class clazz = AuthStartupContributionListener.class
							.getClassLoader().loadClass(
									partyTypeRefDataServiceStr);
					PartyTypeRefManager.getInstance()
							.putPartyTypeRefDataService(
									refID,
									(IPartyTypeRefDataService) clazz
											.newInstance());
				} catch (Throwable t) {
					log
							.error(
									"loadPartyRefModel[refID={0}][IPartyTypeRefDataService={1}] error.",
									new Object[] { refID,
											partyTypeRefDataServiceStr }, t);
				}
			}

		}
		// 加载完成后形成组织机构树的模型
		PartyTypeRefManager.getInstance().calculatePartyTypeTreeModel();

		// party登录后，需要缓存相关party数据，那么需要一棵描述party的树模型，这个模型和组织机构树的区别在于：
		// 1. 组织机构树是参照partyType中是否根节点和是否子节点来形成树的，而此模型是以角色PartyType为单一的根向下延伸的
		// 2. 如果某个partyType没有和角色有直接或间接的关联，那么此partyType是不考虑的
		PartyTypeRefManager.getInstance().calculateLoginPartyTypeTreeModel();
	}

	// 加载菜单资源配置
	private void loadMenuResourceModelConfig(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		Module module = contributionConfig.getModule("MenuResourceType");
		for (Group group : module.getGroups().values()) {
			String typeId = group.getConfigValue("TypeID");
			String typeName = group.getConfigValue("TypeName");
			String menuResourceManager = group
					.getConfigValue("MenuResourceManager");
			ResourceConfigurationManager.getInstance().putResourceTypeName(
					typeId, typeName);
			if (StringUtil.isNotEmpty(menuResourceManager)) {
				try {
					Class clazz = AuthStartupContributionListener.class
							.getClassLoader().loadClass(menuResourceManager);
					ResourceConfigurationManager.getInstance()
							.putMenuResourceManager(typeId,
									(IMenuResourceManager) clazz.newInstance());
				} catch (Throwable t) {
					log
							.error(
									"loadMenuResourceModelConfig[typeId={0}][menuResourceManager={1}] error.",
									new Object[] { typeId, menuResourceManager },
									t);
				}
			}
		}
	}

	public void loadFinished(IContributionEvent event) {
		// 初始化，主要是为了让授权池作为监听器注册到可授权资源池
		AuthRuntimeManager.getInstance();

		PartyManagerServiceLoader
				.setCurrentPartyUserInitService(new CoframePartyUserInitService());
		// 注册功能资源
		BeanFactory beanFactory = BeanFactory.newInstance();
		// 暂时去掉功能管理部分内容
		// DefaultFunctionManager bean =
		// beanFactory.getBean(DefaultFunctionManager.SPRING_BEAN_NAME);
		// bean.initFunctions();

		// 提升登录性能，将角色资源映射加载出来
		List<Party> rolePartyList = AuthRuntimeManager.getInstance()
				.getAllRolePartyList();
		for (Party roleParty : rolePartyList) {
			AuthRuntimeManager.getInstance().getAuthResListByRole(roleParty);
		}

		loadWebInterceptor(event);
		loadPartyService(event);
		loadAuthService(event);
		loadPartyTypeConfig(event);
		loadMenuResourceModelConfig(event);
		loadPartyRoleAuthService(event);
	}

	public void unLoad(IContributionEvent event) {

	}
}