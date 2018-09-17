package org.gocom.components.coframe.resource.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 模块配置文件".cap"装载器
 * @author liuzn (mailto:liuzn@primeton.com)
 *
 */
public class ModuleCapReader {

	/**
	 * 字符集： UTF-8
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 属性文件属性名属性值分割符： "="
	 */
	private static final String REGEX = "=";

	private static final String DISPLAYNAME = "displayName";

	/**
	 * 存储读取的文件属性名属性值键值对的Map
	 */
	private Map<String, String> propertyMap = new HashMap<String, String>();

	/**
	 * 指定的属性文件
	 */
	private File propertiesFile;

	/**
	 * 无参构造方法
	 */
	public ModuleCapReader() {
	}

	/**
	 * 构造指定属性文件的阅读器
	 * 
	 * @param propertiesFile
	 *            指定的属性文件
	 */
	public ModuleCapReader(File propertiesFile, String charset) {
		this.load(propertiesFile, charset);
	}

	/**
	 * 使用默认的字符集构造指定的属性文件阅读器： "UTF-8"
	 * 
	 * @param propertiesFile
	 */
	public ModuleCapReader(File propertiesFile) {
		this(propertiesFile, CHARSET_UTF8);
	}

	/**
	 * 使用指定的字符集装载属性文件
	 * 
	 * @param propertiesFile
	 * @param charset
	 */
	public void load(File propertiesFile, String charset) {
		this.propertiesFile = propertiesFile;
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(propertiesFile),
					charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		analyzePropertiesFile(reader);
	}

	/**
	 * 使用默认的字符集装载一个属性文件: "UTF-8"
	 * 
	 * @param propertiesFile
	 */
	public void load(File propertiesFile) {
		this.load(propertiesFile, CHARSET_UTF8);
	}

	/**
	 * 获取属性值
	 * 
	 * @param key
	 *            属性名
	 * @return 属性值
	 */
	public String getProperty(String key) {
		return propertyMap.get(key);
	}

	/**
	 * 获取模块ID
	 * 
	 * @return 模块ID
	 */
	public String getModuleId() {
		File parentFile = propertiesFile.getParentFile();
		return parentFile.getName();
	}

	/**
	 * 获取模块名称
	 * 
	 * @return 模块名称
	 */
	public String getModuleName() {
		return propertyMap.get(DISPLAYNAME);
	}

	/**
	 * propertiesFile的getter
	 * 
	 * @return propertiesFile
	 */
	public File getPropertiesFile() {
		return propertiesFile;
	}

	/**
	 * propertiesFile的setter
	 * 
	 * @param propertiesFile
	 */
	public void setPropertiesFile(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	/**
	 * 解析属性文件文件，将属性键值对放入Map
	 * 
	 * @param reader
	 *            properties文件的字符流
	 */
	private void analyzePropertiesFile(Reader reader) {
		int tmpChar;
		StringBuilder strBuilder = new StringBuilder();
		try {
			while ((tmpChar = reader.read()) != -1) {
				if (isChangeLine((char) tmpChar)) {
					if (strBuilder.length() > 0) {
						putMap(strBuilder);
						strBuilder.delete(0, strBuilder.length());
					}
				} else {
					strBuilder.append((char) tmpChar);
				}
			}
			if (strBuilder.length() > 0) {
				putMap(strBuilder);
			}
		} catch (IOException e) {

		}
	}

	/**
	 * 判断字符是否是换行符
	 * 
	 * @param charInt
	 *            待判断的字符
	 * @return
	 */
	private boolean isChangeLine(char charInt) {
		if (charInt == '\r' || charInt == '\n') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 读取的一行属性键值对放入Map中
	 * 
	 * @param sbr
	 *            读入的行
	 */
	private void putMap(StringBuilder sbr) {
		String line = sbr.toString();
		String[] keyValue = line.split(REGEX);
		propertyMap.put(keyValue[0], keyValue[1]);
	}

}
