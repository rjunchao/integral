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
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getRoleType <em>RoleType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getPartyId <em>PartyId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCreatetime <em>Createtime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCapRole <em>CapRole</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface CapPartyauth extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.rights.dataset.CapPartyauth";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.rights.dataset", "CapPartyauth");

	public final static IObjectFactory<CapPartyauth> FACTORY = new IObjectFactory<CapPartyauth>() {
		public CapPartyauth create() {
			return (CapPartyauth) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);

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
	public String getRoleType();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getRoleType <em>RoleType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleType</em>' attribute.
	 * @see #getRoleType()
	 */
	public void setRoleType(String roleType);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getPartyId <em>PartyId</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCreateuser <em>Createuser</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime);

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
	public CapRole getCapRole();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapPartyauth#getCapRole <em>CapRole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>CapRole</em>' attribute.
	 * @see #getCapRole()
	 */
	public void setCapRole(CapRole capRole);


}