package com.pub.xbkj.pubapp.exception;

import java.sql.SQLException;

import com.xbkj.common.bs.dao.DAOException;


/**
 * sql传递性异常。用来传递错误sql信息到前台的异常
 * 
 * @since 6.0
 * @version 2009-5-12 下午11:07:51
 */
public class TransferSqlException extends TransferException {
  private static final long serialVersionUID = -4792664149143610434L;

  /**
   * 执行错误的sql语句
   */
  private final String sql;

  /**
   * sql传递性异常的构造函数
   * 
   * @param exception 底层异常
   * @param sql 引发异常的sql语句
   */
  public TransferSqlException(DAOException exception, String sql) {
    super(exception);
    this.sql = sql;
  }

  /**
   * sql传递性异常的构造函数
   * 
   * @param exception 底层异常
   * @param sql 引发异常的sql语句
   */
  public TransferSqlException(SQLException exception, String sql) {
    super(exception);
    this.sql = sql;
  }

  /**
   * 获取引发异常的sql语句
   * 
   * @return sql语句
   */
  public String getSql() {
    return this.sql;
  }
}
