package com.pub.xbkj.pubapp.database;

import java.util.List;

import com.pub.xbkj.pubapp.JavaType;
import com.pub.xbkj.pubapp.data.ValueUtils;
import com.pub.xbkj.pubapp.exception.ExceptionUtils;
import com.xbkj.basic.vo.pub.SuperVO;

/**
 * 表的基本操作
 * 
 */
public abstract class AbstractTableOperator {




  /**
   * 添加数据库字段对应的实体属性值到要进入数据库中的数据中
   * 
   * @param list 要进入数据库中的数据
   * @param vo 实体
   * @param column 数据库字段名
   * @param usedFieldSet 实体中设过值得属性名集合
   */
  protected void fillData(List<Object> list, SuperVO vo, String attribute,
      JavaType type) {
  
    Object value = null;
    if (attribute == null) {
      ExceptionUtils.unSupported("不支持此种业务，属性为空~");
      return;
    }
   
    value = vo.getAttributeValue(attribute);
     
    this.fillData(list, value, attribute,type);
  }

  /**
   * 将数据增加到List里
   * @param list
   * @param value
   * @param attribute
   */
  private void fillData(List<Object> list, Object value,
      String attribute,JavaType type) {
 
    Object obj = ValueUtils.convert(value, attribute,type);
    list.add(obj);
  }

  /**
   * 增加时间戳到要进入数据库表中的数据中
   * 
   * @param list 要进入数据库表中的数据
   * @param ts 当前记录的最新时间戳
   */
  protected void fillTSData(List<Object> list, String ts) {
    list.add(ts.toString());
  }

}
