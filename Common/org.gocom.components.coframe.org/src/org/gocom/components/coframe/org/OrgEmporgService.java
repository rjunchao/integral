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
package org.gocom.components.coframe.org;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmporg;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.util.IOrgConstants;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.TenantManager;

import commonj.sdo.DataObject;

/**
 * 人员机构关系服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public class OrgEmporgService extends DASDaoSupport implements IOrgEmporgService{
	
	private OrgEmppositionService empPositionService = null;
	
	public void addOrgEmporg(OrgEmporg orgEmporgAssosiation){
		orgEmporgAssosiation.setTenantid(TenantManager.getCurrentTenantID());
		orgEmporgAssosiation.setIsmain(IOrgConstants.IS_MAIN_YES);
		getDASTemplate().insertEntity(orgEmporgAssosiation);
	}

	public void deleteOrgEmporg(OrgEmporg[] orgEmporgs){
		if(orgEmporgs == null) return;
		for(DataObject orgEmporg:orgEmporgs){
			getDASTemplate().deleteEntityCascade(orgEmporg);
		}
	}

	public void getOrgEmporg(OrgEmporg orgEmporg){
		getDASTemplate().expandEntity(orgEmporg);
	}


	public OrgEmporg[]  queryOrgEmporgs(CriteriaType criteriaType,PageCond pageCond){
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(OrgEmporg.class, dasCriteria, pageCond);
	}

    public void updateOrgEmporg(OrgEmporg orgEmporg){
	    getDASTemplate().updateEntity(orgEmporg);
    }
    
    public void deleteEmporgByEmp(OrgEmployee emp) {
    	OrgEmporg[] empOrgs = queryOrgEmporgsByEmp(emp);
    	if(empOrgs != null && empOrgs.length > 0){
    		deleteOrgEmporg(empOrgs);
    	}
    }

	public OrgEmporg[] queryOrgEmporgsByOrg(OrgOrganization org) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmporg.QNAME);
		dasCriteria.add(ExpressionHelper.eq(IOrgConstants.ORGID_PROPERTY, org.getOrgid()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmporg.class, dasCriteria);
	}

	/**
	 * 根据员工查询出所有员工机构的关联关系
	 * @param emp
	 * @return 员工机构关联关系列表
	 */
	public OrgEmporg[] queryOrgEmporgsByEmp(OrgEmployee emp) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmporg.QNAME);
		dasCriteria.add(ExpressionHelper.eq(IOrgConstants.EMP_REF_PROPERTY + "." + 
				IOrgConstants.EMPID_PROPERTY, emp.getEmpid()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmporg.class, dasCriteria);
	}

	public OrgEmployee[] queryEmpsByOrgDifferFromPosition(OrgOrganization org, OrgPosition position) {
		OrgEmporg[] empOrgs = queryOrgEmporgsByOrg(org);
		OrgEmployee[] emps = new OrgEmployee[empOrgs.length];
		Map<BigDecimal,OrgEmployee> map = new HashMap<BigDecimal, OrgEmployee>();
		List<OrgEmployee> list = new ArrayList<OrgEmployee>();
		for(int i=0;i<empOrgs.length;i++){
			emps[i] = empOrgs[i].getOrgEmployee();
			map.put(emps[i].getEmpid(), emps[i]);
		}
		OrgEmployee[] emps2 = empPositionService.queryEmpsByPosition(position);
		for(int i=0;i<emps2.length;i++){
			if(map.containsKey(emps2[i].getEmpid())){
				map.remove(emps2[i].getEmpid());
			}
		}
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()){
			list.add(map.get(iter.next()));
		}
		return (OrgEmployee[]) list.toArray();
	}
	
	
	public void setEmpPositionService(OrgEmppositionService empPositionService){
		this.empPositionService = empPositionService;
	}
	
}


