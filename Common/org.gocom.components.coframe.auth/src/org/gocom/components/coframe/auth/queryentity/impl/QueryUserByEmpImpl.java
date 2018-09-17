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

import java.math.BigDecimal;

import org.gocom.components.coframe.auth.queryentity.QueryUserByEmp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getEmpid <em>Empid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getUserId <em>UserId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getUserName <em>UserName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link org.gocom.components.coframe.auth.queryentity.impl.QueryUserByEmpImpl#getOperatorId <em>OperatorId</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements QueryUserByEmp;
 */

public class QueryUserByEmpImpl extends ExtendedDataObjectImpl implements QueryUserByEmp {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_EMPID = 0;
	public final static int INDEX_TENANTID = 1;
	public final static int INDEX_USERID = 2;
	public final static int INDEX_USERNAME = 3;
	public final static int INDEX_EMAIL = 4;
	public final static int INDEX_OPERATORID = 5;
	public final static int SDO_PROPERTY_COUNT = 6;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public QueryUserByEmpImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public QueryUserByEmpImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

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
	public BigDecimal getEmpid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_EMPID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getEmpid <em>Empid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empid</em>' attribute.
	 * @see #getEmpid()
	 */
	public void setEmpid(BigDecimal empid) {
		super.setByIndex(INDEX_EMPID, empid);
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
	public String getUserId() {
		return DataUtil.toString(super.getByIndex(INDEX_USERID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUserId <em>UserId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>UserId</em>' attribute.
	 * @see #getUserId()
	 */
	public void setUserId(String userId) {
		super.setByIndex(INDEX_USERID, userId);
	}

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
	public String getUserName() {
		return DataUtil.toString(super.getByIndex(INDEX_USERNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUserName <em>UserName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>UserName</em>' attribute.
	 * @see #getUserName()
	 */
	public void setUserName(String userName) {
		super.setByIndex(INDEX_USERNAME, userName);
	}

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
	public String getEmail() {
		return DataUtil.toString(super.getByIndex(INDEX_EMAIL, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 */
	public void setEmail(String email) {
		super.setByIndex(INDEX_EMAIL, email);
	}

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
	public String getOperatorId() {
		return DataUtil.toString(super.getByIndex(INDEX_OPERATORID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOperatorId <em>OperatorId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OperatorId</em>' attribute.
	 * @see #getOperatorId()
	 */
	public void setOperatorId(String operatorId) {
		super.setByIndex(INDEX_OPERATORID, operatorId);
	}


}