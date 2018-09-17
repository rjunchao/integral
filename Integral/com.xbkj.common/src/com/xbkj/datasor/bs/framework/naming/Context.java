package com.xbkj.datasor.bs.framework.naming;

import com.grc.datasor.bs.framework.exception.ComponentException;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-17
 * Time: 10:33:03
 */
public interface Context {
    public static final String LOCAL_EJB_JNDI_PREFIX = "java:comp/env/ejb/";

    public static final String LOCAL_JDBC_JNDI_PREFIX = "java:comp/env/jdbc/";

    public static final String LOCAL_JNDI_PREFIX = "java:comp/env/";

    public static final String SERVER = "nc.bs.framework.server.BusinessAppServer";

    public static final String REGIST_SERVICE = "nc.bs.framework.common.RegistService";

    public static final String REMOTE_META_SERVICE = "nc.bs.framework.server.RemoteMetaContext";

    Object lookup(String name) throws ComponentException;
}
