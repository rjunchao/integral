package com.pub.xbkj.pubapp.update;

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
 * 更新表的字段
 * 
 */
public class UpdateTable extends AbstractTableOperator {

 private boolean autoTs = true;

  public UpdateTable(boolean autoTs){
	  this.autoTs = autoTs;
  }
  /**
   * 
   * @param vos
   * @param tableName
   * @param fields
   */
  public void update(SuperVO[] vos,String [] fields,JavaType [] types) {
    

	String prmaryKeyField = vos[0].getPKFieldName();
	String tableName = vos[0].getTableName();
    String sql = this.constructSQL(tableName,prmaryKeyField,fields);

    List<List<Object>> data = this.constuctData(vos, fields,types);

    DataAccessUtils dao = new DataAccessUtils(false);
    dao.update(sql, types, data);
  }


  /**
   * 构造Update语句
   * @param tableName
   * @param primaryKeyField
   * @param fields
   * @return
   */
  private String constructSQL(String tableName ,String primaryKeyField,String [] fields) {
   
    SqlBuilder sql = new SqlBuilder();
    sql.append(" update ");
    sql.append(tableName);
    sql.append(" set ");
    for (String field : fields) {
      sql.append(field);
      sql.append(" = ?");
      sql.append(",");
    }
    //自动添加TS字段
    if(this.autoTs){
    	 sql.append(ElementConstant.KEY_TS);
    	 sql.append(" = ? ");
    }else{
    	sql.deleteLastChar();
    }
   

    sql.append(" where ");
  
    sql.append(primaryKeyField);
    sql.append(" = ? ");
    return sql.toString();
  }

  private List<List<Object>> constuctData(SuperVO[] vos, String[] fields,JavaType [] types) {
    String nowTS = DateUtil.getCurrDateTime();
    String primaryKeyField = vos[0].getPKFieldName();
    List<List<Object>> data = new ArrayList<List<Object>>();
    for (SuperVO vo : vos) {
      vo.setAttributeValue(ElementConstant.KEY_TS, nowTS);
      List<Object> row = new ArrayList<Object>();
      data.add(row);
     
      for (int i = 0, len = fields.length ; i < len ; i++) {
        this.fillData(row, vo, fields[i], types[i]);
      }
      //更新TS
      if(this.autoTs){
    	  this.fillTSData(row, nowTS);
      }
     
      //增加主键条件
      this.fillData(row, vo, primaryKeyField, JavaType.String);
    }
    return data;
  }

}
