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
package org.gocom.components.coframe.auth.party.ref.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gocom.components.coframe.auth.party.impl.OrgRolePartyTypeDataService;
import org.gocom.components.coframe.auth.party.manager.DefaultOrgRoleMananger;
import org.gocom.components.coframe.auth.party.manager.DefaultRoleManager;
import org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.party.manager.DefaultOrgManager;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.party.Party;

/**
 * 机构角色和人员的关系描述（机构角色为父，人员为子）
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class OrgRoleEmpRefDataService {

	//人员
	private DefaultOrgManager orgBean = null;
	//角色
	private DefaultRoleManager roleBean = null;
	//机构角色
	private DefaultOrgRoleMananger orgRoleBean = null;
	/**
	 * 构造方法
	 *
	 */
	public OrgRoleEmpRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		this.orgBean = beanFactory.getBean(DefaultOrgManager.SPRING_BEAN_NAME);
		this.roleBean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
		this.orgRoleBean = beanFactory.getBean(DefaultOrgRoleMananger.SPRING_BEAN_NAME);
	}

	/**
	 * 根据机构角色获取人员
	 * @param parentPartyID 机构角色ID
	 * @param tenantID 租户ID
	 * @return 人员列表
	 */
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据机构角色获取人员，通过查询实体进行一次性查询
		String[] idArray = StringUtil.split(parentPartyID, OrgRolePartyTypeDataService.ORG_ROLE_SPLIT);
		List<Party> returnList = new ArrayList<Party>();
		if (idArray != null && idArray.length == 2) {
			String orgId = idArray[0];
			String roleId = idArray[1];
			QueryEmpByOrgRole[] queryEmpByOrgRoles = this.orgRoleBean.getEmpByOrgRole(orgId, roleId, tenantID);
			if (queryEmpByOrgRoles != null) {
				for (QueryEmpByOrgRole queryEmpByOrgRole : queryEmpByOrgRoles) {
					Party party = new Party(IConstants.EMP_PARTY_TYPE_ID, String.valueOf(queryEmpByOrgRole.getEmpid()), queryEmpByOrgRole.getEmpcode(),
							queryEmpByOrgRole.getEmpname(), tenantID);
					returnList.add(party);
				}
			}
		}
		return returnList;
	}

	/**
	 * 根据人员获取机构角色
	 * @param childPartyID 人员ID
	 * @param tenantID 租户ID
	 * @return 机构角色
	 */
	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据人员获取机构角色，这里通过两次查询，可以通过存储过程等优化
		OrgOrganization[] orgs = this.orgBean.getParentOrgsByEmp(childPartyID, tenantID);
		CapRole[] roles = this.roleBean.getCapRoleListByAuthParty(IConstants.EMP_PARTY_TYPE_ID, childPartyID, tenantID);
		
		if (orgs == null || orgs.length == 0) {
			new ArrayList<Party>(0);
		}
		
		Set<Party> returnSet = new HashSet<Party>();
		
		for(OrgOrganization org : orgs) {
			String orgId = String.valueOf(org.getOrgid());
			String orgCode = org.getOrgcode();
			String orgName = org.getOrgname();
			if (roles != null) {
				for (CapRole role : roles) {
					Party party = new Party(IConstants.ORG_ROLE_PARTY_TYPE_ID, orgId + OrgRolePartyTypeDataService.ORG_ROLE_SPLIT + role.getRoleId(), orgCode
							+ OrgRolePartyTypeDataService.ORG_ROLE_SPLIT + role.getRoleCode(), orgName + OrgRolePartyTypeDataService.ORG_ROLE_SPLIT
							+ role.getRoleName(), tenantID);
					returnSet.add(party);
				}
			}
		}
		
		return new ArrayList<Party>(returnSet);
	}
}
