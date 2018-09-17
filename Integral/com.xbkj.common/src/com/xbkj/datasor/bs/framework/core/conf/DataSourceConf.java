package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;

public class DataSourceConf implements Serializable {
   
    private static final long serialVersionUID = -1665102543382619143L;

    private String oidMark = null;

    private String databaseUrl = "";

    private String user = "";

    private String password = "";

    private String driverClassName = "";
    
    private String dataSourceName;
    
    private String databaseType;

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getOidMark() {
        return oidMark;
    }

    public void setOidMark(String oidMark) {
        this.oidMark = oidMark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

}
