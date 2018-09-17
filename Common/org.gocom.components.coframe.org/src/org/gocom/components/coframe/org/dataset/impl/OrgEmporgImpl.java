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

import org.gocom.components.coframe.org.dataset.OrgEmployee;
import org.gocom.components.coframe.org.dataset.OrgEmporg;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgEmporgImpl#getOrgid <em>Orgid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgEmporgImpl#getIsmain <em>Ismain</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgEmporgImpl#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgEmporgImpl#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.impl.OrgEmporgImpl#getOrgEmployee <em>OrgEmployee</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements OrgEmporg;
 */

public class OrgEmporgImpl extends ExtendedDataObjectImpl implements OrgEmporg {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_ORGID = 0;
	public final static int INDEX_ISMAIN = 1;
	public final static int INDEX_TENANTID = 2;
	public final static int INDEX_APPID = 3;
	public final static int INDEX_ORGEMPLOYEE = 4;
	public final static int SDO_PROPERTY_COUNT = 5;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgEmporgImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public OrgEmporgImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Orgid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orgid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orgid</em>' attribute.
	 * @see #setOrgid(java.math.BigDecimal)
	 */
	public BigDecimal getOrgid() {
		return DataUtil.toBigDecimal(super.getByIndex(INDEX_ORGID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgid <em>Orgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgid</em>' attribute.
	 * @see #getOrgid()
	 */
	public void setOrgid(BigDecimal orgid) {
		super.setByIndex(INDEX_ORGID, orgid);
	}

	/**
	 * Returns the value of the '<em><b>Ismain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ismain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ismain</em>' attribute.
	 * @see #setIsmain(java.lang.String)
	 */
	public String getIsmain() {
		return DataUtil.toString(super.getByIndex(INDEX_ISMAIN, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getIsmain <em>Ismain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ismain</em>' attribute.
	 * @see #getIsmain()
	 */
	public void setIsmain(String ismain) {
		super.setByIndex(INDEX_ISMAIN, ismain);
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
	 * Returns the value of the '<em><b>OrgEmployee</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>OrgEmployee</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>OrgEmployee</em>' attribute.
	 * @see #setOrgEmployee(org.gocom.components.coframe.org.dataset.OrgEmployee)
	 */
	public OrgEmployee getOrgEmployee() {
		return (OrgEmployee) DataUtil.toDataObject(super.getByIndex(INDEX_ORGEMPLOYEE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgEmployee <em>OrgEmployee</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgEmployee</em>' attribute.
	 * @see #getOrgEmployee()
	 */
	public void setOrgEmployee(OrgEmployee orgEmployee) {
		super.setByIndex(INDEX_ORGEMPLOYEE, orgEmployee);
	}


}