package com.xbkj.datasor.bs.framework.server;

import com.xbkj.datasor.bs.framework.common.NCLocator;
import com.xbkj.datasor.bs.framework.component.RemoteProcessComponent;
import com.xbkj.datasor.bs.framework.component.RemoteProcessComponetFactory;
import com.xbkj.datasor.bs.framework.core.AbstractGenericContainer;
import com.xbkj.datasor.bs.framework.core.ComponentMeta;
import com.xbkj.datasor.bs.framework.core.Container;
import com.xbkj.datasor.bs.framework.core.State;
import com.xbkj.datasor.bs.framework.exception.ComponentException;
import com.xbkj.datasor.bs.framework.exception.DuplicateException;
import com.xbkj.datasor.bs.framework.exception.RegisterException;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-21
 * Time: 20:39:07
 * 
 * The container's abstract implementation
 */
public abstract class AbstractContainer extends AbstractGenericContainer<ComponentMeta> implements Container {

    private static final String EXECUTOR_MANAGER = "nc.bs.framework.execute.ExecutorManager";

    protected PublicMetaRepo publicRepo;

    protected AbstractContext ctx;

    protected AbstractContainer() {

	}
    
    public AbstractContainer(String name, PublicMetaRepo publicRepo) {
        super(name);
        if (publicRepo == null) {
            this.publicRepo = new PublicMetaRepo();
        } else {
            this.publicRepo = publicRepo;
        }
    }

    public AbstractContainer(String name, ClassLoader loader, PublicMetaRepo publicRepo) {
        super(name, loader);
        if (publicRepo == null) {
            this.publicRepo = new PublicMetaRepo();
        } else {
            this.publicRepo = publicRepo;
        }
    }

    /**
     * check whether the component existed
     * @param name
     * @return
     */
    public boolean contains(String name) {
        return publicRepo.contains(name) || super.contains(name);
    }

    /**
     * un regist the component with name
     */
    public ComponentMeta deregister(String name) {
        ComponentMeta meta = publicRepo.deregister(name);

        if (state == State.RUNING && meta != null && meta.isActive()) {
            stopActive(meta);
        } else {
            meta = super.deregister(name);
        }
        return meta;
    }

    /**
     * Register a component metaBasic information
     * @param metaBasic
     */
    public void register(ComponentMeta meta) throws ComponentException {
        if (meta == null) {
            throw new RegisterException("Can't regist null component meta");
        }

        if (isIllegalMeta(meta)) {
            throw new DuplicateException(meta.getName());
        }

        if (meta.isPublic()) {
            publicRepo.register(meta);
            if (state == State.RUNING && meta.isActive()) {
                startActive(meta);
            }
        } else {
            super.register(meta);
        }

    }

    public ComponentMeta getMeta(String name) throws ComponentException {
        ComponentMeta meta = publicRepo.getComponentMeta(name);
        if (meta == null) {
            meta = super.getMeta(name);
        }
        return meta;
    }

    /**
     * get all the meta's in the container
     */
    public ComponentMeta[] getMetas() {
        ComponentMeta[] metas1 = publicRepo.getComponentMetas(getName());
        ComponentMeta[] metas2 = metas.toArray(new ComponentMeta[metas.size()]);
        ComponentMeta[] ret = new ComponentMeta[metas1.length + metas2.length];
        System.arraycopy(metas1, 0, ret, 0, metas1.length);
        System.arraycopy(metas2, 0, ret, metas1.length, metas2.length);
        return ret;
    }

    protected void clearAll() {
        publicRepo.clear(getName());
        metaMaps.clear();
    }

    /**
     * before internal start the container
     *
     */
//    protected void beforeInternalStart() {
//        RuntimeEnv.getInstance().setModuleHome(getName(), getUrl().getFile());
//
//        try {
//
//            newBuilder().name(Context.REMOTE_META_SERVICE + "." + getName()).pri(0).asPublic(true).asRemote(true).to(
//                    new Context() {
//                        public Object lookup(String name) throws ComponentException {
//                            return getContext().getComponentMetaVO(name);
//                        }
//                    }).bindin();
//        } catch (ComponentException e) {
//            getLogger().error("Regist meta context error", e);
//        }
//
//    }
//
//    /**
//     * after internal start the container
//     *
//     */
//    protected void afterInternalStart() {
//
//    }
//
//    public AbstractContext getContext() {
//        if (ctx == null) {
//            ctx = new AbstractContext() {
//                @Override
//                protected Container getContainer() {
//                    return AbstractContainer.this;
//                }
//
//                public Object lookup(String name) {
//                    if (Server.class.getName().equals(name)) {
//                        return getServer();
//                    } else {
//                        return super.lookup(name);
//                    }
//
//                }
//            };
//        }
//        return ctx;
//    }
//
//    public void fireAfterStart() {
//        final LifycycleListener[] ls = listeners.toArray(new LifycycleListener[0]);
//        if (ls.length == 0)
//            return;
//        ExecutorManager ex = (ExecutorManager) getContext().lookup(EXECUTOR_MANAGER);
//        try {
//            ex.startExecute(new Runnable() {
//                @SuppressWarnings("unchecked")
//                public void run() {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//
//                    }
//                    for (LifycycleListener l : ls) {
//                        try {
//                            l.afterStart(AbstractContainer.this);
//                        } catch (Throwable thr) {
//                            getLogger().error("afterStart callback error: " + l, thr);
//                        }
//                    }
//                }
//            });
//        } catch (Exception e) {
//            throw new FrameworkRuntimeException("fire listener error", e);
//        }
//    }
//
//    private boolean isServiceHost(String service, String clusterAttr) {
//        return getServer().getConfiguration().isServiceHost(service, clusterAttr);
//    }
//
//    public BuilderImpl newBuilder() {
//        return new BuilderImpl(this);
//    }

//    public boolean isHost(ComponentMeta meta) {
//        return isServiceHost(meta.getName(), meta.getCluster());
//    }

    protected void processActiveAtStart(Object obj) {
        if (obj instanceof RemoteProcessComponent) {
            ((RemoteProcessComponetFactory) NCLocator.getInstance().lookup("RemoteCallPostProcess"))
                    .addPostProcess((RemoteProcessComponent) obj);
        }
    }

    protected void processActiveAtStop(Object obj) {
        if (obj instanceof RemoteProcessComponent) {
            ((RemoteProcessComponetFactory) NCLocator.getInstance().lookup("RemoteCallPostProcess"))
                    .removePostProcess((RemoteProcessComponent) obj);
        }

    }

}