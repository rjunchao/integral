package com.xbkj.datasor.bs.framework.core.conf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-20
 * Time: ����10:54:43
 *
 */
public class ServiceAssignConf implements Serializable {

    private static final long serialVersionUID = 2947093005046452108L;

    private Map<String, Set<String>> svc2svrMap;

    private Map<String, Set<String>> svr2svcMap;

    private boolean enableBgThread;

    public ServiceAssignConf() {
        svc2svrMap = new HashMap<String, Set<String>>();
        svr2svcMap = new HashMap<String, Set<String>>();
    }

    public void add(String server, String service) {
        add(server, service, svr2svcMap);
        add(service, server, svc2svrMap);
    }

    private void add(String key, String value, Map<String, Set<String>> map) {
        Set<String> set = map.get(key);
        if (set == null) {
            set = new HashSet<String>();
            map.put(key, set);
        }
        set.add(value);
    }

    public Set<String> getTargetServers(String service) {
        return svc2svrMap.get(service);
    }

    public Set<String> getAssignedServices(String server) {
        return svr2svcMap.get(server);
    }

    public boolean isEnableBgThread() {
        return enableBgThread;
    }

    public void setEnableBgThread(boolean enableBgThread) {
        this.enableBgThread = enableBgThread;
    }

//    public boolean isServiceAssigned(String service, String server) {
//        boolean ret = true;
//        if (svc2svrMap.keySet().contains(service)) {
//            Set<String> s = svc2svrMap.get(service);
//            if (s == null || s.size() == 0) {
//                ret = true;
//            } else {
//                ret = s.contains(server);
//            }
//        }
//        return ret;
//
//    }
}
