/*******************************************************************************
 * $Header: /cvsroot/OPENSOURCE10/develop/opensources/coframe/source/org.gocom.components.coframe/org.gocom.components.coframe.flowconfig/src/org/gocom/components/coframe/flowconfig/helper/WFConstants.java,v 1.1 2013/05/30 19:23:54 wangwb Exp $
 * $Revision: 1.1 $
 * $Date: 2013/05/30 19:23:54 $
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2012 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2013-5-31
 *******************************************************************************/


package org.gocom.components.coframe.flowconfig.helper;

/**
 * TODO 此处填写 class 信息
 *
 * @author wangwb (mailto:wangwb@primeton.com)
 */

public class WFConstants {
	public static final String PROC_PUBLISH_QUERY_ALL = "all";
	public static final String PROC_PUBLISH_QUERY_PUBLISHED = "published";
	public static final String PROC_PUBLISH_QUERY_UNPUBLISHED = "unpublished";
	public static final int DEPLOY_STATUS_NEW_NEW_VERSION = 0;
	public static final int DEPLOY_STATUS_OVERLAP_OLD_VERSION = 1;
	public static final int DEPLOY_STATUS_OVERLAP_LASTEST_VERSION = 2;
}

/*
 * 修改历史 
 * $Log: WFConstants.java,v $
 * Revision 1.1  2013/05/30 19:23:54  wangwb
 * Update:BUG 40630
 *
 */