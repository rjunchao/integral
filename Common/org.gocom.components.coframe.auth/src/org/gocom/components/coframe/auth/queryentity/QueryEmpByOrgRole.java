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
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getPartyType <em>PartyType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getOrgid <em>Orgid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpid <em>Empid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpcode <em>Empcode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpname <em>Empname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getTenantId <em>TenantId</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface QueryEmpByOrgRole extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.auth.queryentity", "QueryEmpByOrgRole");

	public final static IObjectFactory<QueryEmpByOrgRole> FACTORY = new IObjectFactory<QueryEmpByOrgRole>() {
		public QueryEmpByOrgRole create() {
			return (QueryEmpByOrgRole) DataFactory.INSTANCE.create(TYPE);
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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getRoleId <em>RoleId</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getPartyType <em>PartyType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PartyType</em>' attribute.
	 * @see #getPartyType()
	 */
	public void setPartyType(String partyType);

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
	public BigDecimal getOrgid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getOrgid <em>Orgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgid</em>' attribute.
	 * @see #getOrgid()
	 */
	public void setOrgid(BigDecimal orgid);

	/**
	 * Returns the value of the '<em><b>Empid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empid</em>' attribute.
	 * @see #setEmpid(java.math.BigDecimal)
	 */
	public BigDecimal getEmpid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpid <em>Empid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empid</em>' attribute.
	 * @see #getEmpid()
	 */
	public void setEmpid(BigDecimal empid);

	/**
	 * Returns the value of the '<em><b>Empcode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empcode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empcode</em>' attribute.
	 * @see #setEmpcode(java.lang.String)
	 */
	public String getEmpcode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpcode <em>Empcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empcode</em>' attribute.
	 * @see #getEmpcode()
	 */
	public void setEmpcode(String empcode);

	/**
	 * Returns the value of the '<em><b>Empname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empname</em>' attribute.
	 * @see #setEmpname(java.lang.String)
	 */
	public String getEmpname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getEmpname <em>Empname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empname</em>' attribute.
	 * @see #getEmpname()
	 */
	public void setEmpname(String empname);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.auth.queryentity.QueryEmpByOrgRole#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);


}