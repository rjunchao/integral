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
import java.util.Date;

import org.gocom.components.coframe.framework.application.AppApplication;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getAppcode <em>Appcode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getAppname <em>Appname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getApptype <em>Apptype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getIsopen <em>Isopen</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getOpendate <em>Opendate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getAppdesc <em>Appdesc</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getMaintenance <em>Maintenance</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getManarole <em>Manarole</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getDemo <em>Demo</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getIniwp <em>Iniwp</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getIntaskcenter <em>Intaskcenter</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getIpaddr <em>Ipaddr</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getIpport <em>Ipport</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getApp_id <em>App_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getTenant_id <em>Tenant_id</em>}</li>
 *   <li>{@link org.gocom.components.coframe.framework.application.impl.AppApplicationImpl#getProtocoltype <em>Protocoltype</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements AppApplication;
 */

public class AppApplicationImpl extends ExtendedDataObjectImpl implements AppApplication {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_APPID = 0;
	public final static int INDEX_APPCODE = 1;
	public final static int INDEX_APPNAME = 2;
	public final static int INDEX_APPTYPE = 3;
	public final static int INDEX_ISOPEN = 4;
	public final static int INDEX_OPENDATE = 5;
	public final static int INDEX_URL = 6;
	public final static int INDEX_APPDESC = 7;
	public final static int INDEX_MAINTENANCE = 8;
	public final static int INDEX_MANAROLE = 9;
	public final static int INDEX_DEMO = 10;
	public final static int INDEX_INIWP = 11;
	public final static int INDEX_INTASKCENTER = 12;
	public final static int INDEX_IPADDR = 13;
	public final static int INDEX_IPPORT = 14;
	public final static int INDEX_APP_ID = 15;
	public final static int INDEX_TENANT_ID = 16;
	public final static int INDEX_PROTOCOLTYPE = 17;
	public final static int SDO_PROPERTY_COUNT = 18;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppApplicationImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public AppApplicationImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

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
	public BigDecimal getAppid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_APPID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(BigDecimal appid) {
		super.setByIndex(INDEX_APPID, appid);
	}

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
	public String getAppcode() {
		return DataUtil.toString(super.getByIndex(INDEX_APPCODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppcode <em>Appcode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appcode</em>' attribute.
	 * @see #getAppcode()
	 */
	public void setAppcode(String appcode) {
		super.setByIndex(INDEX_APPCODE, appcode);
	}

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
	public String getAppname() {
		return DataUtil.toString(super.getByIndex(INDEX_APPNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppname <em>Appname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appname</em>' attribute.
	 * @see #getAppname()
	 */
	public void setAppname(String appname) {
		super.setByIndex(INDEX_APPNAME, appname);
	}

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
	public String getApptype() {
		return DataUtil.toString(super.getByIndex(INDEX_APPTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getApptype <em>Apptype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Apptype</em>' attribute.
	 * @see #getApptype()
	 */
	public void setApptype(String apptype) {
		super.setByIndex(INDEX_APPTYPE, apptype);
	}

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
	public String getIsopen() {
		return DataUtil.toString(super.getByIndex(INDEX_ISOPEN, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIsopen <em>Isopen</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isopen</em>' attribute.
	 * @see #getIsopen()
	 */
	public void setIsopen(String isopen) {
		super.setByIndex(INDEX_ISOPEN, isopen);
	}

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
	public Date getOpendate() {
		return DataUtil.toDate(super.getByIndex(INDEX_OPENDATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOpendate <em>Opendate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Opendate</em>' attribute.
	 * @see #getOpendate()
	 */
	public void setOpendate(Date opendate) {
		super.setByIndex(INDEX_OPENDATE, opendate);
	}

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
	public String getUrl() {
		return DataUtil.toString(super.getByIndex(INDEX_URL, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 */
	public void setUrl(String url) {
		super.setByIndex(INDEX_URL, url);
	}

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
	public String getAppdesc() {
		return DataUtil.toString(super.getByIndex(INDEX_APPDESC, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppdesc <em>Appdesc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appdesc</em>' attribute.
	 * @see #getAppdesc()
	 */
	public void setAppdesc(String appdesc) {
		super.setByIndex(INDEX_APPDESC, appdesc);
	}

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
	public BigDecimal getMaintenance() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_MAINTENANCE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getMaintenance <em>Maintenance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maintenance</em>' attribute.
	 * @see #getMaintenance()
	 */
	public void setMaintenance(BigDecimal maintenance) {
		super.setByIndex(INDEX_MAINTENANCE, maintenance);
	}

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
	public String getManarole() {
		return DataUtil.toString(super.getByIndex(INDEX_MANAROLE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getManarole <em>Manarole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manarole</em>' attribute.
	 * @see #getManarole()
	 */
	public void setManarole(String manarole) {
		super.setByIndex(INDEX_MANAROLE, manarole);
	}

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
	public String getDemo() {
		return DataUtil.toString(super.getByIndex(INDEX_DEMO, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDemo <em>Demo</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Demo</em>' attribute.
	 * @see #getDemo()
	 */
	public void setDemo(String demo) {
		super.setByIndex(INDEX_DEMO, demo);
	}

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
	public String getIniwp() {
		return DataUtil.toString(super.getByIndex(INDEX_INIWP, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIniwp <em>Iniwp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iniwp</em>' attribute.
	 * @see #getIniwp()
	 */
	public void setIniwp(String iniwp) {
		super.setByIndex(INDEX_INIWP, iniwp);
	}

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
	public String getIntaskcenter() {
		return DataUtil.toString(super.getByIndex(INDEX_INTASKCENTER, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIntaskcenter <em>Intaskcenter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intaskcenter</em>' attribute.
	 * @see #getIntaskcenter()
	 */
	public void setIntaskcenter(String intaskcenter) {
		super.setByIndex(INDEX_INTASKCENTER, intaskcenter);
	}

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
	public String getIpaddr() {
		return DataUtil.toString(super.getByIndex(INDEX_IPADDR, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIpaddr <em>Ipaddr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipaddr</em>' attribute.
	 * @see #getIpaddr()
	 */
	public void setIpaddr(String ipaddr) {
		super.setByIndex(INDEX_IPADDR, ipaddr);
	}

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
	public String getIpport() {
		return DataUtil.toString(super.getByIndex(INDEX_IPPORT, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIpport <em>Ipport</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipport</em>' attribute.
	 * @see #getIpport()
	 */
	public void setIpport(String ipport) {
		super.setByIndex(INDEX_IPPORT, ipport);
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
	public String getProtocoltype() {
		return DataUtil.toString(super.getByIndex(INDEX_PROTOCOLTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getProtocoltype <em>Protocoltype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocoltype</em>' attribute.
	 * @see #getProtocoltype()
	 */
	public void setProtocoltype(String protocoltype) {
		super.setByIndex(INDEX_PROTOCOLTYPE, protocoltype);
	}


}