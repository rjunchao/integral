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
package org.gocom.components.coframe.rights.user;

import java.util.Date;

import org.gocom.components.coframe.rights.dataset.CapUser;

import com.eos.system.utility.CryptoUtil;
import com.primeton.cap.AppUserManager;
import com.primeton.cap.TenantManager;

/**
 * 用户管理
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */

public class DefaultUserManager {
	
	public final static DefaultUserManager INSTANCE = new DefaultUserManager();

	/**
	 * 设置用户属性
	 * @param capUser 用户对象
	 * @return 用户对象
	 */
	public CapUser setUserAttribute(CapUser capUser) {
		try {
			capUser.setPassword(encrypt(capUser.getPassword()));
			capUser.setCreateuser(AppUserManager.getCurrentUserId());
			capUser.setTenantId(TenantManager.getCurrentTenantID());
			capUser.setCreatetime(new Date());
			capUser.setLastlogin(new Date());
			capUser.setUnlocktime(new Date());
			if (capUser.getStartdate() == null) {
				capUser.setStartdate(new Date());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return capUser;
	}

	/**
	 * 把字符串加密
	 * @param attr
	 * @return
	 */
	public String encodeString(String attr){
		try {
			attr = encrypt(attr);
		} catch (Exception e) {
		}
		return attr;
	}

	private final static String ENCRYPT_KEY = "cap_user";

	// 加密
	private static String encrypt(String password) throws Exception {
		return CryptoUtil.encrypt(password, ENCRYPT_KEY);
	}

	// 解密
	private static String decrypt(String password) throws Exception {
		return CryptoUtil.decrypt(password, ENCRYPT_KEY);
	}
}
