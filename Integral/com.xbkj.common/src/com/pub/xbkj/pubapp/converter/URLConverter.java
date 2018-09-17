package com.pub.xbkj.pubapp.converter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    if ((from == null) || ((from instanceof URL))) {
      return from;
    }
    try
    {
      return new URL(from.toString());
     } 
      catch (MalformedURLException e) {
    	  throw new ConvertException(e.getMessage());
    }
    
  }
}