package com.pub.xbkj.pubapp;

import java.lang.reflect.Array;

import com.pub.xbkj.pubapp.exception.ExceptionUtils;


/**
 * VO构造器
 * 
 */
public class Constructor {
  private Constructor() {
    // 缺省构造方法
  }

  /**
   * VO构造器的工厂方法。
   * 
   * @return 返回VO构造器的实例
   * @deprecated 用具体的static方法替代
   */
  @Deprecated
  public static Constructor getInstance() {
    return new Constructor();
  }

  /**
   * 构造一个类型的实例。当前类必须有默认构造方法
   * 
   * @param <T> 要构造对象的类型
   * @param voclass 要构造实例的Class
   * @return 构造完成的实例
   */
  public static <T> T construct(Class<T> voclass) {
    T instance = null;
    try {
      instance = voclass.newInstance();
    }
    catch (InstantiationException ex) {
      ExceptionUtils.wrappException(ex);
    }
    catch (IllegalAccessException ex) {
      ExceptionUtils.wrappException(ex);
    }
    return instance;
  }

  /**
   * 构造一个类型的数组实例，同时数组中的值已经初始化
   * 
   * @param <T> 要构造数组的类型
   * @param voclass 数组的Class
   * @param size 数组的长度
   * @return 元素已经初始化的数组
   */
  public static <T> T[] construct(Class<T> voclass, int size) {
    T[] instances = Constructor.declareArray(voclass, size);
    for (int i = 0; i < size; i++) {
      instances[i] = Constructor.construct(voclass);
    }
    return instances;
  }

  /**
   * 构造一个类型的数组。数组中的元素没有初始化
   * 
   * @param <T> 要构造数组的类型
   * @param voclass 数组的Class
   * @param size 要构造的数组长度
   * @return 元素没有初始化的数组
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] declareArray(Class<T> voclass, int size) {
    T[] instances = (T[]) Array.newInstance(voclass, size);
    return instances;
  }

}
