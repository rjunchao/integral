
package com.xbkj.log.vo.logging;

/**
 * A representation of a Stack that does not use Synchronization.
 * For compatibility this class supports the same methods as a 
 * java.util.Stack (JDK)
 *
 * @author <a href="mailto:kvisco@ziplink.net">Keith Visco</a>
 * @version $Revision: 1.4 $ $Date: 2000/11/04 01:31:13 $
 * @author cc refactor
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NCStack implements IStackable {

	private int size = 0;
		
	private StackItem top = null;
		
		  //----------------/
		 //- Constructors -/
		//----------------/
		
		/**
		 * Creates an empty Stack
		**/
		public NCStack() {
			super();
		}
		
		/**
		 * Tests for an empty Stack
		 * @return true if there are no elements on the stack, otherwise false.
		**/
		public boolean empty() {
			return (size() == 0);
		} //-- empty
		
		/**
		 * Returns the Object that is currently on top of the Stack. 
		 * Unlike #pop the Object is not removed from the Stack.
		 * @return the Object that is currently the top of the stack
		 * @exception java.util.EmptyStackException when there are no
		 * elements currently on the Stack
		**/
		public Object peek() 
			throws java.util.EmptyStackException 
		{
			if (empty()) throw new java.util.EmptyStackException();
			return top.object;
		} //--peek
		
		/**
		 * Removes and returns the Object that is currently on top of the Stack.
		 * @return the Object that is currently the top of the stack
		 * @exception java.util.EmptyStackException when there are no
		 * elements currently on the Stack
		**/
		public Object pop()
			throws java.util.EmptyStackException 
		{
			if (empty()) throw new java.util.EmptyStackException();
			Object obj = top.object;
			top = top.previous;
			if (top != null) top.next = null;
			--size;
			return obj;
		} //-- pop
		
		/**
		 * Adds the given Object to the top of the Stack
		**/
		public Object push(Object object) {
			StackItem item = new StackItem();
			item.previous = top;
			item.next = null;
			item.object = object;
			if (top != null) top.next = item;
			top = item;
			++size;
			return object;
		} //-- push
		
		/**
		 * Returns the number of items on the Stack
		 * @return the number of items on the Stack
		**/
		public int size() { 
			return size; 
		}
		
		private class StackItem {
			StackItem next = null;
			StackItem previous = null;
			Object object = null;
		} //-- StackItem
}
