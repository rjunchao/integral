package com.pub.xbkj.pubapp.insert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pub.xbkj.pubapp.Constructor;
import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.data.VOMaintainUtils;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.pub.xbkj.pubapp.query.DBTool;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.jdbc.framework.mapping.MappingMeta;

/**
 * 将实体VO插入到到数据库中.
 * <ol>
 * <li>
 * 对于VO跨越多个表存储的，我们认为从属表的主键和主表的主键应该是一致的（简化处理），所以ID只需要设置主表的主
 * 键就可以了。
 * <li>
 * 支持元数据没有主键的实体。
 * </li>
 * <li>
 * 如果新增单据的VO中已经存在主键，则不会覆盖设置一个新的主键。主要是解决徐风在南京14所发现的问题。
 * </li>
 * </ol>
 * <b>问题场景如下：</b> 手工输入一张其他入库单（输入100行左右－－这样保存的时间相对较长），点保存后约
 * 0.5秒（这个时间只能估计前台检查结果，调用后台保存时），拨掉网线，等前台日志里报错后再连上网线，此时前台保存
 * ，提示保存成功。查后台，其实已经保存了二张单据。最近14所调整网络加密策略，网络时断时续，导致后台生成重复单
 * 按钮可用，再点一次保据（用户无法察觉）。请教这种情况下可有好的办法能解决单据重复生 成问题？这个问题还是比较
 * 严重的。
 * <p>
 * <b>林大翰</b>的邮件答复（2009年11月16日 11:23）是： 这个项目断网是主动设置的策略，关键检查一下
 * 断网的时候，我们有没有异常对话框弹出。如果要改，大家可以在点 “新增”的时候 把主表的pk生成，下载到客户端 即可。
 * 这样就可以防止问题的出现。
 * 
 * @param <E> 实体类型
 * 2016年6月22日10:23:21
 */
public class VOInsert<E extends SuperVO> {

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
	  public VOInsert(Class<E> voClass) {
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
	  public VOInsert(Class<E> voClass, String[] fields) {
		  
		this.entity =  Constructor.construct(voClass);
		this.tableName =  this.entity.getTableName();
		 
	    this.attributes = fields;
	   
	  }
  /**
   * 将实体VO插入到数据库中
   * 
   * @param vos 实体VO数组
   * @return 插入到数据库中的实体VO数据
   */
  public E[] insert(E[] vos,String [] fields, JavaType [] types,boolean autoTs) {
  if(vos == null || vos.length < 1){
	  return null;
  }
  
	E vo = vos[0];
    
    String parmaryKeyField = vo.getPKFieldName();
    this.attributes = fields;
    if(this.attributes == null){
    	ExceptionUtils.wrappBusinessException("VOInsert : 要保存的属性不能为空~" );
    	
    }
    if(this.tableName == null){
    	ExceptionUtils.wrappBusinessException("VOInsert : 要保存的表名不能为空~" );
    	
    }
    if(types == null || types.length < 1){
    	ExceptionUtils.wrappBusinessException("VOInsert : 要保存的属性Java类型不能为空~" );
    	
    }
    // 实体有可能没有主键,设置主键
    if (parmaryKeyField != null) {
      this.setOIDs(vos, parmaryKeyField);
    }

    if(autoTs && (types.length-fields.length < 1)){
    	List<JavaType> list = new ArrayList<JavaType>();
    	list.addAll(Arrays.asList(types));
    	list.add(JavaType.String) ;
    	types = list.toArray(new JavaType[list.size()]);
    }
    InsertTable bo = new InsertTable(autoTs);
   
    bo.insert(vos, fields, types);
    return vos;
  }

  /**
   * 设置主键，有值的就跳过
   * @param vos
   * @param parmaryKeyField
   */
  private void setOIDs(E[] vos, String  parmaryKeyField) {
   
    int size = vos.length;
    DBTool dao = new DBTool();
    String[] ids = dao.getOIDs(size);

    int cursor = 0;

    String pkValue = null;
    for (E vo : vos) {
    	pkValue = (String) vo.getAttributeValue(parmaryKeyField);
        if (pkValue == null) {
          vo.setAttributeValue(parmaryKeyField, ids[cursor]);
        }
      
      cursor++;
    }
  }
  
  /**
   * 将实体VO插入到数据库中
   * 
   * @param vos 实体VO数组
   * @return 插入到数据库中的实体VO数据
   */
  public E[] insert(E[] vos) {
  if(vos == null || vos.length < 1){
	  return null;
  }
  
	E vo = vos[0];
    
    String parmaryKeyField = vo.getPKFieldName();
    
    String taleName = vo.getTableName();
    
    if(this.tableName == null){
    	ExceptionUtils.wrappBusinessException("VOInsert : 要保存的表名不能为空~" );
    	
    }
    if(parmaryKeyField == null || "".equals(parmaryKeyField)){
    	ExceptionUtils.wrappBusinessException("VOInsert : 要保存的VO的主键不能为空~" );
    	
    }
     VOMaintainUtils utils = new VOMaintainUtils();
     MappingMeta mappingMeta =  utils.constructMappingMetaWithOutDr(taleName, parmaryKeyField);
    // 实体有可能没有主键,设置主键
    if (parmaryKeyField != null) {
      this.setOIDs(vos, parmaryKeyField);
    }
    boolean autoTs = mappingMeta.getAttributeList().contains("ts");
  
    //如果包含ts,需要删除，后续自动添加,但是java类型不能删除
    if(autoTs){
    	  int index = mappingMeta.getAttributeList().indexOf("ts");
    	  mappingMeta.getAttributeList().remove(index);
    	  mappingMeta.getJavaTypeList().remove(index);
    }
    String [] fields = mappingMeta.getAttributeList().toArray(new String[]{});
     mappingMeta.getJavaTypeList().add(JavaType.String);
  
    JavaType [] types =   mappingMeta.getJavaTypeList().toArray(new JavaType[]{});
    InsertTable bo = new InsertTable(autoTs);
   
    bo.insert(vos, fields, types);
    return vos;
  }

}
