/*
 * �������� 2005-8-22
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework;

import java.io.Serializable;

/**
 * @nopublish
 * @author hey
 *
 * TODO Ҫ��Ĵ���ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class DBMetaInfo implements Serializable{

    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 1230267680871744361L;
    //��ݿ�����
    private int type;
    //��ݿ�汾
    private int Version; 
    //��ݿ����
    private String name;
    //�Ƿ�֧�������
    boolean isSupportBatch=true;
    //�Ƿ�JDBC-ODBC��
    boolean isODBC=false;
    //�û���
    String userName;
    //
    String catalog;
    
   
    
   
    /**
     * @param type
     * @param version
     * @param name
     * @param isSupportBatch
     * @param isODBC
     */
    public DBMetaInfo(int type, int version, String name, boolean isSupportBatch,
            boolean isODBC) {
        super();
        this.type = type;
        Version = version;
        this.name = name;
        this.isSupportBatch = isSupportBatch;
        this.isODBC = isODBC;
    }
    
    
    
    public String getCatalog() {
        return catalog;
    }
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean isODBC() {
        return isODBC;
    }
    public void setODBC(boolean isODBC) {
        this.isODBC = isODBC;
    }
    public boolean isSupportBatch() {
        return isSupportBatch;
    }
    public void setSupportBatch(boolean isSupportBatch) {
        this.isSupportBatch = isSupportBatch;
    }
    /**
     * @return ���� name��
     */
    protected String getName() {
        return name;
    }
    /**
     * @param name Ҫ���õ� name��
     */
    protected void setName(String name) {
        this.name = name;
    }
    /**
     * @return ���� type��
     */
    protected int getType() {
        return type;
    }
    /**
     * @param type Ҫ���õ� type��
     */
    protected void setType(int type) {
        this.type = type;
    }
    /**
     * @return ���� version��
     */
    protected int getVersion() {
        return Version;
    }
    /**
     * @param version Ҫ���õ� version��
     */
    protected void setVersion(int version) {
        Version = version;
    }
}
