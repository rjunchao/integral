package com.pub.xbkj.pubapp.data;

/**
 * IRowSet加工为java对象的接口定义
 * 
 * @param <E> 加工后的对象类型
 */
public interface IRowSetMap<E> {
  /**
   * 将rowet加工为另外一组对象
   * 
   * @param rowset 要加工的rowset
   * @return 加工后的对象数组
   */
  E[] convert(IRowSet rowset);
}
