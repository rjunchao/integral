/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.org.dataset;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getId <em>Id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getCode <em>Code</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getName <em>Name</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getPartyTypeID <em>PartyTypeID</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getTenantID <em>TenantID</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface OrgPartyDataObject extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.org.dataset.OrgPartyDataObject";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "OrgPartyDataObject");

	public final static IObjectFactory<OrgPartyDataObject> FACTORY = new IObjectFactory<OrgPartyDataObject>() {
		public OrgPartyDataObject create() {
			return (OrgPartyDataObject) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 */
	public void setId(String id);

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
	public String getCode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 */
	public void setCode(String code);

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
	public String getName();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 */
	public void setName(String name);

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
	public String getPartyTypeID();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getPartyTypeID <em>PartyTypeID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyTypeID</em>' attribute.
	 * @see #getPartyTypeID()
	 */
	public void setPartyTypeID(String partyTypeID);

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
	public String getTenantID();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPartyDataObject#getTenantID <em>TenantID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantID</em>' attribute.
	 * @see #getTenantID()
	 */
	public void setTenantID(String tenantID);


}