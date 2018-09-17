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
package org.gocom.components.coframe.org.party.impl;

import java.util.List;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.party.manager.DefaultEmpManager;
import org.gocom.components.coframe.org.util.OrgPartyAdaptUtil;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.Party;

/**
 * 员工的Party类型数据服务类
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class EmpPartyTypeDataService implements IPartyTypeDataService {

private DefaultEmpManager bean;
	
	public EmpPartyTypeDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultEmpManager.SPRING_BEAN_NAME);
	}
	
	public List<Party> getAllPartyList(String tenantID) {
		OrgEmployee[] emps = bean.getAllEmps(tenantID);
		return OrgPartyAdaptUtil.adapt(emps);
	}

	public Party getPartyByPartyID(String partyID, String tenantID) {
		OrgEmployee emp = bean.getEmpById(partyID, tenantID);
		return OrgPartyAdaptUtil.adapt(emp);
	}

	public List<Party> getRootPartyList(String tenantID) {
		return getAllPartyList(tenantID);
	}

}
