package com.xbkj.common.jdbc.framework.exception;

import java.sql.SQLException;

/**
 * 
 */

public class MSSQLException extends DbException {
    /**
     * <code>serialVersionUID</code> ��ע��
     */
    private static final long serialVersionUID = 8593535298933741800L;


    /**
     * Constructor for HSQLException.
     *
     * @param s
     * @param e
     */
    public MSSQLException(String msg, SQLException e) {
        super(msg, e);
    }


    public MSSQLException(String msg) {
        super(msg);
    }

    public boolean isDataIntegrityViolation() { //2627,8114,8115
        switch (sqlErrorCode) {
            case 2627:
            case 8114:
            case 8115:
                return (true);
            default:
                return (false);
        }
    }


    public boolean isBadSQLGrammar() { //9207,208

        switch (sqlErrorCode) {
            case 9207:
            case 208:
                return (true); //1722 = invalid number
            default:
                return (false);
        }
    }


}

