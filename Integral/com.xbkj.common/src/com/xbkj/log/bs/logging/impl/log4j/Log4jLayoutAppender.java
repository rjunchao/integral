package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author �ι���
 *
 * Date: 2006-9-10
 * Time: ����12:53:35
 */
public interface Log4jLayoutAppender {
    public void doAppend(LoggingEvent event, Layout layout);
    
    public void doAppend(String formatedMessage);
   
}
