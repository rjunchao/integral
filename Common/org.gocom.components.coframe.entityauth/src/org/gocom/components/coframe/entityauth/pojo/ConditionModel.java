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
package org.gocom.components.coframe.entityauth.pojo;


import java.util.ArrayList;
import java.util.List;

import com.primeton.cap.spi.auth.rule.model.RuleConditionLeftModel;
import com.primeton.cap.spi.auth.rule.model.RuleConditionRightModel;

/**
 * 流程条件模型实体
 * @author lijt (mailto:lijt@primeton.com)
 */

public class ConditionModel extends RuleConditionLeftModel {
	//右边模型
	private List<RuleConditionRightModel> rightModels = new ArrayList<RuleConditionRightModel>();
	/**
	 * 获取属性值
	 * @return 右边模型
	 */
	public List<RuleConditionRightModel> getRightModels() {
		return rightModels;
	}
	/**
	 * 设置属性值
	 * @param rightModels 右边模型
	 */
	public void setRightModels(List<RuleConditionRightModel> rightModels) {
		this.rightModels = rightModels;
	}
	/**
	 * 添加List集合中的元素在右边模型
	 * @param rightModels 模型集合
	 */
	public void addRightModels(List<RuleConditionRightModel> rightModels) {
		this.rightModels.addAll(rightModels);
	}
	/**
	 * 添加数组中的所有元素到右边模型
	 * @param rightModels 模型数组
	 */
	public void addRightModels(RuleConditionRightModel[] rightModels) {
		if (rightModels == null) {
			return;
		}
		for (RuleConditionRightModel rModel : rightModels) {
			this.rightModels.add(rModel);
		}
	}
	/**
	 * 添加单个元素到右边模型
	 * @param rmodel 模型
	 */
	public void addRightModel(RuleConditionRightModel rmodel) {
		this.rightModels.add(rmodel);
	}
	/**
	 * 设置左边模型
	 * @param lModel 左模型
	 */
	public void setLeftModel(RuleConditionLeftModel lModel) {
		this.setId(lModel.getId());
		this.setName(lModel.getName());
		this.setOrder(lModel.getOrder());
		this.setRuleType(lModel.getRuleType());
	}
}
