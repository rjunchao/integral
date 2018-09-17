package com.pub.xbkj.pubapp.exception;

import java.util.ArrayList;
import java.util.List;

import com.xbkj.basic.vo.pub.BusinessException;



public class ValidationException extends BusinessException {
	

	/**
	 * ID
	 */
	private static final long serialVersionUID = -3072640157952294464L;
	private List<ValidationFailure>	failures			= new ArrayList<ValidationFailure>();

	public ValidationException(){}
	
	public ValidationException(List<ValidationFailure> failures) {
		super();
		this.failures = failures;
	}

	public void addValidationFailure(ValidationFailure failure) {
		failures.add(failure);
	}

	public List<String> getFailureMessage() {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < failures.size(); i++)
		{
			result.add(failures.get(i).getMessage());
		}
		return result;
	}

	@Override
	public String getMessage() {
		return this.getUnionStr(getFailureMessage()
				.toArray(new String[0]), "\n", "");
	}
	
	private String errorcode = "32000";
	
	@Override
	public String getErrorCodeString() {
		//优先返回编码重复异常
		for (int i = 0; i < failures.size(); i++) {
			if ("32001".equals(failures.get(i).getErrorcode())) {
				return failures.get(i).getErrorcode();
			} else if ("32002".equals(failures.get(i).getErrorcode())){
				errorcode = failures.get(i).getErrorcode();
			}
		}
		return errorcode;
	}
	public void setErrorCodeString(String errorCode)
	{
		this.errorcode=errorCode;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public List<ValidationFailure> getFailures() {
		return failures;
	}

	public void setFailures(List<ValidationFailure> failures) {
		this.failures = failures;
	}

	  public  String getUnionStr(String[] strAry, String unionChar, String appendChar)
	  {
	    StringBuffer ret = new StringBuffer();
	    for (int i = 0; i < strAry.length; i++) {
	      if (i != 0)
	        ret.append(unionChar);
	      ret.append(appendChar + strAry[i] + appendChar);
	    }
	    return ret.toString();
	  }
}
