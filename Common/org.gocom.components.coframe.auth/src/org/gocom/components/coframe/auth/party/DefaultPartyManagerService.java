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
package org.gocom.components.coframe.auth.party;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.auth.party.config.PartyModel;
import org.gocom.components.coframe.auth.party.manager.DefaultRoleManager;
import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;
import org.gocom.components.coframe.auth.service.PartyAuthModel;
import org.gocom.components.coframe.rights.dataset.CapRole;
import org.gocom.components.coframe.rights.dataset.CapUser;
import org.gocom.components.coframe.rights.user.DefaultUserManager;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.spring.BeanFactory;
import com.eos.system.logging.Logger;
import com.primeton.cap.party.AbstractPartyManagerService;
import com.primeton.cap.party.IPartyManagerService;
import com.primeton.cap.party.IPartyTypeDataService;
import com.primeton.cap.party.IPartyTypeRefDataService;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.PartyType;
import com.primeton.cap.party.PartyTypeRef;
import com.primeton.cap.party.impl.PartyTypeTreeModel;
import com.primeton.cap.party.impl.PartyTypeTreeModel.PartyTypeTreeNode;
import com.primeton.cap.party.manager.PartyTypeManager;
import com.primeton.cap.party.manager.PartyTypeRefManager;
import com.primeton.cap.party.util.PartyUtil;

/**
 * Party服务管理接口默认实现，使用默认配置文件及注册器进行相关数据计算，目前只是本地实现
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class DefaultPartyManagerService extends AbstractPartyManagerService
		implements IPartyManagerService {

	private Logger log = TraceLoggerFactory
			.getLogger(DefaultPartyManagerService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.primeton.cap.IOrderable#getOrder()
	 */
	public int getOrder() {
		return 10000;
	}

	public List<Party> doGetAllPartyList(String partyTypeID, String tenantID) {
		IPartyTypeDataService dataservice = PartyTypeManager.getInstance()
				.getPartyTypeDataService(partyTypeID);
		if (dataservice == null) {
			log.warn("Can not find the partyTypeDataservice for partyTypeID = "
					+ partyTypeID);
			return Collections.emptyList();
		}
		return dataservice.getAllPartyList(tenantID);
	}

	@Override
	public List<Party> doGetRootPartyList(String partyTypeID, String tenantID) {
		IPartyTypeDataService dataservice = PartyTypeManager.getInstance()
				.getPartyTypeDataService(partyTypeID);
		if (dataservice == null) {
			log.warn("Can not find the partyTypeDataservice for partyTypeID = "
					+ partyTypeID);
			return Collections.emptyList();
		}
		return dataservice.getRootPartyList(tenantID);
	}

	public List<PartyType> doGetAllPartyTypeList(String tenantID) {
		Map<String, PartyType> map = PartyTypeManager.getInstance()
				.getPartyTypeMap();
		List<PartyType> partyTypeList = new ArrayList<PartyType>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			partyTypeList.add(map.get(key));
		}
		Collections.sort(partyTypeList, PartyUtil.getPartyTypeComparator());
		return partyTypeList;
	}

	public List<PartyTypeRef> doGetAllPartyTypeRefList(String tenantID) {
		Map<String, PartyTypeRef> map = PartyTypeRefManager.getInstance()
				.getPartyTypeRefMap();
		List<PartyTypeRef> partyTypeRefList = new ArrayList<PartyTypeRef>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			partyTypeRefList.add(map.get(key));
		}
		return partyTypeRefList;
	}

	public Map<String, List<Party>> doGetDirectAssociateChildPartyList(
			String parentPartyID, String parentPartyTypeID, String tenantID) {
		Map<String, PartyTypeRef> map = PartyTypeRefManager.getInstance()
				.getPartyTypeRefMap();
		Iterator<String> it = map.keySet().iterator();
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		while (it.hasNext()) {
			String key = it.next();
			PartyTypeRef partyTypeRef = map.get(key);
			if (parentPartyTypeID.equals(partyTypeRef.getParentPartyType()
					.getTypeID())) {
				IPartyTypeRefDataService dataService = PartyTypeRefManager
						.getInstance().getPartyTypeRefDataService(
								partyTypeRef.getRefID());
				returnMap.put(partyTypeRef.getChildPartyType().getTypeID(),
						dataService.getChildrenPartyList(parentPartyID,
								tenantID));
			}
		}
		return returnMap;
	}

	public Map<String, List<Party>> doGetDirectAssociateChildPartyList(
			String parentPartyID, String parentPartyTypeID,
			String[] childPartyTypes, String tenantID) {
		Map<String, PartyTypeRef> map = PartyTypeRefManager.getInstance()
				.getPartyTypeRefMap();
		Iterator<String> it = map.keySet().iterator();
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		while (it.hasNext()) {
			String key = it.next();
			PartyTypeRef partyTypeRef = map.get(key);
			if (parentPartyTypeID.equals(partyTypeRef.getParentPartyType()
					.getTypeID())) {
				String childPartyTypeID = partyTypeRef.getChildPartyType()
						.getTypeID();
				if (containObj(childPartyTypes, childPartyTypeID)) {
					IPartyTypeRefDataService dataService = PartyTypeRefManager
							.getInstance().getPartyTypeRefDataService(
									partyTypeRef.getRefID());
					returnMap.put(childPartyTypeID, dataService
							.getChildrenPartyList(parentPartyID, tenantID));
				}
			}
		}
		return returnMap;
	}

	public Map<String, List<Party>> doGetDirectAssociateChildPartyList(
			String parentPartyID, String[] partyTypeRefs, String tenantID) {
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		for (String partyTypeRefID : partyTypeRefs) {
			IPartyTypeRefDataService dataService = PartyTypeRefManager
					.getInstance().getPartyTypeRefDataService(partyTypeRefID);
			PartyType childPartyType = PartyTypeRefManager.getInstance()
					.getPartyTypeRef(partyTypeRefID).getChildPartyType();
			returnMap.put(childPartyType.getTypeID(), dataService
					.getChildrenPartyList(parentPartyID, tenantID));
		}

		return returnMap;
	}

	public Map<String, List<Party>> doGetDirectAssociateParentPartyList(
			String childPartyID, String childPartyTypeID, String tenantID) {
		Map<String, PartyTypeRef> map = PartyTypeRefManager.getInstance()
				.getPartyTypeRefMap();
		Iterator<String> it = map.keySet().iterator();
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		while (it.hasNext()) {
			String key = it.next();
			PartyTypeRef partyTypeRef = map.get(key);
			if (childPartyTypeID.equals(partyTypeRef.getChildPartyType()
					.getTypeID())) {
				IPartyTypeRefDataService dataService = PartyTypeRefManager
						.getInstance().getPartyTypeRefDataService(
								partyTypeRef.getRefID());
				returnMap.put(partyTypeRef.getParentPartyType().getTypeID(),
						dataService.getParentPartyList(childPartyID, tenantID));
			}
		}
		return returnMap;
	}

	public Map<String, List<Party>> doGetDirectAssociateParentPartyList(
			String childPartyID, String childPartyTypeID,
			String[] parentPartyTypes, String tenantID) {
		Map<String, PartyTypeRef> map = PartyTypeRefManager.getInstance()
				.getPartyTypeRefMap();
		Iterator<String> it = map.keySet().iterator();
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		while (it.hasNext()) {
			String key = it.next();
			PartyTypeRef partyTypeRef = map.get(key);
			if (childPartyTypeID.equals(partyTypeRef.getChildPartyType()
					.getTypeID())) {
				String parentPartyTypeID = partyTypeRef.getParentPartyType()
						.getTypeID();
				if (containObj(parentPartyTypes, parentPartyTypeID)) {
					IPartyTypeRefDataService dataService = PartyTypeRefManager
							.getInstance().getPartyTypeRefDataService(
									partyTypeRef.getRefID());
					returnMap.put(parentPartyTypeID, dataService
							.getParentPartyList(childPartyID, tenantID));
				}
			}
		}
		return returnMap;
	}

	public Map<String, List<Party>> doGetDirectAssociateParentPartyList(
			String childPartyID, String[] partyTypeRefs, String tenantID) {
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();

		for (String partyTypeRefID : partyTypeRefs) {
			IPartyTypeRefDataService dataService = PartyTypeRefManager
					.getInstance().getPartyTypeRefDataService(partyTypeRefID);
			PartyType parentPartyType = PartyTypeRefManager.getInstance()
					.getPartyTypeRef(partyTypeRefID).getParentPartyType();
			returnMap.put(parentPartyType.getTypeID(), dataService
					.getParentPartyList(childPartyID, tenantID));
		}

		return returnMap;
	}

	public Party doGetPartyByPartyID(String partyID, String partyType,
			String tenantID) {
		IPartyTypeDataService dataservice = PartyTypeManager.getInstance()
				.getPartyTypeDataService(partyType);
		if (dataservice != null) {
			return dataservice.getPartyByPartyID(partyID, tenantID);
		}
		return null;
	}

	public PartyType doGetPartyTypeByTypeID(String partyTypeID, String tenantID) {
		return PartyTypeManager.getInstance().getPartyType(partyTypeID);
	}

	public PartyTypeRef doGetPartyTypeRefByRefID(String partyTypeRefID,
			String tenantID) {
		return PartyTypeRefManager.getInstance()
				.getPartyTypeRef(partyTypeRefID);
	}

	public PartyTypeTreeModel doGetPartyTypeTreeModel(String tenantID) {
		return PartyTypeRefManager.getInstance().getPartyTypeTreeModel();
	}

	@Override
	public List<PartyType> doGetRootPartyTypeList(String tenantID) {
		List<PartyType> returnList = new ArrayList<PartyType>();
		PartyTypeTreeModel partyTypeTreeModel = this.getPartyTypeTreeModel();
		List<PartyTypeTreeNode> rootPartyTreeNodeList = partyTypeTreeModel
				.getRootPartyTreeNode();
		for (PartyTypeTreeNode partyTypeTreeNode : rootPartyTreeNodeList) {
			returnList.add(PartyTypeManager.getInstance().getPartyType(
					partyTypeTreeNode.getPartyTypeID()));
		}
		return returnList;
	}

	@Override
	public PartyTypeTreeModel doGetLoginPartyTypeTreeModel(String tenantID) {
		return PartyTypeRefManager.getInstance().getLoginPartyTypeTreeModel();
	}

	@Override
	public Map<String, List<Party>> doGetLoginPartyCache(String partyID,
			String partyTypeID, String tenantID) {
		Map<String, List<Party>> returnMap = new HashMap<String, List<Party>>();
		PartyTypeTreeModel loginPartyTreeModel = this
				.getLoginPartyTypeTreeModel();
		// 从树模型中找到具体的partyType，然后向上查找到角色，每层都party相关数据都需要缓存
		List<PartyTypeTreeNode> rootPartyTreeNodeList = loginPartyTreeModel
				.getRootPartyTreeNode();
		// 只会有一个根
		PartyTypeTreeNode rootPartyTypeTreeNode = rootPartyTreeNodeList.get(0);
		// 用于存放所有的符合条件的树节点
		List<PartyTypeTreeNode> partyTypeTreeNodeList = new ArrayList<PartyTypeTreeNode>();
		if (rootPartyTypeTreeNode != null) {
			iteratorTree(partyTypeTreeNodeList, rootPartyTypeTreeNode,
					partyTypeID);
		}

		for (PartyTypeTreeNode partyTypeTreeNode : partyTypeTreeNodeList) {
			iteratorAddPartyToMap(returnMap, partyTypeTreeNode, this
					.getPartyByPartyID(partyID, partyTypeID));
		}

		return returnMap;
	}

	private void iteratorAddPartyToMap(Map<String, List<Party>> returnMap,
			PartyTypeTreeNode treeNode, Party party) {
		String partyTypeID = treeNode.getPartyTypeID();

		List<Party> partyList = returnMap.get(partyTypeID);
		if (partyList == null) {
			partyList = new ArrayList<Party>();
			returnMap.put(partyTypeID, partyList);
		}
		if (!partyList.contains(party)) {
			partyList.add(party);
		}

		if (treeNode.isSelfRelation()) {
			String partyTypeRefID = treeNode.getSelfPartyTypeRefID();
			Map<String, List<Party>> parentPartyMap = this
					.getDirectAssociateParentPartyList(party.getId(),
							new String[] { partyTypeRefID });
			List<Party> parentPartyList = parentPartyMap.get(partyTypeID);
			for (Party parentParty : parentPartyList) {
				iteratorAddPartyToMap(returnMap, treeNode, parentParty);
			}
		}
		PartyTypeTreeNode parentPartyTypeTreeNode = treeNode
				.getParentTypeTreeNode();
		if (parentPartyTypeTreeNode != null) {
			String partyTypeRefID = treeNode.getPartyTypeRefID();
			Map<String, List<Party>> parentPartyMap = this
					.getDirectAssociateParentPartyList(party.getId(),
							new String[] { partyTypeRefID });
			List<Party> parentPartyList = parentPartyMap.get(this
					.getPartyTypeRefByRefID(partyTypeRefID)
					.getParentPartyType().getTypeID());
			for (Party parentParty : parentPartyList) {
				iteratorAddPartyToMap(returnMap, parentPartyTypeTreeNode,
						parentParty);
			}
		}

	}

	private void iteratorTree(List<PartyTypeTreeNode> partyTypeList,
			PartyTypeTreeNode partyTypeTreeNode, String partyTypeID) {
		if (partyTypeID.equals(partyTypeTreeNode.getPartyTypeID())) {
			partyTypeList.add(partyTypeTreeNode);
		} else {
			List<PartyTypeTreeNode> childrenPartyTypeTreeNodeList = partyTypeTreeNode
					.getChildrenTypeTreeNode();
			if (childrenPartyTypeTreeNodeList != null) {
				for (PartyTypeTreeNode childPartyTypeTreeNode : childrenPartyTypeTreeNodeList) {
					iteratorTree(partyTypeList, childPartyTypeTreeNode,
							partyTypeID);
				}
			}
		}
	}

	private static boolean containObj(Object[] objs, Object obj) {
		for (Object tmp : objs) {
			if (tmp.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, List<Party>> doGetAllParentPartyList(String partyID,
			String partyTypeID, String tenantID) {
		PartyAuthModel authModel = AuthPartyRuntimeService.getPartyModel(
				partyID, partyTypeID);
		Map<String, List<Party>> partyTypeListMap = new HashMap<String, List<Party>>();

		if (authModel.getPartys() != null) {
			for (Party party : authModel.getPartys()) {
				List<Party> partyList = partyTypeListMap.get(party.getPartyTypeID());
				if(partyList==null){
					partyList = new ArrayList<Party>();
					partyTypeListMap.put(party.getPartyTypeID(), partyList);
				}
				partyList.add(party);
			}
		}
		if (authModel.getRoles() != null) {
			for (Party party : authModel.getRoles()) {
				List<Party> partyList = partyTypeListMap.get(party.getPartyTypeID());
				if(partyList==null){
					partyList = new ArrayList<Party>();
					partyTypeListMap.put(party.getPartyTypeID(), partyList);
				}
				partyList.add(party);
			}
		}
		return partyTypeListMap;
	}

	@Override
	public List<Party> doGetAssociatePartyList(String partyID,
			String partyTypeID, String associatePartyType, String tenantID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Party>> doGetScopePartyMap(String partyID,
			String partyTypeID, String tenantID) {
		// TODO 该处需要重构，用了很多实现中的方法
		return null;
	}

}