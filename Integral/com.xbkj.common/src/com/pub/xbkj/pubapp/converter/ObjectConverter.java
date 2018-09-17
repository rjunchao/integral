package com.pub.xbkj.pubapp.converter;

import java.lang.reflect.Constructor;

public class ObjectConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
	 Constructor<?> ctor;
    if (from == null) {
      return null;
    }
    try
    {
      ctor = toClass.getConstructor(new Class[] { from.getClass() });
    }
    catch (Exception e)
    {
      throw new ConvertException("can not convert " + from + "(" + 
        from.getClass().getName() + ") to " + toClass.getName());
    }
  
    if (ctor != null) {
      try {
        return ctor.newInstance(new Object[] { from });
      } catch (Exception e) {
        throw new ConvertException("can not convert " + from + "(" + 
          from.getClass().getName() + ") to " + 
          toClass.getName());
      }
    }
    throw new ConvertException("can not convert " + from + "(" + 
      from.getClass().getName() + ") to " + toClass.getName());
  }
}