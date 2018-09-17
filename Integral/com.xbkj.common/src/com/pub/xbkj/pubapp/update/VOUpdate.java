package com.pub.xbkj.pubapp.update;

import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.Constructor;
import com.pub.xbkj.pubapp.ElementConstant;
import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.data.VOMaintainUtils;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.mapping.MappingMeta;

/**
 * 更新实体中变化的字段到到数据库中
 * 
 */
public class VOUpdate<E extends SuperVO> {

	 /**
	   * 要查询的实体属性
	   */
	  private String[] attributes;

	 
	  
	  /**
	   * 实体类
	   */
	  private E entity;
	  
	  private String tableName;

	  /**
	   * 实体查询构造函数
	   * 
	   * @param voClass 实体类型Class
	   */
	  public VOUpdate(Class<E> voClass) {
	    this.entity =  Constructor.construct(voClass);
	    this.tableName =  this.entity.getTableName();
		
	  }

	  /**
	   * 实体查询构造函书
	   * 
	   * @param voClass 实体类型Class
	   * @param tableName 表名
	   * @param fileds 要查询的实体属性名
	   */
	  public VOUpdate(Class<E> voClass,String[] fields) {
		this.entity =  Constructor.construct(voClass);
		this.tableName =  this.entity.getTableName();
	    this.attributes = fields;
	  }
  /**
   * 将VO的所有属性（主键除外）更新到数据库中
   * 
   * @param vos 要更新的VO
   * @return 保存到数据库后的最新状态的VO
   */
  public E[] update(E[] vos,JavaType [] types,boolean autoTs) {
   
	  if(vos == null || vos.length < 1){
		  return null;
	  }
	 this.tableName = vos[0].getTableName(); 
	if(this.tableName == null){
		ExceptionUtils.wrappBusinessException("VOUpdate : 更新表名不能为空 ~");
		
	}
	if(this.attributes == null){
		ExceptionUtils.wrappBusinessException("VOUpdate : 更新字段不能为空 ~");
		
	}
	List<String> list = new ArrayList<String>();
	String primaryKeyField = vos[0].getPKFieldName();
    for (String field : this.attributes) {
      // 去掉对主键的更新
      if (field.equals(primaryKeyField)) {
        continue;
      }
      // 去掉对TS的更新（后续会自动处理TS）
      else if (ElementConstant.KEY_TS.equals(field)) {
        continue;
      }
      list.add(field);
    }
    int size = list.size();
    attributes = new String[size];
    attributes = list.toArray(attributes);
    return this.updateReal(vos, attributes,types,autoTs);
  }

 
  /**
   * 将要更新的VO指定更新哪些属性到数据库中
   * 
   * @param vos 要更新的VO
   * @param names 要更新的属性名
   * @return 保存到数据库后的最新状态的VO
   */
  public E[] update(E[] vos, String[] fields,JavaType [] types,boolean autoTs) {
    this.attributes = fields;
    this.tableName = vos[0].getTableName();
    return this.updateReal(vos, this.attributes,types,autoTs);
  }


  /**
   * 实际的更新操作
   * @param vos
   * @param attributes
   * @return
   */
  private E[] updateReal(E[] vos, String[] attributes,JavaType [] types,boolean autoTs) {
	  if(this.tableName == null){
			ExceptionUtils.wrappBusinessException("VOUpdate : 更新表名不能为空 ~");
			
		}
		if(this.attributes == null){
			ExceptionUtils.wrappBusinessException("VOUpdate : 更新字段不能为空 ~");
			
		}
  
	  //更新操作
      UpdateTable bo = new UpdateTable(autoTs);
      int size = types.length;
      JavaType [] tempTypes = null;
      if(autoTs && !( types.length-attributes.length < 1) ){
    	  tempTypes = new JavaType[size+1];
      }else if(autoTs && ( types.length-attributes.length < 1)){
    	  tempTypes = new JavaType[size+2];
      }else{
    	  tempTypes = new JavaType[types.length];
      }
      for(int i = 0 ; i < size ; i++){
    	  tempTypes[i] = types[i];
      }
      if(autoTs && ( types.length-attributes.length < 1) ){
    	  tempTypes[size] = JavaType.String;
    	//把主键类型加上
          tempTypes[size+1] = JavaType.String;
      }else if(autoTs && !( types.length-attributes.length < 1)){
    	  tempTypes[size] = JavaType.String;
      }
      
     
      bo.update(vos,this.attributes,tempTypes);
      


     return vos;
  }
  
  /**
   * 实际的更新操作
   * @param vos
   * @return
   */
  public E[] update(E[] vos) {
	     E vo = vos[0];
	    
	    String parmaryKeyField = vo.getPKFieldName();
	    
	    String taleName = vo.getTableName();
	    
	    if(this.tableName == null){
	    	ExceptionUtils.wrappBusinessException("VOInsert : 要更新的表名不能为空~" );
	    	
	    }
	    if(parmaryKeyField == null || "".equals(parmaryKeyField)){
	    	ExceptionUtils.wrappBusinessException("VOInsert : 要更新的VO的主键不能为空~" );
	    	
	    }
	    
	    VOMaintainUtils utils = new VOMaintainUtils();
	     MappingMeta mappingMeta =  utils.constructMappingMetaWithOutDr(taleName, parmaryKeyField);
	     boolean autoTs = mappingMeta.getAttributeList().contains("ts");
	  //更新操作
      UpdateTable bo = new UpdateTable(autoTs);
     
      //如果包含ts,需要删除，后续自动添加,但是java类型不能删除
      if(autoTs){
      	  int index = mappingMeta.getAttributeList().indexOf("ts");
      	  mappingMeta.getAttributeList().remove(index);
      	  mappingMeta.getJavaTypeList().remove(index);
      }
      //删除主键字段，但不删除类型
      int pkIndex = mappingMeta.getAttributeList().indexOf(parmaryKeyField);
      mappingMeta.getAttributeList().remove(pkIndex);
      mappingMeta.getJavaTypeList().remove(pkIndex);
     //添加ts与主键
      mappingMeta.getJavaTypeList().add(JavaType.String);
      mappingMeta.getJavaTypeList().add(JavaType.String);
      
      String [] fields = mappingMeta.getAttributeList().toArray(new String[]{});
      JavaType [] types = mappingMeta.getJavaTypeList().toArray(new JavaType[]{});
     
      bo.update(vos,fields,types);
      


     return vos;
  }
}
