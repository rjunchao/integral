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
package org.gocom.components.coframe.tools.superadmin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.eos.common.connection.ConnectionHelper;
import com.eos.data.datacontext.DataContextManager;
import com.eos.data.datacontext.IMUODataContext;
import com.eos.data.datacontext.IUserObject;
import com.primeton.cap.ISystemConstants;

/**
 * 
 * 超级管理员辅助类，判断当前用户是否是超级管理员
 * 
 * @author caozw@primeton.com
 * 
 */
public class SuperAdminService {
   
	private static final String SUPER_USER_ID = "sysadmin";
	
	/**
	 * 取出所有用户账号给予查询组织机构树的权限
	 * @2015/4/27
	 * @return
	 */
	public static List<String> queryUserId(String userid){		
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("select orgid from org_organization where orgid in ");
		sb.append("(select orgid from org_employee where userid = '"+userid+"')");
		String sql = sb.toString();
		Connection conn = ConnectionHelper
				.getCurrentContributionConnection("default");		
		Statement stmt = null;
		try {			
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);			
			while (rs.next()) {							
				list.add(rs.getString(1));			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(stmt);
			close(conn);
		}
	       return list;
	}
	
	/**
	 * @2015/4/27
	 */
	private static void close(Connection conn) {
		if (conn == null)
			return;
		try {
			conn.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	
	/**
	 * @2015/4/27
	 */
	private static void close(Statement stmt) {
		if (stmt == null)
			return;
		try {
			stmt.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	
	
	/**
	 * 判断用户所在机构级别生成不同机构树
	 * 注释掉原有方法，新增的判断管理员方法
	 * @2015/4/27
	 * @author lc
	 * @return
	 */
	public static boolean currUserIsSupserAdmin() {
		IMUODataContext muoContext = DataContextManager.current()
				.getMUODataContext();
		IUserObject userObject = muoContext.getUserObject();
		List<String> list = queryUserId((String)userObject.getAttributes().get(ISystemConstants.USER_ID));
		if (userObject != null){
			if(SUPER_USER_ID.equals(userObject.getAttributes().get(ISystemConstants.USER_ID))){
				return true;
			}else{
				if(list.size()>0){	
					return false;	
				}
			}
			 
		}
		return false;		
	}
	
	

	/**
	 * 如果用户名是sysadmin则认为是超级管理员
	 * 
	 * @return
	 */
/*	public static boolean currUserIsSupserAdmin() {
		IMUODataContext muoContext = DataContextManager.current()
				.getMUODataContext();
		IUserObject userObject = muoContext.getUserObject();
		if (userObject != null) {
			return SUPER_USER_ID.equals(userObject.getAttributes().get(ISystemConstants.USER_ID));
		}
		return false;
	}*/
}
