package com.pub.xbkj.pubapp.converter;

import java.sql.Time;

public class SqlTimeConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if (((from instanceof Time)) || (from == null))
      return from;
    if ((from instanceof Number)) {
      return new Time(((Number)from).longValue());
    }
    return Time.valueOf(from.toString());
  }
}