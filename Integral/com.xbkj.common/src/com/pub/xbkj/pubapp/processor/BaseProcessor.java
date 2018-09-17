package com.pub.xbkj.pubapp.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;

/**
 * User: 贺扬<br>
 * Date: 2005-1-14<br>
 * Time: 13:43:06<br>
 * 抽象结果集处理对象，用于自动关闭ResultSet对象，子类需要实现processResultSet方法
 */
public abstract class BaseProcessor implements ResultSetProcessor {

    /**
	 * id
	 */
	private static final long serialVersionUID = 4407909922370195208L;

	public Object handleResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new IllegalArgumentException("resultset parameter can't be null");
        try {
            return processResultSet(rs);
        } catch (SQLException e) {
            throw new SQLException("the resultsetProcessor error!" + e.getMessage(), e.getSQLState());
        } 
//        finally {
//            if (rs != null)
//                try {
//                    rs.close();
//                } catch (Exception e) {
//
//                }
//
//        }

    }

    /**
     * 处理结果集返回需要的对象
     * 
     * @param rs
     *            结果集
     * @return 需要的对象
     * @throws SQLException如果发生错误
     */
    public abstract Object processResultSet(ResultSet rs) throws SQLException;

}
