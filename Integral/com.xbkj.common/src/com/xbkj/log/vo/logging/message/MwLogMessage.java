/*
 * Created on 2005-2-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.message;


/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MwLogMessage extends AbstractLogMessage {
	public static final String MW_INVOKING = "Invoking";
	public static final String MW_TIME_USED = "Time-used";
	public static final String MW_BYTES_OUT = "Bytes-out";
	
	private MwLogMessage() {}
	
	public MwLogMessage(String defaultMessage) {
		super(defaultMessage);
		addLogItem(MW_INVOKING, "");
		addLogItem(MW_TIME_USED, "");
		addLogItem(MW_BYTES_OUT, "");
	}
	
	public MwLogMessage(String defaultMessage, String invoking, long timeUsed, long bytesOut) {
		super(defaultMessage);
		addLogItem(MW_INVOKING, invoking);
		addLogItem(MW_TIME_USED, "" + timeUsed);
		addLogItem(MW_BYTES_OUT, "" + bytesOut);
	}
	
	public void setDefaultMessage(String msg) {
		this.defaultMessage = msg.trim();
	}
	
	public void setInvokingMethod(String invoking) {
		addLogItem(MW_INVOKING, invoking);
	}
	
	public void setTimeUsed(long timeUsed) {
		addLogItem(MW_TIME_USED, "" + timeUsed);
	}

	public void setBytesOut(long bytesOut) {
		addLogItem(MW_BYTES_OUT, "" + bytesOut);
	}


}
