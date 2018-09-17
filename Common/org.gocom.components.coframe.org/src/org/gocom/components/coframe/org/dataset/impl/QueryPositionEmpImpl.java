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
package org.gocom.components.coframe.org.dataset.impl;

import java.math.BigDecimal;

import org.gocom.components.coframe.org.dataset.QueryPositionEmp;

import com.primeton.ext.data.sdo.DataUtil;

import commonj.sdo.Type;

/**
 * 根据岗位查询员工的查询实体，继承自OrgEmployee，多一个positionid属性
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class QueryPositionEmpImpl extends OrgEmployeeImpl implements QueryPositionEmp {

	private static final long serialVersionUID = 1L;
	
	public final static int INDEX_POSITIONID = 39;
	public final static int SDO_PROPERTY_COUNT = 40;

	public QueryPositionEmpImpl() {
		this(QueryPositionEmp.TYPE);
	}

	public QueryPositionEmpImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(QueryPositionEmp.TYPE);
	}

	public BigDecimal getPositionid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_POSITIONID, true));
	}

	public void setPositionid(BigDecimal positionid) {
		super.setByIndex(INDEX_POSITIONID, positionid);
	}
}