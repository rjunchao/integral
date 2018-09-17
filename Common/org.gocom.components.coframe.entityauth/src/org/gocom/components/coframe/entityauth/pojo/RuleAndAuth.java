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
package org.gocom.components.coframe.entityauth.pojo;

import java.io.Serializable;

import com.primeton.cap.impl.auth.rule.ExceptionMessage;
import com.primeton.cap.impl.auth.rule.condition.AndRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleConditionSet;
import com.primeton.cap.spi.auth.rule.Rule;
/**
 * 一个规则的表示以及授权状态.<br>
 * 包含一组规则条件, 用于判断规则上下文.<br>
 * 
 * @author lijt (mailto:lijt@primeton.com)
 */

public final class RuleAndAuth implements Serializable {
	private static final long serialVersionUID = -2680851086866384249L;
	
	private String type;
	private String id;
	private String name;
	private String namespace;
	private String tenantId;
	private IRuleConditionSet rootCondition = new AndRuleCondition();
	private String auth;
	private String authIconFlag;
	
	public void setRule(Rule rule) {
		this.type = rule.getType();
		this.id = rule.getId();
		this.name = rule.getName();
		this.namespace = rule.getNamespace();
		this.tenantId = rule.getTenantId();
		this.rootCondition = rule.getRootCondition();
	}
	
	/**
	 * 获取根规则条件.<br>
	 * 
	 * @return 根规则条件
	 */
	public IRuleConditionSet getRootCondition() {
		return rootCondition;
	}

	/**
	 * 设置根规则条件.<br>
	 * 
	 * @param rootCondition
	 *            根规则条件
	 */
	public void setRootCondition(IRuleConditionSet rootCondition) {
		if (rootCondition == null) {
			throw new RuntimeException(ExceptionMessage.NULL_ARG.fmt("rootCondition"));
		}
		this.rootCondition = rootCondition;
	}


	/**
	 * 列出所有规则条件.<br>
	 * 
	 * @return 规则条件
	 */
	public IRuleCondition[] listConditions() {
		IRuleCondition[] conditions = rootCondition.listConditions();
		return conditions;
	}

	/**
	 * 获取规则标识.<br>
	 * 
	 * @return 规则标识
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置规则标识.<br>
	 * 
	 * @param id
	 *            规则标识
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取规则名称.<br>
	 * 
	 * @return 规则名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置规则名称.<br>
	 * 
	 * @param name
	 *            规则名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取规则名称空间.<br>
	 * 
	 * @return 规则名称空间
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * 设置规则名称空间.<br>
	 * 
	 * @param namespace
	 *            规则名称空间
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 获取规则类型.<br>
	 * 
	 * @return 规则类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置规则类型.<br>
	 * 
	 * @param type
	 *            规则类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取租户标识.<br>
	 * 
	 * @return 租户标识
	 */
	public String getTenantId() {
		return tenantId == null ? "default" : tenantId;
	}

	/**
	 * 设置租户标识.<br>
	 * 
	 * @param tenantId
	 *            租户标识
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	/**
	 * 获取权限.<br>
	 * 
	 * @return 权限
	 */
	public String getAuth() {
		return auth;
	}
	
	/**
	 * 设置权限.<br>
	 * 
	 * @return 权限 1、具备权限 2、不具备权限
	 */
	public void setAuth(String auth) {
		this.auth = auth;
		this.authIconFlag = auth;
	}

	public String getAuthIconFlag() {
		return authIconFlag;
	}

}