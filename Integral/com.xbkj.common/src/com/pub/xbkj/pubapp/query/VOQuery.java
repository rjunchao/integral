package com.pub.xbkj.pubapp.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pub.xbkj.pubapp.Constructor;
import com.pub.xbkj.pubapp.ListToArrayTool;
import com.pub.xbkj.pubapp.SqlBuilder;
import com.pub.xbkj.pubapp.data.IRowSet;
import com.pub.xbkj.pubapp.data.VORowSetMap;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.processor.BeanListProcessor;
import com.xbkj.common.util.CollectionUtil;

/**
 * 根据相关的查询条件以及连接表来查询某个实体的值
 * 暂时只做了单表查询，以后再扩展多表查询
 * 2016年6月21日17:07:28
 * 
 */
public class VOQuery<E extends SuperVO> {

	
//private static Logger logger = TraceLoggerFactory.getLogger(VOQuery.class);
  /**
   * 要查询的实体属性
   */
  private String[] queryAttributes;

  /**
   * 要查询的实体Class
   */
  private Class<E> voClass;
  
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
  public VOQuery(Class<E> voClass) {
    this.voClass = voClass;
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
  public VOQuery(Class<E> voClass, String[] fields) {
    this.voClass = voClass;
    this.queryAttributes = fields;
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
  public VOQuery(Class<E> voClass, String tableName ,String[] fields) {
    this.voClass = voClass;
    this.tableName = tableName;
    this.queryAttributes = fields;
   
  }

 /**
  * 
  * @param tableName 表名
  * @param attributes 要查询的字段
  * @param wheresqlpart 类似于 where xxx=xx 
  * @param condition 条件
  * @param order 排序
  * @return
  */
  private String constructSQL(String tableName,String[] attributes,
      String wheresqlpart, String condition, String order) {

    SqlBuilder sql = new SqlBuilder();
    sql.append(" select ");
    // 要查询的字段
    sql.append(this.constructQueryField(attributes));
    sql.append(" from ");
    
    sql.append( tableName);

    if (wheresqlpart == null) {
      sql.append(" where ");
    }
    else {
      sql.append(" ");
      sql.append(wheresqlpart);
      sql.append(" and ");
    }
    //拼接dr = 0
    sql.append(tableName);
    sql.append(".");
    sql.append(" dr  = 0 ");
    // 额外的条件
    if (condition != null) {
      sql.append(" and ");
      sql.append(condition);
    }

    // 排序语句
    if (order != null) {
      sql.append(" ");
      sql.append(order);
    }

    return sql.toString();
  }

  
/**
 * 构造SQL语句
 * @param attributes 属性名
 * @param tableName 表名
 * @return
 */
  private String constructQueryField(String[] attributes) {
    SqlBuilder sql = new SqlBuilder();
    for (String field : attributes) {
      String name = field;
      sql.append(name);
      sql.append(",");
    }
    sql.deleteLastChar();
    return sql.toString();
  }

 
  private E[] constructVO(String [] attributes, IRowSet rowset) {
    VORowSetMap<E> bo = new VORowSetMap<E>(this.voClass, attributes);
    return bo.convert(rowset);
  }



  /**
   * 根据传入的没有where关键字并以and开头的sql条件和排序语句查询vo
   * 
   * @param condition 查询条件 例如：and name='cc'
   * @param order 排序条件
   * @return 实体数据
   */
  public E[] query(String condition, String order) {

	 if(this.tableName == null ){
		 ExceptionUtils.wrappBusinessException("VOQuery : 查询表名不能为空~");
	 }
	 if(this.queryAttributes == null ){
		 ExceptionUtils.wrappBusinessException("VOQuery : 查询字段不能为空~");
	 }
    String sql =
        this.constructSQL(this.tableName, this.queryAttributes,null, condition, order);
   


    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
    E[] vos = this.constructVO(this.queryAttributes, rowset);
 
    return vos;
  }

  /**
   * 根据传入的没有where关键字并以and开头的sql条件和排序语句查询vo
   * 
   * @param condition 查询条件 例如：and name='cc'
   * @param order 排序条件
   * @return 实体数据
   */
  public E[] query(String [] fields ,String condition, String order) {
	  
	 this.queryAttributes = fields;
	 if(this.tableName == null ){
		 ExceptionUtils.wrappBusinessException("VOQuery : 查询表名不能为空~");
	 }
	 if(this.queryAttributes == null ){
		 ExceptionUtils.wrappBusinessException("VOQuery : 查询字段不能为空~");
	 }
    String sql =
        this.constructSQL(this.tableName, this.queryAttributes,null, condition, order);
   


    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
    E[] vos = this.constructVO(this.queryAttributes, rowset);
 
    return vos;
  }
  /**
   * 根据实体的主键查询VO
   * 
   * @param keys 主键数组
   * @return 实体数据
   */
  public E[] query(String[] keys, String[] fields) {
	  this.queryAttributes = fields;
	  //得到主键名称
     String primaryKeyField = this.entity.getPKFieldName();
     IDQueryCondition tool = new IDQueryCondition();
     //返回带有where语句的ID查询连接条件
     String idSQL = tool.build(this.tableName,primaryKeyField, keys);
     String sql = this.constructSQL(this.tableName,this.queryAttributes, idSQL, null, null);

    
     DataAccessUtils dao = new DataAccessUtils();
     IRowSet rowset = dao.query(sql);
     E[] vos = this.constructVO(this.queryAttributes, rowset);


     E[] returnVOs = this.sortByKey(keys, vos);
  

     return returnVOs;
  }
 

  
  /**
   * 根据VO的主键和其他没有where关键字并以and开头的sql条件查询VO
   * 
   * @param keys 主键数组
   * @param condition 查询条件 例如：and name='cc'
   * @return 实体数据
   */
  public E[] query(String[] keys, String condition) {
	  String primaryKeyField = this.entity.getPKFieldName();

 
    IDQueryCondition tool = new IDQueryCondition();
    String idSQL = tool.build(this.tableName,primaryKeyField, keys);
    String sql =
        this.constructSQL(this.tableName,this.queryAttributes, idSQL, condition, null);
   
    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
    E[] vos = this.constructVO(this.queryAttributes, rowset);
   
    E[] returnVOs = this.sortByKey(keys, vos);
  

    return returnVOs;
  }

  /**
   * 根据传入的有where关键字的sql条件和排序语句查询vo
   * 
   * @param whereSql 必须有where 关键字和临时表表名以及其他条件，唯一不需要 写的就是要查询的字段名、vo
   *          表名以及dr=0条件 例如：
   *          temptable temp where temp.id= ia_bill.cbillid
   * @param order 排序条件，前面没有and
   * @return 实体数据
   */
  public E[] queryWithWhereKeyWord(String whereSql, String order) {
    
    String sql = this.constructSQL(this.tableName,this.queryAttributes, whereSql, null, order);
   
    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
    E[] vos = this.constructVO(this.queryAttributes, rowset);
   

    return vos;
  }

  /**
   * 通過主鍵排序
   * @param keys
   * @param vos
   * @return
   */
  private E[] sortByKey(String[] keys, E[] vos) {
  
    Map<String, E> index = new HashMap<String, E>();
    for (E vo : vos) {
      index.put(vo.getPrimaryKey(), vo);
    }
    List<E> list = new ArrayList<E>();
    for (String key : keys) {
      if (!index.containsKey(key)) {
        continue;
      }
      list.add(index.get(key));
    }
    ListToArrayTool<E> tool = new ListToArrayTool<E>(this.voClass);
    E[] returnVOs = tool.convertToArray(list);
    return returnVOs;
  }
  
  //==========查询总条数===========
  
  /**
   * 根据传入的没有where关键字并以and开头的sql条件和排序语句查询vo
   * 
   * @param condition 查询条件 例如：and name='cc'
   * @param order 排序条件
   * @return 实体数据
   */
  public E[] pageQuery(String condition, String order,int begin,int end) {

    String sql =
        this.constructSQL(this.tableName, this.queryAttributes,null, condition, order);
   
    sql = SqlBuilder.constructPageSql(sql, begin, end);


    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
    E[] vos = this.constructVO(this.queryAttributes, rowset);
 
    return vos;
  }
  
  /**
   * 根据查询条件查询数据总条数
   * @param condition
   * @return
   */
  public int queryCount(String condition) {

	 int count  = 0;
    String sql =
        this.constructCountSQL(this.tableName, null, condition);
   


    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
   
    while(rowset.next()){
    	 count = rowset.getInt(0);
    }
   
    return count;
  }
  
  /**
   * 根据查询条件查询数据总条数
   * @param sql
   * @return
   */
  public int queryCountBySql(String sql) {

	int count  = 0;
    DataAccessUtils dao = new DataAccessUtils();
    IRowSet rowset = dao.query(sql);
   
    if(rowset.next()){
    	 count = rowset.getInt(0);//返回记录数
    }
   
    return count;
  }
  private String constructCountSQL(String tableName,
	      String wheresqlpart, String condition) {

	    SqlBuilder sql = new SqlBuilder();
	    sql.append(" select ");
	    // 要查询的字段
	    sql.append( " count(*) " );
	    sql.append(" from ");
	    
	    sql.append( tableName);

	    if (wheresqlpart == null) {
	      sql.append(" where ");
	    }
	    else {
	      sql.append(" ");
	      sql.append(wheresqlpart);
	      sql.append(" and ");
	    }
	    //拼接dr = 0
	    sql.append(tableName);
	    sql.append(".");
	    sql.append(" dr  = 0 ");
	    // 额外的条件
	    if (condition != null) {
	      sql.append(" and  ");
	      sql.append(condition);
	    }
	    return sql.toString();
	  }

  
     /**
      * 根据查询语句查询数据
      * @param querySql
      * @return
      */
     public E[] query(String querySql) {

		BaseDAO dao = new BaseDAO();
		try{
		List<E> obj = (List<E>) dao.executeQuery(querySql, new BeanListProcessor(this.voClass));
		E [] vos = null;
		if(CollectionUtil.isNotEmpty(obj)){
			int size =  obj.size();
			 vos = Constructor.construct(this.voClass, size);
			for(int i = 0 ; i < size ; i++){
				vos[i] = obj.get(i);
			}
		}
	    return vos;
			
		} catch (DAOException e) {
			e.printStackTrace();
			ExceptionUtils.wrappBusinessException("VOQuery ： 数据查询失败 ~ "+ querySql);		
		}
		return null;
     }
    
}
