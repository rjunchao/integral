package com.pub.xbkj.pubapp.converter;

import java.lang.reflect.Type;

public class GenericContext
{
  private Class<?> baseClass;
  private Type targetType;
  private ConverterManager converterManager = null;

  public GenericContext() {
    this(null, null, null);
  }

  public GenericContext(ConverterManager cm) {
    this(cm, null);
  }

  public GenericContext(Class<?> baseClass) {
    this(null, baseClass);
  }

  public GenericContext(ConverterManager cm, Class<?> baseClass) {
    this(cm, baseClass, null);
  }

  public GenericContext(ConverterManager cm, Class<?> baseClass, Type targetType) {
    this.converterManager = (cm == null ? new ConverterManager() : cm);
    this.baseClass = baseClass;
    this.targetType = targetType;
  }

  public ConverterManager getConverterManager() {
    return this.converterManager;
  }

  public Class<?> getBaseClass() {
    return this.baseClass;
  }

  public void setTargetType(Type t) {
    this.targetType = t;
  }

  public Type getTargetType() {
    return this.targetType;
  }

  public GenericContext newGenericContext() {
    return new GenericContext(this.converterManager, this.baseClass);
  }

  public Object convert(Object from, Class<?> toClass) {
    if (from == null)
      return null;
    if (toClass.isInstance(from)) {
      return from;
    }
    Converter c = getConverterManager().findConverter(toClass);
    if (c != null) {
      if ((c instanceof GenericConverter)) {
        return ((GenericConverter)c).convert(from, toClass, this);
      }
      return c.convert(from, toClass);
    }
    throw new ConvertException(String.format("can't convert %s(%s) to %s", new Object[] { from, from.getClass(), toClass.getName() }));
  }
}