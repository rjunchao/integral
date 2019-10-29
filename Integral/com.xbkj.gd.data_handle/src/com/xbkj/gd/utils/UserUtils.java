package com.xbkj.gd.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.primeton.cap.AppUserManager;
import com.sun.star.uno.RuntimeException;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.SQLParameter;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2017-7-13
 *@version 1.0.0
 *@desc
 */
public class UserUtils {
	
	private static Map<String, String> orgCache = new HashMap<String, String>();
	private static Map<String, List<String>> orgsCache = new HashMap<String, List<String>>();
	private static Map<String, List<Object[]>> nowOrgUsersCache = new HashMap<String, List<Object[]>>();

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
	 * 查询和当前用户是同一个机构的用户
	 * @return
	 */
	public static List<Object[]> getUserList(){
		
		String userOrg = getUserOrg();
		List<Object[]> objs = nowOrgUsersCache.get(userOrg);
		if(objs != null && objs.size() > 0){
			return objs;
		}
		String sql = "SELECT E.EMPCODE, E.EMPNAME FROM ORG_EMPLOYEE E " +
				"WHERE E.EMPCODE != ? AND E.ORGID IN(SELECT ORGID FROM ORG_EMPLOYEE E WHERE EMPCODE=?)";
		SQLParameter parameter = new SQLParameter();
		String user = getUser();
		parameter.addParam(user);
		parameter.addParam(user);
		try {
			objs = new DBUtils().queryObjs(sql, parameter);
			if(objs != null && objs.size() > 0){
				nowOrgUsersCache.put(userOrg, objs);
			}
			return objs;
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询制定机构和下属所有机构
	 */
	public static List<String> findDeptAndChildrenDept(){
		String user = getUser();
		List<String> orgs = orgsCache.get(user);
		if(orgs != null && orgs.size() > 0){
			return orgs;
		}
		List<String> dos = new ArrayList<String>();
		if("sysadmin".equals(user)) {
			//所有机构
			String[] orgids = getAllOrg();
			List<String> all = Arrays.asList(orgids);
			if(all != null && all.size() > 0){
				orgsCache.put(user, all);
			}
			return orgs;
		}
		
		String org = getUserOrg();
		getChildrens(dos, org);
		dos.add(org);//当前机构
		if(dos != null && dos.size() > 0){
			orgsCache.put(user, dos);
		}
		return dos;
	}
	
	/**
	 * parentid
	 * @param obj
	 * @return
	 */
	/*private String getParentId(String obj) {
		
		String org = getUser();
		if(obj == null) {
			//没有父级id
			if("sysadmin".equals(getUser())) {
				return 0L;
			}else {
				return ShiroUtils.getUserEntity().getUserOrg();
			}
		}
		return org;
		
	}*/
	
	private static String[] listOrgInfoByPid(String parentId){
		String sql = "SELECT ORGID FROM ORG_ORGANIZATION WHERE PARENTORGID=" + parentId;
		try {
			return new DBUtils().queryStrs(sql);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void getChildrens(List<String> dos, String parentId) {
		String[] vos = listOrgInfoByPid(parentId);
		if(vos == null){
			return ;
		}
		List<String> list = Arrays.asList(vos);
		if(list != null && list.size() > 0) {
			dos.addAll(list);
			for(String vo : list) {
				getChildrens(dos, vo);
			}
		}else {
			return ;
		}
	}
	
	/**
	 * 获取当前用户所属机构
	 * @return
	 */
	public static String getUserOrg(){
		String user = getUser();
		String org = orgCache.get(user);
		if(org != null){
			return org;
		}
		String sql = "SELECT O.ORGID FROM ORG_EMPLOYEE O WHERE O.EMPCODE='"+user+"'";
		try {
			org = new DBUtils().getOneValue(sql);
			if(org != null){
				orgCache.put(user, org);
			}
			return org;
		} catch (Exception e) {
			throw new RuntimeException("查询机构失败");
		}
		
	}
	
	
	/**
	 * 获取当前用户所属机构
	 * @return
	 */
	public static String[] getAllOrg(){
		
		String sql = "SELECT ORGID FROM ORG_ORGANIZATION";
		try {
			return new DBUtils().queryStrs(sql);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
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
