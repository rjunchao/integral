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
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleCode <em>RoleCode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleName <em>RoleName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleDesc <em>RoleDesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.CapRole#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface CapRole extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.rights.dataset.CapRole";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.rights.dataset", "CapRole");

	public final static IObjectFactory<CapRole> FACTORY = new IObjectFactory<CapRole>() {
		public CapRole create() {
			return (CapRole) DataFactory.INSTANCE.create(TYPE);
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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleId <em>RoleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleId</em>' attribute.
	 * @see #getRoleId()
	 */
	public void setRoleId(String roleId);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);

	/**
	 * Returns the value of the '<em><b>RoleCode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RoleCode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RoleCode</em>' attribute.
	 * @see #setRoleCode(java.lang.String)
	 */
	public String getRoleCode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleCode <em>RoleCode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleCode</em>' attribute.
	 * @see #getRoleCode()
	 */
	public void setRoleCode(String roleCode);

	/**
	 * Returns the value of the '<em><b>RoleName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RoleName</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RoleName</em>' attribute.
	 * @see #setRoleName(java.lang.String)
	 */
	public String getRoleName();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleName <em>RoleName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleName</em>' attribute.
	 * @see #getRoleName()
	 */
	public void setRoleName(String roleName);

	/**
	 * Returns the value of the '<em><b>RoleDesc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RoleDesc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RoleDesc</em>' attribute.
	 * @see #setRoleDesc(java.lang.String)
	 */
	public String getRoleDesc();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getRoleDesc <em>RoleDesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleDesc</em>' attribute.
	 * @see #getRoleDesc()
	 */
	public void setRoleDesc(String roleDesc);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getCreateuser <em>Createuser</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.rights.dataset.CapRole#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime);


}