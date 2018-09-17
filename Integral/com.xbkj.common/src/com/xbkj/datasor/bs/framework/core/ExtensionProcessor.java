package com.xbkj.datasor.bs.framework.core;

import javax.xml.stream.XMLStreamReader;

/**
 * 
 * process elements under component extension
 * 
 * @author hgy
 * 
 */
public interface ExtensionProcessor {

	/**
	 * used to extend a component at deploy time. the meta has not registed at
	 * the container when this occurred. attention: r must position at element
	 * start
	 * 
	 * @param container
	 * @param meta
	 * @param r
	 */
	public void processAtDeploy(GenericContainer<?> container, Meta meta,
			XMLStreamReader r);
	
	public void processAtDeployEnd(GenericContainer<?> container, Meta meta);

	/**
	 * used to extend a component at the container start time
	 * 
	 * @param container
	 * @param meta
	 */
	public void processAtStart(GenericContainer<?> container, Meta meta);

	/**
	 * used to extend a component at the container stop time
	 * 
	 * @param container
	 * @param meta
	 */
	public void processAtStop(GenericContainer<?> container, Meta meta);
}
