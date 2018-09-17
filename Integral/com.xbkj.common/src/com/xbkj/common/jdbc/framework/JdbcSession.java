package com.xbkj.common.jdbc.framework;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.eos.common.connection.SimpleConnectionHelper;
import com.eos.runtime.core.TraceLoggerFactory;
import com.eos.system.logging.Logger;
import com.xbkj.common.jdbc.framework.crossdb.CrossDBConnection;
import com.xbkj.common.jdbc.framework.exception.DbException;
import com.xbkj.common.jdbc.framework.exception.ExceptionFactory;
import com.xbkj.common.jdbc.framework.processor.ResultSetProcessor;
import com.xbkj.common.jdbc.framework.util.DBUtil;

/**
 * User: ���� Date: 2005-1-14 Time: 16:29:51 ��ݿ���ʶ��� �ṩһ��ͳһ��������ݷ���API,����ݷ��ʲ���
 */
public final class JdbcSession {
	private static Logger logger = TraceLoggerFactory.getLogger(JdbcSession.class);
    private Connection conn = null;

    private int maxRows = 100000;

    private int dbType = 0;

    private int timeoutInSec = 0;

    private int fetchSize = 0;

    private PreparedStatement prepStatement = null;

    private Statement statement = null;

    private String lastSQL = null;

    private Batch batch = null;

    private DatabaseMetaData dbmd = null;

    private final int BATCH_SIZE = Integer.valueOf(System.getProperty("nc.maxBatch", "600"));

    private int batchIndex = 0;

    private int batchRows = 0;

    /**
     * 
     * @param con
     *            ��ݿ�����
     */
    public JdbcSession(Connection con) {

        dbType = DBUtil.getDbType(con);
        this.conn = con;
    }

   
//    public JdbcSession() throws DbException {
//        try {
//            Connection con = ConnectionFactory.getConnection();
//            dbType = DBUtil.getDbType(con);
//            // dbType = DataSourceCenter.getInstance().getDatabaseType();
//            this.conn = con;
//        } catch (SQLException e) {
//            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
//        }
//
//    }
    
    /**
     * 创建数据源
     * @throws DbException
     */
    public JdbcSession() throws DbException {
        Connection con = SimpleConnectionHelper.getConnection();
		dbType = DBUtil.getDbType(con);
		// dbType = DataSourceCenter.getInstance().getDatabaseType();
		this.conn = con;

    }

    /**
     * 创建数据库连接
     */
    public JdbcSession(String dataSourceName) throws DbException {
        try {
            Connection con = ConnectionFactory.getConnection(dataSourceName);
            dbType = DataSourceCenter.getInstance().getDatabaseType(dataSourceName);

            this.conn = con;
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }

    }

    /**
     * �����Ƿ��Զ���Ӱ汾(ts)��Ϣ
     * 
     * @param isAddTimeStamp
     */
    public void setAddTimeStamp(boolean isAddTimeStamp) {
        if (conn instanceof CrossDBConnection)
            ((CrossDBConnection) conn).setAddTimeStamp(isAddTimeStamp);
    }

    /**
     * �Ƿ����SQL����
     * 
     * @param isTranslator����
     */
    public void setSQLTranslator(boolean isTranslator) {

        if (conn instanceof CrossDBConnection)
            ((CrossDBConnection) conn).enableSQLTranslator(isTranslator);
    }

    /**
     * �����Զ��ύ
     * 
     * @param autoCommit����
     */
    void setAutoCommit(boolean autoCommit) throws DbException {
        try {
            conn.setAutoCommit(autoCommit);

        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * �õ���ǰ���ӵ�FetchSize��С
     * 
     * @return int ���� FetchSize
     */
    public int getFetchSize() {
        return fetchSize;
    }

    /**
     * ���õ�ǰ���ӵ�FetchSize��С
     * 
     * @param fetchSize����
     */
    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    /**
     * ���õ�ǰ���ӵ����񼶱�
     * 
     * @param level����
     */
    void setTransactionIsolation(int level) throws DbException {
        try {
            conn.setTransactionIsolation(level);
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * �ύ��ǰ���ӵ�����
     */
    void commitTrans() throws DbException {
        try {
            conn.commit();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * �ع���ǰ���ӵ�����
     */
    void rollbackTrans() throws DbException {
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * ���õ�ǰ���ӵ�ֻ��
     * 
     * @param readOnly����
     */
    public void setReadOnly(boolean readOnly) throws DbException {
        try {
            conn.setReadOnly(readOnly);
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * ��ǰ���ӵ��Ƿ�ֻ��
     * 
     * @return �����Ƿ�ֻ��
     */
    public boolean isReadOnly() throws DbException {
        try {
            return conn.isReadOnly();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        }
    }

    /**
     * ����ִ���������
     * 
     * @param maxRows
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    /**
     * �õ�ִ���������
     * 
     * @return
     */
    public int getMaxRows() {
        return maxRows;
    }

    /**
     * ȡ���ѯ
     */
    public void cancelQuery() throws DbException {
        try {
            if (prepStatement != null)
                prepStatement.cancel();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage());
        }
    }

    /**
     * ִ���в����ѯ
     * 
     * @param sql
     *            ��ѯSQL���
     * @param parameter
     *            ��ѯ����
     * @param processor
     *            ��������
     * @return ��ѯ����
     */
    public Object executeQuery(String sql, SQLParameter parameter, ResultSetProcessor processor) throws DbException {
        // if (!isSelectStatement(sql))
        // throw new IllegalArgumentException(sql + "--���ǺϷ��Ĳ�ѯ���");
        Object result = null;
        ResultSet rs = null;

        try {
            if ((!sql.equalsIgnoreCase(lastSQL)) || (prepStatement == null)) {
                if (prepStatement != null) {
                    closeStmt(prepStatement);
                }
                prepStatement = conn.prepareStatement(sql);
                lastSQL = sql;
            }
            prepStatement.clearParameters();
            if (parameter != null) {
                DBUtil.setStatementParameter(prepStatement, parameter);
            }
            if (timeoutInSec > 0)
                prepStatement.setQueryTimeout(timeoutInSec);

            prepStatement.setMaxRows(maxRows > 0 ? maxRows : 0);
            if (fetchSize > 0)
                prepStatement.setFetchSize(fetchSize);
            rs = prepStatement.executeQuery();
            result = processor.handleResultSet(rs);
        }

        catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
            SQLException e1 = new SQLException("db connection has interrupted!");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        } finally {
            closeRs(rs);
        }
        return result;
    }

    /**
     * ִ���޲����ѯ
     * 
     * @param sql
     *            ��ѯSQL���
     * @param processor
     *            ��������
     * @return ��ѯ������
     */
    public Object executeQuery(String sql, ResultSetProcessor processor) throws DbException {
        Object result = null;
        ResultSet rs = null;
        try {
            if (statement == null)
                statement = conn.createStatement();
            if (timeoutInSec > 0)
                statement.setQueryTimeout(timeoutInSec);

            statement.setMaxRows(maxRows > 0 ? maxRows : 0);

            if (fetchSize > 0)
                statement.setFetchSize(fetchSize);
            rs = statement.executeQuery(sql);
            result = processor.handleResultSet(rs);
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
        	logger.error("nullpoint exception", e);
            SQLException e1 = new SQLException("NullPointException cause query error");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        } finally {
            closeRs(rs);
        }
        return result;
    }

    /**
     * ִ���и��²���
     * 
     * @param sql
     *            Ԥ����SQL���
     * @param parameter
     *            �������
     * @return �仯����
     */
    public int executeUpdate(String sql, SQLParameter parameter) throws DbException {
        int updateRows;
        try {
            if ((!sql.equalsIgnoreCase(lastSQL)) || (prepStatement == null)) {
                if (prepStatement != null) {
                    closeStmt(prepStatement);
                }
                prepStatement = conn.prepareStatement(sql);
                lastSQL = sql;
            }

            prepStatement.clearParameters();
            if (parameter != null) {
                DBUtil.setStatementParameter(prepStatement, parameter);
            }
            updateRows = prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
            SQLException e1 = new SQLException("db connection has interrupted!");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        }
        return updateRows;
    }

    /**
     * ִ���޸��²���
     * 
     * @param sql
     *            ����SQL���
     * @return ��������
     */
    public int executeUpdate(String sql) throws DbException {
        // return executeUpdate(sql,null);
        int updateRows = 0;

        try {
            if (statement == null)
                statement = conn.createStatement();
            updateRows = statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
            SQLException e1 = new SQLException("db connection has interrupted!");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        }
        return updateRows;
    }

    /**
     * ����в���������ѯ
     * 
     * @param sql
     * @param parameters
     */
    public void addBatch(String sql, SQLParameter parameters) throws DbException {
        try {
            batchIndex++;
            // 
            if (batch == null)
                batch = new Batch();
            batch.addBatch(sql, parameters);
            if (batchIndex % BATCH_SIZE == 0)
                batchRows = batchRows + executeBatch();
        } catch (SQLException e) {
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
            SQLException e1 = new SQLException("db connection has interrupted!");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        }

    }

    /**
     * ����޲���������ѯ
     * 
     * @param sql
     */
    public void addBatch(String sql) throws DbException {
        addBatch(sql, null);
    }

    /**
     * ִ����������
     * 
     * @return
     */
    public int executeBatch() throws DbException {
        try {

            int rows = 0;
            if (batch != null) {
                rows = batchRows + batch.executeBatch();
            }
            batchRows = 0;
            batchIndex = 0;
            return rows;
        } catch (SQLException e) {
        	logger.error("execute batch exception", e.getNextException());
            throw ExceptionFactory.getException(dbType, e.getMessage(), e);
        } catch (NullPointerException e) {
            SQLException e1 = new SQLException("db connection has interrupted!");
            throw ExceptionFactory.getException(dbType, e1.getMessage(), e1);
        } finally {
            if (batch != null) {
                batch.cleanupBatch();
                batch = null;
            }
        }
    }

    /**
     * �ر���ݿ�����
     */
    public void closeAll() {
        closeStmt(statement);
        closeStmt(prepStatement);
        closeConnection(conn);
    }

    /**
     * �õ���ǰ��ݿ��MetaData
     * 
     * @return ���ص�ǰ��ݿ��MetaData
     * @throws SQLException
     */
    public DatabaseMetaData getMetaData() {
        if (dbmd == null)
            try {
                dbmd = conn.getMetaData();
            } catch (SQLException e) {
            	logger.error("get metadata error", e);
            }
        return dbmd;
    }

    /**
     * �������ﴦ����
     * 
     * @return JdbcTransaction
     */
    public JdbcTransaction createTransaction() {
        return new JdbcTransaction(this);
    }

    /**
     * ˽��Batch��
     */
    private class Batch {
        // private int size;
        private Map<String, PreparedStatement> statementCache = new HashMap<String, PreparedStatement>();

        private Statement batchStatement = null;

        // private int rowCount = 0;
        boolean canBatched = true;

        public Batch() {
            // this.size = 0;
            // canBatched = isSupportBatch();
        }

        public void addBatch(String sql, SQLParameter parameters) throws SQLException {
            if (parameters == null) {
                if (batchStatement == null) {
                    batchStatement = conn.createStatement();
                    batchStatement.setMaxRows(maxRows > 0 ? maxRows : 0);
                }
                batchStatement.addBatch(sql);
                return;
            }
            PreparedStatement ps = (PreparedStatement) statementCache.get(sql);
            if (ps == null) {// ps为空，进行创建
                ps = conn.prepareStatement(sql);
                ps.setMaxRows(maxRows > 0 ? maxRows : 0);
                statementCache.put(sql, ps);
            }
            DBUtil.setStatementParameter(ps, parameters);
            // if (canBatched) { //�����ݿ�֧����������
            ps.addBatch();
            // } else {//���֧����������
            // int updateRow = ps.executeUpdate();
            // rowCount += updateRow;
            // }
            // size++;
        }

        public int executeBatch() throws SQLException {
            // if (!canBatched)//���֧����������
            // return rowCount;
            // ���֧����������
            int totalRowCount = 0;
            for (Iterator iterator = statementCache.values().iterator(); iterator.hasNext();) {
                PreparedStatement ps = (PreparedStatement) iterator.next();
                int[] rowCounts = ps.executeBatch();
                for (int j = 0; j < rowCounts.length; j++) {
                    if (rowCounts[j] == Statement.SUCCESS_NO_INFO) {
                        // do nothing
                    } else if (rowCounts[j] == Statement.EXECUTE_FAILED) {
                        // throw new SQLException("����ִ�е� " + j + "�������?");
                    } else {
                        totalRowCount += rowCounts[j];
                    }
                }
            }
            if (batchStatement != null) {
                int[] rowCounts = batchStatement.executeBatch();
                for (int j = 0; j < rowCounts.length; j++) {
                    if (rowCounts[j] == Statement.SUCCESS_NO_INFO) {
                        // do nothing
                    } else if (rowCounts[j] == Statement.EXECUTE_FAILED) {
                        // throw new SQLException("����ִ�е� " + j + "�������?");
                    } else {
                        totalRowCount += rowCounts[j];
                    }
                }
            }
            return totalRowCount;
        }

        /**
         * ����������ѯ
         */
        public void cleanupBatch() throws DbException {
            for (Iterator iterator = statementCache.values().iterator(); iterator.hasNext();) {
                Statement ps = (PreparedStatement) iterator.next();
                closeStmt(ps);
            }
            statementCache.clear();
            closeStmt(batchStatement);
            // size = 0;
        }
    }

    /**
     * ������ݿ�����
     * 
     * @return ���� conn��
     */
    public Connection getConnection() {

        return conn;
    }

    /**
     * @return ���� dbType��
     */
    public int getDbType() {
        return dbType;
    }

    private void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
        }
    }

    private void closeStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
        }
    }

    private void closeRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
        }
    }
    // private boolean isSelectStatement(String sql) {
    // StringBuffer sb = new StringBuffer(sql.trim());
    // String s = (sb.substring(0, 6));
    // return (s.equalsIgnoreCase("SELECT"));
    // }

    // private boolean isSupportBatch() throws SQLException {
    // return getMetaData().supportsBatchUpdates();
    // }
}
