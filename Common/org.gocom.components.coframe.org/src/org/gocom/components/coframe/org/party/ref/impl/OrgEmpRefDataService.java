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
package org.gocom.components.coframe.org.party.ref.impl;

import java.util.List;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.party.manager.DefaultOrgManager;
import org.gocom.components.coframe.org.util.OrgPartyAdaptUtil;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 机构员工关联数据服务类
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class OrgEmpRefDataService implements IPartyTypeRefDataService {

	private DefaultOrgManager bean;

	public OrgEmpRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultOrgManager.SPRING_BEAN_NAME);
	}
	
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据机构获取机构下的员工列表
		OrgEmployee[] emps = bean.getEmpsByOrg(parentPartyID, tenantID);
		return OrgPartyAdaptUtil.adapt(emps);
	}

	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据人员获取所在机构列表
		OrgOrganization[] orgs = bean.getParentOrgsByEmp(childPartyID, tenantID);
		return OrgPartyAdaptUtil.adapt(orgs);
	}
}
