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
package org.gocom.components.coframe.bps.startup;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.bps.om.PartyTypeAdapterManager;
import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.infra.config.Configuration;
import com.eos.infra.config.Configuration.Group;
import com.eos.infra.config.Configuration.Module;
import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
import com.eos.system.logging.Logger;
import com.primeton.workflow.api.ParticipantType;

/**
 * 组织权限管理构件包启动监听器
 * 
 * @author wuyuhou
 * 
 */
public class OMStartupContributionListener implements IContributionListener {

	private static final Logger log = LoggerFactory
			.getLogger(OMStartupContributionListener.class);

	public void load(IContributionEvent event) {

	}

	/**
	 * 加载party type 类型 <configValue key="prefix">O</configValue> <configValue
	 * key="code">org</configValue> <configValue key="displayName">机构</configValue>
	 * <configValue key="description">机构</configValue> <configValue
	 * key="showAtRootArea">true</configValue> <configValue key="priority">4</configValue>
	 * <configValue key="leafParticipant">false</configValue> <configValue
	 * key="juniorParticipantTypeCodes">org,position,emp</configValue>
	 * <configValue key="jointParticipantType">false</configValue> <configValue
	 * key="jointTypeCodeList"></configValue>
	 */
	private void loadPartyTypes(IContributionEvent event) {
		Configuration contributionConfig = event.getContributionConfiguration();
		Module module = contributionConfig.getModule("PartyTypeAdapter");
		for (Group group : module.getGroups().values()) {
			String prefix = group.getConfigValue("prefix");
			String code = group.getConfigValue("code");
			String displayName = group.getConfigValue("displayName");
			String description = group.getConfigValue("description");
			String showAtRootArea = group.getConfigValue("showAtRootArea");
			String priority = group.getConfigValue("priority");
			String leafParticipant = group.getConfigValue("leafParticipant");
			String juniorParticipantTypeCodes = group
					.getConfigValue("juniorParticipantTypeCodes");
			String jointParticipantType = group
					.getConfigValue("jointParticipantType");
			String jointTypeCodeList = group
					.getConfigValue("jointTypeCodeList");
			ParticipantType type = new ParticipantType();
			type.setCode(code);
			if (prefix != null && prefix.length() > 0) {
				type.setPrefix(prefix.charAt(0));
			}
			type.setDescription(description);
			type.setDisplayName(displayName);
			if (showAtRootArea != null) {
				type.setShowAtRootArea(Boolean.valueOf(showAtRootArea));
			}
			if(StringUtils.isNumeric(priority)){				
				type.setPriority(Integer.parseInt(priority));
			}
			if(jointParticipantType!=null){				
				type.setJointParticipantType(Boolean.valueOf(jointParticipantType));
			}
			if(leafParticipant!=null){
				type.setLeafParticipant(Boolean.valueOf(leafParticipant));
			}
			if(juniorParticipantTypeCodes!=null){
				String[] juniorParticipantTypeCodesArray = juniorParticipantTypeCodes.split(",");
				type.setJuniorParticipantTypeCodes(Arrays.asList(juniorParticipantTypeCodesArray));
			}
			if(jointTypeCodeList!=null){
				String[] jointTypeCodeListArray = jointTypeCodeList.split(",");
				type.setJointTypeCodeList(Arrays.asList(jointTypeCodeListArray));
			}
			PartyTypeAdapterManager.addPartyType(code, type);
		}
	}

	public void loadFinished(IContributionEvent event) {
		loadPartyTypes(event);
	}

	public void unLoad(IContributionEvent event) {

	}
}