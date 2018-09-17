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
package org.gocom.components.coframe.rights.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapResauth;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.rights.gradeauth.GradeAuthService;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.party.Party;
import commonj.sdo.DataObject;

/**
 * 角色管理服务类
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */
public class CapRoleService extends DASDaoSupport implements ICapRoleService {

	private GradeAuthService gradeAuthService = null;

	public void addCapRole(CapRole capRole) {
		getDASTemplate().getPrimaryKey(capRole);
		getDASTemplate().insertEntity(capRole);
	}

	public void deleteCapRole(CapRole[] capRoles) {
		for (DataObject capRole : capRoles) {
			getDASTemplate().deleteEntityCascade(capRole);
		}
	}

	public void getCapRole(CapRole capRole) {
		getDASTemplate().expandEntity(capRole);
	}

	/**
	 * 统计角色总数
	 * 
	 * @param criteria
	 *            查询实体
	 * @return 角色数量
	 */
	public int countCapRole(CriteriaType criteria) {
		criteria.set_entity(CapRole.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(
				criteria);
		return getDASTemplate().count(dasCriteria);
	}

	public CapRole[] queryCapRoles(CriteriaType criteriaType, PageCond pageCond) {
		criteriaType.set_entity(CapRole.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(
				criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(
				CapRole.class, dasCriteria, pageCond);
	}

	public void updateCapRole(CapRole capRole) {
		getDASTemplate().updateEntity(capRole);
	}

	public int countCapRoleResRelation(CapRole capRole) {
		CriteriaType criteriaResAuth = CriteriaType.FACTORY.create();
		criteriaResAuth.set_entity(CapResauth.QNAME);

		// 封装第一个查询条件： 租户ID
		criteriaResAuth.set("_expr[1]/tenantId", capRole.getTenantId());
		criteriaResAuth.set("_expr[1]/_op", "=");
		// 封装第二个查询条件： 角色_参与者ID
		criteriaResAuth.set("_expr[2]/partyId", capRole.getRoleId());
		criteriaResAuth.set("_expr[2]/_op", "=");
		// 封装第三个查询条件： 角色_参与者类型
		criteriaResAuth
				.set("_expr[3]/partyType", IConstants.ROLE_PARTY_TYPE_ID);
		criteriaResAuth.set("_expr[3]/_op", "=");

		IDASCriteria idasResAuth = getDASTemplate().criteriaTypeToDASCriteria(
				criteriaResAuth);
		return getDASTemplate().count(idasResAuth);
	}

	public int deleteCapRoleResRelation(CapRole capRole) {
		CriteriaType criteriaResAuth = CriteriaType.FACTORY.create();
		criteriaResAuth.set_entity(CapResauth.QNAME);

		// 封装第一个查询条件： 租户ID
		criteriaResAuth.set("_expr[1]/tenantId", capRole.getTenantId());
		criteriaResAuth.set("_expr[1]/_op", "=");
		// 封装第二个查询条件： 角色_参与者ID
		criteriaResAuth.set("_expr[2]/partyId", capRole.getRoleId());
		criteriaResAuth.set("_expr[2]/_op", "=");
		// 封装第三个查询条件： 角色_参与者类型
		criteriaResAuth
				.set("_expr[3]/partyType", IConstants.ROLE_PARTY_TYPE_ID);
		criteriaResAuth.set("_expr[3]/_op", "=");

		IDASCriteria idasResAuth = getDASTemplate().criteriaTypeToDASCriteria(
				criteriaResAuth);
		return getDASTemplate().deleteByCriteriaEntity(idasResAuth);
	}

	public void deleteCapRoleResRelationBatch(CapRole[] capRoles) {
		for (CapRole capRole : capRoles) {
			if (countCapRoleResRelation(capRole) > 0) {
				deleteCapRoleResRelation(capRole);
			}
		}
	}

	public int countCapRolePartyRelation(CapRole capRole) {
		CriteriaType criteriaPartyAuth = CriteriaType.FACTORY.create();
		criteriaPartyAuth.set_entity(CapPartyauth.QNAME);

		// 封装第一个查询条件： 租户ID
		criteriaPartyAuth.set("_expr[1]/tenantId", capRole.getTenantId());
		criteriaPartyAuth.set("_expr[1]/_op", "=");
		// 封装第二个查询条件： 角色ID
		criteriaPartyAuth.set("_expr[2]/capRole.roleId", capRole.getRoleId());
		criteriaPartyAuth.set("_expr[2]/_op", "=");
		// 封装第三个查询条件： 角色类型
		criteriaPartyAuth.set("_expr[3]/roleType",
				IConstants.ROLE_PARTY_TYPE_ID);
		criteriaPartyAuth.set("_expr[3]/_op", "=");

		IDASCriteria idasPartyAuth = getDASTemplate()
				.criteriaTypeToDASCriteria(criteriaPartyAuth);
		return getDASTemplate().count(idasPartyAuth);
	}

	public int deleteCapRolePartyRelation(CapRole capRole) {
		CriteriaType criteriaPartyAuth = CriteriaType.FACTORY.create();
		criteriaPartyAuth.set_entity(CapPartyauth.QNAME);

		// 封装第一个查询条件： 租户ID
		criteriaPartyAuth.set("_expr[1]/tenantId", capRole.getTenantId());
		criteriaPartyAuth.set("_expr[1]/_op", "=");
		// 封装第二个查询条件： 角色ID
		criteriaPartyAuth.set("_expr[2]/capRole.roleId", capRole.getRoleId());
		criteriaPartyAuth.set("_expr[2]/_op", "=");
		// 封装第三个查询条件： 角色类型
		criteriaPartyAuth.set("_expr[3]/roleType",
				IConstants.ROLE_PARTY_TYPE_ID);
		criteriaPartyAuth.set("_expr[3]/_op", "=");

		IDASCriteria idasPartyAuth = getDASTemplate()
				.criteriaTypeToDASCriteria(criteriaPartyAuth);
		return getDASTemplate().deleteByCriteriaEntity(idasPartyAuth);
	}

	public void deleteCapRolePartyRelationBatch(CapRole[] capRoles) {
		for (CapRole capRole : capRoles) {
			if (countCapRolePartyRelation(capRole) > 0) {
				deleteCapRolePartyRelation(capRole);
			}
		}
	}

	public List<CapRole> queryAuthorizedRoleList() {
		// 获取可管控party列表
		List<Party> authorizedRolePartyList = gradeAuthService
				.getManagedRoleList();
		if (null == authorizedRolePartyList
				|| authorizedRolePartyList.size() == 0) {
			return new ArrayList<CapRole>();
		}

		// 获取角色ID列表
		List<String> roleIdList = new ArrayList<String>();
		for (Party authorizedRoleParty : authorizedRolePartyList) {
			roleIdList.add(authorizedRoleParty.getId());
		}

		// 根据角色ID获取角色dataObject列表
		CriteriaType capRoleCriteria = CriteriaType.FACTORY.create();
		capRoleCriteria.set_entity(CapRole.QNAME);
		String[] roleIds = new String[roleIdList.size()];
		roleIds = roleIdList.toArray(roleIds);
		capRoleCriteria.set("_expr[1]/roleId", roleIds);
		capRoleCriteria.set("_expr[1]/_op", "in");
		IDASCriteria idasCapRole = getDASTemplate().criteriaTypeToDASCriteria(
				capRoleCriteria);
		CapRole[] capRoles = getDASTemplate().queryEntitiesByCriteriaEntity(
				CapRole.class, idasCapRole);
		List<CapRole> capRoleList = Arrays.asList(capRoles);

		return capRoleList;
	}

	public GradeAuthService getGradeAuthService() {
		return gradeAuthService;
	}

	public void setGradeAuthService(GradeAuthService gradeAuthService) {
		this.gradeAuthService = gradeAuthService;
	}

}
