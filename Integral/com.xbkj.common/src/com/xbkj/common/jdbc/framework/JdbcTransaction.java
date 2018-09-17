package com.xbkj.common.jdbc.framework;

import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;
import com.xbkj.log.bs.logging.Logger;

/**
 * @author hey
 * ����JDBC���ӵ������ܵ���������࣬�����������ֻ�ʺ��ڷ���������session bean������¶Ե����Դ�����������
 */
public class JdbcTransaction {
	/**
	 * @link aggregation
	 */

	private JdbcSession session = null;

	/**
	 * JdbcTransaction���캯��
	 * @param session
	 */
	public JdbcTransaction(JdbcSession session) {
		super();
		this.session = session;
	}
/**
 * �������Դ���뼶��
 * @param i
 */
	public void setTransactionIsolation(int i){

		try {
			session.setTransactionIsolation(i);
		} catch (DbException e) {
			Logger.error("����������뼶�����", e);

		}

	}

	public void startTransaction() {

		try {
			session.setAutoCommit(false);
		} catch (DbException e) {
			Logger.error("������������.  ������������,�����ܱ�֤����ҵ�������", e);

		}

	}
	
	public void rollbackTransaction() {
		try {
			session.rollbackTrans();
		} catch (DbException e) {
			Logger.error("�ع��������", e);

		}
	}

	public void commitTransaction() {

		try {
			session.commitTrans();
		} catch (DbException e) {
			Logger.error("�����ύ����", e);

		}
	}

	/**
	 * @param sql
	 * @throws DbException
	 */
	public void addBatch(String sql) throws DbException {

		try {
			session.addBatch(sql);
		} catch (DbException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * @param sql
	 * @param parameters
	 * @throws DbException
	 */
	public void addBatch(String sql, SQLParameter parameters) throws DbException
			  {
		try {
			session.addBatch(sql, parameters);
		} catch (DbException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * @return
	 * @throws DbException
	 */
	public int executeBatch() throws DbException {
		try {
		return	session.executeBatch();
		} catch (DbException e) {
			rollbackTransaction();
			throw e;
		}  
	}

	/**
	 * @param sql
	 * @param processor
	 * @return
	 * @throws DbException
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor)
			throws DbException {
		try {
			return  session.executeQuery(sql, processor);
		} catch (DbException e) {
			rollbackTransaction();
			throw e; 
		}
		
	}

	/**
	 * @param sql
	 * @param parameter
	 * @param processor
	 * @return
	 * @throws DbException
	 */
	public Object executeQuery(String sql, SQLParameter parameter,
			ResultSetProcessor processor) throws DbException {
		try {
			return  session.executeQuery(sql, parameter,processor);
		} catch (DbException e) {
			rollbackTransaction();
			throw e; 
		}
		
	}

	/**
	 * @param sql
	 * @return
	 * @throws DbException
	 */
	public int executeUpdate(String sql) throws DbException {
		
		try {
			return session.executeUpdate(sql);
		} catch (DbException e) {
			rollbackTransaction();
			throw e; 
		}
	}

	/**
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws DbException
	 */
	public int executeUpdate(String sql, SQLParameter parameter)
			throws DbException {
		try {
			return session.executeUpdate(sql, parameter);
		} catch (DbException e) {
			rollbackTransaction();
			throw e; 
		}
	
	}
	
	public void close()
	{
		session.closeAll();
		}
}
