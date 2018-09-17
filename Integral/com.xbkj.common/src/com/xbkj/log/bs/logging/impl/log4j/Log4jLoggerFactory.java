package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * @author �ι���
 *
 * Date: 2006-9-10
 * Time: ����11:12:03
 */
public class Log4jLoggerFactory implements LoggerFactory {
    
    Log4jLoggerFactory() {
    }    
      
    public Logger makeNewLoggerInstance(String name) {
      return new Log4jLogger(name);
    }    
  }
