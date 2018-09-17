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
package org.gocom.components.coframe.flowconfig;

import org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule;

import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import commonj.sdo.DataObject;

/**
 * 流程规则操作类
 *
 * @author wanghl (mailto:wanghl@primeton.com)
 */
public class CapRuleService extends DASDaoSupport implements ICapRuleService{
	public void addCapRule(CapRule capRule){
		getDASTemplate().getPrimaryKey(capRule);
		getDASTemplate().insertEntity(capRule);
	}

	public void deleteCapRule(CapRule[] capRules ){
		for(DataObject capRule:capRules){
			getDASTemplate().deleteEntityCascade(capRule);
		}
	}


	public void getCapRule(CapRule capRule){
		getDASTemplate().expandEntity(capRule);
	}

	public CapRule[]  queryCapRules(CriteriaType criteria,PageCond pageCond, String sortField, String sortOrder){
		criteria.set_entity(CapRule.QNAME);	
		if(sortField != null && sortOrder != null){
			criteria.set("_orderby[1]/_property", sortField);
			criteria.set("_orderby[1]/_sort",sortOrder);
		}
		pageCond.setBegin(pageCond.getBegin() * pageCond.getLength());
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(CapRule.class, dasCriteria, pageCond);
	}


    public void updateCapRule(CapRule capRule){
	    getDASTemplate().updateEntity(capRule);
    }
    
    public int countCapRules(CriteriaType criteria){
    	criteria.set_entity(CapRule.QNAME);	
    	IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
    	return getDASTemplate().count(dasCriteria);
    }
    
}


