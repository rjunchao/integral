package com.xbkj.datasor.bs.framework.component;

/**
 * @author �ι���
 *
 * Date: 2006-3-21
 * Time: 16:54:26
 */
public interface RemoteProcessComponent extends ActiveComponent {
    public void preProcess();
    
    /**
     * Process for all post invocation
     *
     */
    public void postProcess();
    
    /**
     * Only process when error occurred
     * @param thr
     */
    public void postErrorProcess(Throwable thr);
}
