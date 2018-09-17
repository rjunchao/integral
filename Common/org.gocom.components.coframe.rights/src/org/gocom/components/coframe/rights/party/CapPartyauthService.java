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
package org.gocom.components.coframe.rights.party;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapRole;

import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import commonj.sdo.DataObject;

/**
 * 用户授权管理服务类
 *
 * @author shitf (mailto:shitf@primeton.com)
 */
public class CapPartyauthService extends DASDaoSupport implements ICapPartyauthService{
	public void addCapPartyauth(CapPartyauth capPartyauth){
		getDASTemplate().getPrimaryKey(capPartyauth);
		getDASTemplate().insertEntity(capPartyauth);
	}

	public void deleteCapPartyauth(CapPartyauth[] capPartyauths ){
		for(DataObject capPartyauth:capPartyauths){
			getDASTemplate().deleteEntityCascade(capPartyauth);
		}
	}

	public void getCapPartyauth(CapPartyauth capPartyauth){
		getDASTemplate().expandEntity(capPartyauth);
	}

	public CapPartyauth[]  queryCapPartyauths(CriteriaType criteriaType,PageCond pageCond){
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(CapPartyauth.class, dasCriteria, pageCond);
	}


    public void updateCapPartyauth(CapPartyauth capPartyauth){
	    getDASTemplate().updateEntity(capPartyauth);
    }
    
    public void saveCapPartyauth(CapRole[] capRoles,String partyId){
    	CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
    	capPartyauth.setPartyId(partyId);
    	getDASTemplate().deleteByTemplate(capPartyauth);
    	CapPartyauth[] capPartyauths = DefaultPartyManager.INSTANCE.bulidCapPartyauth(capRoles, partyId);
    	getDASTemplate().saveEntities(capPartyauths);
    }
    
    public CapRole[] getAuths(String partyId) {
		CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
		capPartyauth.setPartyId(partyId);
		CapPartyauth[] capPartyauths = getDASTemplate().queryEntitiesByTemplate(CapPartyauth.class,
				capPartyauth);
		return DefaultPartyManager.INSTANCE.getRoles(capPartyauths);
	}
    
    public void deleteCapPartyauthByTemplate(CapPartyauth capPartyauth){
    	getDASTemplate().deleteByTemplate(capPartyauth);
    }
}

