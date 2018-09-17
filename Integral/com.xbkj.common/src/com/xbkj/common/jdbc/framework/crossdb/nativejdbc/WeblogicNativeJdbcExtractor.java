package com.xbkj.common.jdbc.framework.crossdb.nativejdbc;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import com.xbkj.common.jdbc.framework.crossdb.CrossDBConnection;
import com.xbkj.datasor.bs.framework.exception.FrameworkRuntimeException;

public class WeblogicNativeJdbcExtractor implements NativeJdbcExtractor {

    private Method m = null;

    public Connection getNativeConnection(Connection conn) throws SQLException {
        
        if (conn instanceof CrossDBConnection) {
            return conn;
        }
        
        if (m == null) {
            try {
                m = conn.getClass().getMethod("getVendorObj", new Class[0]);
            } catch (Exception e) {
                throw new FrameworkRuntimeException("Cant get native connection current version weblogic(method)");
            }
        }

        try {

            Object obj = m.invoke(conn, new Object[0]);
            return (Connection) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameworkRuntimeException("Cant get native connection current version weblogic(method invoke)");
        }
    }

}
