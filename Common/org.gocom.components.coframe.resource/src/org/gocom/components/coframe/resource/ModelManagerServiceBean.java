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
package org.gocom.components.coframe.resource;


import com.eos.das.entity.criteria.CriteriaType;
import commonj.sdo.DataObject;

/**
 * 模型管理服务Bean
 * 
 * @author Administrator
 * 
 */
public class ModelManagerServiceBean {

	/**
	 * 构造方法（默认）
	 *
	 */
	public ModelManagerServiceBean() {
		
	}
	
	/**
	 * 取得模型信息
	 * 
	 * @param criteria 查询试题
	 * @param isAuth 是否授权
	 * @param currentRoleID 当前角色Id
	 * @return 模型信息列表
	 */
	public DataObject[] getModules(CriteriaType criteria, String isAuth, String currentRoleID){		
		DataObject[] modules = ModelAuthServic.getModulesFromDb("default", criteria, isAuth, currentRoleID);
		return modules;
	}
	
	

}