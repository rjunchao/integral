package com.xbkj.common.jdbc.framework.processor;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2005-1-14
 * Time: 13:41:47
 * �����ӿ�
 */
public interface ResultSetProcessor extends Serializable {
    /**
     * ������������Ҫ����ݽṹ
     * @param rs      ��ݿ���
     * @return        ������
     * @throws SQLException�������׳�
     */
    public Object handleResultSet(ResultSet rs) throws SQLException;
}
