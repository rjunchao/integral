package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-15
 * Time: ����02:30:57
 *
 */
public interface Monitor {
    public long getScanInterval();

    public void enableHotDeploy();

    public void disableHotDeploy();

    public boolean isEnableHotDeploy();

    public void start();

    public void stop();

    public void setScanInterval(long interval);
}
