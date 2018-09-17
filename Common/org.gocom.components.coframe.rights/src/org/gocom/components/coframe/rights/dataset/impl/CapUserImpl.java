/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.rights.dataset.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.math.BigDecimal;
import java.util.Date;

import org.gocom.components.coframe.rights.dataset.CapUser;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getOperatorId <em>OperatorId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getUserId <em>UserId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getInvaldate <em>Invaldate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getUserName <em>UserName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getAuthmode <em>Authmode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getUnlocktime <em>Unlocktime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getMenutype <em>Menutype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getLastlogin <em>Lastlogin</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getErrcount <em>Errcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getStartdate <em>Startdate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getEnddate <em>Enddate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getValidtime <em>Validtime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getMaccode <em>Maccode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getIpaddress <em>Ipaddress</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.rights.dataset.impl.CapUserImpl#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements CapUser;
 */

public class CapUserImpl extends ExtendedDataObjectImpl implements CapUser {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_OPERATORID = 0;
	public final static int INDEX_TENANTID = 1;
	public final static int INDEX_USERID = 2;
	public final static int INDEX_PASSWORD = 3;
	public final static int INDEX_INVALDATE = 4;
	public final static int INDEX_USERNAME = 5;
	public final static int INDEX_AUTHMODE = 6;
	public final static int INDEX_STATUS = 7;
	public final static int INDEX_UNLOCKTIME = 8;
	public final static int INDEX_MENUTYPE = 9;
	public final static int INDEX_LASTLOGIN = 10;
	public final static int INDEX_ERRCOUNT = 11;
	public final static int INDEX_STARTDATE = 12;
	public final static int INDEX_ENDDATE = 13;
	public final static int INDEX_VALIDTIME = 14;
	public final static int INDEX_MACCODE = 15;
	public final static int INDEX_IPADDRESS = 16;
	public final static int INDEX_EMAIL = 17;
	public final static int INDEX_CREATEUSER = 18;
	public final static int INDEX_CREATETIME = 19;
	public final static int SDO_PROPERTY_COUNT = 20;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapUserImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapUserImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
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
	 * @see #setOperatorId(java.math.BigDecimal)
	 */
	public BigDecimal getOperatorId() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_OPERATORID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOperatorId <em>OperatorId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OperatorId</em>' attribute.
	 * @see #getOperatorId()
	 */
	public void setOperatorId(BigDecimal operatorId) {
		super.setByIndex(INDEX_OPERATORID, operatorId);
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
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(java.lang.String)
	 */
	public String getPassword() {
		return DataUtil.toString(super.getByIndex(INDEX_PASSWORD, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 */
	public void setPassword(String password) {
		super.setByIndex(INDEX_PASSWORD, password);
	}

	/**
	 * Returns the value of the '<em><b>Invaldate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invaldate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invaldate</em>' attribute.
	 * @see #setInvaldate(java.util.Date)
	 */
	public Date getInvaldate() {
		return DataUtil.toDate(super.getByIndex(INDEX_INVALDATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getInvaldate <em>Invaldate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invaldate</em>' attribute.
	 * @see #getInvaldate()
	 */
	public void setInvaldate(Date invaldate) {
		super.setByIndex(INDEX_INVALDATE, invaldate);
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
	 * Returns the value of the '<em><b>Authmode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Authmode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Authmode</em>' attribute.
	 * @see #setAuthmode(java.lang.String)
	 */
	public String getAuthmode() {
		return DataUtil.toString(super.getByIndex(INDEX_AUTHMODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAuthmode <em>Authmode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Authmode</em>' attribute.
	 * @see #getAuthmode()
	 */
	public void setAuthmode(String authmode) {
		super.setByIndex(INDEX_AUTHMODE, authmode);
	}

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(java.lang.String)
	 */
	public String getStatus() {
		return DataUtil.toString(super.getByIndex(INDEX_STATUS, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 */
	public void setStatus(String status) {
		super.setByIndex(INDEX_STATUS, status);
	}

	/**
	 * Returns the value of the '<em><b>Unlocktime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unlocktime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unlocktime</em>' attribute.
	 * @see #setUnlocktime(java.util.Date)
	 */
	public Date getUnlocktime() {
		return DataUtil.toDate(super.getByIndex(INDEX_UNLOCKTIME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUnlocktime <em>Unlocktime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unlocktime</em>' attribute.
	 * @see #getUnlocktime()
	 */
	public void setUnlocktime(Date unlocktime) {
		super.setByIndex(INDEX_UNLOCKTIME, unlocktime);
	}

	/**
	 * Returns the value of the '<em><b>Menutype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menutype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menutype</em>' attribute.
	 * @see #setMenutype(java.lang.String)
	 */
	public String getMenutype() {
		return DataUtil.toString(super.getByIndex(INDEX_MENUTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getMenutype <em>Menutype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Menutype</em>' attribute.
	 * @see #getMenutype()
	 */
	public void setMenutype(String menutype) {
		super.setByIndex(INDEX_MENUTYPE, menutype);
	}

	/**
	 * Returns the value of the '<em><b>Lastlogin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lastlogin</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lastlogin</em>' attribute.
	 * @see #setLastlogin(java.util.Date)
	 */
	public Date getLastlogin() {
		return DataUtil.toDate(super.getByIndex(INDEX_LASTLOGIN, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getLastlogin <em>Lastlogin</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lastlogin</em>' attribute.
	 * @see #getLastlogin()
	 */
	public void setLastlogin(Date lastlogin) {
		super.setByIndex(INDEX_LASTLOGIN, lastlogin);
	}

	/**
	 * Returns the value of the '<em><b>Errcount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Errcount</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Errcount</em>' attribute.
	 * @see #setErrcount(java.math.BigDecimal)
	 */
	public BigDecimal getErrcount() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_ERRCOUNT, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getErrcount <em>Errcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Errcount</em>' attribute.
	 * @see #getErrcount()
	 */
	public void setErrcount(BigDecimal errcount) {
		super.setByIndex(INDEX_ERRCOUNT, errcount);
	}

	/**
	 * Returns the value of the '<em><b>Startdate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Startdate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Startdate</em>' attribute.
	 * @see #setStartdate(java.util.Date)
	 */
	public Date getStartdate() {
		return DataUtil.toDate(super.getByIndex(INDEX_STARTDATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getStartdate <em>Startdate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Startdate</em>' attribute.
	 * @see #getStartdate()
	 */
	public void setStartdate(Date startdate) {
		super.setByIndex(INDEX_STARTDATE, startdate);
	}

	/**
	 * Returns the value of the '<em><b>Enddate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enddate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enddate</em>' attribute.
	 * @see #setEnddate(java.util.Date)
	 */
	public Date getEnddate() {
		return DataUtil.toDate(super.getByIndex(INDEX_ENDDATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getEnddate <em>Enddate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enddate</em>' attribute.
	 * @see #getEnddate()
	 */
	public void setEnddate(Date enddate) {
		super.setByIndex(INDEX_ENDDATE, enddate);
	}

	/**
	 * Returns the value of the '<em><b>Validtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validtime</em>' attribute.
	 * @see #setValidtime(java.lang.String)
	 */
	public String getValidtime() {
		return DataUtil.toString(super.getByIndex(INDEX_VALIDTIME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getValidtime <em>Validtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validtime</em>' attribute.
	 * @see #getValidtime()
	 */
	public void setValidtime(String validtime) {
		super.setByIndex(INDEX_VALIDTIME, validtime);
	}

	/**
	 * Returns the value of the '<em><b>Maccode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maccode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maccode</em>' attribute.
	 * @see #setMaccode(java.lang.String)
	 */
	public String getMaccode() {
		return DataUtil.toString(super.getByIndex(INDEX_MACCODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getMaccode <em>Maccode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maccode</em>' attribute.
	 * @see #getMaccode()
	 */
	public void setMaccode(String maccode) {
		super.setByIndex(INDEX_MACCODE, maccode);
	}

	/**
	 * Returns the value of the '<em><b>Ipaddress</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ipaddress</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ipaddress</em>' attribute.
	 * @see #setIpaddress(java.lang.String)
	 */
	public String getIpaddress() {
		return DataUtil.toString(super.getByIndex(INDEX_IPADDRESS, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIpaddress <em>Ipaddress</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipaddress</em>' attribute.
	 * @see #getIpaddress()
	 */
	public void setIpaddress(String ipaddress) {
		super.setByIndex(INDEX_IPADDRESS, ipaddress);
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
	 * Returns the value of the '<em><b>Createuser</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Createuser</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Createuser</em>' attribute.
	 * @see #setCreateuser(java.lang.String)
	 */
	public String getCreateuser() {
		return DataUtil.toString(super.getByIndex(INDEX_CREATEUSER, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCreateuser <em>Createuser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createuser</em>' attribute.
	 * @see #getCreateuser()
	 */
	public void setCreateuser(String createuser) {
		super.setByIndex(INDEX_CREATEUSER, createuser);
	}

	/**
	 * Returns the value of the '<em><b>Createtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Createtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Createtime</em>' attribute.
	 * @see #setCreatetime(java.util.Date)
	 */
	public Date getCreatetime() {
		return DataUtil.toDate(super.getByIndex(INDEX_CREATETIME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime) {
		super.setByIndex(INDEX_CREATETIME, createtime);
	}


}