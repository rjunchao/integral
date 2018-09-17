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

import java.math.BigDecimal;
import java.util.Date;

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;
import org.gocom.components.coframe.org.util.IOrgConstants;

import com.primeton.cap.TenantManager;

/**
 * 默认机构辅助类
 * @author yangyong (mailto:yangyong@primeton.com)
 */
public class OrgHelper {
	
	public static final String POSITION_TYPE = "organization";
	
	/**
	 * 根据父机构设置子机构部分信息：level, seq, isLeaf,subcount
	 * @param org
	 * @param parentOrg
	 */
	public static void expandOrganizationPropertyByParent(OrgOrganization org, OrgOrganization parentOrg){
		if(org == null) return;
		if(parentOrg == null || parentOrg.getOrgid() == null){
			org.setOrglevel(BigDecimal.valueOf(1));
			//不存在父机构:"."+本机构ID+".";
			org.setOrgseq("."+org.getOrgid()+".");
		}else{
			//级别在父机构的级别上增加1
			org.setOrglevel(parentOrg.getOrglevel().add(BigDecimal.valueOf(1)));
			//存在父机构:父机构顺序+本机构ID+".";
			org.setOrgseq(parentOrg.getOrgseq() + org.getOrgid()+".");
		}
		org.setCreatetime(new Date());
		org.setLastupdate(new Date());
		org.setIsleaf(IOrgConstants.IS_LEAF_YES);
		org.setSubcount(BigDecimal.valueOf(0));
		org.setTenantid(TenantManager.getCurrentTenantID());
	}

	/**
	 * 更改父机构的信息，isLeaf,subcount
	 * @param parentOrg
	 */
	public static void expandParentOrganizationProperty(OrgOrganization parentOrg) {
		if(parentOrg == null || parentOrg.getOrgid() == null){
			return ;
		}
		parentOrg.setSubcount(parentOrg.getSubcount().add(BigDecimal.valueOf(1)));
		parentOrg.setIsleaf(IOrgConstants.IS_LEAF_NO);
	}
	
	public static void expandPositionPropertyByParent(OrgPosition position, OrgPosition parentPosition){
		if(parentPosition == null || parentPosition.getPositionid() == null){
			position.setPosilevel(BigDecimal.valueOf(1));
			position.setPositionseq("."+position.getPositionid()+".");
		}else{
			position.setPosilevel(parentPosition.getPosilevel().add(BigDecimal.valueOf(1)));
			position.setPositionseq(parentPosition.getPositionseq()+position.getPositionid()+".");
		}
		if(parentPosition != null && parentPosition.getOrgOrganization() != null){
			position.setOrgOrganization(parentPosition.getOrgOrganization());
		}
		position.setCreatetime(new Date());
		position.setLastupdate(new Date());
		position.setPositype(POSITION_TYPE);
		position.setIsleaf(IOrgConstants.IS_LEAF_YES);
		position.setSubcount(BigDecimal.valueOf(0));
		position.setTenantid(TenantManager.getCurrentTenantID());
	}
	
	public static void expandParentPositionProperty(OrgPosition parentPosition){
		if(parentPosition == null || parentPosition.getPositionid() == null){
			return;
		}
		parentPosition.setIsleaf(IOrgConstants.IS_LEAF_NO);
		parentPosition.setSubcount(parentPosition.getPosilevel().add(BigDecimal.valueOf(1)));
	}

	public static void expandEmployeeProperty(OrgEmployee emp) {
		emp.setCreatetime(new Date());
		emp.setLastmodytime(new Date());
		emp.setTenantid(TenantManager.getCurrentTenantID());
	}
	
}
