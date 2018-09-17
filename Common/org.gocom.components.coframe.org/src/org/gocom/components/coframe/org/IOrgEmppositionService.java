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
package org.gocom.components.coframe.org;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmpposition;
import org.gocom.components.coframe.org.dataset.OrgPosition;

/**
 * 岗位和人员的关系服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public interface IOrgEmppositionService{

	/**
	 *
	 * @param orgEmpposition OrgEmpposition
	 */
	void addOrgEmpposition(OrgEmpposition orgEmpposition);
	
	/**
	 * @param orgEmppositions
	 */
	void addOrgEmpposition(OrgEmpposition[] orgEmppositions);

	/**
	 *
	 * @param orgEmppositions OrgEmpposition[]
	 */
	void deleteOrgEmpposition(OrgEmpposition[] orgEmppositions);

	/**
	 *
	 * @param orgEmpposition OrgEmpposition[]
	 */
	void getOrgEmpposition(OrgEmpposition orgEmpposition);

	/**
	 *
	 * @param criteria CriteriaType
	 * @param page PageCond
	 * @return OrgEmpposition[]
	 */
	OrgEmpposition[] queryOrgEmppositions(CriteriaType criteriaType,
			PageCond pageCond);

	/**
	 *
	 * @param orgEmpposition OrgEmpposition[]
	 */
	void updateOrgEmpposition(OrgEmpposition orgEmpposition);

	/**
	 * 根据岗位删除岗位关联的人员关系
	 * @param position 岗位
	 */
	void deleteEmppositionsByPosition(OrgPosition position);

	/**
	 * 根据员工删除员工关联的岗位关系
	 * @param emp
	 */
	void deleteEmppositionsByEmp(OrgEmployee emp);

	/**
	 * 根据员工ID和父机构ID，查询出子岗位与员工的关系
	 * @param empId 员工ID
	 * @param parentOrgId 父机构ID
	 * @return
	 */
	OrgEmpposition[] queryOrgEmppositionsOfEmp(String empId, String parentOrgId);
	
	/**
	 * 根据岗位查询所有的人员列表
	 * @param position
	 * @return 人员列表
	 */
	OrgEmployee[] queryEmpsByPosition(OrgPosition position);
}
