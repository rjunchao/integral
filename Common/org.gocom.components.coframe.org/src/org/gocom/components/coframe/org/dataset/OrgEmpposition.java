/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.org.dataset;

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
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getIsmain <em>Ismain</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getOrgEmployee <em>OrgEmployee</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getOrgPosition <em>OrgPosition</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface OrgEmpposition extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.org.dataset.OrgEmpposition";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "OrgEmpposition");
	
	public final static String NODE_TYPE = "OrgEmporg";

	public final static IObjectFactory<OrgEmpposition> FACTORY = new IObjectFactory<OrgEmpposition>() {
		public OrgEmpposition create() {
			return (OrgEmpposition) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public String getIsmain();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getIsmain <em>Ismain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ismain</em>' attribute.
	 * @see #getIsmain()
	 */
	public void setIsmain(String ismain);

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
	public String getTenantid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getTenantid <em>Tenantid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tenantid</em>' attribute.
	 * @see #getTenantid()
	 */
	public void setTenantid(String tenantid);

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
	public String getAppid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(String appid);

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
	public OrgEmployee getOrgEmployee();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getOrgEmployee <em>OrgEmployee</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgEmployee</em>' attribute.
	 * @see #getOrgEmployee()
	 */
	public void setOrgEmployee(OrgEmployee orgEmployee);

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
	public OrgPosition getOrgPosition();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgEmpposition#getOrgPosition <em>OrgPosition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgPosition</em>' attribute.
	 * @see #getOrgPosition()
	 */
	public void setOrgPosition(OrgPosition orgPosition);


}