package com.pub.xbkj.pubapp.data;

import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.insert.TableColPropertyVO;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.xbkj.common.jdbc.framework.mapping.MappingMeta;

/**
 *@author ljb
 *@date 2016-7-31
 *@version 1.0.0
 */
public class VOMaintainUtils {

	

	/**
	 * 取得数据库元数据与java类型的对照关系
	 * @param tableName
	 * @param pkField
	 * @return
	 */
	  public MappingMeta constructMappingMeta(String tableName,String pkField){
		  TableColPropertyVO [] vos = this.queryColProperties(tableName);
		  MappingMeta mappingMeta = new MappingMeta(tableName, pkField);
		  
		  String dataType = null;
		   for(TableColPropertyVO vo : vos){
			   dataType = vo.getData_type();
			   
			   if(dataType.startsWith("VARCHAR2") || "CHAR".equalsIgnoreCase(dataType)){  
				   mappingMeta.addJava(JavaType.String);
			   }else if(dataType.startsWith("NUMBER") && 0 == vo.getData_scale()){
				   mappingMeta.addJava(JavaType.Integer);
			   }else if(dataType.startsWith("NUMBER") && vo.getData_scale() > 0){
				   mappingMeta.addJava(JavaType.Double);
			   }else if(dataType.startsWith("BLOB")){
				  // mappingMeta.addJava(JavaType.BLOB);
				   continue;
			   }
			   mappingMeta.addAttribute(vo.getColumn_name().toLowerCase());
		   }
		  return mappingMeta;
	  }
	  
	  
	  /**
		 * 取得数据库元数据与java类型的对照关系
		 * @param tableName
		 * @param pkField
		 * @return
		 */
		  public MappingMeta constructMappingMetaWithOutDr(String tableName,String pkField){
			  TableColPropertyVO [] vos = this.queryColProperties(tableName);
			  MappingMeta mappingMeta = new MappingMeta(tableName, pkField);
			  
			  String dataType = null;
			   for(TableColPropertyVO vo : vos){
				   if("dr".equalsIgnoreCase(vo.getColumn_name())){
					   continue;
				   }
				   dataType = vo.getData_type();
				  
				   if(dataType.startsWith("VARCHAR2") || "CHAR".equalsIgnoreCase(dataType)){  
					   mappingMeta.addJava(JavaType.String);
				   }else if(dataType.startsWith("NUMBER") && 0 == vo.getData_scale()){
					   mappingMeta.addJava(JavaType.Integer);
				   }else if(dataType.startsWith("NUMBER") && vo.getData_scale() > 0){
					   mappingMeta.addJava(JavaType.Double);
				   }else if(dataType.startsWith("BLOB")){
					   //mappingMeta.addJava(JavaType.BLOB);
					   continue;
				   }
				   mappingMeta.addAttribute(vo.getColumn_name().toLowerCase());
			   }
			  return mappingMeta;
		  }
		  
	  
	  public TableColPropertyVO [] queryColProperties(String tableName){
		  
		  VOQuery<TableColPropertyVO> query = new VOQuery<TableColPropertyVO>(TableColPropertyVO.class);
		  //String [] fields = new String[]{"TABLE_NAME","COLUMN_NAME","DATA_TYPE","DATA_LENGTH","DATA_PRECISION","DATA_SCALE"};
		  String sql = "select TABLE_NAME,COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_PRECISION,DATA_SCALE from user_tab_columns where table_name = '" +tableName.toUpperCase()+"'";
		  
		  TableColPropertyVO[] vos  = query.query(sql);
		  
		  return vos;
	  }
}
