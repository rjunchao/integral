package com.xbkj.mxfw.bs.framework.mx.jdbc;

import java.util.regex.Pattern;

import com.grc.log.bs.logging.Log;

public class SqlMonitorParam {
	public final static Log log = Log.getInstance("threshold");

	public static long timeThreshold = 500;

	public static Pattern clientFilter;
	
	
}
