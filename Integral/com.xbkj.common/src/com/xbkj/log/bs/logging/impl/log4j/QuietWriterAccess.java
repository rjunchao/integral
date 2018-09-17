package com.xbkj.log.bs.logging.impl.log4j;

import org.apache.log4j.helpers.QuietWriter;


public interface QuietWriterAccess {

    public QuietWriter getQuietWriter();
    
    public void rolling();
}
