/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package com.vbm.mas.userpermissions.orgdataset.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;
import com.vbm.mas.userpermissions.orgdataset.FcUserPermissions;

import commonj.sdo.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.impl.FcUserPermissionsImpl#getPk <em>Pk</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.impl.FcUserPermissionsImpl#getOrgid <em>Orgid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.impl.FcUserPermissionsImpl#getEmpid <em>Empid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.impl.FcUserPermissionsImpl#getUserid <em>Userid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.impl.FcUserPermissionsImpl#getPorgid <em>Porgid</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements FcUserPermissions;
 */

public class FcUserPermissionsImpl extends ExtendedDataObjectImpl implements FcUserPermissions {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_PK = 0;
	public final static int INDEX_ORGID = 1;
	public final static int INDEX_EMPID = 2;
	public final static int INDEX_USERID = 3;
	public final static int INDEX_PORGID = 4;
	public final static int SDO_PROPERTY_COUNT = 5;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public FcUserPermissionsImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public FcUserPermissionsImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

	/**
	 * Returns the value of the '<em><b>Pk</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pk</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pk</em>' attribute.
	 * @see #setPk(java.lang.String)
	 */
	public String getPk() {
		return DataUtil.toString(super.getByIndex(INDEX_PK, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPk <em>Pk</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pk</em>' attribute.
	 * @see #getPk()
	 */
	public void setPk(String pk) {
		super.setByIndex(INDEX_PK, pk);
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
	 * @see #setOrgid(int)
	 */
	public int getOrgid() {
		return DataUtil.toInt(super.getByIndex(INDEX_ORGID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getOrgid <em>Orgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgid</em>' attribute.
	 * @see #getOrgid()
	 */
	public void setOrgid(int orgid) {
		super.setByIndex(INDEX_ORGID, orgid);
	}

	/**
	 * Returns the value of the '<em><b>Empid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Empid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Empid</em>' attribute.
	 * @see #setEmpid(int)
	 */
	public int getEmpid() {
		return DataUtil.toInt(super.getByIndex(INDEX_EMPID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getEmpid <em>Empid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empid</em>' attribute.
	 * @see #getEmpid()
	 */
	public void setEmpid(int empid) {
		super.setByIndex(INDEX_EMPID, empid);
	}

	/**
	 * Returns the value of the '<em><b>Userid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userid</em>' attribute.
	 * @see #setUserid(java.lang.String)
	 */
	public String getUserid() {
		return DataUtil.toString(super.getByIndex(INDEX_USERID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getUserid <em>Userid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userid</em>' attribute.
	 * @see #getUserid()
	 */
	public void setUserid(String userid) {
		super.setByIndex(INDEX_USERID, userid);
	}

	/**
	 * Returns the value of the '<em><b>Porgid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Porgid</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Porgid</em>' attribute.
	 * @see #setPorgid(int)
	 */
	public int getPorgid() {
		return DataUtil.toInt(super.getByIndex(INDEX_PORGID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getPorgid <em>Porgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Porgid</em>' attribute.
	 * @see #getPorgid()
	 */
	public void setPorgid(int porgid) {
		super.setByIndex(INDEX_PORGID, porgid);
	}


}