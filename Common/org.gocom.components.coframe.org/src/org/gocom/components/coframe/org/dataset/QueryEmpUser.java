/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.org.dataset;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

/**
 * @extends OrgEmployee;
 */
public interface QueryEmpUser extends OrgEmployee {

	public final static String QNAME = "org.gocom.components.coframe.org.dataset.QueryEmpUser";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "QueryEmpUser");

	public final static IObjectFactory<QueryEmpUser> FACTORY = new IObjectFactory<QueryEmpUser>() {
		public QueryEmpUser create() {
			return (QueryEmpUser) DataFactory.INSTANCE.create(TYPE);
		}
	};
	
	public String getStatus();

	public void setStatus(String status);


}