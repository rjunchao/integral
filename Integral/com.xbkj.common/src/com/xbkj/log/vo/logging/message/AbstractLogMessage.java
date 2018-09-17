/*
 * Created on 2005-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.xbkj.log.vo.logging.message;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author cc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractLogMessage implements ILogMessage {
	protected HashMap logItems = new HashMap();
	protected String defaultMessage = "";
	
	public AbstractLogMessage() {}
	
	public AbstractLogMessage(String arg) {
		this.defaultMessage = XmlCharConverter.getXMLString(arg);
	}
	
	public String toXMLString() {
		StringBuffer sb = new StringBuffer(getDefaultMessage());
		for (Iterator itr = logItems.keySet().iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			sb.append(XmlCharConverter.buildByTag(key, getItemBody(key)));
		}

		return sb.toString();
	}
    
    public String toString() {
        StringBuffer sb = new StringBuffer(getDefaultMessage());
        for (Iterator itr = logItems.keySet().iterator(); itr.hasNext();) {
            String key = (String) itr.next();
            sb.append(key).append("=").append(getItemBody(key));
        }
        return sb.toString();

    }
	
	public String getItemBody(String tag) {
		return logItems.get(tag) + "";
	}
	
	public String getDefaultMessage() {
		return XmlCharConverter.buildDefault(this.defaultMessage);
	}
	
	public void addLogItem(String tag, String body) {
		logItems.put(XmlCharConverter.getXMLString(tag), 
				XmlCharConverter.getXMLString(body));
	}
	
	public void removeLogItem(String tag) {
		logItems.remove(XmlCharConverter.getXMLString(tag));
	}
}
