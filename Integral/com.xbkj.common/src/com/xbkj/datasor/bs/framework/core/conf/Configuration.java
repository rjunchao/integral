package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//import com.grc.basic.vo.jcom.env.Messages;
import com.grc.basic.vo.jcom.lang.StringUtil;
import com.grc.datasor.bs.framework.util.Messages;

/**
 * 
 * Created by UFSoft. User: �ι���
 * 
 * Date: 2007-11-2 Time: ����04:10:44
 * 
 */
public class Configuration implements Serializable {

	private static final long serialVersionUID = 8115941043817515213L;

	public static final String SCHEMA_HTTP = "http";

	public static final String SCHEMA_HTTPS = "https";

	private boolean hotDeploy;

	private boolean encoded;

	private boolean supportBO;

	private String version = "5.x";

	private Map<String, DataSourceConf> dsMetaMap;

	private ClusterConf clusterConf;

	private ServerConf serverConf;

	private List<WebServerConf> webServerConfs;

	private ServiceAssignConf serviceAssignConf;

	public ServiceAssignConf getServiceAssignConf() {
		return serviceAssignConf;
	}

	public void setServiceAssignConf(ServiceAssignConf serviceAssingConf) {
		this.serviceAssignConf = serviceAssingConf;
	}

	public Configuration() {
		dsMetaMap = new HashMap<String, DataSourceConf>();
		webServerConfs = new ArrayList<WebServerConf>();
		supportBO = "true".equals(System.getProperty("nc.supportBO", "true"));
	}

	public boolean isHotDeploy() {
		return hotDeploy;
	}

	public void setHotDeploy(boolean flag) {
		this.hotDeploy = flag;
	}

	public String getServerName() {
		String name = System.getProperty("nc.server.name");

		if (name == null) {
			if (serverConf != null) {
				name = serverConf.getName();
			} else {
				try {
					name = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					name = "DEFSRV";
				}
			}
		}
		return name;
	}

	public DataSourceConf[] getDataSourceConfs() {
		return dsMetaMap.values().toArray(new DataSourceConf[0]);
	}

	public String[] getDataSourceNames() {
		return dsMetaMap.keySet().toArray(new String[0]);
	}

	public DataSourceConf getDataSourceConf(String ds) {
		return dsMetaMap.get(ds);
	}

	public void addDataSourceConf(DataSourceConf dsconf) {
		dsMetaMap.put(dsconf.getDataSourceName(), dsconf);
	}

	public boolean isEncoded() {
		return encoded;
	}

	public void setEncoded(boolean encoded) {
		this.encoded = encoded;
	}

	public WebServerConf[] getWebServerConfs() {
		return webServerConfs.toArray(new WebServerConf[0]);
	}

	public ClusterConf getClusterConf() {
		return clusterConf;
	}

	public void setClusterConf(ClusterConf clusterConf) {
		this.clusterConf = clusterConf;
	}

	public ServerConf getServerConf() {
		return serverConf;
	}

	public void setServerConf(ServerConf serverConf) {
		this.serverConf = serverConf;
	}

	public void addWebServerConf(WebServerConf conf) {
		webServerConfs.add(conf);
	}

	public String getMasterEndpoint() {
		String ret = null;
		if (clusterConf != null) {
			if ("https".equals(clusterConf.getProtocol()))
				ret = clusterConf.getMaster().getHttpsEndpoint();
			else
				ret = clusterConf.getMaster().getHttpEndpoint();
		} else if (serverConf != null) {
			ret = serverConf.getHttpEndpoint();
			if (ret == null) {
				ret = serverConf.getHttpsEndpoint();
			}
		} else {
			// stand alone server with no configuration
			ret = "http://localhost";
		}
		return ret;
	}

	public boolean isMaster() {
		boolean ret = false;
		if (isSingle()) {
			ret = true;
		} else {
			ret = clusterConf.getMaster().getName().equals(getServerName());
		}

		return ret;
	}

	public boolean isSingle() {
		return clusterConf == null || clusterConf.getPeers().length == 0;
	}

	public boolean isServiceHost(String service, String clusterAttr) {
		boolean ret = false;
		if (isSingle()) {
			ret = true;
		} else {
			ret = isServiceAt(service, clusterAttr, getServerName());
		}
		return ret;
	}

	public boolean isServiceConfigAt(String service, String server) {
		boolean ret = false;
		if (isSingle()) {
			ret = true;
		} else {
			ret = serviceAssignConf != null
					&& serviceAssignConf.getTargetServers(service) != null
					&& serviceAssignConf.getTargetServers(service).contains(
							server);
		}
		return ret;
	}

	public boolean isServiceAt(String service, String clusterAttr, String server) {
		boolean ret = true;
		if (isSingle()) {
			ret = true;
		} else {
			if (serviceAssignConf != null
					&& serviceAssignConf.getTargetServers(service) != null
					&& serviceAssignConf.getTargetServers(service).size() > 0) {
				ret = serviceAssignConf.getTargetServers(service).contains(
						server);
			} else {
				if (StringUtil.hasText(clusterAttr)) {
					if ("NONE".equals(clusterAttr)) {
						ret = true;
					} else if ("normal".equals(clusterAttr)) {
						ret = true;
					} else if ("SP".equals(clusterAttr)) {
						ret = isMaster();
					} else if ("NOSP".equals(clusterAttr)) {
						ret = !isMaster();
					} else {
						ret = clusterAttr.equals(getServerName());
					}
				} else {
					ret = true;
				}
			}
		}
		return ret;
	}

	public String[] getServiceAt(String service, String clusterAttr) {
		String[] servers = null;
		if (isSingle()) {
			servers = new String[] { getServerName() };
		} else {
			if (serviceAssignConf != null
					&& serviceAssignConf.getTargetServers(service) != null
					&& serviceAssignConf.getTargetServers(service).size() > 0) {
				servers = serviceAssignConf.getTargetServers(service).toArray(
						new String[0]);
			} else {
				if (StringUtil.hasText(clusterAttr)) {
					if ("normal".equals(clusterAttr)) {
						ServerConf[] scs = clusterConf.getPeers();
						servers = new String[clusterConf.getPeers().length + 1];
						for (int i = 0; i < scs.length; i++) {
							servers[i] = scs[i].getName();
						}
						servers[servers.length - 1] = clusterConf.getMaster()
								.getName();
					} else if ("SP".equals(clusterAttr)) {
						servers = new String[] { clusterConf.getMaster()
								.getName() };
					} else if ("NOSP".equals(clusterAttr)) {
						ServerConf[] scs = clusterConf.getPeers();
						servers = new String[clusterConf.getPeers().length];
						for (int i = 0; i < scs.length; i++) {
							servers[i] = scs[i].getName();
						}

					} else {
						servers = new String[] { clusterAttr };
					}
				} else {
					ServerConf[] scs = clusterConf.getPeers();
					servers = new String[clusterConf.getPeers().length + 1];
					for (int i = 0; i < scs.length; i++) {
						servers[i] = scs[i].getName();
					}
					servers[servers.length - 1] = clusterConf.getMaster()
							.getName();
				}
			}
		}
		return servers;
	}

	public String getMasterConnectorUrl() {
		return getConnectorUrl("SP");
	}

	public String getConnectorUrl(String server) {

		String str = getEndpoint(server);
		if (str == null) {
			throw new IllegalArgumentException(String.format(Messages.noSvrFnd,
					server));
		}
		if (str.endsWith("/")) {
			str = str + "ServiceDispatcherServlet";
		} else {
			str = str + "/ServiceDispatcherServlet";
		}
		return str;
	}

	public String getEndpoint(String svr) {
		String ret = null;
		if (svr.equals("SP")) {
			ret = getMasterEndpoint();
		} else {
			if ("NONE".equals(svr)) {
				svr = getServerName();
			}
			if (clusterConf != null) {
				boolean https = SCHEMA_HTTPS.equals(clusterConf.getProtocol());
				if (svr.equals(clusterConf.getMaster().getName())) {
					ret = https ? clusterConf.getMaster().getHttpsEndpoint()
							: clusterConf.getMaster().getHttpEndpoint();
				} else if ("NOSP".equals(svr)) {
					ServerConf[] scs = clusterConf.getPeers();
					int rdidx = new Random().nextInt();
					if (rdidx < 0) {
						rdidx = -rdidx;
					}
					ServerConf sc = scs[rdidx % scs.length];
					ret = https ? sc.getHttpsEndpoint() : sc.getHttpEndpoint();

				} else {
					ServerConf sc = clusterConf.getPeer(svr);
					if (sc != null) {
						ret = https ? sc.getHttpsEndpoint() : sc
								.getHttpEndpoint();
					}
				}
			} else if (svr.equals(getServerName())) {
				if (serverConf != null) {
					ret = serverConf.getHttpEndpoint();
					if (ret == null)
						ret = serverConf.getHttpsEndpoint();
				}

				if (ret == null)
					// standalone with no configuration
					ret = "http://localhost";
			}
		}
		return ret;
	}

	public String getMasterServerName() {
		String ret = null;
		if (isMaster()) {
			ret = getServerName();
		} else {
			ret = clusterConf.getMaster().getName();
		}
		return ret;
	}

	public String[] getPeerServerNames() {
		String[] ret = null;
		if (isSingle()) {
			ret = new String[0];
		} else {
			ServerConf[] scs = clusterConf.getPeers();
			ret = new String[scs.length];
			for (int i = 0; i < scs.length; i++) {
				ret[i] = scs[i].getName();
			}
		}

		return ret;
	}

	public boolean isBgThreadEnabled() {
		if (this.serviceAssignConf != null) {
			return serviceAssignConf.isEnableBgThread();
		} else {
			return false;
		}
	}

	public boolean supportBO() {
		return supportBO;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
