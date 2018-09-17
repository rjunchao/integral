/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.auth.queryentity;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPositionid <em>Positionid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPosicode <em>Posicode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPosiname <em>Posiname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getTenantId <em>TenantId</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface QueryPositionByRole extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.auth.queryentity.QueryPositionByRole";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.auth.queryentity", "QueryPositionByRole");

	public final static IObjectFactory<QueryPositionByRole> FACTORY = new IObjectFactory<QueryPositionByRole>() {
		public QueryPositionByRole create() {
			return (QueryPositionByRole) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getRoleId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getRoleId <em>RoleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleId</em>' attribute.
	 * @see #getRoleId()
	 */
	public void setRoleId(String roleId);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType);

	/**
	 * Returns the value of the '<em><b>Positionid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Positionid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positionid</em>' attribute.
	 * @see #setPositionid(java.math.BigDecimal)
	 */
	public BigDecimal getPositionid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPositionid <em>Positionid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positionid</em>' attribute.
	 * @see #getPositionid()
	 */
	public void setPositionid(BigDecimal positionid);

	/**
	 * Returns the value of the '<em><b>Posicode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posicode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posicode</em>' attribute.
	 * @see #setPosicode(java.lang.String)
	 */
	public String getPosicode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPosicode <em>Posicode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posicode</em>' attribute.
	 * @see #getPosicode()
	 */
	public void setPosicode(String posicode);

	/**
	 * Returns the value of the '<em><b>Posiname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posiname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posiname</em>' attribute.
	 * @see #setPosiname(java.lang.String)
	 */
	public String getPosiname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getPosiname <em>Posiname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posiname</em>' attribute.
	 * @see #getPosiname()
	 */
	public void setPosiname(String posiname);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryPositionByRole#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);


}