package com.xbkj.common.jdbc.framework.exception;

import java.sql.SQLException;

/**
 * @nopublish
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2005-1-14
 * Time: 16:37:28
 * δ֪����ݿ��쳣
 */
public class UnKnownException extends DbException {
    /**
	 * <code>serialVersionUID</code> ��ע��
	 */
	private static final long serialVersionUID = 7992559253225572055L;


	/**
     * Constructor for HSQLException.
     *
     * @param s
     * @param e
     */
    public UnKnownException(String msg, SQLException e) {
        super(msg, e);
    }


    public UnKnownException(String msg) {
        super(msg);
    }

    public boolean isDataIntegrityViolation() {
        return false;
    }


    public boolean isBadSQLGrammar() { //-22,-28
        return false;
    }


}

