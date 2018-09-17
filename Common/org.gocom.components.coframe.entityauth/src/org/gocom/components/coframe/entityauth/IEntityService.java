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
package org.gocom.components.coframe.entityauth;

import java.util.List;

import org.gocom.components.coframe.entityauth.pojo.ConValue;
import org.gocom.components.coframe.entityauth.pojo.RightValue;

import com.primeton.cap.spi.dataset.service.EntityInfo;
import com.primeton.cap.spi.dataset.service.PropertyInfo;

/**
 * 实体服务接口
 * @author lijt (mailto:lijt@primeton.com)
 */
public interface IEntityService {
	
	/**
	 * 查询所有实体
	 * @return OrgEmpposition[]
	 */
	EntityInfo[] queryEntities();
	
	/**
	 * 查询实体的所有属性
	 * @param namespace String
	 * @param entityName String
	 * @return PropertyInfo[]
	 */
	PropertyInfo[] queryPropertyInfos(String namespace, String entityName);
	
	/**
	 * 查询实体的属性
	 * @param namespace String
	 * @param entityName String
	 * @param propertyName String
	 * @return PropertyInfo
	 */
	PropertyInfo queryPropertyInfo(String namespace, String entityName, String propertyName);
	
	/**
	 * 有属性查询可视判断条件列表
	 * @param namespace
	 * @param entityName
	 * @param propertyName
	 * @return
	 */
	List<ConValue> getConValuesByCondition(String namespace, String entityName, String propertyName);
	
	/**
	 * 有属性查询可视右值列表
	 * @param namespace
	 * @param entityName
	 * @param propertyName
	 * @return
	 */
	List<RightValue> getRightValuesByCondition(String namespace, String entityName, String propertyName);
}
