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
public class PartyTypeRefModel implements Serializable {
	
	private static final long serialVersionUID = 4917953390194747413L;

	private String refID;
	
	private String refName;
	
	private String refType;
	
	private String partyTypeRefDataService;
	
	private String parentPartyTypeID;
	
	private String childPartyTypeID;
	
//	private TenantModel tenant = null;
	
	private Properties extProperties = new Properties();
	
	public void addExtProperty(String key, String value){
		extProperties.put(key, value);
	}
	
	public Properties getExtProperties(){
		return this.extProperties;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getChildPartyTypeID() {
		return childPartyTypeID;
	}

	public void setChildPartyTypeID(String childPartyTypeID) {
		this.childPartyTypeID = childPartyTypeID;
	}

	public String getParentPartyTypeID() {
		return parentPartyTypeID;
	}

	public void setParentPartyTypeID(String parentPartyTypeID) {
		this.parentPartyTypeID = parentPartyTypeID;
	}

	public String getPartyTypeRefDataService() {
		return partyTypeRefDataService;
	}

	public void setPartyTypeRefDataService(String partyTypeRefDataService) {
		this.partyTypeRefDataService = partyTypeRefDataService;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

//	public TenantModel getTenant() {
//		return tenant;
//	}
//
//	public void setTenant(TenantModel tenant) {
//		this.tenant = tenant;
//	}

}