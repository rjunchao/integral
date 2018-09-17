/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package com.vbm.mas.userpermissions.orgdataset;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getPk <em>Pk</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getOrgid <em>Orgid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getEmpid <em>Empid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getUserid <em>Userid</em>}</li>
 *   <li>{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getPorgid <em>Porgid</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface FcUserPermissions extends DataObject {

	public final static String QNAME = "com.vbm.mas.userpermissions.orgdataset.FcUserPermissions";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("com.vbm.mas.userpermissions.orgdataset", "FcUserPermissions");

	public final static IObjectFactory<FcUserPermissions> FACTORY = new IObjectFactory<FcUserPermissions>() {
		public FcUserPermissions create() {
			return (FcUserPermissions) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getPk();

	/**
	 * Sets the value of the '{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getPk <em>Pk</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pk</em>' attribute.
	 * @see #getPk()
	 */
	public void setPk(String pk);

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
	public int getOrgid();

	/**
	 * Sets the value of the '{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getOrgid <em>Orgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orgid</em>' attribute.
	 * @see #getOrgid()
	 */
	public void setOrgid(int orgid);

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
	public int getEmpid();

	/**
	 * Sets the value of the '{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getEmpid <em>Empid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Empid</em>' attribute.
	 * @see #getEmpid()
	 */
	public void setEmpid(int empid);

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
	public String getUserid();

	/**
	 * Sets the value of the '{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getUserid <em>Userid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userid</em>' attribute.
	 * @see #getUserid()
	 */
	public void setUserid(String userid);

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
	public int getPorgid();

	/**
	 * Sets the value of the '{@link com.vbm.mas.userpermissions.orgdataset.FcUserPermissions#getPorgid <em>Porgid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Porgid</em>' attribute.
	 * @see #getPorgid()
	 */
	public void setPorgid(int porgid);


}