/*
 * Created on 2005-1-19
 *
 * @DefaultStack.java
 *
 */

package com.xbkj.log.vo.logging;

import java.util.EmptyStackException;
import java.util.LinkedList;

/**
 * The <code>Stack</code> class represents a last-in-first-out 
 * (LIFO) stack of objects. It contains a <tt>LinkedList</tt> and 
 * implements interface <tt>IStackable</tt> that allow a LinkedList 
 * to be treated as a stack. The usual <tt>push</tt> and <tt>pop</tt> 
 * operations are provided, as well as a method to <tt>peek</tt> at 
 * the top item on the stack, a method to test for whether the stack 
 * is <tt>empty</tt>, and a method to <tt>search</tt> the stack for 
 * an item and discover how far it is from the top.
 * <p>
 * When a stack is first created, it contains no items. 
 *
 * @author  Chencen
 * @version 1.0
 * @since   NC 3.1
 *
 */

public class DefaultStack implements IStackable {
	private LinkedList list = new LinkedList(); 
	
    /**
     * Creates an empty Stack.
     */
    public DefaultStack() {
    }

    /**
     * Pushes an item onto the top of this stack. This has exactly 
     * the same effect as:
     * <blockquote><pre>
     * addFirst(item)</pre></blockquote>
     *
     * @param   item   the item to be pushed onto this stack.
     * @return  the <code>item</code> argument.
     * @see     java.util.LinkedList#addFirst
     */
    public Object push(Object item) {
    	list.addFirst(item);
    	return item;
    }

    /**
     * Removes the object at the top of this stack and returns that 
     * object as the value of this function. 
     *
     * @return     The object at the top of this stack.
     * @exception  EmptyStackException  if this stack is empty.
     */
    public Object pop() {
    	Object	obj = peek();
    	list.removeFirst();
    	return obj;
    }

    /**
     * Looks at the object at the top of this stack without removing it 
     * from the stack. 
     *
     * @return     The object at the top of this stack. 
     * @exception  EmptyStackException  if this stack is empty.
     */
    public Object peek() {
    	int	len = list.size();

    	if (len == 0)
    		throw new EmptyStackException();
    	return list.getFirst();
    }

    /**
     * Tests if this stack is empty.
     *
     * @return  <code>true</code> if and only if this stack contains 
     *          no items; <code>false</code> otherwise.
     */
    public boolean empty() {
    	return list.size() == 0;
    }
}
