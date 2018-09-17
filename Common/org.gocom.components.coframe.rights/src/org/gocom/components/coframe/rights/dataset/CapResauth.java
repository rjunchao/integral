/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.rights.dataset;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyId <em>PartyId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResId <em>ResId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResType <em>ResType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResState <em>ResState</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyScope <em>PartyScope</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapResauth#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface CapResauth extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.rights.dataset.CapResauth";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.rights.dataset", "CapResauth");

	public final static IObjectFactory<CapResauth> FACTORY = new IObjectFactory<CapResauth>() {
		public CapResauth create() {
			return (CapResauth) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getPartyId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyId <em>PartyId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyId</em>' attribute.
	 * @see #getPartyId()
	 */
	public void setPartyId(String partyId);

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
	public String getPartyType();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType);

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
	public String getResId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResId <em>ResId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResId</em>' attribute.
	 * @see #getResId()
	 */
	public void setResId(String resId);

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
	public String getResType();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResType <em>ResType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResType</em>' attribute.
	 * @see #getResType()
	 */
	public void setResType(String resType);

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
	public String getTenantId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);

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
	public String getResState();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getResState <em>ResState</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ResState</em>' attribute.
	 * @see #getResState()
	 */
	public void setResState(String resState);

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
	public String getPartyScope();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getPartyScope <em>PartyScope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyScope</em>' attribute.
	 * @see #getPartyScope()
	 */
	public void setPartyScope(String partyScope);

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
	public String getCreateuser();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getCreateuser <em>Createuser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createuser</em>' attribute.
	 * @see #getCreateuser()
	 */
	public void setCreateuser(String createuser);

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
	public Date getCreatetime();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapResauth#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime);


}