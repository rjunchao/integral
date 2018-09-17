/*
 * Copyright 2013 Primeton.
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
package org.gocom.components.coframe.entityauth.handler;

import org.gocom.components.coframe.tools.tab.TabPageManager;

import com.eos.infra.config.Configuration;
import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
/**
 * 加载资源的监听器
 * @author lijt@primeton.com
 */
public class EntityContributionListener implements IContributionListener {
	
	public void load(IContributionEvent arg0) {

	}

	public void loadFinished(IContributionEvent event) {
		Configuration configuration = event.getContributionConfiguration();
		
		// 加载Tab
		TabPageManager.INSTANCE.loadTabPage("AuthTab", configuration);
		
	}

	public void unLoad(IContributionEvent event) {
		Configuration configuration = event.getContributionConfiguration();
		// 加载Tab
		TabPageManager.INSTANCE.unloadTabPage("AuthTab", "entity_rule", configuration);
	}

}
