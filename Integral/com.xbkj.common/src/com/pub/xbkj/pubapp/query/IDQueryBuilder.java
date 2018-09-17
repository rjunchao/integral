package com.pub.xbkj.pubapp.query;

import com.pub.xbkj.pubapp.SqlBuilder;

/**
 * 根据传入的ID的数量构造查询sql条件。 如果ID少，走in SQL 如果ID多，走临时表 ID中不能有空值
 * 
 */
public class IDQueryBuilder {
  /**
   * 采用in sql的最大的参数长度
   */
  private static final int MAX_IN_COUNT = 500;

  /**
   * 获取in sql的最大的参数长度
   * 
   * @return in sql的最大的参数长度
   */
  public static int getMaxInCount() {
    return IDQueryBuilder.MAX_IN_COUNT;
  }

  /**
   * 根据传入的ID值构造查询条件。传入的ID的值不能 重复，也不能为空。
   * <p>
   * 在我们的程序中，常常会出现要查询两组ID。此时，只有一个临时表是不够用的。 这里再默认一个新的临时表以便业
   * 务代码使用.
   * 
   * @param name sql字段名
   * @param ids 要查询的ID数组
   * @return 返回的查询条件，不会以 and开始
   */
  public String buildAnotherSQL(String name, String[] ids) {
    SqlBuilder sql = new SqlBuilder();
    int length = ids.length;
    if (length <= IDQueryBuilder.MAX_IN_COUNT) {
      sql.append(name, ids);
    }
    else {
      sql.append(name);
      sql.append(" in ");
      sql.startParentheses();
      sql.append(" select id1 from ");
      TempTableDefine define = new TempTableDefine();
      String temptable = define.getAnother(ids);
      sql.append(temptable);
      sql.endParentheses();
    }
    return sql.toString();
  }

  /**
   * 根据传入的ID值构造查询条件，传入的ID的值不能重复，也不能为空
   * 
   * @param name sql字段名
   * @param ids 要查询的ID数组
   * @return 返回的查询条件，不会以 and开始
   */
  public String buildSQL(String name, String[] ids) {
    SqlBuilder sql = new SqlBuilder();
    int length = ids.length;
    if (length <= IDQueryBuilder.MAX_IN_COUNT) {
      sql.append(name, ids);
    }
    else {
      sql.append(name);
      sql.append(" in ");
      sql.startParentheses();
      sql.append(" select id1 from ");
      TempTableDefine define = new TempTableDefine();
      String temptable = define.get(ids);
      sql.append(temptable);
      sql.endParentheses();
    }
    return sql.toString();
  }

}
