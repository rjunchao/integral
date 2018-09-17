/*
 * Created on 2005-1-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StackableThreadLocal extends ThreadLocal {
	
	public void set(Object value) {
		// Get the stack first.
		DefaultStack stack = null;
		Object o = super.get();
		if (null == o) {
			// Should create a new stack and...
			stack = new DefaultStack();
			// and put into ThreadLocal var.
			super.set(stack);
		} else { 
			stack = (DefaultStack) o;
		}
		
		// Then, push the value.
		stack.push(value);
	}
	
	public Object get() {
		Object o = super.get();
		if (null == o) return null; // Nothing...

		DefaultStack stack = null;
		stack = (DefaultStack) o;
		if (stack.empty()) return null; // Nothing in stack.

		return stack.peek(); 
	}
	
	public Object pop() {
		Object o = super.get();
		if (null == o) return null; // Nothing...

		DefaultStack stack = null;
		stack = (DefaultStack) o;
		if (stack.empty()) return null; // Nothing in stack.
	
		return stack.pop();
	}
}
