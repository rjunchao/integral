package com.xbkj.common.util;

import java.util.Collection;
import java.util.Map;

public class ToolUtil
{
  private static final char SEPARATOR = '_';
  public static final String strNull = null;

  public static boolean isNullStr(String str)
  {
    return (str == null) || ("".equals(str.trim()));
  }

  public static boolean isNotNullStr(String str)
  {
    return !isNullStr(str);
  }

  public static boolean isNull(Object obj)
  {
    return obj == null;
  }

  public static boolean isNotNull(Object obj) {
    return !isNull(obj);
  }

  public static boolean isNullCollection(Collection<?> coll)
  {
    return (coll == null) || (coll.isEmpty());
  }

  public static boolean isNotNullCollection(Collection<?> coll) {
    return !isNullCollection(coll);
  }

  public static boolean isNullArr(Object[] arr)
  {
    return (arr == null) || (arr.length == 0);
  }

  public static boolean isNullMap(Map<?, ?> map)
  {
    return (map == null) || (map.isEmpty());
  }

  public static boolean isNotNullMap(Map<?, ?> map)
  {
    return !isNullMap(map);
  }

  public static String formatStrToCamel(String field)
  {
    if (isNullStr(field)) {
      return null;
    }
    field = field.toLowerCase();
    StringBuilder sb = new StringBuilder(field.length());
    boolean upperCase = false;
    for (int i = 0; i < field.length(); i++) {
      char c = field.charAt(i);
      if (c == '_') {
        upperCase = true;
      } else if (upperCase) {
        sb.append(Character.toUpperCase(c));
        upperCase = false;
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    Double d = Double.valueOf(123456010.1415921D);
    int a = 100;
    System.out.println(d.doubleValue() * a);
  }
}