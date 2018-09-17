package com.pub.xbkj.pubapp.converter;

import java.util.Calendar;

public class SqlDateConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if (from == null) {
      return null;
    }
    if ((from instanceof java.sql.Date)) {
      return from;
    }

    if ((from instanceof Number))
      return new java.sql.Date(((Number)from).longValue());
    if ((from instanceof java.util.Date))
      return new java.sql.Date(((java.util.Date)from).getTime());
    if ((from instanceof Calendar))
      return new java.sql.Date(((Calendar)from).getTimeInMillis());
    try
    {
      return java.sql.Date.valueOf(from.toString()); } catch (Exception e) {
    }
    throw new ConvertException("The sql date as string must be the format yyyy-mm-dd");
  }
}