package com.xbkj.datasor.bs.framework.execute;

import java.util.List;

/**
 * Executor Manager,use it to start execute this executor.
 * 
 * Created on 2005-9-27 10:51:10
 */

public interface ExecutorManager {

	public static final boolean JOIN_AND = false;
	public static final boolean JOIN_OR = true;

	/**
	 * execute the runnable
	 */
//	public abstract RunnableItem startExecute(Runnable runnable)
//			throws ExecuteException, IllegalArgumentException;

	/**
	 * join the executor if timeout is 0 ,it will wait for ever until the
	 * condition is satisified. or it is larger than 0,it will quit whether it
	 * come to this time or the condition is satisified
	 * 
	 * @param runnableItems
	 *            , the runnables to check
	 * @param flag
	 *            , whether the runnableItems complete all or just one
	 *            complete,if false,just one complete it will quit,if true,it
	 *            will wait for all the runnables complete
	 * @param timeout
	 *            the waiting timeout
	 */
//	public abstract void join(List<RunnableItem> runnableItems, boolean flag,
//			long timeout);

}
