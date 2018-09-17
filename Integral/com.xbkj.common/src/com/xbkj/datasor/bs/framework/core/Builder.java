package com.xbkj.datasor.bs.framework.core;

/**
 * 
 * Created by UFSoft.
 * User: �ι���
 *
 * Date: 2007-12-20
 * Time: ����10:29:43
 *
 */
public interface Builder extends LightBuilder {
	public Builder name(String name);

	public Builder alias(String name);

	public Builder export(Class<?> itf);

	public Builder export(Class<?>[] itf);

	public Builder export(String itfName);

	public Builder export(String[] itfName);

	public Builder toClass(String impl);

	public Builder to(Class<?> impl);

	public Builder to(Object obj);

	public Builder scope(Scope scope);

	public Builder asPublic(boolean flag);

	public Builder asRemote(boolean flag);

	public Builder at(String cluster);

	public void bindin();

	public void bindin(boolean aliasExport);

	public Builder pri(int pri);

}
