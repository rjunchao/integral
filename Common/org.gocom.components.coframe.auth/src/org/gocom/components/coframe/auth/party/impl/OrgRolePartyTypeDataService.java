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
package org.gocom.components.coframe.auth.party.impl;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.auth.party.manager.DefaultRoleManager;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.party.manager.DefaultOrgManager;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.Party;
/**
 * 机构角色PartyType为机构与角色的交集Party，代表了同时是机构与角色的子的Party
 * 如：A部门的管理者，表示既是A部门的子Party，又拥有管理者角色的Party，通过这种party类型可能找到一堆岗位，也可能找到一堆人员
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class OrgRolePartyTypeDataService implements IPartyTypeDataService {
	// 机构管理对象
	private DefaultOrgManager orgBean = null;

	// 角色管理对象
	private DefaultRoleManager roleBean = null;

	// 机构与角色标识分隔符
	public static String ORG_ROLE_SPLIT = ",";// 机构标识和角色标识不要包含此字符

	public OrgRolePartyTypeDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		orgBean = beanFactory.getBean(DefaultOrgManager.SPRING_BEAN_NAME);
		roleBean = beanFactory.getBean(DefaultRoleManager.SPRING_BEAN_NAME);
	}

	/**
	 * 根据租户ID获取所有参与者集合
	 * 
	 * @param tenantID 租户ID
	 * @return 参与者集合
	 */
	public List<Party> getAllPartyList(String tenantID) {
		OrgOrganization[] orgs = orgBean.getAllOrgs(tenantID);
		CapRole[] roles = roleBean.getAllRoleListByTenant(tenantID);

		// 交叉结合
		List<Party> returnList = new ArrayList<Party>();
		if (orgs != null) {
			for (OrgOrganization org : orgs) {
				String orgId = String.valueOf(org.getOrgid());
				String orgName = org.getOrgname();
				String orgCode = org.getOrgcode();
				if (roles != null) {
					for (CapRole role : roles) {
						Party party = new Party(IConstants.ORG_ROLE_PARTY_TYPE_ID, orgId + ORG_ROLE_SPLIT + role.getRoleId(), orgCode + ORG_ROLE_SPLIT
								+ role.getRoleCode(), orgName + ORG_ROLE_SPLIT + role.getRoleName(), tenantID);
						returnList.add(party);
					}
				}
			}
		}
		return returnList;
	}

	/**
	 * 根据授权ID获取参与者对象
	 * 
	 * @param partyID 授权ID
	 * @param tenantID 租户ID
	 * @return 参与者对象
	 */
	public Party getPartyByPartyID(String partyID, String tenantID) {
		// 将partyID分隔
		String[] idArray = StringUtil.split(partyID, ORG_ROLE_SPLIT);
		if (idArray != null && idArray.length == 2) {
			String orgId = idArray[0];
			String roleId = idArray[1];
			OrgOrganization org = orgBean.getOrgById(orgId, tenantID);
			CapRole role = roleBean.getRoleByRoleIDAndTenant(roleId, tenantID);
			if (org != null && role != null) {
				Party party = new Party(IConstants.ORG_ROLE_PARTY_TYPE_ID, orgId + ORG_ROLE_SPLIT + roleId, org.getOrgcode() + ORG_ROLE_SPLIT
						+ role.getRoleCode(), org.getOrgname() + ORG_ROLE_SPLIT + role.getRoleName(), tenantID);
				return party;
			}
		}
		return null;
	}

	/**
	 * 获取根参与者列表
	 * 
	 * @param tenantID 租户ID
	 * @return 参与者列表
	 */
	public List<Party> getRootPartyList(String tenantID) {
		return getAllPartyList(tenantID);
	}

}