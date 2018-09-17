package com.pub.xbkj.pubapp.query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.data.IRowSet;
import com.pub.xbkj.pubapp.data.RowSet;
import com.pub.xbkj.pubapp.data.ValueUtils;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.pub.xbkj.pubapp.exception.TransferSqlException;



/**
 * 数据访问工具类。将数据库访问操作封装起来
 * 
 */
public class DataAccessUtils {

  /**
   * 从数据库中能够读取的最大纪录数。-1表示不限制纪录的读取
   */
  private static final int MAX_ROWS = -1;

  /**
   * sql语句更新数据库时是否自动增加时间戳
   */
  private boolean autoSetTS = true;

  /**
   * sql语句查询数据库时最大可返回的纪录行
   */
  private int maxRows = DataAccessUtils.MAX_ROWS;

  /**
   * 数据访问工具类默认构造函数
   */
  public DataAccessUtils() {
    // 默认构造函数
  }

  /**
   * 设置更新数据库的时候是否自动在sql语句中刷新时间戳。为否时，更新数据库的sql不会自动拼写更新时间戳的语句
   * 
   * @param autoSetTS 是否自动刷新时间戳
   */
  public DataAccessUtils(boolean autoSetTS) {
    this.autoSetTS = autoSetTS;
  }

  /**
   * 根据传入的sql语句查询数据库，返回离线的数据集
   * 
   * @param sql 查询sql语句
   * @return 离线的数据集
   */
  public IRowSet query(String sql) {
    DBTool tool = new DBTool();
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Object[]> list = new ArrayList<Object[]>();
    int count = -1;
    int rowcount = 0;
    try {
      connection = tool.getConnection();
      stmt = connection.createStatement();

      // 设置结果集
      if (this.maxRows> 0) {
    	  stmt.setMaxRows(this.maxRows);
      }else {
    	  stmt.setMaxRows(0);
      }

      rs = stmt.executeQuery(sql);
      //得到列数
      count = rs.getMetaData().getColumnCount();
      String type = null;
      while (rs.next()) {//遍历查询结果
        Object[] rows = new Object[count];
        for (int i = 0; i < count; i++) {
          rows[i] = rs.getObject(i + 1);
         type = rs.getMetaData().getColumnTypeName(i + 1);
        }
        //添加到集合
        list.add(rows);
        rowcount++;//统计个数
        if (this.maxRows != DataAccessUtils.MAX_ROWS
            && rowcount >= this.maxRows) {
          break;
        }
      }
    }
    catch (SQLException ex) {
      TransferSqlException e = new TransferSqlException(ex, sql);
      ExceptionUtils.wrappException(e);
    }
    finally {
      this.closeDB(connection, stmt, rs);
    }
    int size = list.size();
    //size是有多少个对象，count是每个对象中有多少列
    Object[][] data = new Object[size][count];
    data = list.toArray(data);
    IRowSet rowSet = new RowSet(data);
    return rowSet;
  }

  /**
   * 设置当前可以从数据库中返回的最大纪录数。-1表示不限制读取的数量
   * 
   * @param maxRows 当前可以从数据库中返回的最大纪录数
   */
  public void setMaxRows(int maxRows) {
    this.maxRows = maxRows;
  }

  /**
   * 更新数据库
   * 
   * @param sql 更新数据库sql
   * @return 更新的纪录数
   */
  public int update(String sql) {
    DBTool tool = new DBTool();
    Connection connection = null;
    Statement stmt = null;
    int result = -1;
    try {
      connection = tool.getConnection();
      stmt = connection.createStatement();
      result = stmt.executeUpdate(sql);
    }
    catch (SQLException ex) {
      TransferSqlException e = new TransferSqlException(ex, sql);
      ExceptionUtils.wrappException(e);
    }
    finally {
     DBTool.closeDB(connection, stmt);
    }
    return result;
  }


  private void closeDB(Connection connection, Statement stmt, ResultSet rs) {
      DBTool.closeDB(connection, stmt, rs);
  }

  /**
   * 用参数sql语句更新数据库
   * 
   * @param sql 参数化的更新sql语句
   * @param types 参数类型
   * @param data 参数值列表
   */
  public void update(String sql, JavaType[] types, List<List<Object>> data) {
    DBTool tool = new DBTool();
    Connection connection = null;
    PreparedStatement stmt = null;
    try {
      connection =  tool.getConnection();
      stmt = connection.prepareStatement(sql);
      this.boundleValue(stmt, types, data);//绑定值
      stmt.executeBatch();
    }
    catch (SQLException ex) {
      TransferSqlException e = new TransferSqlException(ex, sql);
      ExceptionUtils.wrappException(e);
    }
    finally {
       DBTool.closeDB(connection, stmt);
    }
    }
  

  private void boundleValue(PreparedStatement stmt, JavaType[] types,
      List<List<Object>> data) {
    int length = types.length;
    try {
      for (List<Object> row : data) {
        for (int i = 0; i < length; i++) {
          Object value = row.get(i);
          this.boundleValue(stmt, i + 1, types[i], value);
        }
        stmt.addBatch();
      }
    }
    catch (SQLException ex) {
      ExceptionUtils.wrappException(ex);
    }
  }
  

  private void boundleValue(PreparedStatement stmt, int index, JavaType type,
      Object value) throws SQLException {
    if ((type == JavaType.Double) || (type == JavaType.BigDecimal)) {
      if (value == null) {
        stmt.setNull(index, Types.NUMERIC);
      }
      else {
        BigDecimal ret = ValueUtils.getBigDecimal(value);
        stmt.setBigDecimal(index, ret);
      }
    }
    else if (type == JavaType.String) {
      this.boundleStringTypeValue(stmt, index, value);
    }
    else if (type == JavaType.Date) {
      this.boundleStringTypeValue(stmt, index, value);
    }
    else if (type == JavaType.Integer) {
      if (value == null) {
        stmt.setNull(index, Types.INTEGER);
      }
      else {
        int ret = ValueUtils.getInt(value);
        stmt.setInt(index, ret);
      }
    }
    else if (type == JavaType.Boolean) {
      Boolean ret = ValueUtils.getBoolean(value);
      stmt.setString(index, ret.booleanValue() ? "Y" : "N");
    }
   
    else if (type == JavaType.Object) {
        if (value == null) {
          stmt.setNull(index, Types.BLOB);
        } else {
          stmt.setObject(index, value);  
        }
      }
    else {
      ExceptionUtils.unSupported();
    }
  }
  
  

  private void boundleStringTypeValue(PreparedStatement stmt, int index,
      Object value) throws SQLException {
    if (value == null) {
      stmt.setNull(index, Types.CHAR);
    }
    else {
      String ret = ValueUtils.getString(value);
      stmt.setString(index, ret);
    }
  }
  
   
}
