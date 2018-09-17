package com.pub.xbkj.pubapp.converter;

public abstract interface IConverterManager
{
  public abstract Converter findConverter(Class<?> paramClass);
}