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

import org.gocom.components.coframe.framework.application.AppApplication;
import org.gocom.components.coframe.framework.application.AppFuncgroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getFuncgroupid <em>Funcgroupid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getFuncgroupname <em>Funcgroupname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getGrouplevel <em>Grouplevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getFuncgroupseq <em>Funcgroupseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getAppFuncgroup <em>AppFuncgroup</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppFuncgroupImpl#getAppApplication <em>AppApplication</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements AppFuncgroup;
 */

public class AppFuncgroupImpl extends ExtendedDataObjectImpl implements AppFuncgroup {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_FUNCGROUPID = 0;
	public final static int INDEX_FUNCGROUPNAME = 1;
	public final static int INDEX_GROUPLEVEL = 2;
	public final static int INDEX_FUNCGROUPSEQ = 3;
	public final static int INDEX_ISLEAF = 4;
	public final static int INDEX_SUBCOUNT = 5;
	public final static int INDEX_APP_ID = 6;
	public final static int INDEX_TENANT_ID = 7;
	public final static int INDEX_APPFUNCGROUP = 8;
	public final static int INDEX_APPAPPLICATION = 9;
	public final static int SDO_PROPERTY_COUNT = 10;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFuncgroupImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppFuncgroupImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Funcgroupid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcgroupid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcgroupid</em>' attribute.
	 * @see #setFuncgroupid(java.math.BigDecimal)
	 */
	public BigDecimal getFuncgroupid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_FUNCGROUPID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncgroupid <em>Funcgroupid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupid</em>' attribute.
	 * @see #getFuncgroupid()
	 */
	public void setFuncgroupid(BigDecimal funcgroupid) {
		super.setByIndex(INDEX_FUNCGROUPID, funcgroupid);
	}

	/**
	 * Returns the value of the '<em><b>Funcgroupname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcgroupname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcgroupname</em>' attribute.
	 * @see #setFuncgroupname(java.lang.String)
	 */
	public String getFuncgroupname() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCGROUPNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncgroupname <em>Funcgroupname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupname</em>' attribute.
	 * @see #getFuncgroupname()
	 */
	public void setFuncgroupname(String funcgroupname) {
		super.setByIndex(INDEX_FUNCGROUPNAME, funcgroupname);
	}

	/**
	 * Returns the value of the '<em><b>Grouplevel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Grouplevel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Grouplevel</em>' attribute.
	 * @see #setGrouplevel(int)
	 */
	public int getGrouplevel() {
		return DataUtil.toInt(super.getByIndex(INDEX_GROUPLEVEL, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getGrouplevel <em>Grouplevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grouplevel</em>' attribute.
	 * @see #getGrouplevel()
	 */
	public void setGrouplevel(int grouplevel) {
		super.setByIndex(INDEX_GROUPLEVEL, grouplevel);
	}

	/**
	 * Returns the value of the '<em><b>Funcgroupseq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Funcgroupseq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Funcgroupseq</em>' attribute.
	 * @see #setFuncgroupseq(java.lang.String)
	 */
	public String getFuncgroupseq() {
		return DataUtil.toString(super.getByIndex(INDEX_FUNCGROUPSEQ, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getFuncgroupseq <em>Funcgroupseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupseq</em>' attribute.
	 * @see #getFuncgroupseq()
	 */
	public void setFuncgroupseq(String funcgroupseq) {
		super.setByIndex(INDEX_FUNCGROUPSEQ, funcgroupseq);
	}

	/**
	 * Returns the value of the '<em><b>Isleaf</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Isleaf</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isleaf</em>' attribute.
	 * @see #setIsleaf(java.lang.String)
	 */
	public String getIsleaf() {
		return DataUtil.toString(super.getByIndex(INDEX_ISLEAF, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIsleaf <em>Isleaf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isleaf</em>' attribute.
	 * @see #getIsleaf()
	 */
	public void setIsleaf(String isleaf) {
		super.setByIndex(INDEX_ISLEAF, isleaf);
	}

	/**
	 * Returns the value of the '<em><b>Subcount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subcount</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subcount</em>' attribute.
	 * @see #setSubcount(java.math.BigDecimal)
	 */
	public BigDecimal getSubcount() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_SUBCOUNT, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getSubcount <em>Subcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subcount</em>' attribute.
	 * @see #getSubcount()
	 */
	public void setSubcount(BigDecimal subcount) {
		super.setByIndex(INDEX_SUBCOUNT, subcount);
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

	/**
	 * Returns the value of the '<em><b>AppApplication</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>AppApplication</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>AppApplication</em>' attribute.
	 * @see #setAppApplication(org.gocom.components.coframe.framework.application.AppApplication)
	 */
	public AppApplication getAppApplication() {
		return (AppApplication) DataUtil.toDataObject(super.getByIndex(INDEX_APPAPPLICATION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppApplication <em>AppApplication</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppApplication</em>' attribute.
	 * @see #getAppApplication()
	 */
	public void setAppApplication(AppApplication appApplication) {
		super.setByIndex(INDEX_APPAPPLICATION, appApplication);
	}


}