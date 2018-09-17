package com.xbkj.datasor.bs.framework.core;

import javax.naming.Context;

import com.grc.datasor.bs.framework.exception.ComponentNotFoundException;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-11-23
 * Time: ����10:38:05
 *
 */
public interface JndiContext {

    public Context getInitialContext();

    public Object lookup(String jndiName) throws ComponentNotFoundException;

    public Object lookupWithoutCache(String jndiName) throws ComponentNotFoundException;

}
