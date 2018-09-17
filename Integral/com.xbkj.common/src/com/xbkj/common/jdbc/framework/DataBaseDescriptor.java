/**
 * @nopublish
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2005-2-1
 * Time: 9:13:01
 *  
 */
package com.xbkj.common.jdbc.framework;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xbkj.common.bs.dao.DAOException;
import com.xbkj.common.jdbc.framework.util.DBConsts;
import com.xbkj.common.jdbc.framework.util.DBUtil;
import com.xbkj.log.bs.logging.Logger;

public class DataBaseDescriptor {
	public final static int UNKNOWN = 0;

	public final static int ORACLE = 1;

	public final static int MSSQL = 2;

	public final static int DB2 = 3;

	public final static int HSQL = 4;

	public final static String ORACLE_NAME = "ORACLE";

	public final static String MSSQL_NAME = "MSSQL";

	public final static String DB2_NAME = "DB2";

	public final static String HSQL_NAME = "HSQL";

	private static DataBaseDescriptor ourInstance = new DataBaseDescriptor();

	private ArrayList<String> alTables = null;

	private HashMap<String, ResultSet> hmTableColumns = null;

	private HashMap<String, String[]> columnsCache = null;

	private HashMap<String, Map<String, Integer>> ColumnTypeCache = null;

	public static DataBaseDescriptor getInstance() {
		return ourInstance;
	}

	private DataBaseDescriptor() {

	}

	private Connection getConnection() throws SQLException {
		return ConnectionFactory.getConnection();
	}

	/**
	 * ����һ����ݿ��е����б�����
	 * 
	 * @return String[] һ����ݿ��е����б�����
	 * @throws Exception
	 */
	public synchronized String[] getTables() throws SQLException {
		ResultSet rsTables = null;
		Connection conn = null;
		try {
			int dbType = DataSourceCenter.getInstance().getDatabaseType();
			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			// �ȼ���Ƿ���Ҫ��ѯ��ݿ�
			if (alTables == null) {
				alTables = new ArrayList<String>();

				if (dbType == DBConsts.SQLSERVER)
					rsTables = dbmd.getTables(null, null, null,
							new String[] { "TABLE" });
				else if (dbType == DBConsts.ORACLE || dbType == DBConsts.DB2)
					rsTables = dbmd.getTables(null, dbmd.getUserName()
							.toUpperCase(), null, new String[] { "TABLE" });

				while (rsTables.next()) {
					alTables.add(rsTables.getString("TABLE_NAME"));
				}// end of while
			}
			if (alTables.size() == 0)
				return new String[0];

		} catch (SQLException e) {
			Logger.error("getTables�����쳣", e);
		} finally {
			DBUtil.closeConnection(conn);
			DBUtil.closeRs(rsTables);
		}
		return alTables.toArray(new String[] {});
	}

	/**
	 * ������ݿ���ĳ���ض���������е����
	 * 
	 * @param table
	 *            ָ���ı����
	 * @return String[] �����е����
	 * @throws Exception
	 */
	public synchronized String[] getColumns(String table) throws SQLException {
		if (columnsCache == null)
			columnsCache = new HashMap<String, String[]>();
		String columns[] = null;
		columns = columnsCache.get(table);
		if (columns != null)
			return columns;
		ResultSet rsColumns = null;
		ArrayList<String> alColumns = new ArrayList<String>();
		Connection conn = null;
		try {
			int dbType = DataSourceCenter.getInstance().getDatabaseType();
			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbType == DBConsts.SQLSERVER)
				rsColumns = dbmd.getColumns(null, null, table.toUpperCase(),
						"%");
			else if (dbType == DBConsts.ORACLE || dbType == DBConsts.DB2)
				rsColumns = dbmd.getColumns(null, dbmd.getUserName()
						.toUpperCase(), table.toUpperCase(), "%");
			while (rsColumns.next()) {
				alColumns.add(rsColumns.getString("COLUMN_NAME"));
			}// end of while
			if (alColumns.size() == 0)
				return new String[0];

		} catch (Exception e) {
			Logger.error("getColumns�����쳣", e);
		} finally {
			DBUtil.closeConnection(conn);
			DBUtil.closeRs(rsColumns);
		}
		columnsCache.put(table, alColumns.toArray(new String[] {}));
		return alColumns.toArray(new String[] {});
	}

	public synchronized String[] getPks(String tableName) throws SQLException {
		java.sql.ResultSet rs = null;
		String pks[] = null;
		java.util.Vector<String> v = new java.util.Vector<String>();
		Connection conn = null;
		try {

			int dbType = DataSourceCenter.getInstance().getDatabaseType();
			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbType == DBConsts.SQLSERVER)
				rs = dbmd.getPrimaryKeys(null, null, tableName.toUpperCase());
			else if (dbType == DBConsts.ORACLE || dbType == DBConsts.DB2)
				rs = dbmd.getPrimaryKeys(null,
						dbmd.getUserName().toUpperCase(), tableName
								.toUpperCase());
			while (rs.next()) {
				String pkStr = rs.getString("COLUMN_NAME").toLowerCase();
				if (!v.contains(pkStr))
					v.add(pkStr);
			}
		} catch (SQLException e) {
			Logger.error("getPks�����쳣", e);
		} finally {
			DBUtil.closeConnection(conn);
			DBUtil.closeRs(rs);
		}
		pks = new String[v.size()];
		if (v.size() > 0)
			v.copyInto(pks);

		return pks;
	}

	public synchronized Map<String, Integer> getColmnTypes(String table) {
		Map<String, Integer> typeMap = new HashMap<String, Integer>();
		Connection conn = null;
		try {

			if (ColumnTypeCache == null)
				ColumnTypeCache = new HashMap<String, Map<String, Integer>>();

			if (ColumnTypeCache.get(table) != null)
				return (Map) ColumnTypeCache.get(table);

			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rsColumns = dbmd.getColumns(null, dbmd.getUserName()
					.toUpperCase(), table.toUpperCase(), "%");
			while (rsColumns.next()) {
				String columnName = rsColumns.getString("COLUMN_NAME");
				int columnType = rsColumns.getShort("DATA_TYPE");
				typeMap.put(columnName, new Integer(columnType));
			}
			ColumnTypeCache.put(table, typeMap);

		} catch (SQLException e) {
			Logger.error("getColmnTypes�����쳣", e);
		} finally {
			DBUtil.closeConnection(conn);

		}
		return typeMap;
	}

	/**
	 * ����Ƶ���ݱ��Ƿ��Ѿ�����
	 * 
	 * @param table
	 *            �����
	 * @return ����Ѿ����ڣ�����true
	 * @throws Exception
	 */
	public synchronized boolean isTableExist(String table) throws Exception {
		// �ȼ���Ƿ���Ҫ��ѯ��ݿ�
		if (alTables == null)
			this.getTables();

		if (alTables.contains(table))
			return true;
		else
			return false;
	}

	public synchronized String getDDL(String tableName) throws SQLException {
		StringBuffer result = new StringBuffer();
		StringBuffer indexBuffer = new StringBuffer("");
		result.append("DROP TABLE " + tableName + " IF EXISTS;\n");
		result.append("\nCREATE CACHED TABLE " + tableName + " (\n");
		Connection conn = null;
		try {

			conn = getConnection();
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet tableMetaData = dbmd.getColumns(null, dbmd.getUserName()
					.toUpperCase(), tableName.toUpperCase(), "%");
			boolean firstLine = true;
			while (tableMetaData.next()) {
				if (firstLine) {
					firstLine = false;
				} else {

					result.append(",\n");
				}
				String columnName = tableMetaData.getString("COLUMN_NAME");
				String columnType = tableMetaData.getString("TYPE_NAME");
				int columnSize = tableMetaData.getInt("COLUMN_SIZE");
				result.append("    " + columnName + " "
						+ convertType(columnType, columnSize) + " ");
			}
			tableMetaData.close();

			ResultSet primaryKeys = null;
			int dbType = DataSourceCenter.getInstance().getDatabaseType();
			if (dbType == DBConsts.SQLSERVER)
				primaryKeys = dbmd.getPrimaryKeys(null, null, tableName
						.toUpperCase());
			else if (dbType == DBConsts.ORACLE || dbType == DBConsts.DB2)
				primaryKeys = dbmd.getPrimaryKeys(null, dbmd.getUserName()
						.toUpperCase(), tableName.toUpperCase());

			String primaryKeyName = null;
			StringBuffer primaryKeyColumns = new StringBuffer();

			while (primaryKeys.next()) {

				String colName = primaryKeys.getString("COLUMN_NAME");
				String thisKeyName = primaryKeys.getString("PK_NAME");
				if ((thisKeyName != null && primaryKeyName == null)
						|| (thisKeyName == null && primaryKeyName != null)
						|| (thisKeyName != null && !thisKeyName
								.equals(primaryKeyName))
						|| (primaryKeyName != null && !primaryKeyName
								.equals(thisKeyName))) {
					if (primaryKeyColumns.length() > 0) {
						// There's something to output
						result.append(",\n    PRIMARY KEY ");

						result.append("(" + primaryKeyColumns.toString() + ")");
						indexBuffer.append("CREATE INDEX " + "i_" + tableName
								+ "_" + primaryKeyColumns + " ON " + tableName
								+ " ( " + primaryKeyColumns + " ASC ); \n");
					}
					// Start again with the new name
					primaryKeyColumns = new StringBuffer();
					primaryKeyName = thisKeyName;
				}
				// Now append the column
				if (primaryKeyColumns.length() > 0) {
					primaryKeyColumns.append(", ");
				}
				primaryKeyColumns.append(colName);
			}
			if (primaryKeyColumns.length() > 0) {
				// There's something to output
				result.append(",\n    PRIMARY KEY ");

				result.append(" (" + primaryKeyColumns.toString() + ")");
				indexBuffer.append("CREATE INDEX " + "i_" + tableName + "_"
						+ primaryKeyColumns.toString().toLowerCase() + " ON "
						+ tableName + " ( "
						+ primaryKeyColumns.toString().toLowerCase()
						+ " ASC ); \n");

			}
			result.append("\n);");
			result.append(indexBuffer);
		} catch (SQLException e) {
			Logger.error("getDDL�����쳣", e);
		} finally {
			DBUtil.closeConnection(conn);
		}
		return result.toString();
	}

	private String convertType(String columnType, int columnSize) {
		if (columnType.equalsIgnoreCase("smallint"))
			return columnType;
		if (columnType.equalsIgnoreCase("varchar2"))
			return "VARCHAR (" + columnSize + ")";
		if (columnType.equalsIgnoreCase("number") && columnSize != 10)
			return "DECIMAL (" + columnSize + ")";
		if (columnType.equalsIgnoreCase("number") && columnSize == 10)
			return "SMALLINT ";
		if (columnType.equalsIgnoreCase("int") && columnSize == 10)
			return "SMALLINT ";
		if (columnType.equalsIgnoreCase("int") && columnSize != 10)
			return "DECIMAL (" + columnSize + ")";
		if (columnType.equalsIgnoreCase("image"))
			return "Binary";
		else
			return columnType + " (" + columnSize + ")";
	}

	/**
	 * ������ݿ�����ͼ����� �������ڣ�(2001-6-3 13:19:41)
	 * 
	 * @param dbType
	 *            int����ݿ�����
	 * @param schema
	 *            java.lang.String
	 * @param strPrifix
	 *            java.lang.String ǰ׺
	 * @return java.lang.String[]
	 * @throws java.sql.SQLException
	 *             �쳣˵����
	 */
	public String[] getTableNames(String strPrefix) throws DAOException {
		String[] tableNames = null;
		PersistenceManager manager = null;
		ResultSet rs = null;
		try {
			manager = PersistenceManager.getInstance();
			int dbType = DataSourceCenter.getInstance().getDatabaseType();
			List vec = new ArrayList();
			DatabaseMetaData dbmd = manager.getMetaData();
			String tableNamePattern = "%";
			if (strPrefix != null)
				tableNamePattern = strPrefix + "%";
			String[] tableTypes = { "TABLE", "VIEW" };
			rs = dbmd.getTables(manager.getCatalog(), manager
					.getSchema(), getTableName(dbType, tableNamePattern),
					tableTypes);
			while (rs.next()) {
				String name = rs.getString("TABLE_NAME");
				vec.add(name);
			}
			if (vec.size() > 0) {
				tableNames = new String[vec.size()];
				tableNames = (String[]) vec.toArray(tableNames);
			}
			return tableNames;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBUtil.closeRs(rs);
			manager.release();
		}
	}

	/**
	 * ������ݿ���صı��� �������ڣ�(2001-8-18 19:52:29)
	 * 
	 * @param dbType
	 *            int
	 * @param tableName
	 *            java.lang.String
	 * @return java.lang.String
	 * @since ��V1.00
	 */
	protected String getTableName(int dbType, String tableName) {
		String strTn = null;
		switch (dbType) {
		case DBConsts.SQLSERVER:
			strTn = tableName;
			break;
		case DBConsts.ORACLE:
		case DBConsts.DB2:
			// ORACLE�轫�����д
			strTn = tableName.toUpperCase();
			break;
		}
		return strTn;
	}

	/**
	 * �ж���ݱ��Ƿ����
	 * 
	 * @param tableName
	 *            ��ݱ����
	 * @return
	 * @throws DAOException
	 *             �����׳�DAOException
	 */
	public boolean isTableExisted(String tableName) throws DAOException {
		if (tableName == null)
			throw new NullPointerException("TableName is null!");
		PersistenceManager manager = null;
		ResultSet rs = null;
		try {
			manager = PersistenceManager.getInstance();
			int dbType = manager.getDBType();
			DatabaseMetaData dbmd = manager.getMetaData();

			// ��ñ�����Ϣ
			rs = dbmd.getTables(manager.getCatalog(), manager
					.getSchema(), getTableName(dbType, tableName),
					new String[] { "TABLE" });
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			throw new DAOException(e.getMessage());
		} finally {
			DBUtil.closeRs(rs);
			manager.release();
		}
	}

	

}
