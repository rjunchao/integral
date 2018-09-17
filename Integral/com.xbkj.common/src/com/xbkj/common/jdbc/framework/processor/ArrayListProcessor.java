package com.xbkj.common.jdbc.framework.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ArrayListProcessor extends  BaseProcessor {
    /**
	 * <code>serialVersionUID</code> 
	 */
	private static final long serialVersionUID = -3631733378522079801L;

	public Object processResultSet(ResultSet rs) throws SQLException {

        List result = new ArrayList();

        while (rs.next()) {
            result.add(ProcessorUtils.toArray(rs));
        }
        return result;
    }

}
