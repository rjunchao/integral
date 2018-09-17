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

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import com.primeton.cap.TenantManager;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmpposition;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.util.IOrgConstants;

import commonj.sdo.DataObject;

/**
 * 岗位和人员的关系服务类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public class OrgEmppositionService extends DASDaoSupport implements IOrgEmppositionService{
	
	public void addOrgEmpposition(OrgEmpposition orgEmpposition){
		orgEmpposition.setTenantid(TenantManager.getCurrentTenantID());
		orgEmpposition.setIsmain("y");
		getDASTemplate().insertEntity(orgEmpposition);
	}
	
	public void addOrgEmpposition(OrgEmpposition[] emppositions){
		if(emppositions != null && emppositions.length > 0){
			for(OrgEmpposition empposition : emppositions){
				addOrgEmpposition(empposition);
			}
		}
	}

	public void deleteOrgEmpposition(OrgEmpposition[] orgEmppositions){
		if(orgEmppositions == null) return;
		for(DataObject orgEmpposition:orgEmppositions){
			getDASTemplate().deleteEntityCascade(orgEmpposition);
		}
	}


	public void getOrgEmpposition(OrgEmpposition orgEmpposition){
		getDASTemplate().expandEntity(orgEmpposition);
	}


	public OrgEmpposition[]  queryOrgEmppositions(CriteriaType criteriaType,PageCond pageCond){
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(OrgEmpposition.class, dasCriteria, pageCond);
	}


    public void updateOrgEmpposition(OrgEmpposition orgEmpposition){
	    getDASTemplate().updateEntity(orgEmpposition);
    }

	public void deleteEmppositionsByPosition(OrgPosition position) {
		OrgEmpposition[] empPositions = queryEmppositionsByPosition(position);
		if(empPositions != null && empPositions.length > 0){
			deleteOrgEmpposition(empPositions);
		}
	}

	public void deleteEmppositionsByEmp(OrgEmployee emp) {
		OrgEmpposition[] empPositions = queryEmppositionsByEmp(emp);
		if(empPositions != null && empPositions.length > 0){
			deleteOrgEmpposition(empPositions);
		}
	}
	
	/**
	 * 根据岗位查询出所有岗位人员关联关系
	 * @param position
	 * @return
	 */
	private OrgEmpposition[] queryEmppositionsByPosition(OrgPosition position) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmpposition.QNAME);
		dasCriteria.add(ExpressionHelper.eq("orgPosition.positionid", position.getPositionid()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmpposition.class, dasCriteria);
	}


	/**
	 * 根据员工查出所有员工岗位关联关系
	 * @param emp
	 * @return 员工岗位关联关系列表
	 */
	private OrgEmpposition[] queryEmppositionsByEmp(OrgEmployee emp) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmpposition.QNAME);
		dasCriteria.add(ExpressionHelper.eq(IOrgConstants.EMP_REF_PROPERTY + "." + 
				IOrgConstants.EMPID_PROPERTY, emp.getEmpid()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmpposition.class, dasCriteria);
	}
    
	public OrgEmpposition[] queryOrgEmppositionsOfEmp(String empId, String parentOrgId) {
		DataObject dataObject = OrgEmpposition.FACTORY.create();
		dataObject.setBigDecimal("empId", new BigDecimal(parentOrgId));
		dataObject.setBigDecimal("parentOrgId", new BigDecimal(parentOrgId));
		OrgEmpposition[] empPositions = getDASTemplate().queryByNamedSql(
				OrgEmpposition.class, "org.gocom.components.coframe.org.empposition.select_empposition", dataObject);
		return empPositions == null ? new OrgEmpposition[0] : empPositions;
	}

	public OrgEmployee[] queryEmpsByPosition(OrgPosition position) {
		OrgEmpposition[] empPositions = queryEmppositionsByPosition(position);
		OrgEmployee[] emps = new OrgEmployee[empPositions.length];
		for(int i=0;i<empPositions.length;i++){
			emps[i] = empPositions[i].getOrgEmployee();
		}
		return emps;
	}

}