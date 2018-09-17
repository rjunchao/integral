package com.vbm.grc.system.pub;

import com.eos.spring.BeanFactory;
import com.vbm.grc.system.itf.pub.IRefRelationPub;

/**
 *@author lijbe
 *@date 2016年7月1日12:04:06
 *@version 1.0.0
 */
public class GrcBeanFactory {

	
	private static class GrcBeanFactoryHolder{
		private static final GrcBeanFactory INSTANCE = new GrcBeanFactory();
	}
	private static GrcBeanFactory grcBeanFactory = null;
	
	
	private GrcBeanFactory(){
		
	}
	
	/**
	 * 访问底层数据接口
	 * @return
	 */
	public IRefRelationPub getRefRelationPub(){	
	
		return BeanFactory.newInstance().getBean("IBaseDAOBean");
	}
	
	public static GrcBeanFactory getInstance(){
		if(grcBeanFactory == null){
			grcBeanFactory = GrcBeanFactoryHolder.INSTANCE;
		}
		return grcBeanFactory;
	}

}
