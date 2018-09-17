package com.pub.xbkj.pubapp.converter;

import java.util.Locale;

public class LocaleConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if ((from == null) || ((from instanceof Locale))) {
      return from;
    }
    String[] parts = Strings.toArray(from.toString(), "_");
    String language = parts.length > 0 ? parts[0] : "";
    String country = parts.length > 1 ? parts[1] : "";
    String variant = parts.length > 2 ? parts[2] : "";
    return language.length() > 0 ? new Locale(language, country, variant) : null;
  }
}