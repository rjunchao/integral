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
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author wuyuhou
 * 
 */
public class PartyModel implements Serializable {

	private static final long serialVersionUID = 89671471136041552L;

	private List<PartyTypeModel> partyTypeModelList = new ArrayList<PartyTypeModel>();

	private List<PartyTypeRefModel> partyTypeRefModelList = new ArrayList<PartyTypeRefModel>();

	public List<PartyTypeModel> getPartyTypeModelList() {
		return partyTypeModelList;
	}

	public void addPartyTypeModel(PartyTypeModel partyTypeModel) {
		this.partyTypeModelList.add(partyTypeModel);
	}

	public List<PartyTypeRefModel> getPartyTypeRefModelList() {
		return partyTypeRefModelList;
	}

	public void addPartyTypeRefModel(PartyTypeRefModel partyTypeRefModel) {
		this.partyTypeRefModelList.add(partyTypeRefModel);
	}
}