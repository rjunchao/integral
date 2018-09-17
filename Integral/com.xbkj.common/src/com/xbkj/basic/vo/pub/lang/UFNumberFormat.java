package com.xbkj.basic.vo.pub.lang;

/**
 * ���ָ�ʽ��װ��
 * �������ڣ�(2001-12-03 10:18:01)
 * @author������
 */
public class UFNumberFormat {
	public static final int NUMBERSTYLE = 0;
	public static final int CURRENCYSTYLE = 1;
	public static final int PERCENTSTYLE = 2;
	public static final int SCIENTIFICSTYLE = 3;
/**
 * DecimalFormat ������ע�⡣
 */
public UFNumberFormat() {
	super();
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(double value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(double value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(double value, int style, java.util.Locale locale) {
	java.text.NumberFormat form = null;
	if (locale == null) {
		switch (style) {
			case NUMBERSTYLE :
				form = java.text.NumberFormat.getNumberInstance();
				break;
			case CURRENCYSTYLE :
				form = java.text.NumberFormat.getCurrencyInstance();
				break;
			case PERCENTSTYLE :
				form = java.text.NumberFormat.getPercentInstance();
				break;
			case SCIENTIFICSTYLE :
			default :
				form = java.text.NumberFormat.getInstance();
				break;
		}
	} else {
		switch (style) {
			case NUMBERSTYLE :
				form = java.text.NumberFormat.getNumberInstance(locale);
				break;
			case CURRENCYSTYLE :
				form = java.text.NumberFormat.getCurrencyInstance(locale);
				break;
			case PERCENTSTYLE :
				form = java.text.NumberFormat.getPercentInstance(locale);
				break;
			case SCIENTIFICSTYLE :
			default :
				form = java.text.NumberFormat.getInstance(locale);
				break;
		}
	}
	form.setMaximumFractionDigits(9);
	return form.format(value);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(int value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(int value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(int value,int style,java.util.Locale locale) {
	return format((long)value,style,locale);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(long value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(long value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(long value, int style, java.util.Locale locale) {
	java.text.NumberFormat form = null;
	if (locale == null) {
		switch (style) {
			case NUMBERSTYLE :
				form = java.text.NumberFormat.getNumberInstance();
				break;
			case CURRENCYSTYLE :
				form = java.text.NumberFormat.getCurrencyInstance();
				break;
			case PERCENTSTYLE :
				form = java.text.NumberFormat.getPercentInstance();
				break;
			case SCIENTIFICSTYLE :
			default :
				form = java.text.NumberFormat.getInstance();
				break;
		}
	} else {
		switch (style) {
			case NUMBERSTYLE :
				form = java.text.NumberFormat.getNumberInstance(locale);
				break;
			case CURRENCYSTYLE :
				form = java.text.NumberFormat.getCurrencyInstance(locale);
				break;
			case PERCENTSTYLE :
				form = java.text.NumberFormat.getPercentInstance(locale);
				break;
			case SCIENTIFICSTYLE :
			default :
				form = java.text.NumberFormat.getInstance(locale);
				break;
		}
	}
	return form.format(value);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Double value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(Double value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Double value, int style, java.util.Locale locale) {
	if (value != null)
		return format(value.doubleValue(), style, locale);
	else
		return "";
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Integer value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Integer value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Integer value, int style, java.util.Locale locale) {
	if (value != null)
		return format(value.intValue(), style, locale);
	else
		return "";
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Long value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(Long value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(Long value, int style, java.util.Locale locale) {
	if (value != null)
		return format(value.longValue(), style, locale);
	else
		return "";
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:08:53)
 * @param value int
 */
public static String format(UFDouble value) {
	return format(value,NUMBERSTYLE);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(UFDouble value,int style) {
	return format(value,style,null);
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-11-30 15:11:23)
 * @return java.lang.String
 * @param value double
 */
public static String format(UFDouble value, int style, java.util.Locale locale) {
	if (value != null)
		return format(value.doubleValue(), style, locale);
	else
		return "";
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-12-3 10:32:30)
 * @param args java.lang.String[]
 */
public static void main(String[] args) {
	/*
	double v1=123456.123456;
	System.out.println(format(v1));
	int v2=123456;
	System.out.println(format(v2));
	Integer v3=new Integer(123456);
	System.out.println(format(v3));
	long  v4=123456789;
	System.out.println(format(v4));
	UFDouble  v5=new UFDouble("123456789.12345678",-8);
	System.out.println(format(v5));
	*/
}
}
