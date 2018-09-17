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
package org.gocom.components.coframe.auth.service.impl;

import org.gocom.components.coframe.tools.IConstants;

/**
 * 员工授权服务实现类，员工需要计算所有父节点
 * 
 * @author caozw (mailto:caozw@primeton.com)
 */
public class UserAuthPartyService extends DefaultAuthPartyService {

	public UserAuthPartyService() {
		super(IConstants.USER_PARTY_TYPE_ID);
	}

	/**
	 * 查询员工
	 */
	public String[] getParentPartyTypes() {
		return new String[] { IConstants.EMP_PARTY_TYPE_ID };
	}

}
