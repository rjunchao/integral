package com.xbkj.gd.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@author rjc
 *@email rjc@ronhe.com.cn
 *@date 2017-9-6
 *@version 1.0.0
 *@desc
 */
public class DateUtils {

	public static final String PATTERN_19 = "yyyy-MM-dd hh:mm:ss";
	public static final String PATTERN_YEAR = "yyyy";
	
	public static String getFormatDate(String pattern){
		return new SimpleDateFormat(pattern).format(new Date());//"yyyy-MM-dd hh:mm:ss"
	}
}
