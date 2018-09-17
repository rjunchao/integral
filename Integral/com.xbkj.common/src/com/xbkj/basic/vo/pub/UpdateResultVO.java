/*
 * @(#)UpdateResultVO.java 1.0 2003-8-12
 *
 * Copyright 2005 UFIDA Software Co. Ltd. All rights reserved.
 * UFIDA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.xbkj.basic.vo.pub;

import com.xbkj.basic.vo.pub.lang.UFDateTime;

/**
 * ����{@link nc.bs.pub.SuperDMO}��<code>insert</code>��<code>insertArray</code>�����ķ���ֵ��
 * <DL>
 * <DT><B>Provider:</B></DT>
 * <DD>NC-UAP</DD>
 * </DL>
 * 
 * @author zjj
 * @since 2.1
 */
public class UpdateResultVO extends ValueObject {
	private String[] pks = null;

	private UFDateTime ts = null;

	public UpdateResultVO() {
		super();
	}

	/**
	 * ���ض������ʾ��ơ�
	 * <p>
	 * �������ڣ�(2001-2-15 14:18:08)
	 * 
	 * @return �������ʾ��ơ�
	 */
	public String getEntityName() {
		return null;
	}

	/**
	 * �����ַ����顣
	 * <p>
	 * �������ڣ�(2003-8-12 10:03:11)
	 * 
	 * @return �����ַ�����
	 */
	public String[] getPks() {
		return pks;
	}

	/**
	 * ʱ�����
	 * <p>
	 * �������ڣ�(2003-8-12 10:03:11)
	 * 
	 * @return ʱ���
	 */
	public UFDateTime getTs() {
		return ts;
	}

	/**
	 * ���������ַ����顣
	 * <p>
	 * �������ڣ�(2003-8-12 10:03:11)
	 * 
	 * @param newPks
	 *            �����ַ�����
	 */
	public void setPks(java.lang.String[] newPks) {
		pks = newPks;
	}

	/**
	 * ����ʱ�����
	 * <p>
	 * �������ڣ�(2003-8-12 10:03:11)
	 * 
	 * @param newTs
	 *            ʱ���
	 */

	public void setTs(UFDateTime newTs) {
		ts = newTs;
	}

	/**
	 * ��֤���������֮�������߼���ȷ�ԡ�
	 * 
	 * @throws ValidationException
	 *             ��֤ʧ��
	 */
	public void validate() throws ValidationException {
	}
}
