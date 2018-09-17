package com.xbkj.datasor.bs.framework.component;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author �ι���
 *
 * Date: 2006-3-21
 * Time: 17:04:51
 */
public interface RemoteProcessComponetFactory {
    /**
     * if no, return empty array, not null
     * @return
     */
    List getPostProcessList();

    void addPostProcess(Class postProcessClass);

    void removePostProcess(Class postProcessClass);

    void addPostProcess(RemoteProcessComponent postProcess);

    void removePostProcess(RemoteProcessComponent postProcess);

    void addThreadScopePostProcess(String name, RemoteProcessComponent postProcess);

    void removeThreadScopePostProcess(String name);

    RemoteProcessComponent getThreadScopePostProcess(String name);

    void clearThreadScopePostProcess();

    Collection getThreadScopePostProcesses();

    void preProcess();

    void postErrorProcess(Throwable thr);

    void postProcess();

}
