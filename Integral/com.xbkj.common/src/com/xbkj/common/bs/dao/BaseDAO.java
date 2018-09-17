package com.xbkj.common.bs.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.query.DBTool;
import com.xbkj.basic.vo.pub.SqlSupportVO;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.basic.vo.pub.UpdateResultVO;
import com.xbkj.common.itf.uap.IUAPQueryBS;
import com.xbkj.common.itf.uap.IVOPersistence;
import com.xbkj.common.jdbc.framework.JdbcSession;
import com.xbkj.common.jdbc.framework.PersistenceManager;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.mapping.MappingMeta;
import com.xbkj.common.jdbc.framework.processor.BaseProcessor;
import com.xbkj.common.jdbc.framework.processor.BeanMappingListProcessor;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;
import com.xbkj.common.jdbc.framework.util.DBConsts;
import com.xbkj.common.jdbc.framework.util.DBUtil;


final public class BaseDAO implements IVOPersistence, IUAPQueryBS {
	private static Logger logger = TraceLoggerFactory.getLogger(BaseDAO.class);
	private String dataSource = null;

	int maxRows = 100000;

	/**
	 * Ĭ�Ϲ��캯��ʹ�õ�ǰ���Դ
	 */
	public BaseDAO() {
	}

	/**
	 * �вι��캯��ʹ��ָ�����Դ
	 * 
	 * @param dataSource
	 *            ���Դ���
	 */
	public BaseDAO(String dataSource) {
		super();
		this.dataSource = dataSource;
	}

	/**
	 * ���SQL ִ����ݿ��ѯ,������ResultSetProcessor�����Ķ��� ���� Javadoc��
	 * 
	 * @param sql
	 *            ��ѯ��SQL
	 * @param processor
	 *            �������
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, processor);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * ���ָ��SQL ִ���в������ݿ��ѯ,������ResultSetProcessor�����Ķ���
	 * 
	 * @param sql
	 *            ��ѯ��SQL
	 * @param parameter
	 *            ��ѯ����
	 * @param processor
	 *            �������
	 */
	public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor) throws DAOException {
		PersistenceManager manager = null;
		Object value = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			JdbcSession session = manager.getJdbcSession();
			value = session.executeQuery(sql, parameter, processor);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * ���ָ��SQL ִ���в������ݿ���²���
	 * 
	 * @param sql
	 *            ���µ�sql
	 * @param parameter
	 *            ���²���
	 * @return
	 * @throws DAOException
	 *             ���·�������׳�DAOException
	 */
	public int executeUpdate(String sql, SQLParameter parameter) throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql, parameter);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * ���ָ��SQL ִ���޲������ݿ���²���
	 * 
	 * @param sql
	 *            ���µ�sql
	 * @return
	 * @throws DAOException
	 *             ���·�������׳�DAOException
	 */
	public int executeUpdate(String sql) throws DAOException {
		PersistenceManager manager = null;
		int value;
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			value = session.executeUpdate(sql);

		} catch (DbException e) {
//			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return value;
	}

	/**
	 * ���VO�����ѯ��VO��Ӧ����������
	 * 
	 * @param className
	 *            SuperVo����
	 * 
	 * @return
	 * @throws DAOException
	 *             ��������׳�DAOException
	 */
	public Collection retrieveAll(Class className) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveAll(className);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ���VO�����where��������vo����
	 * 
	 * @param className
	 *            Vo�����
	 * @param condition
	 *            ��ѯ����
	 * @return ����Vo����
	 * @throws DAOException
	 *             ��������׳�DAOException
	 */
	public Collection retrieveByClause(Class className, String condition) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByClause(className, condition);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	public Collection retrieveByClause(Class className, String condition, SQLParameter params) throws DAOException {
		return retrieveByClause(className, condition, (String[]) null, params);
	}

	/**
	 * ������������򷵻�Vo����
	 * 
	 * @param className
	 *            VO����
	 * @param condition
	 *            ��ѯ����
	 * @param orderBy
	 *            ��������
	 * @return ����Vo����
	 * @throws DAOException
	 *             ��������׳�DAOException
	 */
	public Collection retrieveByClause(Class className, String condition, String orderBy) throws DAOException {
		return retrieveByClause(className, appendOrderBy(condition, orderBy), (String[]) null, null);
	}

	public Collection retrieveByClause(Class className, String condition, String orderBy, SQLParameter params)
			throws DAOException {
		return retrieveByClause(className, appendOrderBy(condition, orderBy), (String[]) null, params);
	}

	/**
	 * ���VO�����where��������ָ���е�vo����
	 * 
	 * @param className
	 *            �����
	 * @param condition
	 *            ��ѯ����
	 * @param fields
	 *            ָ�����ֶ���
	 * 
	 */
	public Collection retrieveByClause(Class className, String condition, String[] fields) throws DAOException {
		return retrieveByClause(className, condition, fields, null);
	}

	public Collection retrieveByClause(Class className, String condition, String[] fields, SQLParameter params)
			throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByClause(className, condition, fields, params);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ���VO�����where������������������ָ���е�vo����
	 * 
	 * @param className
	 *            ����
	 * @param condition
	 *            ��ѯ����
	 * @param orderBy
	 *            ��������
	 * @param fields
	 *            ָ����
	 * @return
	 * @throws DAOException
	 */
	public Collection retrieveByClause(Class className, String condition, String orderBy, String[] fields)
			throws DAOException {

		return retrieveByClause(className, appendOrderBy(condition, orderBy), fields);
	}

	public Collection retrieveByClause(Class className, String condition, String orderBy, String[] fields,
			SQLParameter params) throws DAOException {
		return retrieveByClause(className, appendOrderBy(condition, orderBy), fields, params);
	}

	private String appendOrderBy(String condition, String orderBy) {
		StringBuffer clause = new StringBuffer();
		if (condition != null)
			clause.append(condition);
		if (orderBy != null && condition == null)
			clause.append("ORDER BY ").append(orderBy);
		if (orderBy != null && condition != null) {
			clause.append(" ORDER BY ").append(orderBy);
		}

		return clause.toString();
	}

	/**
	 * ͨ��where������ѯ��������������vo���顣 ֧�ֶ�� �������ڣ�(2002-6-12)
	 * 
	 * @param c
	 *            Class
	 * @param strWhere
	 *            String
	 * @return SuperVO[]
	 * @throws Exception
	 *             �쳣˵����
	 */
	@SuppressWarnings("unchecked")
	public Object[] retrieveByClause(Class c, SqlSupportVO[] sqlvos, String fromStr, String strWhere, String strOrderBy)
			throws DAOException {
		if (sqlvos == null || sqlvos.length == 0)
			throw new NullPointerException("Sqlvos is null;");
		if (fromStr == null)
			throw new NullPointerException("fromStr is null;");
		String[][] fields = new String[2][sqlvos.length];
		MappingMeta meta = new MappingMeta("", "");
		for (int i = 0; i < sqlvos.length; i++) {
			fields[0][i] = sqlvos[i].getSqlSelectField();
			fields[1][i] = sqlvos[i].getVoAttributeName();
			meta.addMapping(sqlvos[i].getVoAttributeName(), sqlvos[i].getSqlSelectField());
		}
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(this.dataSource);
			JdbcSession session = manager.getJdbcSession();
			StringBuffer sql = new StringBuffer("SELECT ");
			for (int i = 0; i < fields[0].length; i++) {
				sql.append(fields[0][i]);
				if (i != fields[0].length - 1)
					sql.append(",");
			}
			sql.append(" FROM ").append(fromStr);

			// create where sentence
			if (strWhere != null && strWhere.trim().length() != 0) {
				sql.append(" WHERE ").append(strWhere);
			}
			// create order by sentence
			if (strOrderBy != null && strOrderBy.trim().length() != 0) {
				sql.append(" ORDER BY ").append(strOrderBy);
			}
			BaseProcessor processor = new BeanMappingListProcessor(c, meta);
			List result = (List) session.executeQuery(sql.toString(), processor);
			return result.toArray((Object[]) Array.newInstance(c, 0));
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());

		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���VO�е�����ֵ��Ϊƥ����������ݿ��в�ѯ��VO��Ӧ�ı�����
	 * 
	 * @param vo
	 *            Ҫ��ѯ��VO����
	 * @param isAnd
	 *            ָ��ƥ���������߼���true���&&,flase���||��
	 * @return
	 * @throws DAOException
	 *             ����ѯ�����׳�DAOException
	 */
	public Collection retrieve(SuperVO vo, boolean isAnd) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieve(vo, isAnd);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

		return values;
	}

	/**
	 * ���ָ��VO��ֵ�Լ��߼���������ָ���ֶε�VO����
	 * 
	 * @param vo
	 *            ����VO
	 * @param isAnd
	 *            �߼�������true���������false��������
	 * 
	 * 
	 */
	public Collection retrieve(SuperVO vo, boolean isAnd, String[] fields) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieve(vo, isAnd, fields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ��ݹ�˾���ָ���ֶη���VO����
	 * 
	 * @param className
	 *            VO����
	 * @param pkCorp
	 *            ��˾����
	 * @param selectedFields
	 *            ��ѯ�ֶ�
	 * 
	 */
	public Collection retrieveByCorp(Class className, String pkCorp, String[] selectedFields) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);

			values = manager.retrieveByCorp(className, pkCorp, selectedFields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ��ݹ�˾PK����ָ��VO����
	 * 
	 * @param className
	 *            VO���
	 * @param ��˾PK
	 * 
	 */
	public Collection retrieveByCorp(Class className, String pkCorp) throws DAOException {
		PersistenceManager manager = null;
		Collection values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByCorp(className, pkCorp);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ���PK��ѯָ��VO
	 * 
	 * @param VO����
	 * @param pk
	 *            ����
	 * 
	 */
	public Object retrieveByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		Object values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByPK(className, pk);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ��������ָ���е�VO����
	 */
	public Object retrieveByPK(Class className, String pk, String[] selectedFields) throws DAOException {
		PersistenceManager manager = null;
		Object values = null;
		try {
			manager = createPersistenceManager(dataSource);
			values = manager.retrieveByPK(className, pk, selectedFields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return values;
	}

	/**
	 * ����һ��VO��������VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String insertVOWithPK(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		String pk = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			pk = manager.insertWithPK(vo);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * ����һ��VO����
	 * 
	 * @param vo
	 *            SuperVO����
	 * 
	 */
	public String insertVO(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		String pk = null;
		try {
			manager = createPersistenceManager(dataSource);
			pk = manager.insert(vo);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;

	}

	/**
	 * ����һ��VO��������VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public String[] insertVOArrayWithPK(SuperVO[] vo) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			pk = manager.insertWithPK(vo);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * ����VO����
	 * 
	 * @param vo
	 *            VO����
	 */
	public String[] insertVOArray(SuperVO[] vo) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			pk = manager.insert(vo);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * ����VO����
	 * 
	 * @param vos
	 *            VO����
	 */
	public String[] insertVOList(List vos) throws DAOException {
		PersistenceManager manager = null;
		String pk[] = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			pk = manager.insert(vos);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
		return pk;
	}

	/**
	 * ���IMappingMeta����һ��VO���󣬸�VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
	public String insertObjectWithPK(Object vo, IMappingMeta meta) throws DAOException {

		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			return manager.insertObjectWithPK(vo, meta);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���IMappingMeta����һ��VO����
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 */
	public String insertObject(Object vo, IMappingMeta meta) throws DAOException {

		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			return manager.insertObject(vo, meta);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���IMappingMeta����VO���󼯺ϣ���VO������ֵ�ǿ������VO��ԭ������
	 * 
	 * @param vo
	 *            VO���󼯺�
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
	public String[] insertObjectWithPK(Object[] vo, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			;
			return manager.insertObjectWithPK(vo, meta);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���IMappingMeta����VO���󼯺�
	 * 
	 * @param vo
	 *            VO���󼯺�
	 * @param meta
	 *            IMappingMeta
	 * @return
	 * @throws DAOException
	 */
	public String[] insertObject(Object[] vo, IMappingMeta meta) throws DAOException {

		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.insertObject(vo, meta);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���VO���������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 */
	public int updateVO(SuperVO vo) throws DAOException {
		return updateVOArray(new SuperVO[] { vo });
	}

	/**
	 * ���VO������ָ���и�����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param fieldNames
	 *            ָ����
	 * @throws DAOException
	 */
	public void updateVO(SuperVO vo, String[] fieldNames) throws DAOException {
		updateVOArray(new SuperVO[] { vo }, fieldNames);
	}

	/**
	 * ���VO�������������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 */
	public int updateVOArray(SuperVO[] vos) throws DAOException {
		return updateVOArray(vos, null);
	}

	/**
	 * ���VO����������ָ���и�����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param fieldNames
	 *            ָ����
	 */
	public int updateVOArray(SuperVO[] vos, String[] fieldNames) throws DAOException {
		return updateVOArray(vos, fieldNames, null, null);

	}

	/**
	 * ���VO���󼯺ϸ�����ݿ�
	 * 
	 * @paramvos VO���󼯺�
	 */
	public void updateVOList(List vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.update(vos);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���VO�����MappingMeta��Ϣ������ݿ�
	 * 
	 * @param vo
	 *            VO����
	 * @param meta
	 *            MappingMeta��Ϣ
	 */
	public int updateObject(Object vo, IMappingMeta meta) throws DAOException {
		return updateObject(vo, meta, null);
	}

	public int updateObject(Object[] vos, IMappingMeta meta) throws DAOException {
		return updateObject(vos, meta, null);
	}

	/**
	 * ����ݿ���ɾ��һ��VO����
	 * 
	 * @param SuperVO[]
	 *            vos
	 * @throws Exception
	 *             �쳣˵����
	 */
	public UpdateResultVO execUpdateByVoState(SuperVO[] vos, String[] selectedFields) throws DAOException {
		ArrayList<SuperVO> listInsert = new ArrayList<SuperVO>();
		ArrayList<SuperVO> listUpdate = new ArrayList<SuperVO>();
		ArrayList<SuperVO> listDelete = new ArrayList<SuperVO>();
		for (int i = 0; i < vos.length; i++) {
			int status = vos[i].getStatus();
			if (status == com.grc.basic.vo.pub.VOStatus.NEW)
				listInsert.add(vos[i]);
			else if (status == com.grc.basic.vo.pub.VOStatus.UPDATED)
				listUpdate.add(vos[i]);
			else if (status == com.grc.basic.vo.pub.VOStatus.DELETED)
				listDelete.add(vos[i]);
		}
		UpdateResultVO rsVO = new UpdateResultVO();
		if (listInsert.size() > 0) {
			rsVO.setPks(insertVOArray((SuperVO[]) listInsert.toArray(new SuperVO[listInsert.size()])));
		}

		if (listUpdate.size() > 0) {
			updateVOArray((SuperVO[]) listUpdate.toArray(new SuperVO[listUpdate.size()]), selectedFields);
		}
		if (listDelete.size() > 0) {
			deleteVOArray((SuperVO[]) listDelete.toArray(new SuperVO[listDelete.size()]));
		}
		return rsVO;
	}

	/**
	 * ����ݿ���ɾ��һ��VO����
	 * 
	 * @param SuperVO[]
	 *            vos
	 * @throws Exception
	 *             �쳣˵����
	 */
	public UpdateResultVO execUpdateByVoState(SuperVO[] vos) throws DAOException {
		return execUpdateByVoState(vos, null);
	}

	/**
	 * ����ݿ���ɾ��һ��VO����
	 * 
	 * @param vo
	 *            VO����
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteVO(SuperVO vo) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vo);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ����ݿ���ɾ��һ��VO����
	 * 
	 * @param vos
	 *            VO�������
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteVOArray(SuperVO[] vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vos);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ����ݿ��и�������PK����ɾ��һ��VO���󼯺�
	 * 
	 * @param className
	 *            Ҫɾ���VO����
	 * @param pks
	 *            PK����
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteByPKs(Class className, String[] pks) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPKs(className, pks);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ����ݿ��и�����������ɾ�����
	 * 
	 * @param className
	 *            VO����
	 * @param wherestr
	 *            ����
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteByClause(Class className, String wherestr) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByClause(className, wherestr);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	public void deleteByClause(Class className, String wherestr, SQLParameter params) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByClause(className, wherestr, params);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ����ݿ��и�������PKɾ��һ��VO���󼯺�
	 * 
	 * @param className
	 *            VO����
	 * @param pk
	 *            PKֵ
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteByPK(Class className, String pk) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteByPK(className, pk);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ����ݿ���ɾ��һ��VO���󼯺�
	 * 
	 * @param vos
	 *            VO���󼯺�
	 * @throws DAOException
	 *             ���ɾ������׳�DAOException
	 */
	public void deleteVOList(List vos) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.delete(vos);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���VO�е�����ֵ��IMappingMetaɾ��ָ�����
	 * 
	 * @param vo
	 *            vo����
	 * @param meta
	 *            IMappingMeta
	 * 
	 */
	public void deleteObject(Object vo, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteObject(vo, meta);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���VO�����е�����ֵ��IMappingMetaɾ��ָ�����
	 * 
	 * @param vo
	 *            vo����
	 * @param meta
	 *            IMappingMeta
	 */
	public void deleteObject(Object[] vos, IMappingMeta meta) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			manager.deleteObject(vos, meta);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta����VO���鼯��
	 * 
	 * @param vo
	 *            vo����
	 * @param meta
	 *            IMappingMeta
	 */
	public Collection retrieve(Object vo, IMappingMeta meta) throws DAOException {

		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieve(vo, meta);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta����ָ��Class��VO���󼯺�
	 * 
	 * @param className
	 *            �����
	 * @param meta
	 *            IMappingMeta
	 */
	public Collection retrieveAll(Class className, IMappingMeta meta) throws DAOException {

		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveAll(className, meta);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta�Ͳ�ѯ��������ָ��Class��VO���󼯺�
	 * 
	 * @param className
	 *            �����
	 * @param condition
	 *            ��ѯ����
	 * @param meta
	 *            IMappingMeta
	 */
	public Collection retrieveByClause(Class className, IMappingMeta meta, String condition) throws DAOException {

		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByClause(className, meta, condition);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	public Collection retrieveByClause(Class className, IMappingMeta meta, String condition, SQLParameter params)
			throws DAOException {
		return retrieveByClause(className, meta, condition, (String[]) null, params);
	}

	public Collection retrieveByClause(Class className, IMappingMeta meta, String condition, String[] fields,
			SQLParameter params) throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByClause(className, meta, condition, fields, params);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ��������� IMappingMeta��ѯ����ָ���е�VO����
	 * 
	 * @param className
	 *            ����
	 * @param meta
	 *            IMappingMeta
	 * @param condition
	 *            ָ������
	 * @param fields
	 *            ��ѯ��
	 */
	public Collection retrieveByClause(Class className, IMappingMeta meta, String condition, String[] fields)
			throws DAOException {

		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByClause(className, meta, condition, fields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta�ʹ�������ɾ�����
	 * 
	 * @param meta
	 * @param wherestr
	 * @return
	 * @throws DAOException
	 */
	public int deleteByClause(IMappingMeta meta, String wherestr) throws DAOException {
		return deleteByClause(meta, wherestr, null);
	}

	public int deleteByClause(IMappingMeta meta, String wherestr, SQLParameter params) throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.deleteByClause(meta, wherestr, params);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���PK��IMappingMetaɾ��ָ�����
	 * 
	 * @param meta
	 *            IMappingMeta
	 * @param pk
	 *            ����
	 * @return
	 * @throws DAOException
	 */
	public int deleteByPK(IMappingMeta meta, String pk) throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.deleteByPK(meta, pk);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta�͹�˾pk����ָ���е�VO����
	 * 
	 * @param c
	 *            VO�����
	 * @param meta
	 *            IMappingMeta
	 * @param pkCorp
	 *            ��˾PK
	 * @param selectedFields
	 *            ָ���ֶ�����
	 * @return
	 * @throws DAOException
	 */
	public Collection retrieveByCorp(Class c, IMappingMeta meta, String pkCorp, String[] selectedFields)
			throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByCorp(c, meta, pkCorp, selectedFields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ��ݹ�˾PK��IMappingMeta���ض�ӦVO����
	 * 
	 * @param c
	 *            VO��
	 * @param meta
	 *            IMappingMeta
	 * @param pkCorp
	 *            ��˾PK
	 * @return
	 * @throws DAOException
	 */
	public Collection retrieveByCorp(Class c, IMappingMeta meta, String pkCorp) throws DAOException {

		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByCorp(c, meta, pkCorp);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ���IMappingMeta��pk����ָ���ֶζ�Ӧ��VO����
	 * 
	 * @param className
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param pk
	 *            ����
	 * @return
	 * @throws DAOException
	 */
	public Object retrieveByPK(Class className, IMappingMeta meta, String pk, String[] selectedFields)
			throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByPK(className, meta, pk, selectedFields);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ���IMappingMeta��pk���ض�Ӧ��VO����
	 * 
	 * @param className
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param pk
	 *            ����
	 * @return
	 * @throws DAOException
	 */
	public Object retrieveByPK(Class className, IMappingMeta meta, String pk) throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.retrieveByPK(className, meta, pk);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ��� IMappingMeta����������VO�����Ӧ����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param whereClause
	 *            �������
	 * @return
	 * @throws DAOException
	 */
	public int updateObject(Object vo, IMappingMeta meta, String whereClause) throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.updateObject(vo, meta, whereClause);
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	/**
	 * ��� IMappingMeta����������VO���������Ӧ����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param whereClause
	 *            �������
	 * @return
	 * @throws DAOException
	 */
	public int updateObject(Object[] vos, IMappingMeta meta, String whereClause) throws DAOException {
		return updateObject(vos, meta, whereClause, null);
	}

	/**
	 * ��� IMappingMeta����������VO���������Ӧ����ݿ�
	 * 
	 * @param vos
	 *            VO����
	 * @param meta
	 *            IMappingMeta
	 * @param whereClause
	 *            �������
	 * @return
	 * @throws DAOException
	 */
	public int updateObject(Object[] vos, IMappingMeta meta, String whereClause, SQLParameter param)
			throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.updateObject(vos, meta, whereClause, param);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	/**
	 * ������Դ����
	 * 
	 * @param key
	 *            nc.vo.pub.oid.OID
	 * @throws java.sql.SQLException
	 *             �쳣˵����
	 */
//	public int getDBType() {
//
//		return DataSourceCenter.getInstance().getDatabaseType(dataSource);
//
//	}

	/**
	 * ������ݿ���صı���
	 * 
	 * @param dbType
	 *            int
	 * @param tableName
	 *            java.lang.String
	 * @return java.lang.String
	 * @since ��V1.00
	 */
	protected String getTableName(int dbType, String tableName) {
		String strTn = null;
		switch (dbType) {
		case DBConsts.SQLSERVER:
			strTn = tableName;
			break;
		case DBConsts.ORACLE:
		case DBConsts.DB2:
			// ORACLE�轫�����д
			strTn = tableName.toUpperCase();
			break;
		}
		return strTn;
	}

	/**
	 * �ж���ݱ��Ƿ����
	 * 
	 * @param tableName
	 *            ��ݱ����
	 * @return
	 * @throws DAOException
	 *             �����׳�DAOException
	 */
	public boolean isTableExisted(String tableName) throws DAOException {
		if (tableName == null)
			throw new NullPointerException("TableName is null!");
		PersistenceManager manager = null;
		ResultSet rs = null;
		try {
			manager = createPersistenceManager(dataSource);
			int dbType = manager.getDBType();
			DatabaseMetaData dbmd = manager.getMetaData();

			// ��ñ�����Ϣ
			rs = dbmd.getTables(manager.getCatalog(), manager.getSchema(), getTableName(dbType, tableName),
					new String[] { "TABLE" });
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBUtil.closeRs(rs);
			if (manager != null)
				manager.release();
		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Modified by cch , add this method ���PK��IMappingMetaɾ��ָ�����
	 * 
	 * @param meta
	 *            IMappingMeta
	 * @param pk
	 *            ����
	 * @return
	 * @throws DAOException
	 */
	public int deleteByPKs(IMappingMeta meta, String[] pks) throws DAOException {
		PersistenceManager manager = null;

		try {
			manager = createPersistenceManager(dataSource);
			return manager.deleteByPKs(meta, pks);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}

	}

	private PersistenceManager createPersistenceManager(String ds) throws DbException {
		PersistenceManager manager = PersistenceManager.getInstance(ds);
		manager.setMaxRows(maxRows);
		return manager;
	}

	public int updateVOArray(final SuperVO[] vos, String[] fieldNames, String whereClause, SQLParameter param)
			throws DAOException {
		PersistenceManager manager = null;
		try {
			manager = createPersistenceManager(dataSource);
			return manager.update(vos, fieldNames, whereClause, param);

		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			if (manager != null)
				manager.release();
		}
	}

	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @return
	 * @throws DAOException
	 */
	public int executeBatchSql(List<String> sqlList) throws DAOException{
		PersistenceManager manager = null;
		int value = -1 ;
		Connection conn = null;
		Statement stmt = null;
		
		try {
			manager = createPersistenceManager(dataSource);
			JdbcSession session = manager.getJdbcSession();
			conn = session.getConnection();
			
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			for(int i = 0 ; i < sqlList.size() ; i++){
				stmt.addBatch(sqlList.get(i));
			}
			
			int [] tmp = stmt.executeBatch();
			value  = tmp.length;
			conn.commit();
			conn.setAutoCommit(true);
			value = session.executeBatch();
		} catch (DbException e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (manager != null)
				manager.release();
			DBTool.closeDB(conn, stmt);
		}
		return value;
	}
	
}
