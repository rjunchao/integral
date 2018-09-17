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
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuid <em>Menuid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuname <em>Menuname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenulabel <em>Menulabel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenucode <em>Menucode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuaction <em>Menuaction</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getUientry <em>Uientry</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenulevel <em>Menulevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getRootid <em>Rootid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getDisplayorder <em>Displayorder</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getImagepath <em>Imagepath</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getExpandpath <em>Expandpath</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuseq <em>Menuseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getOpenmode <em>Openmode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getFunccode <em>Funccode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppMenu#getAppMenu <em>AppMenu</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface AppMenu extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.framework.application.AppMenu";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.framework.application", "AppMenu");

	public final static IObjectFactory<AppMenu> FACTORY = new IObjectFactory<AppMenu>() {
		public AppMenu create() {
			return (AppMenu) DataFactory.INSTANCE.create(TYPE);
		}
	};

	/**
	 * Returns the value of the '<em><b>Menuid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menuid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menuid</em>' attribute.
	 * @see #setMenuid(java.lang.String)
	 */
	public String getMenuid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuid <em>Menuid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menuid</em>' attribute.
	 * @see #getMenuid()
	 */
	public void setMenuid(String menuid);

	/**
	 * Returns the value of the '<em><b>Menuname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menuname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menuname</em>' attribute.
	 * @see #setMenuname(java.lang.String)
	 */
	public String getMenuname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuname <em>Menuname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menuname</em>' attribute.
	 * @see #getMenuname()
	 */
	public void setMenuname(String menuname);

	/**
	 * Returns the value of the '<em><b>Menulabel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menulabel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menulabel</em>' attribute.
	 * @see #setMenulabel(java.lang.String)
	 */
	public String getMenulabel();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenulabel <em>Menulabel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menulabel</em>' attribute.
	 * @see #getMenulabel()
	 */
	public void setMenulabel(String menulabel);

	/**
	 * Returns the value of the '<em><b>Menucode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menucode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menucode</em>' attribute.
	 * @see #setMenucode(java.lang.String)
	 */
	public String getMenucode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenucode <em>Menucode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menucode</em>' attribute.
	 * @see #getMenucode()
	 */
	public void setMenucode(String menucode);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getIsleaf <em>Isleaf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isleaf</em>' attribute.
	 * @see #getIsleaf()
	 */
	public void setIsleaf(String isleaf);

	/**
	 * Returns the value of the '<em><b>Menuaction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menuaction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menuaction</em>' attribute.
	 * @see #setMenuaction(java.lang.String)
	 */
	public String getMenuaction();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuaction <em>Menuaction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menuaction</em>' attribute.
	 * @see #getMenuaction()
	 */
	public void setMenuaction(String menuaction);

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' attribute.
	 * @see #setParameter(java.lang.String)
	 */
	public String getParameter();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getParameter <em>Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter</em>' attribute.
	 * @see #getParameter()
	 */
	public void setParameter(String parameter);

	/**
	 * Returns the value of the '<em><b>Uientry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uientry</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uientry</em>' attribute.
	 * @see #setUientry(java.lang.String)
	 */
	public String getUientry();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getUientry <em>Uientry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uientry</em>' attribute.
	 * @see #getUientry()
	 */
	public void setUientry(String uientry);

	/**
	 * Returns the value of the '<em><b>Menulevel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menulevel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menulevel</em>' attribute.
	 * @see #setMenulevel(short)
	 */
	public short getMenulevel();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenulevel <em>Menulevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menulevel</em>' attribute.
	 * @see #getMenulevel()
	 */
	public void setMenulevel(short menulevel);

	/**
	 * Returns the value of the '<em><b>Rootid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rootid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rootid</em>' attribute.
	 * @see #setRootid(java.lang.String)
	 */
	public String getRootid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getRootid <em>Rootid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rootid</em>' attribute.
	 * @see #getRootid()
	 */
	public void setRootid(String rootid);

	/**
	 * Returns the value of the '<em><b>Displayorder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Displayorder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Displayorder</em>' attribute.
	 * @see #setDisplayorder(short)
	 */
	public short getDisplayorder();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getDisplayorder <em>Displayorder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Displayorder</em>' attribute.
	 * @see #getDisplayorder()
	 */
	public void setDisplayorder(short displayorder);

	/**
	 * Returns the value of the '<em><b>Imagepath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imagepath</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imagepath</em>' attribute.
	 * @see #setImagepath(java.lang.String)
	 */
	public String getImagepath();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getImagepath <em>Imagepath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Imagepath</em>' attribute.
	 * @see #getImagepath()
	 */
	public void setImagepath(String imagepath);

	/**
	 * Returns the value of the '<em><b>Expandpath</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expandpath</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expandpath</em>' attribute.
	 * @see #setExpandpath(java.lang.String)
	 */
	public String getExpandpath();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getExpandpath <em>Expandpath</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expandpath</em>' attribute.
	 * @see #getExpandpath()
	 */
	public void setExpandpath(String expandpath);

	/**
	 * Returns the value of the '<em><b>Menuseq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menuseq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menuseq</em>' attribute.
	 * @see #setMenuseq(java.lang.String)
	 */
	public String getMenuseq();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getMenuseq <em>Menuseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menuseq</em>' attribute.
	 * @see #getMenuseq()
	 */
	public void setMenuseq(String menuseq);

	/**
	 * Returns the value of the '<em><b>Openmode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Openmode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Openmode</em>' attribute.
	 * @see #setOpenmode(java.lang.String)
	 */
	public String getOpenmode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getOpenmode <em>Openmode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Openmode</em>' attribute.
	 * @see #getOpenmode()
	 */
	public void setOpenmode(String openmode);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getSubcount <em>Subcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subcount</em>' attribute.
	 * @see #getSubcount()
	 */
	public void setSubcount(BigDecimal subcount);

	/**
	 * Returns the value of the '<em><b>Appid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Appid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appid</em>' attribute.
	 * @see #setAppid(java.math.BigDecimal)
	 */
	public BigDecimal getAppid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(BigDecimal appid);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getFunccode <em>Funccode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Funccode</em>' attribute.
	 * @see #getFunccode()
	 */
	public void setFunccode(String funccode);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getApp_id <em>App_id</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getTenant_id <em>Tenant_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant_id</em>' attribute.
	 * @see #getTenant_id()
	 */
	public void setTenant_id(String tenant_id);

	/**
	 * Returns the value of the '<em><b>AppMenu</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>AppMenu</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>AppMenu</em>' attribute.
	 * @see #setAppMenu(org.gocom.components.coframe.framework.application.AppMenu)
	 */
	public AppMenu getAppMenu();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppMenu#getAppMenu <em>AppMenu</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>AppMenu</em>' attribute.
	 * @see #getAppMenu()
	 */
	public void setAppMenu(AppMenu appMenu);


}