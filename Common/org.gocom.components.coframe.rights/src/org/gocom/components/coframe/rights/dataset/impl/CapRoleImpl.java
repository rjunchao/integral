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

import org.gocom.components.coframe.rights.dataset.CapRole;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getRoleId <em>RoleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getRoleCode <em>RoleCode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getRoleName <em>RoleName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getRoleDesc <em>RoleDesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapRoleImpl#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements CapRole;
 */

public class CapRoleImpl extends ExtendedDataObjectImpl implements CapRole {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_ROLEID = 0;
	public final static int INDEX_TENANTID = 1;
	public final static int INDEX_ROLECODE = 2;
	public final static int INDEX_ROLENAME = 3;
	public final static int INDEX_ROLEDESC = 4;
	public final static int INDEX_CREATEUSER = 5;
	public final static int INDEX_CREATETIME = 6;
	public final static int SDO_PROPERTY_COUNT = 7;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapRoleImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapRoleImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

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
	public String getRoleId() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLEID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleId <em>RoleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleId</em>' attribute.
	 * @see #getRoleId()
	 */
	public void setRoleId(String roleId) {
		super.setByIndex(INDEX_ROLEID, roleId);
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
	public String getRoleCode() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLECODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleCode <em>RoleCode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleCode</em>' attribute.
	 * @see #getRoleCode()
	 */
	public void setRoleCode(String roleCode) {
		super.setByIndex(INDEX_ROLECODE, roleCode);
	}

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
	public String getRoleName() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLENAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleName <em>RoleName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleName</em>' attribute.
	 * @see #getRoleName()
	 */
	public void setRoleName(String roleName) {
		super.setByIndex(INDEX_ROLENAME, roleName);
	}

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
	public String getRoleDesc() {
		return DataUtil.toString(super.getByIndex(INDEX_ROLEDESC, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRoleDesc <em>RoleDesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RoleDesc</em>' attribute.
	 * @see #getRoleDesc()
	 */
	public void setRoleDesc(String roleDesc) {
		super.setByIndex(INDEX_ROLEDESC, roleDesc);
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