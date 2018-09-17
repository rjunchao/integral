package com.pub.xbkj.pubapp;

/**
 * 元数据 数据模型 常量类
 * 
 */
public class ElementConstant {
	/**
	 * 标识标准产品发布的元素
	 */
	public final static int STANDARD = 0;

	/**
	 * 标识二次开发修改后发布的元素
	 */
	public final static int REDEVELOP = 1;

	/**
	 * 标识用户运行态修改过的元素
	 */
	public final static int USERMOD = 2;

	/**
	 * 数据访问时保留关键字，因为模型里没有这个字段，但VO里有
	 */
	public final static String KEY_VOSTATUS = "status";

	public final static String KEY_DR = "dr";

	public final static String KEY_TS = "ts";

	public final static String PK_VOSTATUS = "ID_ATTR_VOSTATUS";

	public final static String PK_DR = "ID_ATTR_DR";

	public final static String PK_TS = "ID_ATTR_TS";

	// 数据列业务类型
	public final static String FREE_ITEM_NAME = "自由项";/* -=notranslate=- */

	public final static String DEF_ITEM_NAME = "自定义项";/* -=notranslate=- */

	public final static String DEF_COLUMN_VALUE = "*~";

	/** 版型 */
	/* 界面类 */
	public final static String VIEWENTITY = "viewEntity";

	/* 基础数据 */
	public final static String BASE_DATA_ENTITY = "baseDataEntity";

	/** 元数据模型导出生成PDM、导出sql脚本 */
	public final static String COLUMN_LOCALE = "locale";

}
