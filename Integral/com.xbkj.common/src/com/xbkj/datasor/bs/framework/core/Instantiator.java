package com.xbkj.datasor.bs.framework.core;

import com.xbkj.datasor.bs.framework.exception.InstException;
import com.xbkj.datasor.bs.framework.naming.Context;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-21
 * Time: 19:57:29
 * 
 */
public interface Instantiator {
    public Object instantiate(Context ctx, String name, Object args[]) throws InstException;
}
