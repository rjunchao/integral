package com.pub.xbkj.pubapp.delete;

import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;

/**
 * 从数据库中物理删除当前实体的数据，而不是设置DR标志
 * 
 */
public class VORealDelete<E extends SuperVO> {


	private String tableName = null;
  /**
   * 从数据库中物理删除当前实体的数据
   * 
   * @param vos 实体数据
   */
  public void delete(E[] vos) {
   
	  if(vos == null || vos.length < 1){
		  return;
	  }
	  this.tableName = vos[0].getTableName();
	  
	  if(this.tableName == null){
		  ExceptionUtils.wrappBusinessException("VORealDelete : "+vos[0].getClass().getName() + "表名不能为空~");
	  }
      RealDeleteTable bo = new RealDeleteTable();
   
      bo.delete(vos, tableName);
  
  }
}
