/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.framework.application;

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
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getFunccode <em>Funccode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncname <em>Funcname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncdesc <em>Funcdesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncaction <em>Funcaction</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getParainfo <em>Parainfo</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getIscheck <em>Ischeck</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getFunctype <em>Functype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getIsmenu <em>Ismenu</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFunction#getAppFuncgroup <em>AppFuncgroup</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface AppFunction extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.framework.application.AppFunction";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.framework.application", "AppFunction");

	public final static IObjectFactory<AppFunction> FACTORY = new IObjectFactory<AppFunction>() {
		public AppFunction create() {
			return (AppFunction) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getFunccode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getFunccode <em>Funccode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funccode</em>' attribute.
	 * @see #getFunccode()
	 */
	public void setFunccode(String funccode);

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
	public String getFuncname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncname <em>Funcname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcname</em>' attribute.
	 * @see #getFuncname()
	 */
	public void setFuncname(String funcname);

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
	public String getFuncdesc();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncdesc <em>Funcdesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcdesc</em>' attribute.
	 * @see #getFuncdesc()
	 */
	public void setFuncdesc(String funcdesc);

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
	public String getFuncaction();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getFuncaction <em>Funcaction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcaction</em>' attribute.
	 * @see #getFuncaction()
	 */
	public void setFuncaction(String funcaction);

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
	public String getParainfo();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getParainfo <em>Parainfo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parainfo</em>' attribute.
	 * @see #getParainfo()
	 */
	public void setParainfo(String parainfo);

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
	public String getIscheck();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getIscheck <em>Ischeck</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ischeck</em>' attribute.
	 * @see #getIscheck()
	 */
	public void setIscheck(String ischeck);

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
	public String getFunctype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getFunctype <em>Functype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Functype</em>' attribute.
	 * @see #getFunctype()
	 */
	public void setFunctype(String functype);

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
	public String getIsmenu();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getIsmenu <em>Ismenu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ismenu</em>' attribute.
	 * @see #getIsmenu()
	 */
	public void setIsmenu(String ismenu);

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
	public String getApp_id();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getApp_id <em>App_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>App_id</em>' attribute.
	 * @see #getApp_id()
	 */
	public void setApp_id(String app_id);

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
	public String getTenant_id();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getTenant_id <em>Tenant_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant_id</em>' attribute.
	 * @see #getTenant_id()
	 */
	public void setTenant_id(String tenant_id);

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
	public AppFuncgroup getAppFuncgroup();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFunction#getAppFuncgroup <em>AppFuncgroup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppFuncgroup</em>' attribute.
	 * @see #getAppFuncgroup()
	 */
	public void setAppFuncgroup(AppFuncgroup appFuncgroup);


}