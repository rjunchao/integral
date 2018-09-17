package com.xbkj.common.jdbc.framework.type;

import java.io.*;

/**
 * Blob����
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2005-2-3
 * Time: 9:38:10
 */
public class BlobParamType implements SQLParamType {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = -8160659150199130371L;
    Object blob = null;
    byte bytes[] = null;
    InputStream input = null;
    int length = -1;


    public BlobParamType(Object blob) {
        this.blob = blob;
    }

    public BlobParamType(byte[] bytes) {
        this.bytes = bytes;
        this.length = bytes.length;
    }

    public BlobParamType(InputStream input, int length) {
        this.input = input;
        this.length = length;
    }

    public Object getBlob() {
        return blob;
    }


    public byte[] getBytes() {
        if (bytes == null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(blob);
                oos.flush();
                baos.flush();
                bytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public InputStream getInputStream() {
        if (input == null)
            input = new ByteArrayInputStream(getBytes());
        return input;
    }

    public int getLength() {
        if (length == -1)
            length = getBytes().length;
        return length;
    }
}
