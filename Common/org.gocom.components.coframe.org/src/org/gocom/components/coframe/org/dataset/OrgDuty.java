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

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyid <em>Dutyid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutycode <em>Dutycode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyname <em>Dutyname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getParentduty <em>Parentduty</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutylevel <em>Dutylevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyseq <em>Dutyseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutytype <em>Dutytype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getRemark <em>Remark</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgDuty#getAppid <em>Appid</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface OrgDuty extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.org.dataset.OrgDuty";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "OrgDuty");

	public final static String NODE_TYPE = "OrgDuty";
	
	public final static IObjectFactory<OrgDuty> FACTORY = new IObjectFactory<OrgDuty>() {
		public OrgDuty create() {
			return (OrgDuty) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public BigDecimal getDutyid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyid <em>Dutyid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyid</em>' attribute.
	 * @see #getDutyid()
	 */
	public void setDutyid(BigDecimal dutyid);

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
	public String getDutycode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutycode <em>Dutycode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutycode</em>' attribute.
	 * @see #getDutycode()
	 */
	public void setDutycode(String dutycode);

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
	public String getDutyname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyname <em>Dutyname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyname</em>' attribute.
	 * @see #getDutyname()
	 */
	public void setDutyname(String dutyname);

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
	public BigDecimal getParentduty();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getParentduty <em>Parentduty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parentduty</em>' attribute.
	 * @see #getParentduty()
	 */
	public void setParentduty(BigDecimal parentduty);

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
	public int getDutylevel();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutylevel <em>Dutylevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutylevel</em>' attribute.
	 * @see #getDutylevel()
	 */
	public void setDutylevel(int dutylevel);

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
	public String getDutyseq();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutyseq <em>Dutyseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutyseq</em>' attribute.
	 * @see #getDutyseq()
	 */
	public void setDutyseq(String dutyseq);

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
	public String getDutytype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getDutytype <em>Dutytype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dutytype</em>' attribute.
	 * @see #getDutytype()
	 */
	public void setDutytype(String dutytype);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getIsleaf <em>Isleaf</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Isleaf</em>' attribute.
	 * @see #getIsleaf()
	 */
	public void setIsleaf(String isleaf);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getSubcount <em>Subcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subcount</em>' attribute.
	 * @see #getSubcount()
	 */
	public void setSubcount(BigDecimal subcount);

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
	public String getRemark();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getRemark <em>Remark</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remark</em>' attribute.
	 * @see #getRemark()
	 */
	public void setRemark(String remark);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getTenantid <em>Tenantid</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgDuty#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(String appid);


}