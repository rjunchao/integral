package com.pub.xbkj.pubapp.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter
  implements Converter
{
  private final DateFormat dateFormat;

  public DateConverter()
  {
    this(null);
  }

  public DateConverter(DateFormat df) {
    this.dateFormat = (df == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : df);
  }

  public Object convert(Object from, Class<?> toClass) {
    if ((from == null) || ((from instanceof Date))) {
      return from;
    }

    if ((from instanceof Number))
      return new Date(((Number)from).longValue());
    if ((from instanceof Calendar)) {
      return ((Calendar)from).getTime();
    }
    String str = from.toString();
    try {
      return this.dateFormat.parse(str);
    } catch (Throwable t) {
    	  throw new ConvertException("Could not parse date: " + t.getMessage());
    }
  
  }
}