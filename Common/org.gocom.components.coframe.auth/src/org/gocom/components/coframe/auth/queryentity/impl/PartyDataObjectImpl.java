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

import org.gocom.components.coframe.auth.queryentity.PartyDataObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.PartyDataObjectImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.PartyDataObjectImpl#getCode <em>Code</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.PartyDataObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.PartyDataObjectImpl#getPartyTypeID <em>PartyTypeID</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.PartyDataObjectImpl#getTenantID <em>TenantID</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements PartyDataObject;
 */

public class PartyDataObjectImpl extends ExtendedDataObjectImpl implements PartyDataObject {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_ID = 0;
	public final static int INDEX_CODE = 1;
	public final static int INDEX_NAME = 2;
	public final static int INDEX_PARTYTYPEID = 3;
	public final static int INDEX_TENANTID = 4;
	public final static int SDO_PROPERTY_COUNT = 5;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PartyDataObjectImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public PartyDataObjectImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(java.lang.String)
	 */
	public String getId() {
		return DataUtil.toString(super.getByIndex(INDEX_ID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 */
	public void setId(String id) {
		super.setByIndex(INDEX_ID, id);
	}

	/**
	 * Returns the value of the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(java.lang.String)
	 */
	public String getCode() {
		return DataUtil.toString(super.getByIndex(INDEX_CODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 */
	public void setCode(String code) {
		super.setByIndex(INDEX_CODE, code);
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(java.lang.String)
	 */
	public String getName() {
		return DataUtil.toString(super.getByIndex(INDEX_NAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 */
	public void setName(String name) {
		super.setByIndex(INDEX_NAME, name);
	}

	/**
	 * Returns the value of the '<em><b>PartyTypeID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PartyTypeID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PartyTypeID</em>' attribute.
	 * @see #setPartyTypeID(java.lang.String)
	 */
	public String getPartyTypeID() {
		return DataUtil.toString(super.getByIndex(INDEX_PARTYTYPEID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPartyTypeID <em>PartyTypeID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyTypeID</em>' attribute.
	 * @see #getPartyTypeID()
	 */
	public void setPartyTypeID(String partyTypeID) {
		super.setByIndex(INDEX_PARTYTYPEID, partyTypeID);
	}

	/**
	 * Returns the value of the '<em><b>TenantID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>TenantID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>TenantID</em>' attribute.
	 * @see #setTenantID(java.lang.String)
	 */
	public String getTenantID() {
		return DataUtil.toString(super.getByIndex(INDEX_TENANTID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getTenantID <em>TenantID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantID</em>' attribute.
	 * @see #getTenantID()
	 */
	public void setTenantID(String tenantID) {
		super.setByIndex(INDEX_TENANTID, tenantID);
	}


}