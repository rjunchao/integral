package com.pub.xbkj.pubapp.query;


/**
 * 根据传入的ID来构造数据库单表的查询条件。 如果ID少，走in SQL 如果ID多，走临时表 ID中不能有空值
 * 
 */
public class IDQueryCondition {

  /**
   * 根据实体的主键创建ID查询条件 查询条件中带有where语句
   * 
   * @param fieldMeta 主键字段名
   * @param keys 查询的主键
   * @return String 带有where语句的ID查询连接条件
   */
  public String build(String tableName,String fieldMeta, String[] keys) {
    String[] fields = new String[1];
    fields[0] = fieldMeta;
    TableIDQueryCondition condition = new TableIDQueryCondition(tableName,keys);
    return condition.build(fields);
  }

}
