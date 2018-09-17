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
package org.gocom.components.coframe.auth.party.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gocom.components.coframe.auth.queryentity.QueryEmpByRole;
import org.gocom.components.coframe.auth.queryentity.QueryOrgByRole;
import org.gocom.components.coframe.auth.queryentity.QueryPositionByRole;
import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.das.entity.criteria.CriteriaType;
import com.eos.foundation.PageCond;
import com.eos.spring.DASDaoSupport;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.AppUserManager;
import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.PartyType;
import com.primeton.das.entity.impl.hibernate.criterion.Criterion;
import commonj.sdo.DataObject;

/**
 * 员工信息管理，提供对员工数据实体的数据库操作（持久化）
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class DefaultRoleManager extends DASDaoSupport {

	private static String CAP_ROLE_ENTITY_ID_PROPERTY = "roleId";

	private static String CAP_ROLE_ENTITY_CODE_PROPERTY = "roleCode";

	private static String CAP_ROLE_ENTITY_NAME_PROPERTY = "roleName";

	public static String SPRING_BEAN_NAME = "DefaultRoleManagerBean";

	private static String CAP_PARTY_ID = "partyId";

	private static String CAP_PARTY_TYPE = "partyType";

	private final static String HAS_AUTH = "hasAuth";

	/**
	 * 
	 */
	public DefaultRoleManager() {
	}

	public CapRole[] getAllRoleListByTenant(String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
	}

	public CapRole getRoleByRoleIDAndTenant(String roleID, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(CAP_ROLE_ENTITY_ID_PROPERTY, roleID));
		CapRole[] roles = getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
		if (roles != null && roles.length > 0) {
			return roles[0];
		}
		return null;
	}

	public CapRole[] getAllRoleList() {
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
	}

	public CapRole getRoleByRoleID(String roleID) {
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		criteria.add(ExpressionHelper.eq(CAP_ROLE_ENTITY_ID_PROPERTY, roleID));
		CapRole[] roles = getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
		if (roles != null && roles.length > 0) {
			return roles[0];
		}
		return null;
	}

	/**
	 * 用于授权界面的角色列表查询
	 * 
	 * @param roleCode
	 * @param roleName
	 * @return
	 */
	public CapRole[] getRoleList(String roleCode, String roleName, DataObject pagecond) {
		String tenantID = TenantManager.getCurrentTenantID();
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		if (StringUtil.isNotEmpty(roleCode)) {
			criteria.add(ExpressionHelper.like(CAP_ROLE_ENTITY_CODE_PROPERTY, "%" + roleCode + "%"));
		}
		if (StringUtil.isNotEmpty(roleName)) {
			criteria.add(ExpressionHelper.like(CAP_ROLE_ENTITY_NAME_PROPERTY, "%" + roleName + "%"));
		}
		criteria.asc(CAP_ROLE_ENTITY_CODE_PROPERTY);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(CapRole.class, criteria, pagecond);

	}

	/**
	 * 根据角色代码判断是否已经存在
	 * 
	 * @param roleCode
	 * @return
	 */
	public boolean isRoleCodeExist(String roleCode) {
		String tenantID = TenantManager.getCurrentTenantID();
		IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq(CAP_ROLE_ENTITY_CODE_PROPERTY, roleCode));
		CapRole[] roles = getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
		if (roles != null && roles.length > 0) {
			return true;
		}
		return false;
	}

	public boolean insertRole(CapRole role) {
		String tenantID = TenantManager.getCurrentTenantID();
		role.setTenantId(tenantID);
		role.setCreatetime(new Date());
		role.setCreateuser(AppUserManager.getCurrentUserId());
		try {
			getDASTemplate().getPrimaryKey(role);
			getDASTemplate().insertEntity(role);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public boolean updateRole(CapRole role) {
		String tenantID = TenantManager.getCurrentTenantID();
		role.setTenantId(tenantID);
		try {
			getDASTemplate().updateEntity(role);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public int deleteRole(String roleID) {
		PartyType rolePartyType = AuthRuntimeManager.getInstance().getRolePartyType();
		if (rolePartyType != null) {
			String tenantID = TenantManager.getCurrentTenantID();
			if (AuthRuntimeManager.getInstance().delRoleAuth(new Party(rolePartyType.getTypeID(), roleID, null, null, tenantID))) {
				IDASCriteria criteria = DASManager.createCriteria(CapRole.QNAME);
				criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
				criteria.add(ExpressionHelper.eq(CAP_ROLE_ENTITY_ID_PROPERTY, roleID));
				int result = getDASTemplate().deleteByCriteriaEntity(criteria);
				if (result != 0) {
					AuthRuntimeManager.getInstance().delRoleAuth(
							new Party(IConstants.ROLE_PARTY_TYPE_ID, roleID, null, null, TenantManager.getCurrentTenantID()));
					return result;
				}
			}
		}
		return 0;
	}

	/**
	 * 根据PartyID，PartyTypeID，租户获取其拥有的角色
	 * 
	 * @param partyTypeId
	 * @param partyID
	 * @param tenantId
	 * @return
	 */
	public CapRole[] getCapRoleListByAuthParty(String partyTypeId, String partyId, String tenantId) {
		IDASCriteria criteria = DASManager.createCriteria(CapPartyauth.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantId));
		criteria.add(ExpressionHelper.eq(CAP_PARTY_ID, partyId));
		criteria.add(ExpressionHelper.eq(CAP_PARTY_TYPE, partyTypeId));
		criteria.addAssociation("capRole");
		CapPartyauth[] authRoles = getDASTemplate().queryEntitiesByCriteriaEntity(CapPartyauth.class, criteria);
		List<CapRole> returnList = new ArrayList<CapRole>();
		if (authRoles != null) {
			for (CapPartyauth authRole : authRoles) {
				returnList.add(authRole.getCapRole());
			}
		}
		return returnList.toArray(new CapRole[returnList.size()]);
	}

	/**
	 * 通过传入多种party来获取拥有的角色列表
	 * 
	 * @param partyTypeIdArray
	 * @param partyIdArray
	 * @param tenantId
	 * @return
	 */
	public CapRole[] getCapRoleListByAuthPartyList(String[] partyTypeIdArray, String[] partyIdArray, String tenantId) {
		List<CapRole> returnList = new ArrayList<CapRole>();
		if (partyTypeIdArray != null && partyIdArray != null && partyIdArray.length == partyTypeIdArray.length) {
			IDASCriteria criteria = DASManager.createCriteria(CapPartyauth.QNAME);
			criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantId));
			Criterion criterion = null;
			for (int i = 0; i < partyIdArray.length; i++) {
				criterion = getIteratorCriterion(criterion, partyIdArray[i], partyTypeIdArray[i]);
			}
			criteria.add(criterion);
			criteria.addAssociation("capRole");
			CapPartyauth[] authRoles = getDASTemplate().queryEntitiesByCriteriaEntity(CapPartyauth.class, criteria);

			if (authRoles != null) {
				for (CapPartyauth authRole : authRoles) {
					returnList.add(authRole.getCapRole());
				}
			}
		}

		return returnList.toArray(new CapRole[returnList.size()]);
	}

	private Criterion getIteratorCriterion(Criterion criterion, String partyId, String partyTypeId) {
		if (criterion == null) {
			return ExpressionHelper.and(ExpressionHelper.eq(CAP_PARTY_ID, partyId), ExpressionHelper.eq(CAP_PARTY_TYPE, partyTypeId));
		} else {
			return ExpressionHelper.or(criterion, ExpressionHelper.and(ExpressionHelper.eq(CAP_PARTY_ID, partyId), ExpressionHelper.eq(CAP_PARTY_TYPE,
					partyTypeId)));
		}
	}
	
	/**
	 * 根据角色ID获取人员列表
	 * 
	 * @param roleId 角色ID
	 * @param tenantID 租户ID
	 * @return 人员列表
	 */
	public QueryEmpByRole[] queryEmpsByRoleId(String roleId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryEmpByRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("roleId", roleId));
		criteria.add(ExpressionHelper.eq("partyType", IConstants.EMP_PARTY_TYPE_ID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryEmpByRole.class, criteria);
	}

	public CapRole[] queryRoleListByCriteria(CriteriaType criteriaType) {
		IDASCriteria criteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, TenantManager.getCurrentTenantID()));
		return getDASTemplate().queryEntitiesByCriteriaEntity(CapRole.class, criteria);
	}

	private boolean contain(CapRole[] authRoles, CapRole capRole) {
		for (CapRole role : authRoles) {
			if (role.getRoleId().equals(capRole.getRoleId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取所有的Role 并根据partyId判断是否有改role的权限
	 * 
	 * @param criteriaType
	 * @param partyId
	 * @param partyTypeId
	 * @param isAuth
	 * @return
	 */
	public CapRole[] queryRoleListByCriteriaWithAuthState(CriteriaType criteriaType, String partyId, String partyTypeId, String isAuth) {
		CapRole[] roles = this.queryRoleListByCriteria(criteriaType);
		CapRole[] authRoles = this.getCapRoleListByAuthParty(partyTypeId, partyId, TenantManager.getCurrentTenantID());
		List<CapRole> returnList = new ArrayList<CapRole>();
		if (roles != null) {
			for (CapRole role : roles) {
				// 如果states中包含1，则有权限
				if ("1".equals(isAuth)) {
					if (contain(authRoles, role)) {
						role.setString(HAS_AUTH, isAuth);
						returnList.add(role);
					}
				} else if ("0".equals(isAuth)) {
					if (!contain(authRoles, role)) {
						role.setString(HAS_AUTH, isAuth);
						returnList.add(role);
					}
				} else {
					if (contain(authRoles, role)) {
						role.setString(HAS_AUTH, "1");
					} else {
						role.setString(HAS_AUTH, "0");
					}
					returnList.add(role);
				}
			}
		}
		return returnList.toArray(new CapRole[returnList.size()]);
	}

	/**
	 * 批量删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public String[] batchDeleteRole(String roleId) {
		List<String> list = new ArrayList<String>();
		if (roleId.contains(",")) {
			String[] roleIds = roleId.split(",");
			for (int i = 0; i < roleIds.length; i++) {
				int result = deleteRole(roleIds[i]);
				if (result != 1) {
					list.add(roleIds[i]);
				}
			}
		} else {
			int result = deleteRole(roleId);
			if (result != 1) {
				list.add(roleId);
			}
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 
	 * @param criteriaType 查询条件
	 * @param pageCond 分页条件
	 * @return 实体数组
	 */
	public CapRole[] queryCapRoles(CriteriaType criteriaType, PageCond pageCond) {
		IDASCriteria dasCriteria = getDASTemplate().criteriaTypeToDASCriteria(criteriaType);
		return getDASTemplate().queryEntitiesByCriteriaEntityWithPage(CapRole.class, dasCriteria, pageCond);
	}

	/**
	 * 
	 * @param capRoles 实体数组,可以批量删除
	 */
	public void deleteCapRole(CapRole[] capRoles) {
		for (DataObject capRole : capRoles) {
			getDASTemplate().deleteEntityCascade(capRole);
		}
	}

	/**
	 * 根据租户ID和角色ID获取机构列表
	 * @param roleId
	 * @param tenantID
	 * @return
	 */
	public QueryOrgByRole[] queryOrgsByRoleId(String roleId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryOrgByRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("roleId", roleId));
		criteria.add(ExpressionHelper.eq("partyType", IConstants.ORG_PARTY_TYPE_ID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryOrgByRole.class, criteria);
	}

	/**
	 * 根据租户ID和角色ID获取岗位列表
	 * @param roleId
	 * @param tenantID
	 * @return
	 */
	public QueryPositionByRole[] queryPositionsByRoleId(String roleId, String tenantID) {
		IDASCriteria criteria = DASManager.createCriteria(QueryPositionByRole.QNAME);
		criteria.add(ExpressionHelper.eq(IConstants.TENANT_PROPERTY, tenantID));
		criteria.add(ExpressionHelper.eq("roleId", roleId));
		criteria.add(ExpressionHelper.eq("partyType", IConstants.POSITION_PARTY_TYPE_ID));
		return getDASTemplate().queryEntitiesByCriteriaEntity(QueryPositionByRole.class, criteria);
	}
}