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
package org.gocom.components.coframe.auth.ldap;

/**
 * LDAP验证接口
 *
 * @author shitf (mailto:shitf@primeton.com)
 */

public interface ILDAPAuthenticator {
	
	/**
	 * 验证用户
	 * @param ID 用户ID
	 * @param password 用户密码
	 * @return 返回0,验证失败
	 *         返回1,验证成功
	 */
	int authenricate(String userId,String password);
}
