package com.xbkj.common.jdbc.framework.crossdb.nativejdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @nopublish
 * @author hey
 *
 */
public interface NativeJdbcExtractor {

	public Connection getNativeConnection(Connection con) throws SQLException;
}
