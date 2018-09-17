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
package org.gocom.components.coframe.auth.party.config;

import java.io.File;
import java.io.Serializable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eos.system.utility.XmlUtil;

/**
 * 解析Party的配置文件，文件内容参照: 
 * 
 * <PartyModel>
	<PartyTypeList>
		<PartyType typeID="org" name="机构" partyTypeDataService="" description="" isRole="false" priority="2" icon16path="/aaa/test.png" icon32path="/aaa/test.png" showInTree="true" showAtRoot="true" isLeaf="false"/>
		<PartyType typeID="role" name="角色" partyTypeDataService="" description="" isRole="true" priority="3" icon16path="/aaa/test.png" icon32path="/aaa/test.png" showInTree="true" showAtRoot="true" isLeaf="false"/>
  		<PartyType typeID="workgroup" name="工作组" partyTypeDataService="" description="" isRole="false" priority="1" icon16path="/aaa/test.png" icon32path="/aaa/test.png" showInTree="true" showAtRoot="true" isLeaf="true"/>
		<PartyType typeID="user" name="人员" partyTypeDataService="" description="" isRole="false" priority="4" icon16path="/aaa/test.png" icon32path="/aaa/test.png" showInTree="true" showAtRoot="false" isLeaf="true"/>

		<PartyType typeID="test" name="test" partyTypeDataService="" description="" isRole="false" priority="5" icon16path="/aaa/test.png" icon32path="/aaa/test.png" showInTree="true" showAtRoot="false" isLeaf="false"/>
 	</PartyTypeList>
  	<PartyTypeRefList>
		<PartyTypeRef refID="workgroup_user_ref" refName="workgroup_user_ref" refType="p_c" parentPartyTypeID="workgroup" childPartyTypeID="user" partyTypeRefDataService=""/>
  		<PartyTypeRef refID="org_user_ref" refName="org_user_ref" refType="p_c" parentPartyTypeID="org" childPartyTypeID="user" partyTypeRefDataService=""/>
		<PartyTypeRef refID="role_org_ref" refName="role_org_ref" refType="r_p" parentPartyTypeID="role" childPartyTypeID="org" partyTypeRefDataService=""/>
		<PartyTypeRef refID="role_workgroup_ref"  refName="role_workgroup_ref" refType="r_p" parentPartyTypeID="role" childPartyTypeID="workgroup" partyTypeRefDataService=""/>
		<PartyTypeRef refID="org_org_ref" refName="org_org_ref" refType="p_c" parentPartyTypeID="org" childPartyTypeID="org" partyTypeRefDataService=""/>
		<PartyTypeRef refID="workgroup_workgroup_ref" refName="workgroup_workgroup_ref" refType="p_c" parentPartyTypeID="workgroup" childPartyTypeID="workgroup" partyTypeRefDataService=""/>
		<PartyTypeRef refID="role_user_ref" refName="role_user_ref" refType="r_p" parentPartyTypeID="role" childPartyTypeID="user" partyTypeRefDataService=""/>

		<PartyTypeRef refID="role_test_ref" refName="role_test_ref" refType="r_p" parentPartyTypeID="role" childPartyTypeID="test" partyTypeRefDataService=""/>
		<PartyTypeRef refID="test_user_ref" refName="test_user_ref" refType="p_c" parentPartyTypeID="test" childPartyTypeID="user" partyTypeRefDataService=""/>
  	</PartyTypeRefList>
  </PartyModel>
 * 
 * 直接使用 w3c dom 分析，是否需要换成xstream？
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class PartyModelConfiguration implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1010566594235038584L;

	private PartyModel partyModel = null;

	public PartyModelConfiguration(File partyFile) {
		parsePartyFile(partyFile);
	}

	public PartyModel getPartyModel() {
		return this.partyModel;
	}

	private void parsePartyFile(File partyFile) {
		if (partyFile == null) {
			throw new IllegalArgumentException("partyConfigFile is null!");
		}
		if (!partyFile.exists()) {
			throw new IllegalArgumentException("partyConfigFile is not existed:" + partyFile.getAbsolutePath());
		}
		this.partyModel = new PartyModel();
		Document doc = XmlUtil.parseFileThrowsException(partyFile);

		// 解析PartyType节点
		Node partyTypeListNode = XmlUtil.findNode(doc, "PartyModel/PartyTypeList");
		if (partyTypeListNode != null) {
			parsePartyTypeList(partyTypeListNode);
		}

		// 解析PartyTypeRef节点
		Node partyTypeRefListNode = XmlUtil.findNode(doc, "PartyModel/PartyTypeRefList");
		if (partyTypeRefListNode != null) {
			parsePartyTypeRefList(partyTypeRefListNode);
		}

	}

	private void parsePartyTypeRefList(Node partyTypeRefListNode) {
		NodeList partyTypeRefList = XmlUtil.findNodes(partyTypeRefListNode, "PartyTypeRef");
		if (partyTypeRefList != null) {
			int partyTypeRefLength = partyTypeRefList.getLength();
			for (int i = 0; i < partyTypeRefLength; i++) {
				Element partyTypeRefElement = (Element) partyTypeRefList.item(i);
				if (partyTypeRefElement != null) {
					PartyTypeRefModel partyTypeRefModel = new PartyTypeRefModel();
					partyTypeRefModel.setRefID(partyTypeRefElement.getAttribute("refID"));
					partyTypeRefModel.setRefName(partyTypeRefElement.getAttribute("refName"));
					partyTypeRefModel.setRefType(partyTypeRefElement.getAttribute("refType"));
					partyTypeRefModel.setParentPartyTypeID(partyTypeRefElement.getAttribute("parentPartyTypeID"));
					partyTypeRefModel.setChildPartyTypeID(partyTypeRefElement.getAttribute("childPartyTypeID"));
					partyTypeRefModel.setPartyTypeRefDataService(partyTypeRefElement.getAttribute("partyTypeRefDataService"));

					Element extAttributesElement = (Element) XmlUtil.findNode(partyTypeRefElement, "extAttributes");
					if (extAttributesElement != null) {
						NodeList extProperyList = XmlUtil.findNodes(extAttributesElement, "extProperty");
						if (extProperyList != null) {
							int extPropertyLength = extProperyList.getLength();
							for (int j = 0; j < extPropertyLength; j++) {
								Element partyTypeExtPropertyElement = (Element) extProperyList.item(j);
								String extPropertyKey = partyTypeExtPropertyElement.getAttribute("key");
								String extPropertyValue = partyTypeExtPropertyElement.getAttribute("value");

								partyTypeRefModel.addExtProperty(extPropertyKey, extPropertyValue);
							}
						}
					}

					this.partyModel.addPartyTypeRefModel(partyTypeRefModel);
				}
			}
		}
	}

	private void parsePartyTypeList(Node partyTypeListNode) {
		NodeList partyTypeList = XmlUtil.findNodes(partyTypeListNode, "PartyType");
		if (partyTypeList != null) {
			int partyTypeLength = partyTypeList.getLength();
			for (int i = 0; i < partyTypeLength; i++) {
				Element partyTypeElement = (Element) partyTypeList.item(i);
				if (partyTypeElement != null) {
					PartyTypeModel partyTypeModel = new PartyTypeModel();
					partyTypeModel.setTypeID(partyTypeElement.getAttribute("typeID"));
					partyTypeModel.setName(partyTypeElement.getAttribute("name"));
					partyTypeModel.setDescription(partyTypeElement.getAttribute("description"));
					partyTypeModel.setIsRole(partyTypeElement.getAttribute("isRole"));
					partyTypeModel.setPriority(partyTypeElement.getAttribute("priority"));
					partyTypeModel.setIcon16(partyTypeElement.getAttribute("icon16path"));
					partyTypeModel.setIcon32(partyTypeElement.getAttribute("icon32path"));
					partyTypeModel.setShowAtRoot(partyTypeElement.getAttribute("showAtRoot"));
					partyTypeModel.setIsLeaf(partyTypeElement.getAttribute("isLeaf"));
					partyTypeModel.setShowInTree(partyTypeElement.getAttribute("showInTree"));

					partyTypeModel.setPartyTypeDataService(partyTypeElement.getAttribute("partyTypeDataService"));

					// 扩展属性
					Element extAttributesElement = (Element) XmlUtil.findNode(partyTypeElement, "extAttributes");
					if (extAttributesElement != null) {
						NodeList extProperyList = XmlUtil.findNodes(extAttributesElement, "extProperty");
						if (extProperyList != null) {
							int extPropertyLength = extProperyList.getLength();
							for (int j = 0; j < extPropertyLength; j++) {
								Element partyTypeExtPropertyElement = (Element) extProperyList.item(j);
								String extPropertyKey = partyTypeExtPropertyElement.getAttribute("key");
								String extPropertyValue = partyTypeExtPropertyElement.getAttribute("value");

								partyTypeModel.addExtProperty(extPropertyKey, extPropertyValue);
							}
						}
					}

					this.partyModel.addPartyTypeModel(partyTypeModel);
				}
			}
		}

	}
}