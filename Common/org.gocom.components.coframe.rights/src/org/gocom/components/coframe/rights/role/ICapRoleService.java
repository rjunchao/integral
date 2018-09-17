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

import java.util.List;

import org.gocom.components.coframe.rights.dataset.CapRole;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;

/**
 * 角色服务接口
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */
public interface ICapRoleService {

	/**
	 * 
	 * @param capRole
	 *            CapRole
	 */
	void addCapRole(CapRole capRole);

	/**
	 * 
	 * @param capRoles
	 *            CapRole[]
	 */
	void deleteCapRole(CapRole[] capRoles);

	/**
	 * 
	 * @param capRole
	 *            CapRole
	 */
	void getCapRole(CapRole capRole);

	/**
	 * 统计角色总数
	 * 
	 * @param criteria
	 * @return
	 */
	int countCapRole(CriteriaType criteria);

	/**
	 * 
	 * @param criteria
	 *            CriteriaType
	 * @param page
	 *            PageCond
	 * @return CapRole[]
	 */
	CapRole[] queryCapRoles(CriteriaType criteriaType, PageCond pageCond);

	/**
	 * 
	 * @param capRole
	 *            CapRole
	 */
	void updateCapRole(CapRole capRole);

	/**
	 * 统计角色与资源的关联关系的数量
	 * 
	 * @param capRole
	 *            指定的角色
	 * @return 关联关系的数量
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	int countCapRoleResRelation(CapRole capRole);

	/**
	 * 删除角色与资源的关联关系
	 * 
	 * @param capRole
	 *            关联的角色
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	int deleteCapRoleResRelation(CapRole capRole);

	/**
	 * 批量删除角色与资源关联关系
	 * 
	 * @param capRoles
	 *            关联的角色数组
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	void deleteCapRoleResRelationBatch(CapRole[] capRoles);

	/**
	 * 统计角色与参与者的关联关系的数量
	 * 
	 * @param capRole
	 *            指定的角色
	 * @return 关联关系的数量
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	int countCapRolePartyRelation(CapRole capRole);

	/**
	 * 删除角色与参与者的关联关系
	 * 
	 * @param capRole
	 *            关联的角色
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	int deleteCapRolePartyRelation(CapRole capRole);

	/**
	 * 批量删除角色与参与者关联关系
	 * 
	 * @param capRoles
	 *            关联的角色数组
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	void deleteCapRolePartyRelationBatch(CapRole[] capRoles);

	/**
	 * 查询可授权的角色列表
	 * 
	 * @return 角色列表
	 * @author liuzn (mailto:liuzn@primeton.com)
	 */
	List<CapRole> queryAuthorizedRoleList();
}
