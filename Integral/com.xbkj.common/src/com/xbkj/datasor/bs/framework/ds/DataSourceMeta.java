package com.xbkj.datasor.bs.framework.ds;

import java.io.Serializable;

/**
 * User: �ι���
 * Date: 2005-11-9
 * Time: 11:27:11
 * 
 * The IDataSourceMeta implementation
 */
public class DataSourceMeta implements Serializable {

    private static final long serialVersionUID = 1774004595340815036L;

    private String dataSourceName = null;

    private String oidMark = null;

    private String databaseUrl = "";

    private String user = "";

    private String password = "";

    private String driverClassName = "";

    protected DataSourceMeta() {

    }

    public DataSourceMeta(String adataSourceName, String aoidMark, String databaseUrl, String user, String password,
            String driverClassName) {
        dataSourceName = adataSourceName;
        oidMark = aoidMark;
        this.databaseUrl = databaseUrl;
        this.user = user;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public String getOIDMark() {
        return oidMark;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

}
