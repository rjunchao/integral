package com.xbkj.datasor.bs.framework.core;

/**
 * Created by IntelliJ IDEA.
 *  
 * User: �ι��� 
 * Date: 2005-1-25 
 * Time: 9:36:55
 * 
 * 
 * Manager for enhancer
 */
public interface EnhancerManager {
	public Enhancer[] getPreEnhancers();

	public Enhancer[] getPostEnhancers();

	public void addPreEnhancer(Enhancer enhancer);

	public void addPostEnhancer(Enhancer enhancer);

	public void removePreEnhancer(Enhancer enhancer);

	public void removePostEnhancer(Enhancer enhancer);

}
