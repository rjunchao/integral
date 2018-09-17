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
package org.gocom.components.coframe.org.util;

import java.util.ArrayList;
import java.util.List;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.dataset.OrgTreeNode;
import org.gocom.components.coframe.tools.IconCls;

import com.eos.foundation.data.DataObjectUtil;

/**
 * 树节点转换器，将机构，岗位，员工转换成OrgTreeNode对象
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public class OrgTreeNodeConvertor {
	
	public static final String NODE_ID = "nodeId";
	public static final String NODE_TYPE = "nodeType";
	public static final String NODE_NAME = "nodeName";
	public static final String ICON_CLS = "iconCls";
	
	public static OrgTreeNode[] convert(OrgOrganization[] orgs){
		if(orgs == null || orgs.length == 0) return new OrgTreeNode[0];
		OrgTreeNode[] nodes = new OrgTreeNode[orgs.length];
		for(int i = 0;i<orgs.length;i++){
			nodes[i] = convert(orgs[i]);			
		}
		return nodes;
	}
	
	public static OrgTreeNode[] convert(OrgPosition[] positions){
		if(positions == null || positions.length == 0) return new OrgTreeNode[0];
		OrgTreeNode[] nodes = new OrgTreeNode[positions.length];
		for(int i = 0;i<positions.length;i++){
			nodes[i] = convert(positions[i]);
		}
		return nodes;
	}
	
	public static OrgTreeNode[] convert(OrgEmployee[] emps){
		if(emps == null || emps.length == 0) return new OrgTreeNode[0];
		OrgTreeNode[] nodes = new OrgTreeNode[emps.length];
		for(int i = 0;i<emps.length;i++){
			nodes[i] = convert(emps[i]);
		}
		return nodes;
	}
	
	public static OrgTreeNode convert(OrgOrganization org){
		OrgTreeNode node = (OrgTreeNode) DataObjectUtil.convertDataObject(org, OrgTreeNode.QNAME, true);
		node.setNodeId(String.valueOf(org.getOrgid()));
		node.setNodeType(OrgOrganization.NODE_TYPE);
		node.setNodeName(org.getOrgname());
		node.setIconCls(IconCls.ORGANIZATION);
		return node;
	}
	
	public static OrgTreeNode convert(OrgPosition position){
		OrgTreeNode node = (OrgTreeNode) DataObjectUtil.convertDataObject(position, OrgTreeNode.QNAME, true);
		node.setNodeId(String.valueOf(position.getPositionid()));
		node.setNodeType(OrgPosition.NODE_TYPE);
		node.setNodeName(position.getPosiname());
		node.setIconCls(IconCls.POSITION);
		return node;
	}
	
	public static OrgTreeNode convert(OrgEmployee emp){
		OrgTreeNode node = (OrgTreeNode) DataObjectUtil.convertDataObject(emp, OrgTreeNode.QNAME, true);
		node.setNodeId(String.valueOf(emp.getEmpid()));
		node.setNodeType(OrgEmployee.NODE_TYPE);
		node.setNodeName(emp.getEmpname());
		node.setIconCls(IconCls.EMPLOYEE);
		return node;
	}
	
	public static OrgTreeNode[] convertOrgs(OrgOrganization[] orgs){
		if(orgs == null || orgs.length == 0) return new OrgTreeNode[0];
		List<OrgTreeNode> nodelist =new ArrayList<OrgTreeNode>();
		for(int i=0;i<orgs.length;i++){
			OrgTreeNode node = OrgTreeNode.FACTORY.create();
			node.setNodeId(String.valueOf(orgs[i].getOrgid()));
			node.setNodeName(orgs[i].getOrgname());
			node.setIconCls(IconCls.ORGANIZATION);
			node.set("parentId", orgs[i].getOrgOrganization()==null?null:orgs[i].getOrgOrganization().getOrgid());
			nodelist.add(node);
		}
		return nodelist.toArray(new OrgTreeNode[]{});
	}
	
}
