package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-3
 * Time: ����01:01:21
 *
 */
public interface ServiceCache {
    public void put(String name, Object service);

    public Object get(String name);

    public void addBlack(String name);

    public boolean isBlack(String name);

    public void clear();
}
