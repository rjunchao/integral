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
package org.gocom.components.coframe.auth.party.ref.impl;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.auth.party.manager.DefaultUserManager;
import org.gocom.components.coframe.auth.queryentity.QueryUserByEmp;
import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;

/**
 * 用户和人员的关联关系实现，用户为子，人员为父
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class EmpUserRefDataService implements IPartyTypeRefDataService {

	//用户管理
	private DefaultUserManager bean;
	
	public EmpUserRefDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultUserManager.SPRING_BEAN_NAME);
	}

	/**
	 * 	获取用户列表
	 * @param parentPartyID 人员ID
	 * @param tenantID 租户ID
	 * @return 用户列表
	 */
	public List<Party> getChildrenPartyList(String parentPartyID, String tenantID) {
		// 根据人员找用户，这里通过查询实体做本地实现，远程的方式需要替换此处实现
		List<Party> returnList = new ArrayList<Party>();
		QueryUserByEmp queryUser = bean.getQueryUserByEmp(parentPartyID, tenantID);
		if (queryUser != null) {
			Party party = new Party(IConstants.USER_PARTY_TYPE_ID, queryUser.getUserId(), null, queryUser.getUserName(), queryUser.getTenantId());
			party.putExtAttribute(IConstants.EMAIL, queryUser.getEmail());
			returnList.add(party);
		}
		return returnList;
	}

	/**
	 * 根据用户找人员
	 * @param 用户ID
	 * @param tenantID 租户ID
	 * @return 人员列表
	 */
	public List<Party> getParentPartyList(String childPartyID, String tenantID) {
		// 根据用户找人员
		OrgEmployee[] emps = bean.getEmpsByUserId(childPartyID, tenantID);
		List<Party> returnList = new ArrayList<Party>();
		if (emps != null) {
			for (OrgEmployee emp : emps) {
				returnList.add(adapt(emp));
			}
		}
		return returnList;
	}
	/**
	 * 适配器
	 * @param emp 人员
	 * @return 参与者
 	 */
	private Party adapt(OrgEmployee emp) {
		Party party = new Party(IConstants.EMP_PARTY_TYPE_ID, String.valueOf(emp.getEmpid()), emp.getEmpcode(), emp.getEmpname(), emp.getTenantid());
		
		party.putExtAttribute(IConstants.EMAIL, emp.getOemail());
		return party;
	}
}
