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

import org.gocom.components.coframe.org.dataset.OrgDuty;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutyid <em>Dutyid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutycode <em>Dutycode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutyname <em>Dutyname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getParentduty <em>Parentduty</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutylevel <em>Dutylevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutyseq <em>Dutyseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getDutytype <em>Dutytype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getRemark <em>Remark</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgDutyImpl#getAppid <em>Appid</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements OrgDuty;
 */

public class OrgDutyImpl extends ExtendedDataObjectImpl implements OrgDuty {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_DUTYID = 0;
	public final static int INDEX_DUTYCODE = 1;
	public final static int INDEX_DUTYNAME = 2;
	public final static int INDEX_PARENTDUTY = 3;
	public final static int INDEX_DUTYLEVEL = 4;
	public final static int INDEX_DUTYSEQ = 5;
	public final static int INDEX_DUTYTYPE = 6;
	public final static int INDEX_ISLEAF = 7;
	public final static int INDEX_SUBCOUNT = 8;
	public final static int INDEX_REMARK = 9;
	public final static int INDEX_TENANTID = 10;
	public final static int INDEX_APPID = 11;
	public final static int SDO_PROPERTY_COUNT = 12;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgDutyImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgDutyImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Dutyid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutyid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutyid</em>' attribute.
	 * @see #setDutyid(java.math.BigDecimal)
	 */
	public BigDecimal getDutyid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_DUTYID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutyid <em>Dutyid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyid</em>' attribute.
	 * @see #getDutyid()
	 */
	public void setDutyid(BigDecimal dutyid) {
		super.setByIndex(INDEX_DUTYID, dutyid);
	}

	/**
	 * Returns the value of the '<em><b>Dutycode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutycode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutycode</em>' attribute.
	 * @see #setDutycode(java.lang.String)
	 */
	public String getDutycode() {
		return DataUtil.toString(super.getByIndex(INDEX_DUTYCODE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutycode <em>Dutycode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutycode</em>' attribute.
	 * @see #getDutycode()
	 */
	public void setDutycode(String dutycode) {
		super.setByIndex(INDEX_DUTYCODE, dutycode);
	}

	/**
	 * Returns the value of the '<em><b>Dutyname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutyname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutyname</em>' attribute.
	 * @see #setDutyname(java.lang.String)
	 */
	public String getDutyname() {
		return DataUtil.toString(super.getByIndex(INDEX_DUTYNAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutyname <em>Dutyname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyname</em>' attribute.
	 * @see #getDutyname()
	 */
	public void setDutyname(String dutyname) {
		super.setByIndex(INDEX_DUTYNAME, dutyname);
	}

	/**
	 * Returns the value of the '<em><b>Parentduty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parentduty</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parentduty</em>' attribute.
	 * @see #setParentduty(java.math.BigDecimal)
	 */
	public BigDecimal getParentduty() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_PARENTDUTY, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getParentduty <em>Parentduty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parentduty</em>' attribute.
	 * @see #getParentduty()
	 */
	public void setParentduty(BigDecimal parentduty) {
		super.setByIndex(INDEX_PARENTDUTY, parentduty);
	}

	/**
	 * Returns the value of the '<em><b>Dutylevel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutylevel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutylevel</em>' attribute.
	 * @see #setDutylevel(int)
	 */
	public int getDutylevel() {
		return DataUtil.toInt(super.getByIndex(INDEX_DUTYLEVEL, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutylevel <em>Dutylevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutylevel</em>' attribute.
	 * @see #getDutylevel()
	 */
	public void setDutylevel(int dutylevel) {
		super.setByIndex(INDEX_DUTYLEVEL, dutylevel);
	}

	/**
	 * Returns the value of the '<em><b>Dutyseq</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutyseq</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutyseq</em>' attribute.
	 * @see #setDutyseq(java.lang.String)
	 */
	public String getDutyseq() {
		return DataUtil.toString(super.getByIndex(INDEX_DUTYSEQ, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutyseq <em>Dutyseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyseq</em>' attribute.
	 * @see #getDutyseq()
	 */
	public void setDutyseq(String dutyseq) {
		super.setByIndex(INDEX_DUTYSEQ, dutyseq);
	}

	/**
	 * Returns the value of the '<em><b>Dutytype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dutytype</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dutytype</em>' attribute.
	 * @see #setDutytype(java.lang.String)
	 */
	public String getDutytype() {
		return DataUtil.toString(super.getByIndex(INDEX_DUTYTYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getDutytype <em>Dutytype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutytype</em>' attribute.
	 * @see #getDutytype()
	 */
	public void setDutytype(String dutytype) {
		super.setByIndex(INDEX_DUTYTYPE, dutytype);
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
	 * Returns the value of the '<em><b>Remark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remark</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remark</em>' attribute.
	 * @see #setRemark(java.lang.String)
	 */
	public String getRemark() {
		return DataUtil.toString(super.getByIndex(INDEX_REMARK, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRemark <em>Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remark</em>' attribute.
	 * @see #getRemark()
	 */
	public void setRemark(String remark) {
		super.setByIndex(INDEX_REMARK, remark);
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


}