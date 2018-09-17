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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getUserId <em>UserId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getUserName <em>UserName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getOperatorId <em>OperatorId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getEmail <em>Email</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface QueryUserByRole extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.auth.queryentity.QueryUserByRole";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.auth.queryentity", "QueryUserByRole");

	public final static IObjectFactory<QueryUserByRole> FACTORY = new IObjectFactory<QueryUserByRole>() {
		public QueryUserByRole create() {
			return (QueryUserByRole) DataFactory.INSTANCE.create(TYPE);
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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getRoleId <em>RoleId</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType);

	/**
	 * Returns the value of the '<em><b>UserId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>UserId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>UserId</em>' attribute.
	 * @see #setUserId(java.lang.String)
	 */
	public String getUserId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getUserId <em>UserId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>UserId</em>' attribute.
	 * @see #getUserId()
	 */
	public void setUserId(String userId);

	/**
	 * Returns the value of the '<em><b>UserName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>UserName</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>UserName</em>' attribute.
	 * @see #setUserName(java.lang.String)
	 */
	public String getUserName();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getUserName <em>UserName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>UserName</em>' attribute.
	 * @see #getUserName()
	 */
	public void setUserName(String userName);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);

	/**
	 * Returns the value of the '<em><b>OperatorId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>OperatorId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>OperatorId</em>' attribute.
	 * @see #setOperatorId(java.lang.String)
	 */
	public String getOperatorId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getOperatorId <em>OperatorId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OperatorId</em>' attribute.
	 * @see #getOperatorId()
	 */
	public void setOperatorId(String operatorId);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(java.lang.String)
	 */
	public String getEmail();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryUserByRole#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 */
	public void setEmail(String email);


}