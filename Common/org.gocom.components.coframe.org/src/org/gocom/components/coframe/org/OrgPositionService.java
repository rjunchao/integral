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

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.dataset.QueryPositionEmp;
import org.gocom.components.coframe.org.util.OrgHelper;
import org.gocom.components.coframe.org.util.OrgResponse;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;

/**
 * 岗位服务类
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class OrgPositionService extends DASDaoSupport implements IOrgPositionService {
	
	private IOrgEmppositionService empPositionService = null;
	
	public OrgResponse addOrgPosition(OrgPosition position){
		if(!validatePosicode(position)){
			return new OrgResponse(false, "岗位编号："+position.getPosicode()+"已存在");
		}
		OrgPosition parentPosition = position.getOrgPosition();
		if(parentPosition != null && parentPosition.getPositionid() != null){
			parentPosition = getOrgPositionWithDetail(parentPosition);
		}
		getDASTemplate().getPrimaryKey(position);
		OrgHelper.expandPositionPropertyByParent(position, parentPosition);
		getDASTemplate().insertEntity(position);
		if(parentPosition != null && parentPosition.getPositionid() != null){
			OrgHelper.expandParentPositionProperty(parentPosition);
			getDASTemplate().updateEntity(parentPosition);
		}
		return new OrgResponse(true, "添加成功");
	}

	public void deleteOrgPosition(OrgPosition[] orgPositions ){
		if(orgPositions == null) return;
		for(OrgPosition orgPosition:orgPositions){
			getDASTemplate().deleteEntityCascade(orgPosition);
		}
	}

	public void getOrgPosition(OrgPosition orgPosition){
		getDASTemplate().expandEntity(orgPosition);
	}
	
	private OrgPosition getOrgPositionWithDetail(OrgPosition position){
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		dasCriteria.addAssociation("orgPosition");
		dasCriteria.addAssociation("orgOrganization");
		dasCriteria.add(ExpressionHelper.eq("positionid", position.getPositionid()));
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, dasCriteria);
		if(positions != null && positions.length == 1){
			return positions[0];
		}
		return OrgPosition.FACTORY.create();
	}
	
	

	public OrgPosition[] queryOrgPositions(CriteriaType criteriaType,PageCond pageCond){
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		dasCriteria.addAssociation("orgOrganization");
		dasCriteria.addAssociation("orgPosition");
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntityWithPage(OrgPosition.class, dasCriteria, pageCond);
		return positions;
	}


    public OrgResponse updateOrgPosition(OrgPosition position){
		if(!validatePosicode(position)){
			return new OrgResponse(false, "不能修改为已存在的岗位编号："+position.getPosicode());
		}
		getDASTemplate().updateEntity(position);
		return new OrgResponse(true, "修改成功");
    }

	/**
	 * 获取岗位下的所有子岗位
	 * @param positionid
	 * @return
	 */
	public OrgPosition[] querySubPositions(String positionid) {
		if(StringUtils.isBlank(positionid)) {
			return new OrgPosition[0];
		}
		
		// 设置查询条件
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		dasCriteria.add(ExpressionHelper.eq("orgPosition.positionid", positionid));
		
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, dasCriteria);
		
		return positions;
	}
	
	/**
	 * 获取岗位下的所有员工
	 * @param orgid
	 * @return
	 */
	public QueryPositionEmp[] queryEmployeesOfPosition(String positionid) {
		if(StringUtils.isBlank(positionid)) {
			return new QueryPositionEmp[0];
		}
		
		// 设置查询条件
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(QueryPositionEmp.QNAME);
		dasCriteria.add(ExpressionHelper.eq("positionid", positionid));
		
		QueryPositionEmp[] emps = getDASTemplate().queryEntitiesByCriteriaEntity(QueryPositionEmp.class, dasCriteria);
		
		return emps;
	}
	
	/**
	 * 岗位的总记录
	 * @param criteria
	 * @return 总记录
	 */
	public int countOrgPositions(CriteriaType criteria) {
		criteria.set_entity(OrgPosition.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		return getDASTemplate().count(dasCriteria);
	}
	
	/**
	 * 获取子岗位的同时获取父岗位信息
	 * @param position
	 */
	public OrgPosition getOrgPositionWithParent(OrgPosition position){
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		dasCriteria.add(ExpressionHelper.eq("positionid", position.getPositionid()));
		dasCriteria.addAssociation("orgPosition");
		dasCriteria.addAssociation("orgOrganization");
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, dasCriteria);
		if(positions != null && positions.length == 1){
			return positions[0];
		}
		return OrgPosition.FACTORY.create();
	}
	
	/* (non-Javadoc)
	 * @see org.gocom.components.coframe.org.IOrgPositionService#deleteOrgPositionCascade(org.gocom.components.coframe.org.dataset.OrgOrganization)
	 */
	public void deleteOrgPositionCascadeByParentOrg(OrgOrganization parentOrg) {
		//删除子岗位之前，先删除子岗位和员工的关联关系
		OrgPosition[] children = querySubPositionsByOrg(parentOrg);
		for(OrgPosition position : children){
			empPositionService.deleteEmppositionsByPosition(position);
		}
		deleteOrgPosition(children);
	}

	/**
	 * 根据父机构查询所有子岗位
	 * @param parentOrg 父机构
	 * @return 子岗位列表
	 */
	public OrgPosition[] querySubPositionsByOrg(OrgOrganization parentOrg) {
		if(parentOrg.getOrgid() != null){
			IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
			dasCriteria.add(ExpressionHelper.eq("orgOrganization.orgid", parentOrg.getOrgid()));
			return getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, dasCriteria);
		}
		return new OrgPosition[0];
	}
	
	/**
	 * 检验岗位code是否合格
	 * @param posicode
	 * @return true合格，false不合格
	 */
	public boolean validatePosicode(OrgPosition position) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgPosition.QNAME);
		dasCriteria.add(ExpressionHelper.eq("posicode", position.getPosicode()));
		OrgPosition[] positions = getDASTemplate().queryEntitiesByCriteriaEntity(OrgPosition.class, dasCriteria);
		if(positions == null || positions.length == 0){
			return true;
		}
		if(position.getPositionid() != null && positions[0].getPositionid().equals(position.getPositionid())){//修改的情况，只能为1个
			return positions.length == 1;
		}else{//新增,必定没有
			return positions.length == 0;
		}
	}
	
	public void deleteOrgPosition(String positionid, String parentPositionid, String parentType) {
		//以本节点为基准删除
		if(!StringUtils.isBlank(positionid)){
			//先删其子岗位的
			OrgPosition[] positions = querySubPositions(positionid);
			for(OrgPosition position : positions){
				deleteOrgPosition(position.getPositionid()+"", null, null);
			}
			//删除这个岗位和人员的关联关系
			OrgPosition position = OrgPosition.FACTORY.create();
			position.setPositionid(new BigDecimal(positionid));
			empPositionService.deleteEmppositionsByPosition(position);
			//删除自身
			deleteOrgPosition(new OrgPosition[]{position});
		}
	}
	
	public void deleteOrgPosition(String positionid) {
		if(!StringUtils.isBlank(positionid)){
			OrgPosition position = OrgPosition.FACTORY.create();
			position.setPositionid(new BigDecimal(positionid));
			deleteOrgPosition(new OrgPosition[]{position});
		}
	}
	
	public void setEmpPositionService(IOrgEmppositionService empPositionService) {
		this.empPositionService = empPositionService;
	}

}