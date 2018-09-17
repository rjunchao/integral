package com.pub.xbkj.pubapp.converter;

import java.io.File;

public class FileConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if (from == null)
      return null;
    if ((from instanceof File)) {
      return from;
    }
    return new File(from.toString());
  }
}