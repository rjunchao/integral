/*
 * Created on 2005-1-19
 *
 */
package com.xbkj.log.vo.logging;

/**
 * @author cc
 *
 */
public interface IStackable {
	/**
	 * Pushes an item onto the top of this stack.
	 */
	public abstract Object push(Object item);

	/**
	 * Removes the object at the top of this stack and returns that 
	 * object as the value of this function. 
	 */
	public abstract Object pop();

	/**
	 * Looks at the object at the top of this stack without removing it 
	 * from the stack. 
	 */
	public abstract Object peek();

	/**
	 * Tests if this stack is empty.
	 */
	public abstract boolean empty();
}