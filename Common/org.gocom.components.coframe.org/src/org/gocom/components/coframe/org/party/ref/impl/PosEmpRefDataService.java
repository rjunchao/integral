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
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.party.manager.DefaultPositionManager;
import org.gocom.components.coframe.org.util.OrgPartyAdaptUtil;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 岗位员工关联数据服务类
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class PosEmpRefDataService implements IPartyTypeRefDataService {

	private DefaultPositionManager bean;
	
	public PosEmpRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultPositionManager.SPRING_BEAN_NAME);
	}
	
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据岗位获取岗位下的员工列表
		OrgEmployee[] emps = bean.getEmpsByPosition(parentPartyID, tenantID);
		return OrgPartyAdaptUtil.adapt(emps);
	}

	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据人员获取所在机构列表
		OrgPosition[] positions = bean.getParentPositionsByEmp(childPartyID, tenantID);
		return OrgPartyAdaptUtil.adapt(positions);
	}
}
