package com.xbkj.common.jdbc.framework.type;

import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Clob����
 * User: ����
 * Date: 2005-5-16
 * Time: 16:39:24
 * ClobParamType���˵��
 */
public class ClobParamType  implements  SQLParamType{
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 2091823985828181145L;
    String s = null;
    int length = 0;
    Reader reader = null;

    public ClobParamType(String s) {
        try {
            this.s = s;
            length = s.getBytes("iso8859-1").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public ClobParamType(Reader read, int length) {
        this.reader = read;
        this.length = length;
    }

    public Reader getReader() {
        if (reader == null) {
            reader = new StringReader(s);
        }
        return reader;
    }

    public int getLength() {
        return length;
    }


}
