package org.gocom.components.coframe.flowconfig.user;

import com.primeton.cap.spi.auth.rule.AbstractRuleCondition;
import com.primeton.cap.spi.auth.rule.IRuleContext;

/**
 * 所有用户条件匹配类
 * 
 * @author wangwx (mailto:wangwx@primeton.com)
 */

public class AllUserCondition extends AbstractRuleCondition {

	private static final long serialVersionUID = -6937223566864057763L;

	/**
	 * 构造方法(默认)
	 * 
	 */
	public AllUserCondition() {

	}

	/**
	 * 检查规则上下文是否匹配
	 * 
	 * @param context 规则上下文
	 */
	public boolean isMatch(IRuleContext context) {
		return true;
	}

}
