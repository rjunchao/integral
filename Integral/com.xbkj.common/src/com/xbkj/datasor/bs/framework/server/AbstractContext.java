package com.xbkj.datasor.bs.framework.server;

import java.util.ArrayList;

import com.grc.basic.vo.jcom.util.ClassUtil;
import com.grc.datasor.bs.framework.core.ComponentMeta;
import com.grc.datasor.bs.framework.core.Container;
import com.grc.datasor.bs.framework.core.JndiContext;
import com.grc.datasor.bs.framework.core.Mode;
import com.grc.datasor.bs.framework.core.ServiceCache;
import com.grc.datasor.bs.framework.exception.ComponentException;
import com.grc.log.bs.logging.Log;
//import com.grc.datasor.bs.framework.ejb.EJB3ServiceHandler;
//import com.grc.datasor.bs.framework.naming.ListableContext;
//import com.grc.datasor.bs.framework.server.util.ServerConstants;
//import com.grc.datasor.bs.framework.util.EJBUtil;
//import com.grc.datasor.itf.framework.ejb.BMTProxy;
//import com.grc.datasor.itf.framework.ejb.CMTProxy;

/**
 * Created by IntelliJ IDEA. 
 * User: �ι��� 
 * Date: 2005-1-24 
 * Time: 16:04:59
 */
public abstract class AbstractContext //implements ListableContext 
{

    private static final String CMT_DELEGATE = "java:comp/env/ejb/nc.itf.framework.ejb.CMTProxy";

    private static final String BMT_DELEGATE = "java:comp/env/ejb/nc.itf.framework.ejb.BMTProxy";

    protected Log getLogger() {
        return Log.getInstance(this.getClass());
    }

//    public Object lookup(String name) throws ComponentException {
//        
//        if (name == null) {
//            throw new ComponentNotFoundException("component name is null when lookup");
//        }
//        Object retObject = null;
//        retObject = getServiceCache().get(name);
//        if (retObject == null) {
//            JndiContext jndiCtx = getJNDIContext();
//            if (name.startsWith(LOCAL_JNDI_PREFIX)) {
//                retObject = jndiCtx.lookup(name);
//                if (EJBUtil.isHome(retObject)) {
//                    retObject = serviceFromEJBHome(name, retObject, null);
//                    //only support stateless
//                    getServiceCache().put(name, retObject);
//                }
//            } else if (name.equals("javax.transaction.UserTransaction")) {
//                retObject = getUserTransaction();
//                //TODO: add to cache??? i don't know     
//            }
//
//            if (retObject == null) {
//                if (!supportBO() && name.endsWith("BO")) {
//                    throw new ComponentNotFoundException(String.format(notSupportBO, name));
//                }
//
//                ComponentMeta meta = null;
//                
//
//                try {
//                    meta = findMeta(name);
//                } catch (ComponentNotFoundException exp) {
//                    getLogger().warn(String.format(nfdMeta, name, "ESA", " try to search it from jndi."));
//                }
//
//                String originalName = name;
//                if (meta != null && !name.equals(meta.getName())) {
//                    name = meta.getName();
//                    retObject = getServiceCache().get(name);
//                }
//                if (retObject == null) {
//                    boolean needTx = false;
//                    String jndiName = name;
//                   
//                    if(meta != null) {
//                        needTx = meta.getTxAttribute() != null && TxAttribute.NONE != meta.getTxAttribute();
//                        jndiName = meta.getEjbName();
//                    }
//
//                    if (meta == null || needTx) {
//                        if (!isBlackService(name)) {
//                            try {
//                                retObject = jndi(jndiName);
//                            } catch (Throwable thr) {
//
//                            }
//
//                            if (EJBUtil.isHome(retObject)) {
//                                retObject = serviceFromEJBHome(name, retObject, (ComponentMeta) meta);
//                            } else {
//                                if (needTx && retObject != null) {
//                                    retObject = Proxy.newProxyInstance(retObject.getClass().getClassLoader(), ClassUtil
//                                            .getInterfaces(retObject.getClass(), ServerConstants.excludes),
//                                            new EJB3ServiceHandler(name, retObject));
//                                }
//                            }
//
//                            if (retObject != null) {
//                                getServiceCache().put(name, retObject);
//                                if (originalName != name) {
//                                    getServiceCache().put(originalName, retObject);
//                                }
//                            } else {
//                                if (isProductMode()) {
//                                    markServiceBlack(name);
//                                    if (originalName != name) {
//                                        markServiceBlack(originalName);
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (retObject == null) {
//                        //TODO: ?????????
//                        if (isProductMode() && needTx) {
//                            throw new ComponentNotFoundException(name, String.format(nfdTxCmnt, name, "jndi",
//                                    " please deploy it!") + " jndiName: " + jndiName);
//                        }
//                        if (meta != null) {
//                            retObject = findComponent(meta);
//                            if (needTx && !outJAVAEE()) {
//                                return dynamicBService((ComponentMeta) meta, retObject);
//                            }
//                        } else {
//                            throw new ComponentNotFoundException(name, "Can not find component(both in jndi and ESA)");
//                        }
//                    }
//                }
//            }
//        }
//
//        return retObject;
//
//    }
//
//    private Object getUserTransaction() {
//        Object retObject;
//        retObject = UserTransactionHolder.getUserTransaction();
//        if (retObject == null) {
//            if (EnvironmentUtil.isRunningInWebSphere()) {
//                getJNDIContext().lookupWithoutCache("java:comp/UserTransaction");
//            } else {
//                throw new ComponentNotFoundException("Can't find UserTransaction(NotWAS or Not BMT in NC)");
//            }
//        }
//        return retObject;
//    }
//
//    public String[] listNames() {
//        return listNames(null);
//    }
//
//    public String[] listNames(Class intfClazz) {
//        ArrayList<String> list = new ArrayList<String>();
//        //todo: iterate from jndi
//
//        ComponentMeta[] metas = listComponentMetas(intfClazz);
//        for (int i = 0; i < metas.length; i++) {
//            list.add(metas[i].getName());
//        }
//        String[] names = new String[list.size()];
//        list.toArray(names);
//        return names;
//    }
//
//    private Object dynamicBService(ComponentMeta meta, Object obj) {
//        Object delegate = null;
//
//        boolean bmt = false;
//        if (meta.getTxAttribute() == TxAttribute.BMT) {
//            delegate = lookup(BMT_DELEGATE);
//            bmt = true;
//        } else {
//            delegate = lookup(CMT_DELEGATE);
//        }
//
//        if (bmt) {
//            return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), meta.getInterfaces(),
//                    new BMTEJBServiceHandler((BMTProxy) delegate, obj));
//        } else {
//            return Proxy.newProxyInstance(delegate.getClass().getClassLoader(), meta.getInterfaces(),
//                    new CMTEJBServiceHandler((CMTProxy) delegate, obj));
//        }
//    }
//
//    private Object serviceFromEJBHome(String name, Object homeObject, ComponentMeta meta) throws InstException {
//        Object ejbObject = null;
//        Method createMethod = null;
//        Method removeMethod = null;
//
//        try {
//            createMethod = EJBUtil.getDefCreate(homeObject);
//            ejbObject = EJBUtil.createStls(homeObject, createMethod);
//
//            //because the EJBContainer of UFSOFT must use this remove method, someday it may be changed
//            removeMethod = EJBUtil.getRemove(ejbObject);
//            if (meta == null)
//                return Proxy.newProxyInstance(ejbObject.getClass().getClassLoader(), ClassUtil.getInterfaces(ejbObject
//                        .getClass(), ServerConstants.excludes), new HomedEJBServiceHandler(name, homeObject,
//                        createMethod, removeMethod, this));
//            else
//                return Proxy.newProxyInstance(ejbObject.getClass().getClassLoader(), meta.getInterfaces(),
//                        new HomedEJBServiceHandler(name, homeObject, createMethod, removeMethod, this));
//
//        } catch (InvocationTargetException e) {
//            throw new InstException(name, "Can't create  ejb Object", e.getTargetException());
//        } catch (Throwable t) {
//            throw new InstException(name, "Can't create  ejb Object", t);
//        } finally {
//            if (ejbObject != null && removeMethod != null)
//                try {
//                    removeMethod.invoke(ejbObject, new Object[0]);
//                } catch (InvocationTargetException e) {
//                    throw new InstException(name, "remove temp ejb Object error", e.getTargetException());
//                } catch (Throwable t) {
//                    throw new InstException(name, "remove temp ejb Object error ", t);
//                }
//        }
//
//    }
//
//    private Object jndi(String name) {
//        Object retObject = null;
//
//        JndiContext jndiCtx = getJNDIContext();
//
//        if (name.startsWith(LOCAL_JNDI_PREFIX)) {
//            retObject = jndiCtx.lookup(name);
//        } else {
//            boolean startsAsJdbc = name.startsWith("jdbc/");
//            if (isManagedDataSource(name) || startsAsJdbc) {//datasource
//                if (RuntimeEnv.isRunningInWeblogic() || RuntimeEnv.isRunningInWebSphere()) {//direct lookup
//                    try {
//                        retObject = jndiCtx.lookup(name);
//                    } catch (Throwable thr) {
//                    }
//                }
//                retObject = jndiWithReTry(jndiCtx, LOCAL_JDBC_JNDI_PREFIX, name);
//            } else {
//                retObject = jndiWithReTry(jndiCtx, LOCAL_EJB_JNDI_PREFIX, name);
//            }
//        }
//
//        return retObject;
//    }
//
//    private Object jndiWithReTry(JndiContext jndiCtx, String prefix, String name) {
//        Object retObject = null;
//        try {
//            retObject = jndiCtx.lookup(prefix + name);
//        } catch (Throwable t) {
//            try {
//                retObject = jndiCtx.lookup(LOCAL_JNDI_PREFIX + name);
//            } catch (Throwable t1) {
//                retObject = jndiCtx.lookup(name);
//            }
//        }
//        return retObject;
//    }
//
//    public ComponentMetaVO getComponentMetaVO(String name) throws ComponentException {
//        ComponentMeta meta = null;
//        try {
//            meta = findMeta(name);
//        } catch (ComponentNotFoundException exp) {
//
//        }
//
//        if (meta != null) {
//            return meta.getComponentMetaVO();
//        } else {
//            Object obj;
//            obj = lookup(name);
//            Class[] itfs = ClassUtil.getInterfaces(obj.getClass(), ServerConstants.excludes);
//            return new ComponentMetaVO(name, new String[] { name }, itfs);
//        }
//
//    }

    private JndiContext getJNDIContext() {
        return getContainer().getServer().getJndiContext();
    }

    private ServiceCache getServiceCache() {
        return getContainer().getServer().getServiceCache();
    }

    private boolean isBlackService(String name) {
        return getContainer().getServer().getServiceCache().isBlack(name);
    }

    private void markServiceBlack(String name) {
        getContainer().getServer().getServiceCache().addBlack(name);
    }

    private boolean supportBO() {
        return getContainer().getServer().getConfiguration().supportBO();
    }

    private boolean isProductMode() {
        return getContainer().getServer().getMode() == Mode.PRODUCT;
    }

    private boolean outJAVAEE() {
        return getContainer().getServer().getMode() == Mode.OUTJAVAEE;
    }

    private boolean isManagedDataSource(String name) {
        return getContainer().getServer().getConfiguration().getDataSourceConf(name) != null;
    }

    protected ComponentMeta findMeta(String name) throws ComponentException {
        return getContainer().getMeta(name);
    }

    protected ComponentMeta[] listComponentMetas(Class clazz) {
        ArrayList<ComponentMeta> list = new ArrayList<ComponentMeta>(200);
        ComponentMeta[] metas = getContainer().getMetas();
        for (int i = 0; i < metas.length; i++) {
            if (clazz == null || ClassUtil.isAssignableFrom(clazz, metas[i].getInterfaces()))
                list.add(metas[i]);

        }
        ComponentMeta[] retMetas = new ComponentMeta[list.size()];
        list.toArray(retMetas);
        return retMetas;
    }

    protected void checkComponent(String name, Object obj) throws ComponentException {

    }

    protected Object findComponent(ComponentMeta meta) throws ComponentException {
        Object retObject = meta.getInstantiator().instantiate(meta.getContainer().getContext(), meta.getName(), null);
        checkComponent(meta.getName(), retObject);
        return retObject;
    }

    protected abstract Container getContainer();

}
