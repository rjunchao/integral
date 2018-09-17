/*
 * Created on 2005-1-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.patterns;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DefaultLogAttributeProvider implements LogAttributeProvider {
	protected Set attrs = new HashSet();
	
	public DefaultLogAttributeProvider() {
		attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_TIMESTAMP,
				LogAttribute.LOG_ATTR_TIMESTAMP_VAL));
		attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_LEVEL,
				LogAttribute.LOG_ATTR_LEVEL_VAL));
		attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_CLASS,
				LogAttribute.LOG_ATTR_CLASS_VAL));
		attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_MODULE,
				LogAttribute.LOG_ATTR_MODULE_VAL));
		attrs.add(new LogAttribute(LogAttribute.LOG_ATTR_METHOD,
				LogAttribute.LOG_ATTR_METHOD_VAL));
//		new LogAttribute(LogAttribute.LOG_ATTR_THREAD,
//				LogAttribute.LOG_ATTR_THREAD_VAL)
	}

	public Set getAttributes() {
		return attrs;
	}
}
