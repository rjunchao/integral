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
 * @deprecated 
 * 
 * 租户模型，暂时不确定有什么属性
 *
 * @author guwei (mailto:guwei@primeton.com)
 */
public class TenantModel implements Serializable {
	
	private static final long serialVersionUID = 4183070025438556693L;
	
	private Properties extProperties = new Properties();
	
	public void addProperty(String key, String value){
		extProperties.put(key, value);
	}
	
	public Properties getProperties(){
		return this.extProperties;
	}
	
	public String getPropertyValue(String key){
		return this.extProperties.getProperty(key);
	}

}