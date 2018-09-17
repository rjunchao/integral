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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.eos.infra.config.Configuration;
import com.eos.infra.config.Configuration.Group;
import com.eos.infra.config.Configuration.Module;
import com.eos.runtime.resource.IContributionEvent;
import com.eos.runtime.resource.IContributionListener;
import com.primeton.das.entity.impl.handler.EntityHandlerManager;
import com.primeton.ext.das.common.handler.HandlerConfig;
import com.primeton.ext.das.common.handler.Match;

/**
 * 实体授权监听
 * @author lijt (mailto:lijt@primeton.com)
 */

public class EntityAuthContributionHandler implements IContributionListener {
	
	private static List<HandlerConfig> handlerConfigList = new ArrayList<HandlerConfig>();
	public static List<String> skipFilterRuleList = new ArrayList<String>();
	public static List<String> needFilterRuleList = new ArrayList<String>();
	
	public void load(IContributionEvent event) {
		String handlerId = "$$" + EntityAuthHandler.class.getName();
		for (Iterator<HandlerConfig> it = EntityHandlerManager.INSTANCE.getHandlerConfigs().iterator(); it.hasNext();) {
			HandlerConfig config = it.next();
			if (handlerId.equals(config.getHandlerId())) {
				it.remove();
			}
		}
		
		List<Match> matches = new ArrayList<Match>();
		Match match = new Match();
		match.setMatchName("*");
		match.setRegex(true);
		matches.add(match);
		
		HandlerConfig config = new HandlerConfig();
		config.setHandlerId(handlerId);
		config.setClassName(EntityAuthHandler.class.getName());
		config.setNameMatches(matches);
		EntityHandlerManager.INSTANCE.getHandlerConfigs().add(config);
		EntityHandlerManager.INSTANCE.refresh();
		handlerConfigList.add(config);
	}

	public void loadFinished(IContributionEvent event) {
		// 加载规则
		Configuration configuration = event.getContributionConfiguration();
		Module module = configuration.getModule("EntityRules");
		Group skipFilterRuleGroup = module.getGroup("skip_filter_rule");
		Group needFilterRuleGroup = module.getGroup("need_filter_rule");
		Map<String, Configuration.Value> skipFilterRules = skipFilterRuleGroup.getValues();
		Map<String, Configuration.Value> needFilterRules = needFilterRuleGroup.getValues();
		
		if(skipFilterRules != null && skipFilterRules.keySet() != null) {
			for(String key : skipFilterRules.keySet()) {
				skipFilterRuleList.add(skipFilterRules.get(key).getValue());
			}
		}
		
		if(skipFilterRules != null && skipFilterRules.keySet() != null) {
			for(String key : needFilterRules.keySet()) {
				needFilterRuleList.add(needFilterRules.get(key).getValue());
			}
		}
	}

	public void unLoad(IContributionEvent event) {	
		List<HandlerConfig> allHandlerConfigList = EntityHandlerManager.INSTANCE.getHandlerConfigs();
		if(allHandlerConfigList != null && allHandlerConfigList.size()>0) {
			allHandlerConfigList.removeAll(handlerConfigList);
		}
		EntityHandlerManager.INSTANCE.refresh();
	}
}