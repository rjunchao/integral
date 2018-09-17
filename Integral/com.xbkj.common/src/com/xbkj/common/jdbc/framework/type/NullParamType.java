package com.xbkj.common.jdbc.framework.type;


/**
 * 
 * ������
 * User: ����
 * Date: 2005-2-3
 * Time: 9:08:38
 */
public class NullParamType implements SQLParamType {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -6229083933859489148L;
    int type;

    public NullParamType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
