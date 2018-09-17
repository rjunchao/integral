/*
 * �������� 2005-10-11
 *
 * TODO Ҫ��Ĵ���ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package com.xbkj.common.jdbc.framework.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.xbkj.common.jdbc.framework.mapping.IMappingMeta;

/**
 * 
 */
public class BeanMappingListProcessor extends BaseProcessor{
    /**
	 * <code>serialVersionUID</code> ��ע��
	 */

    private static final long serialVersionUID = 973303953249005339L;
	private  IMappingMeta meta;
    private Class type = null;
    
    private String columns[];
    
    
    /**
     * @param meta
     */
    public BeanMappingListProcessor(Class type,IMappingMeta meta) {
        super();
        this.meta = meta;
        this.type = type;
    }
    
    /**
     * @param meta
     */
    public BeanMappingListProcessor(Class type,IMappingMeta meta,String columns[]) {
        super();
        this.meta = meta;
        this.type = type;
        this.columns=columns;
    }
    /* ���� Javadoc��
     * @see nc.jdbc.framework.processor.BaseProcessor#processResultSet(java.sql.ResultSet)
     */
  public  Object processResultSet(ResultSet resultSet) throws SQLException {
	  if(columns==null)
        return ProcessorUtils.toBeanList(resultSet, this.type,meta) ;
	  return ProcessorUtils.toBeanList(resultSet, this.type,meta,columns) ;
    }
}
