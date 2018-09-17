package com.pub.xbkj.pubapp.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.pub.xbkj.pubapp.generator.SequenceGenerator;
import com.xbkj.common.jdbc.framework.ConnectionFactory;
import com.xbkj.common.jdbc.framework.DataSourceCenter;



/**
 * 数据库的访问工具
 * 
 */
public class DBTool {

  private static Logger log = TraceLoggerFactory.getLogger(DBTool.class);
  
  
  /**
   * 关闭连接
   * @param connection
   * @param stmt
   * @param rs
   */
  public  static void closeConn(Connection connection) {
   
    if (connection != null) {
      try {
        connection.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
  }
  
  /**
   * 关闭连接
   * @param connection
   * @param stmt
   * @param rs
   */
  public  static void closeStmt(Statement stmt) {
   
    if (stmt != null) {
      try {
        stmt.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
   
  }

  /**
   * 关闭连接
   * @param connection
   * @param stmt
   * @param rs
   */
  public  static void closeRs(ResultSet rs) {
   
    if (rs != null) {
      try {
        rs.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
  }
  /**
   * 关闭连接
   * @param connection
   * @param stmt
   * @param rs
   */
  public  static void closeDB(Connection connection, Statement stmt, ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
    if (stmt != null) {
      try {
        stmt.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
    if (connection != null) {
      try {
        connection.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
  }

  /**
   * 关闭连接
   * @param connection
   * @param stmt
   * @param rs
   */
  public  static void closeDB(Connection connection, Statement stmt){
    
    if (stmt != null) {
      try {
        stmt.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
    if (connection != null) {
      try {
        connection.close();
      }
      catch (SQLException ex) {
    	  log.error(ex);
      }
    }
  
  }
  /**
   * 获取数据库连接
   * 
   * @return 数据库连接
   */
  public Connection getConnection() {
    String datasource = this.getDataSourceName();
    Connection connection = null;
    try {
      connection = ConnectionFactory.getConnection(datasource);
    }
    catch (SQLException ex) {
      ExceptionUtils.wrappException(ex);
    }
    return connection;
  }

  /**
   * 获取数据源名称
   * 
   * @return 数据源名称
   */
  public String getDataSourceName() {
    String name = DataSourceCenter.getInstance().getSourceName();
    return name;
  }

  /**
   * 获取数据库类型
   * 
   * @return 数据库类型
   * @see nc.jdbc.framework.util.DBConsts
   */
  public int getDBType() {
    String datasource = this.getDataSourceName();
    int type = DataSourceCenter.getInstance().getDatabaseType(datasource);
    return type;
  }
  
  /**
   * 分配指定数量的ID
   * 
   * @param size 要分配ID的数量
   * @return 分配的ID
   */
  public String[] getOIDs(int size) {
    //String datasource = this.getDataSourceName();
    SequenceGenerator bo = new SequenceGenerator();
    String[] oids = bo.generate2( size);
    return oids;
  }
}
