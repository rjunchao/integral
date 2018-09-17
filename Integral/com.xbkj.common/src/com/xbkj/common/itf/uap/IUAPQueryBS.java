/*
 * �������� 2005-9-9
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.itf.uap;

import java.util.Collection;

import com.xbkj.basic.vo.pub.BusinessException;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.SQLParameter;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;

/**
 * @author hey
 * 
 * TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת�� ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public interface IUAPQueryBS {

	abstract public Collection retrieveByCorp(Class c, String pkCorp,
			String[] selectedFields) throws BusinessException;

	abstract public Collection retrieveByCorp(Class c, String pkCorp)
			throws BusinessException;

	abstract public Object retrieveByPK(Class className, String pk)
			throws BusinessException;

	abstract public Object retrieveByPK(Class className, String pk,
			String[] selectedFields) throws BusinessException;

	public Collection retrieveByClause(Class className, String condition,
			String[] fields) throws BusinessException;

	/**
	 * @since 5.5
	 * @param className
	 * @param condition
	 * @param fields
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	public Collection retrieveByClause(Class className, String condition,
			String[] fields, SQLParameter param) throws BusinessException;

	public Collection retrieve(SuperVO vo, boolean isAnd, String[] fields)
			throws BusinessException;

	public Collection retrieve(SuperVO vo, boolean isAnd)
			throws BusinessException;

	/**
	 * ��ѯVO��Ӧ���������
	 * 
	 * @param className
	 * @return
	 * @throws DbException
	 */

	public Collection retrieveAll(Class className) throws BusinessException;

	/**
	 * ���������ѯVO��Ӧ�����
	 * 
	 * @param className
	 * @param condition
	 * @return
	 * @throws DbException
	 */

	public Collection retrieveByClause(Class className, String condition)
			throws BusinessException;

	/**
	 * ִ��һ���в���SQL��ѯ
	 * 
	 * @param sql
	 *            ��ѯ��SQL���
	 * @param parameter
	 *            ��ѯ����
	 * @param processor
	 *            �������
	 * @return
	 * @throws BusinessException
	 *             ��ѯ�������׳��쳣
	 */
	public Object executeQuery(String sql, SQLParameter parameter,
			ResultSetProcessor processor) throws BusinessException;

	/**
	 * ִ��һ���޲����SQL��ѯ
	 * 
	 * @param sql
	 *            ��ѯ��SQL���
	 * @param processor
	 *            �������
	 * @return
	 * @throws BusinessException
	 *             ��ѯ�������׳��쳣
	 */
	public Object executeQuery(String sql, ResultSetProcessor processor)
			throws BusinessException;

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
			throws BusinessException;

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
			throws BusinessException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition, String[] fields)
			throws BusinessException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition, String[] fields,
			SQLParameter param) throws BusinessException;

	public abstract Collection retrieveByClause(Class className,
			IMappingMeta meta, String condition) throws BusinessException;
}
