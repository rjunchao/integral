package com.xbkj.datasor.bs.framework.core;

/**
 * @author �ι���
 *
 * TODO:
 * Date: 2007-1-6
 * Time: ����02:08:40
 */
public interface LifycycleListener<T extends LifeCycle> {
    public void afterStart(T lc);

    public void beforeStop(T lc);
}
