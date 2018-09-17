package com.xbkj.datasor.bs.framework.core;

import com.grc.datasor.bs.framework.exception.ComponentException;
import com.grc.datasor.bs.framework.naming.Context;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-25
 * Time: 9:30:42
 * 
 * It is a context implementation used for lookup all type components in the 
 * container
 */
public final class ComponentContext<T extends GenericContainer<?>> implements Context {
    private String name;

    private T container;

    public ComponentContext(String name, T container) {
        this.name = name;
        this.container = container;
    }

    public String getComponentName() {
        return name;
    }

    public T getContainer() {
        return container;
    }

    public Object lookup(String name) throws ComponentException {
        return container.getContext().lookup(name);
    }

}
