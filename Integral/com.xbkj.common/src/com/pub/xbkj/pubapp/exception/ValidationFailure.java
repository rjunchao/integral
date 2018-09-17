package com.pub.xbkj.pubapp.exception;

import java.io.Serializable;

public class ValidationFailure implements Serializable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2373471977621970308L;
	
	private String message;
 
	private String errorcode;
	
	public ValidationFailure(){
	}
	
	public ValidationFailure(String msg)
	{
		this.message = msg;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	
	
}
