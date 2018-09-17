package com.xbkj.basic.vo.pub;

/**
 *  <p>��װ���ҵ�����һ����ݣ���ƾ֤����Ŀ�ȣ�������ϵͳ����
 * ֮�䴫��ҵ����ݡ�
 *  <p>ʵ����ͬʱ����ʵ��ÿ����Ҫ�־û����Ե�setXXX��getXXX������
 * ��set�����ж����Խ�����֤����֤ʧ���׳�ValidationException��
 * ����
 *  <p>Ϊÿһ���������һ����̬��������������FieldObject������
 * Ϊÿ��FieldObjectʵ��һ��get��������get������ʵ������Ӧ��
 * �Ե�������Ϣ��FieldObject�������˶�Ӧ���Ե�ȡֵ��Χ����ơ���ǩ
 * ��������
 * 
 * <p>
 * �������ڣ�(2001-2-14 15:50:55)
 * @author��Zhao Jijiang
 */
public abstract class ValueObject implements Cloneable, java.io.Serializable {
	private static final long serialVersionUID = -2144910174446136305L;
	/**�жϴ˼�¼�Ƿ�Ϊ��*/
	private boolean  m_isDirty=false;
/**
 * ValueObject ������ע�⡣
 */
public ValueObject() {
	super();
}
/**
 * ��¡һ����ȫ��ͬ��VO����(ǰ�㸴��)��
 * 
 * �������ڣ�(2001-3-7 11:34:51)
 * @return nc.vo.pub.ValueObject
 */
public Object clone(){

	Object o = null;
	try {
		o = super.clone();
	}
	catch (CloneNotSupportedException e) {
		System.out.println("clone not supported!");
	}
	return o;
}
/**
 * ������ֵ�������ʾ��ơ�
 * 
 * �������ڣ�(2001-2-15 14:18:08)
 * @return java.lang.String ������ֵ�������ʾ��ơ�
 */
public abstract String getEntityName();

/**
 * ���ض����ʶ������Ψһ��λ����
 * 
 * �������ڣ�(2001-2-15 9:43:38)
 * @return nc.vo.pub.PrimaryKey
 */
public String getPrimaryKey() throws BusinessException {
	
	throw new BusinessException("Method getPrimaryKey() is not realized in class " + getClass().getName());
}
/**
 * �����쳣��
 * 
 * �������ڣ�(2001-2-28 10:33:37)
 * @param e java.lang.Throwable
 */
protected static void handleException(Throwable e) {
	e.printStackTrace();
}
/**
 * �Ƿ�Ϊ�����
 * �������ڣ�(2001-8-8 12:44:04)
 * @since  V1.00
 * @return boolean
 */
public boolean isDirty() {
	return m_isDirty;
}
/**
 * �����Ƿ�Ϊ�����
 * �������ڣ�(2001-8-8 12:43:23)
 * @since  V1.00
 * @param isDirty boolean
 */
public void setDirty(boolean isDirty) {
	m_isDirty = isDirty;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-3-26 9:05:32)
 * @param key java.lang.String
 */
public void setPrimaryKey(String key) throws BusinessException {

	throw new BusinessException("Method setPrimaryKey() is not realized in class " + getClass().getName());
}
/**
 * ��֤���������֮�������߼���ȷ�ԡ�
 * 
 * �������ڣ�(2001-2-15 11:47:35)
 * @exception nc.vo.pub.ValidationException �����֤ʧ�ܣ��׳�
 *     ValidationException���Դ�����н��͡�
 */
public abstract void validate() throws ValidationException;
}
