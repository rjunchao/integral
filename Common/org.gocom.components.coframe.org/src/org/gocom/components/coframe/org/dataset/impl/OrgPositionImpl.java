/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.org.dataset.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.math.BigDecimal;
import java.util.Date;

import org.gocom.components.coframe.org.dataset.OrgDuty;
import org.gocom.components.coframe.org.dataset.OrgOrganization;
import org.gocom.components.coframe.org.dataset.OrgPosition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPositionid <em>Positionid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPosicode <em>Posicode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPosiname <em>Posiname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPosilevel <em>Posilevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPositionseq <em>Positionseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getPositype <em>Positype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getCreatetime <em>Createtime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getLastupdate <em>Lastupdate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getUpdator <em>Updator</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getStartdate <em>Startdate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getEnddate <em>Enddate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getOrgDuty <em>OrgDuty</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getOrgPosition <em>OrgPosition</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgPositionImpl#getOrgOrganization <em>OrgOrganization</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements OrgPosition;
 */

public class OrgPositionImpl extends ExtendedDataObjectImpl implements OrgPosition {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_POSITIONID = 0;
	public final static int INDEX_POSICODE = 1;
	public final static int INDEX_POSINAME = 2;
	public final static int INDEX_POSILEVEL = 3;
	public final static int INDEX_POSITIONSEQ = 4;
	public final static int INDEX_POSITYPE = 5;
	public final static int INDEX_CREATETIME = 6;
	public final static int INDEX_LASTUPDATE = 7;
	public final static int INDEX_UPDATOR = 8;
	public final static int INDEX_STARTDATE = 9;
	public final static int INDEX_ENDDATE = 10;
	public final static int INDEX_STATUS = 11;
	public final static int INDEX_ISLEAF = 12;
	public final static int INDEX_SUBCOUNT = 13;
	public final static int INDEX_TENANTID = 14;
	public final static int INDEX_APPID = 15;
	public final static int INDEX_ORGDUTY = 16;
	public final static int INDEX_ORGORGANIZATION = 17;
	public final static int INDEX_ORGPOSITION = 18;
	public final static int SDO_PROPERTY_COUNT = 19;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgPositionImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgPositionImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Positionid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Positionid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positionid</em>' attribute.
	 * @see #setPositionid(java.math.BigDecimal)
	 */
	public BigDecimal getPositionid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_POSITIONID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPositionid <em>Positionid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positionid</em>' attribute.
	 * @see #getPositionid()
	 */
	public void setPositionid(BigDecimal positionid) {
		super.setByIndex(INDEX_POSITIONID, positionid);
	}

	/**
	 * Returns the value of the '<em><b>Posicode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posicode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posicode</em>' attribute.
	 * @see #setPosicode(java.lang.String)
	 */
	public String getPosicode() {
		return DataUtil.toString(super.getByIndex(INDEX_POSICODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPosicode <em>Posicode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posicode</em>' attribute.
	 * @see #getPosicode()
	 */
	public void setPosicode(String posicode) {
		super.setByIndex(INDEX_POSICODE, posicode);
	}

	/**
	 * Returns the value of the '<em><b>Posiname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posiname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posiname</em>' attribute.
	 * @see #setPosiname(java.lang.String)
	 */
	public String getPosiname() {
		return DataUtil.toString(super.getByIndex(INDEX_POSINAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPosiname <em>Posiname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posiname</em>' attribute.
	 * @see #getPosiname()
	 */
	public void setPosiname(String posiname) {
		super.setByIndex(INDEX_POSINAME, posiname);
	}

	/**
	 * Returns the value of the '<em><b>Posilevel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Posilevel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Posilevel</em>' attribute.
	 * @see #setPosilevel(java.math.BigDecimal)
	 */
	public BigDecimal getPosilevel() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_POSILEVEL, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPosilevel <em>Posilevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posilevel</em>' attribute.
	 * @see #getPosilevel()
	 */
	public void setPosilevel(BigDecimal posilevel) {
		super.setByIndex(INDEX_POSILEVEL, posilevel);
	}

	/**
	 * Returns the value of the '<em><b>Positionseq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Positionseq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positionseq</em>' attribute.
	 * @see #setPositionseq(java.lang.String)
	 */
	public String getPositionseq() {
		return DataUtil.toString(super.getByIndex(INDEX_POSITIONSEQ, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPositionseq <em>Positionseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positionseq</em>' attribute.
	 * @see #getPositionseq()
	 */
	public void setPositionseq(String positionseq) {
		super.setByIndex(INDEX_POSITIONSEQ, positionseq);
	}

	/**
	 * Returns the value of the '<em><b>Positype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Positype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positype</em>' attribute.
	 * @see #setPositype(java.lang.String)
	 */
	public String getPositype() {
		return DataUtil.toString(super.getByIndex(INDEX_POSITYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPositype <em>Positype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positype</em>' attribute.
	 * @see #getPositype()
	 */
	public void setPositype(String positype) {
		super.setByIndex(INDEX_POSITYPE, positype);
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

	/**
	 * Returns the value of the '<em><b>Lastupdate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lastupdate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lastupdate</em>' attribute.
	 * @see #setLastupdate(java.util.Date)
	 */
	public Date getLastupdate() {
		return DataUtil.toDate(super.getByIndex(INDEX_LASTUPDATE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getLastupdate <em>Lastupdate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lastupdate</em>' attribute.
	 * @see #getLastupdate()
	 */
	public void setLastupdate(Date lastupdate) {
		super.setByIndex(INDEX_LASTUPDATE, lastupdate);
	}

	/**
	 * Returns the value of the '<em><b>Updator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updator</em>' attribute.
	 * @see #setUpdator(java.math.BigDecimal)
	 */
	public BigDecimal getUpdator() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_UPDATOR, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUpdator <em>Updator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updator</em>' attribute.
	 * @see #getUpdator()
	 */
	public void setUpdator(BigDecimal updator) {
		super.setByIndex(INDEX_UPDATOR, updator);
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
	 * Returns the value of the '<em><b>Tenantid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tenantid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tenantid</em>' attribute.
	 * @see #setTenantid(java.lang.String)
	 */
	public String getTenantid() {
		return DataUtil.toString(super.getByIndex(INDEX_TENANTID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getTenantid <em>Tenantid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenantid</em>' attribute.
	 * @see #getTenantid()
	 */
	public void setTenantid(String tenantid) {
		super.setByIndex(INDEX_TENANTID, tenantid);
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
	 * @see #setAppid(java.lang.String)
	 */
	public String getAppid() {
		return DataUtil.toString(super.getByIndex(INDEX_APPID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(String appid) {
		super.setByIndex(INDEX_APPID, appid);
	}

	/**
	 * Returns the value of the '<em><b>OrgDuty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>OrgDuty</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>OrgDuty</em>' attribute.
	 * @see #setOrgDuty(org.gocom.components.coframe.org.dataset.OrgDuty)
	 */
	public OrgDuty getOrgDuty() {
		return (OrgDuty) DataUtil.toDataObject(super.getByIndex(INDEX_ORGDUTY, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgDuty <em>OrgDuty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgDuty</em>' attribute.
	 * @see #getOrgDuty()
	 */
	public void setOrgDuty(OrgDuty orgDuty) {
		super.setByIndex(INDEX_ORGDUTY, orgDuty);
	}

	/**
	 * Returns the value of the '<em><b>OrgPosition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>OrgPosition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>OrgPosition</em>' attribute.
	 * @see #setOrgPosition(org.gocom.components.coframe.org.dataset.OrgPosition)
	 */
	public OrgPosition getOrgPosition() {
		return (OrgPosition) DataUtil.toDataObject(super.getByIndex(INDEX_ORGPOSITION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgPosition <em>OrgPosition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgPosition</em>' attribute.
	 * @see #getOrgPosition()
	 */
	public void setOrgPosition(OrgPosition orgPosition) {
		super.setByIndex(INDEX_ORGPOSITION, orgPosition);
	}

	/**
	 * Returns the value of the '<em><b>OrgOrganization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>OrgOrganization</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>OrgOrganization</em>' attribute.
	 * @see #setOrgOrganization(org.gocom.components.coframe.org.dataset.OrgOrganization)
	 */
	public OrgOrganization getOrgOrganization() {
		return (OrgOrganization) DataUtil.toDataObject(super.getByIndex(INDEX_ORGORGANIZATION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgOrganization <em>OrgOrganization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgOrganization</em>' attribute.
	 * @see #getOrgOrganization()
	 */
	public void setOrgOrganization(OrgOrganization orgOrganization) {
		super.setByIndex(INDEX_ORGORGANIZATION, orgOrganization);
	}


}