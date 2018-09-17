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
import org.gocom.components.coframe.tools.IConstants;

import com.primeton.cap.party.Party;

/**
 * 组织机构对象到Party模型的适配工具类，适配转换对象包括机构、岗位、人员
 *
 * @author yangzhou (mailto:yangzhou@primeton.com)
 */
public class OrgPartyAdaptUtil {

	/**
	 * 适配机构为Party对象
	 * @param org
	 * @return
	 */
	public static Party adapt(OrgOrganization org) {
		if(org == null) return null;
		
		Party party = new Party(IConstants.ORG_PARTY_TYPE_ID, 
				String.valueOf(org.getOrgid()), org.getOrgcode(), 
				org.getOrgname(), org.getTenantid());
		return party;
	}
	
	/**
	 * 适配岗位为Party对象
	 * @param position
	 * @return
	 */
	public static Party adapt(OrgPosition position) {
		if(position == null) return null;
		
		Party party = new Party(IConstants.POSITION_PARTY_TYPE_ID, 
				String.valueOf(position.getPositionid()), position.getPosicode(), 
				position.getPosiname(), position.getTenantid());
		return party;
	}
	
	/**
	 * 适配员工为Party对象
	 * @param emp
	 * @return
	 */
	public static Party adapt(OrgEmployee emp) {
		if(emp == null) return null;
		
		Party party = new Party(IConstants.EMP_PARTY_TYPE_ID, 
				String.valueOf(emp.getEmpid()), emp.getEmpcode(), 
				emp.getEmpname(), emp.getTenantid());
		party.putExtAttribute(IOrgConstants.ORGID_PROPERTY, String.valueOf(emp.getOrgid()));
		party.putExtAttribute(IConstants.EMAIL, emp.getOemail());
		party.putExtAttribute(IConstants.MANAGED_ORGS, emp.getOrgidlist());
		party.putExtAttribute(IConstants.MANAGED_ROLES, emp.getSpecialty());
		
		return party;
	}
	
	/**
	 * 适配机构数组为Party列表
	 * @param orgs
	 * @return
	 */
	public static List<Party> adapt(OrgOrganization[] orgs) {
		List<Party> returnList = new ArrayList<Party>();
		if (orgs != null) {
			for (OrgOrganization org : orgs) {
				returnList.add(adapt(org));
			}
		}
		return returnList;
	}
	
	/**
	 * 适配岗位数组为Party列表
	 * @param positions
	 * @return
	 */
	public static List<Party> adapt(OrgPosition[] positions) {
		List<Party> returnList = new ArrayList<Party>();
		if (positions != null) {
			for (OrgPosition position : positions) {
				returnList.add(adapt(position));
			}
		}
		return returnList;
	}
	
	/**
	 * 适配员工数组为Party列表
	 * @param emps
	 * @return
	 */
	public static List<Party> adapt(OrgEmployee[] emps) {
		List<Party> returnList = new ArrayList<Party>();
		if (emps != null) {
			for (OrgEmployee emp : emps) {
				returnList.add(adapt(emp));
			}
		}
		return returnList;
	}
	
	/**
	 * 适配单个机构为Party列表
	 * @param org
	 * @return
	 */
	public static List<Party> adaptToList(OrgOrganization org) {
		List<Party> returnList = new ArrayList<Party>();
		if (org != null) {
			returnList.add(adapt(org));
		}
		return returnList;
	}
	
	/**
	 * 适配单个岗位为Party列表
	 * @param position
	 * @return
	 */
	public static List<Party> adaptToList(OrgPosition position) {
		List<Party> returnList = new ArrayList<Party>();
		if (position != null) {
			returnList.add(adapt(position));
		}
		return returnList;
	}
	
	/**
	 * 适配单个员工为Party列表
	 * @param emp
	 * @return
	 */
	public static List<Party> adaptToList(OrgEmployee emp) {
		List<Party> returnList = new ArrayList<Party>();
		if (emp != null) {
			returnList.add(adapt(emp));
		}
		return returnList;
	}
}
