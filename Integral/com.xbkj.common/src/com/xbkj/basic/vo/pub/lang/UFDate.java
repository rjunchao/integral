package com.xbkj.basic.vo.pub.lang;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class UFDate implements java.io.Serializable, Comparable {
	static final long serialVersionUID = -1037968151602108293L;

	private String value = null;

	private static final long millisPerDay = 24 * 60 * 60 * 1000;

	private static final int LRUSIZE = 500;

	private static class LRUMap<K, V> extends LinkedHashMap<K, V> {

		private static final long serialVersionUID = 1L;

		public LRUMap(int initSize) {
			super(initSize, 1f, true);
		}

		protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
			if (size() > LRUSIZE)
				return true;
			else
				return false;
		}
	}

	// 512 is fix the bug of jdk to avoid transfer
	private final static Map<Object, UFDate> allUsedDate = new LRUMap<Object, UFDate>(
			512);

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private transient Long currentLong = null;

	/**
	 * UFDate ������ע�͡�
	 */
	public UFDate() {
		this(new Date());
	}

	/**
	 * �Դ�1970��1��1��0ʱ0��0�뵽���ڵĺ���������������
	 * 
	 * @param m
	 *            long
	 */
	public UFDate(long m) {
		GregorianCalendar cal = new GregorianCalendar(TimeZone
				.getTimeZone("Asia/Shanghai"));
		cal.setTimeInMillis(m);
		value = toDateString(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * ����˵������yyyy-MM-dd��ʽ���ַ�����������
	 */
	public UFDate(String strDate) {
		this(strDate, true);
	}

	/**
	 * ����˵��������������yyyy-MM-dd��ʽ���ַ����������� ��������������yyyy��yyyy-MM �������ڣ�(2001-9-18
	 * 10:03:53)
	 * 
	 * @param strDate
	 *            java.lang.String
	 * @param isParse
	 *            boolean
	 */
	public UFDate(String strDate, boolean isParse) {
		if (isParse) {
			value = internalParse(strDate);
		} else {
			if (strDate == null || strDate.trim().length() != 10) {
				throw new IllegalArgumentException("invalid UFDate:" + strDate);
			}
			value = strDate.trim();
		}
	}

	/**
	 * /////////////////////////////////////////////////////////
	 * ����˵������java.sql.Date���͹���UF��������
	 * 
	 * @param date
	 *            java.sql.Date
	 */
	public UFDate(java.sql.Date date) {
		this((java.util.Date) date);
	}

	/**
	 * ����˵������java.util.Date���͹�����������
	 */
	public UFDate(java.util.Date date) {
		value = toDateString(date);
	}

	/**
	 * �Ƚ������Ⱥ󣬶��������ڲ�������֮��Ϊtrue
	 */
	public boolean after(UFDate when) {
		return this.compareTo(when) > 0;
	}

	/**
	 * �Ƚ������Ⱥ󣬶��������ڲ�������֮ǰΪtrue
	 */
	public boolean before(UFDate when) {
		return this.compareTo(when) < 0;
	}

	/**
	 * ��¡���ڶҶ���
	 * 
	 * @return nc.vo.pub.lang.UFDate
	 */
	public Object clone() {
		return new UFDate(value);
	}

	/**
	 * ���������Ⱥ� ����0 ---Ϊ����֮������ ����0 ---�Ͳ���Ϊͬһ�� С��0 ---Ϊ����֮ǰ����
	 */
	public int compareTo(UFDate when) {
		return compareTo(when.getMillis());
	}

	/**
	 * ���������Ⱥ� ����0 ---Ϊ����֮������ ����0 ---�Ͳ���Ϊͬһ�� С��0 ---Ϊ����֮ǰ����
	 */
	private int compareTo(Long whenLong) {
		long retl = this.getMillis() - whenLong;
		if (retl == 0)
			return 0;
		else
			return retl > 0 ? 1 : -1;
	}

	/**
	 * �Ƚ������Ⱥ�trueΪͬһ��
	 */
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof UFDate)) {
			return this.getMillis() == ((UFDate) o).getMillis();
		}
		return false;
	}

	public static UFDate getDate(long d) {

		return getDate(d, false);
	}

	public static UFDate getDate(String strDate) {
		if (strDate == null || strDate.trim().length() == 0)
			return null;
		return getDate(strDate, true);
	}

	public static UFDate getDate(Date date) {
		String strDate = toDateString(date);
		return getDate(strDate, false);
	}

	public static UFDate getDate(String date, boolean check) {
		return getDate((Object) date, check);
	}

	public static UFDate getDate(Long date) {
		return getDate((Object) date, false);
	}

	private static UFDate getDate(Object date, boolean check) {
		if (date instanceof Long || date instanceof String) {
			//for performance
			if (rwl.readLock().tryLock()) {
				try {
					UFDate o = (UFDate) allUsedDate.get(date);
					if (o == null) {
						UFDate n = toUFDate(date, check);
						rwl.readLock().unlock();
						rwl.writeLock().lock();
						try {
							o = allUsedDate.get(date);
							if (o == null) {
								o = n;
								allUsedDate.put(date, o);
							}
						} finally {
							rwl.readLock().lock();
							rwl.writeLock().unlock();
						}
					}
					return o;
				} finally {
					rwl.readLock().unlock();
				}
			} else {
				return toUFDate(date, check);
			}
		} else {
			throw new IllegalArgumentException(
					"expect long or string parameter as the first parameter");
		}
	}

	private static UFDate toUFDate(Object date, boolean check) {
		if (date instanceof String)
			return new UFDate((String) date, check);
		else
			return new UFDate((Long) date);
	}

	/**
	 * �������������ڡ�
	 * 
	 * @param days
	 *            int
	 */
	public UFDate getDateAfter(int days) {
		long l = getMillis() + millisPerDay * days;
		Date date = new Date(l);
		return getDate(date);

	}

	/**
	 * ��������ǰ�����ڡ�
	 * 
	 * @param days
	 *            int
	 */
	public UFDate getDateBefore(int days) {
		return getDateAfter(-days);
	}

	/**
	 * �ڴ˴����뷽��˵���� �������ڣ�(00-7-10 14:54:26)
	 * 
	 * @return int
	 */
	public int getDay() {
		return Integer.parseInt(value.substring(8, 10));
	}

	/**
	 * ����ĳһ���ھ���������ʾ�ڽ���֮��
	 * 
	 * @return int
	 * @param when
	 *            UFDate
	 */
	public int getDaysAfter(UFDate when) {
		int days = 0;
		if (when != null) {
			days = (int) ((this.getMillis() - when.getMillis()) / millisPerDay);
		}
		return days;
	}

	/**
	 * ���غ�һ���ھ�ǰһ����֮��������
	 * 
	 * @return int
	 * @param begin
	 *            UFDate
	 * @param end
	 *            UFDate
	 */
	public static int getDaysBetween(UFDate begin, UFDate end) {
		int days = 0;
		if (begin != null && end != null) {
			days = (int) ((end.getMillis() - begin.getMillis()) / millisPerDay);
		}
		return days;
	}

	public int getDaysMonth() {
		return getDaysMonth(getYear(), getMonth());
	}

	public static int getDaysMonth(int year, int month) {
		switch (month) {
		case 1:
			return 31;
		case 2:
			if (isLeapYear(year))
				return 29;
			else
				return 28;
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		default:
			return 30;
		}
	}

	public String getEnMonth() {
		switch (getMonth()) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";
		}
		return null;
	}

	/**
	 * �ڴ˴����뷽��˵���� �������ڣ�(00-12-18 20:57:29)
	 * 
	 * @return java.lang.String
	 */
	public String getEnWeek() {
		switch (getWeek()) {
		case 0:
			return "Sun";
		case 1:
			return "Mon";
		case 2:
			return "Tue";
		case 3:
			return "Wed";
		case 4:
			return "Thu";
		case 5:
			return "Fri";
		case 6:
			return "Sat";
		}
		return null;
	}

	/**
	 * �ڴ˴����뷽��˵���� �������ڣ�(00-7-10 14:53:44)
	 * 
	 * @return int
	 */
	public int getMonth() {
		return Integer.parseInt(value.substring(5, 7));
	}

	public String getStrDay() {
		return value.substring(8, 10);
	}

	public String getStrMonth() {
		return value.substring(5, 7);
	}

	/**
	 * �ڴ˴����뷽��˵���� �������ڣ�(00-12-18 20:49:02)
	 * 
	 * ԭ�� 1980-1-6��������
	 * 
	 * @return int Sunday-Monday-Saturday 0-6
	 */
	public int getWeek() {
		int days = getDaysAfter(new UFDate("1980-01-06"));
		int week = days % 7;
		if (week < 0)
			week += 7;
		return week;
	}

	/**
	 * ��������ֵ�� �������ڣ�(00-7-10 14:52:13)
	 * 
	 * @return int
	 */
	public int getYear() {
		return Integer.parseInt(value.substring(0, 4));
	}

	/**
	 * �Ƿ����ꡣ
	 * 
	 * @return boolean
	 */
	public boolean isLeapYear() {
		return isLeapYear(getYear());
	}

	/**
	 * �Ƿ����ꡣ
	 * 
	 * @return boolean
	 * @param year
	 *            int
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0))
			return true;
		else
			return false;
	}

	public String toString() {
		return value == null ? "" : value;
	}

	public int compareTo(Object o) {
		if (o instanceof UFDate)
			return compareTo((UFDate) o);
		else if (o instanceof UFDateTime)
			return compareTo(((UFDateTime) o).getMillis());
		else
			throw new IllegalArgumentException();
	}

	/**
	 * �õ�������
	 * <p>
	 * �������ڣ�(2006-1-11 10:24:00)
	 * 
	 * @return long
	 */
	public long getMillis() {
		if (currentLong == null) {
			GregorianCalendar cal = new GregorianCalendar(TimeZone
					.getTimeZone("Asia/Shanghai"));
			cal.set(Calendar.YEAR, getYear());
			cal.set(Calendar.MONTH, getMonth() - 1);
			cal.set(Calendar.DAY_OF_MONTH, getDay());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			currentLong = cal.getTimeInMillis();

		}
		return currentLong;

	}

	/**
	 * ���ӵ�ǰ������һ���ڵ�����
	 * 
	 * @return
	 */
	public int getWeekOfYear() {
		GregorianCalendar calendar = new GregorianCalendar(getYear(),
				getMonth(), getDay());
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * ת��Ϊ{@link java.util.Date}��
	 * 
	 * @return
	 */
	public Date toDate() {
		return new Date(getMillis());
	}

	/*
	 * fangj �������л������޸� ���� Javadoc��
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return value == null ? 17 : value.hashCode();
	}

	private static String internalParse(String sDate) {

		if (sDate == null)
			throw new IllegalArgumentException("invalid UFDate: " + sDate);

		sDate = sDate.trim();
		String[] tokens = new String[3];

		StringTokenizer st = new StringTokenizer(sDate, "-/.");

		if (st.countTokens() != 3) {
			throw new IllegalArgumentException("invalid UFDate: " + sDate);
		}

		int i = 0;
		while (st.hasMoreTokens()) {
			tokens[i++] = st.nextToken().trim();
		}

		try {
			int year = Integer.parseInt(tokens[0]);
			int month = Integer.parseInt(tokens[1]);
			if (month < 1 || month > 12)
				throw new IllegalArgumentException("invalid UFDate: " + sDate);
			int day = Integer.parseInt(tokens[2]);

			int MONTH_LENGTH[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
					31 };
			int LEAP_MONTH_LENGTH[] = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31,
					30, 31 };
			int daymax = isLeapYear(year) ? LEAP_MONTH_LENGTH[month - 1]
					: MONTH_LENGTH[month - 1];

			if (day < 1 || day > daymax)
				throw new IllegalArgumentException("invalid ufdate: " + sDate);

			String strYear = tokens[0];
			for (int j = strYear.length(); j < 4; j++) {
				if (j == 3) {
					strYear = "2" + strYear;
				} else {
					strYear = "0" + strYear;
				}
			}

			String strMonth = String.valueOf(month);
			if (strMonth.length() < 2)
				strMonth = "0" + strMonth;
			String strDay = String.valueOf(day);
			if (strDay.length() < 2)
				strDay = "0" + strDay;
			return strYear + "-" + strMonth + "-" + strDay;
		} catch (Throwable thr) {
			if (thr instanceof IllegalArgumentException) {
				throw (IllegalArgumentException) thr;
			} else {
				throw new IllegalArgumentException("invalid ufdate: " + sDate);
			}
		}

	}

	private static String toDateString(int year, int month, int day) {
		String strYear = String.valueOf(year);
		for (int j = strYear.length(); j < 4; j++)
			strYear = "0" + strYear;
		String strMonth = String.valueOf(month);
		if (strMonth.length() < 2)
			strMonth = "0" + strMonth;
		String strDay = String.valueOf(day);
		if (strDay.length() < 2)
			strDay = "0" + strDay;
		return strYear + "-" + strMonth + "-" + strDay;

	}

	private static String toDateString(Date dt) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		cal.setTime(dt);
		return toDateString(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * ����ַ��������ת������Ч���ڴ�������ת���� /n �������ڣ�(2001-5-28 13:28:29) UIBeanʹ��
	 * 
	 * @return java.lang.String
	 * @param sDate
	 *            java.lang.String
	 */
	public static String getValidUFDateString(String sDate) {
		return internalParse(sDate);
	}

}