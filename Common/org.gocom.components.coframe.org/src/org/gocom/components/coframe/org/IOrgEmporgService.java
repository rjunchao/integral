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
import org.gocom.components.coframe.org.dataset.OrgEmporg;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;

/**
 * 人员机构服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public interface IOrgEmporgService{

	/**
	 *
	 * @param orgEmporg OrgEmporg
	 */
	void addOrgEmporg(OrgEmporg orgEmporg);

	/**
	 *
	 * @param orgEmporgs OrgEmporg[]
	 */
	void deleteOrgEmporg(OrgEmporg[] orgEmporgs);

	/**
	 *
	 * @param orgEmporg OrgEmporg[]
	 */
	void getOrgEmporg(OrgEmporg orgEmporg);

	/**
	 *
	 * @param criteria CriteriaType
	 * @param page PageCond
	 * @return OrgEmporg[]
	 */
	OrgEmporg[] queryOrgEmporgs(CriteriaType criteriaType,
			PageCond pageCond);

	/**
	 *
	 * @param orgEmporg OrgEmporg[]
	 */
	void updateOrgEmporg(OrgEmporg orgEmporg);

	/**
	 * 根据机构查询出关联的Emporg列表
	 * @param org 机构
	 * @return
	 */
	OrgEmporg[] queryOrgEmporgsByOrg(OrgOrganization org);
	
	/**
	 * 根据机构查询出所有人员列表
	 * @param org 机构
	 * @return 人员列表
	 */
	OrgEmployee[] queryEmpsByOrgDifferFromPosition(OrgOrganization org, OrgPosition position);

	/**
	 * 根据员工删除员工机构的关联关系
	 * @param emp
	 */
	void deleteEmporgByEmp(OrgEmployee emp);

	/**
	 * 根据员工查询出所有员工机构关系列表
	 * @param emp
	 * @return
	 */
	OrgEmporg[] queryOrgEmporgsByEmp(OrgEmployee emp);
}
