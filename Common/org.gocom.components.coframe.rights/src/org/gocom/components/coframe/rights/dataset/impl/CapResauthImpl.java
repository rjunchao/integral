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

import org.gocom.components.coframe.rights.dataset.CapResauth;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getPartyId <em>PartyId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getResId <em>ResId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getResType <em>ResType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getResState <em>ResState</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getPartyScope <em>PartyScope</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapResauthImpl#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements CapResauth;
 */

public class CapResauthImpl extends ExtendedDataObjectImpl implements CapResauth {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_PARTYID = 0;
	public final static int INDEX_PARTYTYPE = 1;
	public final static int INDEX_RESID = 2;
	public final static int INDEX_RESTYPE = 3;
	public final static int INDEX_TENANTID = 4;
	public final static int INDEX_RESSTATE = 5;
	public final static int INDEX_PARTYSCOPE = 6;
	public final static int INDEX_CREATEUSER = 7;
	public final static int INDEX_CREATETIME = 8;
	public final static int SDO_PROPERTY_COUNT = 9;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapResauthImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapResauthImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
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
	 * Returns the value of the '<em><b>ResId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ResId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ResId</em>' attribute.
	 * @see #setResId(java.lang.String)
	 */
	public String getResId() {
		return DataUtil.toString(super.getByIndex(INDEX_RESID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getResId <em>ResId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResId</em>' attribute.
	 * @see #getResId()
	 */
	public void setResId(String resId) {
		super.setByIndex(INDEX_RESID, resId);
	}

	/**
	 * Returns the value of the '<em><b>ResType</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ResType</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ResType</em>' attribute.
	 * @see #setResType(java.lang.String)
	 */
	public String getResType() {
		return DataUtil.toString(super.getByIndex(INDEX_RESTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getResType <em>ResType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResType</em>' attribute.
	 * @see #getResType()
	 */
	public void setResType(String resType) {
		super.setByIndex(INDEX_RESTYPE, resType);
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
	 * Returns the value of the '<em><b>ResState</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ResState</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ResState</em>' attribute.
	 * @see #setResState(java.lang.String)
	 */
	public String getResState() {
		return DataUtil.toString(super.getByIndex(INDEX_RESSTATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getResState <em>ResState</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResState</em>' attribute.
	 * @see #getResState()
	 */
	public void setResState(String resState) {
		super.setByIndex(INDEX_RESSTATE, resState);
	}

	/**
	 * Returns the value of the '<em><b>PartyScope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PartyScope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PartyScope</em>' attribute.
	 * @see #setPartyScope(java.lang.String)
	 */
	public String getPartyScope() {
		return DataUtil.toString(super.getByIndex(INDEX_PARTYSCOPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPartyScope <em>PartyScope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyScope</em>' attribute.
	 * @see #getPartyScope()
	 */
	public void setPartyScope(String partyScope) {
		super.setByIndex(INDEX_PARTYSCOPE, partyScope);
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


}