/*
 * @(#)SqlSupportVO.java 1.0 2003-7-25
 *
 * Copyright 2005 UFIDA Software Co. Ltd. All rights reserved.
 * UFIDA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.xbkj.basic.vo.pub;

/**
 * ��ݿ���е��ֶ�����VO������Ķ�Ӧ��ϵVO�ࡣ
 * 
 * <DL>
 * <DT><B>Provider:</B></DT>
 * <DD>NC-UAP</DD>
 * </DL>
 * 
 * @author zjj
 * @since 2.2
 */
public class SqlSupportVO extends ValueObject {
	// ��ݿ���е��ֶ���
	private String sqlSelectField = null;

	// VO������
	private String voAttributeName = null;

	/**
	 * �����ݿ���е��ֶ����VO�������졣
	 * 
	 * @param sqlSelectField
	 *            ��ݿ���е��ֶ���
	 * @param voAttributeName
	 *            VO������
	 */
	public SqlSupportVO(String sqlSelectField, String voAttributeName) {
		super();
		this.sqlSelectField = sqlSelectField;
		this.voAttributeName = voAttributeName;
	}

	/**
	 * ���ض������ʾ��ơ�
	 * <p>
	 * �������ڣ�(2001-2-15 14:18:08)
	 * 
	 * @return ��ʾ��ơ�
	 */
	public String getEntityName() {
		return null;
	}

	/**
	 * �����ݿ���е��ֶ���
	 * <p>
	 * �������ڣ�(2003-7-25 14:16:58)
	 * 
	 * @return ��ݿ���е��ֶ���
	 */
	public String getSqlSelectField() {
		return sqlSelectField;
	}

	/**
	 * �������ݿ���е��ֶ������Ӧ��VO������
	 * 
	 * 
	 * @return VO������
	 */
	public String getVoAttributeName() {
		return voAttributeName;
	}

	/**
	 * ������ݿ���е��ֶ���
	 * 
	 * @param newSqlSelectField
	 *            ��ݿ���е��ֶ���
	 */
	public void setSqlSelectField(String newSqlSelectField) {
		sqlSelectField = newSqlSelectField;
	}

	/**
	 * ��������ݿ���е��ֶ������Ӧ��VO������
	 * 
	 * @param newVoAttributeName
	 *            VO������
	 */
	public void setVoAttributeName(String newVoAttributeName) {
		voAttributeName = newVoAttributeName;
	}

	/**
	 * ��֤���������֮�������߼���ȷ�ԡ�
	 * 
	 * @throws ValidationException
	 *             ��֤ʧ�ܡ�
	 */
	public void validate() throws ValidationException {
	}
}
