/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.flowconfig.flowconfigEntity;

import com.eos.data.sdo.IObjectFactory;

import commonj.sdo.DataObject;
import commonj.sdo.Type;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleId <em>RuleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleName <em>RuleName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleType <em>RuleType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleExpression <em>RuleExpression</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends DataObject;
 */
public interface CapRule extends DataObject {

	public final static String QNAME = "org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule";

	public final static Type TYPE = TypeHelper.INSTANCE.getType("org.gocom.components.coframe.flowconfig.flowconfigEntity", "CapRule");

	public final static IObjectFactory<CapRule> FACTORY = new IObjectFactory<CapRule>() {
		public CapRule create() {
			return (CapRule) DataFactory.INSTANCE.create(TYPE);
		}
	};

	/**
	 * Returns the value of the '<em><b>RuleId</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RuleId</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RuleId</em>' attribute.
	 * @see #setRuleId(java.lang.String)
	 */
	public String getRuleId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleId <em>RuleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleId</em>' attribute.
	 * @see #getRuleId()
	 */
	public void setRuleId(String ruleId);

	/**
	 * Returns the value of the '<em><b>RuleName</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RuleName</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RuleName</em>' attribute.
	 * @see #setRuleName(java.lang.String)
	 */
	public String getRuleName();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleName <em>RuleName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleName</em>' attribute.
	 * @see #getRuleName()
	 */
	public void setRuleName(String ruleName);

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
	public String getTenantId();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getTenantId <em>TenantId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>TenantId</em>' attribute.
	 * @see #getTenantId()
	 */
	public void setTenantId(String tenantId);

	/**
	 * Returns the value of the '<em><b>RuleType</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RuleType</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RuleType</em>' attribute.
	 * @see #setRuleType(java.lang.String)
	 */
	public String getRuleType();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleType <em>RuleType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleType</em>' attribute.
	 * @see #getRuleType()
	 */
	public void setRuleType(String ruleType);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(java.lang.String)
	 */
	public String getNamespace();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 */
	public void setNamespace(String namespace);

	/**
	 * Returns the value of the '<em><b>RuleExpression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RuleExpression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>RuleExpression</em>' attribute.
	 * @see #setRuleExpression(byte[])
	 */
	public byte[] getRuleExpression();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getRuleExpression <em>RuleExpression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleExpression</em>' attribute.
	 * @see #getRuleExpression()
	 */
	public void setRuleExpression(byte[] ruleExpression);

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
	public String getCreateuser();

	/**
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getCreateuser <em>Createuser</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createuser</em>' attribute.
	 * @see #getCreateuser()
	 */
	public void setCreateuser(String createuser);

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
	 * Sets the value of the '{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule#getCreatetime <em>Createtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Createtime</em>' attribute.
	 * @see #getCreatetime()
	 */
	public void setCreatetime(Date createtime);


}