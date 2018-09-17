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

import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.party.manager.DefaultPositionManager;
import org.gocom.components.coframe.org.util.OrgPartyAdaptUtil;

import com.eos.spring.BeanFactory;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.Party;

/**
 * 机构的Party类型数据服务类
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class PositionPartyTypeDataService implements IPartyTypeDataService {

	private DefaultPositionManager bean;
	
	public PositionPartyTypeDataService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		bean = beanFactory.getBean(DefaultPositionManager.SPRING_BEAN_NAME);
	}
	
	public List<Party> getAllPartyList(String tenantID) {
		OrgPosition[] positions = bean.getAllPositions(tenantID);
		return OrgPartyAdaptUtil.adapt(positions);
	}

	public Party getPartyByPartyID(String partyID, String tenantID) {
		OrgPosition position = bean.getPositionById(partyID, tenantID);
		return OrgPartyAdaptUtil.adapt(position);
	}

	public List<Party> getRootPartyList(String tenantID) {
		OrgPosition[] positions = bean.getRootPositions(tenantID);
		return OrgPartyAdaptUtil.adapt(positions);
	}

}
