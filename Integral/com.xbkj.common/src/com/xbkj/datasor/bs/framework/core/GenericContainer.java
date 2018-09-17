package com.xbkj.datasor.bs.framework.core;

import com.xbkj.datasor.bs.framework.exception.ComponentException;
import com.xbkj.datasor.bs.framework.naming.Context;
import com.xbkj.log.bs.logging.Log;

/**
 * 
 * Created by UFSoft. User: �ι���
 * 
 * Date: 2007-12-20 Time: ����01:17:17
 * 
 */
public interface GenericContainer<T> extends LifeCycle {
	public String getName();

	public void register(T meta) throws ComponentException;

	public T deregister(String name) throws ComponentException;

	public T getMeta(String name) throws ComponentException;

	public ClassLoader getClassLoader();

	public Context getContext();

	public T[] getMetas();

	public boolean contains(String name);

	public State getState();

	public Log getLogger();

	public LightBuilder newBuilder();

	public EnhancerManager getEnhancerManager();

	public void setEnhancerManager(EnhancerManager factory);

	<P> P getExtension(Class<P> extensionType);

	<P> void setExtension(P extension, Class<P> extensionType);

}
