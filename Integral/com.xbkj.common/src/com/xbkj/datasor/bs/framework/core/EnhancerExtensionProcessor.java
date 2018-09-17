package com.xbkj.datasor.bs.framework.core;

import javax.xml.stream.XMLStreamReader;

/**
 * ExtensionProcessor to regist the enhancer
 * 
 * @author hgy
 * 
 */
public class EnhancerExtensionProcessor extends ExtensionProcessorSupport {

	private boolean pre;

	private boolean post;

	public void processAtDeploy(GenericContainer<?> container, Meta meta, XMLStreamReader r) {
		if (r.getLocalName().equals("pre")) {
			pre = true;
		} else if (r.getLocalName().equals("post")) {
			post = true;
		}

	}

	public void processPreStart(GenericContainer<?> container, Meta meta) {
		Enhancer e = (Enhancer) container.getContext().lookup(meta.getName());
		if (pre)
			container.getEnhancerManager().addPreEnhancer(e);
		if (post)
			container.getEnhancerManager().addPostEnhancer(e);
	}

	public void processAfterStop(GenericContainer<?> container, Meta meta) {
		Enhancer e = (Enhancer) container.getContext().lookup(meta.getName());
		if (pre)
			container.getEnhancerManager().removePreEnhancer(e);

		if (post)
			container.getEnhancerManager().removePostEnhancer(e);
	}
}
