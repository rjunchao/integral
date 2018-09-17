package com.xbkj.common.bp.itf.uap.oid;

/*
 * Created on 2005-4-7
 * @author yanglei(yanglei@ufsoft.com.cn)
 */


public interface IOidBaseCreator {
	
	
    /** һ����� OID ���� */
	public static final int OID_AMOUNT = 1000;
	
	/** OidBase ��ʼֵ �������ʼ����ֵ��ͻ�����Բ�������ַ���*/
	public static final String OID_BASE_INITIAL_VAL = "10000000000000";
	
	/**
	 * ��� OidBase ����ݵ�ǰ��˾��
	 * @param pk_corp String ��˾ ID
	 * @return String ��ǰ OidBase������Ϊ 14 �ֽڡ�
	 */
	public abstract String createOidBase(String dataSource,String pk_corp);
}
