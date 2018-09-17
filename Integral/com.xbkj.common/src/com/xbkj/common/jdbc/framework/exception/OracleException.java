package com.xbkj.common.jdbc.framework.exception;

import java.sql.SQLException;

/**
 * @nopublish
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2005-1-14
 * Time: 16:37:28
 */
public class OracleException extends DbException {
    /**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 5684021724118043325L;


	/**
     * Constructor for OracleException.
     *
     * @param s
     * @param e
     */
    public OracleException(String msg, SQLException e) {
        super(msg, e);
    }


    public OracleException(String msg) {
        super(msg);
    }

    public boolean isDataIntegrityViolation() {
        switch (sqlErrorCode) {
            case 1:
            case 1400:
            case 2291:
            case 1722:
                return (true); //1722 = invalid number
            default:
                return (false);
        }
    }


    public boolean isBadSQLGrammar() { //900,903,904,917,936,942,17006

        switch (sqlErrorCode) {
            case 900:
            case 903:
            case 904:
            case 917:
            case 936:
            case 942:
            case 17006:
                return (true); //1722 = invalid number
            default:
                return (false);
        }
    }


}

