package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-20
 * Time: ����09:08:39
 *
 */
public class ClusterConf implements Serializable {

    private static final long serialVersionUID = -2947699264228898390L;

    private String name;

    private ServerConf master;

    private Map<String, ServerConf> peers;

    private String protocol = "http";

    public ClusterConf() {
        peers = new HashMap<String, ServerConf>();
    }

    public ServerConf getMaster() {
        return master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaster(ServerConf mgr) {
        this.master = mgr;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void addPeer(ServerConf sc) {
        peers.put(sc.getName(), sc);
    }

    public ServerConf getPeer(String server) {
        return peers.get(server);
    }

    public ServerConf removePeer(String server) {
        return peers.remove(server);
    }

    public ServerConf[] getPeers() {
        return peers.values().toArray(new ServerConf[0]);
    }

}
