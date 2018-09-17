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

import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.framework.application.AppMenu;

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import commonj.sdo.DataObject;

/**
 * TODO 此处填写 class 信息
 *
 * @author fangwl (mailto:fangwl@primeton.com)
 */
/**
 * TODO 此处填写 class 信息
 *
 * @author fangwl (mailto:fangwl@primeton.com)
 */
public interface IAppMenuService{

	/**
	 * 新增菜单
	 * @param appMenu
	 */
	void addAppMenu(AppMenu appMenu);

	/**
	 * 删除菜单
	 * @param appMenus
	 */
	void deleteAppMenu(AppMenu[] appMenus);

	/**
	 * 根据模板查询菜单
	 * @param appMenu
	 */
	void getAppMenu(AppMenu appMenu);

	/**
	 * 分页查询
	 * @param criteriaType
	 * @param pageCond
	 * @return
	 */
	AppMenu[] queryAppMenus(CriteriaType criteriaType,
			PageCond pageCond);

	/**
	 * 根据查询条件查询菜单
	 * @param criteriaType
	 * @return
	 */
	AppMenu[] queryAppMenus(CriteriaType criteriaType);
	
	/**
	 * 修改菜单
	 * @param appMenu
	 */
	void updateAppMenu(AppMenu appMenu);
	
	/**
	 * 获取根菜单
	 * @param menus
	 * @return
	 */
	List<Map> getMenuRoot(DataObject[] menus);
	
	/**
	 * 获取菜单节点
	 * @param menus
	 * @return
	 */
	List<Map> getMenuNode(DataObject[] menus);
	
	
	/**
	 * 根据查询条件查询菜单记录数
	 * @param criteria
	 * @return
	 */
	int countAppMenus(CriteriaType criteria);
	
	/**
	 * 生成主键
	 * @param appMenu
	 */
	void getPrimaryKey(AppMenu appMenu);
	
	/**
	 * 根据主键删除菜单
	 * @param id
	 */
	void deleteMenuById(String id);
	
	/**
	 * 修改菜单父子关系，拖拽时使用
	 * @param menuId
	 * @param targetMenuId
	 */
	void modifyMenuRelation(String menuId, String targetMenuId);
	
	/**
	 * 验证是否存在，0不存在，1存在
	 * @param appMenu
	 * @return
	 */
	int validateAppMenu(AppMenu appMenu);
}
