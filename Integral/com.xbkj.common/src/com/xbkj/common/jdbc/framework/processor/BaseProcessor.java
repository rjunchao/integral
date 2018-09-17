package com.xbkj.common.jdbc.framework.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *
 */
public abstract class BaseProcessor implements ResultSetProcessor {

    public Object handleResultSet(ResultSet rs) throws SQLException {
        if (rs == null)
            throw new IllegalArgumentException("resultset parameter can't be null");
        try {
            return processResultSet(rs);
        } catch (SQLException e) {
            throw new SQLException("the resultsetProcessor error!" + e.getMessage(), e.getSQLState());
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {

                }

        }

    }

    /**
     * ����������Ҫ�Ķ���
     * 
     * @param rs
     *            ���
     * @return ��Ҫ�Ķ���
     * @throws SQLException��������
     */
    public abstract Object processResultSet(ResultSet rs) throws SQLException;

}
