/*
 * Copyright 2013 Primeton.
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
package org.gocom.components.coframe.auth.menu;

import com.primeton.cap.management.resource.IMenuResourceManager;

/**
 * TODO 此处填写 class 信息
 * 
 * @author guwei (mailto:guwei@primeton.com)
 */
public class FunctionMenuResourceManager implements IMenuResourceManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.primeton.cap.management.resource.IMenuResourceManager#canAccess(java.lang.String[])
	 */
	public boolean canAccess(String[] states) {
		// 0代表无权限，1代表有权限
		for (String state : states) {
			if ("1".equals(state)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.primeton.cap.management.resource.IMenuResourceManager#getResourceURI(java.lang.String)
	 */
	public String getResourceURI(String resourceID) {
		return null;
	}

}