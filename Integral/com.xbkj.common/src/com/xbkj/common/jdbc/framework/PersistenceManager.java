
package com.xbkj.common.jdbc.framework;

import java.sql.DatabaseMetaData;
import java.util.Collection;
import java.util.List;

import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;

/**
 * @author hey
 *         <p/>
 *         ����־û�������������
 */
abstract public class PersistenceManager {
	protected int maxRows = 100000;

	/**
	 * �ͷ���Դ
	 */

	abstract public void release();

	/**
	 * �õ�JdbcSession
	 * 
	 * @return ����JdbcSession
	 */
	abstract public JdbcSession getJdbcSession();

	/**
	 * ��һ��ֵ������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	abstract public String insert(final SuperVO vo) throws DbException;

	/**
	 * ��һ��ֵ���󼯺ϲ��뵽��ݿ���
	 * 
	 * @param vos
	 *            ֵ���󼯺�
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	abstract public String[] insert(final List vos) throws DbException;

	/**
	 * ��һ��ֵ����������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ��������
	 * @throws DAOException
	 *             ���������з���������׳��쳣
	 */
	abstract public String[] insert(final SuperVO vo[]) throws DbException;

	/**
	 * ����һ������ݿ����Ѿ�����ֵ����
	 * 
	 * @param vo
	 *            ֵ����
	 * @throws DAOException
	 *             ���������з���������׳��쳣
	 */
	abstract public int update(final SuperVO vo) throws DbException;

	abstract public int update(final List vos) throws DbException;

	abstract public int update(final SuperVO vo[]) throws DbException;

	abstract public int update(final SuperVO[] vo, String[] fieldNames)
			throws DbException;

	/**
	 * @since5.5 ����update�������������Ĵ���
	 * @param vo
	 * @param fieldNames
	 * @param whereClause
	 * @param param
	 * @return
	 * @throws DbException
	 */
	abstract public int update(final SuperVO[] vo, String[] fieldNames,
			String whereClause, SQLParameter param) throws DbException;

	/**
	 * ɾ��һ������ݿ����Ѿ�����ֵ����
	 * 
	 * @param vo
	 * @throws DbException
	 */

	abstract public int delete(final SuperVO vo) throws DbException;

	abstract public int delete(final SuperVO vo[]) throws DbException;

	abstract public int delete(final List vos) throws DbException;

	abstract public int deleteByPK(Class className, String pk)
			throws DbException;

	abstract public int deleteByPKs(Class className, String[] pks)
			throws DbException;

	abstract public int deleteByClause(Class className, String wherestr)
			throws DbException;

	abstract public int deleteByClause(Class className, String wherestr,
			SQLParameter params) throws DbException;

	abstract public Collection retrieveByCorp(Class c, String pkCorp,
			String[] selectedFields) throws DbException;

	abstract public Collection retrieveByCorp(Class c, String pkCorp)
			throws DbException;

	abstract public Object retrieveByPK(Class className, String pk)
			throws DbException;

	abstract public Object retrieveByPK(Class className, String pk,
			String[] selectedFields) throws DbException;

	/**
	 * ���VO���ֶ�ֵ������ѯ���
	 * 
	 * @param vo
	 * @return
	 * @throws DbException
	 */

	abstract public Collection retrieve(SuperVO vo, boolean isAnd)
			throws DbException;

	abstract public Collection retrieve(SuperVO vo, boolean isAnd,
			String[] fields) throws DbException;

	abstract public Object retrieve(SuperVO vo, boolean isAnd, String[] fields,
			ResultSetProcessor processor) throws DbException;

	/**
	 * ��ѯVO��Ӧ���������
	 * 
	 * @param className
	 * @return
	 * @throws DbException
	 */

	abstract public Collection retrieveAll(Class className) throws DbException;

	/**
	 * ���������ѯVO��Ӧ�����
	 * 
	 * @param className
	 * @param condition
	 * @return
	 * @throws DbException
	 */
	abstract public Collection retrieveByClause(Class className,
			String condition, String[] fields) throws DbException;

	abstract public Collection retrieveByClause(Class className,
			String condition) throws DbException;

	abstract public int deleteByPKs(IMappingMeta meta, String[] pks)
			throws DbException;

	/**
	 * �õ���ݿ�����
	 * 
	 * @return
	 */

	abstract public int getDBType();

	/**
	 * �Ƿ��Զ����ʱ���
	 * 
	 * @param isAddTimeStamp
	 */
	abstract public void setAddTimeStamp(boolean isAddTimeStamp);

	/**
	 * �Ƿ����SQL����
	 * 
	 * @param isTranslator
	 */
	abstract public void setSQLTranslator(boolean isTranslator);

	/**
	 * ��һ��ֵ����������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ�������
	 * @throws DAOException
	 *             ���������з���������׳��쳣
	 */
	public abstract String[] insertObject(final Object vo[], IMappingMeta meta)
			throws DbException;

	public abstract String[] insertWithPK(final List vos) throws DbException;

	/**
	 * ��һ��ֵ������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	public abstract String insertWithPK(final SuperVO vo) throws DbException;

	public abstract String[] insertWithPK(final SuperVO vos[])
			throws DbException;

	public abstract String insertObjectWithPK(final Object vo, IMappingMeta meta)
			throws DbException;

	public abstract String[] insertObjectWithPK(final Object vo[],
			IMappingMeta meta) throws DbException;

	/**
	 * ��һ��ֵ������MappingMeta����Ϣ���뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @return
	 * @throws DbException
	 *             ��������ݳ������׳�DbException
	 */
	public abstract String insertObject(final Object vo, IMappingMeta meta)
			throws DbException;

	/**
	 * ��һ��ֵ����������MappingMeta����Ϣ���뵽��ݿ���
	 * 
	 * @param vos
	 *            ֵ��������
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @return ���ظ�������
	 * @throws DbException
	 *             ��������ݳ������׳�DbException
	 */
	public abstract int updateObject(final Object[] vos, IMappingMeta meta)
			throws DbException;

	/**
	 * ��һ��ֵ����������MappingMeta����Ϣ���µ���ݿ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @return ���ظ�������
	 * @throws DbException
	 *             ���������ݳ������׳�DbException
	 */
	public abstract int updateObject(final Object vo, IMappingMeta meta)
			throws DbException;

	public abstract int updateObject(final Object vo, IMappingMeta meta,
			String whereClause) throws DbException;

	public abstract int updateObject(final Object[] vo, IMappingMeta meta,
			String whereClause) throws DbException;

	/**
	 * @since 5.5���������ĸ��´���
	 * @param vo
	 * @param meta
	 * @param whereClause
	 * @param param
	 * @return
	 * @throws DbException
	 */
	public abstract int updateObject(final Object[] vo, IMappingMeta meta,
			String whereClause, SQLParameter param) throws DbException;

	/**
	 * ���MappingMeta��Ϣɾ��ֵ���������Ӧ�����
	 * 
	 * @param vos
	 *            ֵ��������
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @throws DbException
	 *             ���ɾ����ݳ������׳�DbException
	 */
	public abstract void deleteObject(final Object vos[], IMappingMeta meta)
			throws DbException;

	/**
	 * ���MappingMeta��Ϣɾ��ֵ�����Ӧ�����
	 * 
	 * @param vo
	 *            ֵ������
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @throws DbException
	 *             ���ɾ����ݳ������׳�DbException
	 */
	public abstract void deleteObject(final Object vo, IMappingMeta meta)
			throws DbException;

	/**
	 * ���ֵ���������ֵΪ������ѯ��Ӧ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @return
	 * @throws DbException
	 *             ����ѯ�������׳�DbException
	 */
	public abstract Collection retrieve(Object vo, IMappingMeta meta)
			throws DbException;

	/**
	 * ������ӳ����Ϣ��ѯ��Ӧ���������
	 * 
	 * @param className
	 *            ������
	 * @param meta
	 *            ���ӳ����Ϣ
	 * @return
	 * @throws DbException
	 *             ����ѯ�������׳�DbException
	 */
	public abstract Collection retrieveAll(Class className, IMappingMeta meta)
			throws DbException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition, String[] fields)
			throws DbException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition) throws DbException;

	public abstract Collection retrieveByClause(Class className,
			String condition, String[] fields, SQLParameter parameters)
			throws DbException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition, String[] fields,
			SQLParameter params) throws DbException;

	/**
	 * �õ���ǰ��ݿ��DatabaseMetaData
	 * 
	 * @return DatabaseMetaData
	 */
	public abstract DatabaseMetaData getMetaData();

	/**
	 * �õ���ǰ��ݿ��Catalog
	 * 
	 * @return Catalog���
	 */
	public abstract String getCatalog();

	/**
	 * �õ���ǰ��ݿ��Schema
	 * 
	 * @return Schema���
	 */
	public abstract String getSchema();

	/**
	 * ���õ�ǰ����Ϊֻ��
	 * 
	 * @param isReadOnly
	 *            �Ƿ�ֻ��
	 * @throws DbException
	 *             ������ó������׳�DbException
	 */
	public abstract void setReadOnly(boolean isReadOnly) throws DbException;

	/**
	 * 
	 */
	public abstract int deleteByPK(IMappingMeta meta, String pk)
			throws DbException;

	public abstract int deleteByClause(IMappingMeta meta, String wherestr)
			throws DbException;

	public abstract int deleteByClause(IMappingMeta meta, String wherestr,
			SQLParameter params) throws DbException;

	public abstract Collection retrieveByCorp(Class c, IMappingMeta meta,
			String pkCorp) throws DbException;

	public abstract Collection retrieveByCorp(Class c, IMappingMeta meta,
			String pkCorp, String[] selectedFields) throws DbException;

	public abstract Object retrieveByPK(Class className, IMappingMeta meta,
			String pk) throws DbException;

	public abstract Object retrieveByPK(Class className, IMappingMeta meta,
			String pk, String[] selectedFields) throws DbException;

	/**
	 * ���Ĭ�����Դ�õ�PersistenceManagerʵ��
	 * 
	 * @return
	 * @throws DbException
	 *             ���������׳�DbException
	 */
	static public PersistenceManager getInstance() throws DbException {
		return new JdbcPersistenceManager();
	}

	/**
	 * ��ݴ�������Դ����õ�PersistenceManagerʵ��
	 * 
	 * @param dataSourceName
	 *            ���Դ���
	 * @return
	 * @throws DbException
	 *             ���������׳�DbException
	 */
	static public PersistenceManager getInstance(String dataSourceName)
			throws DbException {
		return new JdbcPersistenceManager(dataSourceName);

	}

	/**
	 * ��ݴ����JdbcSession����õ�PersistenceManagerʵ��
	 * 
	 * @param session
	 *            JdbcSession����
	 * @return
	 * @throws DbException
	 *             ���������׳�DbException
	 */
	static public PersistenceManager getInstance(JdbcSession session) {
		return new JdbcPersistenceManager(session);

	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

}