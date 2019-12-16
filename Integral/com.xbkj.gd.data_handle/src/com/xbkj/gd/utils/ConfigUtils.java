package com.xbkj.gd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.gocom.components.coframe.tools.LoggerFactory;

import com.eos.system.logging.Logger;

/**
 *@author rjc
 *@email ruanjc@126.com
 *@date 2019-10-30
 *@version 1.0.0
 *@desc
 */
public class ConfigUtils {
	private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);
	private static Properties prop = new Properties();
	static {
//		com.xbkj.gd.utils.config.propereits
		InputStream is = ConfigUtils.class.getClassLoader().getResourceAsStream("com/xbkj/gd/utils/config.propereits");
		try {
			prop.load(is);
		} catch (IOException e) {
			log.error(e);
		}
	}
	public static String get(String key){
		return prop.getProperty(key);
	}
}
