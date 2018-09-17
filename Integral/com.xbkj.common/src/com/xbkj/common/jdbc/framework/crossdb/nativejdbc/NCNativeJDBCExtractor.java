package com.xbkj.common.jdbc.framework.crossdb.nativejdbc;

import java.sql.Connection;
import java.sql.SQLException;

//import nc.bs.mw.ejb.xares.ConnectionProxy;
//import nc.bs.mw.sql.IerpConnection;

/*
 * @nopublish
 */
public class NCNativeJDBCExtractor implements NativeJdbcExtractor{

	public Connection getNativeConnection(Connection con) throws SQLException{
		
//		if (con instanceof IerpConnection) {
//			if ((((IerpConnection) con)
//			 		.getPhysicalConnection() instanceof ConnectionProxy)){
//				ConnectionProxy proxy = (ConnectionProxy) ((IerpConnection) con)
//				.getPhysicalConnection();
//				return proxy.getPhConnection();
//			}
		
		//	System.out.println("hey:get the realconnection-----------"+proxy.getPhConnection().getClass().getName());
//			return ((IerpConnection) con)
//		 		.getPhysicalConnection();
//		}
		return con;
		
	}

}
