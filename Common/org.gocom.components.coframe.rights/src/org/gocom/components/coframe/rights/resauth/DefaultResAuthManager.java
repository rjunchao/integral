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
package org.gocom.components.coframe.rights.resauth;

import org.gocom.components.coframe.rights.dataset.CapResauth;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.TenantManager;
import com.primeton.cap.party.Party;

/**
 * <pre>
 *  Title: 程序的中文名称
 *  Description: 程序功能的描述
 * </pre>
 * 
 * @author guwei
 * @version 1.00.00
 * 
 */

public class DefaultResAuthManager extends DASDaoSupport {

	public final static String SPRING_BEAN_NAME = "DefaultResAuthManagerBean";

	private static String CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY = "partyId";

	private static String CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY = "partyType";

	private static String CAP_RESAUTH_ENTITY_RES_TYPE_PROPERTY = "resType";

	private static String CAP_RESAUTH_ENTITY_RES_ID_PROPERTY = "resId";

	/**
	 * 
	 */
	public DefaultResAuthManager() {
	}

	/**
	 * 获取某个party对于某种类型资源的所有权限数据，目前party只支持roleParty
	 * 
	 * @param party
	 * @param resType
	 * @return
	 */
	public CapResauth[] getCapResauthListByResType(Party party, String resType) {
		IDASCriteria criteria = DASManager.createCriteria(CapResauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY, party.getId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY, party.getPartyTypeID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_RES_TYPE_PROPERTY, resType));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapResauth.class, criteria);
	}

	public CapResauth getCapResauthByResIdAndType(Party party, String resId, String resType) {
		IDASCriteria criteria = DASManager.createCriteria(CapResauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY, party.getId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY, party.getPartyTypeID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_RES_ID_PROPERTY, resId));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_RES_TYPE_PROPERTY, resType));
		CapResauth[] resAuths = getDASTemplate().queryEntitiesByCriteriaEntity(CapResauth.class, criteria);
		if (resAuths != null && resAuths.length > 0) {
			return resAuths[0];
		}
		return null;
	}

	public void updateCapResauth(CapResauth capResauth) {
		getDASTemplate().updateEntity(capResauth);
	}

	private void updateCapResauthBatch(CapResauth[] capResauths) {
		getDASTemplate().updateEntityBatch(capResauths);
	}

	public void insertCapResauth(CapResauth capResauth) {
		getDASTemplate().insertEntity(capResauth);
	}

	private void insertCapResauthBatch(CapResauth[] capResauth) {
		getDASTemplate().insertEntityBatch(capResauth);
	}

	public void deleteCapResauth(CapResauth capResauth) {
		IDASCriteria criteria = DASManager.createCriteria(CapResauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY, capResauth.getPartyId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY, capResauth.getPartyType()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_RES_ID_PROPERTY, capResauth.getResId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_RES_TYPE_PROPERTY, capResauth.getResType()));
		getDASTemplate().deleteByCriteriaEntity(criteria);
	}

	private void deleteCapResauthBatch(CapResauth[] capResauth) {
		getDASTemplate().deleteEntityBatch(capResauth);
	}

	/**
	 * 保证事务
	 * 
	 * @param toAdd
	 * @param toUpdate
	 * @param toDel
	 */
	public void save(CapResauth[] toAdd, CapResauth[] toUpdate, CapResauth[] toDel) {
		if (toAdd != null && toAdd.length > 0) {
			this.insertCapResauthBatch(toAdd);
		}
		if (toUpdate != null && toUpdate.length > 0) {
			this.updateCapResauthBatch(toUpdate);
		}
		if (toDel != null && toDel.length > 0) {
			this.deleteCapResauthBatch(toDel);
		}
	}

	public CapResauth[] getCapResauthListByParty(Party party) {
		IDASCriteria criteria = DASManager.createCriteria(CapResauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, party.getTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY, party.getId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY, party.getPartyTypeID()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapResauth.class, criteria);
	}

	public int deleteCapResauthByParty(Party party) {
		IDASCriteria criteria = DASManager.createCriteria(CapResauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, party.getTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_ID_PROPERTY, party.getId()));
		criteria.add(ExpressionHelper.eq(CAP_RESAUTH_ENTITY_PARTY_TYPE_PROPERTY, party.getPartyTypeID()));
		return getDASTemplate().deleteByCriteriaEntity(criteria);
	}
}