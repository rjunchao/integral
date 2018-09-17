package com.xbkj.datasor.bs.framework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xbkj.datasor.bs.framework.component.ServiceComponent;
import com.xbkj.datasor.bs.framework.exception.ComponentException;
import com.xbkj.datasor.bs.framework.exception.ComponentNotFoundException;
import com.xbkj.datasor.bs.framework.exception.DuplicateException;
import com.xbkj.datasor.bs.framework.exception.FrameworkRuntimeException;
import com.xbkj.datasor.bs.framework.util.Messages;
import com.xbkj.log.bs.logging.Log;
//import com.grc.datasor.bs.framework.enhancer.InterceptorEnhancer;
//import com.grc.datasor.bs.framework.util.EsaConverterManager;

//import org.granite.convert.ClassConverter;
//import org.granite.convert.ConverterManager;

/**
 * 
 * Created by UFSoft. User: �ι���
 * 
 * Date: 2007-12-21 Time: ����08:35:45
 * 
 */
public abstract class AbstractGenericContainer<T extends Meta> implements GenericContainer<T> {
    protected HashMap<String, T> metaMaps;

    protected ArrayList<T> metas;

    private Log log;

    private ClassLoader loader;

    private String name;

    private EnhancerManager em;

    protected State state;

    protected Map<Class, Object> extensions;

    protected List<LifycycleListener> listeners;

    protected ReentrantReadWriteLock accessLock;

    protected AbstractGenericContainer() {

    }

    public AbstractGenericContainer(String name) {
        this(name, null);
    }

    public AbstractGenericContainer(String name, ClassLoader loader) {
        extensions = new HashMap<Class, Object>();
        metaMaps = new HashMap<String, T>();
        metas = new ArrayList<T>();
        this.loader = (loader == null) ? AbstractGenericContainer.class.getClassLoader() : loader;
        this.name = name;
        log = Log.getInstance(this.getClass().getName() + "." + name);
        listeners = new LinkedList<LifycycleListener>();
        em = new SimpleEnhancerManager();
//        em.addPreEnhancer(new InterceptorEnhancer());
        accessLock = new ReentrantReadWriteLock();
//        ConverterManager cm = new EsaConverterManager();
//        cm.registerConverter(Class.class, new ClassConverter(getClassLoader()));
//        cm = new ConverterManager();
//        setExtension(cm, ConverterManager.class);
        state = State.NOT_INIT;

    }

    public boolean contains(String name) {
        accessLock.readLock().lock();
        try {
            return metaMaps.containsKey(name);
        } finally {
            accessLock.readLock().unlock();
        }
    }

    public T deregister(String name) throws ComponentException {
        accessLock.writeLock().lock();
        try {
            T meta = metaMaps.remove(name);

            if (meta != null) {
                String[] alias = meta.getAlias();

                for (String s : alias) {
                    metaMaps.remove(s);
                }

                if (state == State.RUNING && meta != null && meta.isActive()) {
                    stopActive(meta);
                }

                metas.remove(meta);
            }

            return meta;
        } finally {
            accessLock.writeLock().unlock();
        }
    }

    public ClassLoader getClassLoader() {
        return loader;
    }

    public T getMeta(String name) throws ComponentException {
        accessLock.readLock().lock();
        try {
            T meta = metaMaps.get(name);
            if (meta == null) {
                throw new ComponentNotFoundException(getName(), name);
            }
            return meta;
        } finally {
            accessLock.readLock().unlock();
        }
    }

    public EnhancerManager getEnhancerManager() {
        return em;
    }

    public Log getLogger() {
        return log;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public void register(T meta) throws ComponentException {
        accessLock.writeLock().lock();
        try {
            if (isIllegalMeta(meta)) {
                throw new DuplicateException(meta.getName());
            }

            metaMaps.put(meta.getName(), meta);

            String[] strs = meta.getAlias();

            for (String s : strs) {
                metaMaps.put(s, meta);
            }

            metas.add(meta);

            if (state == State.RUNING && meta.isActive()) {
                startActive(meta);
            }
        } finally {
            accessLock.writeLock().unlock();
        }

    }

    public void setEnhancerManager(EnhancerManager factory) {
        if (factory == null) {
            throw new IllegalArgumentException("EnhancerManager can't be null");
        }
        this.em = factory;

    }

    @SuppressWarnings("unchecked")
    public synchronized void addLifecycleListener(LifycycleListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
        if (state == State.RUNING) {
            try {
                listener.afterStart(this);
            } catch (Throwable thr) {
                getLogger().error(String.format(Messages.lflErr, "afterStart", listener), thr);
            }
        }

    }

    public void removeLifecycleListener(LifycycleListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void start() throws Exception {
        if (state != State.INITED && state != State.STOPPED) {
            throw new FrameworkRuntimeException(String.format(Messages.stateCantStart, getName()));
        }
        getLogger().debug(getName() + " start....");
        state = State.STARTING;
        beforeInternalStart();
        internalStart();
        afterInternalStart();
        getLogger().debug(getName() + " now is running....");
        fireAfterStart();
        state = State.RUNING;

    }

    @SuppressWarnings("unchecked")
    protected void fireAfterStart() {
        LifycycleListener[] ls = listeners.toArray(new LifycycleListener[0]);
        if (ls.length == 0)
            return;
        for (LifycycleListener l : ls) {
            try {
                l.afterStart(this);
            } catch (Throwable thr) {
                getLogger().error("afterStart callback error: " + l, thr);
            }
        }
    }

    protected void internalStart() {
        T[] metas = getMetas();
        Arrays.sort(metas, new PriorityComparator(true));
        for (int i = 0; i < metas.length; i++) {
            if (isHost(metas[i])) {
                ExtensionProcessor[] eps = metas[i].getExtensionProcessors();
                if (eps != null) {
                    for (ExtensionProcessor ep : eps) {
                        try {
                            ep.processAtStart(metas[i].getContainer(), metas[i]);
                        } catch (Throwable e) {
                            getLogger().error(String.format(Messages.epStartErr, metas[i].getName(), getName()), e);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < metas.length; i++) {
            if (metas[i].isActive()) {
                try {
                    startActive(metas[i]);
                } catch (Throwable e) {
                    getLogger().error(String.format(Messages.sstartErr, metas[i].getName(), getName()), e);
                }
            }
        }

    }

    /**
     * before internal start the container
     * 
     */
    protected void beforeInternalStart() {

    }

    protected void afterInternalStart() {
    }

    protected void beforeInternalStop() {
    }

    protected void afterInternalStop() {
    }

    public void stop() throws Exception {
        if (state != State.RUNING && state != State.STARTING) {
            getLogger().debug(String.format(Messages.alreadStop, getName()));
        }

        getLogger().debug(String.format(Messages.cbStop, getName()));
        fireBeforeStop();
        state = State.STOPPING;
        beforeInternalStop();
        internalStop();
        afterInternalStop();
        metaMaps.clear();
        state = State.STOPPED;
        getLogger().debug(String.format(Messages.ceStop, getName()));

    }

    protected void internalStop() {
        T[] metas = getMetas();
        Arrays.sort(metas, new PriorityComparator(false));

        for (int i = 0; i < metas.length; i++) {
            if (metas[i].isActive()) {
                stopActive(metas[i]);
            }
        }

        for (int i = 0; i < metas.length; i++) {
            if (isHost(metas[i])) {
                ExtensionProcessor[] eps = metas[i].getExtensionProcessors();
                if (eps != null) {
                    for (ExtensionProcessor ep : eps) {
                        try {
                            ep.processAtStop(metas[i].getContainer(), metas[i]);
                        } catch (Throwable e) {
                            getLogger().error(String.format(Messages.epStopErr, metas[i].getName(), getName()), e);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected void fireBeforeStop() {
        LifycycleListener[] ls = listeners.toArray(new LifycycleListener[0]);
        for (LifycycleListener l : ls) {
            try {
                l.beforeStop(this);
            } catch (Throwable thr) {
                getLogger().error(String.format(Messages.lflErr, "beforeStop", l), thr);
            }
        }

    }

    protected boolean isIllegalMeta(T meta) {
        String name = meta.getName();
        if (contains(name)) {
            return true;
        }
        String alias[] = meta.getAlias();
        for (int i = 0; alias != null && i < alias.length; i++) {
            if (contains(alias[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean isHost(T meta) {
        return true;
    }

    protected void processActiveAtStart(Object obj) {

    }

    protected void processActiveAtStop(Object obj) {

    }

    protected void startActive(T meta) {
        if (isHost(meta)) {
            Object retObject = meta.getInstantiator().instantiate(getContext(), meta.getName(), null);
            if (retObject instanceof ServiceComponent) {
                try {
                    if (retObject instanceof ServiceComponent) {
                        getLogger().debug(String.format(Messages.sbStart, meta.getName(), getName()));
                        if (!((ServiceComponent) retObject).isStarted())
                            ((ServiceComponent) retObject).start();
                        getLogger().debug(String.format(Messages.seStart, meta.getName(), getName()));

                        processActiveAtStart(retObject);
                    }
                } catch (Throwable e) {
                    getLogger().error(String.format(Messages.sstartErr, meta.getName(), getName()), e);
                }
            }
        }

    }

    protected void stopActive(T meta) {
        if (isHost(meta)) {
            try {
                Object retObject = meta.getInstantiator().instantiate(getContext(), meta.getName(), null);
                if (retObject instanceof ServiceComponent) {
                    synchronized (retObject) {
                        processActiveAtStop(retObject);
                        getLogger().debug(String.format(Messages.sbStop, meta.getName(), getName()));
                        if (((ServiceComponent) retObject).isStarted())
                            ((ServiceComponent) retObject).stop();
                        getLogger().debug(String.format(Messages.seStop, meta.getName(), getName()));
                    }
                }
            } catch (Throwable exp) {
                getLogger().error(String.format(Messages.sstopErr, meta.getName(), getName()), exp);
            }
        }

    }

    public String toString() {
        return getName();
    }

    public int hashCode() {
        return getName() != null ? getName().hashCode() : super.hashCode();
    }

    public <P> P getExtension(Class<P> extensionType) {
        Object obj = extensions.get(extensionType);
        if (null != obj) {
            return extensionType.cast(obj);
        }
        return null;
    }

    public <P> void setExtension(P extension, Class<P> extensionType) {
        extensions.put(extensionType, extension);
    }

}
