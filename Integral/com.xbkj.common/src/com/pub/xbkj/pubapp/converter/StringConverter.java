package com.pub.xbkj.pubapp.converter;


public class StringConverter
  implements Converter
{
  public Object convert(Object value, Class<?> toClass)
  {
    if ((value == null) || ((value instanceof String))) {
      return value;
    }
    return Strings.toString(value);
  }
}