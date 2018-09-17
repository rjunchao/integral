/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.auth.queryentity.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.math.BigDecimal;

import org.gocom.components.coframe.auth.queryentity.QueryOrgByRole;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getOrgid <em>Orgid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getOrgcode <em>Orgcode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getOrgname <em>Orgname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryOrgByRoleImpl#getTenantId <em>TenantId</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements QueryOrgByRole;
 */

public class QueryOrgByRoleImpl extends ExtendedDataObjectImpl implements QueryOrgByRole {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_ROLEID = 0;
	public final static int INDEX_PARTYTYPE = 1;
	public final static int INDEX_ORGID = 2;
	public final static int INDEX_ORGCODE = 3;
	public final static int INDEX_ORGNAME = 4;
	public final static int INDEX_TENANTID = 5;
	public final static int SDO_PROPERTY_COUNT = 6;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public QueryOrgByRoleImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public QueryOrgByRoleImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>RoleId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RoleId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RoleId</em>' attribute.
	 * @see #setRoleId(java.lang.String)
	 */
	public String getRoleId() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLEID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleId <em>RoleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleId</em>' attribute.
	 * @see #getRoleId()
	 */
	public void setRoleId(String roleId) {
		super.setByIndex(INDEX_ROLEID, roleId);
	}

	/**
	 * Returns the value of the '<em><b>PartyType</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PartyType</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PartyType</em>' attribute.
	 * @see #setPartyType(java.lang.String)
	 */
	public String getPartyType() {
		return DataUtil.toString(super.getByIndex(INDEX_PARTYTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType) {
		super.setByIndex(INDEX_PARTYTYPE, partyType);
	}

	/**
	 * Returns the value of the '<em><b>Orgid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orgid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orgid</em>' attribute.
	 * @see #setOrgid(java.math.BigDecimal)
	 */
	public BigDecimal getOrgid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_ORGID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgid <em>Orgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgid</em>' attribute.
	 * @see #getOrgid()
	 */
	public void setOrgid(BigDecimal orgid) {
		super.setByIndex(INDEX_ORGID, orgid);
	}

	/**
	 * Returns the value of the '<em><b>Orgcode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orgcode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orgcode</em>' attribute.
	 * @see #setOrgcode(java.lang.String)
	 */
	public String getOrgcode() {
		return DataUtil.toString(super.getByIndex(INDEX_ORGCODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgcode <em>Orgcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgcode</em>' attribute.
	 * @see #getOrgcode()
	 */
	public void setOrgcode(String orgcode) {
		super.setByIndex(INDEX_ORGCODE, orgcode);
	}

	/**
	 * Returns the value of the '<em><b>Orgname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orgname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orgname</em>' attribute.
	 * @see #setOrgname(java.lang.String)
	 */
	public String getOrgname() {
		return DataUtil.toString(super.getByIndex(INDEX_ORGNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgname <em>Orgname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgname</em>' attribute.
	 * @see #getOrgname()
	 */
	public void setOrgname(String orgname) {
		super.setByIndex(INDEX_ORGNAME, orgname);
	}

	/**
	 * Returns the value of the '<em><b>TenantId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>TenantId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>TenantId</em>' attribute.
	 * @see #setTenantId(java.lang.String)
	 */
	public String getTenantId() {
		return DataUtil.toString(super.getByIndex(INDEX_TENANTID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId) {
		super.setByIndex(INDEX_TENANTID, tenantId);
	}


}