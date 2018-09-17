package com.xbkj.basic.vo.pub;

/**
 *  <p>����һ��ѭ�������δ桢ȡ�������Ե�һ������VO��
 *  <p>���磺A������B���������һ��VO����󣬿�ͨ����������ô�VO�����
 * ��������ֵ���������֪�����VO��������Щ���ԣ�
 *  <p>=========================================
 *  <p>String[] keys = vo.getAttributeNames();
 *  <p>for ( int i = 0; i < keys.length; i++ ) {
 *  <p>
 *  <p>    Object obj = getAttributeValue(key[i]);
 *  <p>    ... ...//ʹ��obj
 *  <p>}
 *  <p>=========================================
 *  <p>����VO�����Ӧ���ڵ��������յ�����
 *  <p>
 * �������ڣ�(01-3-20 17:15:05)
 * @author��Zhao Jijiang
 */
public abstract class CircularlyAccessibleValueObject extends ValueObject {
	private static final long serialVersionUID = -4360103926517671160L;
	private int status = VOStatus.UNCHANGED;

/**
 * CircularlyAccessibleValueObject ������ע�⡣
 */
public CircularlyAccessibleValueObject() {
	super();
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:26:03)
 * @return java.lang.String[]
 */
public abstract String[] getAttributeNames();
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:24:29)
 * @param key java.lang.String
 */
public abstract Object getAttributeValue(String attributeName);
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-4-11 14:48:58)
 * @return int
 */
public int getStatus() {
	
	return this.status;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(01-3-20 17:24:29)
 * @param key java.lang.String
 */
public abstract void setAttributeValue(String name, Object value);
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-4-11 14:48:58)
 * @return int
 */
public void setStatus(int status) {
	
	this.status = status;
}
}
