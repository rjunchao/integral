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
import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositionid <em>Positionid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosicode <em>Posicode</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosiname <em>Posiname</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosilevel <em>Posilevel</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositionseq <em>Positionseq</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositype <em>Positype</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getCreatetime <em>Createtime</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getLastupdate <em>Lastupdate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getUpdator <em>Updator</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getStartdate <em>Startdate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getEnddate <em>Enddate</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getStatus <em>Status</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getIsleaf <em>Isleaf</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getSubcount <em>Subcount</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getTenantid <em>Tenantid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getAppid <em>Appid</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgDuty <em>OrgDuty</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgPosition <em>OrgPosition</em>}</li>
 *   <li>{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgOrganization <em>OrgOrganization</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface OrgPosition extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.org.dataset.OrgPosition";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.org.dataset", "OrgPosition");

	public final static String NODE_TYPE = "OrgPosition";
	
	public final static IObjectFactory<OrgPosition> FACTORY = new IObjectFactory<OrgPosition>() {
		public OrgPosition create() {
			return (OrgPosition) DataFactory.INSTANCE.create(TYPE);
		}
	};

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
	public BigDecimal getPositionid();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositionid <em>Positionid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positionid</em>' attribute.
	 * @see #getPositionid()
	 */
	public void setPositionid(BigDecimal positionid);

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
	public String getPosicode();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosicode <em>Posicode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posicode</em>' attribute.
	 * @see #getPosicode()
	 */
	public void setPosicode(String posicode);

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
	public String getPosiname();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosiname <em>Posiname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posiname</em>' attribute.
	 * @see #getPosiname()
	 */
	public void setPosiname(String posiname);

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
	public BigDecimal getPosilevel();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPosilevel <em>Posilevel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Posilevel</em>' attribute.
	 * @see #getPosilevel()
	 */
	public void setPosilevel(BigDecimal posilevel);

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
	public String getPositionseq();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositionseq <em>Positionseq</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positionseq</em>' attribute.
	 * @see #getPositionseq()
	 */
	public void setPositionseq(String positionseq);

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
	public String getPositype();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getPositype <em>Positype</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positype</em>' attribute.
	 * @see #getPositype()
	 */
	public void setPositype(String positype);

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
	public Date getCreatetime();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime);

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
	public Date getLastupdate();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getLastupdate <em>Lastupdate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lastupdate</em>' attribute.
	 * @see #getLastupdate()
	 */
	public void setLastupdate(Date lastupdate);

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
	public BigDecimal getUpdator();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getUpdator <em>Updator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updator</em>' attribute.
	 * @see #getUpdator()
	 */
	public void setUpdator(BigDecimal updator);

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
	public Date getStartdate();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getStartdate <em>Startdate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Startdate</em>' attribute.
	 * @see #getStartdate()
	 */
	public void setStartdate(Date startdate);

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
	public Date getEnddate();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getEnddate <em>Enddate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enddate</em>' attribute.
	 * @see #getEnddate()
	 */
	public void setEnddate(Date enddate);

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
	public String getStatus();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 */
	public void setStatus(String status);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getIsleaf <em>Isleaf</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getSubcount <em>Subcount</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subcount</em>' attribute.
	 * @see #getSubcount()
	 */
	public void setSubcount(BigDecimal subcount);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getTenantid <em>Tenantid</em>}' attribute.
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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getAppid <em>Appid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Appid</em>' attribute.
	 * @see #getAppid()
	 */
	public void setAppid(String appid);

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
	public OrgDuty getOrgDuty();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgDuty <em>OrgDuty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgDuty</em>' attribute.
	 * @see #getOrgDuty()
	 */
	public void setOrgDuty(OrgDuty orgDuty);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgPosition <em>OrgPosition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgPosition</em>' attribute.
	 * @see #getOrgPosition()
	 */
	public void setOrgPosition(OrgPosition orgPosition);

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
	public OrgOrganization getOrgOrganization();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.org.dataset.OrgPosition#getOrgOrganization <em>OrgOrganization</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>OrgOrganization</em>' attribute.
	 * @see #getOrgOrganization()
	 */
	public void setOrgOrganization(OrgOrganization orgOrganization);

}