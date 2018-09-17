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
import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getAppcode <em>Appcode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getAppname <em>Appname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getApptype <em>Apptype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getIsopen <em>Isopen</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getOpendate <em>Opendate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getUrl <em>Url</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getAppdesc <em>Appdesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getMaintenance <em>Maintenance</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getManarole <em>Manarole</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getDemo <em>Demo</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getIniwp <em>Iniwp</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getIntaskcenter <em>Intaskcenter</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getIpaddr <em>Ipaddr</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getIpport <em>Ipport</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.AppApplication#getProtocoltype <em>Protocoltype</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface AppApplication extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.framework.application.AppApplication";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.framework.application", "AppApplication");

	public final static IObjectFactory<AppApplication> FACTORY = new IObjectFactory<AppApplication>() {
		public AppApplication create() {
			return (AppApplication) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(BigDecimal appid);

	/**
	 * Returns the value of the '<em><b>Appcode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Appcode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appcode</em>' attribute.
	 * @see #setAppcode(java.lang.String)
	 */
	public String getAppcode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getAppcode <em>Appcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appcode</em>' attribute.
	 * @see #getAppcode()
	 */
	public void setAppcode(String appcode);

	/**
	 * Returns the value of the '<em><b>Appname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Appname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appname</em>' attribute.
	 * @see #setAppname(java.lang.String)
	 */
	public String getAppname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getAppname <em>Appname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appname</em>' attribute.
	 * @see #getAppname()
	 */
	public void setAppname(String appname);

	/**
	 * Returns the value of the '<em><b>Apptype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Apptype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Apptype</em>' attribute.
	 * @see #setApptype(java.lang.String)
	 */
	public String getApptype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getApptype <em>Apptype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Apptype</em>' attribute.
	 * @see #getApptype()
	 */
	public void setApptype(String apptype);

	/**
	 * Returns the value of the '<em><b>Isopen</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Isopen</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Isopen</em>' attribute.
	 * @see #setIsopen(java.lang.String)
	 */
	public String getIsopen();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getIsopen <em>Isopen</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isopen</em>' attribute.
	 * @see #getIsopen()
	 */
	public void setIsopen(String isopen);

	/**
	 * Returns the value of the '<em><b>Opendate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Opendate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Opendate</em>' attribute.
	 * @see #setOpendate(java.util.Date)
	 */
	public Date getOpendate();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getOpendate <em>Opendate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opendate</em>' attribute.
	 * @see #getOpendate()
	 */
	public void setOpendate(Date opendate);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(java.lang.String)
	 */
	public String getUrl();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 */
	public void setUrl(String url);

	/**
	 * Returns the value of the '<em><b>Appdesc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Appdesc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Appdesc</em>' attribute.
	 * @see #setAppdesc(java.lang.String)
	 */
	public String getAppdesc();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getAppdesc <em>Appdesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appdesc</em>' attribute.
	 * @see #getAppdesc()
	 */
	public void setAppdesc(String appdesc);

	/**
	 * Returns the value of the '<em><b>Maintenance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maintenance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maintenance</em>' attribute.
	 * @see #setMaintenance(java.math.BigDecimal)
	 */
	public BigDecimal getMaintenance();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getMaintenance <em>Maintenance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maintenance</em>' attribute.
	 * @see #getMaintenance()
	 */
	public void setMaintenance(BigDecimal maintenance);

	/**
	 * Returns the value of the '<em><b>Manarole</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manarole</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manarole</em>' attribute.
	 * @see #setManarole(java.lang.String)
	 */
	public String getManarole();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getManarole <em>Manarole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manarole</em>' attribute.
	 * @see #getManarole()
	 */
	public void setManarole(String manarole);

	/**
	 * Returns the value of the '<em><b>Demo</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Demo</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Demo</em>' attribute.
	 * @see #setDemo(java.lang.String)
	 */
	public String getDemo();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getDemo <em>Demo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Demo</em>' attribute.
	 * @see #getDemo()
	 */
	public void setDemo(String demo);

	/**
	 * Returns the value of the '<em><b>Iniwp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iniwp</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iniwp</em>' attribute.
	 * @see #setIniwp(java.lang.String)
	 */
	public String getIniwp();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getIniwp <em>Iniwp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iniwp</em>' attribute.
	 * @see #getIniwp()
	 */
	public void setIniwp(String iniwp);

	/**
	 * Returns the value of the '<em><b>Intaskcenter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intaskcenter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intaskcenter</em>' attribute.
	 * @see #setIntaskcenter(java.lang.String)
	 */
	public String getIntaskcenter();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getIntaskcenter <em>Intaskcenter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intaskcenter</em>' attribute.
	 * @see #getIntaskcenter()
	 */
	public void setIntaskcenter(String intaskcenter);

	/**
	 * Returns the value of the '<em><b>Ipaddr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ipaddr</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ipaddr</em>' attribute.
	 * @see #setIpaddr(java.lang.String)
	 */
	public String getIpaddr();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getIpaddr <em>Ipaddr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipaddr</em>' attribute.
	 * @see #getIpaddr()
	 */
	public void setIpaddr(String ipaddr);

	/**
	 * Returns the value of the '<em><b>Ipport</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ipport</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ipport</em>' attribute.
	 * @see #setIpport(java.lang.String)
	 */
	public String getIpport();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getIpport <em>Ipport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipport</em>' attribute.
	 * @see #getIpport()
	 */
	public void setIpport(String ipport);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getApp_id <em>App_id</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getTenant_id <em>Tenant_id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenant_id</em>' attribute.
	 * @see #getTenant_id()
	 */
	public void setTenant_id(String tenant_id);

	/**
	 * Returns the value of the '<em><b>Protocoltype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocoltype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocoltype</em>' attribute.
	 * @see #setProtocoltype(java.lang.String)
	 */
	public String getProtocoltype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.framework.application.AppApplication#getProtocoltype <em>Protocoltype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocoltype</em>' attribute.
	 * @see #getProtocoltype()
	 */
	public void setProtocoltype(String protocoltype);


}