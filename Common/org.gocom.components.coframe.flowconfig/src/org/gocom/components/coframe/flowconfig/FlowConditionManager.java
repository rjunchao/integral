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
package org.gocom.components.coframe.flowconfig;


import org.gocom.components.coframe.flowconfig.helper.AuthFlowRuleHelper;
import org.gocom.components.coframe.flowconfig.model.FlowConditionModel;

import com.primeton.cap.impl.auth.rule.model.RuleConditionLeftModelManager;
import com.primeton.cap.impl.auth.rule.model.RuleConditionRightModelManager;
import com.primeton.cap.spi.auth.rule.model.RuleConditionLeftModel;
import com.primeton.cap.spi.auth.rule.model.RuleConditionRightModel;

/**
 * 流程规则里的条件配置管理类
 * 
 * @author 
 * 
 */
public class FlowConditionManager {
	/**
	 * 获取流程的条件配置
	 * 
	 * @return 流程条件配置相关信息
	 */
	public static FlowConditionModel[] getFlowConditionLeftModel() {
		RuleConditionLeftModel leftModels[] = RuleConditionLeftModelManager.INSTANCE
				.listModels(AuthFlowRuleHelper.FLOW_RULE_TYPE);
		FlowConditionModel[] flModels = new FlowConditionModel[leftModels.length];
		for (int i = 0; i < leftModels.length; i++) {
			RuleConditionLeftModel lModel = leftModels[i];
			FlowConditionModel flModel = new FlowConditionModel();
			flModel.setLeftModel(lModel);
			String leftId = lModel.getId();
			RuleConditionRightModel rModels[] = RuleConditionRightModelManager.INSTANCE
					.listModels(AuthFlowRuleHelper.FLOW_RULE_TYPE, leftId);
			flModel.addRightModels(rModels);
			flModels[i] = flModel;
		}
		return flModels;
	}
}

