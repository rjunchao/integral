package com.pub.xbkj.pubapp.query;

import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.SqlBuilder;

/**
 * 根据传入的ID来构造数据库单表的查询条件。 如果ID少，走in SQL 如果ID多，走临时表 ID中不能有空值
 * 
 */
public class TableIDQueryCondition {

  /**
   * 可以支持的临时表的ID的个数
   */
  private static final int MAX_FIELD_COUNT = 3;

  /**
   * ID数据集合
   */
  private List<String[]> idList;

  /**
   * 查询表表名
   */
  private String realTableName;
  /**
   * 临时表表名
   */
  private String tempTable;

  /**
   * 构造函数
   * 
   * @param list ID数据集合
   */
  public TableIDQueryCondition(List<String[]> list) {
    this.idList = list;
    int length = this.idList.get(0).length;
    if (length > IDQueryBuilder.getMaxInCount()) {
      this.tempTable = this.createTempTable(this.idList);
    }
  }

  /**
   * 构造函数
   * @param tableName 表面
   * @param ids 单列ID数组
   */
  public TableIDQueryCondition(String tableName,String[] ids) {
	this.realTableName = tableName;
    this.idList = new ArrayList<String[]>();
    this.idList.add(ids);
    int length = this.idList.get(0).length;
    if (length > IDQueryBuilder.getMaxInCount()) {//判断查询长度是否大于最大长度
      this.tempTable = this.createTempTable(this.idList);
    }
  }

  /**
   * 根据实体元数据属性创建ID查询条件 查询条件中带有where语句
   * 
   * @param fields 实体元数据属性
   * @return 查询条件
   */
  public String build(String[] fields) {
    int length = this.idList.get(0).length;
    int size = fields.length;
    
    String sql = null;
    if (length <= IDQueryBuilder.getMaxInCount()) {
      if (size == 1) {
        sql = this.buildInSql(this.idList, fields[0]);
      }
      else {
        sql = this.buildInSql(this.idList, fields);
      }
    }
    else {
      sql = this.buildTempTableSql(this.tempTable, fields);
    }
    return sql;
  }

  private String buildInSql(List<String[]> list, String field) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(" where ");
    sql.append(this.buildName(field), list.get(0));
    return sql.toString();
  }

  private String buildInSql(List<String[]> list, String[] fields) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(" where ");
    int length = list.get(0).length;

    int cursor = 0;
    final int andLength = 5;
    final int orLength = 3;
    for (int i = 0; i < length; i++) {
      sql.startParentheses();
      for (String field : fields) {
        String[] rows = list.get(cursor);
        sql.append(this.buildName(field), rows[i]);
        sql.append(" and ");
      }
      // 删除多余的and
      for (int j = 0; j < andLength; j++) {
        sql.deleteLastChar();
      }
      sql.endParentheses();
      sql.append(" or ");
    }
    // 删除多余的or
    for (int j = 0; j < orLength; j++) {
      sql.deleteLastChar();
    }
    return sql.toString();
  }

  private String buildName(String field) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(this.realTableName);
    sql.append(".");
    sql.append(field);
    return sql.toString();
  }

  private String buildTempTableSql(String table, String field) {
    SqlBuilder sql = new SqlBuilder();
    sql.append(" ,");
    sql.append(table);
    sql.append(" temp where ");
    sql.append(this.realTableName);
    sql.append(".");
    sql.append(field);
    sql.append(" = temp.id1 ");
    return sql.toString();
  }

  private String buildTempTableSql(String table, String[] fields) {
    String sql = null;
   
    sql = this.buildTempTableSql(table, fields[0]);
   
    return sql;
  }

  private String createTempTable(List<String[]> list) {
    TempTableDefine define = new TempTableDefine();
    String table = null;
    if (list.size() == 1) {
      String[] id = list.get(0);
      table = define.get(id);
    }
    else if (list.size() == 2) {
      String[] id1 = list.get(0);
      String[] id2 = list.get(1);
      table = define.get(id1, id2);
    }
    else if (list.size() == TableIDQueryCondition.MAX_FIELD_COUNT) {
      String[] id1 = list.get(0);
      String[] id2 = list.get(1);
      String[] id3 = list.get(2);
      table = define.get(id1, id2, id3);
    }
    return table;
  }

}
