package com.xbkj.basic.vo.pub.lang;

/**
 * �������ͷ�װ ����:����
 */
public final class UFBoolean implements java.io.Serializable,Comparable{

    /**
     * The <code>Boolean</code> object corresponding to the primitive value
     * <code>true</code>.
     */
    public static final UFBoolean TRUE = new UFBoolean(true);

    /**
     * The <code>Boolean</code> object corresponding to the primitive value
     * <code>false</code>.
     */
    public static final UFBoolean FALSE = new UFBoolean(false);

    private static final long serialVersionUID = -2971431361057093474L;

    private boolean value = false;
    

    /**
     * ���ַ��͹���UFBoolean---'Y'��'y'ʱΪtrue,����Ϊfalse��
     */
    public UFBoolean(char ch) {
        super();
        value = (ch == 'Y' || ch == 'y');
    }

    /**
     * ���ַ���UFBoolean---"Y"��"y"ʱΪtrue,����Ϊfalse��
     */
    public UFBoolean(String val) {
        if (val != null
                && val.length() > 0
                && (val.equalsIgnoreCase("true") || val.charAt(0) == 'Y' || val
                        .charAt(0) == 'y'))
            value = true;
        else
            value = false;
    }

    /**
     * �������͹���UFBoolean��
     */
    public UFBoolean(boolean b) {
        super();
        value = b;
    }

    /**
     * ���ض���boolean��ֵ
     */
    public boolean booleanValue() {
        return value;
    }

    public static UFBoolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }
    
    public static UFBoolean valueOf(String val) {
        if (val != null
                && val.length() > 0
                && (val.equalsIgnoreCase("true") || val.charAt(0) == 'Y' || val
                        .charAt(0) == 'y'))
            return TRUE;
        else
            return FALSE;
    }

    /**
     * �Ƚ�������ֵ�Ƿ����(���ǱȽ϶������Ƿ�Ϊͬһ����)��
     */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof UFBoolean)) {
            return value == ((UFBoolean) obj).booleanValue();
        }
        return false;
    }

    /**
     * ��ɽ��շ���ɢ�д���---��Boolean��ͬ��
     */
    public int hashCode() {
        return value ? 1231 : 1237;
    }

    /**
     * ���ر�ʾ�ö���String��ֵ��trueʱΪ"Y",falseʱΪ"Y"��
     */
    public String toString() {
        return value ? "Y" : "N";
    }

	public int compareTo(Object o)
	{
		if(o==null) return 1;
		return toString().compareTo(o.toString());
	}
}