/*******************************************************************************
 *
 * Copyright (c) 2001-2006 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on Apr 11, 2008
 *******************************************************************************/
package org.gocom.components.coframe.flowconfig.flowconfigEntity.impl;

import com.primeton.ext.data.sdo.DataUtil;
import com.primeton.ext.data.sdo.ExtendedDataObjectImpl;

import commonj.sdo.Type;

import java.util.Date;

import org.gocom.components.coframe.flowconfig.flowconfigEntity.CapRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getRuleId <em>RuleId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getRuleName <em>RuleName</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getTenantId <em>TenantId</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getRuleType <em>RuleType</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getRuleExpression <em>RuleExpression</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getCreateuser <em>Createuser</em>}</li>
 *   <li>{@link org.gocom.components.coframe.flowconfig.flowconfigEntity.impl.CapRuleImpl#getCreatetime <em>Createtime</em>}</li>
 * </ul>
 * </p>
 *
 * @extends ExtendedDataObjectImpl;
 *
 * @implements CapRule;
 */

public class CapRuleImpl extends ExtendedDataObjectImpl implements CapRule {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	public final static int INDEX_RULEID = 0;
	public final static int INDEX_RULENAME = 1;
	public final static int INDEX_TENANTID = 2;
	public final static int INDEX_RULETYPE = 3;
	public final static int INDEX_NAMESPACE = 4;
	public final static int INDEX_RULEEXPRESSION = 5;
	public final static int INDEX_CREATEUSER = 6;
	public final static int INDEX_CREATETIME = 7;
	public final static int SDO_PROPERTY_COUNT = 8;

	public final static int EXTENDED_PROPERTY_COUNT = -1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapRuleImpl() {
		this(TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public CapRuleImpl(Type type) {
		super(type);
	}

	protected void validate() {
		validateType(TYPE);
	}

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
	public String getRuleId() {
		return DataUtil.toString(super.getByIndex(INDEX_RULEID, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRuleId <em>RuleId</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleId</em>' attribute.
	 * @see #getRuleId()
	 */
	public void setRuleId(String ruleId) {
		super.setByIndex(INDEX_RULEID, ruleId);
	}

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
	public String getRuleName() {
		return DataUtil.toString(super.getByIndex(INDEX_RULENAME, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRuleName <em>RuleName</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleName</em>' attribute.
	 * @see #getRuleName()
	 */
	public void setRuleName(String ruleName) {
		super.setByIndex(INDEX_RULENAME, ruleName);
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
	public String getRuleType() {
		return DataUtil.toString(super.getByIndex(INDEX_RULETYPE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRuleType <em>RuleType</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleType</em>' attribute.
	 * @see #getRuleType()
	 */
	public void setRuleType(String ruleType) {
		super.setByIndex(INDEX_RULETYPE, ruleType);
	}

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
	public String getNamespace() {
		return DataUtil.toString(super.getByIndex(INDEX_NAMESPACE, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 */
	public void setNamespace(String namespace) {
		super.setByIndex(INDEX_NAMESPACE, namespace);
	}

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
	public byte[] getRuleExpression() {
		return DataUtil.toBytes(super.getByIndex(INDEX_RULEEXPRESSION, true));
	}

	/**
	 * Sets the value of the '{@link com.primeton.eos.Test#getRuleExpression <em>RuleExpression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>RuleExpression</em>' attribute.
	 * @see #getRuleExpression()
	 */
	public void setRuleExpression(byte[] ruleExpression) {
		super.setByIndex(INDEX_RULEEXPRESSION, ruleExpression);
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