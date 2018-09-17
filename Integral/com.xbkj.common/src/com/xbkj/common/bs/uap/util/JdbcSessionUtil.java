package com.xbkj.common.bs.uap.util;

import com.xbkj.common.jdbc.framework.JdbcSession;
import com.xbkj.log.bs.logging.Log;

/*
 * Created on 2005-4-7
 * @author yanglei(yanglei@ufsoft.com.cn)
 */

public class JdbcSessionUtil {
    private static Log logger = Log.getInstance(JdbcSessionUtil.class);
 	
	public static JdbcSession createJdbcSession() {
	    JdbcSession session = null;
		try {
		
			session = new JdbcSession();
		} catch (Exception e1) {			
			logger.warn("Cannot create datasource, use default.", e1);
		} 

		return session;
		
	}

    /**
     * @param dbJNDI
     * @return
     */
    public static JdbcSession createJdbcSession(String dsName) {
        JdbcSession session = null;
		try {
			if(null != dsName && dsName.length() > 0){
				session = new JdbcSession(dsName);	
			}else{
				session = createJdbcSession();
			}				
			
		} catch (Exception e1) {			
			logger.warn("Cannot create datasource, use default.", e1);
		} 

		return session;
    
    }
	 
	
}
