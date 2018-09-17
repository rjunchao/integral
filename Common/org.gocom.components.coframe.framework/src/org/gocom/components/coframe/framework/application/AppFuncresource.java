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

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getResid <em>Resid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getRestype <em>Restype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getRespath <em>Respath</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getCompackname <em>Compackname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getResname <em>Resname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncresource#getAppFunction <em>AppFunction</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface AppFuncresource extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.framework.application.AppFuncresource";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.framework.application", "AppFuncresource");

	public final static IObjectFactory<AppFuncresource> FACTORY = new IObjectFactory<AppFuncresource>() {
		public AppFuncresource create() {
			return (AppFuncresource) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public BigDecimal getResid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getResid <em>Resid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resid</em>' attribute.
	 * @see #getResid()
	 */
	public void setResid(BigDecimal resid);

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
	public String getRestype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getRestype <em>Restype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Restype</em>' attribute.
	 * @see #getRestype()
	 */
	public void setRestype(String restype);

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
	public String getRespath();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getRespath <em>Respath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Respath</em>' attribute.
	 * @see #getRespath()
	 */
	public void setRespath(String respath);

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
	public String getCompackname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getCompackname <em>Compackname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compackname</em>' attribute.
	 * @see #getCompackname()
	 */
	public void setCompackname(String compackname);

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
	public String getResname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getResname <em>Resname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resname</em>' attribute.
	 * @see #getResname()
	 */
	public void setResname(String resname);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getApp_id <em>App_id</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getTenant_id <em>Tenant_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant_id</em>' attribute.
	 * @see #getTenant_id()
	 */
	public void setTenant_id(String tenant_id);

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
	public AppFunction getAppFunction();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncresource#getAppFunction <em>AppFunction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppFunction</em>' attribute.
	 * @see #getAppFunction()
	 */
	public void setAppFunction(AppFunction appFunction);


}