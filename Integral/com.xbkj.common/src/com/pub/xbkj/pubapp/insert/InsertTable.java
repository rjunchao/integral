package com.pub.xbkj.pubapp.insert;

import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.ElementConstant;
import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.SqlBuilder;
import com.pub.xbkj.pubapp.database.AbstractTableOperator;
import com.pub.xbkj.pubapp.query.DataAccessUtils;
import com.xbkj.basic.vo.pub.SuperVO;
import com.xbkj.common.util.DateUtil;


/**
 * 向数据库表中插入数据
 * 
 */
public class InsertTable extends AbstractTableOperator {


	/**
	 * 是否自动设置TS
	 */
	private boolean autoTs = true;
	public InsertTable(boolean autoTs ){
		this.autoTs = autoTs;
	}
  /**
   * 根据数据库表元数据将实体数据插入到数据库中
   * 
   * @param vos 实体数据
   * @param tableName 数据库表名
   * @param fields 保存的字段
   * @param types对应的JavaType
   */
  public void insert(SuperVO[] vos,String [] fields,JavaType [] types) {
 
	 String tableName = vos[0].getTableName();
   
	 String sql = this.constructSQL(tableName,fields);//构建sql insert into () values(?.....);
   
    List<List<Object>> data = this.constuctData(vos, fields,types);
    DataAccessUtils dao = new DataAccessUtils(false);
    dao.update(sql, types, data);
  }

  private String constructSQL(String table,String [] fields) {
    SqlBuilder sql = new SqlBuilder();

   
    sql.append(" insert into ");
    sql.append(table);
    sql.startParentheses();//添加左括号 (
    int length = fields.length;
    for (int i = 0; i < length; i++) {//循环添加属性
      sql.append(fields[i]);
      sql.append(",");
    }
    sql.append(ElementConstant.KEY_DR);//删除标志位 dr
    sql.append(" ,");
    if(this.autoTs){
    	 sql.append(ElementConstant.KEY_TS);//提ts
    	 sql.endParentheses();
    }else{
    	sql.deleteLastChar();//删除最后一个字符，一般是逗号
    	sql.endParentheses();//添加后括号
    }
   
    
  
    sql.append(" values ");
    sql.startParentheses();
    for (int i = 0; i < length; i++) {
      sql.append("?");
      sql.append(",");
    }
   
    sql.append("0,");
    if(this.autoTs){
    	 sql.append("?");
   }else{
   		sql.deleteLastChar();
   }
    sql.endParentheses();//添加最后一个括号
  

    return sql.toString();
  }

  private List<List<Object>> constuctData(SuperVO[] vos,String [] fields,JavaType [] types) {
    String nowTs = DateUtil.getCurrDateTime();
    List<List<Object>> data = new ArrayList<List<Object>>();
   
    for (SuperVO vo : vos) {
      vo.setAttributeValue(ElementConstant.KEY_TS, nowTs);
      List<Object> row = new ArrayList<Object>();
      data.add(row);
     
      for (int i = 0,len = fields.length ; i < len ;i++) {
        this.fillData(row, vo, fields[i],types[i]);
      }
      if(this.autoTs){
    	  this.fillTSData(row, nowTs);
      }
     
    }
    return data;
  }

}
