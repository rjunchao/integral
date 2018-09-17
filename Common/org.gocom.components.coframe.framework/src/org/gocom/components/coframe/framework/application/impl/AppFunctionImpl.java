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

import org.gocom.components.coframe.framework.application.AppFuncgroup;
import org.gocom.components.coframe.framework.application.AppFunction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getFunccode <em>Funccode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getFuncname <em>Funcname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getFuncdesc <em>Funcdesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getFuncaction <em>Funcaction</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getParainfo <em>Parainfo</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getIscheck <em>Ischeck</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getFunctype <em>Functype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getIsmenu <em>Ismenu</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFunctionImpl#getAppFuncgroup <em>AppFuncgroup</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements AppFunction;
 */

public class AppFunctionImpl extends ExtendedDataObjectImpl implements AppFunction {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_FUNCCODE = 0;
	public final static int INDEX_FUNCNAME = 1;
	public final static int INDEX_FUNCDESC = 2;
	public final static int INDEX_FUNCACTION = 3;
	public final static int INDEX_PARAINFO = 4;
	public final static int INDEX_ISCHECK = 5;
	public final static int INDEX_FUNCTYPE = 6;
	public final static int INDEX_ISMENU = 7;
	public final static int INDEX_APP_ID = 8;
	public final static int INDEX_TENANT_ID = 9;
	public final static int INDEX_APPFUNCGROUP = 10;
	public final static int SDO_PROPERTY_COUNT = 11;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFunctionImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFunctionImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Funccode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funccode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funccode</em>' attribute.
	 * @see #setFunccode(java.lang.String)
	 */
	public String getFunccode() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCCODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFunccode <em>Funccode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funccode</em>' attribute.
	 * @see #getFunccode()
	 */
	public void setFunccode(String funccode) {
		super.setByIndex(INDEX_FUNCCODE, funccode);
	}

	/**
	 * Returns the value of the '<em><b>Funcname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcname</em>' attribute.
	 * @see #setFuncname(java.lang.String)
	 */
	public String getFuncname() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncname <em>Funcname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcname</em>' attribute.
	 * @see #getFuncname()
	 */
	public void setFuncname(String funcname) {
		super.setByIndex(INDEX_FUNCNAME, funcname);
	}

	/**
	 * Returns the value of the '<em><b>Funcdesc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcdesc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcdesc</em>' attribute.
	 * @see #setFuncdesc(java.lang.String)
	 */
	public String getFuncdesc() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCDESC, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncdesc <em>Funcdesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcdesc</em>' attribute.
	 * @see #getFuncdesc()
	 */
	public void setFuncdesc(String funcdesc) {
		super.setByIndex(INDEX_FUNCDESC, funcdesc);
	}

	/**
	 * Returns the value of the '<em><b>Funcaction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcaction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcaction</em>' attribute.
	 * @see #setFuncaction(java.lang.String)
	 */
	public String getFuncaction() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCACTION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncaction <em>Funcaction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcaction</em>' attribute.
	 * @see #getFuncaction()
	 */
	public void setFuncaction(String funcaction) {
		super.setByIndex(INDEX_FUNCACTION, funcaction);
	}

	/**
	 * Returns the value of the '<em><b>Parainfo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parainfo</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parainfo</em>' attribute.
	 * @see #setParainfo(java.lang.String)
	 */
	public String getParainfo() {
		return DataUtil.toString(super.getByIndex(INDEX_PARAINFO, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getParainfo <em>Parainfo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parainfo</em>' attribute.
	 * @see #getParainfo()
	 */
	public void setParainfo(String parainfo) {
		super.setByIndex(INDEX_PARAINFO, parainfo);
	}

	/**
	 * Returns the value of the '<em><b>Ischeck</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ischeck</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ischeck</em>' attribute.
	 * @see #setIscheck(java.lang.String)
	 */
	public String getIscheck() {
		return DataUtil.toString(super.getByIndex(INDEX_ISCHECK, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIscheck <em>Ischeck</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ischeck</em>' attribute.
	 * @see #getIscheck()
	 */
	public void setIscheck(String ischeck) {
		super.setByIndex(INDEX_ISCHECK, ischeck);
	}

	/**
	 * Returns the value of the '<em><b>Functype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Functype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functype</em>' attribute.
	 * @see #setFunctype(java.lang.String)
	 */
	public String getFunctype() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFunctype <em>Functype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Functype</em>' attribute.
	 * @see #getFunctype()
	 */
	public void setFunctype(String functype) {
		super.setByIndex(INDEX_FUNCTYPE, functype);
	}

	/**
	 * Returns the value of the '<em><b>Ismenu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ismenu</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ismenu</em>' attribute.
	 * @see #setIsmenu(java.lang.String)
	 */
	public String getIsmenu() {
		return DataUtil.toString(super.getByIndex(INDEX_ISMENU, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIsmenu <em>Ismenu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ismenu</em>' attribute.
	 * @see #getIsmenu()
	 */
	public void setIsmenu(String ismenu) {
		super.setByIndex(INDEX_ISMENU, ismenu);
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
	 * Returns the value of the '<em><b>AppFuncgroup</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>AppFuncgroup</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>AppFuncgroup</em>' attribute.
	 * @see #setAppFuncgroup(org.gocom.components.coframe.framework.application.AppFuncgroup)
	 */
	public AppFuncgroup getAppFuncgroup() {
		return (AppFuncgroup) DataUtil.toDataObject(super.getByIndex(INDEX_APPFUNCGROUP, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppFuncgroup <em>AppFuncgroup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppFuncgroup</em>' attribute.
	 * @see #getAppFuncgroup()
	 */
	public void setAppFuncgroup(AppFuncgroup appFuncgroup) {
		super.setByIndex(INDEX_APPFUNCGROUP, appFuncgroup);
	}


}