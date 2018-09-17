package com.xbkj.log.vo.logging.message;

/**
 * @author cc
 * @author hgy
 */
public interface ILogMessage {
	public String getDefaultMessage();
	public void addLogItem(String tag, String body);
	public void removeLogItem(String tag);
	public String getItemBody(String tag);
	public String toString();
    public String toXMLString();
}
