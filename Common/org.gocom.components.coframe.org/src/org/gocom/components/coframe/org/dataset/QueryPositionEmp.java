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
package org.gocom.components.coframe.org.dataset;

import java.math.BigDecimal;

import com.eos.data.sdo.IObjectFactory;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

/**
 * 根据岗位查询员工的查询实体，继承自OrgEmployee，多一个positionid属性
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public interface QueryPositionEmp extends OrgEmployee {

	String QNAME = "org.gocom.components.coframe.org.dataset.QueryPositionEmp";

	Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "QueryPositionEmp");
	
	String POSITIONID = "positionid";

	static IObjectFactory<QueryPositionEmp> FACTORY = new IObjectFactory<QueryPositionEmp>() {
		public QueryPositionEmp create() {
			return (QueryPositionEmp) DataFactory.INSTANCE.create(TYPE);
		}
	};

	BigDecimal getPositionid();
	
	void setPositionid(BigDecimal positionid);
}