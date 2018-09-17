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

import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapRole;

/**
 * 
 *
 * @author shitf (mailto:shitf@primeton.com)
 */
public interface ICapPartyauthService{

	/**
	 *
	 * @param capPartyauth CapPartyauth
	 */
	void addCapPartyauth(CapPartyauth capPartyauth);

	/**
	 *
	 * @param capPartyauths CapPartyauth[]
	 */
	void deleteCapPartyauth(CapPartyauth[] capPartyauths);

	/**
	 *
	 * @param capPartyauth CapPartyauth
	 */
	void getCapPartyauth(CapPartyauth capPartyauth);

	/**
	 *
	 * @param criteria CriteriaType
	 * @param page PageCond
	 * @return CapPartyauth[]
	 */
	CapPartyauth[] queryCapPartyauths(CriteriaType criteriaType,
			PageCond pageCond);

	/**
	 *
	 * @param capPartyauth CapPartyauth
	 */
	void updateCapPartyauth(CapPartyauth capPartyauth);
	
	/**
     * 保存多条CapPartyauth记录
     * @param capPartyauths CapPartyauth对象数组
     */
    void saveCapPartyauth(CapRole[] capRoles,String partyId);
    
    /**
     * 根据partyId查询所有记录
     * @param partyId 用户Id
     * @return CapPartyauth对象数组
     */
    CapRole[] getAuths(String partyId);
    
    /**
     * 根据模板删除party信息
     * @param capPartyauth
     */
    void deleteCapPartyauthByTemplate(CapPartyauth capPartyauth);

}
