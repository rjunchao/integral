package com.vbm.grc.system.itf.pub;

import com.xbkj.basic.vo.pub.BusinessException;

/**
 * 
 * @author lijbe
 * @version 1.0.0.0
 * @date 2016年7月1日10:37:25
 * 
 * 表引用关系判断接口
 */
public interface IRefRelationPub {

	/**
	 * 判断tableName下
	 * @param tableName
	 * @param keyField table下的字段
	 * @param keyVale 要删除的值
	 * @return
	 * @throws BusinessException
	 */
	public boolean isReferenced(String tableName, String keyField,String keyValue) throws BusinessException;
}
