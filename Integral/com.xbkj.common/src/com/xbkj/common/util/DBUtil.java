package com.xbkj.common.util;

import com.eos.common.connection.ConnectionHelper;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import commonj.sdo.DataObject;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil
{
  public static final int INSERT_BATCH_SIZE = 5000;
  private static final Logger logger = TraceLoggerFactory.getLogger(DBUtil.class);

  public static boolean update(String dsName, String sql)
  {
    if (ToolUtil.isNullStr(dsName)) {
      dsName = "default";
    }
    Connection conn = ConnectionHelper.getCurrentContributionConnection(dsName);
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
    } catch (SQLException e) {
      logger.error(e.getMessage());
      return false;
    } finally {
      close_res_stmt_conn(null, stmt, conn);
    }
    return true;
  }

  public static String buildInCond(DataObject[] dataArr, String fieldColum)
  {
    StringBuffer sql = new StringBuffer(128);
    int len = dataArr.length;
    for (int i = 0; i < len; i++) {
      sql.append("'").append(dataArr[i].get(fieldColum)).append("'");
      if ((len > 1) && (i < len - 1)) {
        sql.append(",");
      }
    }
    return sql.toString();
  }

  public static void setPstmtIntVal(PreparedStatement pstmt, int index, Integer val)
    throws SQLException
  {
    if (ToolUtil.isNull(val))
      pstmt.setNull(index, 4);
    else
      pstmt.setInt(index, val.intValue());
  }

  public static void setPstmtDoubleVal(PreparedStatement pstmt, int index, Double val) throws SQLException
  {
    if (ToolUtil.isNull(val))
      pstmt.setNull(index, 8);
    else
      pstmt.setDouble(index, val.doubleValue());
  }

  public static Double getDou(ResultSet rs, String rsField)
    throws SQLException
  {
    BigDecimal doubVal = rs.getBigDecimal(rsField);
    if (ToolUtil.isNull(doubVal)) {
      return null;
    }
    return Double.valueOf(doubVal.doubleValue());
  }

  public static Integer getInt(ResultSet rs, String rsField) throws SQLException
  {
    BigDecimal intVal = rs.getBigDecimal(rsField);
    if (ToolUtil.isNull(intVal)) {
      return null;
    }
    return Integer.valueOf(intVal.intValue());
  }

  public static void close_res_stmt_conn(ResultSet rs, Statement stmt, Connection conn)
  {
    if (rs != null) {
      try {
        rs.close();
        rs = null;
      } catch (SQLException e) {
        logger.error(e.getMessage());
        rs = null;
      }
    }
    if (stmt != null) {
      try {
        stmt.close();
        stmt = null;
      } catch (SQLException e) {
        logger.error(e.getMessage());
        stmt = null;
      }
    }
    if (conn != null)
      try {
        conn.close();
        conn = null;
      } catch (SQLException e) {
        logger.error(e.getMessage());
        conn = null;
      }
  }
}