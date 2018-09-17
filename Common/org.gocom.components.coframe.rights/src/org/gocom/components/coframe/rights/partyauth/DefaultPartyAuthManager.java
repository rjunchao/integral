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
package org.gocom.components.coframe.rights.partyauth;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.party.Party;

/**
 * 
 * 
 * @author wangwx (mailto:wangwx@primeton.com)
 */
public class DefaultPartyAuthManager extends DASDaoSupport {

	public final static String SPRING_BEAN_NAME = "DefaultPartyAuthManagerBean";

	private static String CAP_PARTYAUTH_ENTITY_ROLE_TYPE_PROPERTY = "roleType";

	private static String CAP_PARTYAUTH_ENTITY_PARTY_TYPE_PROPERTY = "partyId";

	private static String CAP_PARTYAUTH_ENTITY_PARTY_ID_PROPERTY = "partyType";

	public void savePartyAuth(CapPartyauth capPartyauth) {
		getDASTemplate().saveEntity(capPartyauth);
	}

	public void deletePartyAuth(CapPartyauth capPartyauth) {
		getDASTemplate().deleteEntity(capPartyauth);
	}

	public void deletePartyAuthBatch(CapPartyauth[] capPartyauths) {
		getDASTemplate().deleteEntityBatch(capPartyauths);
	}

	public void savePartyAuthBatch(CapPartyauth[] capPartyauths) {
		getDASTemplate().saveEntities(capPartyauths);
	}

	public void insertAndDelete(CapPartyauth[] toAdd, CapPartyauth[] toDel) {
		if (toAdd != null && toAdd.length > 0) {
			this.savePartyAuthBatch(toAdd);
		}
		if (toDel != null && toDel.length > 0) {
			this.deletePartyAuthBatch(toDel);
		}
	}

	public int delPartyAuthByRole(Party roleParty) {
		IDASCriteria criteria = DASManager.createCriteria(CapPartyauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, roleParty.getTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_PARTYAUTH_ENTITY_ROLE_TYPE_PROPERTY, roleParty.getPartyTypeID()));
		criteria.add(ExpressionHelper.eq("capRole.roleId", roleParty.getId()));
		criteria.add(ExpressionHelper.eq("capRole.tenantId", roleParty.getTenantID()));
		return getDASTemplate().deleteByCriteriaEntity(criteria);

	}

	public void deletePartyAuth(String partyId, String partyType, String tenantId) {
		IDASCriteria criteria = DASManager.createCriteria(CapPartyauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantId));
		criteria.add(ExpressionHelper.eq(CAP_PARTYAUTH_ENTITY_PARTY_ID_PROPERTY, partyId));
		criteria.add(ExpressionHelper.eq(CAP_PARTYAUTH_ENTITY_PARTY_TYPE_PROPERTY, partyType));
		getDASTemplate().deleteByCriteriaEntity(criteria);
	}
}