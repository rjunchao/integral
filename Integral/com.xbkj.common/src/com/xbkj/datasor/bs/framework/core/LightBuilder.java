package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-20
 * Time: ����01:20:35
 *
 */
public interface LightBuilder {
	public LightBuilder name(String name);

	public LightBuilder alias(String name);

	public LightBuilder export(Class<?> itf);

	public LightBuilder export(Class<?>[] itfs);

	public LightBuilder export(String itfName);

	public LightBuilder export(String[] itfNames);

	public LightBuilder toClass(String impl);

	public LightBuilder to(Class<?> impl);

	public LightBuilder to(Object obj);

	public LightBuilder scope(Scope scope);

	public LightBuilder asActive(boolean flag);

	public void bindin(boolean aliasExport);

	public void bindin();

	public LightBuilder pri(int pri);

}
