/*
 * �������� 2005-10-14
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.bs.dao;

import com.xbkj.basic.vo.pub.BusinessException;

/**
 * @author hey
 * 
 * ��ݷ��ʶ����쳣��
 */
public class DAOException extends BusinessException {

	/**
	 * 
	 */
	public DAOException() {
		super();
		// TODO �Զ���ɹ��캯����
	}

	/**
	 * @param s
	 */
	public DAOException(String s) {
		super(s);
		// TODO �Զ���ɹ��캯����
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
		// TODO �Զ���ɹ��캯����
	}

	/**
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
		// TODO �Զ���ɹ��캯����
	}

}
