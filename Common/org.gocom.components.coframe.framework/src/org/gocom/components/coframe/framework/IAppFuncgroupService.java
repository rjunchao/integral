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
package org.gocom.components.coframe.framework;

import org.gocom.components.coframe.framework.application.AppFuncgroup;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;

/**
 * TODO 此处填写 class 信息
 *
 * @author fangwl (mailto:fangwl@primeton.com)
 */
public interface IAppFuncgroupService{

	/**
	 * 新增功能组
	 * @param appFuncgroup
	 */
	void addAppFuncgroup(AppFuncgroup appFuncgroup);

	/**
	 * 删除功能组
	 * @param appFuncgroups
	 */
	void deleteAppFuncgroup(AppFuncgroup[] appFuncgroups);

	/**
	 * 根据模板获取功能组
	 * @param appFuncgroup
	 */
	void getAppFuncgroup(AppFuncgroup appFuncgroup);

	/**
	 * 分页查询
	 * @param criteria
	 * @param pageCond
	 * @return
	 */
	AppFuncgroup[] queryAppFuncgroups(CriteriaType criteria,
			 PageCond pageCond);

	/**
	 * 修改功能组
	 * @param appFuncgroup
	 */
	void updateAppFuncgroup(AppFuncgroup appFuncgroup);
	
	/**
	 * 查询所有功能组
	 * @param criteria
	 * @return
	 */
	AppFuncgroup[] queryAllAppFuncgroups(CriteriaType criteria);
	
	/**
	 * 查询功能组总数
	 * @param criteria
	 * @return
	 */
	int countAppFuncgroup(CriteriaType criteria);
	
	/**
	 * 根据查询条件查询功能组
	 * @param criteria
	 * @return
	 */
	AppFuncgroup[]  queryAppFuncgroups(CriteriaType criteria);
	
	/**
	 * 根据功能组id删除功能组
	 * @param id
	 */
	void deleteFuncGroupById(String id);
	
	/**
	 * 获取主键
	 * @param appFuncgroup
	 */
	void getPrimaryKey(AppFuncgroup appFuncgroup);
	
	/**
	 * 获取当前功能组的子孙
	 * @param appFuncgroup
	 * @return
	 */
	AppFuncgroup[] getChildFuncGroups(AppFuncgroup appFuncgroup);
	
	/**
	 * 修改功能组关系，树拖拽时使用,功能组拖到功能组
	 * @param funcGroupId 当前节点id
	 * @param targerGroupId 目标节点id
	 */
	void modifyFuncGroupRelation(String funcGroupId, String targetGroupId);
	
}
