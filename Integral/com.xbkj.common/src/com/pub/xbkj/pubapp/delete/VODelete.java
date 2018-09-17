package com.pub.xbkj.pubapp.delete;

import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;

/**
 * 从数据库中删除当前的数据
 * 
 * @param <E> 实体类型

 */
public class VODelete<E extends SuperVO> {

	private String tableName = null;
  /**
   * 从数据库中删除当前实体
   * 
   * @param vos 实体
   */
  public void delete(E[] vos) {
   
	  this.tableName = vos[0].getTableName();
	  if(this.tableName == null ){
		  ExceptionUtils.wrappBusinessException("VODelete : 删除数据表名不能为空~");
	  }
     DeleteTable bo = new DeleteTable();
    
     bo.delete(vos);
    
  }
}
