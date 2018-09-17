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
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmporg;
import org.gocom.components.coframe.org.dataset.OrgEmpposition;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.dataset.QueryEmpUser;
import org.gocom.components.coframe.org.util.IOrgConstants;
import org.gocom.components.coframe.org.util.OrgHelper;
import org.gocom.components.coframe.org.util.OrgResponse;
import org.gocom.components.coframe.rights.dataset.CapUser;
import org.gocom.components.coframe.rights.user.DefaultUserManager;
import org.gocom.components.coframe.rights.user.ICapUserService;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;

/**
 * 人员服务类
 *
 *  @author yangyong (mailto:yangyong@primeton.com)
 */
public class OrgEmployeeService extends DASDaoSupport implements IOrgEmployeeService{
	
	private ICapUserService userService = null;
	
	private IOrgEmporgService emporgService = null;
	
	private IOrgEmppositionService empPositionService = null;
	
	public OrgResponse addOrgEmployee(OrgEmployee emp, CapUser user, OrgOrganization org){
		if(!validateEmpcode(emp)){
			return new OrgResponse(false, "员工代码："+emp.getEmpcode() + "已存在");
		}
		//存在用户关联
		if(!StringUtils.isBlank(user.getUserId())){
			CapUser existsUser = userService.getCapUserByUserId(user.getUserId());
			//emp是否关联用户
			if(existsUser != null){//不能关联用户
				OrgEmployee[] userRelatedEmployees = queryEmployeesByOperatorId(existsUser);
				if(userRelatedEmployees != null && userRelatedEmployees.length > 0){
					return new OrgResponse(false, "用户登录名:"+existsUser.getUserId()+"已关联员工");
				}
				user.setOperatorId(existsUser.getOperatorId());
				user.setPassword(DefaultUserManager.INSTANCE.encodeString(user.getPassword()));
				userService.updateCapUser(user);//覆盖已有的用户信息
			}else{//新用户
				user.setUserName(user.getUserId());
				userService.addCapUser(user);
			}
			//关联用户和员工
			emp.setOperatorid(user.getOperatorId());
			emp.setUserid(user.getUserId());
		}
		
		//设置主键
		getDASTemplate().getPrimaryKey(emp);
		
		OrgHelper.expandEmployeeProperty(emp);
		
		emp.setOrgid(org.getOrgid());
		getDASTemplate().insertEntity(emp);
		
		//添加员工机构关系
		addEmporg(emp, org);
		return new OrgResponse(true, "添加成功");
	}
	
	/**
	 * 检验员工code是否合格
	 * @param emp
	 * @return true合格，false不合格
	 */
	public boolean validateEmpcode(OrgEmployee emp) {
		IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmployee.QNAME);
		dasCriteria.add(ExpressionHelper.eq(IOrgConstants.EMPCODE_PROPERTY, emp.getEmpcode()));
		OrgEmployee[] existsEmps = getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmployee.class, dasCriteria);
		if(existsEmps == null || existsEmps.length == 0){
			return true;
		}
		if(emp.getEmpid() != null && existsEmps[0].getEmpid().equals(emp.getEmpid())){//修改的情况，只能为1个
			return existsEmps.length == 1;
		}else{//新增,必定没有
			return existsEmps.length == 0;
		}
	}
	
	/**
	 * 添加机构和人员的关系
	 * @param emp 员工
	 * @param org 机构
	 */
	private void addEmporg(OrgEmployee emp, OrgOrganization org){
		OrgEmporg orgEmpAssosiation = OrgEmporg.FACTORY.create();
		orgEmpAssosiation.setOrgEmployee(emp);
		orgEmpAssosiation.setOrgid(org.getOrgid());
		emporgService.addOrgEmporg(orgEmpAssosiation);
	}

	/**
	 * 根据用户查询出它关联的员工
	 * @param user
	 * @return
	 */
	private OrgEmployee[] queryEmployeesByOperatorId(CapUser user) {
		if(user != null && user.getOperatorId() != null){
			IDASCriteria dasCriteria = getDASTemplate().createCriteria(OrgEmployee.QNAME);
			dasCriteria.add(ExpressionHelper.eq("operatorid", user.getOperatorId()));
			return getDASTemplate().queryEntitiesByCriteriaEntity(OrgEmployee.class, dasCriteria);
		}
		return new OrgEmployee[0];
	}

	public void deleteOrgEmployee(OrgEmployee[] emps){
		if(emps == null) return;
		for(OrgEmployee emp:emps){
			empPositionService.deleteEmppositionsByEmp(emp);
			emporgService.deleteEmporgByEmp(emp);
			getDASTemplate().deleteEntityCascade(emp);
		}
	}

	public void getOrgEmployee(OrgEmployee emp){
		getDASTemplate().expandEntity(emp);
	}
	
	public OrgEmployee[] queryOrgEmployees(CriteriaType criteriaType,PageCond pageCond){
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(OrgEmployee.class, dasCriteria, pageCond);
	}
	
	public QueryEmpUser[]  queryEmpUsers(CriteriaType criteriaType,PageCond pageCond){
		criteriaType.set_entity(QueryEmpUser.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(QueryEmpUser.class, dasCriteria, pageCond);
	}
	
	public int countOrgEmployees(CriteriaType criteria) {
		criteria.set_entity(OrgEmployee.QNAME);
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteria);
		return getDASTemplate().count(dasCriteria);
	}
	
	public OrgResponse updateOrgEmployee(OrgEmployee emp) {
		if(!validateEmpcode(emp)){
			return new OrgResponse(false, "不能修改为已存在的员工代码:"+emp.getEmpcode());
		}
		getDASTemplate().updateEntity(emp);
		return new OrgResponse(true, "修改成功");
	}
	
    public OrgResponse updateOrgEmployee(OrgEmployee emp, CapUser user){
		if(!validateEmpcode(emp)){
			return new OrgResponse(false, "不能修改为已存在的员工代码:"+emp.getEmpcode());
		}
		//存在用户关联
		if(!StringUtils.isBlank(user.getUserId())){
			CapUser existsUser = userService.getCapUserByUserId(user.getUserId());
			if(existsUser == null){
				user.setPassword(IConstants.DEFAULT_PASSWORD);
				userService.addCapUser(user);
				emp.setUserid(user.getUserId());
				emp.setOperatorid(user.getOperatorId());
			}else{//可以关联原来的员工
				if(existsUser.getOperatorId().equals(user.getOperatorId())){//同一个用户，可以更新
					userService.updateCapUser(user);
				}else{//不同用户，则不能关联已存在员工的用户
					OrgEmployee[] employees = queryEmployeesByOperatorId(existsUser);
					if(employees.length > 0){
						return new OrgResponse(false, "不能关联到一个已关联员工的用户");
					}
				}
				emp.setOperatorid(existsUser.getOperatorId());
				emp.setUserid(existsUser.getUserId());
			}
		}else{
			//取消员工和用户的关联
			emp.setUserid(null);
			emp.setOperatorid(null);
		}
		getDASTemplate().updateEntity(emp);
		return new OrgResponse(true, "修改成功");
    }
    
	/* (non-Javadoc)
     * @see org.gocom.components.coframe.org.IOrgEmployeeService#deleteEmpAndOrgRelationship(org.gocom.components.coframe.org.dataset.OrgOrganization)
     */
    public void deleteEmpAndOrgRelationship(OrgOrganization org) {
    	if(org.getOrgid() == null) return;
    	OrgEmporg[] empOrgs = emporgService.queryOrgEmporgsByOrg(org);
    	if(empOrgs != null && empOrgs.length > 0){
    		emporgService.deleteOrgEmporg(empOrgs);
    	}
    }
    
    public void deleteOrgEmployee(String id, String parentId, String parentType,String isDeleteUserCascade) {
    	if(StringUtils.isBlank(parentType)) return;
    	OrgEmployee empWillBeDelete = OrgEmployee.FACTORY.create();
    	empWillBeDelete.setEmpid(new BigDecimal(id));
    	if("true".equals(isDeleteUserCascade)){
    		getOrgEmployee(empWillBeDelete);
    	}
    	if(OrgOrganization.NODE_TYPE.equals(parentType)){//父节点是机构
    		//删除机构和人员的关系
    		OrgEmporg empOrgAssosiation = OrgEmporg.FACTORY.create();
    		empOrgAssosiation.setOrgid(new BigDecimal(parentId));
    		empOrgAssosiation.setOrgEmployee(empWillBeDelete);
    		//删除关联关系
    		emporgService.deleteOrgEmporg(new OrgEmporg[]{empOrgAssosiation});
    		//判断人员是否在其他机构下，如果不在，则删除这个员工
    		OrgEmporg[] existsEmpOrgAssosiations = emporgService.queryOrgEmporgsByEmp(empWillBeDelete);
    		if(existsEmpOrgAssosiations.length == 0){
    			deleteOrgEmployee(new OrgEmployee[]{empWillBeDelete});
    		}
    	}else if(OrgPosition.NODE_TYPE.equals(parentType)){//父节点是岗位
    		//直接删除人员和岗位的关联关系
    		OrgEmpposition empPositionAssosiation = OrgEmpposition.FACTORY.create();
    		OrgPosition position = OrgPosition.FACTORY.create();
    		position.setPositionid(new BigDecimal(parentId));
    		empPositionAssosiation.setOrgEmployee(empWillBeDelete);
    		empPositionAssosiation.setOrgPosition(position);
    		empPositionService.deleteOrgEmpposition(new OrgEmpposition[]{empPositionAssosiation});
    	}
    	CapUser capUser = CapUser.FACTORY.create();
    	capUser.setUserId(empWillBeDelete.getUserid());
    	if(capUser.getUserId()!=null){
    		userService.deleteCapUserByTemplate(capUser);
    	}
    }
    
	public OrgEmployee[] queryEmpsAllowAddInPosition(String userid, String empname, String positionid, PageCond page) {
		OrgEmployee[] emps = getDASTemplate().queryByNamedSqlWithPage(
				OrgEmployee.class, "org.gocom.components.coframe.org.empQuery.selectEmpAllowAdd", page, getNameSqlParamsMap(userid, empname, positionid));
		return emps;
	}
	
	public Integer countEmpsAllowAddInPosition(String userid, String empname, String positionid) {
		Integer[] empSize = getDASTemplate().queryByNamedSql(Integer.class,"org.gocom.components.coframe.org.empQuery.countEmpAllowAdd", getNameSqlParamsMap(userid, empname, positionid));
		if(empSize == null || empSize.length == 0) return 0;
		return empSize[0];
	}

	public OrgEmployee[] queryEmpsInPosition(String userid, String empname, String positionid, PageCond page) {
		OrgEmployee[] emps = getDASTemplate().queryByNamedSqlWithPage(
				OrgEmployee.class, "org.gocom.components.coframe.org.empQuery.selectEmpInPosition",page, getNameSqlParamsMap(userid, empname, positionid));
		return emps;		
	}
	
	public Integer countEmpsInPosition(String userid, String empname, String positionid) {
		Integer[] empSize = getDASTemplate().queryByNamedSql(Integer.class, "org.gocom.components.coframe.org.empQuery.countEmpInPosition", getNameSqlParamsMap(userid, empname, positionid));
		if(empSize == null || empSize.length == 0) return 0;
		return empSize[0];
	}
	
	private HashMap<String, Object> getNameSqlParamsMap(String userid, String empname, String positionid){
		HashMap<String, Object> nameSqlParamsMap = new HashMap<String, Object>();
		if(!StringUtils.isBlank(userid)){
			nameSqlParamsMap.put("userid", userid);
		}
		if(!StringUtils.isBlank(empname)){
			nameSqlParamsMap.put("empname", empname);
		}
		if(!StringUtils.isBlank(positionid)){
			nameSqlParamsMap.put("positionid", new BigDecimal(positionid));
		}
		return nameSqlParamsMap;
	}
	
	public void setUserService(ICapUserService userService) {
		this.userService = userService;
	}

	public void setEmporgService(IOrgEmporgService emporgService) {
		this.emporgService = emporgService;
	}

	public void setEmpPositionService(IOrgEmppositionService empPositionService) {
		this.empPositionService = empPositionService;
	}
	
	public void deleteCapUserRelatedEmp(String userId){
		CapUser capUser = CapUser.FACTORY.create();
		capUser.setUserId(userId);
		getDASTemplate().deleteByTemplate(capUser);
	}

}