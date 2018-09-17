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
package org.gocom.components.coframe.org.party.manager;

import static org.gocom.components.coframe.org.util.IOrgConstants.EMPID_PROPERTY;
import static org.gocom.components.coframe.org.util.IOrgConstants.EMP_REF_PROPERTY;
import static org.gocom.components.coframe.org.util.IOrgConstants.ORGID_PROPERTY;
import static org.gocom.components.coframe.org.util.IOrgConstants.ORG_REF_PROPERTY;
import static org.gocom.components.coframe.org.util.IOrgConstants.POSITIONID_PROPERTY;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmporg;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;

/**
 * 组织机构信息管理，提供对组织机构数据实体的数据库操作（持久化）
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class DefaultOrgManager extends DASDaoSupport {

	/**	springBean名称标识 */
	public static final String SPRING_BEAN_NAME = "DefaultOrgManagerBean";
	
	/**
	 * 根据租户ID查询所有组织机构信息列表
	 * @param tenantID 租户ID
	 * @return 组织机构对象信息列表
	 */
	public OrgOrganization[] getAllOrgs(String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgOrganization.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgOrganization.class, criteria);
	}

	/**
	 * 根据机构ID和租户ID查询组织机构信息
	 * 
	 * @param orgid 机构ID
	 * @param tenantID 租户ID
	 * @return 组织机构对象信息，未找到返回null
	 */
	public OrgOrganization getOrgById(String orgid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgOrganization.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(ORGID_PROPERTY, orgid));
		OrgOrganization[] orgArray = getDASTemplate().queryEntitiesByCriteriaEntity(OrgOrganization.class, criteria);
		if (orgArray != null && orgArray.length == 1) {
			return orgArray[0];
		}
		return null;
	}

	/**
	 * 根据租户ID查询组织结构根对象信息列表
	 * 
	 * @param tenantID 租户ID
	 * @return 组织结构对象信息列表
	 */
	public OrgOrganization[] getRootOrgs(String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgOrganization.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.isNull(ORG_REF_PROPERTY));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgOrganization.class, criteria);
	}

	/**
	 * 根据父机构ID和租户ID获取子机构列表
	 * @param parentOrgid
	 * @param tenantID
	 * @return
	 */
	public OrgOrganization[] getSubOrgs(String parentOrgid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgOrganization.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(ORG_REF_PROPERTY + "." + ORGID_PROPERTY, parentOrgid));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgOrganization.class, criteria);
	}

	/**
	 * 根据机构ID和租户ID获取父机构
	 * @param orgid
	 * @param tenantID
	 * @return
	 */
	public OrgOrganization getParentOrg(String orgid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgOrganization.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(ORGID_PROPERTY, orgid));
		criteria.addAssociation(ORG_REF_PROPERTY);
		OrgOrganization[] orgs = getDASTemplate().queryEntitiesByCriteriaEntity(OrgOrganization.class, criteria);
		if (orgs != null && orgs.length == 1) {
			OrgOrganization org = orgs[0];
			return org.getOrgOrganization();
		}
		return null;
	}

	/**
	 * 根据机构ID和租户ID获取员工列表
	 * @param orgid
	 * @param tenantID
	 * @return
	 */
	public OrgEmployee[] getEmpsByOrg(String orgid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgEmporg.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(ORGID_PROPERTY, orgid));
		criteria.addAssociation(EMP_REF_PROPERTY);
		criteria.add(ExpressionHelper.eq(EMP_REF_PROPERTY + "." + IConstants.TENANT_PROPERTY, tenantID));
		OrgEmporg[] emporgs = getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmporg.class, criteria);
		getDASTemplate().expandEntitiesRelation(emporgs, EMP_REF_PROPERTY); // 虽然用了addAssociation()，此处也必须展开关联实体，否则报ClassCastException

		// 将员工机构关系列表组装为员工列表
		List<OrgEmployee> emps = new ArrayList<OrgEmployee>(emporgs.length);
		for(OrgEmporg emporg : emporgs) {
			emps.add(emporg.getOrgEmployee());
		}
		return emps.toArray(new OrgEmployee[emps.size()]);
	}

	/**
	 * 通过员工ID和租户ID获取员工所在父机构列表
	 * @param empid
	 * @param tenantID
	 * @return
	 */
	public OrgOrganization[] getParentOrgsByEmp(String empid, String tenantID) {
		// 先查出员工机构关系
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgEmporg.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(EMP_REF_PROPERTY + "." + EMPID_PROPERTY, empid));
		OrgEmporg[] emporgs = getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmporg.class, criteria);
		
		// 将员工机构关系列表组装为机构列表
		List<OrgOrganization> orgs = new ArrayList<OrgOrganization>(emporgs.length);
		for(OrgEmporg emporg : emporgs) {
			OrgOrganization org = OrgOrganization.FACTORY.create();
			org.setOrgid(emporg.getOrgid());
			getDASTemplate().expandEntity(org);
			orgs.add(org);
		}
		return orgs.toArray(new OrgOrganization[orgs.size()]);
	}

	/**
	 * 根据机构ID和租户ID获取机构下的岗位
	 * @param orgid
	 * @param tenantID
	 * @return
	 */
	public OrgPosition[] getPositionsByOrg(String orgid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(ORG_REF_PROPERTY + "." + ORGID_PROPERTY, orgid));
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
		return positions;
	}

	/**
	 * 根据岗位ID和租户ID获取岗位所在的父机构
	 * @param positionid
	 * @param tenantID
	 * @return
	 */
	public OrgOrganization getParentOrgByPosition(String positionid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(POSITIONID_PROPERTY, positionid));
		criteria.addAssociation(ORG_REF_PROPERTY);
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
		if(positions != null && positions.length == 1) {
			return positions[0].getOrgOrganization();
		}
		return null;
	}
	
}
