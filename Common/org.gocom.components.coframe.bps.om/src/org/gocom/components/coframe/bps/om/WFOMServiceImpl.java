/*******************************************************************************
 * $Header:
 * /cvsroot/PCAP700/develop/core/opensource/org.gocom.cap.auth/org.gocom.cap.auth.base/src/org/gocom/cap/auth/base/workflow/party/impl/WFOMServiceImpl.java,v
 * 1.1 2012/10/25 09:08:48 wuyh Exp $ $Revision: 1.4 $ $Date: 2012/10/25
 * 09:08:48 $
 * 
 * ==============================================================================
 * 
 * Copyright (c) 2001-2012 Primeton Technologies, Ltd. All rights reserved.
 * 
 * Created on 2012-6-18
 ******************************************************************************/

package org.gocom.components.coframe.bps.om;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gocom.components.coframe.auth.service.AuthPartyRuntimeService;
import org.gocom.components.coframe.auth.service.PartyAuthModel;
import org.gocom.components.coframe.tools.IConstants;

import com.eos.data.datacontext.DataContextManager;
import com.eos.workflow.omservice.IWFOMService;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.cap.TenantManager;
import com.primeton.cap.party.Party;
import com.primeton.cap.party.PartyType;
import com.primeton.cap.party.PartyTypeRef;
import com.primeton.cap.party.impl.PartyTypeTreeModel;
import com.primeton.cap.party.impl.PartyTypeTreeModel.PartyTypeTreeNode;
import com.primeton.cap.party.manager.PartyRuntimeManager;
import com.primeton.cap.party.manager.PartyTypeManager;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.ParticipantType;

/**
 * 这里面有一个特殊处理，就是当party时为emp时，其实partyId传过来的是用户，需要做一次转换
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class WFOMServiceImpl implements IWFOMService {

	public WFOMServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#findParticipantByID(java.lang.String,
	 *      java.lang.String)
	 */
	public WFParticipant findParticipantByID(String typeCode,
			String participantID) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);
		// if(typeCode.equals(IConstants.EMP_PARTY_TYPE_ID)){
		// participantID = getEmpIdByUserId(participantID);
		// }
		Party party = PartyRuntimeManager.getInstance().getPartyByPartyID(
				participantID, typeCode);
		if (party != null) {
			return adapt(party);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#findRootParticipants(java.lang.String)
	 */
	public List<WFParticipant> findRootParticipants(String typeCode) {
		// 工作流中的角色是支持嵌套的，而这次的默认组织机构实现是不支持的，所以此处会有一定的不同
		// 按照目前的配置，只有机构和角色会作为root节点显示
		typeCode = getPartyTypeByWFTypeCode(typeCode, null);
		List<PartyType> rootPartyTypeList = PartyRuntimeManager.getInstance()
				.getRootPartyTypeList();
		if (rootPartyTypeList != null) {
			for (PartyType partyType : rootPartyTypeList) {
				if (typeCode.equals(partyType.getTypeID())) {
					List<Party> partyList = PartyRuntimeManager.getInstance()
							.getRootPartyList(typeCode);
					List<WFParticipant> returnList = new ArrayList<WFParticipant>();
					for (Party party : partyList) {
						returnList.add(adapt(party));
					}
					return returnList;
				}
			}
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getAllChildParticipants(java.lang.String,
	 *      java.lang.String)
	 */
	public List<WFParticipant> getAllChildParticipants(String typeCode,
			String participantID) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);
		// 角色下只显示人员，机构下面会显示子机构或者人员，此方法不支持嵌套查找
		PartyTypeTreeModel partyTypeTreeModel = PartyRuntimeManager
				.getInstance().getPartyTypeTreeModel();

		// 首先根据typeCode定位到节点
		PartyTypeTreeNode treeNode = getPartyTypeTreeNode(partyTypeTreeModel
				.getRootPartyTreeNode(), typeCode);

		List<String> partyTypeRefList = new ArrayList<String>();
		// 获取此节点的子节点，也可能是自关联
		if (treeNode.isSelfRelation()) {
			partyTypeRefList.add(treeNode.getSelfPartyTypeRefID());
		}
		List<PartyTypeTreeNode> childTreeNodeList = treeNode
				.getChildrenTypeTreeNode();
		if (childTreeNodeList != null) {
			for (PartyTypeTreeNode childTreeNode : childTreeNodeList) {
				partyTypeRefList.add(childTreeNode.getPartyTypeRefID());
			}
		}

		// Map<String, List<Party>>returnMap =
		// PartyRuntimeManager.getInstance().getDirectAssociateChildPartyList(participantID,
		// partyTypeRefList.toArray(new String[partyTypeRefList.size()]));
		//		
		//		
		// Iterator<String>it = returnMap.keySet().iterator();
		// while(it.hasNext()){
		// String key = it.next();
		// tmpList.addAll(returnMap.get(key));
		// }

		List<Party> tmpList = new ArrayList<Party>();
		for (String ref : partyTypeRefList) {
			PartyTypeRef partyTypeRef = PartyRuntimeManager.getInstance()
					.getPartyTypeRefByRefID(ref);
			tmpList.addAll(PartyRuntimeManager.getInstance()
					.getDirectAssociateChildPartyList(participantID,
							new String[] { ref }).get(
							partyTypeRef.getChildPartyType().getTypeID()));
		}

		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		for (Party party : tmpList) {
			returnList.add(adapt(party));
		}

		return returnList;
	}

	private PartyTypeTreeNode getPartyTypeTreeNode(
			List<PartyTypeTreeNode> treeNodeList, String typeCode) {
		if (treeNodeList == null) {
			return null;
		}
		for (PartyTypeTreeNode treeNode : treeNodeList) {
			if (treeNode.getPartyTypeID().equals(typeCode)) {
				return treeNode;
			} else {
				PartyTypeTreeNode returnNode = getPartyTypeTreeNode(treeNode
						.getChildrenTypeTreeNode(), typeCode);
				if (returnNode != null) {
					return returnNode;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getAllParentParticipants(java.lang.String,
	 *      java.lang.String)
	 */
	public List<WFParticipant> getAllParentParticipants(String typeCode,
			String participantID) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);

		// 如果typeCode是人员，需要将particiapantID转换一次（因为此时的值是用户ID）
		// if(typeCode.equals(IConstants.EMP_PARTY_TYPE_ID)){
		// participantID = getEmpIdByUserId(participantID);
		// }

		// 根据人员获取机构，根据机构获取机构，根据人员获取角色，根据机构获取角色
		// 首先在机构树上查找对应于typeCode的节点，然后根据父子关系来找到所有的父
		PartyTypeTreeModel partyTypeTreeModel = PartyRuntimeManager
				.getInstance().getPartyTypeTreeModel();
		List<PartyTypeTreeNode> tmpReturnList = new ArrayList<PartyTypeTreeNode>();
		findAllPartyTypeTreeNode(tmpReturnList, typeCode, partyTypeTreeModel
				.getRootPartyTreeNode());
		List<Party> partyList = new ArrayList<Party>();

		List<String> tmpList = new ArrayList<String>();
		for (PartyTypeTreeNode treeNode : tmpReturnList) {
			// 找到父亲，并且注意过滤
			if (treeNode.isSelfRelation()) {
				String partyTypeID = treeNode.getPartyTypeID();
				if (tmpList.contains(partyTypeID)) {
					continue;
				}
				tmpList.add(partyTypeID);
				partyList
						.addAll(PartyRuntimeManager.getInstance()
								.getDirectAssociateParentPartyList(
										participantID,
										new String[] { treeNode
												.getSelfPartyTypeRefID() })
								.get(partyTypeID));
			} else {
				PartyTypeTreeNode parentPartyTypeTreeNode = treeNode
						.getParentTypeTreeNode();
				if (parentPartyTypeTreeNode != null) {
					String parentPartyTypeID = parentPartyTypeTreeNode
							.getPartyTypeID();
					if (tmpList.contains(parentPartyTypeID)) {
						continue;
					}
					tmpList.add(parentPartyTypeID);
					partyList
							.addAll(PartyRuntimeManager.getInstance()
									.getDirectAssociateParentPartyList(
											participantID,
											new String[] { treeNode
													.getPartyTypeRefID() })
									.get(parentPartyTypeID));
				}
			}
		}

		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		for (Party party : partyList) {
			returnList.add(adapt(party));
		}

		return returnList;
	}

	private void findAllPartyTypeTreeNode(List<PartyTypeTreeNode> returnList,
			String typeCode, List<PartyTypeTreeNode> treeNodeList) {
		if (treeNodeList != null) {
			for (PartyTypeTreeNode treeNode : treeNodeList) {
				if (treeNode.getPartyTypeID().equals(typeCode)) {
					returnList.add(treeNode);
					// 一般相同类型的节点是不会在其子中存在的，所以不需要再递归
				} else {
					findAllPartyTypeTreeNode(returnList, typeCode, treeNode
							.getChildrenTypeTreeNode());
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getChildParticipants(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List<WFParticipant> getChildParticipants(String typeCode,
			String participantID, String childType) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);
		childType = getPartyTypeByWFTypeCode(childType, null);
		List<Party> partyList = PartyRuntimeManager.getInstance()
				.getDirectAssociateChildPartyList(participantID, typeCode,
						new String[] { childType }).get(childType);
		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		for (Party party : partyList) {
			returnList.add(adapt(party));
		}

		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getJointChildParticipant(java.lang.String,
	 *      java.util.List)
	 */
	public List<WFParticipant> getJointChildParticipant(
			String jointParticipantType, List<String> participantIDList) {
		jointParticipantType = getPartyTypeByWFTypeCode(jointParticipantType, null);

		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		List<Party> partyList = new ArrayList<Party>();

		// 这里结合工作流的调用参数
		if (IConstants.ORG_ROLE_PARTY_TYPE_ID.equals(jointParticipantType)) {
			// 对应于机构角色类型
			if (participantIDList != null && participantIDList.size() == 2) {
				String roleId = participantIDList.get(0);
				String orgId = participantIDList.get(1);
				// TODO 这里写死了类型，需要修改
				partyList.addAll(PartyRuntimeManager.getInstance()
						.getDirectAssociateChildPartyList(orgId + "," + roleId,
								IConstants.ORG_ROLE_PARTY_TYPE_ID,
								new String[] { IConstants.EMP_PARTY_TYPE_ID })
						.get(IConstants.EMP_PARTY_TYPE_ID));
			}
		}

		for (Party party : partyList) {
			returnList.add(adapt(party));
		}

		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getParentParticipants(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List<WFParticipant> getParentParticipants(String typeCode,
			String participantID, String parentType) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);
		// if(typeCode.equals(IConstants.EMP_PARTY_TYPE_ID)){
		// participantID = getEmpIdByUserId(participantID);
		// }

		parentType = getPartyTypeByWFTypeCode(parentType, null);
		List<Party> partyList = PartyRuntimeManager.getInstance()
				.getDirectAssociateParentPartyList(participantID, typeCode,
						new String[] { parentType }).get(parentType);
		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		for (Party party : partyList) {
			returnList.add(adapt(party));
		}

		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getParticipantScope(java.lang.String,
	 *      java.lang.String)
	 */
	public List<WFParticipant> getParticipantScope(String typeCode,
			String participantID) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, participantID);
		
		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		returnList = getWFParticipantList(typeCode, participantID);
		
		if("emp".equals(typeCode)) {
			// 员工查用户
			String userId = DataContextManager.current().getMUODataContext().getUserObject().getUserName();
			List<WFParticipant> userReturnList = new ArrayList<WFParticipant>();
			userReturnList = getWFParticipantList("user", userId);
			returnList.addAll(userReturnList);
		}
		
		return returnList;
	}
	
	private List<WFParticipant> getWFParticipantList(String typeCode,
			String participantID) {
		List<WFParticipant> returnList = new ArrayList<WFParticipant>();
		/*
		Party currParty = PartyRuntimeManager.getInstance().getPartyByPartyID(
				participantID, typeCode);
		if (currParty != null) {
			returnList.add(adapt(currParty));
		} else {
			Party party = new Party(typeCode, participantID, null, typeCode,
					TenantManager.getCurrentTenantID());
			returnList.add(adapt(party));
		}
		*/
		PartyAuthModel partyAuthModel = AuthPartyRuntimeService.getPartyModel(
				participantID, typeCode);
		if (partyAuthModel.getPartys() != null) {
			for (Party party : partyAuthModel.getPartys()) {
				returnList.add(adapt(party));
			}
		}
		if (partyAuthModel.getRoles() != null) {
			for (Party party : partyAuthModel.getRoles()) {
				returnList.add(adapt(party));
			}
		}

		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getParticipantType(java.lang.String)
	 */
	public ParticipantType getParticipantType(String typeCode) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, null);
		PartyType partyType = PartyRuntimeManager.getInstance()
				.getPartyTypeByTypeID(typeCode);
		return adapt(partyType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getParticipantTypes()
	 */
	public List<ParticipantType> getParticipantTypes() {
		Map<String, PartyType> partyTypeMap = PartyTypeManager.getInstance()
				.getPartyTypeMap();
		List<ParticipantType> returnList = new ArrayList<ParticipantType>();
		Iterator<String> it = partyTypeMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			PartyType partyType = partyTypeMap.get(key);
			if (partyType.isShowInTree()) {
				ParticipantType participantType = adapt(partyType);
				if (participantType != null) {
					returnList.add(participantType);
				}
			}
		}
		return returnList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eos.workflow.omservice.IWFOMService#getParticipants(java.lang.String,
	 *      com.primeton.workflow.api.PageCond)
	 */
	public List<WFParticipant> getParticipants(String typeCode, PageCond pc) {
		typeCode = getPartyTypeByWFTypeCode(typeCode, null);
		List<Party> partyList = PartyRuntimeManager.getInstance()
				.getAllPartyList(typeCode);
		List<WFParticipant> returnList = new ArrayList<WFParticipant>();

		if (pc != null) {
			int begin = pc.getBeginIndex();
			int length = pc.getLength();
			int count = 0, rsCount = 0;

			if (pc.getIsCount()) {
				count = partyList.size();
			}
			pc.setCount(count);
			rsCount = begin + length;

			if (length <= partyList.size() && begin < partyList.size()) {
				for (int i = begin; i < rsCount; i++) {
					returnList.add(adapt(partyList.get(i)));
				}
			} else {
				for (Party party : partyList) {
					returnList.add(adapt(party));
				}
			}
		}
		return returnList;
	}

	/**
	 * 将party适配成WfParticipant
	 * 
	 * @param party
	 * @return
	 */
	private WFParticipant adapt(Party party) {
		return new WFParticipant(party.getId(), party.getName(), party
				.getExtAttribute("email"), party.getPartyTypeID());
	}

	private String getPartyTypeByWFTypeCode(String typeCode, String participantID) {
		if (participantID==null || participantID.trim().length()==0) {
			return typeCode;
		}
		if ("emp".equals(typeCode)) {
			try {
				Long.parseLong(participantID);
			} catch (Throwable e) {
				/* 如果typeCode=="emp" 但是 participantID不是数字，就把typeCode改成"user" */
				typeCode = "user";
			}
		}
		if("person".equals(typeCode)) {
			typeCode = "emp";
		}
		return typeCode;
	}

	private ParticipantType adapt(PartyType partyType) {
		return PartyTypeAdapterManager.getPartyType(partyType.getTypeID());
	}
}