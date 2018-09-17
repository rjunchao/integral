package com.pub.xbkj.pubapp.exception;
public class CarrierRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 4946018652993178290L;

	private String errorCode;
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 *            异常信息
	 */
	public CarrierRuntimeException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * 
	 * @param ex
	 *            底层异常
	 */
	public CarrierRuntimeException(Exception ex) {
		super(ex.getMessage(), ex);
	}

}
