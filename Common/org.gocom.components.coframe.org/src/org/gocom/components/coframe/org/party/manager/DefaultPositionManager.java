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
import static org.gocom.components.coframe.org.util.IOrgConstants.POSITIONID_PROPERTY;
import static org.gocom.components.coframe.org.util.IOrgConstants.POSITION_REF_PROPERTY;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmpposition;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;

/**
 * 岗位信息管理，提供对岗位数据实体的数据库操作（持久化）
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class DefaultPositionManager extends DASDaoSupport {

	/**	springBean名称标识 */
	public static final String SPRING_BEAN_NAME = "DefaultPositionManagerBean";
	
	/**
	 * 根据租户ID查询所有岗位列表
	 * @param tenantID
	 * @return
	 */
	public OrgPosition[] getAllPositions(String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
	}

	/**
	 * 根据岗位id查询岗位
	 * @param partyID
	 * @param positionid
	 * @return
	 */
	public OrgPosition getPositionById(String positionid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(POSITIONID_PROPERTY, positionid));
		OrgPosition[] positionArray = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
		if (positionArray != null && positionArray.length == 1) {
			return positionArray[0];
		}
		return null;
	}

	/**
	 * 根据租户ID获取所有的一级岗位（直接在机构下的岗位）列表
	 * @param tenantID
	 * @return
	 */
	public OrgPosition[] getRootPositions(String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.isNull(POSITION_REF_PROPERTY));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
	}

	/**
	 * 根据租户ID和岗位ID获取岗位下的员工
	 * @param positionid
	 * @param tenantID
	 * @return
	 */
	public OrgEmployee[] getEmpsByPosition(String positionid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgEmpposition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(POSITION_REF_PROPERTY + "." + POSITIONID_PROPERTY, positionid));
		criteria.addAssociation(EMP_REF_PROPERTY);
		OrgEmpposition[] emppositons = getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmpposition.class, criteria);
		getDASTemplate().expandEntitiesRelation(emppositons, EMP_REF_PROPERTY); // 虽然用了addAssociation()，此处也必须展开关联实体，否则报ClassCastException
		return buildEmps(emppositons);
	}

	/**
	 * 根据租户ID和员工ID获取员工所在的父岗位列表
	 * @param empid
	 * @param tenantID
	 * @return
	 */
	public OrgPosition[] getParentPositionsByEmp(String empid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgEmpposition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(EMP_REF_PROPERTY + "." + EMPID_PROPERTY, empid));
		criteria.addAssociation(POSITION_REF_PROPERTY);
		OrgEmpposition[] emppositons = getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmpposition.class, criteria);
		getDASTemplate().expandEntitiesRelation(emppositons, POSITION_REF_PROPERTY); // 虽然用了addAssociation()，此处也必须展开关联实体，否则报ClassCastException
		return buildPositions(emppositons);
	}

	/**
	 * 根据租户ID和岗位ID获取岗位的子岗位列表
	 * @param positionid
	 * @param tenantID
	 * @return
	 */
	public OrgPosition[] getSubPositions(String positionid, String tenantID) {
		IDASCriteria criteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		// 设置关联的父机构ID条件
		criteria.add(ExpressionHelper.eq(POSITION_REF_PROPERTY + "." + POSITIONID_PROPERTY, positionid));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, criteria);
	}

	/**
	 * 从员工岗位关系列表中构建员工列表
	 * @param emppositons
	 * @return
	 */
	private OrgEmployee[] buildEmps(OrgEmpposition[] emppositons) {
		if(emppositons == null) {
			return new OrgEmployee[0];
		}
		
		OrgEmployee[] emps = new OrgEmployee[emppositons.length];
		for(int i = 0; i < emppositons.length; i++) {
			emps[i] = emppositons[i].getOrgEmployee();
		}
		
		return emps;
	}
	
	/**
	 * 从员工岗位关系列表中构建岗位列表
	 * @param emppositons
	 * @return
	 */
	private OrgPosition[] buildPositions(OrgEmpposition[] emppositons) {
		if(emppositons == null) {
			return new OrgPosition[0];
		}
		
		OrgPosition[] positions = new OrgPosition[emppositons.length];
		for(int i = 0; i < emppositons.length; i++) {
			positions[i] = emppositons[i].getOrgPosition();
		}
		
		return positions;
	}
}
