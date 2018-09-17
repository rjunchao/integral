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

import org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;

/**
 * 默认机构角色管理
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class DefaultOrgRoleMananger extends DASDaoSupport {
	
	public static String SPRING_BEAN_NAME = "DefaultOrgRoleManangerBean";

	/**
	 * 由机构与角色查询人员信息
	 * 
	 * @param orgId 机构ID
	 * @param roleId 角色ID
	 * @param tenantID 租户ID
	 * @return 人员信息数组
	 */
	public QueryEmpByOrgRole[] getEmpByOrgRole(String orgId, String roleId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryEmpByOrgRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("roleId", roleId));
		criteria.add(ExpressionHelper.eq("partyType", IConstants.EMP_PARTY_TYPE_ID));
		criteria.add(ExpressionHelper.eq("orgid", orgId));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryEmpByOrgRole.class, criteria);
	}

}