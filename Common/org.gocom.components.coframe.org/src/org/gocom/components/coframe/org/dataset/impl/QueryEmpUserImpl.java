/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.org.dataset.impl;

import commonj.sdo.Type;

import org.gocom.components.coframe.org.dataset.QueryEmpUser;

/**
 * @extends OrgEmployeeImpl;
 * 
 * @implements QueryEmpUser;
 */

public class QueryEmpUserImpl extends OrgEmployeeImpl implements QueryEmpUser {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_STATUS = 40;
	public final static int SDO_PROPERTY_COUNT = 41;

	public QueryEmpUserImpl() {
		this(QueryEmpUser.TYPE);
	}

	public QueryEmpUserImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(QueryEmpUser.TYPE);
	}

	public String getStatus() {
		return null;
	}

	public void setStatus(String status) {
		
	}

}