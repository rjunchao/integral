package com.xbkj.datasor.bs.framework.server;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.xbkj.datasor.bs.framework.core.ComponentMeta;
import com.xbkj.datasor.bs.framework.exception.ComponentException;
import com.xbkj.datasor.bs.framework.exception.DuplicateException;
import com.xbkj.datasor.bs.framework.exception.RegisterException;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-12
 * Time: ����10:55:43
 *
 */
public class PublicMetaRepo {

    private static final int INIT_SIZE = 256;

    private ArrayList<ComponentMeta> metas;

    private Map<String, ComponentMeta> nameIndices;

    private Map<String, List<ComponentMeta>> modueIndices;

    private ReentrantReadWriteLock accessLock;

    public PublicMetaRepo(int size) {
        accessLock = new ReentrantReadWriteLock();
        nameIndices = new ConcurrentHashMap<String, ComponentMeta>(size);
        metas = new ArrayList<ComponentMeta>(size);
        modueIndices = new ConcurrentHashMap<String, List<ComponentMeta>>(64);
    }

    public PublicMetaRepo() {
        this(INIT_SIZE);
    }

    public void register(ComponentMeta meta) throws ComponentException {
        accessLock.writeLock().lock();
        try {
            if (meta == null) {
                throw new RegisterException("can't regist null component meta");
            }

            if (isIllegalMeta(meta)) {
                throw new DuplicateException(meta.getName());
            }
            metas.add(meta);

            buildIndices(meta);
        } finally {
            accessLock.writeLock().unlock();
        }
    }

    public boolean contains(String name) {
        return nameIndices.containsKey(name);
    }

    public ComponentMeta deregister(String name) {
        accessLock.writeLock().lock();
        try {
            ComponentMeta meta = nameIndices.remove(name);

            if (meta != null) {
                cleanInvalidIndices(meta);
                metas.remove(meta);
            }
            return meta;
        } finally {
            accessLock.writeLock().unlock();
        }

    }

    boolean isIllegalMeta(ComponentMeta meta) {
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

    private void buildIndices(ComponentMeta meta) {
        nameIndices.put(meta.getName(), meta);
        String[] names = meta.getAlias();
        for (int i = 0; i < names.length; i++) {
            nameIndices.put(names[i], meta);
        }

        if (meta.getContainer() != null) {
            String module = meta.getContainer().getName();
            List<ComponentMeta> list = modueIndices.get(module);
            if (list == null) {
                list = new ArrayList<ComponentMeta>(64);
                modueIndices.put(module, list);
            }
            list.add(meta);
        }

    }

    private void cleanInvalidIndices(ComponentMeta meta) {

        String[] alias = meta.getAlias();

        for (String s : alias) {
            nameIndices.remove(s);
        }

        if (meta.getContainer() != null) {
            List<ComponentMeta> list = modueIndices.get(meta.getContainer().getName());
            if (list != null) {
                list.remove(meta);
            }
        }

    }

    public ComponentMeta getComponentMeta(String name) {

        accessLock.readLock().lock();
        try {
            return nameIndices.get(name);

        } finally {
            accessLock.readLock().unlock();
        }

    }

    public ComponentMeta[] getComponentMetas() {
        accessLock.readLock().lock();
        try {
            return (ComponentMeta[]) metas.toArray(new ComponentMeta[metas.size()]);
        } finally {
            accessLock.readLock().unlock();
        }
    }

    public ComponentMeta[] getComponentMetas(String module) {
        accessLock.readLock().lock();
        try {
            List<ComponentMeta> list = modueIndices.get(module);
            if (list == null) {
                return new ComponentMeta[0];
            } else {
                ComponentMeta[] metas = new ComponentMeta[list.size()];
                metas = list.toArray(metas);
                return metas;
            }
        } finally {
            accessLock.readLock().unlock();
        }
    }

    public int getCount(String module) {
        accessLock.readLock().lock();
        try {
            List<ComponentMeta> list = modueIndices.get(module);
            if (list == null) {
                return 0;
            } else {
                return list.size();
            }
        } finally {
            accessLock.readLock().unlock();
        }

    }

    public int getCount() {
        accessLock.readLock().lock();
        try {
            return metas.size();
        } finally {
            accessLock.readLock().unlock();
        }
    }

    public void clear() {
        accessLock.writeLock().lock();
        try {
            modueIndices.clear();
            nameIndices.clear();
            metas.clear();
        } finally {
            accessLock.writeLock().unlock();
        }
    }

    public void clear(String module) {
        accessLock.writeLock().lock();
        try {
            List<ComponentMeta> list = modueIndices.get(module);
            if (list != null) {
                modueIndices.remove(module);
                for (int i = 0; i < list.size(); i++) {
                    Iterator itr = nameIndices.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry entry = (Map.Entry) itr.next();
                        if (entry.getValue().equals(list.get(i))) {
                            itr.remove();
                        }
                    }
                    metas.remove(list.get(i));
                }

            }
        } finally {
            accessLock.writeLock().unlock();
        }
    }

    public void dump(PrintStream out) {
        accessLock.readLock().lock();
        try {
            ComponentMeta[] ms = new ComponentMeta[metas.size()];
            ms = metas.toArray(ms);

            for (int i = 0; i < ms.length; i++) {
                out.println(((ComponentMeta) ms[i]).getName());
            }
        } finally {
            accessLock.readLock().unlock();
        }
    }

}
