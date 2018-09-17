package com.xbkj.datasor.bs.framework.ejb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xbkj.datasor.bs.framework.naming.Context;
//import com.grc.datasor.bs.framework.util.EJBUtil;

/**
 * @author �ι���
 * 
 * High performance than NamedEJBToBServiceHandler
 */
public class HomedEJBServiceHandler //extends AbstractEJBServiceHandler 
{

    Context ctx;

    private Object ejbHome;

    private transient Method createMethod;

    private transient Method removeMethod;

//    public HomedEJBServiceHandler(String name, Object ejbHome, Method createMethod, Method removeMethod, Context ctx) {
//        super(name);
//        this.ejbHome = ejbHome;
//        this.ctx = ctx;
//        this.createMethod = createMethod;
//        this.removeMethod = removeMethod;
//    }
//
//    protected Object getBServiceEJB() throws Throwable {
//        Object ejbObject = null;
//        try {
//            ejbObject = EJBUtil.createStls(ejbHome, createMethod);
//        } catch (InvocationTargetException e) {
//            throw e.getTargetException();
//        }
//        return ejbObject;
//    }

    protected void removeBServiceEJB(Object ejb) throws Throwable {
        if (ejb != null) {
            try {
                removeMethod.invoke(ejb, new Object[0]);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }

        }
    }

}