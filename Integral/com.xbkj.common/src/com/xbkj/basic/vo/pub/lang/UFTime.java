package com.xbkj.basic.vo.pub.lang;

import java.text.SimpleDateFormat;

/**
 * ////////////////////////////////////////////////////////////////////
 * ������������HH:mm:ss�ĸ�ʽ��ʾʱ�䡣
 * ////////////////////////////////////////////////////////////////////
 */
public final class UFTime implements java.io.Serializable,Comparable{
	static final long serialVersionUID = 7886265777567493523L;
/**
 * UFTime ������ע�͡�
 */
public UFTime() {
	super();
}
/**
 * �Դ�1970��1��1��0ʱ0��0�뵽���ڵĺ�����������ʱ��
 * �������ڣ�(00-7-10 14:37:17)
 * @param m long
 */
public UFTime(long m) {
	this(new java.util.Date(m));
}
/**
 *
 * @version (00-7-3 13:55:11)
 *
 * @param strTime java.lang.String
 */
public UFTime(String strTime) {
	this(strTime,true);
}
/**
 * /////////////////////////////////////////////////////////
 * ����˵������java.sql.Date���͹���UFʱ������
 * ����˵����
 * ����ֵ��
 * ��֪���󣺣���ѡ��
 * �׳��쳣������ѡ��
 * ��ĵĶ������ԣ�����ѡ��
 * �����ʷ������ѡ��
 * ʹ�÷����ѡ��
 * ״̬��Ǩ������ѡ��
 * �������⣺����ѡ��
 * /////////////////////////////////////////////////////////
 *
 * @param date java.sql.Date
 */
public UFTime(java.sql.Date date) {
	this( (java.util.Date)date );
}
/**
 * /////////////////////////////////////////////////////////
 * ����˵������java.util.Date���͹���ʱ������
 * ����˵����
 * ����ֵ��
 * ��֪���󣺣���ѡ��
 * �׳��쳣������ѡ��
 * ��ĵĶ������ԣ�����ѡ��
 * �����ʷ������ѡ��
 * ʹ�÷����ѡ��
 * ״̬��Ǩ������ѡ��
 * �������⣺����ѡ��
 * /////////////////////////////////////////////////////////
 *
 * @param date java.util.Date
 */
public UFTime(java.util.Date date) {
	this((new SimpleDateFormat("HH:mm:ss")).format(date));
}



/**
 * �ڴ˴����뷽��˵����
 * �������ڣ�(00-7-6 9:53:30)
 * @return java.lang.String
 */
public String toString() {
	return value==null?"":value;
}

	private String value = null;

/**
 * �Ƚ�ʱ���Ⱥ󣬶���ʱ���ڲ���ʱ��֮��Ϊtrue
 */
public boolean after(UFTime when) {
	return value.compareTo(when.toString()) > 0;
}

/**
 * �Ƚ�ʱ���Ⱥ󣬶���ʱ���ڲ���ʱ��֮ǰΪtrue������������
 */
public boolean before(UFTime when) {
	return value.compareTo(when.toString()) < 0;
}

/**
 * ��¡ʱ�����
 * @return nc.vo.pub.lang.UFTime
 */
public Object clone() {
	return new UFTime(value);
}

/**
 * ����ʱ���Ⱥ󣬲��������ڣ�
	����0Ϊ����֮��ʱ��
	����0�Ͳ���Ϊͬһʱ��
	С��0Ϊ����֮ǰʱ��
 */
public int compareTo(UFTime when) {
	return value.compareTo(when.toString());
}

/**
 * �Ƚ������Ⱥ�trueΪͬһ��
 */
public boolean equals(Object o) {
	if ((o != null) && (o instanceof UFTime)) {
		return value.equals(o.toString());
	}
	return false;
}

/**
 * �ڴ˴����뷽��˵����
 * �������ڣ�(00-7-10 15:01:20)
 * @return java.lang.Integer
 */
public int getHour() {
	return Integer.valueOf(value.substring(0,2)).intValue();
}

/**
 * �ڴ˴����뷽��˵����
 * �������ڣ�(00-7-10 15:02:25)
 * @return java.lang.Integer
 */
public int getMinute() {
	return Integer.valueOf(value.substring(3,5)).intValue();
}

/**
 * �ڴ˴����뷽��˵����
 * �������ڣ�(00-7-10 15:03:01)
 * @return java.lang.Integer
 */
public int getSecond() {
	return Integer.valueOf(value.substring(6,8)).intValue();
}

/**
 * ����ַ��ʱ����ת������Чʱ�䴮������ת����
 * �������ڣ�(2001-5-28 13:28:29)
 * @return java.lang.String
 * @param sTime java.lang.String
 */
public static String getValidUFTimeString(String sTime) {
	if (sTime == null)
		return null;
	if (isAllowTime(sTime))
		return sTime;
	else {
		//�����ת������ת��
		try {
			int hour = 0;
			int minute = 0;
			int second = 0;
			int index = sTime.indexOf(":");
			if (index < 1) {
				if (sTime.trim().length() > 0)
					hour = Integer.parseInt(sTime.trim());
			} else {
				hour = Integer.parseInt(sTime.trim().substring(0, index));
				String sTemp = sTime.trim().substring(index + 1);
				if (sTemp.trim().length() > 0) {
					index = sTemp.indexOf(":");
					if (index < 1) {
						minute = Integer.parseInt(sTemp.trim());
					} else {
						minute = Integer.parseInt(sTemp.trim().substring(0, index));
						if (sTemp.trim().substring(index + 1).trim().length() > 0)
							second = Integer.parseInt(sTemp.trim().substring(index + 1));
					}
				}
			}
			if (hour < 0 || hour > 24 || minute < 0 || minute > 59 || second < 0 || second > 59)
				return null;
			String strHour = String.valueOf(hour);
			if (strHour.length() < 2)
				strHour = "0" + strHour;
			String strMinute = String.valueOf(minute);
			if (strMinute.length() < 2)
				strMinute = "0" + strMinute;
			String strSecond = String.valueOf(second);
			if (strSecond.length() < 2)
				strSecond = "0" + strSecond;
			//
			return strHour + ":" + strMinute + ":" + strSecond;
		} catch (Exception e) {
			return null;
		}
	}
}

/**
 * ����ַ�11-18λ��ת����ʱ�䷵��true��
 * @return boolean
 * @param strDateTime java.lang.String
 */
public static boolean isAllowTime(String strTime) {
	if (strTime == null || strTime.trim().length() == 0)
		return true;
	if (strTime.trim().length() != 8)
		return false;
	for (int i = 0; i < 8; i++) {
		char c = strTime.trim().charAt(i);
		if (i == 2 || i == 5) {
			if (c != ':')
				return false;
		} else
			if (c < '0' || c > '9')
				return false;
	}
	int hour = Integer.parseInt(strTime.trim().substring(0, 2));
	int minute = Integer.parseInt(strTime.trim().substring(3, 5));
	int second = Integer.parseInt(strTime.trim().substring(6, 8));
	if (hour < 0 || hour > 24 || minute < 0 || minute > 59 || second < 0 || second > 59)
		return false;
	return true;
}

/**
 * �Դ�1970��1��1��0ʱ0��0�뵽���ڵĺ�����������ʱ��
 * ����o�����壬ֻ��Ϊ�˱�ʾһ���µĹ���
 * �������ڣ�(00-7-10 14:37:17)
 * @param m long
 */
public UFTime(long m, Object o) {
	if (m == 24 * 3600000) {
		value = "24:00:00";
		return;
	}
	long seconds = m / 1000;
	long hour = seconds / 3600;
	hour %= 24;
	long minute = seconds / 60;
	minute %= 60;
	long second = seconds % 60;
	value = "";
	if (hour < 10)
		value += "0" + hour;
	else
		value += hour;
	value += ":";
	if (minute < 10)
		value += "0" + minute;
	else
		value += minute;
	value += ":";
	if (second < 10)
		value += "0" + second;
	else
		value += second;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-9-18 10:54:22)
 * @param strTime java.lang.String
 * @param isParse boolean
 */
public UFTime(String strTime, boolean isParse) {
	if (isParse)
		value = getValidUFTimeString(strTime);
	else
		value = strTime;
}

/**
 * �õ�������
 * �������ڣ�(2002-8-23 13:38:33)
 * @return java.lang.Long
 */
public long getMillis() {
	return ((getHour() * 60 + getMinute()) * 60 + getSecond()) * 1000;
}

public int compareTo(Object o)
{
	if(o instanceof UFTime)
		return value.compareTo(o.toString());
	throw new RuntimeException("Unsupported parameter type while comparing UFTime!");
}

}