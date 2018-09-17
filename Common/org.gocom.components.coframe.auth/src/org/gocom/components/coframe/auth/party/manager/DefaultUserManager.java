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
package org.gocom.components.coframe.auth.party.manager;

import org.gocom.components.coframe.auth.queryentity.QueryEmpAndUserByUser;
import org.gocom.components.coframe.auth.queryentity.QueryUserByEmp;
import org.gocom.components.coframe.auth.queryentity.QueryUserByRole;
import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.rights.dataset.CapUser;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;

/**
 * 用户管理
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class DefaultUserManager extends DASDaoSupport {

	// 默认用户管理Bean
	public static String SPRING_BEAN_NAME = "DefaultUserManagerBean";

	private final static String CAP_USER_ENTITY_USERID_PROPERTY = "userid";
	
	/**
	 * 查询所有用户
	 * 
	 * @param tenantID 租户ID
	 * @return 所有用户数组
	 */
	public CapUser[] getAllUserList(String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(CapUser.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapUser.class, criteria);
	}

	public CapUser getCapUserByUserId(String userId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(CapUser.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("userId", userId));
		CapUser[] userArray = getDASTemplate().queryEntitiesByCriteriaEntity(CapUser.class, criteria);
		if (userArray != null && userArray.length == 1) {
			return userArray[0];
		}
		return null;
	}

	/**
	 * 根据员工获取用户，使用查询实体
	 * 
	 * @param empId 员工ID
	 * @param tenantID 租户ID
	 * @return 查询用户实体
	 */
	public QueryUserByEmp getQueryUserByEmp(String empId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryUserByEmp.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("empid", empId));
		QueryUserByEmp[] queryUserArray = getDASTemplate().queryEntitiesByCriteriaEntity(QueryUserByEmp.class, criteria);
		if (queryUserArray != null && queryUserArray.length == 1) {
			return queryUserArray[0];
		}
		return null;
	}

	/**
	 * 根据角色ID查询用户列表
	 * 
	 * @param roleId 角色ID
	 * @param tenantID 租户ID
	 * @return 用户列表
	 */
	public QueryUserByRole[] queryCapUserListByRoleId(String roleId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryUserByRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("roleId", roleId));
		criteria.add(ExpressionHelper.eq("partyType", IConstants.USER_PARTY_TYPE_ID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryUserByRole.class, criteria);
	}

	/**
	 * 根据用户ID查询员工和用户数组
	 * 
	 * @param userId 用户ID
	 * @param tenantID 租户ID
	 * @return 员工和用户数组
	 */
	public QueryEmpAndUserByUser[] queryEmpAndUserByUserId(String userId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryEmpAndUserByUser.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("userid", userId));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryEmpAndUserByUser.class, criteria);
	}

	/**
	 * 根据租户ID和用户ID查询员工数组
	 * @param userId
	 * @param tenantID
	 * @return
	 */
	public OrgEmployee[] getEmpsByUserId(String userId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(OrgEmployee.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(CAP_USER_ENTITY_USERID_PROPERTY, userId));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmployee.class, criteria);
	}
}