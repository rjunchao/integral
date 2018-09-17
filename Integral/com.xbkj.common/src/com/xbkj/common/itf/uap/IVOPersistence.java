/*
 * �������� 2005-9-30
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.itf.uap;

import java.util.List;

import com.ibm.db2.jcc.b.nc;
import com.xbkj.basic.vo.pub.BusinessException;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;

/**
 * @author hey
 *
 * TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public interface IVOPersistence {  
    /**
     * ��һ��ֵ������뵽��ݿ���
     * 
     * @param vo
     *            ֵ����
     * @throws DbException
     *             ���������з���������׳��쳣
     */
    abstract public String  insertVO(final SuperVO vo) throws BusinessException;

    /**
     * ��һ��ֵ���󼯺ϲ��뵽��ݿ���
     * 
     * @param vos
     *            ֵ���󼯺�
     * @throws DbException
     *             ���������з���������׳��쳣
     */
    abstract public String[] insertVOList(final List vos) throws BusinessException;

    /**
     * ��һ��ֵ����������뵽��ݿ���
     * 
     * @param vo
     *            ֵ�������
     * @throws BusinessException
     *             ���������з���������׳��쳣
     */
    abstract public String[] insertVOArray(final SuperVO vo[])
            throws BusinessException;

    /**
     * ����һ������ݿ����Ѿ�����ֵ����
     * 
     * @param vo
     *            ���µ�vo����
     * @throws BusinessException
     *             �����¹���з���������׳��쳣
     */
    abstract public int updateVO(final SuperVO vo) throws BusinessException;

    /**
     * ����VO����
     * 
     * @param vos
     *            vo����
     * @throws BusinessException
     *             �����¹���з���������׳��쳣
     */
    abstract public void updateVOList(final List vos) throws BusinessException;

    abstract public int updateVOArray(final SuperVO vo[])
            throws BusinessException;

    /**
     * ɾ��һ������ݿ����Ѿ�����ֵ����
     * 
     * @param vo
     *            vo����
     * @throws DbException
     *             ���ɾ�����з���������׳��쳣
     */

    abstract public void deleteVO(final SuperVO vo) throws BusinessException;

    /**
     * ɾ��һ������ݿ����Ѿ�����ֵ��������
     * 
     * @param vo
     *            vo����
     * @throws BusinessException
     *             ���ɾ�����з���������׳��쳣
     */
    abstract public void deleteVOArray(final SuperVO vo[])
            throws BusinessException;

    /**
     * ɾ��һ������ݿ����Ѿ�����ֵ���󼯺�
     * 
     * @param vos
     * @throws BusinessException
     *             ���ɾ�����з���������׳��쳣
     */
    abstract public void deleteVOList(final List vos) throws BusinessException;

    abstract public void deleteByPK(Class className, String pk)
    throws BusinessException;
    
    abstract public void deleteByPKs(Class className, String[] pks)
            throws BusinessException;

    abstract public void deleteByClause(Class className, String wherestr)
            throws BusinessException;


     /**
     * ��һ��ֵ����������뵽��ݿ���
     *
     * @param vo ֵ�������
     * @throws nc.bs.dao.DAOException ���������з���������׳��쳣
     */
    public abstract String[] insertObject(final Object vo[], IMappingMeta meta)
            throws BusinessException;

    /**
     * ��һ��ֵ������MappingMeta����Ϣ���뵽��ݿ���
     *
     * @param vo   ֵ����
     * @param meta ���ӳ����Ϣ
     * @return
     * @throws DbException ��������ݳ������׳�DbException
     */
    public abstract String insertObject(final Object vo, IMappingMeta meta)
            throws BusinessException;

    /**
     * ��һ��ֵ����������MappingMeta����Ϣ���뵽��ݿ���
     *
     * @param vos  ֵ��������
     * @param meta ���ӳ����Ϣ
     * @return ���ظ�������
     * @throws DbException ��������ݳ������׳�DbException
     */
    public abstract int updateObject(final Object[] vos, IMappingMeta meta)
            throws BusinessException;

    /**
     * ��һ��ֵ����������MappingMeta����Ϣ���µ���ݿ���
     *
     * @param vo   ֵ����
     * @param meta ���ӳ����Ϣ
     * @return ���ظ�������
     * @throws DbException ���������ݳ������׳�DbException
     */
    public abstract int updateObject(final Object vo, IMappingMeta meta)
            throws BusinessException;

    /**
     * ���MappingMeta��Ϣɾ��ֵ���������Ӧ�����
     *
     * @param vos  ֵ��������
     * @param meta ���ӳ����Ϣ
     * @throws DbException ���ɾ����ݳ������׳�DbException
     */
    public abstract void deleteObject(final Object vos[], IMappingMeta meta)
            throws BusinessException;

    /**
     * ���MappingMeta��Ϣɾ��ֵ�����Ӧ�����
     *
     * @param vo   ֵ������
     * @param meta ���ӳ����Ϣ
     * @throws DbException ���ɾ����ݳ������׳�DbException
     */
    public abstract void deleteObject(final Object vo, IMappingMeta meta)
            throws BusinessException;
}
