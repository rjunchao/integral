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
package org.gocom.components.coframe.rights.user;

import java.util.Date;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.TenantManager;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapUser;
import org.gocom.components.coframe.rights.party.CapPartyauthService;
import org.gocom.components.coframe.rights.party.ICapPartyauthService;
import org.gocom.components.coframe.tools.IConstants;

import commonj.sdo.DataObject;

/**
 * 用户服务类
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */
public class CapUserService extends DASDaoSupport implements ICapUserService {
	public ICapPartyauthService partyauthService = null;

	public void addCapUser(CapUser capUser) {
		DefaultUserManager.INSTANCE.setUserAttribute(capUser);
		getDASTemplate().getPrimaryKey(capUser);
		getDASTemplate().insertEntity(capUser);
	}

	public void deleteCapUser(CapUser[] capUsers) {
		for (DataObject capUser : capUsers) {
			getDASTemplate().deleteEntityCascade(capUser);
			CapPartyauth partyauth = CapPartyauth.FACTORY.create();
			partyauth.setPartyId(capUser.getString("userId"));
			partyauth.setPartyType(IConstants.USER_PARTY_TYPE_ID);
			partyauth.setTenantId(TenantManager.getCurrentTenantID());
			partyauthService.deleteCapPartyauthByTemplate(partyauth);
		}
	}

	public void getCapUser(CapUser capUser) {
		getDASTemplate().expandEntity(capUser);
	}

	public CapUser[] queryCapUsers(CriteriaType criteria, PageCond page) {
		criteria.set_entity(CapUser.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(
				criteria);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(
				CapUser.class, dasCriteria, page);
	}

	public void updateCapUser(CapUser capUser) {
		if(capUser.getStartdate()==null){
			capUser.setStartdate(new Date());
		}
		getDASTemplate().updateEntity(capUser);
	}

	public int countCapUser(CriteriaType criteria) {
		criteria.set_entity(CapUser.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(
				criteria);
		return getDASTemplate().count(dasCriteria);
	}

	public Boolean checkUser(String userId) {
		CapUser capUser = CapUser.FACTORY.create();
		capUser.setUserId(userId);
		CapUser[] capUsers = getDASTemplate().queryEntitiesByTemplate(
				CapUser.class, capUser);
		if (capUsers.length > 0)
			return true;
		return false;
	}
	
	public void getCapUserByTemplate(CapUser template, CapUser capUser) {
		getDASTemplate().expandEntityByTemplate(template, capUser);
	}
	
	public void deleteCapUserByTemplate(CapUser capUser){
		getDASTemplate().deleteByTemplate(capUser);
	}

	/* (non-Javadoc)
	 * @see org.gocom.components.coframe.rights.user.ICapUserService#getCapUserByUserId(java.lang.String)
	 */
	public CapUser getCapUserByUserId(String userId) {
		if(!StringUtils.isBlank(userId)){
			IDASCriteria dasCriteria = getDASTemplate().createCriteria(CapUser.QNAME);
			dasCriteria.add(ExpressionHelper.eq("userId", userId));
			CapUser[] users = getDASTemplate().queryEntitiesByCriteriaEntity(CapUser.class, dasCriteria);
			return users.length > 0 ? users[0] : null;
		}
		return null;
	}

	public boolean validateUserId(CapUser user) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(CapUser.QNAME);
		dasCriteria.add(ExpressionHelper.eq("userId", user.getUserId()));
		CapUser[] users = getDASTemplate().queryEntitiesByCriteriaEntity(CapUser.class, dasCriteria);
		if(users == null || users.length == 0){
			return true;
		}
		if(user.getUserId() != null && users[0].getOperatorId().equals(user.getOperatorId())){//修改的情况，只能为1个
			return users.length == 1;
		}else{//新增,必定没有
			return users.length == 0;
		}
	}
	
	public void updatePasswords(CapUser[] capUsers) {
		for(CapUser capUser : capUsers){
		    String password = DefaultUserManager.INSTANCE.encodeString("000000");
		    capUser.setPassword(password);
		}
		getDASTemplate().updateEntityBatch(capUsers);
	}
	
	public boolean authentication(String userId,String password){
		CapUser capUser = CapUser.FACTORY.create();
		capUser.setUserId(userId);
		capUser.setPassword(password);
		CapUser[] capUsers = getDASTemplate().queryEntitiesByTemplate(
				CapUser.class, capUser);
		if (capUsers.length > 0)
			return true;
		return false;
	}
	
	public String encodePassword(String password){
		return DefaultUserManager.INSTANCE.encodeString(password);
	}

	public void setPartyauthService(ICapPartyauthService partyauthService) {
		this.partyauthService = partyauthService;
	}
}
