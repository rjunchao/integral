package com.xbkj.common.jdbc.framework.mapping;

import java.io.Serializable;


/**
 *
 *
 * 数据库元数据接口定义
 *
 */
public interface IMappingMeta extends Serializable {
    /**
     * 返回主键字段
     * @return
     */
    public abstract String getPrimaryKey();

    /**
     * 返回表名
     */
    public abstract String getTableName();

    /**
     * 返回VO属性集合
     * @return
     */
    public abstract String[] getAttributes();

    /**
     * 返回元数据列集合
     * @return
     */
    public abstract String[] getColumns();

}