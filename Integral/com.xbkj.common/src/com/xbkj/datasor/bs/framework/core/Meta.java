package com.xbkj.datasor.bs.framework.core;

import com.xbkj.datasor.bs.framework.exception.ComponentException;

public interface Meta extends Priority{
    public String getName();

    public boolean isSingleton();

    public Instantiator getInstantiator() throws ComponentException;

    public Class<?>[] getInterfaces();

    public boolean isActive();

    public String[] getAlias();
    
    public GenericContainer<?> getContainer();
    
    public ExtensionProcessor[] getExtensionProcessors();
    
    public EnhancerManager getEnhancerManager();
    
    public void setEnhancerManager(EnhancerManager factory);
    
    public boolean needContainerEnhance();
    
    
    
    
}
