package com.xbkj.datasor.bs.framework.core;

import com.xbkj.datasor.bs.framework.exception.EnhanceException;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-25
 * Time: 9:35:27
 * 
 * it is used to enhance the component with some custom feature
 */
public interface Enhancer {
    public boolean accept(Meta meta, Object comp);

    public Object enhance(Meta meta, Object comp) throws EnhanceException;
}
