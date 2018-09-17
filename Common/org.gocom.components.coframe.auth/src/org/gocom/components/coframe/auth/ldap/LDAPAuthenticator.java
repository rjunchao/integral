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

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * LDAP验证实现类
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */
public class LDAPAuthenticator implements ILDAPAuthenticator {

	private String URL = "ldap://localhost/";

	private String BASEDN = "o=primeton,c=cn";

	private String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

	/*
	 * (non-Javadoc)
	 * @see org.gocom.components.coframe.rights.ldap.ILDAPAuthenticator#authenricate(java.lang.String,
	 *      java.lang.String)
	 */
	public int authenricate(String userId, String password) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
		env.put(Context.PROVIDER_URL, URL + BASEDN);
		env.put(Context.AUTHORITATIVE, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "uid=" + userId + "," + BASEDN);
		env.put(Context.SECURITY_CREDENTIALS, password);
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
			return 1;
		} catch (AuthenticationException e) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
