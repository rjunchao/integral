package com.pub.xbkj.pubapp;

import com.pub.xbkj.pubapp.exception.ExceptionUtils;



/**
 * sql语句构造器。方便拼写sql，减少不比要的错误
 * 
 * @since 6.0
 * @version 2006-6-20 18:50:23
 * 
 */
public class SqlBuilder {

  /**
   * 存放拼写sql的StringBuffer
   */
  private StringBuffer buffer = new StringBuffer();

  /**
   * 将一个非空的对象转化为String拼写入sql语句
   * 
   * @param obj 非空的对象
   */
  public void append(Object obj) {
    this.buffer.append(obj.toString());
  }

  /**
   * 将一个字符串拼写入sql语句
   * 
   * @param str 字符串
   */
  public void append(String str) {
    this.buffer.append(str);
  }

  /**
   * 对于整数值构造“等于”条件
   * 
   * @param name sql字段名
   * @param value int值
   */
  public void append(String name, int value) {
    this.buffer.append(name);
    this.buffer.append("=");
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于int数组值构造in条件
   * 
   * @param name sql字段名
   * @param values int数组值
   */
  public void append(String name, int[] values) {
    this.buffer.append(name);
    this.buffer.append(" in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 对于Integer值构造“等于”条件
   * 
   * @param name sql字段名
   * @param value Integer值
   */
  public void append(String name, Integer value) {
    this.buffer.append(name);
    this.buffer.append("=");
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于Integer数组值构造in条件
   * 
   * @param name sql字段名
   * @param values Integer数组值
   */
  public void append(String name, Integer[] values) {
    this.buffer.append(name);
    this.buffer.append(" in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

 

  /**
   * 对于字符串值构造“等于”条件
   * 
   * @param name sql字段名
   * @param value String值 不能为空，否则抛异常。因为不知道是否要添加~
   */
  public void append(String name, String value) {
    if (value != null) {
      this.buffer.append(name);
      this.buffer.append("='");
      this.buffer.append(value);
      this.buffer.append("' ");
    }
    else {
      ExceptionUtils.unSupported();
    }
  }

  /**
   * 对于整数值构造operator所指定的条件
   * 
   * @param name sql字段名
   * @param operator sql操作符
   * @param value int值
   */
  public void append(String name, String operator, int value) {
    this.buffer.append(name);
    this.buffer.append(operator);
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于Integer值构造operator所指定的条件
   * 
   * @param name sql字段名
   * @param operator sql操作符
   * @param value Integer值
   */
  public void append(String name, String operator, Integer value) {
    this.buffer.append(name);
    this.buffer.append(operator);
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于字符串值构造operator所指定的条件
   * 
   * @param name sql字段名
   * @param operator sql操作符
   * @param value String值
   */
  public void append(String name, String operator, String value) {
    this.buffer.append(name);
    this.buffer.append(operator);
    this.buffer.append(" '");
    this.buffer.append(value);
    this.buffer.append("' ");
  }

  /**
   * 对于UFDouble值构造operator所指定的条件
   * 
   * @param name sql字段名
   * @param operator sql操作符
   * @param value UFDouble值
   */
  public void append(String name, String operator, Double value) {
    this.buffer.append(name);
    this.buffer.append(operator);
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于String数组值构造in条件
   * 
   * @param name sql字段名
   * @param values String数组值
   */
  public void append(String name, String[] values) {
    int length = values.length;
    if (length == 1) {
      this.append(name, values[0]);
      return;
    }
    this.buffer.append(name);
    this.buffer.append(" in (");
    for (int i = 0; i < length; i++) {
      this.buffer.append("'");
      this.buffer.append(values[i]);
      this.buffer.append("'");
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 对于Boolean值构造“等于”条件,true转换为Y,false 转化为N
   * 
   * @param name sql字段名
   * @param value Boolean值
   */
  public void append(String name, Boolean value) {
    this.buffer.append(name);
    this.buffer.append("='");
    if(value){
    	 this.buffer.append("Y");
    }
    else{
    	 this.buffer.append("N");
    }
    this.buffer.append("' ");
  }

  /**
   * 对于UFDouble值构造“等于”条件
   * 
   * @param name sql字段名
   * @param value UFDouble值
   */
  public void append(String name, Double value) {
    this.buffer.append(name);
    this.buffer.append("=");
    this.buffer.append(value);
    this.buffer.append(" ");
  }

  /**
   * 对于UFDouble数组值构造in条件
   * 
   * @param name sql字段名
   * @param values UFDouble数组值
   */
  public void append(String name, Double[] values) {
    this.buffer.append(name);
    this.buffer.append(" in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 构造case when sql语句
   * 
   * @param condition 条件语句
   * @param trueExpression 为真时的语句
   * @param falseExpression 为假时的语句
   */
  public void appendCaseWhen(String condition, String trueExpression,
      String falseExpression) {
    this.buffer.append(" case when ");
    this.buffer.append(condition);
    this.buffer.append(" then ");
    this.buffer.append(trueExpression);
    this.buffer.append(" else ");
    this.buffer.append(falseExpression);
    this.buffer.append(" end ");
  }

  /**
   * 对于ID字段，例如：varchar(20)、varchar(36)、varchar(101)，将is not null表达式拚写为=~
   * 
   * @param name 字段名称
   */
  public void appendIDIsNotNull(String name) {
    this.buffer.append(name);
    this.buffer.append("<>'~' ");
  }

  /**
   * 对于ID字段，例如：varchar(20)、varchar(36)、varchar(101)，将is null表达式拚写为=~
   * 
   * @param name 字段名称
   */
  public void appendIDIsNull(String name) {
    this.buffer.append(name);
    this.buffer.append("='~' ");
  }

  /**
   * 对于int数组值构造not in条件
   * 
   * @param name sql字段名
   * @param values int数组值
   */
  public void appendNot(String name, int[] values) {
    this.buffer.append(name);
    this.buffer.append(" not in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 对于Integer数组值构造not in条件
   * 
   * @param name sql字段名
   * @param values Integer数组值
   */
  public void appendNot(String name, Integer[] values) {
    this.buffer.append(name);
    this.buffer.append(" not in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  

  /**
   * 对于String数组值构造not in条件
   * 
   * @param name sql字段名
   * @param values String数组值
   */
  public void appendNot(String name, String[] values) {
    this.buffer.append(name);
    this.buffer.append(" not in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append("'");
      this.buffer.append(values[i]);
      this.buffer.append("'");
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 对于UFDouble数组值构造not in条件
   * 
   * @param name sql字段名
   * @param values UFDouble[]数组值
   */
  public void appendNot(String name, Double[] values) {
    this.buffer.append(name);
    this.buffer.append(" not in (");
    int length = values.length;
    for (int i = 0; i < length; i++) {
      this.buffer.append(values[i]);
      this.buffer.append(",");
    }
    length = this.buffer.length();
    this.buffer.deleteCharAt(length - 1);
    this.buffer.append(") ");
  }

  /**
   * 对于数字类型的字段需要判断 is null的时候，调用此方法
   * 
   * @param name 字段名称
   */
  public void appendNumberIsNull(String name) {
    this.buffer.append(name);
    // 暂时如此处理
    this.buffer.append(" is null ");
  }

  /**
   * 拼写数值取精度的sql
   * 
   * @param expression 数值表达式
   * @param precision 精度
   */
  public void appendRound(String expression, int precision) {
    this.buffer.append(" round ( ");
    this.buffer.append(expression);
    this.buffer.append(",");
    this.buffer.append(precision);
    this.buffer.append(" ) ");
  }

  /**
   * 拼写数值取精度的sql
   * 
   * @param expression 数值表达式
   * @param precision 精度
   */
  public void appendRound(String expression, Integer precision) {
    this.buffer.append(" round ( ");
    this.buffer.append(expression);
    this.buffer.append(",");
    this.buffer.append(precision);
    this.buffer.append(" ) ");
  }

  /**
   * 删除最后一个字符
   */
  public void deleteLastChar() {
    this.buffer.deleteCharAt(this.buffer.length() - 1);
  }

  /**
   * 拼写）号
   */
  public void endParentheses() {
    this.buffer.append(" ) ");
  }

  /**
   * 重新设置sql，将前面拼写的sql清空
   */
  public void reset() {
    this.buffer.setLength(0);
  }

  /**
   * 拼写(号
   */
  public void startParentheses() {
    this.buffer.append(" ( ");
  }

  @Override
  public String toString() {
    return this.buffer.toString();
  }
  /**
   * 构造分页SQL
   * @param sql 未分頁SQL
   * @param begin 开始位置
   * @param end 结束位置
   */
  public static String constructPageSql(String sql,int begin,int end){
	 // oracle 分页查询
	 // String pageSql = "select * from (select s1.*,rownum row_num from (" +sql+") s1 ) s2 where s2.row_num between "+begin+" and "+end+" ";
	 /*
	  * 这里临时使用MySQL的分页查询
	  */
	  String pageSql = sql + " LIMIT " + begin + ", "  + end;
	  return pageSql;
  }
}
