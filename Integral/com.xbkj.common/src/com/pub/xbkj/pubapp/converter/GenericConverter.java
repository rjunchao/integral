package com.pub.xbkj.pubapp.converter;

public abstract class GenericConverter
  implements Converter
{
  public Object convert(Object from, Class<?> toClass)
  {
    return convert(from, toClass, new GenericContext(toClass));
  }

  public abstract Object convert(Object paramObject, Class<?> paramClass, GenericContext paramGenericContext);
}