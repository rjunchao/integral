package com.xbkj.datasor.bs.framework.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.xbkj.datasor.bs.framework.core.ServiceCache;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-1
 * Time: ����12:41:03
 *
 */
public class ServiceCacheImpl implements ServiceCache {

    private final Map<String, Object> cache;

    private Set<String> blackSet = Collections.synchronizedSet(new HashSet<String>());

    public ServiceCacheImpl() {
        cache = new ConcurrentHashMap<String, Object>(256);
        blackSet = Collections.synchronizedSet(new HashSet<String>());
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void clear() {
        cache.clear();
        blackSet.clear();
    }

    public void addBlack(String name) {
        blackSet.add(name);

    }

    public boolean isBlack(String name) {
        return blackSet.contains(name);
    }
}
