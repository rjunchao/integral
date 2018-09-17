package com.pub.xbkj.pubapp.delete;

import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.SqlBuilder;
import com.pub.xbkj.pubapp.database.AbstractTableOperator;
import com.pub.xbkj.pubapp.query.DataAccessUtils;
import com.xbkj.basic.vo.pub.SuperVO;

/**
 * 从数据库中真正的删除表记录，而不是仅仅打上DR标志
 * 
 */
public class RealDeleteTable extends AbstractTableOperator {

  /**
   * 根据数据库表元数据物理删除表中存放的实体数据
   * 
   * @param vos 实体数据
   * @param table 数据库表元数据
   */
  public void delete(SuperVO[] vos, String tableName) {
  
	String primaryKeyField = vos[0].getPKFieldName();
    String sql = this.constructSQL(tableName,primaryKeyField);

    
    List<List<Object>> data = this.constuctData(vos);
    JavaType [] types = new JavaType[]{JavaType.String};
    DataAccessUtils dao = new DataAccessUtils(false);
    dao.update(sql, types, data);
  }

  

  private String constructSQL(String tableName,String primaryKeyField) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(" delete from ");
    sql.append(tableName);
    sql.append(" where ");
   
    sql.append(primaryKeyField);
    sql.append(" = ? ");
    return sql.toString();
  }

  private List<List<Object>> constuctData(SuperVO[] vos) {
    List<List<Object>> data = new ArrayList<List<Object>>();
    
   String primaryKeyField = vos[0].getPKFieldName();

    for (SuperVO vo : vos) {
      List<Object> row = new ArrayList<Object>();
      data.add(row);
      this.fillData(row, vo, primaryKeyField, JavaType.String);
    }
    return data;
  }

}
