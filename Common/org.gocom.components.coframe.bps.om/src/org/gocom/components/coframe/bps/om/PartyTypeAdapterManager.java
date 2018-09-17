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
package org.gocom.components.coframe.bps.om;

import java.util.HashMap;
import java.util.Map;

import com.primeton.workflow.api.ParticipantType;

/**
 * TODO 此处填写 class 信息
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class PartyTypeAdapterManager {
	private static Map<String, ParticipantType> partyTypes = new HashMap<String, ParticipantType>();

	public static void addPartyType(String key, ParticipantType value) {
		partyTypes.put(key, value);
	}

	public static ParticipantType getPartyType(String key) {
		return partyTypes.get(key);
	}
}
