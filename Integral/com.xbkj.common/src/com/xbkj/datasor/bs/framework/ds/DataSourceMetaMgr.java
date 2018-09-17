package com.xbkj.datasor.bs.framework.ds;

import com.xbkj.datasor.bs.framework.common.InvocationInfoProxy;

/**
 * @author cxz
 */
public class DataSourceMetaMgr {

    private static DataSourceMetaMgr mgr = null;

    public static DataSourceMetaMgr getInstance() {
        mgr = new DataSourceMetaMgr();
        return mgr;
    }

    @SuppressWarnings("deprecation")
//    public DataSourceMeta[] getDataSourceMetas() {
//        return ServerConfiguration.getServerConfiguration().getDataSourceMetas();
//    }
//
//    @SuppressWarnings("deprecation")
//    public String[] getDataSourceNames() {
//        return ServerConfiguration.getServerConfiguration().getDataSourceNames();
//    }

    public String getOIDMark(String ds) {
        DataSourceMeta meta = null;//getDataSourceMeta(ds);
        if (meta == null)
            return "AA";
        else
            return meta.getOIDMark();

    }

    @SuppressWarnings("deprecation")
    public DataSourceMeta getDataSourceMeta(String ds) {
//        return ServerConfiguration.getServerConfiguration().getDataSourceMeta(ds);
    	return null;
    }

    public DataSourceMeta getDataSourceMeta() {
        return getDataSourceMeta(getSourceName());
    }

//    @SuppressWarnings("deprecation")
    private String getSourceName() {
        String dataSource = InvocationInfoProxy.getInstance().getUserDataSource();
        if (dataSource == null)
            dataSource = InvocationInfoProxy.getInstance().getDefaultDataSource();
        if (dataSource == null)
            return "design";
        return dataSource;
    }

}
