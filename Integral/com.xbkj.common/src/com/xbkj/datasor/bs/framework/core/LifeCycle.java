package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-3
 * Time: ����11:36:26
 *
 */
public interface LifeCycle {
    public void start() throws Exception;

    public void stop() throws Exception;
    
    public void addLifecycleListener(LifycycleListener listener);

    public void removeLifecycleListener(LifycycleListener listener);
}
