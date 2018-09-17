package com.xbkj.common.jdbc.framework.crossdb.nativejdbc;

/**
 * @nopublish
 */ 
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import com.xbkj.log.bs.logging.Logger;

public class WebSphereNativeJdbcExtractor implements NativeJdbcExtractor {
 
	private static final String JDBC_ADAPTER_CONNECTION_NAME = "com.ibm.ws.rsadapter.jdbc.WSJdbcConnection";

	private static final String JDBC_ADAPTER_UTIL_NAME = "com.ibm.ws.rsadapter.jdbc.WSJdbcUtil";

	private Method webSphereNativeConnectionMethod;

	private Class webSphereConnectionClass;

	public WebSphereNativeJdbcExtractor() {
		// Detect WebSphere 5 connection classes.
		try {
			this.webSphereConnectionClass = getClass().getClassLoader()
					.loadClass(JDBC_ADAPTER_CONNECTION_NAME);
			Class jdbcAdapterUtilClass = getClass().getClassLoader().loadClass(
					JDBC_ADAPTER_UTIL_NAME);
			this.webSphereNativeConnectionMethod = jdbcAdapterUtilClass
					.getMethod("getNativeConnection",
							new Class[] { this.webSphereConnectionClass });
		} catch (Exception ex) {
			Logger.error("Could not find WebSphere 5 connection pool classes",
					ex);
		}
	}

	public Connection getNativeConnection(Connection con) throws SQLException {

		if (this.webSphereConnectionClass != null
				&& this.webSphereConnectionClass.isAssignableFrom(con
						.getClass())) {
			try {
				return (Connection) this.webSphereNativeConnectionMethod
						.invoke(null, new Object[] { con });
			} catch (InvocationTargetException ex) {
				throw new SQLException(
						"WebSphere's getNativeConnection method failed");
			} catch (Exception ex) {
				throw new SQLException(
						"Could not access WebSphere's getNativeConnection method");
			}
		}
		return con;

	}

}
