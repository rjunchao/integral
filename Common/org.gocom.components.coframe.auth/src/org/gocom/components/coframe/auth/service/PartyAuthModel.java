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
package org.gocom.components.coframe.auth.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.primeton.cap.party.Party;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class PartyAuthModel {
	private Map<String, Party> partyMap = new HashMap<String, Party>();

	/**
	 * 由于party可能是多个，该处采用map存储，最好取value
	 */
	private Map<String, Party> roleMap = new HashMap<String, Party>();

	public void addParty(Party party) {
		partyMap.put(getPartyKey(party), party);
	}

	public void addRoleparty(Party party) {
		roleMap.put(getPartyKey(party), party);
	}

	public Collection<Party> getPartys() {
		return partyMap.values();
	}

	public Collection<Party> getRoles() {
		return roleMap.values();
	}

	private String getPartyKey(Party party) {
		return party.getId() + "_" + party.getPartyTypeID();
	}
}
