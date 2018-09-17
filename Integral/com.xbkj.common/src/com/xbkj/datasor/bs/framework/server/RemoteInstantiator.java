package com.xbkj.datasor.bs.framework.server;

import com.xbkj.datasor.bs.framework.core.ComponentMeta;
//import com.grc.datasor.bs.framework.remote.RemoteExporter;

/**
 * @author �ι���
 * 
 * Date: 2006-6-7 Time: 11:17:34
 * 
 * TODO: optimize
 */
public class RemoteInstantiator //implements Instantiator 
{

    private String connectorUrl;

    private String server;

    private ComponentMeta meta;

    private Object remoteObject;

    public RemoteInstantiator(String server, ComponentMeta meta) {
        this.server = server;
        this.meta = meta;
        connectorUrl = resolveTargetServerURL(server);
    }

//    public Object instantiate(Context ctx, String name, Object[] args) throws ComponentException {
//        //TODO: TEST THE TARGET CONNECTOR AND WAIT
//        if (remoteObject == null) {
//            String svrName = server;
//            if (ComponentMeta.CLUSTER_SP.equalsIgnoreCase(svrName)) {
//                svrName = meta.getContainer().getServer().getConfiguration().getMasterServerName();
//            }
//            remoteObject = RemoteExporter.getInstance().export(connectorUrl, server, meta.getContainer().getName(),
//                    name, meta.getInterfaces());
//        }
//        return remoteObject;
//    }

    private String resolveTargetServerURL(String serverAttr) {
        return meta.getContainer().getServer().getConfiguration().getConnectorUrl(serverAttr);
    }

}
