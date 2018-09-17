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
package org.gocom.components.coframe.auth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.gocom.components.coframe.auth.menu.DefaultMenuAuthService;
import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapResauth;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.rights.partyauth.DefaultPartyAuthManager;
import org.gocom.components.coframe.rights.resauth.DefaultResAuthManager;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.common.connection.ConnectionHelper;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IUserObject;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.spring.BeanFactory;
import com.eos.system.logging.Logger;
import com.eos.system.utility.StringUtil;
import com.primeton.cap.AppUserManager;
import com.primeton.cap.TenantManager;
import com.primeton.cap.auth.AuthResource;
import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.MenuTree;
import com.primeton.cap.auth.manager.AuthRuntimeManager;
import com.primeton.cap.party.Party;

/**
 * 
 * 默认授权管理实现，用于资源授权、party授权
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class DefaultAuthManagerService implements IAuthManagerService {
	private Logger log = TraceLoggerFactory
			.getLogger(DefaultAuthManagerService.class);

	private DefaultResAuthManager resAuthBean = null;

	private DefaultPartyAuthManager partyAuthBean = null;

	public DefaultAuthManagerService() {
		BeanFactory beanFactory = BeanFactory.newInstance();
		this.resAuthBean = beanFactory
				.getBean(DefaultResAuthManager.SPRING_BEAN_NAME);
		this.partyAuthBean = beanFactory
				.getBean(DefaultPartyAuthManager.SPRING_BEAN_NAME);

	}

	public boolean addOrUpdateAuthRes(Party party, AuthResource authRes) {
		// 如果缓存中不存在，说明是要新增，否则是更新
		String resId = authRes.getResourceID();
		String resType = authRes.getResourceType();
		try {
			String state = AuthRuntimeManager.getInstance()
					.getAuthResourceState(party, resId, resType);
			if (StringUtil.isEmpty(state)) {// 新增授权信息
				CapResauth capResauth = CapResauth.FACTORY.create();
				capResauth.setPartyId(party.getId());
				capResauth.setPartyType(party.getPartyTypeID());
				capResauth.setResId(resId);
				capResauth.setResType(resType);
				capResauth.setResState(authRes.getState());
				capResauth.setCreatetime(new Date());
				capResauth.setCreateuser(AppUserManager.getCurrentUserId());
				capResauth.setTenantId(TenantManager.getCurrentTenantID());
				this.resAuthBean.insertCapResauth(capResauth);
			} else {
				CapResauth capResauth = this.resAuthBean
						.getCapResauthByResIdAndType(party, resId, resType);
				String newState = authRes.getState();
				if (!newState.equals(capResauth.getResState())) {
					capResauth.setResState(newState);
					this.resAuthBean.updateCapResauth(capResauth);
				}
			}
			return true;
		} catch (Throwable t) {
			log.error("Add or update resource auth [resId=" + resId
					+ ", resType=" + resType + "] failed.", t);
			return false;
		}
	}

	public boolean addOrUpdateAuthResBatch(Party party,
			List<AuthResource> authResList) {
		try {
			List<CapResauth> toInsert = new ArrayList<CapResauth>();
			List<CapResauth> toUpdate = new ArrayList<CapResauth>();
			Map<String, Map<String, CapResauth>> tmpAuthTypeMap = new HashMap<String, Map<String, CapResauth>>();
			for (AuthResource authResource : authResList) {
				String resId = authResource.getResourceID();
				String resType = authResource.getResourceType();
				String state = AuthRuntimeManager.getInstance()
						.getAuthResourceState(party, resId, resType);
				if (StringUtil.isEmpty(state)) {
					CapResauth capResauth = CapResauth.FACTORY.create();
					capResauth.setPartyId(party.getId());
					capResauth.setPartyType(party.getPartyTypeID());
					capResauth.setResId(resId);
					capResauth.setResType(resType);
					capResauth.setResState(authResource.getState());
					capResauth.setCreatetime(new Date());
					capResauth.setCreateuser(AppUserManager.getCurrentUserId());
					capResauth.setTenantId(TenantManager.getCurrentTenantID());
					toInsert.add(capResauth);
				} else {
					// 如果需要更新的数据量很大，这里单个去查询可能会慢，一般来说批量更新的资源类型是相同的
					// 如果某种类型的资源授权信息很多，一次性查询出来处理也会比较慢，所以这里可能要根据实际资源类型分开处理
					Map<String, CapResauth> tmpAuthIdMap = tmpAuthTypeMap
							.get(resType);
					if (tmpAuthIdMap == null) {
						CapResauth[] resauths = this.resAuthBean
								.getCapResauthListByResType(party, resType);
						tmpAuthIdMap = new HashMap<String, CapResauth>();
						for (CapResauth tmpResauth : resauths) {
							tmpAuthIdMap.put(tmpResauth.getResId(), tmpResauth);
						}
						tmpAuthTypeMap.put(resType, tmpAuthIdMap);
					}
					CapResauth capResauth = tmpAuthIdMap.get(resId);
					capResauth.setResState(authResource.getState());
					toUpdate.add(capResauth);
				}
			}

			this.resAuthBean.save(toInsert.toArray(new CapResauth[toInsert
					.size()]), toUpdate
					.toArray(new CapResauth[toUpdate.size()]), null);

			return true;
		} catch (Throwable t) {
			log.error("Add or update resources auth failed.", t);
			return false;
		}
	}

	public boolean addOrUpdatePartyAuth(Party roleParty, Party party) {
		try {
			CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
			capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
			capPartyauth.setPartyId(party.getId());
			capPartyauth.setPartyType(party.getPartyTypeID());
			capPartyauth.setCreateuser(AppUserManager.getCurrentUserId());
			capPartyauth.setCreatetime(new Date());
			capPartyauth.setTenantId(TenantManager.getCurrentTenantID());
			CapRole capRole = CapRole.FACTORY.create();
			capRole.setRoleId(roleParty.getId());
			capRole.setRoleCode(roleParty.getCode());
			capRole.setRoleName(roleParty.getName());
			capPartyauth.setCapRole(capRole);

			this.partyAuthBean.savePartyAuth(capPartyauth);
		} catch (Throwable t) {
			log.error("insert partyAuth [rolePartyId=" + roleParty.getId()
					+ ",partyId=" + party.getId() + "] failed.");
			return false;
		}
		return true;
	}

	public boolean addOrUpdatePartyAuthBatch(Party roleParty,
			List<Party> partyList) {
		if (partyList == null) {
			return false;
		}
		List<CapPartyauth> capPartyAuthList = new ArrayList<CapPartyauth>();

		CapRole capRole = CapRole.FACTORY.create();
		capRole.setRoleId(roleParty.getId());
		capRole.setRoleCode(roleParty.getCode());
		capRole.setRoleName(roleParty.getName());

		try {
			for (Party party : partyList) {
				CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
				capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
				capPartyauth.setPartyId(party.getId());
				capPartyauth.setPartyType(party.getPartyTypeID());
				capPartyauth.setCreateuser(AppUserManager.getCurrentUserId());
				capPartyauth.setCreatetime(new Date());
				capPartyauth.setTenantId(TenantManager.getCurrentTenantID());

				capPartyauth.setCapRole(capRole);
				capPartyAuthList.add(capPartyauth);
			}

			this.partyAuthBean.savePartyAuthBatch(capPartyAuthList
					.toArray(new CapPartyauth[capPartyAuthList.size()]));

		} catch (Throwable t) {
			log.error("insert partyAuth batch failed.");
			return false;
		}
		return true;
	}

	public boolean addOrUpdatePartyAuthBatch(List<Party> rolePartyList,
			Party party) {
		if (rolePartyList == null) {
			return false;
		}
		List<CapPartyauth> capPartyAuthList = new ArrayList<CapPartyauth>();
		try {
			for (Party roleParty : rolePartyList) {
				CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
				capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
				capPartyauth.setPartyId(party.getId());
				capPartyauth.setPartyType(party.getPartyTypeID());
				capPartyauth.setCreateuser(AppUserManager.getCurrentUserId());
				capPartyauth.setCreatetime(new Date());
				capPartyauth.setTenantId(TenantManager.getCurrentTenantID());
				CapRole capRole = CapRole.FACTORY.create();
				capRole.setRoleId(roleParty.getId());
				capRole.setRoleCode(roleParty.getCode());
				capRole.setRoleName(roleParty.getName());
				capPartyauth.setCapRole(capRole);
				capPartyAuthList.add(capPartyauth);
			}

			this.partyAuthBean.savePartyAuthBatch(capPartyAuthList
					.toArray(new CapPartyauth[capPartyAuthList.size()]));

		} catch (Throwable t) {
			log.error("insert partyAuth batch failed.");
			return false;
		}
		return true;
	}

	public boolean deletePartyAuth(String partyId, String partyType) {
		String tenantId = TenantManager.getCurrentTenantID();
		try {
			this.partyAuthBean.deletePartyAuth(partyId, partyType, tenantId);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public boolean delAuthRes(Party party, AuthResource authRes, int mode) {
		String resId = authRes.getResourceID();
		String resType = authRes.getResourceType();
		try {
			if (mode == IAuthManagerService.DEL_MODE_SINGLE) {// 删除一条记录
				CapResauth capResauth = CapResauth.FACTORY.create();
				capResauth.setPartyId(party.getId());
				capResauth.setPartyType(party.getPartyTypeID());
				capResauth.setResId(resId);
				capResauth.setResType(resType);
				this.resAuthBean.deleteCapResauth(capResauth);
			} else {
				List<AuthResource> authResList = AuthRuntimeManager
						.getInstance().getAuthResListWithChildrenByRole(party,
								resId, resType);
				// 这边目前和批量新增或更新一样，性能上可以有提升空间
				List<CapResauth> toDel = new ArrayList<CapResauth>();
				Map<String, Map<String, CapResauth>> tmpAuthTypeMap = new HashMap<String, Map<String, CapResauth>>();
				for (AuthResource authResource : authResList) {
					String childResId = authResource.getResourceID();
					String childResType = authResource.getResourceType();
					Map<String, CapResauth> tmpAuthIdMap = tmpAuthTypeMap
							.get(childResType);
					if (tmpAuthIdMap == null) {
						CapResauth[] resauths = this.resAuthBean
								.getCapResauthListByResType(party, childResType);
						tmpAuthIdMap = new HashMap<String, CapResauth>();
						for (CapResauth tmpResauth : resauths) {
							tmpAuthIdMap.put(tmpResauth.getResId(), tmpResauth);
						}
						tmpAuthTypeMap.put(childResType, tmpAuthIdMap);
					}
					CapResauth capResauth = tmpAuthIdMap.get(childResId);
					if (capResauth != null) {
						toDel.add(capResauth);
					}
				}
				this.resAuthBean.save(null, null, toDel
						.toArray(new CapResauth[toDel.size()]));
			}

			return true;
		} catch (Throwable t) {
			log.error("Delete resource auth [resId=" + resId + ", resType="
					+ resType + ", mode=" + mode + "] failed.", t);
			return false;
		}
	}

	public boolean delAuthResBatch(Party party, List<AuthResource> authResList,
			int mode) {
		try {
			Map<String, Map<String, CapResauth>> tmpAuthTypeMap = new HashMap<String, Map<String, CapResauth>>();
			List<CapResauth> toDel = new ArrayList<CapResauth>();
			if (mode == IAuthManagerService.DEL_MODE_SINGLE) {
				for (AuthResource authRes : authResList) {
					String childResId = authRes.getResourceID();
					String childResType = authRes.getResourceType();
					Map<String, CapResauth> tmpAuthIdMap = tmpAuthTypeMap
							.get(childResType);
					if (tmpAuthIdMap == null) {
						CapResauth[] resauths = this.resAuthBean
								.getCapResauthListByResType(party, childResType);
						tmpAuthIdMap = new HashMap<String, CapResauth>();
						for (CapResauth tmpResauth : resauths) {
							tmpAuthIdMap.put(tmpResauth.getResId(), tmpResauth);
						}
						tmpAuthTypeMap.put(childResType, tmpAuthIdMap);
					}
					CapResauth capResauth = tmpAuthIdMap.get(childResId);
					if (capResauth != null) {
						toDel.add(capResauth);
					}
				}
			} else {
				for (AuthResource authRes : authResList) {
					String resId = authRes.getResourceID();
					String resType = authRes.getResourceType();
					List<AuthResource> authResListWithChildren = AuthRuntimeManager
							.getInstance().getAuthResListWithChildrenByRole(
									party, resId, resType);
					for (AuthResource authResource : authResListWithChildren) {
						String childResId = authResource.getResourceID();
						String childResType = authResource.getResourceType();
						Map<String, CapResauth> tmpAuthIdMap = tmpAuthTypeMap
								.get(childResType);
						if (tmpAuthIdMap == null) {
							CapResauth[] resauths = this.resAuthBean
									.getCapResauthListByResType(party,
											childResType);
							tmpAuthIdMap = new HashMap<String, CapResauth>();
							for (CapResauth tmpResauth : resauths) {
								tmpAuthIdMap.put(tmpResauth.getResId(),
										tmpResauth);
							}
							tmpAuthTypeMap.put(childResType, tmpAuthIdMap);
						}
						CapResauth capResauth = tmpAuthIdMap.get(childResId);
						if (capResauth != null) {
							toDel.add(capResauth);
						}
					}
				}
			}
			this.resAuthBean.save(null, null, toDel
					.toArray(new CapResauth[toDel.size()]));
			return true;
		} catch (Throwable t) {
			log.error("Delete resources auth failed.", t);
			return false;
		}
	}

	public boolean delPartyAuth(Party roleParty, Party party, int delMode) {
		try {
			if (delMode == IAuthManagerService.DEL_MODE_SINGLE) {
				CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
				capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
				capPartyauth.setPartyId(party.getId());
				capPartyauth.setPartyType(party.getPartyTypeID());
				CapRole capRole = CapRole.FACTORY.create();
				capRole.setRoleId(roleParty.getId());
				capPartyauth.setCapRole(capRole);

				this.partyAuthBean.deletePartyAuth(capPartyauth);
			}
		} catch (Throwable t) {
			log.error("delete partyAuth [rolePartyId=" + roleParty.getId()
					+ ",partyId=" + party.getId() + "] failed.");
			return false;
		}
		return true;
	}

	public boolean delPartyAuthBatch(Party roleParty, List<Party> partyList,
			int delMode) {
		if (partyList == null) {
			return false;
		}
		List<CapPartyauth> capPartyAuthList = new ArrayList<CapPartyauth>();

		CapRole capRole = CapRole.FACTORY.create();
		capRole.setRoleId(roleParty.getId());
		capRole.setRoleCode(roleParty.getCode());
		capRole.setRoleName(roleParty.getName());

		try {
			if (delMode == IAuthManagerService.DEL_MODE_SINGLE) {
				for (Party party : partyList) {
					CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
					capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
					capPartyauth.setPartyId(party.getId());
					capPartyauth.setPartyType(party.getPartyTypeID());

					capPartyauth.setCapRole(capRole);
					capPartyAuthList.add(capPartyauth);
				}

				this.partyAuthBean.deletePartyAuthBatch(capPartyAuthList
						.toArray(new CapPartyauth[capPartyAuthList.size()]));
			}
		} catch (Throwable t) {
			log.error("delete partyAuth batch failed.");
			return false;
		}
		return true;
	}

	public boolean delPartyAuthBatch(List<Party> rolePartyList, Party party,
			int delMode) {
		if (rolePartyList == null) {
			return false;
		}
		List<CapPartyauth> capPartyAuthList = new ArrayList<CapPartyauth>();
		try {
			if (delMode == IAuthManagerService.DEL_MODE_SINGLE) {
				for (Party roleParty : rolePartyList) {
					CapPartyauth capPartyauth = CapPartyauth.FACTORY.create();
					capPartyauth.setRoleType(IConstants.ROLE_PARTY_TYPE_ID);
					capPartyauth.setPartyId(party.getId());
					capPartyauth.setPartyType(party.getPartyTypeID());
					CapRole capRole = CapRole.FACTORY.create();
					capRole.setRoleId(roleParty.getId());
					capPartyauth.setCapRole(capRole);
					capPartyAuthList.add(capPartyauth);
				}

				this.partyAuthBean.deletePartyAuthBatch(capPartyAuthList
						.toArray(new CapPartyauth[capPartyAuthList.size()]));
			}
		} catch (Throwable t) {
			log.error("delete partyAuth batch failed.");
			return false;
		}
		return true;
	}

	public List<AuthResource> getAuthResListByRole(Party party) {
		// 返回一个测试列表
		CapResauth[] resauths = this.resAuthBean
				.getCapResauthListByParty(party);
		List<AuthResource> returnList = new ArrayList<AuthResource>();
		if (resauths != null) {
			for (CapResauth resauth : resauths) {
				AuthResource authResource = new AuthResource(
						resauth.getResId(), resauth.getResType(), resauth
								.getResState());
				returnList.add(authResource);
			}
		}
		return returnList;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean delAuthResByRole(Party party) {
		try {
			this.resAuthBean.deleteCapResauthByParty(party);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public boolean delPartyAuthByRole(Party roleParty) {
		try {
			this.partyAuthBean.delPartyAuthByRole(roleParty);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}

	public MenuTree getUserMenuTree() {
		IUserObject userObject = DataContextManager.current()
				.getMUODataContext().getUserObject();
		String roleIds = (String) userObject
				.get(com.primeton.cap.auth.IConstants.ROLE_LIST);
//		if(StringUtils.isEmpty(roleIds)){
//			roleIds = this.getRolesByUserId(userObject.getUserId());
//		}
		List<Party> roles = new ArrayList<Party>();
		if (roleIds != null) {
			String[] roleIdArr = roleIds
					.split(com.primeton.cap.auth.IConstants.SPLIET);
			for (String roleId : roleIdArr) {
				if (!StringUtils.isEmpty(roleId)) {
					roles.add(new Party(IConstants.ROLE_PARTY_TYPE_ID, roleId,
							roleId, roleId));
				}
			}
		}
		DefaultMenuAuthService menuAuthService = new DefaultMenuAuthService(
				roles);
		return menuAuthService.getAllPartyAuthMenuTree();
	}

	public MenuTree getUserMenuTree(IMenuTreeFilter arg0) {
		return null;
	}

//	@Override
	public MenuTree getUserMenuTreeByAppCode(String arg0) {
		// TODO 自动生成的方法存根
		return null;
	}

	private String getRolesByUserId(String userid){
		Connection conn = null;
		
		Statement stmt =  null;
		
		ResultSet rs = null;
		try {
			conn = ConnectionHelper.getCurrentContributionConnection("default");
			stmt = conn.createStatement();
			String sql = "select r.role_id from cap_role r inner join cap_partyauth p on r.role_id = p.role_id  where p.party_id = '"+userid+"' ";
		
			rs = stmt.executeQuery(sql);
			 List<String> roleList = new ArrayList<String>();
			while(rs.next()){
				roleList.add(rs.getString(1));
			}
			if(roleList.size() < 1){
				return null;
			}
			String roles = roleList.toString();
			roles = roles.substring(1, roles.length() - 1);
			return roles;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			try {
			if(rs != null){	
			  rs.close();
			}
			
			if(stmt != null){
				stmt.close();
			}
			
			if(conn != null){
				conn.close();
			}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
		return null;
	}
}