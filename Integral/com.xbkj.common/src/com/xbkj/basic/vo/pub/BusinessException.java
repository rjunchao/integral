package com.xbkj.basic.vo.pub;



public class BusinessException extends Exception {
	static final long serialVersionUID = -35466L;

    private String hint;
    
	
	private String errorCodeString = ""; 
 
    
    public BusinessException() {
        super();
    }

   
    public BusinessException(String s) {
        super(s);
        setErrorCodeString(IErrorDict.ERR_BUSI_BILL_EXCEPTION);
    }
    
    /**
     * BusinessException ������ע�⡣
     * @param s java.lang.String
     */
    public BusinessException(String s,String errorCode) {
        super(s);
        setErrorCodeString(errorCode);
    }

   
    public java.lang.String getHint() {
        return hint;
    }

   
    public void setHint(java.lang.String newHint) {
        hint = newHint;
    }

   
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

	public String getErrorCodeString() {
		return errorCodeString;
	}

	public void setErrorCodeString(String errorCode) {
		this.errorCodeString = errorCode;
	}

}
