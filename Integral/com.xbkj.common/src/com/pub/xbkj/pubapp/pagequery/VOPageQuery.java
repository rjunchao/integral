package com.pub.xbkj.pubapp.pagequery;

import java.util.List;

import com.eos.foundation.PageCond;
import com.pub.xbkj.pubapp.Constructor;
import com.pub.xbkj.pubapp.SqlBuilder;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.pub.xbkj.pubapp.query.VOQuery;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.bs.dao.BaseDAO;
import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.processor.BeanListProcessor;
import com.xbkj.common.util.ArrayUtil;
import com.xbkj.common.util.CollectionUtil;

/**
 *@author lijbe
 *@date 2016年6月22日00:54:34
 *@version 1.0.0
 *分页查询工具类,适配普元平台分页工具类，真正查询使用自己的工具类，
 *分页使用普元平台方法，减少前端工作量
 *
 */
public class VOPageQuery<E extends SuperVO> {
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
	  public VOPageQuery(Class<E> voClass) {
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
	  public VOPageQuery(Class<E> voClass, String tableName ,String[] fields) {
	    this.voClass = voClass;
	    this.tableName = tableName;
	    this.queryAttributes = fields;
	   
	  }
	  
	  /**
	   * 分页查询
	   * @param condition
	   * @param order
	   * @param page
	   * @return
	   */
	  public E[] query(String condition, String order,String [] fields ,PageCond page) {

		    this.queryAttributes  = fields;
		    if(ArrayUtil.isEmpty(fields)){
			    ExceptionUtils.wrappBusinessException("查询字段不能为空~");
		    }
		     
		     int count =0;
		     VOQuery<E> query = new VOQuery<E>(this.voClass,this.tableName,this.queryAttributes);
		    count = query.queryCount(condition);
			if(count < 1){
				return null;
			}
			//每页显示条数，默认20条
			int length = page.getLength();
			if(length == 0){
				length = 20;
			}
			
			page.setCount(count);
			page.setTotalPage(count/length);
			
			int begin = page.getBegin();
			int end = begin + length;
			//从下一条开始查询
			if(begin >  0 ){
				begin += 1;
			}
			
			E [] vos = query.pageQuery(condition, order, begin, end);
		    return vos;
     }
	  
	  /**
	   * 使用SQL查询数据，进行分页查询
	   * @param querySql 查询条件
	   * @param queryCountSql 总条数查询条件
	   * @param page 分页信息
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	public E[] query(String querySql ,String queryCountSql,PageCond page) {

		  
		  int count =0;
		  VOQuery<E> query = new VOQuery<E>(this.voClass);
		  count = query.queryCountBySql(queryCountSql);
		     
	  if(count < 1){
			return null;
		}
		//每页显示条数，默认20条
		int length = page.getLength();
		if(length == 0){
			length = 20;
		}
		
		page.setCount(count);
		page.setTotalPage(count/length);
		
		int begin = page.getBegin();
		int end = begin + length;
		//从下一条开始查询
		/*if(begin >  0 ){
			begin += 1;
		}*/
		//构造分页查询SQL
		querySql = SqlBuilder.constructPageSql(querySql, begin, end);
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
			ExceptionUtils.wrappBusinessException("VOPageQuery ： 数据查询失败 ~ "+ querySql);		
		}
		return null;
   }
	 
}
