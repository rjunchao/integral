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

import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;
import com.primeton.cap.spi.auth.rule.RuleConditionDefinition.Property;

/**
 * 
 * 规则条件前端的树形模型
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */
public class Condition extends AbstractRuleCondition{

	// 编号
	private String id;

	// 名称
	private String name;

	// 操作者
	private String operator;

	// 左ID
	private String leftId;

	// 条件
	private String condition;

	// 右ID
	private String rightId;

	// 扩展
	private List<Property> extension = new ArrayList<Property>();

	// 子节点
	private List<Condition> children = new ArrayList<Condition>();

	/**
	 * 构造方法(默认)
	 * 
	 */
	public Condition() {
		super();
	}

	/**
	 * 获取子节点
	 * 
	 * @return 子节点集合
	 */
	public List<Condition> getChildren() {
		return children;
	}

	/**
	 * 设置子节点
	 * 
	 * @param children 子节点集合
	 */
	public void setChildren(List<Condition> children) {
		this.children = children;
	}

	/**
	 * 增加子节点
	 * 
	 * @param ac 子节点
	 */
	public void addChildren(Condition ac) {
		this.children.add(ac);
	}

	/**
	 * 获取条件
	 * 
	 * @return 条件
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 设置条件
	 * 
	 * @param condition 条件
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 取得ID
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 ID
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取操作者
	 * 
	 * @return 操作者
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作者
	 * 
	 * @param operator 操作者
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取左ID
	 * 
	 * @return
	 */
	public String getLeftId() {
		return leftId;
	}

	/**
	 * 设置左ID
	 * 
	 * @param leftId 左ID
	 */
	public void setLeftId(String leftId) {
		this.leftId = leftId;
	}

	/**
	 * 获取扩展
	 * 
	 * @return 扩展
	 */
	public List<Property> getExtension() {
		return extension;
	}

	/**
	 * 设置扩展
	 * 
	 * @param extension 扩展
	 */
	public void setExtension(List<Property> extension) {
		this.extension = extension;
	}

	/**
	 * 获取右ID
	 * 
	 * @return
	 */
	public String getRightId() {
		return rightId;
	}

	/**
	 * 设置右ID
	 * 
	 * @param rightId 右ID
	 */
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public boolean isMatch(IRuleContext arg0) {
		return true;
	}

}
