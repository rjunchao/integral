package com.xbkj.gd.utils;

import com.primeton.cap.AppUserManager;
import com.xbkj.common.bs.dao.DAOException;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-7-13
 *@version 1.0.0
 *@desc
 */
public class UserUtils {

//	public static String getRole(){
//		IUserObject user = DataContextManager.current().getMUODataContext().getUserObject();
//		
//	}
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static String getUser(){
		return AppUserManager.getCurrentUserId();
	}
	
	/**
	 * 获取当前用户所属机构
	 * @return
	 */
	public static String getUserOrg(){
		String sql = "SELECT o.ORGID FROM org_employee o WHERE o.EMPCODE='"+getUser()+"'";
		try {
			return new DBUtils().getOneValue(sql);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 对中文进行编码
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

}
