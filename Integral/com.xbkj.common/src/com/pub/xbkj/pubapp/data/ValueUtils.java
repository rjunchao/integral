package com.pub.xbkj.pubapp.data;

import java.math.BigDecimal;

import com.pub.xbkj.pubapp.JavaType;

/**
 * 值转换工具类，将某个未知类型的object转换为特定类型的值。
 * 
 */
public class ValueUtils {
  private ValueUtils() {
    // 缺省构造方法
  }

  /**
   * 将值转换为BigDecimal类型
   * 
   * @param value 要转换的值
   * @return 类型为BigDecimal的值
   */
  public static BigDecimal getBigDecimal(Object value) {
    BigDecimal retValue = null;
    if (value == null) {
      return null;
    }
    if (value instanceof BigDecimal) {
      retValue = (BigDecimal) value;
    }
    else if (value instanceof Double) {
      retValue =  new BigDecimal((Double)value);
    }
    else {
      String str = value.toString();
      try {
        retValue = new BigDecimal(str);
      }
      catch (NumberFormatException ex) {
        ValueUtils.throwIllegalArgumentException(value, ex);
      }
    }
    return retValue;
  }


  /**
   * 值转换工具类的工厂方法。
   * 
   * @return 返回值转化工具类的实例
   * @deprecated 用具体转换值的static方法替代
   */
  @Deprecated
  public static ValueUtils getInstance() {
    return new ValueUtils();
  }

  /**
   * 将值转换为int类型
   * 
   * @param value 要转换的值
   * @return 类型为int的值
   */
  public static int getInt(Object value) {
    return ValueUtils.getInt(value, 0);
  }

  /**
   * 将值转换为int类型，如果传出的值为null，则返回默认值
   * 
   * @param value 要转换的值
   * @param defaultValue 默认值
   * @return 类型为int的值
   */
  public static int getInt(Object value, int defaultValue) {
    Integer temp = ValueUtils.getInteger(value);
    int ret = defaultValue;
    if (temp != null) {
      ret = temp.intValue();
    }
    return ret;
  }

  /**
   * 将值转换为Integer类型
   * 
   * @param value 要转换的值
   * @return 类型为Integer的值
   */
  public static Integer getInteger(Object value) {
    Integer retValue = null;
    if (value == null) {
      return null;
    }
    if (value instanceof Integer) {
      retValue = (Integer) value;
    }
    else {
      String str = value.toString();
      try {
        retValue = Integer.valueOf(str);
      }
      catch (NumberFormatException ex) {
        ValueUtils.throwIllegalArgumentException(value, ex);
      }
    }
    return retValue;
  }

  /**
   * 将值转换为String类型
   * 
   * @param value 要转换的值
   * @return 类型为String的值
   */
  public static String getString(Object value) {
    String retValue = null;
    if (value == null) {
      return null;
    }

    retValue = value.toString().trim();
    return retValue;
  }

  /**
   * 将值转换为Boolean类型
   * 
   * @param value 要转换的值
   * @return 类型为Boolean的值
   */
  public static Boolean getBoolean(Object value) {
    Boolean retValue = null;
    if (value == null) {
      return Boolean.FALSE;
    }
    if (value instanceof Boolean) {
      retValue = (Boolean) value;
      retValue = retValue.booleanValue() ?Boolean.TRUE : Boolean.FALSE;
    }
    else {
      retValue = Boolean.valueOf(value.toString().trim());
      retValue =
          Boolean.TRUE.equals(retValue) ?Boolean.TRUE : Boolean.FALSE;
    }
    return retValue;
  }

 
 
  /**
   * 将值转换为Double类型
   * 
   * @param value 要转换的值
   * @return 类型为Double的值
   */
  public static Double getDouble(Object value) {
    Double ret = null;
    if (value == null) {
      return null;
    }

    if (value instanceof Double) {
      ret = (Double) value;
    }
    else if (value instanceof BigDecimal) {
      BigDecimal temp = (BigDecimal) value;
      ret = temp.doubleValue();
    }
    else if (value instanceof Number) {
      Number number = (Number) value;
      double temp = number.doubleValue();
      ret = new Double(temp);
    }
    else {
      String str = value.toString();
      try {
        ret = new Double(str);
      }
      catch (Exception ex) {
        ValueUtils.throwIllegalArgumentException(value, ex);
      }
    }
    return ret;
  }

 


 

  private static void throwIllegalArgumentException(Object value, Exception ex) {
    StringBuffer bfer = new StringBuffer();
    bfer.append("the value is:");
    bfer.append(value);
    bfer.append(" the error message is :");
    bfer.append(ex.getMessage());
    throw new IllegalArgumentException(bfer.toString());
  }

  /**
   * 根据元数据属性信息中定义的数据类型转换值的类型
   * 
   * @param value 要转换的值
   * @param attribute 元数据属性信息
   * @return 元数据属性信息所定义数据类型的值
   */
  public static Object convert(Object value, String attribute,JavaType type) {
    
    Object ret = value;
    if (type == JavaType.Double) {
      ret = ValueUtils.getDouble(value);
    }
    else if (type == JavaType.String) {
      ret = ValueUtils.getString(value);
    }
    else if (type == JavaType.Integer) {
      ret = ValueUtils.getInteger(value);
    }
    else if (type == JavaType.Boolean) {
      ret = ValueUtils.getBoolean(value);
    }
    else if (type == JavaType.Date) {
      //ret = ValueUtils.getDate(value);
    	 ret = ValueUtils.getString(value);
    }
  
    else if (type == JavaType.BigDecimal) {
      ret = ValueUtils.getDouble(value);
    }
    else if (type == JavaType.Object) {
      ret = value;
    }
    else {
      String message = "不支持此种业务，请检查"; /*-=notranslate=-*/
      throw new IllegalArgumentException(message);
    }
    return ret;
  }

}
