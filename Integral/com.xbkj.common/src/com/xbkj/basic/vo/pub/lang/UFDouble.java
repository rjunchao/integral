package com.xbkj.basic.vo.pub.lang;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * NCϵͳ�ĸ������ࡣͳһ���?����㣬��ǰ�㷨�ɴ���45λ��Ч���֡�
 * 
 * @author��zhw MAX VALUE : (+ -) 999999999 999999999 999999999 . 999999999
 *             �������Ч�ʲ��� :...100000�μӷ�....... total use time:801
 *             end:12099276885989120016302438418812825600000 �������Ч�ʲ��� :...100000
 *             00 ��double �˷� .... total use time:3084 �������Ч�ʲ���
 *             :...100000�γ˷�....... total use time:2224
 * @�޸�:���� 1.�������������� UFDouble(String,int) 2.�޸�UFDouble(long,int)����
 *        3.�޸�UFDouble(double,int)�������������� 4.�޸�round(double,int,int)ָ�����
 *        5.�޸�UFDouble(String)���� 6.Ĭ��ָ��Ϊ��8
 *        7.ɾ��ԭ����ʱ�޸�ԭ�����power��powerֻ���ڹ���ʱָ����������ٸı�
 * @�޸� ��ΰ ��� �Կ�ѧ�����֧�� UFDouble(String,int) UFDouble(String) ���һ������
 *     setValue(Double,int) �޸� UFDouble(double,int) �޸� ������ �� BUG ����BUG �ڼӷ���
 *     2001-09-11 A �����˵����ԭʼ���û�е�������� B �ڼӷ�����н���ж�ʱû���жϵ�С��λ
 * @�޸� ���� ȥ��Ϊ0ʱ�ĸ��� 2001-11-09.01
 * @�޸� ���� 2001-11-23.02 �������� trimZero = false;//��ѹ��С����β����0
 * @�޸� ���� 2001-11-29.01 �޸�UFDouble(String)��UFDouble(String,int)���죭����������
 * @�޸� ���� 2001-11-30.01 �޸�UFDouble(String)��UFDouble(String,int)���죭��֧�ֶ�","�Ľ���
 * 
 * 
 */
public class UFDouble extends java.lang.Number implements java.io.Serializable,
		Comparable {
	static final long serialVersionUID = -809396813980155342L;

	private int power = DEFAULT_POWER;

	/**
	 * Rounding mode to round away from zero. Always increments the digit prior
	 * to a non-zero discarded fraction. Note that this rounding mode never
	 * decreases the magnitude of the calculated value.
	 */
	public final static int ROUND_UP = 0;

	/**
	 * Rounding mode to round towards zero. Never increments the digit prior to
	 * a discarded fraction (i.e., truncates). Note that this rounding mode
	 * never increases the magnitude of the calculated value.
	 */
	public final static int ROUND_DOWN = 1;

	/**
	 * Rounding mode to round towards positive infinity. If the BigDecimal is
	 * positive, behaves as for <tt>ROUND_UP</tt>; if negative, behaves as for
	 * <tt>ROUND_DOWN</tt>. Note that this rounding mode never decreases the
	 * calculated value.
	 */
	public final static int ROUND_CEILING = 2;

	/**
	 * Rounding mode to round towards negative infinity. If the BigDecimal is
	 * positive, behave as for <tt>ROUND_DOWN</tt>; if negative, behave as for
	 * <tt>ROUND_UP</tt>. Note that this rounding mode never increases the
	 * calculated value.
	 */
	public final static int ROUND_FLOOR = 3;

	/**
	 * Rounding mode to round towards "nearest neighbor" unless both neighbors
	 * are equidistant, in which case round up. Behaves as for <tt>ROUND_UP</tt>
	 * if the discarded fraction is &gt;= .5; otherwise, behaves as for
	 * <tt>ROUND_DOWN</tt>. Note that this is the rounding mode that most of us
	 * were taught in grade school.
	 */
	public final static int ROUND_HALF_UP = 4;

	/**
	 * Rounding mode to round towards "nearest neighbor" unless both neighbors
	 * are equidistant, in which case round down. Behaves as for
	 * <tt>ROUND_UP</tt> if the discarded fraction is &gt; ...5; otherwise,
	 * behaves as for <tt>ROUND_DOWN</tt>.
	 */
	public final static int ROUND_HALF_DOWN = 5;

	/**
	 * Rounding mode to round towards the "nearest neighbor" unless both
	 * neighbors are equidistant, in which case, round towards the even
	 * neighbor. Behaves as for ROUND_HALF_UP if the digit to the left of the
	 * discarded fraction is odd; behaves as for ROUND_HALF_DOWN if it's even.
	 * Note that this is the rounding mode that minimizes cumulative error when
	 * applied repeatedly over a sequence of calculations.
	 */
	public final static int ROUND_HALF_EVEN = 6;

	/**
	 * Rounding mode to assert that the requested operation has an exact result,
	 * hence no rounding is necessary. If this rounding mode is specified on an
	 * operation that yields an inexact result, an <tt>ArithmeticException</tt>
	 * is thrown.
	 */
	public final static int ROUND_UNNECESSARY = 7;

	/** MAX ARRAY LENGTH ��ʾ�����Чλ�� 6 �� 9 */
	private final static int ARRAY_LENGTH = 5;

	private final static int EFFICIENCY_SEATE = 9;

	/** ���� (long) Math.pow(10, EFFICIENCY_SEATE); ע��Ҫ�������ͬ����� */
	private final static long MAX_ONELONG_VALUE = (long) 1E9;

	/** power V1 E POWER 1234566E-4 power ȡֵ 0~-9 */
	private final static long POWER_ARRAY[];

	/**
	 * 1��2��λ��3~7ȡ5��8��9��λ��ע�⣺��������λ���ƣ����UFDouble�� power = -n�� ���� -n
	 * λ�Ͻ��м��㣻������������ƣ����UFDouble�� power = -n������ -��n+1) λ�Ͻ�����λ���㡣
	 */
	public final static int ROUND_TO_ZERO_AND_HALF = 8;

	private byte si = 1;

	/** ����λV */
	private long v[] = new long[ARRAY_LENGTH];
	static {
		POWER_ARRAY = new long[EFFICIENCY_SEATE + 2];
		for (int i = 0; i < POWER_ARRAY.length - 1; i++) {
			POWER_ARRAY[i] = (long) Math.pow(10, EFFICIENCY_SEATE - i);
		}
		POWER_ARRAY[POWER_ARRAY.length - 1] = 0;
	}

	public final static UFDouble ONE_DBL = new UFDouble(1f);

	public final static UFDouble ZERO_DBL = new UFDouble(0f);

	/**
	 * UFDouble ������ע�⡣
	 */
	public UFDouble() {
		super();
	}

	/**
	 * ��ʼ��һ������ double value<br>
	 * default power is -8;
	 * 
	 * 
	 */
	public UFDouble(double d) throws NumberFormatException {
		this(d, DEFAULT_POWER);
	}

	/**
	 * ��ʼ��һ������ double value<br>
	 * newPower ָ��<br>
	 */
	public UFDouble(double d, int newPower) throws NumberFormatException {
		setValue(d, newPower);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-5-30 9:55:31)
	 * 
	 * @param d
	 *            int
	 */
	public UFDouble(int d) {
		this((long) d);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-5-30 9:58:20)
	 * 
	 * @param d
	 *            int
	 * @param pow
	 *            int
	 */
	public UFDouble(int d, int pow) {
		this((long) d, pow);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-5-30 9:54:44)
	 * 
	 * @param d
	 *            long
	 */
	public UFDouble(long d) {
		this(d, DEFAULT_POWER);
	}

	/**
	 * �������<br>
	 * long VALUE
	 */
	public UFDouble(long d, int pow) throws NumberFormatException {
		// v1 = d;
		// power = pow;
		this(d + 0.0, pow);
	}

	/**
	 * ���ж���ķ����л������ӹ���
	 * 
	 * @param dv
	 * @param si
	 * @param pow
	 * @throws NumberFormatException
	 */
	public UFDouble(long[] dv, byte si, int pow) throws NumberFormatException {
		if (dv == null || dv.length != ARRAY_LENGTH) {
			throw new NumberFormatException("array length must be 5");
		}
		this.v = dv;
		this.si = si;
		this.power = pow;
	}

	/**
	 * ��ʼ��һ������ double value<br>
	 * default power is -4;
	 * 
	 * 
	 */
	public UFDouble(Double d) throws NumberFormatException {
		this(d.doubleValue(), DEFAULT_POWER);
	}

	/**
	 * ��ʼ��һ������ double value<br>
	 * default power С��λ���(<9);
	 * 
	 * 
	 */
	public UFDouble(String str) throws NumberFormatException {
		String s = "";
		if (str == null || str.trim().length() == 0)
			s = "0";
		else {
			java.util.StringTokenizer token = new java.util.StringTokenizer(
					str, ",");
			while (token.hasMoreElements()) {
				s += token.nextElement().toString();
			}
			if (s.indexOf('e') >= 0 || s.indexOf('E') >= 0) {
				setValue(Double.parseDouble(s), -8);
				return;
			}
			if (s.charAt(0) == '-') {
				si = -1;
				s = s.substring(1);
			} else if (s.charAt(0) == '+')
				s = s.substring(1);
		}
		int loc = s.indexOf('.');
		if (loc >= 0) {
			String s1 = s.substring(loc + 1);
			if (s1.length() > -DEFAULT_POWER) {
				if (-DEFAULT_POWER >= EFFICIENCY_SEATE)
					s1 = s1.substring(0, EFFICIENCY_SEATE);
				else
					s1 = s1.substring(0, 1 - DEFAULT_POWER);
			}
			power = -s1.length();
			if (power < -EFFICIENCY_SEATE) {
				power = -EFFICIENCY_SEATE;
				s1 = s.substring(loc + 1, EFFICIENCY_SEATE + 1 + loc);
			} else {
				for (int i = s1.length(); i < EFFICIENCY_SEATE; i++)
					s1 += "0";
			}
			v[0] = Long.parseLong(s1);
			s = s.substring(0, loc);
		} else {
			power = 0;
			v[0] = 0;
		}
		/** ����ֻʣ������֣���Ҫ���м��� */
		int len = s.length();
		int sitLoc = 1;
		while (len > 0) {
			String s1 = "";
			if (len > EFFICIENCY_SEATE) {
				s1 = s.substring(len - EFFICIENCY_SEATE);
				s = s.substring(0, len - EFFICIENCY_SEATE);
			} else {
				s1 = s;
				s = "";
			}
			len = s.length();
			v[sitLoc++] = Long.parseLong(s1);
		}
		for (int i = sitLoc; i < v.length; i++)
			v[i] = 0;
		round(ROUND_HALF_UP);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-9 16:43:08)
	 * 
	 * @param s
	 *            java.lang.String
	 * @param newPower
	 *            int
	 */
	public UFDouble(String str, int newPower) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);
		String s = "";
		if (str == null || str.trim().length() == 0)
			s = "0";
		else {
			java.util.StringTokenizer token = new java.util.StringTokenizer(
					str, ",");
			while (token.hasMoreElements()) {
				s += token.nextElement().toString();
			}
			if (s.indexOf('e') >= 0 || s.indexOf('E') >= 0) {
				setValue(Double.parseDouble(s), -newPower);
				return;
			}
			if (s.charAt(0) == '-') {
				si = -1;
				s = s.substring(1);
			} else if (s.charAt(0) == '+')
				s = s.substring(1);
		}
		int loc = s.indexOf('.');
		if (loc >= 0) {
			String s1 = s.substring(loc + 1);
			if (s1.length() > -newPower) {
				if (-newPower >= EFFICIENCY_SEATE)
					s1 = s1.substring(0, EFFICIENCY_SEATE);
				else
					s1 = s1.substring(0, 1 - newPower);
			}
			// power = -s1.length();
			// if (power < newPower) {
			// s1 = s.substring(loc + 1, -newPower + 1 + loc);
			// }
			power = newPower;
			for (int i = s1.length(); i < EFFICIENCY_SEATE; i++)
				s1 += "0";
			v[0] = Long.parseLong(s1);
			s = s.substring(0, loc);
		} else {
			power = newPower;
			v[0] = 0;
		}
		/** ����ֻʣ������֣���Ҫ���м��� */
		int len = s.length();
		int sitLoc = 1;
		while (len > 0) {
			String s1 = "";
			if (len > EFFICIENCY_SEATE) {
				s1 = s.substring(len - EFFICIENCY_SEATE);
				s = s.substring(0, len - EFFICIENCY_SEATE);
			} else {
				s1 = s;
				s = "";
			}
			len = s.length();
			v[sitLoc++] = Long.parseLong(s1);
		}
		for (int i = sitLoc; i < v.length; i++)
			v[i] = 0;
		round(ROUND_HALF_UP);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-17 14:42:28)
	 * 
	 * @param value
	 *            java.math.BigDecimal
	 */
	public UFDouble(java.math.BigDecimal value) {
		this(value.toString(), value.scale());
	}

	/**
	 * ����Ĺ��ܡ���;�������Եĸ�ģ��Լ�����ִ��ǰ������״̬��
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 * @param fd
	 *            nc.vo.pub.lang.UFDouble
	 */
	public UFDouble(UFDouble fd) {
		si = fd.si;
		for (int i = 0; i < v.length; i++) {
			v[i] = fd.v[i];
		}
		power = fd.power;
	}

	/**
	 * ����һ����
	 */
	public UFDouble add(double d1) {
		return add(new UFDouble(d1));
	}

	/**
	 * ����һ����
	 */
	public UFDouble add(UFDouble ufd) {
		return add(ufd, DEFAULT_POWER, ROUND_HALF_UP);
	}

	/**
	 * ����һ����
	 */
	public UFDouble add(UFDouble ufd, int newPower) {
		return add(ufd, newPower, ROUND_HALF_UP);
	}

	/**
	 * ����һ���� ufd<br>
	 * 
	 * @param newPower
	 *            ָ��<br>
	 * @param roundingMode
	 *            ��λ��ʽ<br>
	 */
	public UFDouble add(UFDouble ufd, int newPower, int roundingMode) {
		/** �����ж�POWER�Ĵ�С */
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		UFDouble fdnew = new UFDouble(this);
		//
		fdnew.power = newPower;
		//
		fdnew.addUp0(ufd, newPower, roundingMode);
		return fdnew;
	}

	/**
	 * ����һ����
	 */
	private void addUp0(double ufd) {
		addUp0(new UFDouble(ufd), power, ROUND_HALF_UP);
	}

	/**
	 * ����һ���� ufd<br>
	 * 
	 * @param newPower
	 *            ָ��<br>
	 * @param roundingMode
	 *            ��λ��ʽ<br>
	 */
	private void addUp0(UFDouble ufd, int newPower, int roundingMode) {
		/** �����ж�POWER�Ĵ�С */
		// power = newPower > 0 ? 0 : ((newPower >= -9) ? newPower : -9);
		toPlus();
		ufd.toPlus();
		for (int i = 0; i < v.length; i++) {
			v[i] += ufd.v[i];
		}
		judgNegative();
		adjustIncluedFs();
		/** ��toPlus�� ufd ���еķ�ű�λ���е������ */
		ufd.judgNegative();
		round(roundingMode);
	}

	/**
	 * �ڸ���λ���Ͻ����˴������Ҫ����ֵ���е�������� ������BUG��û�н��������û�н������������ǽ�ȥ��
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 */
	private void adjustIncluedFs() {
		for (int i = 1; i < v.length; i++) {
			if (v[i - 1] < 0) {
				v[i]--;
				v[i - 1] += MAX_ONELONG_VALUE;
			} else {
				v[i] = v[i] + v[i - 1] / MAX_ONELONG_VALUE;
				v[i - 1] = v[i - 1] % MAX_ONELONG_VALUE;
			}
		}
	}

	/**
	 * ����Ĺ��ܡ���;�������Եĸ�ģ��Լ�����ִ��ǰ������״̬��
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 */
	private void adjustNotIncluedFs() {
		for (int i = 1; i < v.length; i++) {
			v[i] = v[i] + v[i - 1] / MAX_ONELONG_VALUE;
			v[i - 1] = v[i - 1] % MAX_ONELONG_VALUE;
		}
	}

	/**
	 * Compares this object with the specified object for order. Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 * <p>
	 * 
	 * The implementor must ensure <tt>sgn(x.compareTo(y)) ==
	 * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>. (This
	 * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
	 * <tt>y.compareTo(x)</tt> throws an exception.)
	 * <p>
	 * 
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
	 * <tt>x.compareTo(z)&gt;0</tt>.
	 * <p>
	 * 
	 * Finally, the implementer must ensure that <tt>x.compareTo(y)==0</tt>
	 * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for all
	 * <tt>z</tt>.
	 * <p>
	 * 
	 * It is strongly recommended, but <i>not</i> strictly required that
	 * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>. Generally speaking, any
	 * class that implements the <tt>Comparable</tt> interface and violates this
	 * condition should clearly indicate this fact. The recommended language is
	 * "Note: this class has a natural ordering that is inconsistent with
	 * equals."
	 * 
	 * @param o
	 *            the Object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object.
	 * 
	 * @throws ClassCastException
	 *             if the specified object's type prevents it from being
	 *             compared to this Object.
	 */
	public int compareTo(Object o) {
		return toDouble().compareTo(((UFDouble) o).toDouble());
	}

	/**
	 * ����Ĺ��ܡ���;�������Եĸ�ģ��Լ�����ִ��ǰ������״̬��
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 */
	private void cutdown() {
		int p = -power;
		v[0] = v[0] / POWER_ARRAY[p] * POWER_ARRAY[p];
	}

	public UFDouble div(double d1) {
		double d = getDouble() / d1;
		UFDouble ufd = new UFDouble(d, DEFAULT_POWER);
		ufd.round(ROUND_HALF_UP);
		return ufd;
	}

	public UFDouble div(UFDouble ufd) {
		double d = getDouble() / ufd.getDouble();
		UFDouble ufdNew = new UFDouble(d, DEFAULT_POWER);
		ufdNew.round(ROUND_HALF_UP);
		return ufdNew;
	}

	public UFDouble div(UFDouble ufd, int newPower) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		double d = getDouble() / ufd.getDouble();
		UFDouble ufdNew = new UFDouble(d, newPower);
		ufdNew.round(ROUND_HALF_UP);
		return ufdNew;
	}

	/**
	 * ����һ���� ufd<br>
	 * ������Ҫ���ڼӼ���λ���г���м򻯣�ֱ�����ø�������д��?
	 * 
	 * @param newPower
	 *            ָ��<br>
	 * @param roundingMode
	 *            ��λ��ʽ<br>
	 */
	public UFDouble div(UFDouble ufd, int newPower, int roundingMode) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		double d = getDouble() / ufd.getDouble();
		UFDouble ufdNew = new UFDouble(d);
		ufdNew.power = newPower;
		ufdNew.round(roundingMode);
		return ufdNew;
	}

	/**
	 * ȡ�����ֵ<br>
	 */
	public double doubleValue() {
		double d = 0;
		for (int i = v.length - 1; i >= 0; i--) {
			d *= MAX_ONELONG_VALUE;
			d += v[i];
		}
		d /= MAX_ONELONG_VALUE;
		return d * si;
	}

	/**
	 * Returns the value of the specified number as a <code>float</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>float</code>.
	 */
	public float floatValue() {
		return (float) getDouble();
	}

	/**
	 * ȡ�����ֵ<br>
	 */
	public double getDouble() {
		return this.doubleValue();
	}

	/**
	 * �÷������Ƽ��ⲿʹ��
	 * 
	 * @return
	 */
	public long[] getDV() {
		return this.v;
	}

	/**
	 * �÷������Ƽ��ⲿʹ��
	 * 
	 * @return
	 */
	public byte getSIValue() {
		return this.si;
	}

	/**
	 * Returns the value of the specified number as an <code>int</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>int</code>.
	 */
	public int intValue() {
		return (int) getDouble();
	}

	/**
	 * �жϵ�ǰ����ֵ�Ƿ��Ǹ�������Ҫ�ĵ���
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 */
	private void judgNegative() {
		/** at first adjust if is ���� */
		boolean isFs = false;
		for (int i = v.length - 1; i >= 0; i--) {
			if (v[i] < 0) {
				/** �Ǹ��� */
				isFs = true;
				break;
			}
			if (v[i] > 0)
				break;
		}
		if (isFs) {
			for (int i = 0; i < v.length; i++)
				v[i] = -v[i];
			si = -1;
		}
	}

	/**
	 * Returns the value of the specified number as a <code>long</code>. This
	 * may involve rounding.
	 * 
	 * @return the numeric value represented by this object after conversion to
	 *         type <code>long</code>.
	 */
	public long longValue() {
		long d = 0;
		/** ȥ����λ */
		for (int i = v.length - 1; i > 0; i--) {
			d *= MAX_ONELONG_VALUE;
			d += v[i];
		}
		return d * si;
	}

	/**
	 * ���Գ��� �������ڣ�(2001-4-11 9:07:13)
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(String[] args) {
		/*
		 * UFDouble ufd0 = new UFDouble("1.12345678"); UFDouble ufd1 = new
		 * UFDouble("12.12345678",-9); UFDouble ufd2 = new
		 * UFDouble("123.12345678945730"); UFDouble ufd3 = new
		 * UFDouble("1234.12345678945730",-9); UFDouble ufd4 = new
		 * UFDouble("12345.1234567895730"); UFDouble ufd5 = new
		 * UFDouble("123456.1234567895730",-9); UFDouble ufd6 = new
		 * UFDouble("1234567.123456789573094307463"); UFDouble ufd7 = new
		 * UFDouble("12345678.123456789573094307463",-9); UFDouble ufd8 = new
		 * UFDouble("123456789.123456789573094307463",-4); UFDouble ufd9 = new
		 * UFDouble("1.1"); UFDouble ufd10 =ufd9.add(new UFDouble(0.0));
		 * UFDouble ufd11 =ufd9.sub(new UFDouble(0.0)); UFDouble ufd12
		 * =ufd9.multiply(new UFDouble(1.0)); UFDouble ufd13 =ufd9.div(new
		 * UFDouble(1.0)); UFDouble ufd14 =ufd9.div(1.0);
		 * System.out.println("ufd1 "+ufd1); UFDouble ufd2 = new
		 * UFDouble(10.0000); ufd2.setTrimZero(true); System.out.println("ufd2
		 * "+ufd2); UFDouble ufd3 = new UFDouble(-10.0); ufd3.setTrimZero(true);
		 * System.out.println("ufd3 "+ufd3); UFDouble ufd4 = new UFDouble(100.);
		 * ufd4.setTrimZero(true); System.out.println("ufd4 "+ufd4); UFDouble
		 * ufd5 = new UFDouble(0.000); ufd5.setTrimZero(true);
		 * System.out.println("ufd5 "+ufd5);
		 * 
		 * double d=9999999999999998.00; UFDouble ufd1 = new UFDouble(d);
		 * System.out.println("9999999999999998.00 is "+ufd1); UFDouble ufd2 =
		 * new UFDouble(99999999999999999.00);
		 * System.out.println("99999999999999999.00"+ufd2); UFDouble ufd3 = new
		 * UFDouble("9999999999999999.00"); System.out.println("ufd3 "+ufd3);
		 * UFDouble ufd4 = new UFDouble("99999999999999999.00");
		 * System.out.println("ufd4 "+ufd4);
		 * 
		 * UFDouble ufd1 = new UFDouble(0.1); UFDouble ufd2 = new UFDouble(3.0);
		 * UFDouble ufd3 = new UFDouble(-10); UFDouble ufd4 = new UFDouble(-3);
		 * System.out.println(ufd3 + "*" + ufd4 + "=" + ufd4.multiply(ufd3));
		 * System.out.println(ufd3 + "== " + ufd4 + "=" + ufd4.equals(ufd3));
		 * System.out.println(ufd4 + "== " + ufd4 + "=" + ufd4.equals(ufd4));
		 * System.out.println(ufd4 + "== " + (-3) + "=" + ufd4.equals(new
		 * Integer(-3))); System.out.println(new UFDouble("-10.5e15")); double[]
		 * db = { 1.5, 0.3, 0.6, 0.75555555, 0.633333, 0.711111, 0.8679 };
		 * System.out.println(ufd.add(ufd1)); System.out.println(ufd.sub(ufd1));
		 * System.out.println(ufd.multiply(ufd1));
		 * System.out.println(ufd.div(ufd1));
		 * System.out.println(ufd.multiply(ufd2)); System.out.println(ufd + "/"
		 * + ufd2 + "=" + ufd.div(ufd2)); System.out.println(ufd.sum(db, -1));
		 * System.out.println(ufd.sum(db, -2)); System.out.println(ufd.sum(db,
		 * -3, 0)); System.out.println(ufd.sum(db, -3));
		 * System.out.println(ufd.sum(db, -4)); System.out.println(ufd.sum(db,
		 * -5)); System.out.println(ufd.sum(db, -6));
		 * System.out.println(ufd.sum(db, -7)); System.out.println(ufd.sum(db,
		 * -8)); System.out.println(ufd.sum(db, -8)); // System.out.println(new
		 * UFDouble(10).multiply(1.0)); System.out.println(new UFDouble(10.0));
		 * System.out.println(new UFDouble(10.37));
		 * 
		 * System.out.println(new UFDouble("12345678901234567890.12345678"));
		 * System.out.println(new UFDouble("12345678901234567890.1234567"));
		 * System.out.println(new UFDouble("12345678901234567890.123457"));
		 * java.util.Random r = new java.util.Random(100); double d = 0; int
		 * power = -5; double d1 = Math.pow(10, power); ufd = new UFDouble(0,
		 * power); for (int i = 0; i < 20; i++) { long l2 = r.nextLong(); double
		 * d2 = l2 * 1.0 * d1; d += d2; ufd1 = new UFDouble(d2, power); ufd =
		 * ufd.add(ufd1); System.out.println( " d:" + d + " ufd:" + ufd +
		 * " d2 :" + new UFDouble(d2, power) + ":" + (d - ufd.getDouble())); }
		 * 
		 * System.out.println("sub:------"); for (int i = 0; i < 20; i++) { long
		 * l2 = r.nextLong(); double d2 = l2 * 1.0 * d1; d -= d2; ufd =
		 * ufd.sub(new UFDouble(d2, power)); System.out.println( " d:" + d + "
		 * ufd:" + ufd + " d2 :" + new UFDouble(d2, power) + ":" + (d -
		 * ufd.getDouble())); } System.out.println("TEST SUM:"); double
		 * sumarray[] = new double[100]; d = 0; for (int i = 0; i < 50; i++) {
		 * sumarray[i] = Math.abs(r.nextLong() * 1000.0); d += sumarray[i]; }
		 * for (int i = 0; i < 50; i++) { sumarray[50 + i] =
		 * Math.abs(r.nextLong() * 1.0 / 1000000.00); d += sumarray[i + 50]; }
		 * ufd = sum(sumarray, -5, 0); System.out.println("sum double:" + d + "
		 * sum jd:" + ufd + " " + sum(sumarray)); System.out.println("Test
		 * mutil"); d = 0; ufd = new UFDouble(0); for (int i = 0; i < 50; i++) {
		 * d1 = sumarray[i] * sumarray[i + 50]; d += d1; ufd1 = new
		 * UFDouble(sumarray[i], power); ufd2 = new UFDouble(sumarray[i + 50],
		 * power); UFDouble ufdm = ufd1.multiply(ufd2);
		 * System.out.println("mulity double:" + d1 + " mulity jd:" + ufdm); ufd
		 * = ufd.add(ufd1.multiply(ufd2)); } System.out.println("mulity double
		 * total :" + d + " mulity jd total :" + ufd);
		 * 
		 * System.out.println("�������Ч�ʲ��� :...100000�μӷ�......."); ufd = new
		 * UFDouble(); long l2 = r.nextLong(); double d2 = l2 * 1.0 * d1; d2 =
		 * Math.abs(d2); ufd1 = new UFDouble(d2, power); long begin =
		 * System.currentTimeMillis(); for (int i = 0; i < 100000; i++) { ufd =
		 * ufd.add(ufd1); } long end = System.currentTimeMillis();
		 * System.out.println("total use time:" + (end - begin));
		 * System.out.println("end:" + ufd); System.out.println("�������Ч�ʲ���
		 * :...100000 00 ��double �˷� ...."); begin = System.currentTimeMillis();
		 * for (int i = 0; i < 10000000; i++) { d2 *= 1000.0; } end =
		 * System.currentTimeMillis(); System.out.println("total use time:" +
		 * (end - begin));
		 * 
		 * System.out.println("�������Ч�ʲ��� :...100000�γ˷�......."); begin =
		 * System.currentTimeMillis(); ufd1 = new UFDouble(d2, power); for (int
		 * i = 0; i < 100000; i++) { ufd = ufd.multiply(ufd1); } end =
		 * System.currentTimeMillis(); System.out.println("total use time:" +
		 * (end - begin));
		 * 
		 * UFDouble ufd000 = new UFDouble(10.000000, -4);
		 * System.out.println(ufd000.toString());
		 * 
		 * UFDouble ufd111 = new UFDouble(10.123456, -4); UFDouble ufd222 =
		 * ufd111.add(new UFDouble(10), -2, 4); System.out.println(ufd111);
		 * System.out.println(ufd222);
		 */
	}

	public UFDouble multiply(double d1) {
		/** �����ж�POWER�Ĵ�С */
		UFDouble ufD1 = new UFDouble(d1);
		return multiply(ufD1, DEFAULT_POWER, ROUND_HALF_UP);
	}

	public UFDouble multiply(UFDouble ufd) {
		return multiply(ufd, DEFAULT_POWER, ROUND_HALF_UP);
	}

	public UFDouble multiply(UFDouble ufd, int newPower) {
		return multiply(ufd, newPower, ROUND_HALF_UP);
	}

	/**
	 * ����һ���� ufd<br>
	 * 
	 * @param newPower
	 *            ָ��<br>
	 * @param roundingMode
	 *            ��λ��ʽ<br>
	 */
	public UFDouble multiply(UFDouble ufd, int newPower, int roundingMode) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		long mv[] = new long[ARRAY_LENGTH * 2 + 1];
		for (int i = 0; i < mv.length; i++) {
			mv[i] = 0;
		}
		for (int i = 0; i < v.length; i++) {
			for (int j = 0; j < v.length; j++) {
				long l = v[i] * ufd.v[j];
				mv[i + j] += l % MAX_ONELONG_VALUE;
				mv[i + j + 1] += l / MAX_ONELONG_VALUE;
			}
		}
		UFDouble fdnew = new UFDouble();
		fdnew.power = newPower;
		// ZhangYang�޸�
		fdnew.si = this.si;
		//
		fdnew.si = (byte) (fdnew.si * ufd.si);
		for (int i = 0; i < v.length; i++) {
			fdnew.v[i] = mv[i + 1];
		}
		fdnew.round(roundingMode);
		return fdnew;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-11 15:35:15)
	 * 
	 * @return ierp.pub.vo.data.UFDouble
	 * @param d
	 *            double
	 * @param roundingtype
	 *            int
	 */
	private UFDouble round(double d, int newPower, int roundingMode) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		boolean increment = true;
		switch (roundingMode) {
		case ROUND_UP:
			increment = true;
			break;
		case ROUND_CEILING:
			increment = false;
			break;
		case ROUND_FLOOR:
			increment = (d > 0);
			break;
		case ROUND_DOWN:
			increment = (d < 0);
			break;
		case ROUND_TO_ZERO_AND_HALF:
			/*
			 * // һ���������λ���ƣ�1��2��λ��3~7ȡ5��8��9��λ��ֻ�������� long l = (long) (d /
			 * Math.pow(10, newPower)); double fraction = d - l; if (fraction <
			 * 0.3) { fraction = 0; } else if (fraction < 0.8) { fraction = 0.5;
			 * } else { fraction = 1; } return new UFDouble(l + fraction,
			 * newPower);
			 */
		}
		long l = (long) (d + ((increment) ? 0.5 : 0));
		return new UFDouble(l, newPower);
	}

	/**
	 * ���յ�ǰ��POWER��ȥ��С����Ҫ�Ĳ��� ���� 999999.99123456 power = -2 result is
	 * 999999.990000000 ��Ҫ�Ը�����λ��ʽ���п��� �������ڣ�(2001-4-11 15:35:15)
	 * 
	 * @return ierp.pub.vo.data.UFDouble
	 * @param d
	 *            double
	 * @param roundingtype
	 *            int
	 */
	private void round(int roundingMode) {
		boolean increment = true;
		switch (roundingMode) {
		case ROUND_UP:
			increment = true;
			break;
		case ROUND_CEILING:
			increment = si == 1;
			break;
		case ROUND_FLOOR:
			increment = si == -1;
			break;
		case ROUND_DOWN:
			increment = false;
			// si == -1;
			break;
		case ROUND_TO_ZERO_AND_HALF:
			// һ���������λ���ƣ�1��2��λ��3~7ȡ5��8��9��λ��ֻ��������
			/*
			 * long l = (long)(d / Math.pow(10, newPower)); double fraction = d
			 * - l; if (fraction < 0.3) { fraction = 0; } else if (fraction <
			 * 0.8) { fraction = 0.5; } else { fraction = 1; } return new
			 * UFDouble(l + fraction, newPower);
			 */
		}
		int p = -power;
		long vxs = POWER_ARRAY[p + 1];
		/** ���ڲ����� */
		if (increment) {
			v[0] += vxs * 5;
			adjustNotIncluedFs();
		}
		cutdown();
		// Ϊ0ʱȥ������
		boolean isZero = true;
		for (int i = 0; i < v.length; i++) {
			if (v[i] != 0) {
				isZero = false;
				break;
			}
		}
		if (si == -1 && isZero)
			si = 1;
		//	
	}

	/**
	 * @see #ROUND_UP
	 * @see #ROUND_DOWN
	 * @see #ROUND_CEILING
	 * @see #ROUND_FLOOR
	 * @see #ROUND_HALF_UP
	 * @see #ROUND_HALF_DOWN
	 * @see #ROUND_HALF_EVEN
	 * @see #ROUND_UNNECESSARY
	 */
	public UFDouble setScale(int power, int roundingMode) {
		return multiply(ONE_DBL, power, roundingMode);
	}

	/**
	 * ��ʼ��һ������ double value<br>
	 * newPower ָ��<br>
	 */
	private void setValue(double d, int newPower) throws NumberFormatException {
		double dd, ld;

		if (d < 0) {
			d = -d;
			si = -1;
		}
		dd = d;
		/** power from 0 to -9 */
		// power = newPower > 0 ? -newPower : newPower;
		// if (power < -9)
		// power = -9;
		power = getValidPower(newPower);

		/** ����POWER ����ڲ�ͳһʹ��С����9Ϊ���� */
		double dxs = d % 1;
		d -= dxs;
		ld = d;
		for (int i = 1; i < v.length; i++) {
			v[i] = (long) (d % MAX_ONELONG_VALUE);
			d = d / MAX_ONELONG_VALUE;
		}
		long v2 = 0;
		if (dxs == 0.0)
			v2 = (long) (dxs * MAX_ONELONG_VALUE);
		else {
			if (dd / ld == 1.0) {
				dxs = 0.0;
				v2 = (long) (dxs * MAX_ONELONG_VALUE);
			} else {
				if (power <= -8) {
					int iv = (int) v[2];
					if (iv != 0) {
						if (iv >= 1000000)
							power = -0;
						else if (iv >= 100000)
							power = -1;
						else if (iv >= 10000)
							power = -2;
						else if (iv >= 1000)
							power = -3;
						else if (iv >= 100)
							power = -4;
						else if (iv >= 10)
							power = -5;
						else if (iv >= 1)
							power = -6;
					} else {
						iv = (int) v[1];
						if (iv >= 100000000)
							power = -7;
					}
					if (power < 0) {
						int ii = -power;
						double d1;
						int i2 = 1;
						double dxs1;
						for (int i = 1; i < ii; i++) {
							i2 *= 10;
							dxs1 = ((double) Math.round(dxs * i2)) / i2;
							d1 = ld + dxs1;
							if (dd / d1 == 1.0) {
								dxs = dxs1;
								break;
							}
						}
					}
				}
				v2 = (long) ((dxs + 0.00000000001) * MAX_ONELONG_VALUE);
			}
		}
		v[0] = v2;
		round(ROUND_HALF_UP);
	}

	public UFDouble sub(double d1) {
		UFDouble ufd = new UFDouble(d1);
		return sub(ufd, DEFAULT_POWER, ROUND_HALF_UP);
	}

	public UFDouble sub(UFDouble ufd) {
		return sub(ufd, DEFAULT_POWER, ROUND_HALF_UP);
	}

	public UFDouble sub(UFDouble ufd, int newPower) {
		return sub(ufd, newPower, ROUND_HALF_UP);
	}

	/**
	 * ��ȥһ���� ufd<br>
	 * 
	 * @param newPower
	 *            ָ��<br>
	 * @param roundingMode
	 *            ��λ��ʽ<br>
	 */
	public UFDouble sub(UFDouble ufd, int newPower, int roundingMode) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		UFDouble ufdnew = new UFDouble(ufd);
		ufdnew.si = (byte) -ufdnew.si;
		return add(ufdnew, newPower, roundingMode);
	}

	/**
	 * 
	 * ����һ�����<br>
	 * ÿһ������ROUND����<br>
	 * 
	 */
	public static UFDouble sum(double[] dArray) {
		return sum(dArray, DEFAULT_POWER);
	}

	/**
	 * 
	 * ����һ�����<br>
	 * ÿһ������ROUND����<br>
	 * 
	 */
	public static UFDouble sum(double[] dArray, int newPower) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		UFDouble ufd = new UFDouble(0, newPower);
		for (int i = 0; i < dArray.length; i++) {
			ufd.addUp0(dArray[i]);
		}
		return ufd;
	}

	/**
	 * 
	 * ����һ�����<br>
	 * ÿһ������ROUND����<br>
	 * 
	 */

	public static UFDouble sum(double[] dArray, int newPower, int roundingMode) {
		// newPower = newPower > 0 ? -newPower : ((newPower > -9) ? newPower :
		// -9);
		newPower = getValidPower(newPower);

		UFDouble ufd = new UFDouble(0, newPower);
		for (int i = 0; i < dArray.length; i++) {
			UFDouble ufdNew = new UFDouble(dArray[i], newPower);
			ufd.addUp0(ufdNew, newPower, roundingMode);
		}
		return ufd;
	}

	/**
	 * ת��ΪBigDecimal��
	 * <p>
	 * �������ڣ�(2001-4-17 14:45:43)
	 * 
	 * @return java.math.BigDecimal
	 */
	public BigDecimal toBigDecimal() {

		return new BigDecimal(toString());
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-17 14:57:02)
	 * 
	 * @return java.lang.Double
	 */
	public Double toDouble() {
		return new Double(getDouble());
	}

	/**
	 * Ϊ�˽������㣬�����㣬�����λ��ӵ�ÿһ����ֵ��ȥ�� ������мӺ�Ȼ����н�λ���� �����еķ�ű任��ÿһλ��
	 * 
	 * @exception �쳣����
	 * @see ��Ҫ�μ����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * @deprecated�÷����������һ���汾���Ѿ���������
	 */
	private void toPlus() {
		if (si == 1)
			return;
		si = 1;
		for (int i = 0; i < v.length; i++) {
			v[i] = -v[i];
		}
	}

	public String toString() {
		/** û�����λ���ʾǰ��û����Чλ�� */
		boolean addZero = false;
		StringBuffer sb = new StringBuffer();
		if (si == -1)
			sb.append("-");
		for (int i = v.length - 1; i > 0; i--) {
			if (v[i] == 0 && !addZero)
				continue;
			String temp = String.valueOf(v[i]);
			if (addZero) {
				int len = temp.length();
				int addZeroNo = EFFICIENCY_SEATE - len;
				for (int j = 0; j < addZeroNo; j++) {
					sb.append('0');
				}
			}
			sb.append(temp);
			addZero = true;
		}
		if (!addZero)
			sb.append('0');
		/** û��С��λ */
		if (power < 0) {
			sb.append('.');
			for (int j = 0; j < EFFICIENCY_SEATE && j < -power; j++) {
				sb.append((v[0] / POWER_ARRAY[j + 1]) % 10);
			}
		}
		// ѹ��С����β��0
		int index = -1;
		if (isTrimZero()) {
			if (power < 0) {
				String sTemp = sb.toString();
				for (int i = sb.length() - 1; i >= 0; i--) {
					if (sTemp.substring(i, i + 1).equals("0"))
						index = i;
					else {
						if (sTemp.substring(i, i + 1).equals(".")) {
							index = i;
						}
						break;
					}
				}
			}
		}
		if (index >= 0)
			sb = sb.delete(index, sb.length());
		return sb.toString();
	}

	public final static int DEFAULT_POWER = -8;

	private boolean trimZero = false;

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-14 10:37:15)
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble abs() {
		UFDouble fdnew = new UFDouble();
		fdnew.power = this.power;
		fdnew.si = 1;
		for (int i = 0; i < v.length; i++) {
			fdnew.v[i] = v[i];
		}
		return fdnew;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-16 13:29:06)
	 * 
	 * @return int
	 */
	public int getPower() {
		return power;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-23 9:10:54)
	 * 
	 * @return boolean
	 */
	public boolean isTrimZero() {
		return trimZero;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-6-12 10:14:43)
	 * 
	 * @param ufd
	 *            nc.vo.pub.lang.UFDouble
	 */
	public UFDouble mod(UFDouble ufd) {
		return mod(ufd, DEFAULT_POWER, ROUND_HALF_UP);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-6-12 10:14:43)
	 * 
	 * @param ufd
	 *            nc.vo.pub.lang.UFDouble
	 */
	public UFDouble mod(UFDouble ufd, int newPower) {
		return mod(ufd, newPower, ROUND_HALF_UP);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-6-12 10:14:43)
	 * 
	 * @param ufd
	 *            nc.vo.pub.lang.UFDouble
	 */
	public UFDouble mod(UFDouble ufd, int newPower, int roundingMode) {
		UFDouble ufdDiv = div(ufd, 0, ROUND_DOWN);
		UFDouble ufdnew = sub(ufdDiv.multiply(ufd));
		if (ufd.si != si)
			ufdnew = ufdnew.sub(ufd);
		ufdnew.power = newPower;
		ufdnew.round(roundingMode);
		return ufdnew;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-11-23 9:10:54)
	 * 
	 * @param newTrimZero
	 *            boolean
	 */
	public void setTrimZero(boolean newTrimZero) {
		trimZero = newTrimZero;
	}

	private static int getValidPower(int newPower) {
		/** power from 0 to -9 */
		int power = newPower > 0 ? -newPower : newPower;
		if (power < -9)
			power = -9;
		return power;

	}

	@Override
	public int hashCode() {
		int v = 0;
		for (int i = 0; i < this.v.length; i++) {
			v += this.v[i];
		}
		return v * this.si;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof UFDouble) {
			UFDouble ud = (UFDouble) o;
			return si == ud.si && Arrays.equals(v, ud.v);
		}
		return false;

	}
}