package com.pub.xbkj.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.eos.data.datacontext.DataContextManager;
import com.pub.xbkj.pubapp.data.IRowSet;
import com.pub.xbkj.pubapp.query.DataAccessUtils;
import com.xbkj.common.util.StringUtil;

/**
 *@author lijbe
 *@date 
 *@version 1.0.0
 */
public class XbkjCommonUtil {
 
	private static ConcurrentMap<String, String>  USER_ORG_MAP  = new ConcurrentHashMap<String,String>();
	
	/**
	 * 取得userId
	 * @return
	 */
	public static String getSysUserId(){
		
		String userId = DataContextManager.current().getMUODataContext().getUserObject().getUserId();
		return userId;
	}
	
	/**
	 * 取得empcode
	 * @return
	 */
	public static String getEmpCode(){
		
		//
		String userId = DataContextManager.current().getMUODataContext().getUserObject().getUserId();
		//根据userid得到登录用户的编码
		String sql ="";
		if(StringUtil.isNotEmpty(userId)){
			sql = "select e.empcode from org_employee e where e.empid='"+userId+"'";
			DataAccessUtils utils = new DataAccessUtils();
			//查询
			IRowSet rowSet = utils.query(sql);
			String empcode = null;
			while(rowSet.next()){
				empcode = rowSet.getString(0);
			}
			return empcode;
		}else{
			return null;
		}
	}
	
	
	
	/**
	 * 取得userName
	 * @return
	 */
     public static String getSysUserName(){
		
		String userId = DataContextManager.current().getMUODataContext().getUserObject().getUserName();
		
		return userId;
	}
     
     /**
      * 查询当前用户的机构编码
      * @param userId
      * @return
      */
     public static synchronized String getOrgCodeByCurrUser(String userId){
    	 if(USER_ORG_MAP.isEmpty()){
    		 initUserOrg(null);
    	 }
    	 if(USER_ORG_MAP.containsKey(userId)){
    		 return USER_ORG_MAP.get(userId); 
    	 }else{
    		 initUserOrg(userId);
    	 }
    	 
    	 return USER_ORG_MAP.get(userId);
    	 
     }
     
     private static void initUserOrg(String userId){
    	 
    	 String sql = "select e.empid,o.orgcode from org_employee e inner join org_organization o on e.orgid= o.orgid ";
     	
    	 if(StringUtil.isNotEmpty(userId)){
    		 sql +=" and e.empid = '"+userId+"' or  e.operatorid = '"+userId+"' ";
    	 }
    	 DataAccessUtils dao = new DataAccessUtils();
    	 
    	 IRowSet rowset = dao.query(sql);
    	 while(rowset.next()){
    		 USER_ORG_MAP.put(rowset.getString(0), rowset.getString(1));
    	 }
    	
     }
	
}
