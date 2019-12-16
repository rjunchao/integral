package com.xbkj.common.jdbc.framework;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.pub.xbkj.pubapp.generator.SequenceGenerator;
import com.xbkj.basic.vo.pub.BeanHelper;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.mapping.AttributeMapping;
import com.xbkj.common.jdbc.framework.mapping.BeanMapping;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.mapping.MappingMetaManager;
import com.xbkj.common.jdbc.framework.processor.BaseProcessor;
import com.xbkj.common.jdbc.framework.processor.BeanListProcessor;
import com.xbkj.common.jdbc.framework.processor.BeanMappingListProcessor;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;
import com.xbkj.common.jdbc.framework.util.DBConsts;
import com.xbkj.common.jdbc.framework.util.DBUtil;
import com.xbkj.common.jdbc.framework.util.SQLHelper;
import com.xbkj.log.bs.logging.Logger;

/**
 * User: ����<br>
 * Date: 2005-6-24 Time: 10:56:48<br>
 * ����־û�������
 */
public class JdbcPersistenceManager extends PersistenceManager {
	// ��ݿ�Ự
	JdbcSession session;

	// ���Դ���
	String dataSource = null;

	private DatabaseMetaData dbmd = null;

	private static Map cache = new ConcurrentHashMap();;

	/**
	 * �޲����캯��
	 * 
	 * @throws DbException
	 */
	protected JdbcPersistenceManager() throws DbException {
		init();
	}

	/**
	 * �в����캯��
	 * 
	 * @param dataSource
	 *            ���Դ���
	 * @throws DbException
	 *             ��������ӷ���������׳��쳣
	 */
	protected JdbcPersistenceManager(String dataSource) throws DbException {
		this.dataSource = dataSource;
		init();
	}

	protected JdbcPersistenceManager(JdbcSession session) {
		session.setMaxRows(maxRows);
		this.session = session;
	}

	/**
	 * �õ�JdbcSession
	 * 
	 * @return ����JdbcSession
	 */
	public JdbcSession getJdbcSession() {
		return session;
	}

	/**
	 * �ͷ���Դ
	 */
	public void release() {
		if (dbmd != null)
			dbmd = null;
		if (session != null) {
			session.closeAll();
			session = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.jdbc.framework.ee#insertWithPK(nc.vo.pub.SuperVO)
	 */
	public String insertWithPK(final SuperVO vo) throws DbException {
		String pk[] = insertWithPK(new SuperVO[] { vo });
		return pk[0];
	}

	/**
	 * ��һ��ֵ������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ����
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	public String insert(final SuperVO vo) throws DbException {
		String pk[] = insert(new SuperVO[] { vo });
		return pk[0];
	}

	/**
	 * ��һ��ֵ���󼯺ϲ��뵽��ݿ���
	 * 
	 * @param vos
	 *            ֵ���󼯺�
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	public String[] insertWithPK(final List vos) throws DbException {
		return insertWithPK((SuperVO[]) vos.toArray(new SuperVO[] {}));
	}

	/**
	 * ��һ��ֵ���󼯺ϲ��뵽��ݿ���
	 * 
	 * @param vos
	 *            ֵ���󼯺�
	 * @throws DbException
	 *             ���������з���������׳��쳣
	 */
	public String[] insert(final List vos) throws DbException {

		return insert((SuperVO[]) vos.toArray(new SuperVO[] {}));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.jdbc.framework.ee#insertWithPK(nc.vo.pub.SuperVO[])
	 */
	public String[] insertWithPK(final SuperVO vos[]) throws DbException {
		return insert(vos, true);

	}

	/**
	 * ��һ��ֵ����������뵽��ݿ���
	 * 
	 * @param vos
	 *            ֵ�������
	 * @throws DAOException
	 *             ���������з���������׳��쳣
	 */
	public String[] insert(final SuperVO vos[]) throws DbException {
		return insert(vos, false);
	}

//	private String[] preparePK(final SuperVO vos[], boolean withPK) {
//		String corpPk = (String) vos[0].getAttributeValue("pk_corp");
//		if (corpPk == null || (corpPk = corpPk.trim()).equals("")
//				|| corpPk.equals("null"))
//			corpPk = SQLHelper.getCorpPk();
//		if (withPK) {
//			String[] pks = new String[vos.length];
//			int[] idx = new int[vos.length];
//			int length = 0;
//			for (int i = 0; i < vos.length; i++) {
//				if (vos[i] == null) {
//					continue;
//				} else {
//					String thePK = vos[i].getPrimaryKey();
//					if (thePK == null || thePK.trim().length() == 0) {
//						idx[length++] = i;
//					} else {
//						pks[i] = thePK;
//					}
//				}
//			}
//
//			if (length > 0) {
//				String[] npks = new SequenceGenerator(dataSource).generate(
//						corpPk, length);
//				for (int i = 0; i < length; i++) {
//					vos[idx[i]].setPrimaryKey(npks[i]);
//					pks[idx[i]] = npks[i];
//				}
//			}
//			return pks;
//
//		} else {
//			String[] pks = new SequenceGenerator(dataSource).generate(corpPk,
//					vos.length);
//			for (int i = 0; i < vos.length; i++) {
//				if (vos[i] != null) {
//					vos[i].setPrimaryKey(pks[i]);
//				} else {
//					pks[i] = null;
//				}
//			}
//			return pks;
//		}
//	}
	
	//swf修改于20150716
	private String[] preparePK(final SuperVO vos[], boolean withPK) {
		String corpPk = (String) vos[0].getAttributeValue("pk_corp");
//		if (corpPk == null || (corpPk = corpPk.trim()).equals("")
//				|| corpPk.equals("null"))
//			corpPk = SQLHelper.getCorpPk();
		corpPk = "11111111111111111111";
		if (withPK) {
			String[] pks = new String[vos.length];
			int[] idx = new int[vos.length];
			int length = 0;
			for (int i = 0; i < vos.length; i++) {
				if (vos[i] == null) {
					continue;
				} else {
					String thePK = vos[i].getPrimaryKey();
					if (thePK == null || thePK.trim().length() == 0) {
						idx[length++] = i;
					} else {
						pks[i] = thePK;
					}
				}
			}

			if (length > 0) {
				String[] npks = new SequenceGenerator(dataSource).generate(
						corpPk, length);
				for (int i = 0; i < length; i++) {
					vos[idx[i]].setPrimaryKey(npks[i]);
					pks[idx[i]] = npks[i];
				}
			}
			return pks;

		} else {
			String[] pks = new SequenceGenerator(dataSource).generate(corpPk,vos.length);
			for (int i = 0; i < vos.length; i++) {
				if (vos[i] != null) {
					vos[i].setPrimaryKey(pks[i]);
				} else {
					pks[i] = null;
				}
			}
			return pks;
		}
	}

	@SuppressWarnings("unchecked")
	protected String[] insert(final SuperVO vos[], boolean withPK)
			throws DbException {
		isNull(vos);
		if (vos.length == 0) {
			return new String[0];
		}
		String[] pks = null;
		try {
			String tableName = vos[0].getTableName();

			Map types = getColmnTypes(tableName);

			String names[] = getValidNames(vos[0], types);

			String sql = SQLHelper.getInsertSQL(tableName, names);
			//swf修改于20150716
			pks = preparePK(vos, withPK);
			//修改结束

			if (vos.length == 1) {
				SQLParameter parameter = getSQLParam(vos[0], names, types);
				session.executeUpdate(sql, parameter);
			} else {
				for (int i = 0; i < vos.length; i++) {
					if (vos[i] == null)
						continue;
					SQLParameter parameter = getSQLParam(vos[i], names, types);
					session.addBatch(sql, parameter);
				}
				session.executeBatch();
			}

		} finally {

		}
		return pks;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.jdbc.framework.ee#insertObjectWithPK(java.lang.Object,
	 * nc.jdbc.framework.mapping.IMappingMeta)
	 */
	public String insertObjectWithPK(final Object vo, IMappingMeta meta)
			throws DbException {
		return insertObjectWithPK(new Object[] { vo }, meta)[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nc.jdbc.framework.ee#insertObjectWithPK(java.lang.Object[],
	 * nc.jdbc.framework.mapping.IMappingMeta)
	 */
	public String[] insertObjectWithPK(final Object vo[], IMappingMeta meta)
			throws DbException {
		return insertObject(vo, meta, true);
	}

	public String insertObject(final Object vo, IMappingMeta meta)
			throws DbException {
		return insertObject(new Object[] { vo }, meta)[0];
	}

	/**
	 * ��һ��ֵ����������뵽��ݿ���
	 * 
	 * @param vo
	 *            ֵ�������
	 * @throws DAOException
	 *             ���������з���������׳��쳣
	 */
	public String[] insertObject(final Object vo[], IMappingMeta meta)
			throws DbException {
		return insertObject(vo, meta, false);
	}

	protected String[] insertObject(final Object vo[], IMappingMeta meta,
			boolean withPK) throws DbException {
		isNull(vo);
		if (vo.length == 0)
			return new String[0];
		String[] pk;
		AttributeMapping map = MappingMetaManager.getMapingMeta(meta);
		// �õ�����
		String tableName = meta.getTableName();
		// �õ���˾����
		String corpPk = SQLHelper.getCorpPk();
		pk = new SequenceGenerator(dataSource).generate(corpPk, vo.length);
		Map types = getColmnTypes(tableName);
		// �õ������SQL���
		// String sql = SQLHelper.getInsertSQL(tableName, names);
		// ѭ������VO����
		for (int i = 0; i < vo.length; i++) {
			if (vo[i] == null)
				continue;
			String beanPkName = map.getAttributeName(meta.getPrimaryKey())
					.toLowerCase();
			if (withPK) {// if vo has pk
				String thePK = (String) BeanHelper.getProperty(vo[i],
						beanPkName);
				// BeanHelper.setProperty(vo[i], beanPkName, pk[i]);
				if (thePK == null || thePK.trim().length() == 0) {
					BeanHelper.setProperty(vo[i], beanPkName, pk[i]);
				} else {
					pk[i] = thePK;
				}
			} else {
				BeanHelper.setProperty(vo[i], beanPkName, pk[i]);
			}
			BeanMapping mapping = new BeanMapping(vo[i], meta);
			if (types != null)
				mapping.setType(types);
			SQLParameter parameter = mapping.getInsertParamter();
			session.addBatch(mapping.getInsertSQL(), parameter);
		}
		session.executeBatch();
		return pk;
	}

	/**
	 * ����һ������ݿ����Ѿ�����ֵ����
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public int update(final SuperVO vo) throws DbException {
		if (vo == null) {
			throw new IllegalArgumentException("ֵ�������Ϊ��");
		}
		return update(new SuperVO[] { vo }, null);
	}

	public int update(final List vos) throws DbException {
		return update((SuperVO[]) vos.toArray(new SuperVO[] {}), null);

	}

	public int update(final SuperVO[] vo) throws DbException {
		return update(vo, null);

	}

	public int update(final SuperVO[] vo, String[] fieldNames)
			throws DbException {
		return update(vo, fieldNames, null, null);
	}

	@Override
	public int update(final SuperVO[] vo, String[] fieldNames,
			String whereClause, SQLParameter param) throws DbException {
		isNull(vo);
		if (vo.length == 0)
			return 0;
		int row = 0;

		// session.setAddTimeStamp(false);
		// �õ�����
		String tableName = vo[0].getTableName();
		String pkName = vo[0].getPKFieldName();
		// �õ��汾
		// UFDateTime ts = new UFDateTime(new SystemTsGenerator().generateTS());
		String[] names;
		Map<String, Integer> types = getColmnTypes(tableName);
		if (fieldNames != null) {
			names = fieldNames; // ָ�������ֶ�
		} else { // �õ������ֶ����͵��б�
			// �õ��Ϸ����ֶ��б�
			names = getUpdateValidNames(vo[0], types, pkName);
		}
		// �õ������SQL���
		String sql = SQLHelper.getUpdateSQL(tableName, names, pkName);
		if (vo.length == 1) {
			SQLParameter parameter = getSQLParam(vo[0], names, types);
			parameter.addParam(vo[0].getPrimaryKey());
			if (whereClause == null)
				row = session.executeUpdate(sql, parameter);
			else {
				addParameter(parameter, param);
				row = session.executeUpdate(sql + " and " + whereClause,
						parameter);
			}
		} else {
			for (int i = 0; i < vo.length; i++) {
				if (vo[i] == null)
					continue;
				// vo[i].setAttributeValue("ts", ts);
				SQLParameter parameter = getSQLParam(vo[i], names, types);
				parameter.addParam(vo[i].getPrimaryKey());
				if (whereClause == null)
					session.addBatch(sql, parameter);
				else {
					addParameter(parameter, param);
					session.addBatch(sql + " and " + whereClause, parameter);
				}
			}
			row = session.executeBatch();
		}
		return row;
	}

	public int updateObject(final Object vo, IMappingMeta meta)
			throws DbException {
		return updateObject(new Object[] { vo }, meta);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#updateObject(java.lang.Object,
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String)
	 */
	public int updateObject(final Object vo, IMappingMeta meta,
			String whereClause) throws DbException {
		return updateObject(new Object[] { vo }, meta, whereClause);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#updateObject(java.lang.Object[],
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String)
	 */
	public int updateObject(final Object[] vo, IMappingMeta meta,
			String whereClause) throws DbException {
		return updateObject(vo, meta, whereClause, null);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#updateObject(java.lang.Object[],
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String)
	 */
	public int updateObject(final Object[] vo, IMappingMeta meta,
			String whereClause, SQLParameter param) throws DbException {

		isNull(vo);
		if (vo.length == 0)
			return 0;

		// �õ�����
		String tableName = meta.getTableName();
		Map<String, Integer> types = getColmnTypes(tableName);
		if (vo.length == 1) {
			if (vo[0] == null)
				return -1;
			BeanMapping mapping = new BeanMapping(vo[0], meta);
			if (types != null)
				mapping.setType(types);
			SQLParameter parameter = mapping.getUpdateParamter();
			if (whereClause == null) {
				if (mapping.isNullPK())
					return -1;
				return session.executeUpdate(mapping.getUpdateSQL(), parameter);
			} else {
				// �ϲ�����
				addParameter(parameter, param);
				if (mapping.isNullPK())
					return session.executeUpdate(mapping.getUpdateSQL()
							+ " WHERE " + whereClause, parameter);
				else
					return session.executeUpdate(mapping.getUpdateSQL()
							+ " AND " + whereClause, parameter);
			}
		}
		for (int i = 0; i < vo.length; i++) {
			if (vo[i] == null)
				continue;
			BeanMapping mapping = new BeanMapping(vo[i], meta);
			if (types != null)
				mapping.setType(types);
			SQLParameter parameter = mapping.getUpdateParamter();
			if (whereClause == null) {
				if (mapping.isNullPK())
					return -1;
				session.addBatch(mapping.getUpdateSQL(), parameter);
			} else {
				// �ϲ�����
				addParameter(parameter, param);
				if (mapping.isNullPK())
					session.addBatch(mapping.getUpdateSQL() + " WHERE "
							+ whereClause, parameter);
				else
					session.addBatch(mapping.getUpdateSQL() + " AND "
							+ whereClause, parameter);
			}
		}
		return session.executeBatch();
	}

	private void addParameter(SQLParameter parameter, SQLParameter addParams) {
		if (addParams != null)
			for (int i = 0; i < addParams.getCountParams(); i++) {
				parameter.addParam(addParams.get(i));
			}
	}

	/**
     * 
     */
	public int updateObject(final Object[] vo, IMappingMeta meta)
			throws DbException {
		return updateObject(vo, meta, null);
	}

	public int delete(final List vos) throws DbException {
		isNull(vos);
		return delete((SuperVO[]) vos.toArray(new SuperVO[] {}));
	}

	public int delete(final SuperVO vo) throws DbException {
		isNull(vo);
		return delete(new SuperVO[] { vo });
	}

	public int delete(final SuperVO vo[]) throws DbException {
		isNull(vo);
		if (vo.length == 0)
			return 0;
		// �õ�����
		String sql = SQLHelper.getDeleteByPKSQL(vo[0].getTableName(), vo[0]
				.getPKFieldName());
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < vo.length; i++) {
			if (vo[i] == null)
				continue;
			parameter.addParam(vo[i].getPrimaryKey());
			session.addBatch(sql, parameter);
			parameter.clearParams();
		}
		return session.executeBatch();
	}

	public void deleteObject(final Object vo, IMappingMeta meta)
			throws DbException {
		deleteObject(new Object[] { vo }, meta);
	}

	public void deleteObject(final Object vos[], IMappingMeta meta)
			throws DbException {
		isNull(vos);
		if (vos.length == 0)
			return;
		for (int i = 0; i < vos.length; i++) {
			if (vos[i] == null)
				continue;
			BeanMapping mapping = new BeanMapping(vos[i], meta);
			SQLParameter parameter = mapping.getDeleteParamter();
			session.addBatch(mapping.getDeleteSQL(), parameter);
		}
		session.executeBatch();

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see
	 * nc.jdbc.framework.ee#deleteByPK(nc.jdbc.framework.mapping.IMappingMeta,
	 * java.lang.String)
	 */
	public int deleteByPK(IMappingMeta meta, String pk) throws DbException {
		return deleteByPKs(meta, new String[] { pk });
	}

	/**
	 * 
	 * @param meta
	 * @param pks
	 * @return
	 * @throws DbException
	 * 
	 *             modified by cch
	 */
	public int deleteByPKs(IMappingMeta meta, String[] pks) throws DbException {
		String sql = "DELETE FROM " + meta.getTableName() + " WHERE "
				+ meta.getPrimaryKey() + "=?";
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < pks.length; i++) {
			parameter.addParam(pks[i]);
			session.addBatch(sql, parameter);
			parameter.clearParams();
		}
		return session.executeBatch();
	}

	/**
     * 
     */
	public int deleteByPK(Class className, String pk) throws DbException {
		return deleteByPKs(className, new String[] { pk });
	}

	/**
     * 
     */
	public int deleteByPKs(Class className, String[] pks) throws DbException {
		SuperVO supervo = initSuperVOClass(className);
		String sql = "DELETE FROM " + supervo.getTableName() + " WHERE "
				+ supervo.getPKFieldName() + "=?";
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < pks.length; i++) {
			parameter.addParam(pks[i]);
			session.addBatch(sql, parameter);
			parameter.clearParams();
		}
		return session.executeBatch();
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see
	 * nc.jdbc.framework.ee#deleteByClause(nc.jdbc.framework.mapping.IMappingMeta
	 * , java.lang.String)
	 */
	public int deleteByClause(IMappingMeta meta, String wherestr)
			throws DbException {
		return deleteByClause(meta, wherestr, null);
	}

	/**
	 * @param className
	 * @param wherestr
	 * @return
	 * @throws DbException
	 */
	public int deleteByClause(Class className, String wherestr)
			throws DbException {
		return deleteByClause(className, wherestr, null);

	}

	public int deleteByClause(Class className, String wherestr,
			SQLParameter params) throws DbException {
		SuperVO supervo = initSuperVOClass(className);
		String sql = new StringBuffer().append("DELETE FROM ").append(
				supervo.getTableName()).toString();
		if (wherestr != null) {
			wherestr = wherestr.trim();
			if (wherestr.length() > 0) {
				if (wherestr.toLowerCase().startsWith("WHERE"))
					wherestr = wherestr.substring(5);
				if (wherestr.length() > 0)
					sql = sql + " WHERE " + wherestr;
			}
		}
		if (params == null)
			return session.executeUpdate(sql);
		else
			return session.executeUpdate(sql, params);

	}

	public Collection retrieveByCorp(Class c, String pkCorp) throws DbException {
		return retrieveByCorp(c, pkCorp, null);
	}

	public Collection retrieveByCorp(Class c, String pkCorp,
			String[] selectedFields) throws DbException {

		if (pkCorp.equals("0001") || pkCorp.equals("@@@@")) {
			SQLParameter param = new SQLParameter();
			param.addParam("0001");
			param.addParam("@@@@");
			return retrieveByClause(c, "pk_corp=? or pk_corp=?",
					selectedFields, param);
		} else {
			SQLParameter param = new SQLParameter();
			param.addParam(pkCorp);
			return retrieveByClause(c, "pk_corp=?", selectedFields, param);
		}
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#retrieveByCorp(java.lang.Class,
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String)
	 */
	public Collection retrieveByCorp(Class c, IMappingMeta meta, String pkCorp)
			throws DbException {
		return retrieveByCorp(c, meta, pkCorp, null);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#retrieveByCorp(java.lang.Class,
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String,
	 * java.lang.String[])
	 */
	public Collection retrieveByCorp(Class c, IMappingMeta meta, String pkCorp,
			String[] selectedFields) throws DbException {
		if (pkCorp.equals("0001") || pkCorp.equals("@@@@")) {
			SQLParameter param = new SQLParameter();
			param.addParam("0001");
			param.addParam("@@@@");
			return retrieveByClause(c, meta, "pk_corp=? or pk_corp=?",
					selectedFields, param);
		} else {
			SQLParameter param = new SQLParameter();
			param.addParam(pkCorp);
			return retrieveByClause(c, meta, "pk_corp=?", selectedFields, param);
		}
	}

	/**
     * 
     */
	public Object retrieveByPK(Class className, String pk) throws DbException {
		return retrieveByPK(className, pk, null);
	}

	/**
     * 
     * 
     */
	public Object retrieveByPK(Class className, String pk,
			String[] selectedFields) throws DbException {
		SuperVO vo = initSuperVOClass(className);
		if (pk == null)
			throw new IllegalArgumentException("pk is null");
		SQLParameter param = new SQLParameter();
		param.addParam(pk.trim());
		List results = (List) retrieveByClause(className, vo.getPKFieldName()
				+ "=?", selectedFields, param);
		if (results.size() >= 1)
			return results.get(0);
		return null;

	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#retrieveByPK(java.lang.Class,
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String)
	 */
	public Object retrieveByPK(Class className, IMappingMeta meta, String pk)
			throws DbException {
		return retrieveByPK(className, meta, pk, null);
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.jdbc.framework.ee#retrieveByPK(java.lang.Class,
	 * nc.jdbc.framework.mapping.IMappingMeta, java.lang.String,
	 * java.lang.String[])
	 */
	public Object retrieveByPK(Class className, IMappingMeta meta, String pk,
			String[] selectedFields) throws DbException {
		if (pk == null)
			throw new IllegalArgumentException("pk is null");
		SQLParameter param = new SQLParameter();
		param.addParam(pk.trim());
		List results = (List) retrieveByClause(className, meta, meta
				.getPrimaryKey()
				+ "=?", selectedFields, param);
		if (results.size() >= 1)
			return results.get(0);
		return null;
	}

	/**
     * 
     */
	public Collection retrieve(SuperVO vo, boolean isAnd) throws DbException {
		return retrieve(vo, isAnd, null);

	}

	/**
     * 
     */
	public Collection retrieve(Object vo, IMappingMeta meta) throws DbException {
		isNull(vo);
		BeanMapping mapping = new BeanMapping(vo, meta);
		// �õ������SQL���
		String sql = mapping.getSelectwithParamSQL();
		SQLParameter param = mapping.getSelectParameter();
		// session.setReadOnly(true);
		return (Collection) session.executeQuery(sql, param,
				new BeanMappingListProcessor(vo.getClass(), meta));

	}

	/**
     * 
     */
	public Collection retrieve(SuperVO vo, boolean isAnd, String[] fields)
			throws DbException {
		isNull(vo);
		String tableName = vo.getTableName();
		// �õ������ֶ����͵��б�
		Map types = getColmnTypes(tableName);
		// �õ��Ϸ����ֶ��б�
		String names[] = getNotNullValidNames(vo, types);
		// �õ������SQL���
		String sql = SQLHelper.getSelectSQL(tableName, names, isAnd, fields);
		SQLParameter param = getSQLParam(vo, names);
		// session.setReadOnly(true);
		return (Collection) session.executeQuery(sql, param,
				new BeanListProcessor(vo.getClass()));

	}

	public Object retrieve(SuperVO vo, boolean isAnd, String[] fields,
			ResultSetProcessor processor) throws DbException {
		isNull(vo);
		String tableName = vo.getTableName();
		// �õ������ֶ����͵��б�
		Map types = getColmnTypes(tableName);
		// �õ��Ϸ����ֶ��б�
		String names[] = getNotNullValidNames(vo, types);
		// �õ������SQL���
		String sql = SQLHelper.getSelectSQL(tableName, names, isAnd, fields);
		SQLParameter param = getSQLParam(vo, names);
		// session.setReadOnly(true);
		return session.executeQuery(sql, param, processor);
	}

	/**
	 * @param className
	 * @param meta
	 * @return
	 * @throws DbException
	 */
	public Collection retrieveAll(Class className, IMappingMeta meta)
			throws DbException {
		Object vo = InitClass(className);
		BeanMapping mapping = new BeanMapping(vo, meta);
		// session.setReadOnly(true);
		return (Collection) session.executeQuery(mapping.getSelectSQL(),
				new BeanMappingListProcessor(className, meta));

	}

	/**
     * 
     */
	public Collection retrieveAll(Class className) throws DbException {

		SuperVO vo = initSuperVOClass(className);
		String tableName = vo.getTableName();
		String sql = "SELECT * FROM " + tableName;
		// session.setReadOnly(true);
		return (Collection) session.executeQuery(sql, new BeanListProcessor(
				className));

	}

	/**
     * 
     */
	public Collection retrieveByClause(Class className, String condition)
			throws DbException {
		return retrieveByClause(className, condition, null);
	}

	public Collection retrieveByClause(Class className, String condition,
			String[] fields, SQLParameter parameters) throws DbException {
		BaseProcessor processor = new BeanListProcessor(className);
		return (Collection) session.executeQuery(buildSql(className, condition,
				fields), parameters, processor);

	}

	public Collection retrieveByClause(Class className, String condition,
			String[] fields) throws DbException {
		return retrieveByClause(className, condition, fields, null);
	}

	/**
	 * @param className
	 * @param meta
	 * @param condition
	 * @param fields
	 * @return
	 * @throws DbException
	 */
	public Collection retrieveByClause(Class className, IMappingMeta meta,
			String condition, String[] fields) throws DbException {
		return retrieveByClause(className, meta, condition, fields, null);
	}

	public int getDBType() {
		return session.getDbType();
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public DatabaseMetaData getMetaData() {
		if (dbmd == null)
			dbmd = getJdbcSession().getMetaData();
		return dbmd;
	}

	/**
	 * @param isAddTimeStamp
	 */
	public void setAddTimeStamp(boolean isAddTimeStamp) {
		session.setAddTimeStamp(isAddTimeStamp);
	}

	/**
	 * @param isTranslator
	 */
	public void setSQLTranslator(boolean isTranslator) {
		session.setSQLTranslator(isTranslator);
	}

	public String getCatalog() {
		String catalog = null;
		switch (getDBType()) {
		case DBConsts.SQLSERVER:
		case DBConsts.DB2:
			// null means drop catalog name from the selection criteria
			catalog = null;
			break;
		case DBConsts.ORACLE:
			// "" retrieves those without a catalog
			catalog = "";
			break;
		}
		return catalog;
	}

	public String getSchema() {
		String strSche = null;
		try {
			String schema = getMetaData().getUserName();
			switch (getDBType()) {
			case DBConsts.SQLSERVER:
				strSche = "dbo";
				break;
			case DBConsts.ORACLE:
			case DBConsts.DB2: {
				if (schema == null || schema.length() == 0)
					throw new IllegalArgumentException("ORACLE��ݿ�ģʽ������Ϊ��");
				// ORACLE�轫ģʽ���д
				strSche = schema.toUpperCase();
				break;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strSche;
	}

	public void setReadOnly(boolean isReadOnly) throws DbException {

		session.setReadOnly(isReadOnly);
	}

	/**
	 * ��ʼ����ݿ�Ự����
	 * 
	 * @throws DbException
	 */
	private void init() throws DbException {
		if (dataSource == null)
			session = new JdbcSession();
		else
			session = new JdbcSession(dataSource);

		session.setMaxRows(maxRows);
	}

	private void isNull(Object vo) {
		if (vo == null) {
			throw new IllegalArgumentException("ֵ�������Ϊ��");
		}
	}

	public void setMaxRows(int maxRows) {
		super.setMaxRows(maxRows);
		session.setMaxRows(maxRows);
	}

	/**
	 * @param className
	 * @param meta
	 * @param condition
	 * @return
	 * @throws DbException
	 */
	public Collection retrieveByClause(Class className, IMappingMeta meta,
			String condition) throws DbException {
		return retrieveByClause(className, meta, condition, meta.getColumns());
	}

	/**
	 * �õ��������Ͷ���
	 * 
	 * @param vo
	 * @param names
	 * @param types
	 * @return
	 */
	private SQLParameter getSQLParam(SuperVO vo, String names[],
			Map<String, Integer> types) {
		SQLParameter params = new SQLParameter();
		for (int i = 0; i < names.length; i++) {
			/*if (names[i].equalsIgnoreCase("ts")) //修改于2017:07:25 rjc
				continue;*/
			int type = types.get(names[i].toUpperCase());
			Object value = vo.getAttributeValue(names[i]);
			if (value == null) {
				params.addNullParam(type);
				continue;
			}
			if (type == Types.BLOB || type == Types.LONGVARBINARY
					|| type == Types.VARBINARY || type == Types.BINARY) {
				params.addBlobParam(value);
				continue;
			}
			if (type == Types.CLOB || type == Types.LONGVARCHAR) {
				params.addClobParam(String.valueOf(value));
				continue;
			}
			params.addParam(value);

		}
		return params;
	}

	/**
	 * �õ���Ч�������
	 * 
	 * @param vo
	 * @param types
	 * @return
	 */
	private String[] getValidNames(final SuperVO vo, Map types) {
		
		String names[] = vo.getAttributeNames();
		List nameList = new ArrayList();
		
		for (int i = 0; i < names.length; i++) {
			if (types.get(names[i].toUpperCase()) != null
					//&& !names[i].equalsIgnoreCase("ts") //  //修改于2017:07:25 rjc
					) {
				nameList.add(names[i]);
			}
		}
		return (String[]) nameList.toArray(new String[] {});
	}

	/**
	 * �õ���Ч�����
	 * 
	 * @param vo
	 * @param types
	 * @param pkName
	 * @return
	 */
	private String[] getUpdateValidNames(SuperVO vo, Map types, String pkName) {
		String names[] = vo.getAttributeNames();
		List nameList = new ArrayList();
		for (int i = 0; i < names.length; i++) {
			if (types.get(names[i].toUpperCase()) != null
					&& !names[i].equalsIgnoreCase(pkName)
					&& !names[i].equalsIgnoreCase("ts")) {
				nameList.add(names[i]);
			}
		}
		return (String[]) nameList.toArray(new String[] {});
	}

	/**
	 * @param vo
	 * @param type
	 * @return
	 */
	private String[] getNotNullValidNames(SuperVO vo, Map type) {
		String names[] = vo.getAttributeNames();
		List nameList = new ArrayList();
		for (int i = 0; i < names.length; i++) {
			if (type.get(names[i].toUpperCase()) != null
					&& vo.getAttributeValue(names[i]) != null) {
				nameList.add(names[i]);
			}
		}
		if (nameList.size() == 0)
			return null;
		return (String[]) nameList.toArray(new String[] {});
	}

	/**
	 * @param vo
	 * @param names
	 * @return
	 */
	private SQLParameter getSQLParam(SuperVO vo, String[] names) {
		if (names == null || names.length == 0) {
			return null;
		}
		SQLParameter parameter = new SQLParameter();
		for (int i = 0; i < names.length; i++) {
			parameter.addParam(vo.getAttributeValue(names[i]));
		}
		return parameter;
	}

	public Connection getConnection() {
		if (session != null)
			return session.getConnection();
		return null;
	}

	/**
	 * �õ��е�����
	 * 
	 * @param table
	 * @return
	 */
	private Map<String, Integer> getColmnTypes(String table) {
		Map<String, Integer> result = (Map<String, Integer>) cache.get(table);
		if (result == null) {
			Map<String, Integer> typeMap = new HashMap<String, Integer>();
			ResultSet rsColumns = null;
			try {

				if (getDBType() == DBConsts.SQLSERVER)
					rsColumns = getMetaData().getColumns(null, null,
							table.toUpperCase(), "%");
				else if(getDBType() == DBConsts.POSTGRESQL) {
					rsColumns = getMetaData().getColumns(null, null,
							table.toLowerCase(), "%");
				} else {
					rsColumns = getMetaData().getColumns(null, getSchema(),
							table.toUpperCase(), "%");
				}
				while (rsColumns.next()) {
					String columnName = rsColumns.getString("COLUMN_NAME")
							.toUpperCase();
					int columnType = rsColumns.getShort("DATA_TYPE");
					typeMap.put(columnName, columnType);
				}
				cache.put(table, typeMap);
				result = typeMap;
			} catch (SQLException e) {
				Logger.error("get table metadata error", e);
			} finally {
				DBUtil.closeRs(rsColumns);
			}
		}
		return result;
	}

	public static void clearColumnTypes(String table) {
		cache.remove(table);
	}

	private Object InitClass(Class className) {
		try {
			return className.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("����Ĳ������޷�����ʵ��");
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("���벻�Ϸ�����");
		}
	}

	private SuperVO initSuperVOClass(Class className) {
		Object vo;
		try {
			vo = className.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("����Ĳ������޷�����ʵ��");
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("���벻�Ϸ�����");
		}
		if (!(vo instanceof SuperVO))
			throw new IllegalArgumentException("����Ĳ����಻��SuperVO");
		return (SuperVO) vo;
	}

	private String buildSql(Class className, String condition, String[] fields) {
		SuperVO vo = (SuperVO) InitClass(className);
		String pkName = vo.getPKFieldName();
		boolean hasPKField = false;
		StringBuffer buffer = new StringBuffer();
		String tableName = vo.getTableName();
		if (fields == null)
			buffer.append("SELECT * FROM ").append(tableName);
		else {
			buffer.append("SELECT ");
			for (int i = 0; i < fields.length; i++) {
				buffer.append(fields[i]).append(",");
				if (fields[i].equalsIgnoreCase(pkName))
					hasPKField = true;
			}
			if (!hasPKField)
				buffer.append(pkName).append(",");
			buffer.setLength(buffer.length() - 1);
			buffer.append(" FROM ").append(tableName);
		}
		if (condition != null && condition.length() != 0) {
			if (condition.toUpperCase().trim().startsWith("ORDER "))
				buffer.append(" ").append(condition);
			else
				buffer.append(" WHERE ").append(condition);
		}

		return buffer.toString();
	}

	@Override
	public Collection retrieveByClause(Class className, IMappingMeta meta,
			String condition, String[] fields, SQLParameter params)
			throws DbException {
		String sql = SQLHelper.getSelectSQL(meta.getTableName(), fields);
		if (condition != null && condition.length() != 0) {
			if (condition.trim().toUpperCase().startsWith("ORDER "))
				sql = new StringBuffer().append(sql).append(" ").append(
						condition).toString();
			else
				sql = new StringBuffer().append(sql).append(" WHERE ").append(
						condition).toString();
		}
		BaseProcessor processor = new BeanMappingListProcessor(className, meta,
				fields);
		if (params != null) {
			return (Collection) session.executeQuery(sql, params, processor);
		} else {
			return (Collection) session.executeQuery(sql, processor);
		}
	}

	@Override
	public int deleteByClause(IMappingMeta meta, String wherestr,
			SQLParameter params) throws DbException {
		String sql = new StringBuffer().append("DELETE FROM ").append(
				meta.getTableName()).toString();
		if (wherestr != null) {
			wherestr = wherestr.trim();
			if (wherestr.length() > 0) {
				if (wherestr.toLowerCase().startsWith("WHERE"))
					wherestr = wherestr.substring(5);
				if (wherestr.length() > 0)
					sql = sql + " WHERE " + wherestr;
			}
		}
		if (params == null)
			return session.executeUpdate(sql);
		else
			return session.executeUpdate(sql, params);

	}

}
