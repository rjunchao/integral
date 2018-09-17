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

import java.io.Serializable;
import java.util.Properties;

/**
 * 
 *
 * @author wuyuhou
 *
 */
public class PartyTypeModel implements Serializable {
	
	private static final long serialVersionUID = -1552305668683060874L;

	private String typeID;
	
	private String partyTypeDataService;
	
	private String name;
	
	private String description;
	
	private String priority;
	
	private String icon16;
	
	private String icon32;
	
	private String showAtRoot;
	
	private String isLeaf;
	
	private String isRole;
	
	private String showInTree;
	
//	private TenantModel tenant = null;
	
	private Properties extProperties = new Properties();
	
	public void addExtProperty(String key, String value){
		extProperties.put(key, value);
	}
	
	public Properties getExtProperties(){
		return this.extProperties;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon16() {
		return icon16;
	}

	public void setIcon16(String icon16) {
		this.icon16 = icon16;
	}

	public String getIcon32() {
		return icon32;
	}

	public void setIcon32(String icon32) {
		this.icon32 = icon32;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIsRole() {
		return isRole;
	}

	public void setIsRole(String isRole) {
		this.isRole = isRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartyTypeDataService() {
		return partyTypeDataService;
	}

	public void setPartyTypeDataService(String partyTypeDataService) {
		this.partyTypeDataService = partyTypeDataService;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getShowAtRoot() {
		return showAtRoot;
	}

	public void setShowAtRoot(String showAtRoot) {
		this.showAtRoot = showAtRoot;
	}
	
	public String getShowInTree(){
		return showInTree;
	}
	
	public void setShowInTree(String showInTree){
		this.showInTree = showInTree;
	}

//	public TenantModel getTenant() {
//		return tenant;
//	}
//
//	public void setTenant(TenantModel tenant) {
//		this.tenant = tenant;
//	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
}