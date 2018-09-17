package com.pub.xbkj.pubapp.delete;

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
 * 删除表记录
 * 
 */
public class DeleteTable extends AbstractTableOperator {

  /**
   * 根据实体和对应的数据库表元数据删除表中的记录
   * 
   * @param vos 实体
   * @param table 数据库表元数据
   */
  public void delete(SuperVO[] vos) {
    String tableName = vos[0].getTableName();
    String primaryKeyField = vos[0].getPKFieldName();
    String sql = this.constructSQL(tableName,primaryKeyField);
    
    JavaType[] types = new JavaType[]{JavaType.String,JavaType.String};
    List<List<Object>> data = this.constuctData(vos);
    DataAccessUtils dao = new DataAccessUtils(false);
    dao.update(sql, types, data);
  }

 

  private String constructSQL(String tableName,String primarkKeyField) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(" update ");
    sql.append(tableName);
    sql.append(" set ");
    sql.append(ElementConstant.KEY_DR, 1);
    sql.append(",");
    sql.append(ElementConstant.KEY_TS);
    sql.append(" = ? ");
    sql.append(" where ");
    sql.append(primarkKeyField);
    sql.append(" = ? ");
    return sql.toString();
  }

  private List<List<Object>> constuctData(SuperVO[] vos) {
    String nowTS = DateUtil.getCurrDateTime();
    List<List<Object>> data = new ArrayList<List<Object>>();
    String primaryKeyField = vos[0].getPKFieldName();
   
    for (SuperVO vo : vos) {
      // 更新VO中的时间戳
      vo.setAttributeValue(ElementConstant.KEY_TS, nowTS);
      List<Object> row = new ArrayList<Object>();
      data.add(row);
      this.fillTSData(row, nowTS);
      this.fillData(row, vo, primaryKeyField, JavaType.String);
    }
    return data;
  }

}
