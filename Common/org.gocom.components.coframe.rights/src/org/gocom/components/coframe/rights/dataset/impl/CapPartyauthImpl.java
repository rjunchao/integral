/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.rights.dataset.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.util.Date;

import org.gocom.components.coframe.rights.dataset.CapPartyauth;
import org.gocom.components.coframe.rights.dataset.CapRole;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getRoleType <em>RoleType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getPartyId <em>PartyId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getCreatetime <em>Createtime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapPartyauthImpl#getCapRole <em>CapRole</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements CapPartyauth;
 */

public class CapPartyauthImpl extends ExtendedDataObjectImpl implements CapPartyauth {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_TENANTID = 0;
	public final static int INDEX_ROLETYPE = 1;
	public final static int INDEX_PARTYID = 2;
	public final static int INDEX_PARTYTYPE = 3;
	public final static int INDEX_CREATEUSER = 4;
	public final static int INDEX_CREATETIME = 5;
	public final static int INDEX_CAPROLE = 6;
	public final static int SDO_PROPERTY_COUNT = 7;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapPartyauthImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapPartyauthImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
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

	/**
	 * Returns the value of the '<em><b>RoleType</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RoleType</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RoleType</em>' attribute.
	 * @see #setRoleType(java.lang.String)
	 */
	public String getRoleType() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLETYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleType <em>RoleType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleType</em>' attribute.
	 * @see #getRoleType()
	 */
	public void setRoleType(String roleType) {
		super.setByIndex(INDEX_ROLETYPE, roleType);
	}

	/**
	 * Returns the value of the '<em><b>PartyId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PartyId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PartyId</em>' attribute.
	 * @see #setPartyId(java.lang.String)
	 */
	public String getPartyId() {
		return DataUtil.toString(super.getByIndex(INDEX_PARTYID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPartyId <em>PartyId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyId</em>' attribute.
	 * @see #getPartyId()
	 */
	public void setPartyId(String partyId) {
		super.setByIndex(INDEX_PARTYID, partyId);
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
	 * Returns the value of the '<em><b>Createuser</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Createuser</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Createuser</em>' attribute.
	 * @see #setCreateuser(java.lang.String)
	 */
	public String getCreateuser() {
		return DataUtil.toString(super.getByIndex(INDEX_CREATEUSER, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCreateuser <em>Createuser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createuser</em>' attribute.
	 * @see #getCreateuser()
	 */
	public void setCreateuser(String createuser) {
		super.setByIndex(INDEX_CREATEUSER, createuser);
	}

	/**
	 * Returns the value of the '<em><b>Createtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Createtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Createtime</em>' attribute.
	 * @see #setCreatetime(java.util.Date)
	 */
	public Date getCreatetime() {
		return DataUtil.toDate(super.getByIndex(INDEX_CREATETIME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime) {
		super.setByIndex(INDEX_CREATETIME, createtime);
	}

	/**
	 * Returns the value of the '<em><b>CapRole</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>CapRole</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>CapRole</em>' attribute.
	 * @see #setCapRole(org.gocom.components.coframe.rights.dataset.CapRole)
	 */
	public CapRole getCapRole() {
		return (CapRole) DataUtil.toDataObject(super.getByIndex(INDEX_CAPROLE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCapRole <em>CapRole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>CapRole</em>' attribute.
	 * @see #getCapRole()
	 */
	public void setCapRole(CapRole capRole) {
		super.setByIndex(INDEX_CAPROLE, capRole);
	}


}