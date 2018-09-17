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
package org.gocom.components.coframe.auth.login;

import java.util.List;

import org.gocom.components.coframe.auth.DefaultAuthManagerService;
import org.gocom.components.coframe.init.UserObjectInit;

import com.eos.access.http.OnlineUserManager;
import com.eos.data.datacontext.UserObject;
import com.primeton.cap.auth.IAuthManagerService;
import com.primeton.cap.auth.MenuTree.MenuTreeNode;

/**
 * 登录服务类
 * 
 * @author shitf (mailto:shitf@primeton.com)
 */
public class LoginService {
	
	/**
	 * 登录一个UserObject
	 * @param userObject UserObject对象
	 */
	public void login(UserObject userObject){
		OnlineUserManager.login(userObject);
	}
	
	/**
	 * 
	 * 构造userObject
	 * @param userObject
	 * @param userId
	 */
	public UserObject initUserObject(String userId) {
		return UserObjectInit.INSTANCE.init(userId);
	}
	
	/**
	 * 获取首页菜单数据
	 * @return
	 */
	public List<MenuTreeNode> getUserMenuTree(){
		IAuthManagerService service = new DefaultAuthManagerService();
		return service.getUserMenuTree().getMenuTreeRootNodeList();
	}
	/**
	 * 注销用户
	 * @param userObject
	 */
	public UserObject logout(String uniqueId){
		return (UserObject) OnlineUserManager.logoutByUniqueId(uniqueId);
	}
}
