package com.xbkj.datasor.bs.framework.core;

import com.xbkj.datasor.bs.framework.common.ComponentMetaVO;

/**
 * Created by IntelliJ IDEA.
 * User: �ι���
 * Date: 2005-1-21
 * Time: 19:53:06
 * 
 * The deployed component meta informatioin of the component
 */
public interface ComponentMeta extends Meta {
    public static final String CLUSTER_SP = "SP";

    public static final String NORMAL = "NORMAL";

    public boolean isPublic();

    public boolean isRemote();

    public TxAttribute getTxAttribute();

    public String getCluster();

    public String getEjbName();

    public Container getContainer();

    public ComponentMetaVO getComponentMetaVO();

}
