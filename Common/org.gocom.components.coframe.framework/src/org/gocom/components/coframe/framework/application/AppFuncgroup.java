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
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupid <em>Funcgroupid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupname <em>Funcgroupname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getGrouplevel <em>Grouplevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupseq <em>Funcgroupseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getAppFuncgroup <em>AppFuncgroup</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getAppApplication <em>AppApplication</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface AppFuncgroup extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.framework.application.AppFuncgroup";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.framework.application", "AppFuncgroup");

	public final static IObjectFactory<AppFuncgroup> FACTORY = new IObjectFactory<AppFuncgroup>() {
		public AppFuncgroup create() {
			return (AppFuncgroup) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public BigDecimal getFuncgroupid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupid <em>Funcgroupid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupid</em>' attribute.
	 * @see #getFuncgroupid()
	 */
	public void setFuncgroupid(BigDecimal funcgroupid);

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
	public String getFuncgroupname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupname <em>Funcgroupname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupname</em>' attribute.
	 * @see #getFuncgroupname()
	 */
	public void setFuncgroupname(String funcgroupname);

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
	public int getGrouplevel();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getGrouplevel <em>Grouplevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Grouplevel</em>' attribute.
	 * @see #getGrouplevel()
	 */
	public void setGrouplevel(int grouplevel);

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
	public String getFuncgroupseq();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getFuncgroupseq <em>Funcgroupseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funcgroupseq</em>' attribute.
	 * @see #getFuncgroupseq()
	 */
	public void setFuncgroupseq(String funcgroupseq);

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
	public String getIsleaf();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getIsleaf <em>Isleaf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isleaf</em>' attribute.
	 * @see #getIsleaf()
	 */
	public void setIsleaf(String isleaf);

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
	public BigDecimal getSubcount();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getSubcount <em>Subcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subcount</em>' attribute.
	 * @see #getSubcount()
	 */
	public void setSubcount(BigDecimal subcount);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getApp_id <em>App_id</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getTenant_id <em>Tenant_id</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getAppFuncgroup <em>AppFuncgroup</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppFuncgroup</em>' attribute.
	 * @see #getAppFuncgroup()
	 */
	public void setAppFuncgroup(AppFuncgroup appFuncgroup);

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
	public AppApplication getAppApplication();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppFuncgroup#getAppApplication <em>AppApplication</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppApplication</em>' attribute.
	 * @see #getAppApplication()
	 */
	public void setAppApplication(AppApplication appApplication);


}