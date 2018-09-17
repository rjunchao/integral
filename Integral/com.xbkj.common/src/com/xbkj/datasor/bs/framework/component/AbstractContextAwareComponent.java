package com.xbkj.datasor.bs.framework.component;

import com.xbkj.datasor.bs.framework.core.ComponentContext;
import com.xbkj.datasor.bs.framework.core.GenericContainer;

/**
 * Created by UFSoft.
 * User: �ι���
 * Date: 2005-1-22
 * Time: 15:12:06
 * 
 * �����ContextAwareComponent,�����Ѱ˽�����
 */
public abstract class AbstractContextAwareComponent<T extends GenericContainer<?>> implements ContextAwareComponent<T> {
    ComponentContext<T> context;

    public void setComponentContext(ComponentContext<T> context) {
        this.context = context;
    }

    public ComponentContext<T> getComponentContext() {
        return context;
    }

}
