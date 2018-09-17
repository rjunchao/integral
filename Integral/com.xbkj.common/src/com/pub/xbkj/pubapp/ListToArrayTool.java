package com.pub.xbkj.pubapp;

import java.util.List;

import com.vbm.grc.pubapp.exception.ExceptionUtils;

/**
 * 链表到数组的转换工具。注意，这个工具类是给公共框架使用的。因为公共框架并不知道
 * 具体的数据类型。业务组代码就不要使用此类，因为业务组一定知道具体的数据类型是
 * 什么。公共框架代码在使用的时候，一定要考虑处理长度为0的链表
 * <p>
 * <b>传入的链表的长度不能为0</b>
 * 
 * @param <E> 要转换对象的类型
 */
public class ListToArrayTool<E> {
  /**
   * 要从链表转化为数组的对象的class
   */
  private Class<E> clazz;

  /**
   * 链表到数组的转换工具的构造函数
   */
  public ListToArrayTool() {
    // 默认构造方法
  }

  /**
   * 链表到数组的转换工具的构造函数
   * 
   * @param clazz 要转换对象的class
   */
  public ListToArrayTool(Class<E> clazz) {
    this.clazz = clazz;
  }

  /**
   * 将链表转换为数组
   * 
   * @param list 链表
   * @return 转换后的数组
   */
  @SuppressWarnings({
    "unchecked", "cast"
  })
  public E[] convertToArray(List<E> list) {
    int size = list.size();
    if (size == 0) {
      // list的数量为0，然后又无法知道确切地类型，只能不支持
      if (this.clazz == null) {
        ExceptionUtils.unSupported();
      }
      else {
        E[] instances = (E[]) Constructor.construct(this.clazz, 0);
        return instances;
      }
    }
    Class<?> objClass = (Class<?>) list.get(0).getClass();
    E[] instances = (E[]) Constructor.construct(objClass, size);
    instances = list.toArray(instances);
    return instances;
  }
}
