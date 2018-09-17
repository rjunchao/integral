/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.framework.application.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.math.BigDecimal;

import org.gocom.components.coframe.framework.application.AppFuncresource;
import org.gocom.components.coframe.framework.application.AppFunction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getResid <em>Resid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getRestype <em>Restype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getRespath <em>Respath</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getCompackname <em>Compackname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getResname <em>Resname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncresourceImpl#getAppFunction <em>AppFunction</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements AppFuncresource;
 */

public class AppFuncresourceImpl extends ExtendedDataObjectImpl implements AppFuncresource {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_RESID = 0;
	public final static int INDEX_RESTYPE = 1;
	public final static int INDEX_RESPATH = 2;
	public final static int INDEX_COMPACKNAME = 3;
	public final static int INDEX_RESNAME = 4;
	public final static int INDEX_APP_ID = 5;
	public final static int INDEX_TENANT_ID = 6;
	public final static int INDEX_APPFUNCTION = 7;
	public final static int SDO_PROPERTY_COUNT = 8;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFuncresourceImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFuncresourceImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Resid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resid</em>' attribute.
	 * @see #setResid(java.math.BigDecimal)
	 */
	public BigDecimal getResid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_RESID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getResid <em>Resid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resid</em>' attribute.
	 * @see #getResid()
	 */
	public void setResid(BigDecimal resid) {
		super.setByIndex(INDEX_RESID, resid);
	}

	/**
	 * Returns the value of the '<em><b>Restype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Restype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Restype</em>' attribute.
	 * @see #setRestype(java.lang.String)
	 */
	public String getRestype() {
		return DataUtil.toString(super.getByIndex(INDEX_RESTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRestype <em>Restype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Restype</em>' attribute.
	 * @see #getRestype()
	 */
	public void setRestype(String restype) {
		super.setByIndex(INDEX_RESTYPE, restype);
	}

	/**
	 * Returns the value of the '<em><b>Respath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Respath</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Respath</em>' attribute.
	 * @see #setRespath(java.lang.String)
	 */
	public String getRespath() {
		return DataUtil.toString(super.getByIndex(INDEX_RESPATH, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRespath <em>Respath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Respath</em>' attribute.
	 * @see #getRespath()
	 */
	public void setRespath(String respath) {
		super.setByIndex(INDEX_RESPATH, respath);
	}

	/**
	 * Returns the value of the '<em><b>Compackname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compackname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compackname</em>' attribute.
	 * @see #setCompackname(java.lang.String)
	 */
	public String getCompackname() {
		return DataUtil.toString(super.getByIndex(INDEX_COMPACKNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCompackname <em>Compackname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compackname</em>' attribute.
	 * @see #getCompackname()
	 */
	public void setCompackname(String compackname) {
		super.setByIndex(INDEX_COMPACKNAME, compackname);
	}

	/**
	 * Returns the value of the '<em><b>Resname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resname</em>' attribute.
	 * @see #setResname(java.lang.String)
	 */
	public String getResname() {
		return DataUtil.toString(super.getByIndex(INDEX_RESNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getResname <em>Resname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resname</em>' attribute.
	 * @see #getResname()
	 */
	public void setResname(String resname) {
		super.setByIndex(INDEX_RESNAME, resname);
	}

	/**
	 * Returns the value of the '<em><b>App_id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>App_id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>App_id</em>' attribute.
	 * @see #setApp_id(java.lang.String)
	 */
	public String getApp_id() {
		return DataUtil.toString(super.getByIndex(INDEX_APP_ID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getApp_id <em>App_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>App_id</em>' attribute.
	 * @see #getApp_id()
	 */
	public void setApp_id(String app_id) {
		super.setByIndex(INDEX_APP_ID, app_id);
	}

	/**
	 * Returns the value of the '<em><b>Tenant_id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenant_id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenant_id</em>' attribute.
	 * @see #setTenant_id(java.lang.String)
	 */
	public String getTenant_id() {
		return DataUtil.toString(super.getByIndex(INDEX_TENANT_ID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getTenant_id <em>Tenant_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant_id</em>' attribute.
	 * @see #getTenant_id()
	 */
	public void setTenant_id(String tenant_id) {
		super.setByIndex(INDEX_TENANT_ID, tenant_id);
	}

	/**
	 * Returns the value of the '<em><b>AppFunction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>AppFunction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>AppFunction</em>' attribute.
	 * @see #setAppFunction(org.gocom.components.coframe.framework.application.AppFunction)
	 */
	public AppFunction getAppFunction() {
		return (AppFunction) DataUtil.toDataObject(super.getByIndex(INDEX_APPFUNCTION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppFunction <em>AppFunction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppFunction</em>' attribute.
	 * @see #getAppFunction()
	 */
	public void setAppFunction(AppFunction appFunction) {
		super.setByIndex(INDEX_APPFUNCTION, appFunction);
	}


}