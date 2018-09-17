package com.xbkj.datasor.bs.framework.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.xbkj.datasor.bs.framework.core.conf.ClusterConf;
import com.xbkj.datasor.bs.framework.core.conf.Configuration;
import com.xbkj.datasor.bs.framework.core.conf.DataSourceConf;
import com.xbkj.datasor.bs.framework.core.conf.ServerConf;
import com.xbkj.datasor.bs.framework.core.conf.WebServerConf;
import com.xbkj.datasor.bs.framework.ds.DataSourceMeta;

/**
 * 
 * Created by UFSoft. User: �ι���
 * 
 * Date: 2007-11-20 Time: ����03:53:38
 * 
 */
public class ServerConfiguration {

	private static ServerConfiguration sc;

	Configuration configuration;

	private ServerConfiguration() {

	}

	public boolean getEnableHotDeploy() {
		return configuration.isHotDeploy();
	}

	public boolean enableBackgroundThread() {
		return configuration.isBgThreadEnabled();
	}

	public void setEnableHotDeploy(boolean hotDeploy) {
		configuration.setHotDeploy(hotDeploy);
	}

	public String getServerName() {
		return configuration.getServerName();
	}

	public DataSourceMeta[] getDataSourceMetas() {
		DataSourceMeta[] ret = new DataSourceMeta[0];
		DataSourceConf[] dscs = configuration.getDataSourceConfs();

		if (dscs != null && dscs.length > 0) {
			ret = new DataSourceMeta[dscs.length];
			for (int i = 0; i < dscs.length; i++) {
				ret[i] = new DataSourceMeta(dscs[i].getDataSourceName(),
						dscs[i].getOidMark(), dscs[i].getDatabaseUrl(), dscs[i]
								.getUser(), dscs[i].getPassword(), dscs[i]
								.getDriverClassName());
			}
		}
		return ret;
	}

	public String[] getDataSourceNames() {
		return configuration.getDataSourceNames();
	}

	public DataSourceMeta getDataSourceMeta(String ds) {
		DataSourceConf dsc = configuration.getDataSourceConf(ds);
		DataSourceMeta ret = null;
		if (dsc != null) {
			ret = new DataSourceMeta(dsc.getDataSourceName(), dsc.getOidMark(),
					dsc.getDatabaseUrl(), dsc.getUser(), dsc.getPassword(), dsc
							.getDriverClassName());
		}
		return ret;
	}

//	public static ServerConfiguration getServerConfiguration() {
//		if (sc == null) {
//			synchronized (ServerConfiguration.class) {
//				ServerConfiguration sc1 = new ServerConfiguration();
//				sc1.configuration = BusinessAppServer.getInstance()
//						.getConfiguration();
//				sc = sc1;
//			}
//		}
//		return sc;
//	}

	public synchronized static void clear() {
		sc = null;
	}

	public boolean isMaster() {
		return configuration.isMaster();
	}

	public boolean isSingle() {
		return configuration.isSingle();
	}

	public String getMasterEndpointURL() {
		return configuration.getMasterConnectorUrl();
	}

	public WebServerConf[] getWebServerInfo() {
		return configuration.getWebServerConfs();
	}

	public WebServerConf getDefWebServerInfo() {
		WebServerConf[] webServer = configuration.getWebServerConfs();
		return webServer == null || webServer.length == 0 ? null : webServer[0];
	}

	public String getDefWebServerURL() {
		WebServerConf info = getDefWebServerInfo();
		if (info != null) {
			return info.toString();
		} else {
			String str = configuration.getEndpoint(getServerName());
			if (str == null) {
				return "http://localhost";
			}
			int index = str.lastIndexOf("/");
			return str.substring(0, index);
		}

	}

	public String getServerRunningService(String svc) {
		String[] svrs = configuration.getServiceAt(svc, null);
		if (svrs == null || svrs.length == 0) {
			return null;
		} else {
			return svrs[0];
		}
	}

	public String[] getPeerServers() {
		return configuration.getPeerServerNames();
	}

	public String getMasterServerName() {
		return configuration.getMasterServerName();
	}

	public String getServerEndpointURL(String svr) {
		return configuration.getConnectorUrl(svr);
	}

	public String getAddressOfServer(String server) {
		String address = null;
		ClusterConf cc = configuration.getClusterConf();
		ServerConf sc = null;
		if (cc != null) {
			sc = cc.getPeer(server);

		} else if (configuration.getServerConf() != null) {
			sc = configuration.getServerConf();
		}

		if (sc != null) {
			if (sc.getHttpEndpointConf() != null) {
				address = sc.getHttpEndpointConf().getAddress();
			} else if (sc.getHttpsEndpointConf() != null) {
				address = sc.getHttpsEndpointConf().getAddress();
			}
		}

		if (address == null || "127.0.0.1".equals(address)) {
			try {
				address = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {

			}
		}
		return address;

	}
}
