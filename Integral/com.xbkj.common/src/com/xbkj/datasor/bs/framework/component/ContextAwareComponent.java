package com.xbkj.datasor.bs.framework.component;

import com.xbkj.datasor.bs.framework.core.ComponentContext;
import com.xbkj.datasor.bs.framework.core.GenericContainer;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-15
 * Time: 10:35:17
 *
 * It is the basic interface provided by singletonObj layer. every singletonObj such a
 * s BService,BProcess...is derived from this interface.
 * <b>Attention:</b> the framework can manage any arbitrary bean, the compoent only provide a
 * model for singletonObj, service development. it is not mandatory to implement this interface
 * only for managable by the framework
 *
 */
public interface ContextAwareComponent<T extends GenericContainer<?>> {

    /**
     * As ervery arbitrary bean is deployed in and managed by module container, some bean
     * maybe needs service provided by module container, this method just for meet such
     * rquirement.
     *
     * @param context - the module context provided by module container
     */
    public void setComponentContext(ComponentContext<T> context);

}
