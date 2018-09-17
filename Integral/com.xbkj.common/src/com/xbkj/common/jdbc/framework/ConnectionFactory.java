package com.xbkj.common.jdbc.framework;

import java.sql.Connection;
import java.sql.SQLException;

import com.eos.common.connection.ConnectionHelper;
import com.eos.common.connection.SimpleConnectionHelper;


/**
 * @author hey ��ݿ����ӹ�������װ��ݿ����ӵĹ����̣��򻯿ͻ��˵ĵ���
 */

public class ConnectionFactory {
	/**
	 * �õ���ǰ�߳����Դ������
	 * 
	 * @return ������ݿ�����
	 * @throws SQLException
	 *             ����ڵõ�������ӹ���з�������׳���ݷ����쳣
	 */
	static public Connection getConnection() throws SQLException {

//		// System.out.println("get connection" + realConn);
//		CrossDBConnection realConn = new CrossDBConnection(DataSourceCenter
//				.getInstance().getConnection());
//		if (Logger.isInfoEnabled()){
//			NativeJdbcExtractor extractor = NativeJdbcExtractorFactory
//			.createJdbcExtractor();
//			//Trace.traceDetail("Open " + realConn.getId()+"_"+!extractor.getNativeConnection(realConn).getAutoCommit());
//		}
////		return realConn;
//		return null;
		
		 Connection con = SimpleConnectionHelper.getConnection();
		 return con;
	}

	
	static public Connection getConnection(String dataSource)
			throws SQLException {
		
		 Connection con = ConnectionHelper.getCurrentContributionConnection(dataSource);
        // dbType = DBUtil.getDbType(con);
         // dbType = DataSourceCenter.getInstance().getDatabaseType();
        // this.conn = con;
	
		 return con;
	}
}
